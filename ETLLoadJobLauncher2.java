package com.bcbsfl.pharmacy.etl.load.service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.core.pattern.EqualsIgnoreCaseReplacementConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.bcbsfl.pharmacy.etl.load.entity.PrimePriorAuths;
import com.bcbsfl.pharmacy.etl.load.repository.PrimePriorAuthsRepo;
import com.bcbsfl.pharmacy.etl.load.util.FieldMappingUtil;

@Component
public class ETLLoadJobLauncher2 {
	
	
	@Autowired
	PrimePriorAuthsRepo priorAuthsRepo;
	
    private static final String FILENAME = "C:\\Users\\nazar\\Desktop\\ABC_PA_Daily_Extract_File_20221017.xml";
    

    //@Scheduled(cron = "0 */1 * ? * *")
    public void scheduleTaskWithFixedRate() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            File inputFile = new File(FILENAME);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("prime-transport:PAResponse");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    NodeList childNodes = eElement.getChildNodes();
                    PrimePriorAuths primePriorAuths = new PrimePriorAuths();
                    for (int i = 0; i < childNodes.getLength(); i++) {
                        Node child = childNodes.item(i);
                        if (child.getNodeType() == Node.ELEMENT_NODE) {
                            printNode(child, "", child.getNodeName(), primePriorAuths);
                        }                   
                    }
                    System.out.println(primePriorAuths.toString());
//                    priorAuthsRepo.save(primePriorAuths);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }

    private static boolean isLeafNode(Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            NodeList childNodes = node.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private static void printNode(Node node, String indent, String path , PrimePriorAuths primePriorAuths) {
        if (isLeafNode(node)) {
        	
        	String modifiedPath = createModifiedPath(path);
//            System.out.println(indent + "XPath: " + path + " | Modified: " + modifiedPath + ": " + node.getTextContent().trim());
            FieldMappingUtil.setProperty(primePriorAuths, modifiedPath, node.getTextContent().trim());
            
            //setProperty(primePriorAuths, modifiedPath, node.getTextContent().trim());
            
        }
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node child = childNodes.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                String childPath = path + "/" + child.getNodeName();
                printNode(child, indent + "    ", childPath, primePriorAuths);
            }
        }
    }

    private static void setProperty1(PrimePriorAuths primePriorAuths, String path, String value) {
        try {
            String methodName = "set" + path.replace("_", "");
            Method method = PrimePriorAuths.class.getMethod(methodName, String.class);
            method.invoke(primePriorAuths, value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            System.out.println("Error setting property for path: " + path + " with value: " + value);
        }
    }
    
    
    private static void setProperty(PrimePriorAuths primePriorAuths, String columnName, String value) {
        Field[] fields = PrimePriorAuths.class.getDeclaredFields();

        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            if (column != null && columnName.equalsIgnoreCase(column.name())) {
                String fieldName = field.getName();
                String methodName = "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
                try {
                    Method method = PrimePriorAuths.class.getMethod(methodName, field.getType());
                    // Assuming all fields are of type String. You might need to handle different types.
                    method.invoke(primePriorAuths, value);
                    return;
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    System.out.println("Error setting property for column: " + columnName + " with value: " + value);
                }
            }
        }
    }
    
    private static String createModifiedPath(String path) {
        String[] parts = path.split("/");
        for (int i = 0; i < parts.length; i++) {
            parts[i] = removeNamespace(parts[i]);
        }
        return String.join("_", parts).toLowerCase();
    }

    private static String removeNamespace(String nodeName) {
        return nodeName.contains(":") ? nodeName.substring(nodeName.indexOf(':') + 1) : nodeName;
    }
    

}
