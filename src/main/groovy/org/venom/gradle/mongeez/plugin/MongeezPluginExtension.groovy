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
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.internal.reflect.Instantiator
import org.venom.gradle.mongeez.model.MongeezDatabase

/**
 * Created by Marino Borra on 01/05/2014.
 */
@Slf4j
class MongeezPluginExtension {

	final NamedDomainObjectContainer<MongeezDatabase> databases

	private final Instantiator instantiator

	private final Project project

	MongeezDatabase defaultDatabase

	String description

	Boolean verbose = false

	String version;

	/**
	 * <p></p>
	 *
	 * @param project
	 * @param instantiator
	 * @param databases
	 */
	MongeezPluginExtension( final Project project, final Instantiator instantiator, databases  ) {
		this.instantiator = instantiator
		this.project = project
		this.databases = databases
	}

	public Project getProject() {
		return this.project
	}

	def databases( Closure closure ) {
		databases.configure( closure )
	}

}
