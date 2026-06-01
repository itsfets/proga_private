package core;

import dto.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.TreeSet;


public class StandardDumper {
    private final DocumentBuilder dBuilder;
    private String fileName;

    public StandardDumper() {
        String ENV_NAME = "TEMP";
        String path = System.getenv().get(ENV_NAME);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            this.dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        if (path != null) {
            if (Files.isDirectory(Paths.get(path))) {
                this.fileName = String.valueOf(Paths.get(path, "/collection.xml"));
            } else {
                this.fileName = path;
            }
        } else {
            System.err.println("Couldn't get path for system variable " + ENV_NAME + "!\nExiting the program!");
            System.exit(0);
        }
    }

    public TreeSet<StudyGroup> readCollection() {
        TreeSet<StudyGroup> studyGroups = new TreeSet<>();
        StringBuilder fileContent = new StringBuilder();
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                System.out.println("File " + fileName + " does not exist!");
            }
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                fileContent.append(scanner.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Initialized an empty collection");
            return studyGroups;
        } catch (NullPointerException e) {
            System.out.println("Required system variable does not exist! Initialized a new Collection!");
            return studyGroups;
        }
        if (fileContent.isEmpty()) {
            return studyGroups;
        }
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(fileContent.toString().getBytes());
            Document collection = dBuilder.parse(inputStream);
            collection.getDocumentElement().normalize();
            NodeList nodeList = collection.getElementsByTagName("studyGroup");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                Element studyGroupCoordinates = (Element) element.getElementsByTagName("studyGroupCoordinates").item(0);

                StudyGroup studyGroup = new StudyGroup();

                studyGroup.setId(Integer.parseInt(element.getAttribute("id")));
                studyGroup.setName(element.getAttribute("name"));
                studyGroup.setCoordinates(new Coordinates(
                        Integer.parseInt(studyGroupCoordinates.getAttribute("x")),
                        Integer.parseInt(studyGroupCoordinates.getAttribute("y"))));
                studyGroup.setCreationDate(LocalDateTime.parse(element.getAttribute("creationDate")));
                studyGroup.setStudentsCount(Long.parseLong(element.getAttribute("studentsCount")));
                studyGroup.setTransferredStudents(Long.parseLong(element.getAttribute("transferredStudents")));
                studyGroup.setAverageMark(Double.parseDouble(element.getAttribute("averageMark")));
                studyGroup.setFormOfEducation(FormOfEducation.valueOf(element.getAttribute("formOfEducation")));


                Element studyGroupAdmin = (Element) element.getElementsByTagName("Person").item(0);
                if (studyGroupAdmin.getAttribute("null").equals("FALSE")) {
                    Element studyGroupAdminLocation = (Element) studyGroupAdmin.getElementsByTagName("groupAdminLocation").item(0);
                    studyGroup.setGroupAdmin(new Person(
                            studyGroupAdmin.getAttribute("name"),
                            Double.parseDouble(studyGroupAdmin.getAttribute("height")),
                            get_color(studyGroupAdmin.getAttribute("eyeColor")),
                            new Location(
                                    Float.parseFloat(studyGroupAdminLocation.getAttribute("x")),
                                    Double.parseDouble(studyGroupAdminLocation.getAttribute("y")),
                                    Long.parseLong(studyGroupAdminLocation.getAttribute("z")),
                                    studyGroupAdminLocation.getAttribute("name"))));
                } else studyGroup.setGroupAdmin(null);

                studyGroups.add(studyGroup);
            }
        } catch (EOFException e) {
            System.out.println("[INFO] Collection is empty!");
            return studyGroups;
        } catch (IOException | SAXException e) {
            System.err.println(e.getMessage());
        }
        return studyGroups;
    }

    private Document buildDoc(TreeSet<StudyGroup> collection) {
        Document doc = dBuilder.newDocument();
        Element rootElement = doc.createElement("collection");
        doc.appendChild(rootElement);
        for (StudyGroup studyGroup : collection) {
            Element studyGroupElement = doc.createElement("studyGroup");
            studyGroupElement.setAttribute("id", String.valueOf(studyGroup.getId()));
            studyGroupElement.setAttribute("name", studyGroup.getName());

            Element studyGroupCoordinates = doc.createElement("studyGroupCoordinates");
            studyGroupCoordinates.setAttribute("x", String.valueOf(studyGroup.getCoordinates().getX()));
            studyGroupCoordinates.setAttribute("y", String.valueOf(studyGroup.getCoordinates().getY()));
            studyGroupElement.appendChild(studyGroupCoordinates);

            studyGroupElement.setAttribute("creationDate", String.valueOf(studyGroup.getCreationDate()));
            studyGroupElement.setAttribute("studentsCount", String.valueOf(studyGroup.getStudentsCount()));
            studyGroupElement.setAttribute("transferredStudents", String.valueOf(studyGroup.getTransferredStudents()));
            studyGroupElement.setAttribute("averageMark", String.valueOf(studyGroup.getAverageMark()));
            studyGroupElement.setAttribute("formOfEducation", studyGroup.getFormOfEducation().toString());

            Element groupAdminElement = doc.createElement("Person");
            if (studyGroup.getGroupAdmin() != null) {
                groupAdminElement.setAttribute("null", Boolean.FALSE.toString());
                groupAdminElement.setAttribute("name", studyGroup.getGroupAdmin().getName());
                groupAdminElement.setAttribute("height", String.valueOf(studyGroup.getGroupAdmin().getHeight()));
                if (studyGroup.getGroupAdmin().getEyeColor() != null) {
                    groupAdminElement.setAttribute("eyeColor", studyGroup.getGroupAdmin().getEyeColor().toString());
                } else groupAdminElement.setAttribute("eyeColor", null);

                Element groupAdminLocationElement = doc.createElement("groupAdminLocation");
                groupAdminLocationElement.setAttribute("x", String.valueOf(studyGroup.getGroupAdmin().getLocation().getX()));
                groupAdminLocationElement.setAttribute("y", String.valueOf(studyGroup.getGroupAdmin().getLocation().getY()));
                groupAdminLocationElement.setAttribute("z", String.valueOf(studyGroup.getGroupAdmin().getLocation().getZ()));
                groupAdminLocationElement.setAttribute("name", studyGroup.getGroupAdmin().getLocation().getName());

                groupAdminElement.appendChild(groupAdminLocationElement);
            } else {
                groupAdminElement.setAttribute("null", Boolean.TRUE.toString());
            }
            studyGroupElement.appendChild(groupAdminElement);

            rootElement.appendChild(studyGroupElement);
        }
        return doc;
    }

    public void saveCollection(TreeSet<StudyGroup> collection) {
        try {
            Document doc = buildDoc(collection);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource domSource = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileWriter(fileName));
            transformer.transform(domSource, result);
        } catch (IOException e) {
            System.out.println("Access denied!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private Color get_color(String color) {
        try {
            if (color != null) {
                return Color.valueOf(color);
            } else return null;
        } catch (IllegalArgumentException e) {
            System.out.println("Detected an unknown value in groupAdmin.eyeColor! It was replaced with null value!");
            return null;
        }
    }
}
