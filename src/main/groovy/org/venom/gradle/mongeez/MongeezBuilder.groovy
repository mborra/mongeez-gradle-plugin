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
package org.venom.gradle.mongeez

import com.mongodb.Mongo
import com.mongodb.ServerAddress
import groovy.util.logging.Slf4j
import org.gradle.api.tasks.StopExecutionException
import org.mongeez.Mongeez
import org.mongeez.MongoAuth
import org.springframework.core.io.UrlResource
import org.venom.gradle.mongeez.exceptions.NotYetImplemented
import org.venom.gradle.mongeez.model.MongeezAuthentication
import org.venom.gradle.mongeez.model.MongeezDatabase

/**
 * Created by Marino Borra on 06/05/2014.
 */
@Slf4j
class MongeezBuilder implements Builder<Mongeez> {

	private Mongeez mongeez

	/**
	 *
	 * @return
	 */
	static MongeezBuilder newBuilder() {
		new MongeezBuilder();
	}

	/**
	 *
	 * @param definition
	 * @return
	 */
	@Override
	Mongeez build( Closure definition ) {
		this.mongeez = new Mongeez()

		evaluateClosure definition

		this.mongeez
	}

	void authentication( MongeezAuthentication authentication ) {
		throw new NotYetImplemented( "Not yet implemented" )
	}

	/**
	 *
	 * @param database
	 */
	void database( final MongeezDatabase database ) {
		assert database != null, "'database' parameter must not be null"
		configureDatabaseName( database )
		configureAuthentication( database )
		configureServers( database )
	}

	def configureAuthentication( final MongeezDatabase database ) {
		if ( database.getUsername() || database.getPassword() ) {
			mongeez.setAuth( new MongoAuth( database.getUsername(), database.getPassword() ) )
		}
	}

	def configureServers( MongeezDatabase database ) {
		final List<ServerAddress> servers = database.
												getServers().
													findAll { it.host != null && it.port != null }.
													collect { new ServerAddress( it.host, it.port ) };
		log.info( "Server(s):[{}]", servers )
		if ( servers.empty ) {
			throw new StopExecutionException( "" )/* FIXME */
		} else {
			mongeez.setMongo( new Mongo( servers ) )
		}
	}

	def configureDatabaseName( MongeezDatabase database ) {
		mongeez.setDbName( database.getDatabaseName() )
	}

	/**
	 *
	 * @param changeFiles
	 */
	void changeFiles( final File changeFiles ) {
		assert changeFiles != null, "'changeFiles' parameter must not be null"
		if ( changeFiles.exists() ) {
			mongeez.setFile( new UrlResource( "file:".concat( changeFiles.absolutePath ) ) ) /* Is too tightly coupled with Spring*/
		} else {
			/* TODO Implement a StopActionException on function of failFast flag, to create */
			throw new StopExecutionException( "Change files:[${changeFiles}] doesn't exist" )
		}
	}

	def methodMissing( String name, arguments ) {
		log.info( "Method:[${name}] missing" )
		if ( name in ['host', 'port'] ) {
			/* Extract from URI */
		}
	}

	def propertyMissing( String name ) {
		log.info( "Property:[${name}] missing" )
	}

	/**
	 *
	 * @param closure
	 * @return
	 */
	private evaluateClosure( final Closure closure ) {
		final Closure closureClone = closure.clone()
		closureClone.delegate = this
		closureClone.resolveStrategy = Closure.DELEGATE_ONLY
		closureClone()
	}

}