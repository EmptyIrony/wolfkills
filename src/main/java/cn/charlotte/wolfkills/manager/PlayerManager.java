package cn.charlotte.wolfkills.manager;

import cn.charlotte.wolfkills.Main;
import cn.charlotte.wolfkills.data.Game;
import cn.charlotte.wolfkills.data.PlayerData;

public class PlayerManager {
    public void addPlayer(long qq, long group, Game game){
        if(game.getPlayers().size() >= 12){
            Main.CQ.sendGroupMsg(group,Main.CC.at(qq)+" 加入游戏失败，人数已满！");
            return;
        }
        for (PlayerData data:game.getPlayers()){
            if(data.getQq() == qq){
                Main.CQ.sendGroupMsg(group,Main.CC.at(qq)+" 加入游戏失败，你已经加入了这场游戏！");
                return;
            }
        }
        game.getPlayers().add(new PlayerData(qq));
        Main.CQ.sendGroupMsg(group,Main.CC.at(qq)+" 加入游戏成功！目前人数("+game.getPlayers().size()+"/12)");
    }

    public void removePlayer(long qq,long group,Game game){
        for (PlayerData data:game.getPlayers()){
            if(data.getQq() == qq){
                game.getPlayers().remove(data);
                Main.CQ.sendGroupMsg(group,Main.CC.at(qq)+" 离开游戏成功！目前人数("+game.getPlayers().size()+"/12)");
                return;
            }
        }
        Main.CQ.sendGroupMsg(group,Main.CC.at(qq)+" 离开游戏失败，您并不在本场游戏中！");
    }
}
