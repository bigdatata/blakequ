package wyf.wpf.role;				//���������
/*
 * ������������˶�Ա��������Ҫ��װ����Ա��һЩ��Ϣ
 * ���⻹����һ����Ա����levelUp���������Ӯ��һ��
 * ������ʤ��֮���������������
 */
public class Player{
	public int x;						//��Ա���ĵ�x����
	public int y;						//��Ա���ĵ�y����
	public int movingDirection=-1;		//�˶�Ա���˶�����12��4��
	public int movingSpan = 1;			//�ƶ�����
	public int attackDirection;		//��������0��8��
	public int power=10;				//����ʱ������ٶȴ�С
	
	public void levelUp(){		//���������
		movingSpan+=1;			//ÿ���ƶ��Ĳ�������
		if(movingSpan > 5){
			movingSpan = 5;
		}
	}
}