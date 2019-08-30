package cn.charlotte.wolfkills.runnable;

import cn.charlotte.wolfkills.Main;
import cn.charlotte.wolfkills.data.Game;
import cn.charlotte.wolfkills.enums.GameStatus;
import cn.charlotte.wolfkills.manager.GameManager;

import java.util.Collections;

public class GameRunable implements Runnable {

    private int timer;
    private Game game;

    public GameRunable(Game game){
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
                Main.CQ.sendGroupMsg(game.getGroup(),"游戏将在 " + timer + " 秒后开始！");
            }
            timer--;
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }
        //开始游戏代码段
        Main.CQ.sendGroupMsg(game.getGroup(),"游戏即将开始！");
        Collections.shuffle(game.getPlayers());
        game.setStatus(GameStatus.STARTING);
        GameManager.sendVocations(game);
    }
}
