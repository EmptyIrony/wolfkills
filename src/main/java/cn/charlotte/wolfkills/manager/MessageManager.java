package cn.charlotte.wolfkills.manager;

import cn.charlotte.wolfkills.listener.PreStartListener;
import com.sobte.cqp.jcq.entity.IMsg;

public class MessageManager {
    /**
     * ˽����Ϣ (Type=21)<br>
     * ���������ڿ�Q���̡߳��б����á�<br>
     *
     * @param subType �����ͣ�11/���Ժ��� 1/��������״̬ 2/����Ⱥ 3/����������
     * @param msgId   ��ϢID
     * @param fromQQ  ��ԴQQ
     * @param msg     ��Ϣ����
     * @param font    ����
     * @return ����ֵ*����*ֱ�ӷ����ı� ���Ҫ�ظ���Ϣ�������api����<br>
     * ���� ����  {@link IMsg#MSG_INTERCEPT MSG_INTERCEPT} - �ضϱ�����Ϣ�����ټ�������<br>
     * ע�⣺Ӧ�����ȼ�����Ϊ"���"(10000)ʱ������ʹ�ñ�����ֵ<br>
     * ������ظ���Ϣ������֮���Ӧ��/�������������� ����  {@link IMsg#MSG_IGNORE MSG_IGNORE} - ���Ա�����Ϣ
     */

    public void onPrivateMsg(int subType, int msgId, long fromQQ, String msg, int font) {


    }

    /**
     * Ⱥ��Ϣ (Type=2)<br>
     * ���������ڿ�Q���̡߳��б����á�<br>
     *
     * @param subType       �����ͣ�Ŀǰ�̶�Ϊ1
     * @param msgId         ��ϢID
     * @param fromGroup     ��ԴȺ��
     * @param fromQQ        ��ԴQQ��
     * @param fromAnonymous ��Դ������
     * @param msg           ��Ϣ����
     * @param font          ����
     */

    public void onGroupMsg(int subType, int msgId, long fromGroup, long fromQQ, String fromAnonymous, String msg, int font) {
        PreStartListener.onGroupMsg(subType,msgId,fromGroup,fromQQ,fromAnonymous,msg,font);
    }
}
