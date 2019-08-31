package cn.charlotte.wolfkills.enums;

public enum GameStatus {
    WAITING,  //等待玩家
    STARTING, //随机身份 发放身份 初始化
    NIGHT, //天黑
    DEFENDER, // 守卫行动
    WOLFTALK, //狼人交流
    WOLFSELECT, // 狼人杀人
    WITCH, //女巫
    PROPHET, //预言家
    PREPOLICE, // 上警
    POLICE, //警上发言
    QUITPOLICE, //退出竞选
    VOTEPOLICE, // 选择警长
    MORNING, //天亮 公布信息
    CHOSINGSAY, // 选择发言顺序
    SHAREPOLICE,//警徽传递
    BIU,//猎人开枪
    SAY, //发言
    DEATHSAY,
    VOTE, //公投
    END, //结束
}
