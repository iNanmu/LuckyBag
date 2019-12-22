package org.serverct.ersha.bisai.luckybag;

/**
 * @author ersha
 * @date 2019/12/1
 */
public class Config {

    public static String getMessage(String yaml){
        return Main.getInstance().getConfig().getString("Message."+yaml).replace("&", "§");
    }
}
