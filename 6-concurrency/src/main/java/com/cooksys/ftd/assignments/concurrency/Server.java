package com.cooksys.ftd.assignments.concurrency;

import com.cooksys.ftd.assignments.concurrency.model.config.ServerConfig;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Server implements Runnable {
	
	private boolean isDisabled = false;
	private int port = 8080;
	private int maxClients = -1;

    public Server(ServerConfig config) {
        this.isDisabled = config.isDisabled();
        this.port = config.getPort();
        this.maxClients = config.getMaxClients();
    }

    @Override
    public void run() {
        throw new NotImplementedException();
    }
}
