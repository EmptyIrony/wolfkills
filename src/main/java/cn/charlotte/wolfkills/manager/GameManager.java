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
    private List<Long> voted; //��ͶƱ����
    private Map<Long,Integer> wolfVote;
    private PlayerData wolfTalking;
    private PlayerData temp; //8֪��ȡʲô���ְ��Ҹ�һ�� �����˻�ɱ�����
    private boolean temp2; //�����Ƿ��������
    private boolean temp3; //Ů���Ƿ��������
    private PlayerData temp4; //Ů�׻�ɱ�����'
    private boolean temp5; //Ԥ�Լ��Ƿ��������
    private List<PlayerData> police;

    //�������
    public void sendVocations(Game game) {
        Main.CQ.setGroupWholeBan(game.getGroup(),true);
        //�ж���Ϸ����������������
        List<Vocation> vocations = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            vocations.add(Vocation.VILLAGER);
        }

        for (int i = 0; i < 2; i++) {
            vocations.add(Vocation.WOLF);
        }

        vocations.add(Vocation.PROPHET);
        vocations.add(Vocation.WITCH);
        //8������ʱ�������һ��δ�����Ľ�ɫ
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

        Main.CQ.sendGroupMsg(game.getGroup(),"���ڷ������,������Ϸ�� " + game.getPlayers().size() + " ����Ҳ���.");
        //Todo: ˵������ɫ���Լ�����ɫ
        for (int i = 0; i < game.getPlayers().size(); i++) {
            PlayerData data = game.getPlayers().get(i);
            data.setNum(i+1);
            data.setVocation(vocations.get(i));

            switch (data.getVocation()) {
                case VILLAGER:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "��ã���ӭ�μӱ�������ɱ\r\n�������ǡ�ƽ��\r\n����: ��Ĺ۲�����������ļ��ܣ��������˵Ļ��ԣ�����������Ӫ���ʤ����");
                    break;
                case WOLF:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "��ã���ӭ�μӱ�������ɱ\r\n�������ǡ����ˡ�\r\n����: ÿ���ܹ�����һ����ң����ܳ������е������������ʤ����˽���ҡ��Ա��������Ա�������ֱ�ӽ�����һ�����");
                    break;
                case PROPHET:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "��ã���ӭ�μӱ�������ɱ\r\n�������ǡ�Ԥ�Լҡ�\r\n����: ÿ���ܹ���֤һ������Ǻ��˻��ǻ��ˣ���ú������Σ��������Ϣ������˻�ʤ��");
                    break;
                case HUNTER:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "��ã���ӭ�μӱ�������ɱ\r\n�������ǡ����ˡ�\r\n����: ��������Դ��߳�������һ�����ŵ���ң���Ҳ����ѡ�񲻿�ǹ");
                    break;
                case BEAR:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "��ã���ӭ�μӱ�������ɱ\r\n�������ǡ��ܡ�\r\n����: ������������λ������λ������ʱ ������������û�л������ѳ����򲻻�����");
                    break;
                case WITCH:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "��ã���ӭ�μӱ�������ɱ\r\n�������ǡ�Ů�ס�\r\n����: ����һƿ��ҩ�Ͷ�ҩ����ҩ���Է�ֹһ�������ҹ����ȥ����ҩ���Զ���һ����ң��¶�������Ҳ����Է������ܻ��߱������ػ�");
                    break;
                case KNIGHT:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "��ã���ӭ�μӱ�������ɱ\r\n�������ǡ���ʿ��\r\n����: ������ڰ�������ʱ�̶���˵���� ID�����ɶ�������Ծ�������������Ϊ��������������ݣ����ǳ��֣�ֱ�ӽ�����һ����ڣ�����Ǻ��ˣ���������л��");
                    break;
                case DEFENDER:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "��ã���ӭ�μӱ�������ɱ\r\n�������ǡ�������\r\n����: �������ҹ���ػ�һ����ң�������Ҳ��ᱻ����ɱ�����㲻����������ͬʱ�ػ�һ�����");
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
            Main.CQ.sendPrivateMsg(playerData.getQq(),"�����ֵ��㷢���ˣ���ʱ15s��һ�仰");
            try {
                sleep(15*1000);
            } catch (InterruptedException ignored) {}
        });
        game.setStatus(GameStatus.WOLFSELECT);
    }

    public void sendWolfPrivateMessage(Game game,PlayerData sender,String msg){
        for (PlayerData wolf:game.getWolfTeam()){
            if(!wolf.equals(sender)){
                Main.CQ.sendPrivateMsg(wolf.getQq(),wolf.getNum() + "�����: " + msg);
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
                message.append(player.getNum()+". �����ˡ�"+Main.CQ.getGroupMemberInfoV2(game.getGroup(), player.getQq()).getNick() + (player.isDead() ? Main.CC.emoji(128128) : "") + "\r\n");
                continue;
            }

        if (game.getNightNum() == 1) {
            message.append(player.getNum() + ". ��ҹäɱ\r\n");
            continue;
        }

        message.append(player.getNum() + ". " + Main.CQ.getGroupMemberInfoV2(game.getGroup(), player.getQq()).getNick() + (player.isDead() ? Main.CC.emoji(128128) : "") + "\r\n");
    }
       message.append("��ֱ�ӻظ���Ž���ͶƱ 20s");

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
        //sleep��֪���᲻��Ӱ�쵽ͶƱ
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

    private PlayerData tempfunc(Game game){ //8֪��ȡʲô���ְ��Ҹ�һ��
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
