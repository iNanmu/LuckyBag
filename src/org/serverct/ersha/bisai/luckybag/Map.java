package org.serverct.ersha.bisai.luckybag;

import org.bukkit.command.CommandSender;

import java.util.HashMap;

/**
 * @author ersha
 * @date 2019/12/29
 */
public class Map {

    /**福袋冷却**/
    public static HashMap<String, String> BagCooling = new HashMap<>();

    /**福袋组**/
    public static HashMap<CommandSender, String> AttributeGroup = new HashMap<>();
}
