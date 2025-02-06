import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] args) {
        // Print current date and time with timezone
        printCurrentDateTime();

        // Validate command-line arguments
        String xmlPath = null;
        String xsdPath = null;
        for (int i = 0; i < args.length - 1; i++) {
            if ("-xml".equals(args[i])) {
                xmlPath = args[i + 1];
            } else if ("-xsd".equals(args[i])) {
                xsdPath = args[i + 1];
            }
        }

        if (xmlPath == null || xsdPath == null) {
            System.out.println("Usage: java -jar MyApplication.jar -xml <xml_path> -xsd <xsd_path>");
            System.exit(1);
        }

        // Validate XML file against XSD
        validateXML(xmlPath, xsdPath);
    }

    private static void printCurrentDateTime() {
        // Get current date and time
        ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        System.out.println("Current Date and Time: " + now.format(formatter));
    }

    private static void validateXML(String xmlPath, String xsdPath) {
        try {
            File xmlFile = new File(xmlPath);
            File xsdFile = new File(xsdPath);

            if (!xmlFile.exists() || !xsdFile.exists()) {
                System.out.println("XML or XSD file not found. Please check the file paths.");
                return;
            }

            // Load schema
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(xsdFile);
            Validator validator = schema.newValidator();

            // Validate XML
            validator.validate(new StreamSource(xmlFile));
            System.out.println("XML is valid.");
        } catch (Exception e) {
            System.out.println("XML validation failed: " + e.getMessage());
        }
    }
}
