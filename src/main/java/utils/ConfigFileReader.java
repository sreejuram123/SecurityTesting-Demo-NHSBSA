package utils;

import enums.ConfigProperties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {

    private static final Properties properties = new Properties();

    static {
        try (FileInputStream file = new FileInputStream("src/test/resources/config/config.properties")) {
            properties.load(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(ConfigProperties key) {
        return properties.getProperty(key.name().toLowerCase());
    }

    public static String getvalue(String key) {
        return properties.getProperty(key.toLowerCase());
    }


}
