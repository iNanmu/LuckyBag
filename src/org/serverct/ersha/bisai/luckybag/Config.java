package org.serverct.ersha.bisai.luckybag;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author ersha
 * @date 2019/12/1
 */
public class Config {

    private static FileConfiguration config = Main.getInstance().getConfig();

    public static Object getSetting(String path){
        return config.get(path);
    }

    public static String getMessage(String yaml){
        return config.getString("Message."+yaml).replace("&", "§");
    }
}
