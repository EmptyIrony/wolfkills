package cn.charlotte.wolfkills.manager;

import cn.charlotte.wolfkills.Main;
import cn.charlotte.wolfkills.data.Game;
import cn.charlotte.wolfkills.data.PlayerData;
import cn.charlotte.wolfkills.enums.GameStatus;
import cn.charlotte.wolfkills.enums.Vocation;
import sun.rmi.runtime.Log;

import java.util.*;

import static java.lang.Thread.sleep;

public class GameManager {
    private List<Long> voted; //已投票狼人
    private Map<Long,Integer> wolfVote;
    private PlayerData wolfTalking;
    private PlayerData temp; //8知道取什么名字帮我改一下 被狼人击杀的玩家
    private boolean temp2; //守卫是否操作过了
    private boolean temp3; //女巫是否操作过了
    private PlayerData temp4; //女巫击杀的玩家'
    private boolean temp5; //预言家是否操作过了
    private List<PlayerData> police;

    //发送身份
    public void sendVocations(Game game) {
        Main.CQ.setGroupWholeBan(game.getGroup(),true);
        //判断游戏人数以填充身份数量
        List<Vocation> vocations = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            vocations.add(Vocation.VILLAGER);
        }

        for (int i = 0; i < 2; i++) {
            vocations.add(Vocation.WOLF);
        }

        vocations.add(Vocation.PROPHET);
        vocations.add(Vocation.WITCH);
        //8人以上时随机增加一名未被填充的角色
        if  (game.getPlayers().size() > 7) {
            List<Vocation> vocationadd = new ArrayList<>();
            List<Vocation> needAdd = new ArrayList<>();
            vocationadd.add(Vocation.HUNTER);
            vocationadd.add(Vocation.KNIGHT);
            vocationadd.add(Vocation.BEAR);
            vocationadd.add(Vocation.DEFENDER);
            for (Vocation vocation : vocationadd) {
                if (!vocations.contains(vocation)){
                    needAdd.add(vocation);
                }
            }

            vocations.add(needAdd.get(new Random().nextInt(needAdd.size())));
        }
        if (game.getPlayers().size() > 8) {
            vocations.add(Vocation.WOLF);
        }
        if (game.getPlayers().size() > 9) {
            vocations.add(Vocation.VILLAGER);
        }
        if  (game.getPlayers().size() > 10) {
            List<Vocation> vocationadd = new ArrayList<>();
            List<Vocation> needAdd = new ArrayList<>();
            vocationadd.add(Vocation.HUNTER);
            vocationadd.add(Vocation.KNIGHT);
            vocationadd.add(Vocation.BEAR);
            vocationadd.add(Vocation.DEFENDER);
            for (Vocation vocation : vocationadd) {
                if (!vocations.contains(vocation)){
                    needAdd.add(vocation);
                }
            }

            vocations.add(needAdd.get(new Random().nextInt(needAdd.size())));
        }
        if (game.getPlayers().size() > 11) {
            vocations.add(Vocation.WOLF);
        }
        Collections.shuffle(vocations);

