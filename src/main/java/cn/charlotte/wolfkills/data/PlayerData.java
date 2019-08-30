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

    //������Ϊlombok��ԭ��get��������ispot lol�㿴�Ÿ�8
    private boolean pot1; //��ҩ ����ȡ����ϵ��
    private boolean pot2; //��ҩ ����ȡ����ϵ��
}
