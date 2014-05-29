mongeez-gradle-plugin <sub>(Experimental)</sub> [![Build Status](https://travis-ci.org/mborra/mongeez-gradle-plugin.svg?branch=master)](https://travis-ci.org/mborra/mongeez-gradle-plugin)
=====================

[Mongeez](https://github.com/secondmarket/mongeez) ([MongoDB](http://www.mongodb.org) Easy Change Management) [Gradle](http://www.gradle.org) Plugin

```groovy

	buildscript {
		repositories {
			mavenCentral()
			mavenLocal()
		}
		dependencies {
			classpath "org.venom.gradle:mongeez-gradle-plugin:1.0.0-SNAPSHOT"
		}
	}

	apply plugin: 'mongeez'

	mongeez {
		version = '1.0.0'

		verbose = true

		description = "Migrate MongoDB configurations"

		databases {
			local {
				description = "Local development database"
				databaseName = "local"
				servers {
					servera {
						host = "localhost"
						port = 27017
					}
				}
			}
			social {
				description = "Reactive Social development database"
				databaseName = "social"
				servers {
					servera {
						host = "localhost"
						port = 27017
					}
				}
			}
		}

		defaultDatabase = databases.local
	}

	task mongeezUpdateLocal( type: MongeezUpdateTask, description: "Update MongoDB" ) {
		changeFiles { "$projectDir/src/main/resources/mongeez.xml" }
	}

```

## Repository
	Coming soon...