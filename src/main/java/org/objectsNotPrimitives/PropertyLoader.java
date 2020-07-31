package org.objectsNotPrimitives;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {

    public static Properties load(String propertiesPath) {
        Properties property = new Properties();
        InputStream input = PropertyLoader.class.getClassLoader().getResourceAsStream(propertiesPath);
        try {
            property.load(input);
            return property;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("application.properties file not found");
            return null;
        }

    }
}
