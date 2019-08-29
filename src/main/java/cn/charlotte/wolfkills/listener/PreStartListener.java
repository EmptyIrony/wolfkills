package cn.charlotte.wolfkills.listener;

import cn.charlotte.wolfkills.Main;
import cn.charlotte.wolfkills.data.Game;
import cn.charlotte.wolfkills.data.PlayerData;
import cn.charlotte.wolfkills.enums.GameStatus;

public class PreStartListener {
    public PreStartListener(){

    }

    void onMessage(int subType, int msgId, long fromGroup, long fromQQ, String fromAnonymous, String msg, int font){
        Game game = new Game();
        if(!Game.gameMap.containsKey(fromGroup)){
            Game.gameMap.put(fromGroup,game);
        }

        if (game.getStatus()== GameStatus.WAITING){
            switch (msg){
                case "!������Ϸ":
                    Main.getPlayerManager().addPlayer(fromQQ,fromGroup,game);
                    break;
                case "!�˳���Ϸ":
                    Main.getPlayerManager().removePlayer(fromQQ,fromGroup,game);
                    break;
            }
        }


    }

}
