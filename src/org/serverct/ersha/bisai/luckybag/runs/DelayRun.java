package org.serverct.ersha.bisai.luckybag.runs;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.serverct.ersha.bisai.luckybag.Main;
import org.serverct.ersha.bisai.luckybag.util.Reward;

/**
 * 延迟执行指令
 * @author ersha
 * @date 2019/12/29
 */
public class DelayRun extends BukkitRunnable {

    private Player player;
    private String command;

    public void delayRun(Player player, String command, int time){
        this.player = player;
        this.command = command;
        this.runTaskLater(Main.getInstance(), time*20);
    }

    @Override
    public void run() {
        if (player.isOnline()){
            new Reward(player).runCommand(command);
        }
    }
}
