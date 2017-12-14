package com.butchjgo.dao;

import com.butchjgo.entity.Student;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.annotation.PostConstruct;
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class StudentDAO implements DAO<Student> {
    // more info at: https://www.youtube.com/watch?v=zFufOEsvHqU & https://www.youtube.com/watch?v=Bxv3n4HS4zA

    private String xmlPath = "C:\\Apps\\ds.xml";

    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

    Document document = documentBuilder.newDocument();

    private List<Student> students;

    public StudentDAO() throws ParserConfigurationException {
    }

    @PostConstruct
    void prepareTempData() {
        students = new LinkedList<>();
        try {
            if (!Files.exists(Paths.get(xmlPath))) {

                students = getTMPStudentData();

                document = list2Document(students);

                saveDocument2File(this.document, this.xmlPath);

            } else {
                document = getDocumentFromFile(xmlPath);

                students = document2List(document);
            }
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    private List<Student> getTMPStudentData() {
        List<Student> students = new LinkedList<>();
        students.add(new Student("SE62100", "NGUYEN DAM DUC THOAI", "SE1073", 21));
        students.add(new Student("SE62111", "NGUYEN VAN A", "SE1067", 24));
        students.add(new Student("SE1181", "TRAN THI B", "PC1098", 18));
        return students;
    }

    private List<Student> document2List(Document document) {

        List<Student> students = new LinkedList<>();

        NodeList nodeList = document.getElementsByTagName("student");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            Element element = (Element) node;
            String code = element.getElementsByTagName("code").item(0).getTextContent();
            String name = element.getElementsByTagName("name").item(0).getTextContent();
            String classCode = element.getElementsByTagName("classcode").item(0).getTextContent();

            int age = Integer.parseInt(element.getElementsByTagName("age").item(0).getTextContent());

            Student student = new Student(code, name, classCode, age);
            students.add(student);
        }
        return students;
    }

    private Document list2Document(List<Student> studentList) throws ParserConfigurationException {

        Element element = document.createElement("students");
        document.appendChild(element);

        for (Student student : students) {

            Element studentElement = student2XMLElement(student);

            element.appendChild(studentElement);
        }
        return document;
    }

    private Document getDocumentFromFile(String path) throws IOException, SAXException, ParserConfigurationException {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(path);

        return document;
    }

    private void saveDocument2File(Document document, String path) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transfor = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(path);
        transfor.transform(domSource, streamResult);
    }

    private Element student2XMLElement(Student student) {
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

        /*element1Student.appendChild(element1Code)
                .appendChild(element1Name)
                .appendChild(element1ClassCode)
                .appendChild(element1Age);*/

        return element1Student;
    }

    @Override
    public void create(Student student) {

        NodeList nodeList = document.getElementsByTagName("student");

        Element studentElement = student2XMLElement(student);

        nodeList.item(0).getParentNode().insertBefore(studentElement, nodeList.item(0));

        students.add(student);

        try {
            saveDocument2File(this.document, xmlPath);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateById(String id) {

    }

    @Override
    public void deleteById(String id) {
        Node studentNode = searchNodeById(id);
        if (studentNode == null) return;
        document.getDocumentElement().removeChild(studentNode);
        try {
            saveDocument2File(this.document,xmlPath);
            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).getCode().equals(id)) {
                    students.remove(i);
                    break;
                }
            }
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Student searchById(String id) {

        return null;
    }

    private Node searchNodeById(String id) {
        NodeList nodeList = document.getElementsByTagName("code");
        Node node = null;
        for (int i = 0; i < nodeList.getLength(); i++) {
            String code = nodeList.item(i).getTextContent();
            if (code != null && code.equals(id)) {
                node = nodeList.item(i).getParentNode();
                break;
            }
        }
        return node;
    }

    @Override
    public List<Student> getAll() {
        return this.students;
    }
}
