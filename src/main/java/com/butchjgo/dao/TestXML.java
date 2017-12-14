package com.butchjgo.dao;

import com.butchjgo.entity.Student;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class TestXML {
    public static void main(String[] args) {
        TestXML testXML = new TestXML();
        try {
            //testXML.createXML();
            testXML.readXML();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private List<Student> students = new LinkedList<>();

    public TestXML() {
        students.add(new Student("SE62100", "NGUYEN DAM DUC THOAI", "SE1073", 21));
        students.add(new Student("SE62111", "NGUYEN VAN A", "SE1067", 24));
        students.add(new Student("SE1181", "TRAN THI B", "PC1098", 18));
    }

    public void readXML() throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document document = documentBuilder.parse("C:\\Apps\\ds.xml");

        NodeList nodeList = document.getElementsByTagName("student");

        students = new LinkedList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            Element element = (Element) node;
            String code = element.getElementsByTagName("code").item(0).getTextContent();
            String name = element.getElementsByTagName("name").item(0).getTextContent();
            String classCode = element.getElementsByTagName("classcode").item(0).getTextContent();

            int age = Integer.parseInt(element.getElementsByTagName("age").item(0).getTextContent());

            Student student = new Student(code,name,classCode,age);
            students.add(student);
        }
        System.out.println(students.size());
    }

    public void createXML() throws ParserConfigurationException, TransformerConfigurationException,TransformerException {

        final String xmlPath = "C:\\Apps\\ds.xml";

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document document = documentBuilder.newDocument();

        Element element = document.createElement("students");
        document.appendChild(element);

        for (Student student : students) {
            Element element1Student = document.createElement("student");

            Element element1Code = document.createElement("code");
            element1Code.appendChild(document.createTextNode(student.getCode()));
            element1Student.appendChild(element1Code);

            Element element1Name = document.createElement("name");
            element1Name.appendChild(document.createTextNode(student.getName()));
            element1Student.appendChild(element1Name);

            Element element1ClassCode = document.createElement("classcode");
            element1ClassCode.appendChild(document.createTextNode(student.getClassCode()));
            element1Student.appendChild(element1ClassCode);

            Element element1Age = document.createElement("age");
            element1Age.appendChild(document.createTextNode(String.valueOf(student.getAge())));
            element1Student.appendChild(element1Age);

            element.appendChild(element1Student);
        }
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transfor = transformerFactory.newTransformer();

        DOMSource domSource = new DOMSource(document);

        StreamResult streamResult = new StreamResult(xmlPath);

        transfor.transform(domSource,streamResult);
    }
}
