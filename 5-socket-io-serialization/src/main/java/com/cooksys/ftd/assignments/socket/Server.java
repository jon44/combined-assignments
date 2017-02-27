package com.cooksys.ftd.assignments.socket;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.Student;

public class Server extends Utils {

    /**
     * Reads a {@link Student} object from the given file path
     *
     * @param studentFilePath the file path from which to read the student config file
     * @param jaxb the JAXB context to use during unmarshalling
     * @return a {@link Student} object unmarshalled from the given file path
     */
    public static Student loadStudent(String studentFilePath, JAXBContext jaxb) {

    	File studentFile = new File(studentFilePath);
    	
    	try {
			Unmarshaller unmarshaller = jaxb.createUnmarshaller();
			Student student = new Student();
			student = (Student) unmarshaller.unmarshal(studentFile);
			return student;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
    	
    	return null;
    }

    /**
     * The server should load a {@link com.cooksys.ftd.assignments.socket.model.Config} object from the
     * <project-root>/config/config.xml path, using the "port" property of the embedded
     * {@link com.cooksys.ftd.assignments.socket.model.LocalConfig} object to create a server socket that
     * listens for connections on the configured port.
     *
     * Upon receiving a connection, the server should unmarshal a {@link Student} object from a file location
     * specified by the config's "studentFilePath" property. It should then re-marshal the object to xml over the
     * socket's output stream, sending the object to the client.
     *
     * Following this transaction, the server may shut down or listen for more connections.
     */
    public static void main(String[] args) {
    	
    	JAXBContext jaxb = Utils.createJAXBContext();

    	String configFilePath = "./config/config.xml";
    	Config config = Utils.loadConfig(configFilePath, jaxb);
    	
    	int port = config.getLocal().getPort();
    	
    	try {
			ServerSocket serverSocket = new ServerSocket(port);
			Socket clientSocket = serverSocket.accept();
			
			OutputStream out = clientSocket.getOutputStream();
			
			String studentFilePath = config.getStudentFilePath();
			Student student = loadStudent(studentFilePath, jaxb);
			
			Marshaller marshaller = jaxb.createMarshaller();
			//marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(student, out);
			
			serverSocket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (PropertyException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		} 
    }
}
