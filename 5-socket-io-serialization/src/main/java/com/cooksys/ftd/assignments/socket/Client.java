package com.cooksys.ftd.assignments.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
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
			InputStreamReader in = new InputStreamReader(clientSocket.getInputStream());
			String readIntoString = new String();
			StringReader reader = new StringReader(readIntoString);
			//char[] charArray = new char[500];
			//in.read(charArray, 0, 500);
			//System.out.println(in.toString());
			//StringReader reader = new StringReader();
			
			Unmarshaller unmarshaller = jaxb.createUnmarshaller();
			System.out.println("before unmarshal student");
			Student student = (Student) unmarshaller.unmarshal(reader);
			//Student student = (Student) unmarshaller.unmarshal(reader);
			//System.out.println(student.getFavoriteIDE());
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
    }
}
