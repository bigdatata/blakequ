package com.hao;

/**
 * Ӣ�����ħս������������(ս���غϵ�)
 * @author Administrator
 *
 */
public class FightCalc
{
	public static final int MIN_ORGE_INDEX = 50;
	public static final int MAX_ORGE_INDEX = 80;

	private static final int OFFSET = 50;
	//��ħ����,��ͬ����Ķ�ħ�в�ͬ�����ԣ�31�ֶ�ӦorgeName�еĲ�ͬ���ֵĶ�ħ��
	private static final int[][] orgeAttr= {
	//HP,attack,defend,money,experience	
		{50, 20, 1, 1, 1},
		{70, 15, 2, 2, 1},
		{100, 20, 5, 3, 2},
		{110, 25, 5, 5, 3},
		{200, 35, 10, 5, 3},
		{150, 40, 20, 8, 5},
		{125, 50, 25, 10, 7},
		{150, 65, 30, 10, 8},
		{300, 75, 45, 13, 10},
		{400, 90, 50, 15, 12},
		{500, 115, 65, 15, 15},
		{250, 120, 70, 20, 15},
		{450, 150, 90, 22, 19},
		{550, 170, 100, 25, 20},
		{100, 200, 110, 30, 25},
		{700, 250, 125, 32, 25},
		{1300, 300, 150, 40, 30},
		{850, 350, 200, 45, 35},
		{500, 400, 260, 47, 35},
		{900, 450, 330, 50, 40},
		{1250, 500, 400, 55, 45},
		{1500, 560, 460, 60, 50},
		{1200, 620, 520, 65, 50},
		{2000, 680, 590, 70, 55},
		{900, 750, 650, 77, 60},
		{1500, 830, 730, 80, 65},
		{2500, 900, 850, 84, 70},
		{1200, 980, 900, 88, 75},
		{3100, 1050, 950, 92, 80},
		{15000, 1000, 1000, 100, 100},
		{25000, 1500, 1200, 150, 120}
	};
	//�������Գ�������Ӧ����orgeAttr��ÿ�����ͣ�
	public static final int HP = 0,
							 ATTACK = 1,
							 DEFEND = 2,
							 MONEY = 3,
							 EXPERIENCE = 4;
	//�غϳ���
	public static final int BOUT_NUM = 0,
							HERO_DAMAGE_PER_BOUT = 1,
							ORGE_DAMAGE_PER_BOUT = 2;
	//�������͵Ķ�ħ
	private static final String[] orgeName = 
	{
		"��ͷ�� ", "��ͷ�� ","С���� ", "������ ","��ͷ��","����ʿ��",
		"������ʦ", "������ ", "������ ", "���öӳ�", "ʯͷ����", "���·�ʦ",
		"��������", "������ ", "�߼���ʦ", "����", "������ʿ", "�����ʿ", 
		"���·�ʦ", "������ʿ", "ڤ���� ", "�߼�����", "˫�ֽ�ʿ", "ڤսʿ ", 
		"��׶ӳ�", "�鷨ʦ ", "ڤ�ӳ� ", "����ʿ ", "Ӱ��սʿ", "����ħ��", "ڤ��ħ��"
	};
	
	private HeroSprite					hero;		//Ӣ��
	private int						curOrge;		//��ǰ��ħ
	private int						heroDamagePerBout, orgeDamagePerBout;	//ÿ�غϹ��������öԷ���ʧ��Ѫ����
	private int						heroBout;		//Ӣ��Ҫɱ����ǰ��ħ��Ҫ��ս���غ���
	private int						orgeBout;		//��ǰ��ħҪɱ����ǰӢ����Ҫս���Ļغ���


	public FightCalc(HeroSprite hero)
	{
		this.hero = hero;
	}


	/**
	 * ��ȡ��ǰ��ħ����
	 * @param type
	 * @return
	 */
	private int calcType(int type)
	{
		return type - OFFSET;
	}


