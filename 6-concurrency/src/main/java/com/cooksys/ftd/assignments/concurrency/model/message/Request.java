package com.cooksys.ftd.assignments.concurrency.model.message;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Request {
	
	public static JAXBContext context() {
		
		try {
			return JAXBContext.newInstance(Request.class);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		return null;
	}

    @XmlValue
    private RequestType type;

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }
}
