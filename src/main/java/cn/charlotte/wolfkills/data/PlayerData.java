package cn.charlotte.wolfkills.data;

import cn.charlotte.wolfkills.enums.Vocation;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class PlayerData {

    private final long qq;
    private int num;
    private Vocation vocation;
    private boolean dead;
    private boolean police;

    //对了因为lombok的原因get方法成了ispot lol你看着改8
    private boolean pot1; //解药 懒得取名字系列
    private boolean pot2; //毒药 懒得取名字系列
}
