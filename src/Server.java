package com.hmkcode.neo4j;

import org.neo4j.graphdb.factory.GraphDatabaseFactory;
//import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.kernel.GraphDatabaseAPI;
import org.neo4j.server.WrappingNeoServerBootstrapper;
import org.neo4j.server.configuration.Configurator;
import org.neo4j.server.configuration.ServerConfigurator;
import org.neo4j.shell.ShellSettings;

@SuppressWarnings("deprecation")
public class Server {
	
	public static void main(String args[])
	        {
	                GraphDatabaseAPI graphdb = (GraphDatabaseAPI) new GraphDatabaseFactory()
	                .newEmbeddedDatabaseBuilder( "db/graphDB" )
	                .setConfig( ShellSettings.remote_shell_enabled, "TRUE" )
	                .newGraphDatabase();
	                ServerConfigurator config;
	                config = new ServerConfigurator( graphdb );
	                // let the server endpoint be on a custom port
	                config.configuration().setProperty(Configurator.WEBSERVER_PORT_PROPERTY_KEY, 7575 );
	 
	                WrappingNeoServerBootstrapper srv;
	                srv = new WrappingNeoServerBootstrapper( graphdb, config );
	                srv.start();
	            }
	        }