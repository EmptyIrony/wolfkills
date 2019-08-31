package cn.charlotte.wolfkills.listener;

import cn.charlotte.wolfkills.Main;
import cn.charlotte.wolfkills.data.Game;
import cn.charlotte.wolfkills.enums.GameStatus;

public class PreStartListener {
    public PreStartListener(){

    }

    public static void onMessage(int subType, int msgId, long fromGroup, long fromQQ, String fromAnonymous, String msg, int font){
        Game.gameMap.putIfAbsent(fromGroup, new Game(fromGroup));

        if (Game.gameMap.get(fromGroup).getStatus() == GameStatus.WAITING) {
            if (fromGroup == 561991434) {
                switch (msg) {
                case "!加入游戏":
                    Main.getPlayerManager().addPlayer(fromQQ, Game.gameMap.get(fromGroup));
                    break;
                case "!退出游戏":
                    Main.getPlayerManager().removePlayer(fromQQ, Game.gameMap.get(fromGroup));
                    break;

                }
            }
        }


    }

}
