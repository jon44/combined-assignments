package com.cooksys.ftd.assignments.file;

import com.cooksys.ftd.assignments.file.model.Contact;
import com.cooksys.ftd.assignments.file.model.Instructor;
import com.cooksys.ftd.assignments.file.model.Session;
import com.cooksys.ftd.assignments.file.model.Student;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

    /**
     * Creates a {@link Student} object using the given studentContactFile.
     * The studentContactFile should be an XML file containing the marshaled form of a
     * {@link Contact} object.
     *
     * @param studentContactFile the XML file to use
     * @param jaxb the JAXB context to use
     * @return a {@link Student} object built using the {@link Contact} data in the given file
     */
    public static Student readStudent(File studentContactFile, JAXBContext jaxb) {
    	
    	Contact contact = null;
        
    	try {
			Unmarshaller unmarshaller = jaxb.createUnmarshaller();
			contact = (Contact) unmarshaller.unmarshal(studentContactFile);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
    	
    	Student student = new Student();
    	student.setContact(contact);
    	
    	return student;
    }

    /**
     * Creates a list of {@link Student} objects using the given directory of student contact files.
     *
     * @param studentDirectory the directory of student contact files to use
     * @param jaxb the JAXB context to use
     * @return a list of {@link Student} objects built using the contact files in the given directory
     */
    public static List<Student> readStudents(File studentDirectory, JAXBContext jaxb) {
    	
    	ArrayList<Student> studentList = new ArrayList<>();
    	File[] studentFiles = studentDirectory.listFiles();
    	
    	for(File i : studentFiles){
    		studentList.add(readStudent(i, jaxb));
    	}
    	
    	return studentList;
    }

    /**
     * Creates an {@link Instructor} object using the given instructorContactFile.
     * The instructorContactFile should be an XML file containing the marshaled form of a
     * {@link Contact} object.
     *
     * @param instructorContactFile the XML file to use
     * @param jaxb the JAXB context to use
     * @return an {@link Instructor} object built using the {@link Contact} data in the given file
     */
    public static Instructor readInstructor(File instructorContactFile, JAXBContext jaxb) {
    	
    	Contact contact = null;
        
    	try {
			Unmarshaller unmarshaller = jaxb.createUnmarshaller();
			contact = (Contact) unmarshaller.unmarshal(instructorContactFile);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
    	
    	Instructor instructor = new Instructor();
    	instructor.setContact(contact);
    	
    	return instructor;
    }

    /**
     * Creates a {@link Session} object using the given rootDirectory. A {@link Session}
     * root directory is named after the location of the {@link Session}, and contains a directory named
     * after the start date of the {@link Session}. The start date directory in turn contains a directory named
     * `students`, which contains contact files for the students in the session. The start date directory
     * also contains an instructor contact file named `instructor.xml`.
     *
     * @param rootDirectory the root directory of the session data, named after the session location
     * @param jaxb the JAXB context to use
     * @return a {@link Session} object built from the data in the given directory
     */
    public static Session readSession(File rootDirectory, JAXBContext jaxb) {
    	
    	Session session = new Session();
    	Instructor instructor = new Instructor();
    	List<Student> studentList = new ArrayList<>();
    	File studentsFile = null;
    	File instructorFile = null;
    	
    	//tempFileList is used to store each folders sub-folders
    	//1. - tempFileList[0] = "date" folder
    	//
    	//2. - tempFileList[0] = "students" folder
    	//	   tempFileList[1] = "instructor.xml"
    	File[] tempFileList;
    	
    	String location = rootDirectory.getName();
    	
    	tempFileList = rootDirectory.listFiles();	//1
    	String date = tempFileList[0].getName();	
    	
    	tempFileList = tempFileList[0].listFiles();	//2
    	studentsFile = tempFileList[1];		
    	instructorFile = tempFileList[0];	
    	
    	instructor = readInstructor(instructorFile, jaxb);
    	studentList = readStudents(studentsFile, jaxb);
    	
    	session.setLocation(location);
    	session.setStartDate(date);
    	session.setInstructor(instructor);
    	session.setStudents(studentList);
    	
    	return session;
    }

    /**
     * Writes a given session to a given XML file
     *
     * @param session the session to write to the given file
     * @param sessionFile the file to which the session is to be written
     * @param jaxb the JAXB context to use
     */
    public static void writeSession(Session session, File sessionFile, JAXBContext jaxb) {
        
    	try {
			Marshaller marshaller = jaxb.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(session, sessionFile);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
    }

    /**
     * Main Method Execution Steps:
     * 1. Configure JAXB for the classes in the com.cooksys.serialization.assignment.model package
     * 2. Read a session object from the <project-root>/input/memphis/ directory using the methods defined above
     * 3. Write the session object to the <project-root>/output/session.xml file.
     *
     * JAXB Annotations and Configuration:
     * You will have to add JAXB annotations to the classes in the com.cooksys.serialization.assignment.model package
     *
     * Check the XML files in the <project-root>/input/ directory to determine how to configure the {@link Contact}
     *  JAXB annotations
     *
     * The {@link Session} object should marshal to look like the following:
     *      <session location="..." start-date="...">
     *           <instructor>
     *               <contact>...</contact>
     *           </instructor>
     *           <students>
     *               ...
     *               <student>
     *                   <contact>...</contact>
     *               </student>
     *               ...
     *           </students>
     *      </session>
     */
    public static void main(String[] args) {
    	
    	File inputFile = new File("./input/memphis/");
    	File outputFile = new File("./output/session.xml");
    	Session session = null;
    	
    	try {
			JAXBContext jaxb = JAXBContext.newInstance(Contact.class, Session.class);
			session = readSession(inputFile, jaxb);
			writeSession(session, outputFile, jaxb);
		} catch (JAXBException e) {
			e.printStackTrace();
		}    	
    }
}
