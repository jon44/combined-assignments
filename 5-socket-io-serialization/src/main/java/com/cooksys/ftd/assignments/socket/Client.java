package com.cooksys.ftd.assignments.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.Student;

public class Client {

    /**
     * The client should load a {@link com.cooksys.ftd.assignments.socket.model.Config} object from the
     * <project-root>/config/config.xml path, using the "port" and "host" properties of the embedded
     * {@link com.cooksys.ftd.assignments.socket.model.RemoteConfig} object to create a socket that connects to
     * a {@link Server} listening on the given host and port.
     *
     * The client should expect the server to send a {@link com.cooksys.ftd.assignments.socket.model.Student} object
     * over the socket as xml, and should unmarshal that object before printing its details to the console.
     */
    public static void main(String[] args) {
    	
    	JAXBContext jaxb = Utils.createJAXBContext();

    	String configFilePath = "./config/config.xml";
    	Config config = Utils.loadConfig(configFilePath, jaxb);
    	    	
    	String hostName = config.getRemote().getHost();
    	int port = config.getRemote().getPort();
    	
    	try {
    		Socket clientSocket = new Socket(hostName, port);
			BufferedReader clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			String stringStudent = clientReader.readLine();
			StringReader stringReader = new StringReader(stringStudent);
			Unmarshaller unmarshaller = jaxb.createUnmarshaller();
			Student student = (Student) unmarshaller.unmarshal(stringReader);
			System.out.println(student.toString());
			
			clientSocket.close();
						
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
    }
}
