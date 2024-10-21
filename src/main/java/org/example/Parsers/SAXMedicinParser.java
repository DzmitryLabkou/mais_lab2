package org.example.Parsers;

import org.example.Medicin.Medicin;
import org.example.Medicin.Medicin.Version;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;
import java.util.List;

public class SAXMedicinParser extends DefaultHandler {

    private static final Logger logger = LogManager.getLogger(SAXMedicinParser.class);

    private List<Medicin> medicins = new ArrayList<>();
    private Medicin.MedicinBuilder currentMedicinBuilder;
    private Medicin.Version currentVersion;
    private List<Version> versionsList = new ArrayList<>();
    private List<String> analogsList = new ArrayList<>();
    private String currentElement;

    public List<Medicin> parse(String xmlFilePath) {
        logger.info("Начало парсинга файла: {}", xmlFilePath);
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(xmlFilePath, this);
            logger.info("Парсинг завершен, найдено {} лекарственных препаратов", medicins.size());
        } catch (Exception e) {
            logger.error("Ошибка при парсинге XML файла: {}", xmlFilePath, e);
        }
        return medicins;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("Medicin")) {
            logger.debug("Начало обработки элемента Medicin");
            currentMedicinBuilder = new Medicin.MedicinBuilder(
                    attributes.getValue("Id"),
                    attributes.getValue("Name")
            );
            analogsList.clear();
            versionsList.clear();
        } else if (qName.equalsIgnoreCase("Version")) {
            logger.debug("Начало обработки элемента Version");
            currentVersion = new Version(
                    "", "", "", "", "", "", "", 0, 0.0f, "", ""
            );
        }
        currentElement = qName;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String data = new String(ch, start, length).trim();
        if (currentMedicinBuilder != null && !data.isEmpty()) {
            switch (currentElement) {
                case "Pharm":
                    currentMedicinBuilder.withPharm(data);
                    break;
                case "Group":
                    currentMedicinBuilder.withGroup(data);
                    break;
                case "Analog":
                    analogsList.add(data);
                    break;
                case "Form":
                    currentVersion = new Version(data, currentVersion.getManufacturer(), currentVersion.getCertificateNumber(),
                            currentVersion.getIssuedDate(), currentVersion.getExpiryDate(), currentVersion.getOrganization(),
                            currentVersion.getPackageType(), currentVersion.getQuantity(), currentVersion.getPrice(),
                            currentVersion.getDosage(), currentVersion.getFrequency());
                    break;
                case "Manufacturer":
                    currentVersion = new Version(currentVersion.getForm(), data, currentVersion.getCertificateNumber(),
                            currentVersion.getIssuedDate(), currentVersion.getExpiryDate(), currentVersion.getOrganization(),
                            currentVersion.getPackageType(), currentVersion.getQuantity(), currentVersion.getPrice(),
                            currentVersion.getDosage(), currentVersion.getFrequency());
                    break;
                case "CertificateNumber":
                    currentVersion = new Version(currentVersion.getForm(), currentVersion.getManufacturer(), data,
                            currentVersion.getIssuedDate(), currentVersion.getExpiryDate(), currentVersion.getOrganization(),
                            currentVersion.getPackageType(), currentVersion.getQuantity(), currentVersion.getPrice(),
                            currentVersion.getDosage(), currentVersion.getFrequency());
                    break;
                case "Issued":
                    currentVersion = new Version(currentVersion.getForm(), currentVersion.getManufacturer(), currentVersion.getCertificateNumber(),
                            data, currentVersion.getExpiryDate(), currentVersion.getOrganization(), currentVersion.getPackageType(),
                            currentVersion.getQuantity(), currentVersion.getPrice(), currentVersion.getDosage(), currentVersion.getFrequency());
                    break;
                case "Expires":
                    currentVersion = new Version(currentVersion.getForm(), currentVersion.getManufacturer(), currentVersion.getCertificateNumber(),
                            currentVersion.getIssuedDate(), data, currentVersion.getOrganization(), currentVersion.getPackageType(),
                            currentVersion.getQuantity(), currentVersion.getPrice(), currentVersion.getDosage(), currentVersion.getFrequency());
                    break;
                case "Organization":
                    currentVersion = new Version(currentVersion.getForm(), currentVersion.getManufacturer(), currentVersion.getCertificateNumber(),
                            currentVersion.getIssuedDate(), currentVersion.getExpiryDate(), data, currentVersion.getPackageType(),
                            currentVersion.getQuantity(), currentVersion.getPrice(), currentVersion.getDosage(), currentVersion.getFrequency());
                    break;
                case "PackageType":
                    currentVersion = new Version(currentVersion.getForm(), currentVersion.getManufacturer(), currentVersion.getCertificateNumber(),
                            currentVersion.getIssuedDate(), currentVersion.getExpiryDate(), currentVersion.getOrganization(),
                            data, currentVersion.getQuantity(), currentVersion.getPrice(), currentVersion.getDosage(), currentVersion.getFrequency());
                    break;
                case "Quantity":
                    currentVersion = new Version(currentVersion.getForm(), currentVersion.getManufacturer(), currentVersion.getCertificateNumber(),
                            currentVersion.getIssuedDate(), currentVersion.getExpiryDate(), currentVersion.getOrganization(),
                            currentVersion.getPackageType(), Integer.parseInt(data), currentVersion.getPrice(),
                            currentVersion.getDosage(), currentVersion.getFrequency());
                    break;
                case "Price":
                    currentVersion = new Version(currentVersion.getForm(), currentVersion.getManufacturer(), currentVersion.getCertificateNumber(),
                            currentVersion.getIssuedDate(), currentVersion.getExpiryDate(), currentVersion.getOrganization(),
                            currentVersion.getPackageType(), currentVersion.getQuantity(), Float.parseFloat(data),
                            currentVersion.getDosage(), currentVersion.getFrequency());
                    break;
                case "Dosage":
                    currentVersion = new Version(currentVersion.getForm(), currentVersion.getManufacturer(), currentVersion.getCertificateNumber(),
                            currentVersion.getIssuedDate(), currentVersion.getExpiryDate(), currentVersion.getOrganization(),
                            currentVersion.getPackageType(), currentVersion.getQuantity(), currentVersion.getPrice(),
                            data, currentVersion.getFrequency());
                    break;
                case "Frequency":
                    currentVersion = new Version(currentVersion.getForm(), currentVersion.getManufacturer(), currentVersion.getCertificateNumber(),
                            currentVersion.getIssuedDate(), currentVersion.getExpiryDate(), currentVersion.getOrganization(),
                            currentVersion.getPackageType(), currentVersion.getQuantity(), currentVersion.getPrice(),
                            currentVersion.getDosage(), data);
                    break;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("Medicin")) {
            currentMedicinBuilder.withAnalogs(analogsList.toArray(new String[0]));
            currentMedicinBuilder.withVersions(versionsList.toArray(new Version[0]));
            medicins.add(currentMedicinBuilder.build());
            logger.info("Лекарство добавлено: {}", currentMedicinBuilder.toString());
        } else if (qName.equalsIgnoreCase("Version")) {
            versionsList.add(currentVersion);
            logger.debug("Версия лекарства добавлена: {}", currentVersion.toString());
        }
        currentElement = "";
    }
}