        Main.CQ.sendGroupMsg(game.getGroup(),"正在发放身份,本场游戏共 " + game.getPlayers().size() + " 名玩家参与.");
        //Todo: 说明各角色数以及发角色
        for (int i = 0; i < game.getPlayers().size(); i++) {
            PlayerData data = game.getPlayers().get(i);
            data.setNum(i+1);
            data.setVocation(vocations.get(i));

            switch (data.getVocation()) {
                case VILLAGER:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "你好，欢迎参加本场狼人杀\r\n你的身份是【平民】\r\n技能: 你的观察能力就是你的技能，打破狼人的谎言，帮助好人阵营获得胜利！");
                    break;
                case WOLF:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "你好，欢迎参加本场狼人杀\r\n你的身份是【狼人】\r\n技能: 每晚能够击败一名玩家，击败场上所有的神或者民则获得胜利！私聊我【自爆】即可自爆而亡，直接进入下一轮天黑");
                    break;
                case PROPHET:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "你好，欢迎参加本场狼人杀\r\n你的身份是【预言家】\r\n技能: 每晚能够验证一名玩家是好人还是坏人，获得好人信任，用你的信息带领好人获胜！");
                    break;
                case HUNTER:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "你好，欢迎参加本场狼人杀\r\n你的身份是【猎人】\r\n技能: 你死后可以带走场上任意一名活着的玩家，你也可以选择不开枪");
                    break;
                case BEAR:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "你好，欢迎参加本场狼人杀\r\n你的身份是【熊】\r\n技能: 早上如果你的上位或者下位有狼人时 你会咆哮，如果没有或者你已出局则不会咆哮");
                    break;
                case WITCH:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "你好，欢迎参加本场狼人杀\r\n你的身份是【女巫】\r\n技能: 你有一瓶解药和毒药，解药可以防止一名玩家在夜里死去，毒药可以毒死一名玩家，呗毒死的玩家不可以发动技能或者被守卫守护");
                    break;
                case KNIGHT:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "你好，欢迎参加本场狼人杀\r\n你的身份是【骑士】\r\n技能: 你可以在白天任意时刻对我说【刺 ID】即可对他发起对决，如果该名玩家为狼人则你亮明身份，该狼出局，直接进入下一轮天黑，如果是好人，则你以死谢罪");
                    break;
                case DEFENDER:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "你好，欢迎参加本场狼人杀\r\n你的身份是【守卫】\r\n技能: 你可以在夜里守护一名玩家，该名玩家不会被狼人杀死，你不可以在两晚同时守护一名玩家");
                    break;
                case WOLFKING:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "NMSL");
            }
            try{
                sleep(3*1000);
            }catch (Exception ignored){}
        }
        game.getPlayers().forEach(playerData -> {
            if (playerData.getVocation()==Vocation.WOLF){
                game.getWolfTeam().add(playerData);
            }else {
                game.getHumanTeam().add(playerData);
            }
        });
    }

    public void wolfPrivate(Game game){
        game.getWolfTeam().forEach(playerData -> {
            wolfTalking = playerData;
            Main.CQ.sendPrivateMsg(playerData.getQq(),"现在轮到你发言了，限时15s，一句话");
            try {
                sleep(15*1000);
            } catch (InterruptedException ignored) {}
        });
        game.setStatus(GameStatus.WOLFSELECT);
    }

    public void sendWolfPrivateMessage(Game game,PlayerData sender,String msg){
        for (PlayerData wolf:game.getWolfTeam()){
            if(!wolf.equals(sender)){
                Main.CQ.sendPrivateMsg(wolf.getQq(),wolf.getNum() + "号玩家: " + msg);
            }
        }
    }

    public void wolfVoting(Game game) {
        wolfVote = new HashMap<>();
        voted = new ArrayList<>();
        StringBuilder message = new StringBuilder();
        message.append("\r\n");
        for (PlayerData player : game.getPlayers()) {
            if (player.getVocation() == Vocation.WOLF) {
                message.append(player.getNum()+". 【狼人】"+Main.CQ.getGroupMemberInfoV2(game.getGroup(), player.getQq()).getNick() + (player.isDead() ? Main.CC.emoji(128128) : "") + "\r\n");
                continue;
            }

        if (game.getNightNum() == 1) {
            message.append(player.getNum() + ". 首夜盲杀\r\n");
            continue;
        }

        message.append(player.getNum() + ". " + Main.CQ.getGroupMemberInfoV2(game.getGroup(), player.getQq()).getNick() + (player.isDead() ? Main.CC.emoji(128128) : "") + "\r\n");
    }
       message.append("请直接回复序号进行投票 20s");

        game.getWolfTeam().forEach(playerData -> {
            Main.CQ.sendPrivateMsg(playerData.getQq(),message.toString());
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        try {
            sleep(20*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //sleep不知道会不会影响到投票
        temp = tempfunc(game);
        game.setStatus(GameStatus.DEFENDER);
    }

    public void defendChoosing(Game game){
        StringBuilder message = new StringBuilder();
        for (PlayerData player : game.getPlayers()) {
            if (player.getVocation()==Vocation.DEFENDER){
                Main.CQ.sendPrivateMsg(player.getQq(),message.toString());
            }
        }

    }

    private PlayerData tempfunc(Game game){ //8知道取什么名字帮我改一下
        PlayerData temp = null;
        for (PlayerData playerData:game.getPlayers()){
            if(wolfVote.containsKey(playerData.getQq())){
                if(temp == null){
                    temp = playerData;
                } else {
                    if(wolfVote.get(playerData.getQq()) > wolfVote.get(temp.getQq())){
                        temp = playerData;
                    }
                }
            }
        }
        return temp;
    }

    public PlayerData getWolfTalking() {
        return wolfTalking;
    }
    public Map<Long, Integer> getWolfVote() {
        return wolfVote;
    }

    public List<Long> getVoted() {
        return voted;
    }


    public PlayerData getTemp() {
        return temp;
    }

    public boolean getTemp2() {
        return temp2;
    }

    public void setTemp2(boolean temp2) {
        this.temp2 = temp2;
    }

    public boolean getTemp3() {
        return temp3;
    }

    public void setTemp3(boolean temp3) {
        this.temp3 = temp3;
    }

    public void setTemp(PlayerData temp) {
        this.temp = temp;
    }

    public PlayerData getTemp4() {
        return temp4;
    }

    public void setTemp4(PlayerData temp4) {
        this.temp4 = temp4;
    }

    public boolean getTemp5() {
        return temp5;
    }

    public void setTemp5(boolean temp5) {
        this.temp5 = temp5;
    }

    public List<PlayerData> getPolice() {
        return police;
    }
}
