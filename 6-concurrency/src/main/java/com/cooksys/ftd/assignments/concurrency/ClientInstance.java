package com.cooksys.ftd.assignments.concurrency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.Socket;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.cooksys.ftd.assignments.concurrency.model.config.ClientInstanceConfig;
import com.cooksys.ftd.assignments.concurrency.model.message.Request;
import com.cooksys.ftd.assignments.concurrency.model.message.Response;

public class ClientInstance implements Runnable {
	
	private int delay = -1;
	private List<Request> request;
	Socket socket;

    public ClientInstance(ClientInstanceConfig config, Socket socket) {
        this.delay = config.getDelay();
        this.request = config.getRequests();
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
        	JAXBContext requestContext = Request.context();
            JAXBContext responseContext = Response.context();
        	
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        	PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        	Unmarshaller unmarshaller = responseContext.createUnmarshaller();
			Marshaller marshaller = requestContext.createMarshaller();
			
			for(int i = 0; i < request.size(); i++) {
				
				if(delay > 0 && i != 0) {
					Thread.sleep(delay);
				}
				
				StringWriter stringWriter = new StringWriter();
				marshaller.marshal(request.get(i),  stringWriter);
				printWriter.println(stringWriter.toString());
				printWriter.flush();
				
				String responseString = bufferedReader.readLine();
				StringReader stringReader = new StringReader(responseString);
				Response response = (Response) unmarshaller.unmarshal(stringReader);
				
				System.out.println(response.getData() + "\n");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
    }
}
