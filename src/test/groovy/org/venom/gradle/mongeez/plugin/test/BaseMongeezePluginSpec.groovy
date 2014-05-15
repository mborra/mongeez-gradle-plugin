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
package org.venom.gradle.mongeez.plugin.test

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.venom.gradle.mongeez.plugin.tasks.MongeezUpdateTask
import spock.lang.Specification

/**
 * Created by marino.borra@gmail.com on 02/05/2014.
 */
class MongeezPluginSpec extends Specification {

	def "Execute apply mongeez plugin"() {
		given:
			def Project project = ProjectBuilder.builder().build()
		when:
			project.apply plugin: "mongeez"
		then:
			project.tasks.mongeezUpdate instanceof MongeezUpdateTask
	}

}
