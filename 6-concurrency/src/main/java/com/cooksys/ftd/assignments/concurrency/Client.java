package com.cooksys.ftd.assignments.concurrency;

import java.util.List;

import com.cooksys.ftd.assignments.concurrency.model.config.ClientConfig;
import com.cooksys.ftd.assignments.concurrency.model.config.ClientInstanceConfig;
import com.cooksys.ftd.assignments.concurrency.model.config.SpawnStrategy;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Client implements Runnable {

	private boolean isDisabled = false;
	private int port = 8080;
	private int maxInstances = -1;
	private SpawnStrategy spawnStrategy = SpawnStrategy.NONE;
    private List<ClientInstanceConfig> instances;

	
    public Client(ClientConfig config) {
        this.isDisabled = config.isDisabled();
        this.port = config.getPort();
        this.maxInstances = config.getMaxInstances();
        this.spawnStrategy = config.getSpawnStrategy();
        this.instances = config.getInstances();
    }

    @Override
    public void run() {
        throw new NotImplementedException();
    }
}