	/**
	 * ����Ӣ�ۺ͵�ǰ��ħ��ս���غ�
	 */
	private void calcBout()
	{
		heroDamagePerBout = orgeAttr[curOrge][ATTACK] - hero.getDefend();	//Ӣ��ÿ�غϵĹ�����=��ħ�Ĺ�����-Ӣ�۵ķ�����
		orgeDamagePerBout = hero.getAttack() - orgeAttr[curOrge][DEFEND];
		//����heroBout��˼����ֻ��ҪheroBout�غϾ��ܽ���ħɱ��
		heroBout = orgeAttr[curOrge][HP] / orgeDamagePerBout;				//Ӣ���뵱ǰ��ħս���غ���=��ħ��Ѫ��/��ħÿ�غϵ���ʧѪ��
		//���Ϊ�˷�ֹ���������heroBoutΪ���Σ���50/8=6,��ʵ����Ҫ7���غϲ���ɱ��
		//�ʶ���Ҫ��50-6*8=2>0����������ڽ�herBout��1
		if ((orgeAttr[curOrge][HP] - orgeDamagePerBout * heroBout) > 0)
			heroBout++;
		//��ħ�Ĺ�����̫С��С��Ӣ�۷����������ܶ�Ӣ���������
		if (heroDamagePerBout <= 0)
		{
			heroDamagePerBout = 0;
			orgeBout = Integer.MAX_VALUE;		//��Ϊ�Զ�ħ��������в���ʶ���ħ�Ļغ�����Ϊ��󣬼���Զ����ɱ��Ӣ��
		}
		//��ħ�Ĺ�������ʹӢ����ʧѪ��
		else
		{
			//��ħҪɱ��Ӣ����Ҫ���еĻغ���
			orgeBout = hero.getHp() / heroDamagePerBout;
			
			if ((hero.getHp() - heroDamagePerBout * orgeBout) > 0)
				orgeBout++;
		}
	}


	/**
	 * ��Ӣ���Ƿ��ܹ�����ħ
	 * @param type
	 * @return
	 */
	public boolean canAttack(int type)
	{
		boolean result = false;
		curOrge = calcType(type);
		//��������Ӣ�۵Ĺ�����
		if (orgeAttr[curOrge][DEFEND] >= hero.getAttack())
		{
			result = false;
		}
		else
		{
			calcBout();
			//���Ӣ��ɱ����ħ�Ļغ���С���ܹ���
			if (heroBout < orgeBout)
				result = true;
		}
		return result;
	}

	/**
	 * ��ȡս������
	 * @return ���飺<p>
	 * 0--Ӣ��ɱ����ħ��Ҫ�Ļغ�<p>
	 * 1--Ӣ�۵Ĺ�����<p>
	 * 2--��ħ�Ĺ�����
	 */
	public int[] getFightParam()
	{
		int[] result = new int[3];
		result[BOUT_NUM] = heroBout;
		result[HERO_DAMAGE_PER_BOUT] = heroDamagePerBout;
		result[ORGE_DAMAGE_PER_BOUT] = orgeDamagePerBout;
		return result;
	}

	/**
	 * ��ȡָ�����͵Ķ�ħ����
	 * @param type
	 * @return
	 */
	public String getOrgeName(int type)
	{
		return orgeName[calcType(type)];
	}


	/**
	 * ��ȡָ�����Ͷ�ħ����
	 * @param type
	 * @return
	 */
	public int[] getOrgeAttr(int type)
	{
		int[] result = new int[5];
		for (int i = 0; i < 5; i++)
		{
			result[i] = orgeAttr[calcType(type)][i];

		}
		return result;
	}


	/**
	 * ��ָ�����Ͷ�ħս����Ӣ�۵Ĺ�����
	 * @param type
	 * @return <p> return -1, ���ܴ�ܶ�ħ; <p> >0, ��ܶ�ħ��Ҫ�Ĺ�����
	 */
	public int getHeroDamage(int type)
	{
		int result = 0;
		curOrge = calcType(type);
		if (orgeAttr[curOrge][DEFEND] >= hero.getAttack())
		{
			result = -1;
		}
		else
		{
			calcBout();
			result = heroBout * heroDamagePerBout;
		}
		return result;
	}
}

