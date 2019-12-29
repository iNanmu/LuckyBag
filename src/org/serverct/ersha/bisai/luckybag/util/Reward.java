package org.serverct.ersha.bisai.luckybag.util;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.serverct.ersha.bisai.luckybag.runs.DelayRun;

import java.util.List;
import java.util.Random;


/**
 * @author ersha
 * @date 2019/12/29
 */
public class Reward {

    private Player player;
    private List<String> rewardList;

    public Reward(Player player){
        this.player = player;
    }
    public Reward(Player player, List<String> rewardList){
        this.player = player;
        this.rewardList = rewardList;
        this.run();
    }

    private void run(){
        for (String s : rewardList){
            String[] args = s.split("#");
            runReward(args[0], args[1].replace("%player%", player.getName()).replace("&", "§"));
        }
    }

    /**
     * 执行奖励
     */
    private void runReward(String type, String text){
        switch (type){
            case "[MESSAGE]":
                player.sendMessage(text);
                break;
            case "[COMMAND]":
                runCommand(text);
                break;
            case "[TITLE]":
                runTitle(text);
                break;
            case "[SOUND]":
                runSound(text);
                break;
            case "[CHANCE]":
                runChance(text);
                break;
            case "[ITEMS]":
                runItems(text);
                break;
            case "[DELAY]":
                String[] args = text.split(",");
                String a = text.substring(args[0].length()+1).trim();
                new DelayRun().delayRun(player, a, Integer.parseInt(args[0]));
                break;
            default:
                break;
        }
    }

    /**
     * 让玩家以OP执行指令
     */
    public void runCommand(String commands){
        String[] args1 = commands.split(",");
        for (String arg : args1) {
            String values = "123456asd";
            Integer value = 0;
            if (arg.contains("<r:")){
                values = arg.split("<r:")[1].split(">")[0];
                value = Util.replaceInteger("<r:"+values+">");
            }
            command(arg.replace("<r:"+values+">", value+""));
        }
    }
    private void command(String commands){
        boolean getOp = player.isOp();
        try {
            player.setOp(true);
            Bukkit.dispatchCommand(player, commands);
        }catch (Exception ignored){
        }finally {
            player.setOp(getOp);
        }
    }

    /**
     * 发送标题
     */
    private void runTitle(String title){
        String[] args = title.split(",");
        player.sendTitle(args[0], args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
    }

    /**
     * 发送音效
     */
    private void runSound(String sound){
        String[] args = sound.split(",");
        player.playSound(player.getLocation(), Sound.valueOf(args[0]), Float.parseFloat(args[1]), Float.parseFloat(args[2]));
    }

    /**
     * 概率触发奖励
     */
    private void runChance(String object){
        String[] args = object.split(",");
        if (Integer.parseInt(args[0]) >= new Random().nextInt(100)){
            for (int i = 1;i<args.length;i++) {
                String[] args2 = args[i].split(",");
                String[] args3 = args2[0].replace("$", "#").split("#");
                runReward(args3[0], args3[1].replace("&", "§"));
            }
        }
    }

    /**
     * 给予物品
     */
    private void runItems(String project){
        String[] args = project.split(",");
        ItemStack itemStack = Items.items(args[0]);
        int amount = Util.replaceInteger(args[1]);
        itemStack.setAmount(amount);
        player.sendMessage(args[2].replace("%amount%", amount+""));
        if (Util.playerInventory(player)){
            player.getInventory().addItem(itemStack);
            return;
        }
        player.getWorld().dropItem(player.getLocation(), itemStack);
    }
}
