package cn.charlotte.wolfkills.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Vocation {
    VILLAGER("平民", "你的观察能力就是你的技能，打破狼人的谎言，帮助好人阵营获得胜利！"),
    DEFENDER("守卫", "你可以在夜里守护一名玩家，该名玩家不会被狼人杀死，你不可以在两晚同时守护一名玩家"),
    WOLF("狼人", "每晚能够击败一名玩家，击败场上所有的神或者民则获得胜利！私聊我【自爆】即可自爆而亡，直接进入下一轮天黑"),
    WOLFKING("NMSL", ""),
    WITCH("女巫", "你有一瓶解药和毒药，解药可以防止一名玩家在夜里死去，毒药可以毒死一名玩家，呗毒死的玩家不可以发动技能或者被守卫守护"),
    HUNTER("猎人", "你死后可以带走场上任意一名活着的玩家，你也可以选择不开枪"),
    KNIGHT("骑士", "你可以在白天任意时刻对我说【刺 ID】即可对他发起对决，如果该名玩家为狼人则你亮明身份，该狼出局，直接进入下一轮天黑，如果是好人，则你以死谢罪"),
    BEAR("熊", "早上如果你的上位或者下位有狼人时 你会咆哮，如果没有或者你已出局则不会咆哮"),
    PROPHET("预言家", "每晚能够验证一名玩家是好人还是坏人，获得好人信任，用你的信息带领好人获胜！");

    private final String title, ability;
}
