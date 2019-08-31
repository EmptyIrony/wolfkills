package cn.charlotte.wolfkills.manager;

import cn.charlotte.wolfkills.Main;
import cn.charlotte.wolfkills.data.Game;
import cn.charlotte.wolfkills.data.PlayerData;
import cn.charlotte.wolfkills.enums.GameStatus;
import cn.charlotte.wolfkills.enums.Vocation;
import cn.charlotte.wolfkills.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

import static java.lang.Thread.sleep;
@Getter
public class GameManager {
    private PlayerData wolfTalking;
    @Setter
    private PlayerData saying;
    private List<PlayerData> said = new ArrayList<>();
    @Setter
    private boolean sayEnd;

    private List<Long> voted = new ArrayList<>(); //已投票狼人
    private Map<Long, Integer> wolfVote = new HashMap<>();
    private PlayerData wolfKilled; //8知道取什么名字帮我改一下 被狼人击杀的玩家
    private boolean defenderUsed; //守卫是否操作过了
    private boolean whichUsed; //女巫是否操作过了
    private PlayerData whichKilled; //女巫击杀的玩家'
    private boolean temp5; //预言家是否操作过了
    private boolean temp6;//猎人技能是否用过
    private List<PlayerData> police = new ArrayList<>();
    private PlayerData voteByeBye;
    private boolean end;

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
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "你好，欢迎参加本场狼人杀  你是【" + data.getNum() + "】号\r\n你的身份是【平民】\r\n技能: 你的观察能力就是你的技能，打破狼人的谎言，帮助好人阵营获得胜利！");
                    break;
                case WOLF:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "你好，欢迎参加本场狼人杀  你是【" + data.getNum() + "】号\r\n你的身份是【狼人】\r\n技能: 每晚能够击败一名玩家，击败场上所有的神或者民则获得胜利！私聊我【自爆】即可自爆而亡，直接进入下一轮天黑");
                    break;
                case PROPHET:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "你好，欢迎参加本场狼人杀  你是【" + data.getNum() + "】号\r\n你的身份是【预言家】\r\n技能: 每晚能够验证一名玩家是好人还是坏人，获得好人信任，用你的信息带领好人获胜！");
                    break;
                case HUNTER:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "你好，欢迎参加本场狼人杀  你是【" + data.getNum() + "】号\r\n你的身份是【猎人】\r\n技能: 你死后可以带走场上任意一名活着的玩家，你也可以选择不开枪");
                    break;
                case BEAR:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "你好，欢迎参加本场狼人杀  你是【" + data.getNum() + "】号\r\n你的身份是【熊】\r\n技能: 早上如果你的上位或者下位有狼人时 你会咆哮，如果没有或者你已出局则不会咆哮");
                    break;
                case WITCH:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "你好，欢迎参加本场狼人杀  你是【" + data.getNum() + "】号\r\n你的身份是【女巫】\r\n技能: 你有一瓶解药和毒药，解药可以防止一名玩家在夜里死去，毒药可以毒死一名玩家，被毒死的玩家不可以发动技能或者被守卫守护");
                    break;
                case KNIGHT:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "你好，欢迎参加本场狼人杀  你是【" + data.getNum() + "】号\r\n你的身份是【骑士】\r\n技能: 你可以在白天任意时刻对我说【刺 ID】即可对他发起对决，如果该名玩家为狼人则你亮明身份，该狼出局，直接进入下一轮天黑，如果是好人，则你以死谢罪");
                    break;
                case DEFENDER:
                    Main.CQ.sendPrivateMsg(game.getPlayers().get(i).getQq(), "你好，欢迎参加本场狼人杀  你是【" + data.getNum() + "】号\r\n你的身份是【守卫】\r\n技能: 你可以在夜里守护一名玩家，该名玩家不会被狼人杀死，你不可以在两晚同时守护一名玩家");
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

    public void start(Game game) {
        if (game.getStatus()!=GameStatus.WOLFTALK){
            return;
        }

        game.getWolfTeam().forEach(playerData -> {
            wolfTalking = playerData;
            Main.CQ.sendPrivateMsg(playerData.getQq(),"现在轮到你发言了，限时15s，一句话");
            try {
                sleep(15*1000);
            } catch (InterruptedException ignored) {}
        });
        game.setStatus(GameStatus.WOLFSELECT);
        wolfVoting(game);
    }

    private void wolfVoting(Game game) {
        if (game.getStatus()!=GameStatus.WOLFSELECT){
            return;
        }
        for (PlayerData data : game.getPlayers()) {
            wolfVote.put(data.getQq(), 0);
        }


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
            sleep(20*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        wolfKilled = voteByeByePlayer(game);

        game.setStatus(GameStatus.DEFENDER);
        defendChoosing(game);
        voted.clear();
    }

    private void defendChoosing(Game game){
        if (game.getStatus()!=GameStatus.DEFENDER){
            return;
        }
        Main.CQ.sendGroupMsg(game.getGroup(), "守卫正在选择守护目标...");

        StringBuilder message = new StringBuilder();
        for (PlayerData player : game.getPlayers()) {
            message.append(player.getNum() + ". " + Main.CQ.getGroupMemberInfoV2(game.getGroup(), player.getQq()).getNick() + (player.isDead() ? Main.CC.emoji(128128) : "") + "\r\n");
        }
        message.append("请发送你要守护的人的序号，请注意你不可以连着两晚守护同一名玩家");
        
        for (PlayerData player : game.getPlayers()) {
            if (player.getVocation()==Vocation.DEFENDER&&!player.isDead()){
                Main.CQ.sendPrivateMsg(player.getQq(),message.toString());
                break;
            }
        }
        try {
            sleep(20*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        game.setStatus(GameStatus.WITCH);
        witchChoosing(game);
    }

    private void witchChoosing(Game game){
        if (game.getStatus()!=GameStatus.WITCH){
            return;
        }
        Main.CQ.sendGroupMsg(game.getGroup(), "女巫正在行动...");

        StringBuilder message = new StringBuilder();
        for (PlayerData player : game.getPlayers()) {
            if (game.getNightNum()==1){
                message.append(player.getNum()+". 首夜盲毒");
                continue;
            }
            message.append(player.getNum() + ". " + Main.CQ.getGroupMemberInfoV2(game.getGroup(), player.getQq()).getNick() + (player.isDead() ? Main.CC.emoji(128128) : "") + "\r\n");
        }
        message.append("昨晚，" + (wolfKilled == null ? "是平安夜" : wolfKilled.getNum() + "遇害，你可以发送【救】 20s")); //缺少变量

        for (PlayerData player : game.getPlayers()) {
            if (player.getVocation()==Vocation.WITCH&&!player.isDead()){
                Main.CQ.sendPrivateMsg(player.getQq(),message.toString());
                break;
            }
        }
        try {
            sleep(20*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        game.setStatus(GameStatus.PROPHET);
        prophetChoosing(game);
    }

    private void prophetChoosing(Game game){
        if (game.getStatus()!=GameStatus.PROPHET){
            return;
        }
        Main.CQ.sendGroupMsg(game.getGroup(), "预言家正在验人...");
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < game.getPlayers().size(); i++) {
            PlayerData player = StringUtils.getPlayerByNum((i + 1), game);
            if (game.getNightNum()==1){
                message.append(player.getNum()+". 首夜盲验");
                continue;
            }
            message.append(player.getNum() + ". " + Main.CQ.getGroupMemberInfoV2(game.getGroup(), player.getQq()).getNick() + (player.isDead() ? Main.CC.emoji(128128) : "") + "\r\n");
        }
        message.append("你要验证身份的是？请直接回复序号 20s");
        for (PlayerData alivePlayer : game.getAlivePlayers()) {
            if (alivePlayer.getVocation() == Vocation.PROPHET) {
                Main.CQ.sendPrivateMsg(alivePlayer.getQq(), message.toString());
                break;
            }
        }

        try {
            sleep(20*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (game.getNightNum()==1){
            game.setStatus(GameStatus.PREPOLICE);
            prePolice(game);
        }else {
            game.setStatus(GameStatus.MORNING);
            morning(game);
        }
    }

    private void prePolice(Game game){
        Main.CQ.sendGroupMsg(game.getGroup(), "天亮了，现在开始警长竞选环节\r\n想要参加竞选的玩家可以私聊发送给我【上警】 20s");
        try {
            sleep(20*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        game.setStatus(GameStatus.POLICE);
        police(game);
    }

    private void police(Game game) {
        StringBuilder message = new StringBuilder();
        message.append("上警的玩家有\r\n");
        for (PlayerData data : police) {
            message.append(data.getNum() + "号");
        }
        Main.CQ.sendGroupMsg(game.getGroup(), message.toString());
        try {
            sleep(3 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (PlayerData data : police) {
            Main.CQ.sendGroupMsg(game.getGroup(), "" + data.getNum() + "号开始发言 120s");
            saying = data;
            int timer = 0;
            while (timer <= 120 && !sayEnd) {
                timer--;
                try {
                    sleep(1000);
                } catch (Exception ignored) {
                }
            }
            sayEnd = false;
        }

        game.setStatus(GameStatus.QUITPOLICE);
        quitPolice(game);

        Main.CQ.sendGroupMsg(game.getGroup(),message.toString());
    }

    private void quitPolice(Game game) {
        if (game.getStatus()!=GameStatus.QUITPOLICE){
            return;
        }

        StringBuilder message = new StringBuilder();
        message.append("发言完毕，想要退出竞选的玩家请私聊机器人【退水】 20s");
        Main.CQ.sendGroupMsg(game.getGroup(),message.toString());
        try {
            sleep(20*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        game.setStatus(GameStatus.VOTEPOLICE);
        for (PlayerData data : getPolice()) {
            game.getVote().put(data, 0);
        }

        policeVoting(game);
    }

    private void policeVoting(Game game){
        Main.CQ.logInfo("[Debug]", "执行了policeVoting方法");
        if (game.getStatus()!=GameStatus.VOTEPOLICE){
            return;
        }
        game.getVote().clear();
        for (PlayerData data : police) {
            game.getVote().put(data, 0);
        }

            StringBuilder message = new StringBuilder();
            message.append("现在警上的玩家还剩\r\n");
        for (PlayerData data : police) {
            message.append(data.getNum() + "号 ");
        }
            message.append("\r\n请各位玩家投票 20s");
            Main.CQ.sendGroupMsg(game.getGroup(),message.toString());
            try{
                sleep(20*1000);
            }catch (Exception e){
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
                Main.CQ.sendGroupMsg(game.getGroup(), "平票！进入第二轮发言");
                game.setStatus(GameStatus.POLICE);
                police = data;
                this.police(game);
            } else {
                Main.CQ.sendGroupMsg(game.getGroup(), data.get(0).getNum() + "号玩家当选警长！他有1.5票行使权");
                game.setPolice(data.get(0));


                game.setStatus(GameStatus.MORNING);
                voted.clear();
                morning(game);
            }
    }
    private void morning(Game game){
        StringBuilder message = new StringBuilder();
        if (wolfKilled == null && whichKilled == null) {
            message.append("昨晚是平安夜，没有玩家死去");
        } else if (wolfKilled == null || whichKilled == null) {
            message.append(wolfKilled == null ? "昨晚，" + whichKilled.getNum() + "号玩家死去" : "昨晚，" + wolfKilled.getNum() + "号玩家死去");
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

                message.append("昨晚，" + whichKilled.getNum() + "号 " + wolfKilled.getNum() + "号 玩家遇害");
            } else {
                useGun(game, wolfKilled);
                useGun(game, whichKilled);
                message.append("昨晚，" + wolfKilled.getNum() + "号 " + whichKilled.getNum() + "号 玩家遇害");
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
        if (game.getNightNum()==1){
            message.append(""+"号，请说遗言 120s 结束发言请输入【过】");
            game.setStatus(GameStatus.DEATHSAY);
            Main.CQ.sendGroupMsg(game.getGroup(),message.toString());

        }
        StringBuilder msg = new StringBuilder();
        if (game.getPolice()!=null){
            game.setStatus(GameStatus.CHOSINGSAY);
            msg.append("警长，请决定由警左发言还是警右发言 私聊我【左】或者【右】 20s");
            Main.CQ.sendGroupMsg(game.getGroup(),msg.toString());
            try {
                sleep(20*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else {
            PlayerData say = game.getAlivePlayers().get(new Random().nextInt(game.getAlivePlayers().size()));
            msg.append("没有警长，随机从"+say.getNum()+"号开始发言");
            saying = say;
            Main.CQ.sendGroupMsg(game.getGroup(),msg.toString());
        }
        game.setStatus(GameStatus.SAY);
        say(game);
    }

    private void removePolice(Game game){
        if ((wolfKilled != null && game.getPolice().getNum() == wolfKilled.getNum()) || (whichKilled != null && game.getPolice().getNum() == whichKilled.getNum())) {
            game.setStatus(GameStatus.SHAREPOLICE);
            Main.CQ.sendGroupMsg(game.getGroup(), "请私聊发送给我你想要给警徽的玩家编号，或者发送【撕】舍去警徽 20s");
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
            Main.CQ.sendGroupMsg(game.getGroup(), "猎人，请选择是否开枪，如果是请私聊【枪 ID】 20s");
            try {
                sleep(20 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private boolean isEnd(Game game){
        int wolfs = 0;
        int villager = 0;
        int god = 0;

        for (PlayerData alivePlayer : game.getAlivePlayers()) {
            if (alivePlayer.getVocation()==Vocation.WOLF){
                wolfs++;
            }else if (alivePlayer.getVocation()==Vocation.VILLAGER){
                villager++;
            }else {
                god++;
            }

        }

        if (wolfs>=(god+villager)||villager==0||god==0){
            game.setWinner("wolf");
            return true;
        }else if (wolfs==0){
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
            Main.CQ.sendGroupMsg(game.getGroup(), saying.getNum() + "号玩家请发言 120s 结束发言请输入【过】");
            int timer = 0;
            while (sayEnd || timer <= 120) {
                timer++;
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            said.add(saying);
            sayEnd = false;

            int num = saying.getNum();
            num = num + 1;
            if (num > game.getPlayers().size()) {
                num = 1;
            }
            while (!game.getPlayers().get(num).isDead()) {
                num = num + 1;
                if (num > game.getPlayers().size()) {
                    num = 1;
                }
            }

            saying = game.getPlayers().get(num);
        }
        game.setStatus(GameStatus.VOTE);
        vote(game);
        game.getVote().clear();
        for (PlayerData alivePlayer : game.getAlivePlayers()) {
            game.getVote().put(alivePlayer, 0);
        }
    }

    private void vote(Game game) {
        StringBuilder message = new StringBuilder();

        message.append("所有玩家发言完毕，现在是投票时间\r\n");
        for (int i = 0; i < game.getPlayers().size(); i++) {
            PlayerData player = StringUtils.getPlayerByNum((i + 1), game);
            message.append(player.getNum() + ". " + Main.CQ.getGroupMemberInfoV2(game.getGroup(), player.getQq()).getNick() + (player.isDead() ? Main.CC.emoji(128128) : "") + "\r\n");
        }
        message.append("请私聊我需要以投票 20s");
        try {
            sleep(20 * 1000);
        } catch (Exception ignored) {
        }

        int num = 0;
        List<PlayerData> data = new ArrayList<>();
        List<Integer> nums = new ArrayList<>();
        StringBuilder vote = new StringBuilder();

        for (Map.Entry<PlayerData, Integer> entry : game.getVote().entrySet()) {
            vote.append(entry.getKey() + "号 -> " + entry.getValue());
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
            Main.CQ.sendGroupMsg(game.getGroup(), "平票！进入第二轮发言");
            game.setStatus(GameStatus.SAY);
            this.say(game);
            return;
        }
        if (integers.isEmpty()) {
            Main.CQ.sendGroupMsg(game.getGroup(), "无人投票，无人出局");
        } else {
            Main.CQ.sendGroupMsg(game.getGroup(), integers.get(0) + "号被公投出局");
            this.voteByeBye = StringUtils.getPlayerByNum(integers.get(0), game);
        }

        voteEnd(game);
        voted.clear();
        game.getVote().clear();
    }

    private void voteEnd(Game game) {
        StringBuilder message = new StringBuilder();
        message.append(voteByeBye.getNum() + "号被公投出局\r\n号 请说遗言 120秒 可以使用【过】");
        Main.CQ.sendGroupMsg(game.getGroup(), message.toString());
        game.setStatus(GameStatus.DEATHSAY);
        useGun(game, voteByeBye);
        removePolice(game);
        if (isEnd(game)) {
            game.setEnd(true);
            return;
        } else {
            game.setEnd(false);
        }
        int timer = 0;
        while (sayEnd || timer <= 120) {
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

    private PlayerData voteByeByePlayer(Game game) { //8知道取什么名字帮我改一下
        PlayerData temp = null;
        for (PlayerData playerData : game.getPlayers()) {
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
        for (PlayerData wolf : game.getWolfTeam()) {
            if (wolf.getNum() != sender.getNum()) {
                Main.CQ.sendPrivateMsg(wolf.getQq(), sender.getNum() + "号玩家: " + msg);
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

        Main.CQ.sendGroupMsg(game.getGroup(), data.getNum() + "号玩家自爆，直接进入下一轮天黑");
        game.getAlivePlayers().remove(data);


    }

}
