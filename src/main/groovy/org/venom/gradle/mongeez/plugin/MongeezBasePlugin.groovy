/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.venom.gradle.mongeez.plugin

import groovy.util.logging.Slf4j
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.internal.file.FileResolver
import org.gradle.internal.reflect.Instantiator
import org.venom.gradle.mongeez.model.MongeezDatabase
import org.venom.gradle.mongeez.plugin.tasks.MongeezTaskCreator
import org.venom.gradle.mongeez.plugin.tasks.MongeezUpdateTask

import javax.inject.Inject

/**
 * Created by marino.borra@gmail.com on 01/05/2014.
 */
@Slf4j
class MongeezBasePlugin implements Plugin<Project> {

	private final FileResolver fileResolver
	private final Instantiator instantiator

	private final static def NAMESPACE_EXTENSION = 'mongeez'

	private Project project

	@Inject
	MongeezBasePlugin( final Instantiator instantiator, final FileResolver fileResolver ) {
		this.instantiator = instantiator
		this.fileResolver = fileResolver
	}

	/**
	 *
	 * @param project
	 */
	@Override
	void apply( final Project project ) {
		this.project = project

		/* Workaround */
		project.ext.MongeezUpdateTask = MongeezUpdateTask

		def databases = project.container( MongeezDatabase ) { String name ->
			project.gradle.services.get(Instantiator ).newInstance( MongeezDatabase, name, project )
		}

		final MongeezPluginExtension mongeezExtension =
				project.
					extensions.
						create( NAMESPACE_EXTENSION, MongeezPluginExtension, this.project, this.instantiator, databases )

		MongeezTaskCreator.createDefault( project )
	}
}
