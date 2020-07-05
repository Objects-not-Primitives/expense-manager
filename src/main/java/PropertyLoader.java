import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {

    public static Properties loadProperties(String propertiesPath) throws IOException {
        Properties property = new Properties();
        InputStream input = new FileInputStream(propertiesPath);
        property.load(input);
        input.close();
        return property;
    }
}
