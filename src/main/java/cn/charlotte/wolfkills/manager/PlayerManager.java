package cn.charlotte.wolfkills.manager;

import cn.charlotte.wolfkills.Main;
import cn.charlotte.wolfkills.data.Game;
import cn.charlotte.wolfkills.data.PlayerData;

public class PlayerManager {
    public void addPlayer(long qq, long group, Game game){
        if(game.getPlayers().size() >= 12){
            Main.CQ.sendGroupMsg(group,Main.CC.at(qq)+" ������Ϸʧ�ܣ�����������");
            return;
        }
        for (PlayerData data:game.getPlayers()){
            if(data.getQq() == qq){
                Main.CQ.sendGroupMsg(group,Main.CC.at(qq)+" ������Ϸʧ�ܣ����Ѿ��������ⳡ��Ϸ��");
                return;
            }
        }
        game.getPlayers().add(new PlayerData(qq));
        Main.CQ.sendGroupMsg(group,Main.CC.at(qq)+" ������Ϸ�ɹ���Ŀǰ����("+game.getPlayers().size()+"/12)");
    }

    public void removePlayer(long qq,long group,Game game){
        for (PlayerData data:game.getPlayers()){
            if(data.getQq() == qq){
                game.getPlayers().remove(data);
                Main.CQ.sendGroupMsg(group,Main.CC.at(qq)+" �뿪��Ϸ�ɹ���Ŀǰ����("+game.getPlayers().size()+"/12)");
                return;
            }
        }
        Main.CQ.sendGroupMsg(group,Main.CC.at(qq)+" �뿪��Ϸʧ�ܣ��������ڱ�����Ϸ�У�");
    }
}
