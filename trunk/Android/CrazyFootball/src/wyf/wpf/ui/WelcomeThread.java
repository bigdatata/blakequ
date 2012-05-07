package wyf.wpf.ui;		//���������


/*
 * ����̳���Thread����Ҫʵ�ֻ�ӭ����ĺ�̨����
 * ���޸���ʵ�ֶ���Ч��
 */
public class WelcomeThread extends Thread{
	WelcomeView father;				//WelcomeView���������
	public boolean isWelcoming = false;	//�߳�ִ�б�־λ
	public float rotateAngle = 60;			//��ת�Ƕ�
	public int rotateCounter = 0;			//��ת������
	public int animationCounter=0;			//��֡������
	public int sleepSpan = 150;			//����ʱ��
	//����������ʼ����Ҫ��Ա����	
	public WelcomeThread(WelcomeView father){
		this.father = father;
		isWelcoming = true;
	}	
	public void run(){//�̵߳�ִ�з���
		while(isWelcoming){
			switch(father.status){//��ȡ���ڵ�״̬
			case 0:			//��״̬Ϊ3��ͼƬ������ʾ		
				animationCounter++;				//��֡�������Լ�
				if(animationCounter == 4){		//�������ﵽ4ʱ��֡
					father.index ++;
					if(father.index == 3){		//�ж��Ƿ񲥷��������֡
						father.status = 1;		//ת����һ״̬
					}	
					animationCounter = 0;		//��ռ�����
				}
				break;
			case 1://��״̬Ϊ����ͼƬ��ת�Ž���
				father.matrix.postRotate(rotateAngle);		//��ת�Ƕ�
				rotateCounter++;							//�������Լ�
				if(rotateCounter == 6){//��ת����������
					father.status = 2;//����״̬
					father.alpha = 0;	//����alphaֵ�����ڲ˵�����
				}
				break;
			case 2://��״̬Ϊ�˵����Բ˵�����
				father.alpha +=51;			//alphaֵ����
				if(father.alpha >= 255){
					father.status = 3;//�������״̬����״̬��ҿ���ѡ��˵�ѡ��
				}
				break;
			case 3:	//��������˴���״̬�����Լ����Լ��ر�
				this.isWelcoming = false;
				break;
			}
			try{
				Thread.sleep(sleepSpan );		//����һ��ʱ��
			}
			catch(Exception e){
				e.printStackTrace();			//���񲢴�ӡ�쳣
			}
		}
	}
}