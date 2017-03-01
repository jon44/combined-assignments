package com.cooksys.ftd.assignments.concurrency;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.cooksys.ftd.assignments.concurrency.model.config.Config;

public class Main {

    /**
     * First, load a {@link com.cooksys.ftd.assignments.concurrency.model.config.Config} object from
     * the <project-root>/config/config.xml file.
     *
     * If the embedded {@link com.cooksys.ftd.assignments.concurrency.model.config.ServerConfig} object
     * is not disabled, create a {@link Server} object with the server config and spin off a thread to run it.
     *
     * If the embedded {@link com.cooksys.ftd.assignments.concurrency.model.config.ClientConfig} object
     * is not disabled, create a {@link Client} object with the client config and spin off a thread to run it.
     */
    public static void main(String[] args) {
    	
    	Path path = Paths.get("./config/config.xml");
    	Config config = Config.load(path);
    	
    	if(!config.getServer().isDisabled()){
    		Server server = new Server(config.getServer());
    		Thread serverThread = new Thread(server);
    		serverThread.start();
    	}
    	
    	if(!config.getClient().isDisabled()){
    		Client client = new Client(config.getClient());
    		Thread clientThread = new Thread(client);
    		clientThread.start();
    	}
    }
}
