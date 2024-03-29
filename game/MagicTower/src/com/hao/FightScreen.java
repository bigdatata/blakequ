package com.hao;

import com.hao.util.TextUtil;
import com.hao.util.yarin;
import com.hao.view.GameScreen;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * 打斗的场景,以弹出形式显示出来
 * @author Administrator
 *
 */
public class FightScreen
{
	private Paint			paint		= null;
	
	private boolean isFighting = true;
	private GameScreen gameScreen;
	private boolean heroFirst;
	private Canvas mcanvas;
	private HeroSprite hero;
	
	//private int boutNum;
	private int orgeHp;							//恶魔血量
	private int orgeAttack;						//恶魔攻击力
	private int orgeDefend;						//恶魔防御力
	private int orgeMoney;						//恶魔的钱
	private int orgeExperience;					//恶魔经验值
	private int heroDamagePerBout;				//每回合英雄伤害值
	private int orgeDamagePerBout; 				//每回合恶魔伤害值
	
	//恶魔属性，位置和宽度，类型
	private int orgeSrcX,orgeSrcY,
				 w = GameMap.TILE_WIDTH;
	
	private int orgeType;
	private Bitmap orgeImage = null;
	private Bitmap heroImage = null;
	
	public TextUtil			tu=null;
	
	public FightScreen(GameScreen gameScreen, FightCalc fightCalc, HeroSprite hero, int type)
	{
		this.gameScreen = gameScreen;
		paint = new Paint();
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		tu = new TextUtil();

		orgeImage = gameScreen.getImage(GameScreen.IMAGE_MAP);
		heroImage = gameScreen.getImage(GameScreen.IMAGE_HERO);

		this.hero = hero;
		this.orgeType = type;

		int[] orgeAttr = fightCalc.getOrgeAttr(orgeType);
		orgeHp = orgeAttr[FightCalc.HP];
		orgeAttack = orgeAttr[FightCalc.ATTACK];
		orgeDefend = orgeAttr[FightCalc.DEFEND];
		orgeMoney = orgeAttr[FightCalc.MONEY];
		orgeExperience = orgeAttr[FightCalc.EXPERIENCE];

		int[] fightParam = fightCalc.getFightParam();
		heroDamagePerBout = fightParam[FightCalc.HERO_DAMAGE_PER_BOUT];
		orgeDamagePerBout = fightParam[FightCalc.ORGE_DAMAGE_PER_BOUT];

		int[] srcXY = calcXY(orgeType - 1);
		orgeSrcX = srcXY[0];
		orgeSrcY = srcXY[1];

	}


	/**
	 * 计算恶魔位置
	 * @param index
	 * @return
	 */
	private int[] calcXY(int index)
	{
		int[] result = { 0, 0 };
		int row = index / 11;
		int col = index - row * 11;
		result[0] = col * w;
		result[1] = row * w;
		return result;
	}


	/**
	 * 战斗时dialog绘制
	 * @param canvas
	 */
	public void onDraw(Canvas canvas)
	{
		mcanvas = canvas;

		int tx, ty, tw, th;
		tw = yarin.SCREENW;
		th = yarin.MessageBoxH;
		tx = 0;
		//文本居中
		ty = (yarin.SCREENH - yarin.MessageBoxH) / 2;

		showMessage();

		if (!isFighting)
		{
			tu.DrawText(mcanvas);
		}
		else
		{
			//先绘制双方头像
			yarin.drawImage(canvas, orgeImage, 0, ty + (th - GameMap.TILE_WIDTH) / 2, GameMap.TILE_WIDTH, GameMap.TILE_WIDTH, orgeSrcX, orgeSrcY);
			yarin.drawImage(canvas, heroImage, (tw - GameMap.TILE_WIDTH), ty + (th - GameMap.TILE_WIDTH) / 2, GameMap.TILE_WIDTH, GameMap.TILE_WIDTH, 0, 0);
			paint.setColor(Color.WHITE);
			// 怪物
			{
				tx = 40;
				ty = (yarin.SCREENH - yarin.MessageBoxH) / 2 + 5;
				yarin.drawString(canvas, "生命:" + orgeHp, tx, ty, paint);
				yarin.drawString(canvas, "攻击:" + orgeAttack, tx, ty + yarin.TextSize, paint);
				yarin.drawString(canvas, "防御:" + orgeDefend, tx, ty + 2 * yarin.TextSize, paint);
			}
			// 英雄
			{
				String string = "";
				ty = (yarin.SCREENH - yarin.MessageBoxH) / 2 + 5;
				string = hero.getHp() + ":生命";
				yarin.drawString(canvas, string, (tw - 40 - paint.measureText(string)), ty, paint);
				string = hero.getAttack() + ":攻击";
				yarin.drawString(canvas, string, (tw - 40 - paint.measureText(string)), ty + yarin.TextSize, paint);
				string = hero.getDefend() + ":防御";
				yarin.drawString(canvas, string, (tw - 40 - paint.measureText(string)), ty + 2 * yarin.TextSize, paint);
			}
		}

		tick();
	}


	/**
	 * 绘制对话框的外框体
	 */
	public void showMessage()
	{
		int x = 0;
		int y = (yarin.SCREENH - yarin.MessageBoxH) / 2;
		int w = yarin.SCREENW;
		int h = yarin.MessageBoxH;
		Paint ptmPaint = new Paint();
		ptmPaint.setARGB(255, 0, 0, 0);

		yarin.fillRect(mcanvas, x, y, w, h, ptmPaint);

		ptmPaint = null;
	}


	/**
	 * 战斗结束后显示的信息
	 */
	private void tick()
	{
		if (orgeHp <= 0)
		{
			isFighting = false;
			tu.InitText("得到" + orgeMoney + "个金币  " + "经验值增加" + orgeExperience, 0, (yarin.SCREENH - yarin.MessageBoxH) / 2, yarin.SCREENW, yarin.MessageBoxH,
				0x0, 0xff0000, 255, yarin.TextSize);
		}
		else if (heroFirst == true)
		{
			orgeHp -= orgeDamagePerBout;
			if (orgeHp <= 0)
			{
				orgeHp = 0;
			}
		}
		else
		{
			hero.cutHp(heroDamagePerBout);
		}
		heroFirst = !heroFirst;
	}


	public boolean onKeyUp(int keyCode)
	{
		switch (keyCode)
		{
			case yarin.KEY_DPAD_UP:
				break;
			case yarin.KEY_DPAD_DOWN:
				break;
			case yarin.KEY_DPAD_OK:
				if (!isFighting)
				{
					hero.addMoney(orgeMoney);
					hero.addExperience(orgeExperience);
					gameScreen.mshowFight = false;
					gameScreen.mFightScreen = null;
					System.gc();
				}
				break;
			case yarin.KEY_SOFT_RIGHT:
				break;
		}
		return true;
	}
}

