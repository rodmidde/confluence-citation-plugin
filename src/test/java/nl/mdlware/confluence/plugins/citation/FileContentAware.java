package nl.mdlware.confluence.plugins.citation;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class FileContentAware {
    public static String readFileAsString(String filePath) {
        URL locationOfPageData = ClassLoader.getSystemResource(filePath);
        try {
            String pageDataContents = FileUtils.readFileToString(new File(locationOfPageData.toURI()), "UTF-8");
            return pageDataContents;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
