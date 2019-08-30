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
                Main.CQ.sendGroupMsg(game.getGroup(),"�������㣬ֹͣ��ʱ��");
                return;
            }
            if(timer == 60 || timer == 30 || timer == 10 || timer == 5){
                Main.CQ.sendGroupMsg(game.getGroup(),"��Ϸ���� " + timer + " ���ʼ��");
            }
            timer--;
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }
        //��ʼ��Ϸ�����
        Main.CQ.sendGroupMsg(game.getGroup(),"��Ϸ������ʼ��");
        Collections.shuffle(game.getPlayers());
        game.setStatus(GameStatus.STARTING);
        GameManager.sendVocations(game);
    }
}
