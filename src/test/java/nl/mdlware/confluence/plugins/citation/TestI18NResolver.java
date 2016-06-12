package nl.mdlware.confluence.plugins.citation;

import com.atlassian.sal.api.message.I18nResolver;
import com.atlassian.sal.api.message.Message;
import com.atlassian.sal.api.message.MessageCollection;

import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public class TestI18NResolver implements I18nResolver {
    private Properties properties;

    public TestI18NResolver()
    {
        properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemResourceAsStream("i18n/citation.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getRawText(String s) {
        return null;
    }

    public String getRawText(Locale locale, String s) {
        return null;
    }

    public String getText(String s, Serializable... serializables) {
        return null;
    }

    public String getText(Locale locale, String s, Serializable... serializables) {
        return null;
    }

    public String getText(String s) {
        return properties.getProperty(s);
    }

    public String getText(Locale locale, String s) {
        return null;
    }

    public String getText(Message message) {
        return null;
    }

    public String getText(Locale locale, Message message) {
        return null;
    }

    public Message createMessage(String s, Serializable... serializables) {
        return null;
    }

    public MessageCollection createMessageCollection() {
        return null;
    }

    public Map<String, String> getAllTranslationsForPrefix(String s) {
        return null;
    }

    public Map<String, String> getAllTranslationsForPrefix(String s, Locale locale) {
        return null;
    }
}
