package cn.charlotte.wolfkills.manager;

import cn.charlotte.wolfkills.Main;
import cn.charlotte.wolfkills.data.Game;
import cn.charlotte.wolfkills.data.PlayerData;
import cn.charlotte.wolfkills.enums.GameStatus;
import cn.charlotte.wolfkills.enums.Vocation;
import cn.charlotte.wolfkills.runnable.SpreadStatusRunnable;
import cn.charlotte.wolfkills.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

import static java.lang.Thread.sleep;

@Getter
public class GameManager implements Runnable {
    private PlayerData wolfTalking;
    @Setter
    private Game game;
    @Setter
    private PlayerData saying;
    private List<PlayerData> said = new ArrayList<>();
    @Setter
    private boolean sayEnd;
    @Setter
    private boolean forceStop;

    private List<Long> voted = new ArrayList<>(); //��ͶƱ����
    private Map<Long, Integer> wolfVote = new HashMap<>();
    private PlayerData wolfKilled; //8֪��ȡʲô���ְ��Ҹ�һ�� �����˻�ɱ�����
    private boolean defenderUsed; //�����Ƿ��������
    private boolean whichUsed; //Ů���Ƿ��������
    private PlayerData whichKilled; //Ů�׻�ɱ�����'
    private boolean temp5; //Ԥ�Լ��Ƿ��������
    private boolean temp6;//���˼����Ƿ��ù�
    private List<PlayerData> police = new ArrayList<>();
    private PlayerData voteByeBye;
    private boolean end;

    @Override
    public void run() {
        start(this.game);
    }

