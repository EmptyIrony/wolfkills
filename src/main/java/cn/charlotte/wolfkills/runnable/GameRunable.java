package cn.charlotte.wolfkills.runnable;

import cn.charlotte.wolfkills.Main;
import cn.charlotte.wolfkills.data.Game;
import cn.charlotte.wolfkills.enums.GameStatus;

import java.util.Collections;

import static java.lang.Thread.sleep;

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
                sleep(1000);
            } catch (Exception e) {
            }
        }
        //开始游戏代码段
        Main.CQ.sendGroupMsg(game.getGroup(),"游戏即将开始！正在进行初始化...");
        Collections.shuffle(game.getPlayers());
        game.setAlivePlayers(game.getPlayers());
        game.setStatus(GameStatus.STARTING);
        Main.getGameManager().sendVocations(game);
        try{
            sleep(3*1000);
        }catch (Exception ignored){}

        Main.CQ.sendGroupMsg(game.getGroup(),"身份牌发放完毕！游戏开始！");

        while (!game.isEnd()){
            game.setNightNum(game.getNightNum()+1);
            Main.CQ.sendGroupMsg(game.getGroup(),"天黑了，狼人们都出来行动吧");

            game.setStatus(GameStatus.WOLFTALK);
            game.getPlayers().forEach((data)->{
                if (game.getWolfTeam().contains(data)){
                    StringBuilder message = new StringBuilder();
                    message.append("狼人你好，你的队友是\r\n");
                    game.getWolfTeam().forEach(playerData -> {
                        message.append(playerData.getNum()+"号 ");
                    });
                }
            });
            Main.getGameManager().start(game);
            Main.CQ.logInfo("[Debug]", "完成一次循环");

        }
        StringBuilder message = new StringBuilder();
        message.append("游戏结束！");
        if (game.getWinner().equalsIgnoreCase("wolf")) {
            message.append("狼人获胜！");
        } else {
            message.append("好人获胜！");
        }

        Main.CQ.sendGroupMsg(game.getGroup(), message.toString());
        Main.getMysql().settlement(game);
}
}
