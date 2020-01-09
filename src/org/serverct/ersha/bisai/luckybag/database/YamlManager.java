package org.serverct.ersha.bisai.luckybag.database;

import org.bukkit.configuration.file.YamlConfiguration;
import org.serverct.ersha.bisai.luckybag.Main;
import org.serverct.ersha.bisai.luckybag.enumerate.ChangedType;
import org.serverct.ersha.bisai.luckybag.extension.AttributePlus;

import java.io.File;

/**
 * @author ersha
 * @date 2020/1/3
 */
public class YamlManager implements Database {

    private static File file = new File(Main.getInstance().getDataFolder()+"/database.yml");
    private static YamlConfiguration yaml;

    YamlManager(){
        yaml = YamlConfiguration.loadConfiguration(file);
    }

    private void save(){
        try {
            yaml.save(file);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void changedPlayerData(String player, ChangedType type, int value) {
        int newValue = 0;
        switch (type) {
            case ADD:
                newValue = getPlayerValue(player) + value;
                break;
            case TAKE:
                newValue = getPlayerValue(player) - value;
                break;
            case SET:
                newValue = value;
                break;
            default:
                break;
        }
        yaml.set("database."+player, newValue);
        this.save();
        AttributePlus.loadAttribute(player);
    }

    @Override
    public int getPlayerValue(String player) {
        return yaml.getInt("database."+player);
    }
}
