package com.cooksys.ftd.assignments.concurrency;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.cooksys.ftd.assignments.concurrency.model.config.ClientConfig;
import com.cooksys.ftd.assignments.concurrency.model.config.ClientInstanceConfig;
import com.cooksys.ftd.assignments.concurrency.model.config.SpawnStrategy;

public class Client implements Runnable {

	private boolean isDisabled = false;
	private int port = 8080;
	private String host = "localhost";
	private int maxInstances = -1;
	private int howManyInstances = 0;
	private SpawnStrategy spawnStrategy = SpawnStrategy.NONE;
    private List<ClientInstanceConfig> instances;
    private List<Thread> threadList = new ArrayList<>();

	
    public Client(ClientConfig config) {
        this.isDisabled = config.isDisabled();
        this.port = config.getPort();
        this.host = config.getHost();
        this.maxInstances = config.getMaxInstances();
        this.spawnStrategy = config.getSpawnStrategy();
        this.instances = config.getInstances();
    }

    @Override
    public void run() {
    	
    	if(spawnStrategy == SpawnStrategy.NONE) {
    		System.out.println("---NO SPAWN STRATEGY---");
    		return;
    	} else if(spawnStrategy == SpawnStrategy.SEQUENTIAL) {
    		howManyInstances = 1;
    	} else if(spawnStrategy == SpawnStrategy.PARALLEL) {
    		howManyInstances = maxInstances;
    	}
    	
    	threadList.clear();
        
    	try {
    		Thread.sleep(5000);
    		
    		int numInstances = 0;
    		while(numInstances < instances.size()) {
    			
    			for(int i = 0; i < howManyInstances; i++) {
    				
    				if(numInstances >= instances.size()) {
    					break;
    				}
    				
    				Socket socket = new Socket(host, port);
        			ClientInstanceConfig instanceConfig = instances.get(numInstances);
        			ClientInstance instance = new ClientInstance(instanceConfig, socket);
        			Thread thread = new Thread(instance);
        			threadList.add(thread);
        			thread.start();
        			numInstances++;
    			}
    			
    			System.out.println(threadList.size());
    			for(int i = 0; i < threadList.size(); i++) {
    				System.out.println(i);
    				System.out.println(threadList.get(i).isAlive());
    				threadList.get(i).join();
    			}
    			
    			threadList.clear();
    		}
		
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
}
