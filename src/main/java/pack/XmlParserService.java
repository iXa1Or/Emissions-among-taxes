
package pack;

import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

public class XmlParserService {

    public static Map<String, Item> parseFiles(String[] filePaths) throws Exception {
        Map<String, Item> items = new HashMap<>();
        for (String path : filePaths) {
            if (path.endsWith(".zip")) {
                items.putAll(parseZipFile(path));
            } else if (path.endsWith(".xml")) {
                items.putAll(parseXml(new FileInputStream(path)));
            }
        }
        return items;
    }
    public static Map<String, Item> parseZipFile(String zipFilePath) throws Exception {
        Map<String, Item> items = new HashMap<>();
        try (ZipFile zipFile = new ZipFile(zipFilePath)) {
            for (ZipEntry entry : Collections.list(zipFile.entries())) {
                if (entry.getName().endsWith(".xml") && !entry.getName().startsWith("__MACOSX")) {
                    try (InputStream inputStream = zipFile.getInputStream(entry)) {
                        items.putAll(parseXml(inputStream));
                    }
                }
            }
        }
        return items;
    }

    private static Map<String, Item> parseXml(InputStream inputStream) throws Exception {
        Map<String, Item> items = new HashMap<>();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputStream);
        doc.getDocumentElement().normalize();

        NodeList nodeList = doc.getElementsByTagName("Item");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                String inn = element.getElementsByTagName("INN").item(0).getTextContent();
                int revenue = Integer.parseInt(element.getElementsByTagName("Revenue").item(0).getTextContent());
                String taxName = element.getElementsByTagName("TaxName").item(0).getTextContent();
                int taxSum = Integer.parseInt(element.getElementsByTagName("TaxSum").item(0).getTextContent());

                // Если элемент уже существует
                items.computeIfPresent(inn, (key, existingItem) -> {
                    existingItem.addTaxName(taxName);
                    existingItem.addTaxSum(taxSum);
                    return existingItem;
                });

                // Если элемента еще нет, то создаем новый
                items.computeIfAbsent(inn, key -> {
                    Item item = new Item();
                    item.setINN(inn);
                    item.setRevenue(revenue);
                    item.addTaxName(taxName);
                    item.addTaxSum(taxSum);
                    return item;
                });
            }
        }
        return items;
    }
}