    //�������
    public void sendVocations(Game game) {
        Main.CQ.setGroupWholeBan(game.getGroup(), true);
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
        if (game.getPlayers().size() > 7) {
            List<Vocation> vocationadd = new ArrayList<>();
            List<Vocation> needAdd = new ArrayList<>();
            vocationadd.add(Vocation.HUNTER);
            vocationadd.add(Vocation.KNIGHT);
            vocationadd.add(Vocation.BEAR);
            vocationadd.add(Vocation.DEFENDER);
            for (Vocation vocation : vocationadd) {
                if (!vocations.contains(vocation)) {
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
        if (game.getPlayers().size() > 10) {
            List<Vocation> vocationadd = new ArrayList<>();
            List<Vocation> needAdd = new ArrayList<>();
            vocationadd.add(Vocation.HUNTER);
            vocationadd.add(Vocation.KNIGHT);
            vocationadd.add(Vocation.BEAR);
            vocationadd.add(Vocation.DEFENDER);
            for (Vocation vocation : vocationadd) {
                if (!vocations.contains(vocation)) {
                    needAdd.add(vocation);
                }
            }

            vocations.add(needAdd.get(new Random().nextInt(needAdd.size())));
        }

        if (game.getPlayers().size() > 11) {
            vocations.add(Vocation.WOLF);
        }

        Collections.shuffle(vocations);

        //Main.getInstance().getExecutorPool().execute(new SpreadStatusRunnable(game, vocations));
        new SpreadStatusRunnable(game, vocations).run();
    }

    public void start(Game game) {
        if (game.getStatus() != GameStatus.WOLFTALK) {
            return;
        }

        game.getWolfTeam().values().forEach(playerData -> {
            wolfTalking = playerData;
            Main.CQ.sendPrivateMsg(playerData.getQq(), "�����ֵ��㷢���ˣ���ʱ15s��һ�仰");
            try {
                sleep(15 * 1000);
            } catch (InterruptedException ignored) {
            }
        });
        game.setStatus(GameStatus.WOLFSELECT);
        wolfVoting(game);
    }

    private void wolfVoting(Game game) {
        if (game.getStatus() != GameStatus.WOLFSELECT) {
            return;
        }
        for (PlayerData data : game.getPlayers().values()) {
            wolfVote.put(data.getQq(), 0);
        }


        StringBuilder message = new StringBuilder();
        message.append("\r\n");
        for (PlayerData player : game.getPlayers().values()) {
            if (player.getVocation() == Vocation.WOLF) {
                message.append(player.getNum()).append(". �����ˡ�").append(Main.CQ.getGroupMemberInfoV2(game.getGroup(), player.getQq()).getNick()).append(player.isDead() ? Main.CC.emoji(128128) : "").append("\r\n");
                continue;
            }

            if (game.getNightNum() == 1) {
                message.append(player.getNum()).append(". ��ҹäɱ\r\n");
                continue;
            }

            message.append(player.getNum()).append(". ").append(Main.CQ.getGroupMemberInfoV2(game.getGroup(), player.getQq()).getNick()).append(player.isDead() ? Main.CC.emoji(128128) : "").append("\r\n");
        }
        message.append("��ֱ�ӻظ���Ž���ͶƱ 20s");

        game.getWolfTeam().values().forEach(playerData -> {
            if (!playerData.isDead()) {
                Main.CQ.sendPrivateMsg(playerData.getQq(), message.toString());
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            sleep(20 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        wolfKilled = voteByeByePlayer(game);

        game.setStatus(GameStatus.DEFENDER);
        defendChoosing(game);
        voted.clear();
    }

    private void defendChoosing(Game game) {
        if (game.getStatus() != GameStatus.DEFENDER) {
            return;
        }
        Main.CQ.sendGroupMsg(game.getGroup(), "��������ѡ���ػ�Ŀ��...");

        StringBuilder message = new StringBuilder();
        for (PlayerData player : game.getPlayers().values()) {
            message.append(player.getNum()).append(". ").append(Main.CQ.getGroupMemberInfoV2(game.getGroup(), player.getQq()).getNick()).append(player.isDead() ? Main.CC.emoji(128128) : "").append("\r\n");
        }
        message.append("�뷢����Ҫ�ػ����˵���ţ���ע���㲻�������������ػ�ͬһ�����");

        for (PlayerData player : game.getPlayers().values()) {
            if (player.getVocation() == Vocation.DEFENDER && !player.isDead()) {
                Main.CQ.sendPrivateMsg(player.getQq(), message.toString());
                break;
            }
        }
        try {
            sleep(20 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        game.setStatus(GameStatus.WITCH);
        witchChoosing(game);
    }

    private void witchChoosing(Game game) {
        if (game.getStatus() != GameStatus.WITCH) {
            return;
        }
        Main.CQ.sendGroupMsg(game.getGroup(), "Ů�������ж�...");

        StringBuilder message = new StringBuilder();
        for (PlayerData player : game.getPlayers().values()) {
            if (game.getNightNum() == 1) {
                message.append(player.getNum() + ". ��ҹä��");
                continue;
            }
            message.append(player.getNum() + ". " + Main.CQ.getGroupMemberInfoV2(game.getGroup(), player.getQq()).getNick() + (player.isDead() ? Main.CC.emoji(128128) : "") + "\r\n");
        }
        message.append("����" + (wolfKilled == null ? "��ƽ��ҹ" : wolfKilled.getNum() + "����������Է��͡��ȡ� 20s")); //ȱ�ٱ���

        for (PlayerData player : game.getPlayers().values()) {
            if (player.getVocation() == Vocation.WITCH && !player.isDead()) {
                Main.CQ.sendPrivateMsg(player.getQq(), message.toString());
                break;
            }
        }
        try {
            sleep(20 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        game.setStatus(GameStatus.PROPHET);
        prophetChoosing(game);
    }

    private void prophetChoosing(Game game) {
        if (game.getStatus() != GameStatus.PROPHET) {
            return;
        }
        Main.CQ.sendGroupMsg(game.getGroup(), "Ԥ�Լ���������...");
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < game.getPlayers().size(); i++) {
            PlayerData player = StringUtils.getPlayerByNum((i + 1), game);
            if (game.getNightNum() == 1) {
                message.append(player.getNum() + ". ��ҹä��");
                continue;
            }
            message.append(player.getNum() + ". " + Main.CQ.getGroupMemberInfoV2(game.getGroup(), player.getQq()).getNick() + (player.isDead() ? Main.CC.emoji(128128) : "") + "\r\n");
        }
        message.append("��Ҫ��֤��ݵ��ǣ���ֱ�ӻظ���� 20s");
        for (PlayerData alivePlayer : game.getAlivePlayers().values()) {
            if (alivePlayer.getVocation() == Vocation.PROPHET) {
                Main.CQ.sendPrivateMsg(alivePlayer.getQq(), message.toString());
                break;
            }
        }

        try {
            sleep(20 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        StringBuilder p = new StringBuilder();
        for (int i = 0; i < game.getPlayers().size(); i++) {
            for (Map.Entry<Long, PlayerData> entry : game.getPlayers().entrySet()) {
                if ((i + 1) == entry.getValue().getNum()) {
                    message.append(entry.getValue().getNum() + ". " + Main.CQ.getGroupMemberInfoV2(game.getGroup(), entry.getValue().getQq()).getNick() + (entry.getValue().isDead() ? Main.CC.emoji(128128) : "") + "\r\n");
                }
            }
        }

        Main.CQ.sendGroupMsg(game.getGroup(), p.toString());

        if (game.getNightNum() == 1) {
            game.setStatus(GameStatus.PREPOLICE);
            prePolice(game);
        } else {
            game.setStatus(GameStatus.MORNING);
            morning(game);

        }
    }

    private void prePolice(Game game) {
        Main.CQ.sendGroupMsg(game.getGroup(), "�����ˣ����ڿ�ʼ������ѡ����\r\n��Ҫ�μӾ�ѡ����ҿ���˽�ķ��͸��ҡ��Ͼ��� 30s");
        try {
            sleep(30 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        game.setStatus(GameStatus.POLICE);
        police(game);
    }

    private void police(Game game) {
        StringBuilder message = new StringBuilder();
        message.append("�Ͼ��������\r\n");
        for (PlayerData data : police) {
            message.append(data.getNum() + "��");
        }
        if (police.isEmpty()) {
            Main.CQ.sendGroupMsg(game.getGroup(), "�����Ͼ�");
            game.setStatus(GameStatus.MORNING);
            morning(game);
        } else
            Main.CQ.sendGroupMsg(game.getGroup(), message.toString());
        try {
            sleep(3 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (PlayerData data : police) {
            Main.CQ.sendGroupMsg(game.getGroup(), "" + data.getNum() + "�ſ�ʼ���� 120s");
            saying = data;
            int timer = 0;
            while (timer <= 120 && !sayEnd) {
                timer--;
                Main.CQ.logInfo("[DeBug]", "���Լ�ʱ�����ڹ���");
                try {
                    sleep(1000);
                } catch (Exception ignored) {
                }
            }
            sayEnd = false;
        }

        game.setStatus(GameStatus.QUITPOLICE);
        quitPolice(game);

        Main.CQ.sendGroupMsg(game.getGroup(), message.toString());
    }

    private void quitPolice(Game game) {
        if (game.getStatus() != GameStatus.QUITPOLICE) {
            return;
        }

        Main.CQ.sendGroupMsg(game.getGroup(), "������ϣ���Ҫ�˳���ѡ�������˽�Ļ����ˡ���ˮ�� 20s");
        try {
            sleep(20 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        game.setStatus(GameStatus.VOTEPOLICE);
        for (PlayerData data : getPolice()) {
            game.getVote().put(data, 0);
        }

        policeVoting(game);
    }

    private void policeVoting(Game game) {
        Main.CQ.logInfo("[Debug]", "ִ����policeVoting����");
        if (game.getStatus() != GameStatus.VOTEPOLICE) {
            return;
        }
        game.getVote().clear();
        for (PlayerData data : police) {
            game.getVote().put(data, 0);
        }

        StringBuilder message = new StringBuilder();
        message.append("���ھ��ϵ���һ�ʣ\r\n");
        for (PlayerData data : police) {
            message.append(data.getNum()).append("�� ");
        }
        message.append("\r\n���λ���ͶƱ 20s");
        Main.CQ.sendGroupMsg(game.getGroup(), message.toString());
        try {
            sleep(20 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int num = 0;
        List<PlayerData> data = new ArrayList<>();
        for (Map.Entry<PlayerData, Integer> entry : game.getVote().entrySet()) {
            if (data.isEmpty()) {
                num = entry.getValue();
                data.add(entry.getKey());
            }
            if (entry.getValue() > num) {
                data.clear();
                data.add(entry.getKey());
                num = entry.getValue();
            }
            if (entry.getValue() == num) {
                if (data.contains(entry.getKey())) {
                    continue;
                }
                data.add(entry.getKey());
                num = entry.getValue();
            }
        }

        if (data.size() >= 2) {
            Main.CQ.sendGroupMsg(game.getGroup(), "ƽƱ������ڶ��ַ���");
            game.setStatus(GameStatus.POLICE);
            police = data;
            this.police(game);
        } else {
            Main.CQ.sendGroupMsg(game.getGroup(), data.get(0).getNum() + "����ҵ�ѡ����������1.5Ʊ��ʹȨ");
            game.setPolice(data.get(0));


            game.setStatus(GameStatus.MORNING);
            voted.clear();
            morning(game);
        }
    }

    private void morning(Game game) {
        StringBuilder message = new StringBuilder();
        if (wolfKilled == null && whichKilled == null) {
            message.append("������ƽ��ҹ��û�������ȥ");
        } else if (wolfKilled == null || whichKilled == null) {
            message.append(wolfKilled == null ? "����" + whichKilled.getNum() + "�������ȥ" : "����" + wolfKilled.getNum() + "�������ȥ");
            if (wolfKilled == null) {
                useGun(game, whichKilled);
                whichKilled.setDead(true);
            } else {
                useGun(game, wolfKilled);
                wolfKilled.setDead(true);
            }
        } else {
            if (wolfKilled.getNum() > whichKilled.getNum()) {
                useGun(game, wolfKilled);
                useGun(game, whichKilled);
                wolfKilled.setDead(true);
                whichKilled.setDead(true);

                message.append("����").append(whichKilled.getNum()).append("�� ").append(wolfKilled.getNum()).append("�� �������");
            } else {
                useGun(game, wolfKilled);
                useGun(game, whichKilled);
                message.append("����").append(wolfKilled.getNum()).append("�� ").append(whichKilled.getNum()).append("�� �������");
            }
        }
        if (isEnd(game)) {
            game.setEnd(true);
        } else {
            game.setEnd(false);
        }

        if (isEnd(game)) {
            game.setEnd(true);
        } else {
            game.setEnd(false);
        }
        removePolice(game);
        if (game.getNightNum() == 1 && (wolfKilled != null || whichKilled != null)) {
            message.append("��˵���� 120s �������������롾����");
            game.setStatus(GameStatus.DEATHSAY);
            Main.CQ.sendGroupMsg(game.getGroup(), message.toString());
            sayEnd = false;
            saying = wolfKilled;
            int timer = 0;
            while (!sayEnd && timer <= 120) {
                timer++;
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            saying = null;
            sayEnd = false;

        }
        StringBuilder msg = new StringBuilder();
        if (game.getPolice() != null) {
            game.setStatus(GameStatus.CHOSINGSAY);
            msg.append("������������ɾ����Ի��Ǿ��ҷ��� ˽���ҡ��󡿻��ߡ��ҡ� 20s");
            Main.CQ.sendGroupMsg(game.getGroup(), msg.toString());
            try {
                sleep(20 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            PlayerData say = game.getAlivePlayers().get(StringUtils.getPlayerByNum(new Random().nextInt(game.getAlivePlayers().size()), game).getQq());
            msg.append("û�о����������").append(say.getNum()).append("�ſ�ʼ����");
            saying = say;
            Main.CQ.sendGroupMsg(game.getGroup(), msg.toString());
        }
        game.setStatus(GameStatus.SAY);
        say(game);
    }

    private void removePolice(Game game) {
        if ((wolfKilled != null && game.getPolice().getNum() == wolfKilled.getNum()) || (whichKilled != null && game.getPolice().getNum() == whichKilled.getNum())) {
            game.setStatus(GameStatus.SHAREPOLICE);
            Main.CQ.sendGroupMsg(game.getGroup(), "��˽�ķ��͸�������Ҫ�����յ���ұ�ţ����߷��͡�˺����ȥ���� 20s");
            try {
                sleep(20 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void useGun(Game game, PlayerData data) {
        if (data.getVocation() == Vocation.HUNTER) {
            game.setStatus(GameStatus.BIU);
            Main.CQ.sendGroupMsg(game.getGroup(), "���ˣ���ѡ���Ƿ�ǹ���������˽�ġ�ǹ ID�� 20s");
            try {
                sleep(20 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private boolean isEnd(Game game) {
        int wolfs = 0;
        int villager = 0;
        int god = 0;

        for (PlayerData alivePlayer : game.getAlivePlayers().values()) {
            if (alivePlayer.getVocation() == Vocation.WOLF) {
                wolfs++;
            } else if (alivePlayer.getVocation() == Vocation.VILLAGER) {
                villager++;
            } else {
                god++;
            }

        }

        if (wolfs >= (god + villager) || villager == 0 || god == 0) {
            game.setWinner("wolf");
            return true;
        } else if (wolfs == 0) {
            game.setWinner("human");
            return true;
        }
        return false;
    }

    private void say(Game game) {
        if (game.getStatus() != GameStatus.SAY) {
            return;
        }
        while (said.size() <= game.getAlivePlayers().size()) {
            Main.CQ.sendGroupMsg(game.getGroup(), saying.getNum() + "������뷢�� 120s �������������롾����");
            int timer = 0;
            while (!sayEnd && timer <= 120) {
                timer++;
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Main.CQ.logInfo("[DeBug]", "��ʱ�����ڹ���");
            }
            said.add(saying);
            sayEnd = false;

            int num = saying.getNum();
            num = num + 1;
            if (num > game.getPlayers().size()) {
                num = 1;
            }
            while (!game.getPlayers().get(StringUtils.getPlayerByNum(num, game).getQq()).isDead()) {
                num = num + 1;
                if (num > game.getPlayers().size()) {
                    num = 1;
                }
            }

            for (Map.Entry<Long, PlayerData> longPlayerDataEntry : game.getPlayers().entrySet()) {
                if (longPlayerDataEntry.getValue().getNum() == num) {
                    saying = longPlayerDataEntry.getValue();
                    break;
                }
            }
        }
        said.clear();
        game.setStatus(GameStatus.VOTE);
        vote(game);
        game.getVote().clear();
        for (PlayerData alivePlayer : game.getAlivePlayers().values()) {
            game.getVote().put(alivePlayer, 0);
        }
    }

    private void vote(Game game) {
        StringBuilder message = new StringBuilder();

        message.append("������ҷ�����ϣ�������ͶƱʱ��\r\n");
        for (int i = 0; i < game.getPlayers().size(); i++) {
            PlayerData player = StringUtils.getPlayerByNum((i + 1), game);
            message.append(player.getNum()).append(". ").append(Main.CQ.getGroupMemberInfoV2(game.getGroup(), player.getQq()).getNick()).append(player.isDead() ? Main.CC.emoji(128128) : "").append("\r\n");
        }
        message.append("��˽������Ҫ��ͶƱ 20s");
        try {
            sleep(20 * 1000);
        } catch (Exception ignored) {
        }

        int num = 0;
        List<PlayerData> data = new ArrayList<>();
        List<Integer> nums = new ArrayList<>();
        StringBuilder vote = new StringBuilder();

        for (Map.Entry<PlayerData, Integer> entry : game.getVote().entrySet()) {
            vote.append(entry.getKey().getNum()).append("�� -> ").append(entry.getValue());
            if (entry.getKey().getNum() == game.getPolice().getNum()) {
                nums.add(entry.getValue());
            }
            nums.add(entry.getValue());
        }

        List<Integer> integers = new ArrayList<>();
        int iii = 0;
        for (Integer integer : nums) {
            if (iii == 0) {
                iii = integer;
            }
            if (integer > iii) {
                integers.clear();
                integers.add(integer);
                iii = integer;
            }
            if (integer == iii) {
                if (integers.contains(integer)) {
                    continue;
                }
                integers.add(integer);
                iii = integer;
            }
        }


        Main.CQ.sendGroupMsg(game.getGroup(), vote.toString());

        if (integers.size() >= 2) {


            Main.CQ.sendGroupMsg(game.getGroup(), "ƽƱ������ڶ��ַ���");
            game.setStatus(GameStatus.SAY);
            this.say(game);
            return;
        }
        if (integers.isEmpty()) {
            Main.CQ.sendGroupMsg(game.getGroup(), "����ͶƱ�����˳���");
        } else {
            Main.CQ.sendGroupMsg(game.getGroup(), integers.get(0) + "�ű���Ͷ����");
            this.voteByeBye = StringUtils.getPlayerByNum(integers.get(0), game);
        }

        voteEnd(game);
        voted.clear();
        game.getVote().clear();
    }

    private void voteEnd(Game game) {
        Main.CQ.sendGroupMsg(game.getGroup(), voteByeBye.getNum() + "�ű���Ͷ����\r\n�� ��˵���� 120�� ����ʹ�á�����");
        game.setStatus(GameStatus.DEATHSAY);
        useGun(game, voteByeBye);
        removePolice(game);
        StringUtils.getPlayerByNum(voteByeBye.getNum(), game).setDead(true);
        game.getAlivePlayers().remove(StringUtils.getPlayerByNum(voteByeBye.getNum(), game).getQq());
        if (isEnd(game)) {
            game.setEnd(true);
            return;
        } else {
            game.setEnd(false);
        }
        int timer = 0;
        while (!sayEnd && timer <= 120) {
            timer++;
            try {
                sleep(120 * 1000);
            } catch (Exception ignored) {
            }
        }
        sayEnd = false;
        end = true;

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


    public PlayerData getWolfKilled() {
        return wolfKilled;
    }

    public void setWolfKilled(PlayerData wolfKilled) {
        this.wolfKilled = wolfKilled;
    }

    public boolean getDefenderUsed() {
        return defenderUsed;
    }

    public void setDefenderUsed(boolean defenderUsed) {
        this.defenderUsed = defenderUsed;
    }

    public boolean getWhichUsed() {
        return whichUsed;
    }

    public void setWhichUsed(boolean whichUsed) {
        this.whichUsed = whichUsed;
    }

    public PlayerData getWhichKilled() {
        return whichKilled;
    }

    public void setWhichKilled(PlayerData whichKilled) {
        this.whichKilled = whichKilled;
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

    private PlayerData voteByeByePlayer(Game game) { //8֪��ȡʲô���ְ��Ҹ�һ��
        PlayerData temp = null;
        for (PlayerData playerData : game.getPlayers().values()) {
            if (wolfVote.containsKey(playerData.getQq())) {
                if (temp == null) {
                    temp = playerData;
                } else {
                    if (wolfVote.get(playerData.getQq()) > wolfVote.get(temp.getQq())) {
                        temp = playerData;
                    }
                }
            }
        }
        return temp;
    }

    public void sendWolfPrivateMessage(Game game, PlayerData sender, String msg) {
        for (PlayerData wolf : game.getWolfTeam().values()) {
            if (wolf.getNum() != sender.getNum()) {
                Main.CQ.sendPrivateMsg(wolf.getQq(), sender.getNum() + "�����: " + msg);
            }
        }
    }

    public void boom(Game game, PlayerData data) {
        data.setDead(true);
        if (isEnd(game)) {
            game.setEnd(true);
            return;
        } else {
            game.setEnd(false);
        }

        Main.CQ.sendGroupMsg(game.getGroup(), data.getNum() + "������Ա���ֱ�ӽ�����һ�����");
        game.getAlivePlayers().remove(data.getQq());


    }

}
