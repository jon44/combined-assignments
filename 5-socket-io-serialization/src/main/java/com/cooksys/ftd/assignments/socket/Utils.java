package com.cooksys.ftd.assignments.socket;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.LocalConfig;
import com.cooksys.ftd.assignments.socket.model.RemoteConfig;
import com.cooksys.ftd.assignments.socket.model.Student;

/**
 * Shared static methods to be used by both the {@link Client} and {@link Server} classes.
 */
public class Utils {
    /**
     * @return a {@link JAXBContext} initialized with the classes in the
     * com.cooksys.socket.assignment.model package
     */
    public static JAXBContext createJAXBContext() {
    	
    	try {
			JAXBContext jaxb = JAXBContext.newInstance(Config.class, LocalConfig.class, RemoteConfig.class, Student.class);
			return jaxb;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
    	
    	return null;
    }

    /**
     * Reads a {@link Config} object from the given file path.
     *
     * @param configFilePath the file path to the config.xml file
     * @param jaxb the JAXBContext to use
     * @return a {@link Config} object that was read from the config.xml file
     */
    public static Config loadConfig(String configFilePath, JAXBContext jaxb) {
    	
    	File configFile = new File(configFilePath);
    	
    	try {
			Unmarshaller unmarshaller = jaxb.createUnmarshaller();
			Config config = (Config) unmarshaller.unmarshal(configFile);
			return config;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
}
