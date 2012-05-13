package com.hao;

/**
 * 英雄与恶魔战斗参数及计算(战斗回合等)
 * @author Administrator
 *
 */
public class FightCalc
{
	public static final int MIN_ORGE_INDEX = 50;
	public static final int MAX_ORGE_INDEX = 80;

	private static final int OFFSET = 50;
	//恶魔属性,不同级别的恶魔有不同的属性（31种对应orgeName中的不同名字的恶魔）
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
	//基本属性常量（对应上面orgeAttr的每行类型）
	public static final int HP = 0,
							 ATTACK = 1,
							 DEFEND = 2,
							 MONEY = 3,
							 EXPERIENCE = 4;
	//回合常量
	public static final int BOUT_NUM = 0,
							HERO_DAMAGE_PER_BOUT = 1,
							ORGE_DAMAGE_PER_BOUT = 2;
	//所有类型的恶魔
	private static final String[] orgeName = 
	{
		"绿头怪 ", "红头怪 ","小蝙蝠 ", "骷髅人 ","青头怪","骷髅士兵",
		"初级法师", "大蝙蝠 ", "兽面人 ", "骷髅队长", "石头怪人", "麻衣法师",
		"初级卫兵", "红蝙蝠 ", "高级法师", "怪王", "白衣武士", "金甲卫士", 
		"红衣法师", "兽面武士", "冥卫兵 ", "高级卫兵", "双手剑士", "冥战士 ", 
		"金甲队长", "灵法师 ", "冥队长 ", "灵武士 ", "影子战士", "红衣魔王", "冥灵魔王"
	};
	
	private HeroSprite					hero;		//英雄
	private int						curOrge;		//当前恶魔
	private int						heroDamagePerBout, orgeDamagePerBout;	//每回合攻击力（让对方损失的血量）
	private int						heroBout;		//英雄要杀死当前恶魔需要的战斗回合数
	private int						orgeBout;		//当前恶魔要杀死当前英雄需要战斗的回合数


	public FightCalc(HeroSprite hero)
	{
		this.hero = hero;
	}


	/**
	 * 获取当前恶魔类型
	 * @param type
	 * @return
	 */
	private int calcType(int type)
	{
		return type - OFFSET;
	}


	/**
	 * 计算英雄和当前恶魔的战斗回合
	 */
	private void calcBout()
	{
		heroDamagePerBout = orgeAttr[curOrge][ATTACK] - hero.getDefend();	//英雄每回合的攻击力=恶魔的攻击力-英雄的防御力
		orgeDamagePerBout = hero.getAttack() - orgeAttr[curOrge][DEFEND];
		//计算heroBout意思就是只需要heroBout回合就能将恶魔杀死
		heroBout = orgeAttr[curOrge][HP] / orgeDamagePerBout;				//英雄与当前恶魔战斗回合数=恶魔的血量/恶魔每回合的损失血量
		//这个为了防止计算出来的heroBout为整形，如50/8=6,而实际上要7个回合才能杀死
		//故而需要用50-6*8=2>0如果成立则在将herBout加1
		if ((orgeAttr[curOrge][HP] - orgeDamagePerBout * heroBout) > 0)
			heroBout++;
		//恶魔的攻击力太小，小于英雄防御力，不能对英雄造成损伤
		if (heroDamagePerBout <= 0)
		{
			heroDamagePerBout = 0;
			orgeBout = Integer.MAX_VALUE;		//因为对恶魔不构成威胁，故而恶魔的回合数设为最大，即永远不能杀死英雄
		}
		//恶魔的攻击力能使英雄损失血量
		else
		{
			//恶魔要杀死英雄需要进行的回合数
			orgeBout = hero.getHp() / heroDamagePerBout;
			
			if ((hero.getHp() - heroDamagePerBout * orgeBout) > 0)
				orgeBout++;
		}
	}


	/**
	 * 看英雄是否能攻击恶魔
	 * @param type
	 * @return
	 */
	public boolean canAttack(int type)
	{
		boolean result = false;
		curOrge = calcType(type);
		//防御大于英雄的攻击力
		if (orgeAttr[curOrge][DEFEND] >= hero.getAttack())
		{
			result = false;
		}
		else
		{
			calcBout();
			//如果英雄杀死恶魔的回合数小就能攻击
			if (heroBout < orgeBout)
				result = true;
		}
		return result;
	}

	/**
	 * 获取战斗参数
	 * @return 数组：<p>
	 * 0--英雄杀死恶魔需要的回合<p>
	 * 1--英雄的攻击力<p>
	 * 2--恶魔的攻击力
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
	 * 获取指定类型的恶魔名字
	 * @param type
	 * @return
	 */
	public String getOrgeName(int type)
	{
		return orgeName[calcType(type)];
	}


	/**
	 * 获取指定类型恶魔参数
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
	 * 与指定类型恶魔战斗中英雄的攻击力
	 * @param type
	 * @return <p> return -1, 不能打败恶魔; <p> >0, 打败恶魔需要的攻击力
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

