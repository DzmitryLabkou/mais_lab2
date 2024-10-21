package org.example;

import org.example.Medicin.Medicin;
import org.example.Parsers.SAXMedicinParser;
import org.example.Parsers.DOMMedicinParser;
import org.example.Parsers.STAXMedicinParser;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String xmlFilePath = "src\\main\\resources\\medicins.xml";  // Путь к XML файлу

        // DOM парсинг
        DOMMedicinParser domParser = new DOMMedicinParser();
        List<Medicin> medicinsFromDOM = domParser.parse(xmlFilePath);
        System.out.println("Medicins parsed with DOM:");
        medicinsFromDOM.forEach(System.out::println);

        // SAX парсинг
        SAXMedicinParser saxParser = new SAXMedicinParser();
        List<Medicin> medicinsFromSAX = saxParser.parse(xmlFilePath);
        System.out.println("Medicins parsed with SAX:");
        medicinsFromSAX.forEach(System.out::println);

        // STAX парсинг
        STAXMedicinParser staxParser = new STAXMedicinParser();
        List<Medicin> medicinsFromSTAX = staxParser.parse(xmlFilePath);
        System.out.println("Medicins parsed with STAX:");
        medicinsFromSTAX.forEach(System.out::println);
    }
}