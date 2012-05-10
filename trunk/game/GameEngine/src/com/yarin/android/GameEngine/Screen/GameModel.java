package com.yarin.android.GameEngine.Screen;
/**
 * ��Ϸģʽ�ࣺ������ǰ��Ϸ��ģʽ���˶����Ի���
 * �����˶�ģʽָ���ǰ�����ҵļ��̲�������Ϊ��
 * �Ի�ģʽ��ָ���Ǻ�NPC����ʱʹ�õĶԻ���ģʽ����ҿ�ͨ�����̿��ơ�ѡ���ѡ��
 *
 */
public class GameModel
{
	//�˶�ģʽ
	public static final int FREEMOVE_MODEL=1;
	//�Ի�ģʽ
	public static final int DIALOG_MODEL=2;
	//��ǰ��ģʽ����
	private int modelType=0;
	
	public GameModel(){
		modelType=FREEMOVE_MODEL;
	}
	
	public GameModel(int gameModel){
		this.modelType=gameModel;
	}
	
	public int getModelType() {
		return modelType;
	}
	
	public void setModelType(int modelType) {
		this.modelType = modelType;
	}
	
	/**
	 * �жϵ�ǰ�Ƿ����˶�ģʽ
	 * @return ������˶�ģʽ���򷵻�true�����򷵻�false
	 */
	public boolean isMoveModel(){
		return (modelType==FREEMOVE_MODEL);
	}
	
	/**
	 * �жϵ�ǰ�Ƿ��ǶԻ�ģʽ
	 * @return ����ǶԻ�ģʽ���򷵻�true�����򷵻�false
	 */
	public boolean isDialogModel(){
		return (modelType==DIALOG_MODEL);
	}
}

