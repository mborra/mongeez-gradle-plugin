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
package org.venom.gradle.mongeez.model

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.internal.reflect.Instantiator

/**
 * Created by Marino Borra on 05/05/2014.
 */
class MongeezDatabase {

	final NamedDomainObjectContainer<MongeezServer> servers

	final Project project

	final String name

	@Input
	String databaseName

	String description

	String username

	String password

	/**
	 *
	 * @param name
	 * @param project
	 */
	MongeezDatabase( final String name, final Project project ) {
		servers = project.container( MongeezServer ) { String serverName ->
			project.gradle.services.get(Instantiator ).newInstance( MongeezServer, serverName )
		}
		this.project = project
		this.name = name
	}

	def servers( Closure closure ) {
		servers.configure( closure )
	}

}