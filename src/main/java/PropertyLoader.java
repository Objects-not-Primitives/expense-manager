import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {

    public static Properties load(String propertiesPath) throws IOException {
        Properties property = new Properties();
        InputStream input = PropertyLoader.class.getClassLoader().getResourceAsStream(propertiesPath);
        property.load(input);
        return property;
    }
}
