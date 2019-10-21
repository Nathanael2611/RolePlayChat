package fr.nathanael2611.roleplaychat.client.config;


import java.io.*;
import java.nio.charset.Charset;
import java.util.Properties;

public class ChatConfig {

    private ChatType hrp;
    private ChatType normal;
    private ChatType shout;
    private ChatType whisp;
    private ChatType action;

    private ChatType[] chatTypes;

    private final File configFile;

    private Properties properties = new Properties();

    private FileInputStream input;

    public ChatConfig(File configFile) throws Exception
    {
        this.configFile = configFile;
        reload();
        chatTypes = new ChatType[] {
                hrp, normal, shout, whisp, action
        };
    }

    public void reload() throws Exception
    {
        input = new FileInputStream(configFile);
        properties.load(
                new InputStreamReader(input, Charset.forName("UTF-8"))
        );

        createPropertyIfNull("prefix.hrp", "(");
        createPropertyIfNull("prefix.normal", "");
        createPropertyIfNull("prefix.shout", "!");
        createPropertyIfNull("prefix.whisp", "$");
        createPropertyIfNull("prefix.action", "*");

        createPropertyIfNull("distance.hrp", "0");
        createPropertyIfNull("distance.normal", "15");
        createPropertyIfNull("distance.shout", "30");
        createPropertyIfNull("distance.whisp", "2");
        createPropertyIfNull("distance.action", "15");

        createPropertyIfNull("format.hrp", "[HRP] {player} ({rpname}) : {message}");
        createPropertyIfNull("format.normal", "[RP] {rpname} : {message}");
        createPropertyIfNull("format.shout", "[RP-SHOUT] {rpname} : {message}");
        createPropertyIfNull("format.whisp", "[RP-WHISP] {rpname} : {message}");
        createPropertyIfNull("format.action", "§o{rpname} {message}");

        hrp = new ChatType(
                properties.getProperty("prefix.hrp"),
                Integer.parseInt(properties.getProperty("distance.hrp")),
                properties.getProperty("format.hrp")
        );
        normal = new ChatType(
                properties.getProperty("prefix.normal"),
                Integer.parseInt(properties.getProperty("distance.normal")),
                properties.getProperty("format.normal")
        );
        shout = new ChatType(
                properties.getProperty("prefix.shout"),
                Integer.parseInt(properties.getProperty("distance.shout")),
                properties.getProperty("format.shout")
        );
        whisp = new ChatType(
                properties.getProperty("prefix.whisp"),
                Integer.parseInt(properties.getProperty("distance.whisp")),
                properties.getProperty("format.whisp")
        );
        action = new ChatType(
                properties.getProperty("prefix.action"),
                Integer.parseInt(properties.getProperty("distance.action")),
                properties.getProperty("format.action")
        );

        properties.store(new OutputStreamWriter(new FileOutputStream(configFile), "UTF-8"), "Config file");

        input.close();
    }

    public void createPropertyIfNull(String key, String value) {
        if(properties.getProperty(key) == null){
            properties.setProperty(key, value);
        }
    }

    public ChatType getChatType(EnumChatTypes type){
        switch (type){
            case HRP: return hrp;
            case SHOUT: return shout;
            case WISPH: return whisp;
            case ACTION: return action;
            case NORMAL: return normal;
        }
        return normal;
    }

    public ChatType[] getChatTypes() {
        return chatTypes;
    }
}
