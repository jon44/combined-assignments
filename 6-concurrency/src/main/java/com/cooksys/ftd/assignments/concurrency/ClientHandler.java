package com.cooksys.ftd.assignments.concurrency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.cooksys.ftd.assignments.concurrency.model.message.Request;
import com.cooksys.ftd.assignments.concurrency.model.message.RequestType;
import com.cooksys.ftd.assignments.concurrency.model.message.Response;

public class ClientHandler implements Runnable {
	
	Socket socket;
	
	public ClientHandler(Socket socket){
		this.socket = socket;
	}

    @Override
    public void run() {
    	JAXBContext requestContext = Request.context();
        JAXBContext responseContext = Response.context();
        Boolean done = false;
        RequestType requestType;
        
        try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        	PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			Unmarshaller unmarshaller = requestContext.createUnmarshaller();
			Marshaller marshaller = responseContext.createMarshaller();

        	while(!done) {
				String requestString = bufferedReader.readLine();
				StringReader stringReader = new StringReader(requestString);
				Request request = (Request) unmarshaller.unmarshal(stringReader);
				
				Response response = new Response();
				String responseString;
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
				LocalDateTime now;

				requestType = request.getType();
				switch(requestType) {
					case IDENTITY :
						responseString = "local address: " + socket.getLocalAddress() + " local port: " + socket.getLocalPort();
						response.setData(responseString);
						response.setType(requestType);
						response.setSuccessful(true);
						break;
					case TIME :
						now = LocalDateTime.now();
						responseString = "time: " + now.format(formatter);
						response.setData(responseString);
						response.setType(requestType);
						response.setSuccessful(true);
						break;
					case DONE :
						done = true;
						break;
					default :
						response.setData("---INVALID REQUEST---");
						response.setType(null);
						response.setSuccessful(false);
				}
				
				if(!done) {
					
					StringWriter stringWriter = new StringWriter();
					marshaller.marshal(response, stringWriter);
					printWriter.println(stringWriter.toString());
					printWriter.flush();
				}
			}
        	//after while
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
    }
}
