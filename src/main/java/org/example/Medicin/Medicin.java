package org.example.Medicin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Medicin {

    private static final Logger logger = LogManager.getLogger(Medicin.class);

    private final String id;
    private final String name;
    private final String pharm;
    private final String group;
    private final String[] analogs;
    private final Version[] versions;

    private Medicin(MedicinBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.pharm = builder.pharm;
        this.group = builder.group;
        this.analogs = builder.analogs;
        this.versions = builder.versions;

        logger.info("Лекарственный препарат создан с id: {} и именем: {}", this.id, this.name);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPharm() {
        return pharm;
    }

    public String getGroup() {
        return group;
    }

    public String[] getAnalogs() {
        return analogs;
    }

    public Version[] getVersions() {
        return versions;
    }

    public static class MedicinBuilder {
        private String id;
        private String name;
        private String pharm = "Unknown"; // Значение по умолчанию
        private String group;
        private String[] analogs = new String[0];
        private Version[] versions = new Version[0];

        public MedicinBuilder(String id, String name) {
            this.id = id;
            this.name = name;
            logger.debug("Создание MedicinBuilder для препарата с id: {}", id);
        }

        public MedicinBuilder withPharm(String pharm) {
            this.pharm = pharm;
            return this;
        }

        public MedicinBuilder withGroup(String group) {
            this.group = group;
            return this;
        }

        public MedicinBuilder withAnalogs(String[] analogs) {
            this.analogs = analogs;
            return this;
        }

        public MedicinBuilder withVersions(Version[] versions) {
            this.versions = versions;
            return this;
        }

        public Medicin build() {
            logger.debug("Сборка объекта Medicin с id: {}", this.id);
            return new Medicin(this);
        }
    }

    @Override
    public String toString() {
        StringBuilder analogsStr = new StringBuilder();
        for (String analog : analogs) {
            analogsStr.append(analog).append(", ");
        }

        StringBuilder versionsStr = new StringBuilder();
        for (Version version : versions) {
            versionsStr.append(version.toString()).append("\n");
        }

        return "Medicin{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", pharm='" + pharm + '\'' +
                ", group='" + group + '\'' +
                ", analogs=" + analogsStr.toString() +
                ", versions=\n" + versionsStr.toString() +
                '}';
    }

    // Вложенный класс для версий исполнения лекарства
    public static class Version {
        private final String form;
        private final String manufacturer;
        private final String certificateNumber;
        private final String issuedDate;
        private final String expiryDate;
        private final String organization;
        private final String packageType;
        private final int quantity;
        private final float price;
        private final String dosage;
        private final String frequency;

        public Version(String form, String manufacturer, String certificateNumber, String issuedDate,
                       String expiryDate, String organization, String packageType, int quantity,
                       float price, String dosage, String frequency) {
            this.form = form;
            this.manufacturer = manufacturer;
            this.certificateNumber = certificateNumber;
            this.issuedDate = issuedDate;
            this.expiryDate = expiryDate;
            this.organization = organization;
            this.packageType = packageType;
            this.quantity = quantity;
            this.price = price;
            this.dosage = dosage;
            this.frequency = frequency;
        }

        // Геттеры
        public String getForm() {
            return form;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public String getCertificateNumber() {
            return certificateNumber;
        }

        public String getIssuedDate() {
            return issuedDate;
        }

        public String getExpiryDate() {
            return expiryDate;
        }

        public String getOrganization() {
            return organization;
        }

        public String getPackageType() {
            return packageType;
        }

        public int getQuantity() {
            return quantity;
        }

        public float getPrice() {
            return price;
        }

        public String getDosage() {
            return dosage;
        }

        public String getFrequency() {
            return frequency;
        }

        @Override
        public String toString() {
            return "Version{" +
                    "form='" + form + '\'' +
                    ", manufacturer='" + manufacturer + '\'' +
                    ", certificateNumber='" + certificateNumber + '\'' +
                    ", issuedDate='" + issuedDate + '\'' +
                    ", expiryDate='" + expiryDate + '\'' +
                    ", organization='" + organization + '\'' +
                    ", packageType='" + packageType + '\'' +
                    ", quantity=" + quantity +
                    ", price=" + price +
                    ", dosage='" + dosage + '\'' +
                    ", frequency='" + frequency + '\'' +
                    '}';
        }
    }
}