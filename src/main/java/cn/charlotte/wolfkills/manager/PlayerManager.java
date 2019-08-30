package cn.charlotte.wolfkills.manager;

import cn.charlotte.wolfkills.Main;
import cn.charlotte.wolfkills.data.Game;
import cn.charlotte.wolfkills.data.PlayerData;
import cn.charlotte.wolfkills.runnable.GameRunable;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager {
    private Map<Long, GameRunable> runableMap = new HashMap<>();

    public void addPlayer(long qq, Game game) {
        if(game.getPlayers().size() >= 12){
            Main.CQ.sendGroupMsg(game.getGroup(), Main.CC.at(qq) + " 加入游戏失败，人数已满！");
            return;
        }
        for (PlayerData data:game.getPlayers()){
            if(data.getQq() == qq){
                Main.CQ.sendGroupMsg(game.getGroup(), Main.CC.at(qq) + " 加入游戏失败，你已经加入了这场游戏！");
                return;
            }
        }
        game.getPlayers().add(new PlayerData(qq));
        Main.CQ.sendGroupMsg(game.getGroup(), Main.CC.at(qq) + " 加入游戏成功！(" + game.getPlayers().size() + "/12)");
        if (game.getPlayers().size() == 7) {
            if (runableMap.get(game.getGroup()) == null) {
                runableMap.put(game.getGroup(), new GameRunable(game));
                Thread thread = new Thread(runableMap.get(game.getGroup()));
                thread.start();
            }
        }
    }

    public void removePlayer(long qq, Game game) {
        for (PlayerData data:game.getPlayers()){
            if(data.getQq() == qq){
                game.getPlayers().remove(data);
                Main.CQ.sendGroupMsg(game.getGroup(), Main.CC.at(qq) + " 离开游戏成功！(" + game.getPlayers().size() + "/12)");
                runableMap.remove(game.getGroup());
                return;
            }
        }
        Main.CQ.sendGroupMsg(game.getGroup(), Main.CC.at(qq) + " 离开游戏失败，您并不在本场游戏中！");
    }
}
