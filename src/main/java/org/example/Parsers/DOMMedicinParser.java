package org.example.Parsers;

import org.example.Medicin.Medicin;
import org.example.Medicin.Medicin.Version;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DOMMedicinParser {

    private static final Logger logger = LogManager.getLogger(DOMMedicinParser.class);

    public List<Medicin> parse(String xmlFilePath) {
        List<Medicin> medicins = new ArrayList<>();
        logger.info("Начало парсинга файла: {}", xmlFilePath);
        try {
            File inputFile = new File(xmlFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList medicinList = doc.getElementsByTagName("Medicin");
            logger.debug("Найдено {} элементов Medicin", medicinList.getLength());

            for (int i = 0; i < medicinList.getLength(); i++) {
                Node node = medicinList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element medicinElement = (Element) node;

                    // Получаем Id и Name как атрибуты
                    String id = medicinElement.getAttribute("Id");
                    String name = medicinElement.getAttribute("Name");

                    Medicin.MedicinBuilder builder = new Medicin.MedicinBuilder(id, name)
                            .withPharm(medicinElement.getElementsByTagName("Pharm").item(0).getTextContent())
                            .withGroup(medicinElement.getElementsByTagName("Group").item(0).getTextContent());

                    // Парсинг аналогов
                    NodeList analogsList = medicinElement.getElementsByTagName("Analog");
                    String[] analogs = new String[analogsList.getLength()];
                    for (int j = 0; j < analogsList.getLength(); j++) {
                        analogs[j] = analogsList.item(j).getTextContent();
                    }
                    builder.withAnalogs(analogs);

                    // Парсинг версий
                    NodeList versionList = medicinElement.getElementsByTagName("Version");
                    Version[] versions = new Version[versionList.getLength()];
                    for (int j = 0; j < versionList.getLength(); j++) {
                        Element versionElement = (Element) versionList.item(j);

                        Version version = new Version(
                                versionElement.getElementsByTagName("Form").item(0).getTextContent(),
                                versionElement.getElementsByTagName("Manufacturer").item(0).getTextContent(),
                                versionElement.getElementsByTagName("Number").item(0).getTextContent(),
                                versionElement.getElementsByTagName("Issued").item(0).getTextContent(),
                                versionElement.getElementsByTagName("Expires").item(0).getTextContent(),
                                versionElement.getElementsByTagName("Organization").item(0).getTextContent(),
                                versionElement.getElementsByTagName("Type").item(0).getTextContent(),
                                Integer.parseInt(versionElement.getElementsByTagName("Quantity").item(0).getTextContent()),
                                Float.parseFloat(versionElement.getElementsByTagName("Price").item(0).getTextContent()),
                                versionElement.getElementsByTagName("Dosage").item(0).getTextContent(),
                                versionElement.getElementsByTagName("Frequency").item(0).getTextContent()
                        );

                        versions[j] = version;
                    }
                    builder.withVersions(versions);

                    medicins.add(builder.build());
                }
            }
            logger.info("Парсинг завершен, найдено {} лекарств", medicins.size());
        } catch (Exception e) {
            logger.error("Ошибка при парсинге XML файла: {}", xmlFilePath, e);
        }
        return medicins;
    }
}

