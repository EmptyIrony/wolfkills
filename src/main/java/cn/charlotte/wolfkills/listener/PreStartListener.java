package cn.charlotte.wolfkills.listener;

import cn.charlotte.wolfkills.Main;
import cn.charlotte.wolfkills.data.Game;
import cn.charlotte.wolfkills.data.PlayerData;
import cn.charlotte.wolfkills.enums.GameStatus;

public class PreStartListener {
    public PreStartListener(){

    }

    void onMessage(int subType, int msgId, long fromGroup, long fromQQ, String fromAnonymous, String msg, int font){
        Game.gameMap.putIfAbsent(fromGroup,new Game());

        Game game = Game.gameMap.get(fromGroup);




        if (game.getStatus()== GameStatus.WAITING){
            switch (msg){
                case "!加入游戏":
                    //需要判断是否在players里，否则不允许加入
                game.getPlayers().add(new PlayerData(fromQQ));
                Main.CQ.sendGroupMsg(fromGroup,Main.CC.at(fromQQ)+" 加入游戏成功！目前人数("+game.getPlayers().size()+"/12)");
                break;
                case "!退出游戏":

            }
        }


    }

}
