package org.serverct.ersha.bisai.luckybag.database;

import org.serverct.ersha.bisai.luckybag.enumerate.ChangedType;

/**
 * @author ersha
 * @date 2020/1/3
 */
public interface Database {

    /**
     * 玩家数据变动
     * @param player 玩家
     * @param type 操作类型
     * @param value 值
     */
    void changedPlayerData(String player, ChangedType type, int value);

    /**
     * 获取玩家当前福气值
     * @param player 玩家
     * @return int
     */
    int getPlayerValue(String player);
}
