package org.example.Parsers;

import org.example.Medicin.Medicin;
import org.example.Medicin.Medicin.Version;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class STAXMedicinParser {

    private static final Logger logger = LogManager.getLogger(STAXMedicinParser.class);

    public List<Medicin> parse(String xmlFilePath) {
        List<Medicin> medicins = new ArrayList<>();
        logger.info("Начало парсинга файла: {}", xmlFilePath);

        Medicin.MedicinBuilder medicinBuilder = null;
        Version currentVersion = null;
        String currentElement = "";
        List<String> analogsList = new ArrayList<>();
        List<Version> versionsList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(xmlFilePath)) {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(fis);

            while (reader.hasNext()) {
                int event = reader.next();

                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:
                        currentElement = reader.getLocalName();

                        if ("Medicin".equals(currentElement)) {
                            medicinBuilder = new Medicin.MedicinBuilder(
                                    reader.getAttributeValue(null, "Id"),
                                    reader.getAttributeValue(null, "Name")
                            );
                            logger.debug("Начало парсинга элемента Medicin с id: {}", reader.getAttributeValue(null, "Id"));
                            analogsList.clear();
                            versionsList.clear();
                        } else if ("Version".equals(currentElement)) {
                            currentVersion = new Version(
                                    "", "", "", "", "", "", "", 0, 0.0f, "", ""
                            );
                            logger.debug("Начало парсинга элемента Version");
                        }
                        break;

                    case XMLStreamConstants.CHARACTERS:
                        String data = reader.getText().trim();

                        if (medicinBuilder != null && !data.isEmpty()) {
                            switch (currentElement) {
                                case "Pharm":
                                    medicinBuilder.withPharm(data);
                                    break;
                                case "Group":
                                    medicinBuilder.withGroup(data);
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
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        currentElement = reader.getLocalName();

                        if ("Medicin".equals(currentElement) && medicinBuilder != null) {
                            medicinBuilder.withAnalogs(analogsList.toArray(new String[0]));
                            medicinBuilder.withVersions(versionsList.toArray(new Version[0]));
                            medicins.add(medicinBuilder.build());
                            logger.debug("Лекарственный препарат добавлен в список: {}", medicinBuilder.build().getName());
                        } else if ("Version".equals(currentElement) && currentVersion != null) {
                            versionsList.add(currentVersion);
                            logger.debug("Версия лекарства добавлена в список");
                        }
                        break;
                }
            }
            logger.info("Парсинг завершен, найдено {} лекарственных препаратов", medicins.size());

        } catch (XMLStreamException | NumberFormatException e) {
            logger.error("Ошибка при парсинге XML файла: {}", xmlFilePath, e);
        } catch (Exception e) {
            logger.error("Неизвестная ошибка: {}", xmlFilePath, e);
        }

        return medicins;
    }
}