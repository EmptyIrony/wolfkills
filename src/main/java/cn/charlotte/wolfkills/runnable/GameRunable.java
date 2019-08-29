package cn.charlotte.wolfkills.runnable;

import cn.charlotte.wolfkills.Main;
import cn.charlotte.wolfkills.data.Game;

public class GameRunable implements Runnable{

    private int timer;
    private Game game;

    GameRunable(Game game){
        this.timer = 60;
        this.game = game;
    }


    @Override
    public void run() {
        while (timer > 0){
            if(game.getPlayers().size() < 7){
                Main.CQ.sendGroupMsg(game.getGroup(),"人数不足，停止计时！");
                return;
            }
            if(timer == 60 || timer == 30 || timer == 10 || timer == 5){
                Main.CQ.sendGroupMsg(game.getGroup(),"游戏还有 " + timer + " 开始！");
            }
            timer--;
        }
        //开始游戏代码段
        Main.CQ.sendGroupMsg(game.getGroup(),"游戏即将开始！");
    }
}
