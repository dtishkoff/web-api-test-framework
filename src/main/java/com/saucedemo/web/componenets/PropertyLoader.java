package com.saucedemo.web.componenets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class PropertyLoader {

    public static Properties loadDefaultAndCustomProps(List<String> propFilePathList) throws IOException {
        Properties testProps = new Properties();
        Iterator it = propFilePathList.iterator();

        while(it.hasNext()) {
            String propFilePath = (String)it.next();
            testProps.load(PropertyLoader.class.getResourceAsStream(propFilePath));
        }

        testProps.putAll(System.getProperties());
        return testProps;
    }
}
