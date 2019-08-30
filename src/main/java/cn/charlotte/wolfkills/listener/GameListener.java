package cn.charlotte.wolfkills.listener;

import cn.charlotte.wolfkills.Main;
import cn.charlotte.wolfkills.data.PlayerData;
import cn.charlotte.wolfkills.enums.Vocation;
import cn.charlotte.wolfkills.util.StringUtils;

import java.util.Map;
import java.util.Set;

public class GameListener {
    private static int defendedNum;


    public static void onPrivateMsg(int subType, int msgId, long fromQQ, String msg, int font) {
        Set<Map.Entry<Long, cn.charlotte.wolfkills.data.Game>> entrySet = cn.charlotte.wolfkills.data.Game.gameMap.entrySet();
        for (Map.Entry<Long, cn.charlotte.wolfkills.data.Game> map : entrySet) {
            cn.charlotte.wolfkills.data.Game game = map.getValue();
            for (int i = 0; i < game.getPlayers().size(); i++) {
                PlayerData playerData = game.getPlayers().get(i);
                if (playerData.getQq() == fromQQ) {
                    switch (game.getStatus()) {
                        case DEFENDER:
                            if (!playerData.isDead()) {
                                Main.CQ.logInfo("[Debug]", "该玩家未死");
                                if (playerData.getVocation().equals(Vocation.DEFENDER)) {
                                    Main.CQ.logInfo("[Debug]", "该玩家为守卫");
                                    if (!Main.getGameManager().getDefenderUsed()) {
                                        Main.CQ.logInfo("[Debug]", "该玩家守卫未使用");
                                        if (StringUtils.isNum(msg)) {
                                            Main.CQ.logInfo("[Debug]", "输入为数字");
                                            int num = Integer.parseInt(msg);
                                            if (num > 0 && num <= game.getPlayers().size()) {
                                                Main.CQ.logInfo("[Debug]", "范围内的数字");
                                                PlayerData player = StringUtils.getPlayerByNum(num, game);
                                                if (player == null) {
                                                    //虽然我觉得不大可能会是null
                                                    return;
                                                }
                                                Main.CQ.logInfo("[Debug]", "获取data");
                                                if (defendedNum == num) {
                                                    Main.CQ.sendPrivateMsg(fromQQ, "你守护过: " + num + " 号了");
                                                    return;
                                                }
                                                if (Main.getGameManager().getWolfKilled() != null) {
                                                    if (Main.getGameManager().getWolfKilled().getNum() == player.getNum()) {
                                                        Main.getGameManager().setWolfKilled(null);
                                                    }
                                                }
                                                defendedNum = num;
                                                Main.getGameManager().setDefenderUsed(true);
                                                Main.CQ.sendPrivateMsg(fromQQ, "你将守护的玩家: " + num + " 号");
                                            } else {
                                                Main.CQ.sendPrivateMsg(fromQQ, "请输入一个正常的号码");
                                            }
                                        } else {
                                            Main.CQ.sendPrivateMsg(fromQQ, "请输入一个正常的号码");
                                        }
                                    }
                                }
                            }
                            break;
                        case WOLFTALK:
                            if (!playerData.isDead()) {
                                if (playerData.getVocation().equals(Vocation.WOLF)) {
                                    if (Main.getGameManager().getWolfTalking().getNum() == playerData.getNum()) {
                                        if (!game.getWolfSaid().contains(playerData.getQq())) {
                                            Main.getGameManager().sendWolfPrivateMessage(game, playerData, msg);
                                            game.getWolfSaid().add(playerData.getQq());
                                            Main.CQ.sendPrivateMsg(fromQQ, "已发送");
                                        } else {
                                            Main.CQ.sendPrivateMsg(fromQQ, "现在你不能说话，可能是因为现在还没轮到你或已经说过了");
                                        }
                                    }
                                }
                            }

                            break;
                        case WOLFSELECT:
                            if (!playerData.isDead()) {
                                if (playerData.getVocation().equals(Vocation.WOLF)) {
                                    if (StringUtils.isNum(msg)) {
                                        if (!Main.getGameManager().getVoted().contains(fromQQ)) {
                                            int num = Integer.parseInt(msg);
                                            if (num > 0 && num <= game.getPlayers().size()) {
                                                PlayerData player = StringUtils.getPlayerByNum(num, game);
                                                if (player == null) {
                                                    //虽然我觉得不大可能会是null
                                                    return;
                                                }
                                                if (Main.getGameManager().getWolfVote().containsKey(player.getQq())) {
                                                    Main.getGameManager().getWolfVote().put(player.getQq(), Main.getGameManager().getWolfVote().get(fromQQ) + 1);
                                                } else {
                                                    Main.getGameManager().getWolfVote().put(player.getQq(), 1);
                                                }
                                                Main.getGameManager().getVoted().add(fromQQ);
                                                Main.CQ.sendPrivateMsg(fromQQ, "你投给了 " + num + " 号");
                                            } else {
                                                Main.CQ.sendPrivateMsg(fromQQ, "请输入一个正常的号码");
                                            }
                                        }
                                    } else {
                                        Main.CQ.sendPrivateMsg(fromQQ, "请输入一个正常的号码");
                                    }
                                }
                            }
                            break;
                        case WITCH:
                            if (!playerData.isDead()) {
                                Main.CQ.logInfo("[Debug]", "该玩家未死");
                                if (playerData.getVocation().equals(Vocation.WITCH)) {
                                    Main.CQ.logInfo("[Debug]", "该玩家是女巫");
                                    if (!Main.getGameManager().getWhichUsed()) {
                                        Main.CQ.logInfo("[Debug]", "该玩家未操作过");
                                        if (Main.getGameManager().getWolfKilled() != null && playerData.isPot1()) {
                                            Main.CQ.logInfo("[Debug]", "未使用过毒药和有人死亡");
                                            if (msg.equals("救")) {
                                                Main.CQ.logInfo("[Debug]", "该玩家指令正确");
                                                Main.getGameManager().setWolfKilled(null);
                                                Main.CQ.sendGroupMsg(game.getGroup(), "成功！");
                                                Main.getGameManager().setWhichUsed(true);
                                            }
                                        }
                                    }

                                    if (playerData.isPot2()) {
                                        Main.CQ.logInfo("[Debug]", "该玩家未使用过解药");
                                        if (StringUtils.isNum(msg)) {
                                            Main.CQ.logInfo("[Debug]", "该玩家输入为数字");
                                            int num = Integer.parseInt(msg);
                                            if (num > 0 && num <= game.getPlayers().size()) {
                                                Main.CQ.logInfo("[Debug]", "该玩家数字输入范围正确");
                                                PlayerData player = StringUtils.getPlayerByNum(num, game);
                                                if (player == null) {
                                                    //虽然我觉得不大可能会是null
                                                    return;
                                                }
                                                Main.CQ.logInfo("[Debug]", "获取目标data");
                                                if (Main.getGameManager().getWolfKilled().getNum() != player.getNum()) {
                                                    Main.getGameManager().setWhichKilled(player);
                                                }
                                                Main.getGameManager().setWhichUsed(true);
                                                Main.CQ.sendPrivateMsg(fromQQ, "成功");
                                            }
                                        }
                                    }
                                }
                            }

                            break;
                        case PROPHET:
                            if (!playerData.isDead()) {
                                if (playerData.getVocation().equals(Vocation.PROPHET)) {
                                    if (!Main.getGameManager().getTemp5()) {
                                        if (StringUtils.isNum(msg)) {
                                            int num = Integer.parseInt(msg);
                                            if (num > 0 && num <= game.getPlayers().size()) {
                                                PlayerData player = StringUtils.getPlayerByNum(num, game);
                                                if (player == null) {
                                                    //虽然我觉得不大可能会是null
                                                    return;
                                                }
                                                Main.getGameManager().setTemp5(true);
                                                if (game.getWolfTeam().contains(player)) {
                                                    Main.CQ.sendPrivateMsg(fromQQ, num + " 号玩家身份: 狼人");
                                                } else {
                                                    Main.CQ.sendPrivateMsg(fromQQ, num + " 号玩家身份: 好人");
                                                }
                                            } else {
                                                Main.CQ.sendPrivateMsg(fromQQ, "请输入一个正常的号码");
                                            }
                                        } else {
                                            Main.CQ.sendPrivateMsg(fromQQ, "请输入一个正常的号码");
                                        }
                                    }
                                }
                            }

                            break;
                        case PREPOLICE:
                            if (!playerData.isDead()) {
                                if (msg.equals("上警")) {
                                    if (!Main.getGameManager().getPolice().contains(playerData)) {
                                        Main.getGameManager().getPolice().add(playerData);
                                        Main.CQ.sendPrivateMsg(fromQQ, "成功");
                                    }
                                }
                            }

                            break;
                        case POLICE:
                            if (!playerData.isDead()) {
                                if (playerData.getNum() == Main.getGameManager().getSaying().getNum()) {
                                    if (msg.equals("过")) {
                                        Main.getGameManager().setSayEnd(true);
                                    }
                                    Main.CQ.sendGroupMsg(game.getGroup(), playerData.getNum() + "号玩家: " + msg);
                                }
                            }
                            break;
                        case QUITPOLICE:
                            if (!playerData.isDead()) {
                                if (msg.equals("退水")) {
                                    if (Main.getGameManager().getPolice().contains(playerData)) {
                                        Main.getGameManager().getPolice().remove(playerData);
                                        Main.CQ.sendPrivateMsg(fromQQ, "成功");
                                    }
                                }
                            }
                            break;
                        case VOTEPOLICE:

                            if (!playerData.isDead()) {
                                if (!Main.getGameManager().getPolice().contains(playerData)) {
                                    if (StringUtils.isNum(msg)) {
                                        int num = Integer.parseInt(msg);
                                        if (Main.getGameManager().getVoted().contains(fromQQ)) {
                                            return;
                                        }
                                            game.getVote().put(StringUtils.getPlayerByNum(num, game), game.getVote().get(StringUtils.getPlayerByNum(num, game)) + 1);
                                            Main.CQ.sendGroupMsg(fromQQ, "成功");
                                            Main.getGameManager().getVoted().add(fromQQ);
                                    }
                                }
                            }

                            break;
                        case VOTE:
                            if (!playerData.isDead()) {
                                if (StringUtils.isNum(msg)) {
                                    int num = Integer.parseInt(msg);
                                    if (Main.getGameManager().getVoted().contains(fromQQ)) {
                                        return;
                                    }
                                    game.getVote().put(playerData, num);
                                    Main.CQ.sendGroupMsg(fromQQ, "成功");
                                    Main.getGameManager().getVoted().add(fromQQ);
                                }
                            }


                            break;
                        case SAY:
                            if (!playerData.isDead()) {
                                if (msg.equals("过")) {
                                    Main.getGameManager().setSayEnd(true);
                                }
                                if (Main.getGameManager().getSaying().getNum() == playerData.getNum()) {
                                    Main.CQ.sendGroupMsg(game.getGroup(), playerData.getNum() + "号玩家: " + msg);
                                }
                            }
                            break;

                        case BIU:
                            if (Main.getGameManager().isTemp6()) {
                                return;
                            }
                            if (StringUtils.isNum(msg)) {
                                int num = Integer.parseInt(msg);
                                StringUtils.getPlayerByNum(num, game).setDead(true);
                                Main.CQ.sendGroupMsg(game.getGroup(), "猎人带走了" + msg + "号");
                            }
                            break;
                        case DEATHSAY:

                            break;
                        case SHAREPOLICE:
                            if (game.getPolice().getQq() == fromQQ) {
                                if (StringUtils.isNum(msg)) {
                                    game.setPolice(StringUtils.getPlayerByNum(Integer.parseInt(msg), game));
                                } else {
                                    if (msg.equals("撕")) {
                                        game.setPolice(null);
                                    }
                                }
                            }
                            break;
                        case CHOSINGSAY:
                            if (game.getPolice().getQq() == fromQQ) {
                                if (msg.equals("左")) {
                                    int num = game.getPolice().getNum();
                                    if (num == 1) {
                                        num = game.getPlayers().size();
                                    } else {
                                        num = num - 1;
                                    }
                                    Main.getGameManager().setSaying(StringUtils.getPlayerByNum(num, game));
                                    Main.CQ.sendGroupMsg(game.getGroup(), "警长选择警左发言");
                                } else if (msg.equals("右")) {
                                    int num = game.getPolice().getNum();
                                    if (num == game.getPlayers().size()) {
                                        num = 1;
                                    } else {
                                        num = num + 1;
                                    }
                                    Main.getGameManager().setSaying(StringUtils.getPlayerByNum(num, game));
                                    Main.CQ.sendGroupMsg(game.getGroup(), "警长选择警右发言");
                                }
                            }
                            break;
                    }
                    if (msg.equals("自爆")) {
                        if (!playerData.isDead()) {
                            if (game.getWolfTeam().contains(playerData)) {

                            }
                        }
                    }


                    return;
                }
            }
        }
    }
}
