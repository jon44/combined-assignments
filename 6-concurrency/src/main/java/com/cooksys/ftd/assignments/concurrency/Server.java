package com.cooksys.ftd.assignments.concurrency;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.cooksys.ftd.assignments.concurrency.model.config.ServerConfig;

public class Server implements Runnable {
	
	private boolean isDisabled = false;
	private int port = 8080;
	private int maxClients = -1;
	private List<Thread> threadList = new ArrayList<>();

    public Server(ServerConfig config) {
        this.isDisabled = config.isDisabled();
        this.port = config.getPort();
        this.maxClients = config.getMaxClients();
    }

    @Override
    public void run() {
    	
    	try {
			ServerSocket serverSocket = new ServerSocket(port);
			serverSocket.setSoTimeout(60000);
			threadList.clear();
						
			while(true) {
				
				if(threadList.size() < maxClients) {
					
					Socket instanceSocket = serverSocket.accept();
					ClientHandler clientHandler = new ClientHandler(instanceSocket);
					Thread thread = new Thread(clientHandler);
					threadList.add(thread);
					thread.start();
				}
				
				for(int i = 0; i < threadList.size(); i++) {
					if(!threadList.get(i).isAlive()) {
						threadList.remove(i);
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	System.out.println("\n---SERVER CLOSING---\n");
    }
}
