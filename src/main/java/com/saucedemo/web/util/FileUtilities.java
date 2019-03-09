package com.saucedemo.web.util;

import java.net.URL;

/**
 * FileUtilities provides convenience methods for loading files and their contents within the project.
 */
public class FileUtilities {

    /**
     * Load a resource available within the classpath as a URL.
     *
     * @param relativeFilePath the relative path to the file in the classpath from the test/resources directory, e.g. <em>defaultTest.properties</em>
     * @return the URL reference for the specified path, note that this does not guarantee the path is valid
     */
    public static URL getResource(String relativeFilePath) {
        return FileUtilities.class.getClassLoader().getResource(relativeFilePath);
    }
}

