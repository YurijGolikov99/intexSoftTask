package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import static logger.CustomLog.error;
import static logger.CustomLog.info;

public class ConfigManager {

    private static final String PATH_TO_TESTDATA = "src/test/resources/testData/data.properties";

    public static String getProperty(String propertyKeyValue){
        String valueOfProperty = "";
        FileInputStream fis;
        Properties property = new Properties();
        try{
            fis = new FileInputStream(PATH_TO_TESTDATA);
            property.load(fis);

            valueOfProperty = property.getProperty(propertyKeyValue);

        } catch (IOException e) {
            error("Reading file error %s" + e.getMessage());
        }
        info("Getting property %s=%s", propertyKeyValue, valueOfProperty);
        return valueOfProperty;
    }
}
