package org.serverct.ersha.bisai.luckybag.runs;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.serverct.ersha.bisai.luckybag.Config;
import org.serverct.ersha.bisai.luckybag.Main;
import org.serverct.ersha.bisai.luckybag.Map;

/**
 * @author ersha
 * @date 2019/12/29
 */
public class CoolingRun extends BukkitRunnable {

    private String name;
    private String bag;

    public void coolingRun(String name, String bag, Integer time){
        this.name = name;
        this.bag = bag;
        this.runTaskLater(Main.getInstance(), time*20);
    }

    @Override
    public void run() {
        Map.BagCooling.remove(this.name + ":" + this.bag);
        String bagName = Main.getInstance().getConfig().getString("Items.List." + this.bag + ".Name");
        if (Bukkit.getPlayer(name).isOnline()) {
            Bukkit.getPlayer(name).sendMessage(Config.getMessage("bagCoolingEnd").replace("%bag%",bagName.replace("&","§")));
        }
    }
}
