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
package org.venom.gradle.mongeez.plugin.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import org.venom.gradle.mongeez.MongeezBuilder
import org.venom.gradle.mongeez.model.MongeezDatabase
import org.venom.gradle.mongeez.plugin.MongeezPluginExtension

/**
 * Created by Marino Borra on 02/05/2014.
 */
class MongeezUpdateTask extends DefaultTask {

	private final MongeezPluginExtension pluginExtension;

	@Nested @Optional
	private MongeezDatabase database

	@Input
	def changeFiles

	/**
	 * <p>Constructor</p>
	 */
	MongeezUpdateTask() {
		pluginExtension = project.
							getExtensions().
								getByType( MongeezPluginExtension );
	}

	/**
	 *
	 */
	@TaskAction
	def update() {
		MongeezBuilder.newBuilder().build {
			database this.resolvesDatabaseConfiguration()
			changeFiles this.getChangeFiles()
		}.process()
	}

	/**
	 *
	 * @return project resolved changeFiles as a java.io.File
	 */
	def getChangeFiles() {
		project.file( this.changeFiles )
	}

	/**
	 * <p></p>
	 *
	 * @return a database configuration
	 */
	def resolvesDatabaseConfiguration() {
		this.database ?: this.pluginExtension?.getDefaultDatabase()
	}

	/**
	 * <p></p>
	 *
	 * @param closure
	 * @return
	 */
	def database( Closure closure ) {
		this.database = new MongeezDatabase( name )
		project.configure( this.database, closure )
	}

}
