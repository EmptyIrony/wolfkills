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
                case "!������Ϸ":
                    //��Ҫ�ж��Ƿ���players������������
                game.getPlayers().add(new PlayerData(fromQQ));
                Main.CQ.sendGroupMsg(fromGroup,Main.CC.at(fromQQ)+" ������Ϸ�ɹ���Ŀǰ����("+game.getPlayers().size()+"/12)");
                break;
                case "!�˳���Ϸ":

            }
        }


    }

}
