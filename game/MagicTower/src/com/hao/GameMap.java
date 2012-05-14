package com.hao;

import javax.microedition.lcdui.game.TiledLayer;

import com.hao.view.GameScreen;

import android.graphics.Bitmap;

public class GameMap
{
	/**
	 * 初始和结束时英雄位置，不同关卡在不同的位置
	 * 如第0关，地图为floorArray[0][0],英雄起始索引为115，结束时索引为16
	 * 这样判断英雄是否进入下一关
	 */
	private static final int[][] heroPosition = 
	{
		//begin position
		{115,104,11,111,109,111,109,115,11,39,70,111,119,111,104,3,5, 93, 111,119,39,60},
		//end position
		{16, 1,  99,109,99, 108,103,1,  51,83,99,119,111,114,5,  7,71,111,119,49, 71,60}	
	};
	
	/**
	 * 它是同一个恶魔的两个不同动作的图像在图片中的不同位置的差
	 * 从45开始的图元对象都有动作（45-80）
	 * 而他们的另个动作是从89-124(45-80都加上44)，即是一一对应的45-89...
	 * 从而能形成一个完整的动作
	 */
	public static final int			SWITCH_OFFSET	= 44;				
	public static final int			TILE_WIDTH		= 32;				//每个单元的宽
	public static final int			TILE_HEIGHT		= TILE_WIDTH;		//每个单元的高
	private static final int			TILE_NUM_COL	= 11;			//11个水平单元（11行）
	private static final int			TILE_NUM_ROW	= TILE_NUM_COL; //11个垂直单元（11列）
	public static final int			MAP_WIDTH		= TILE_WIDTH * TILE_NUM_COL;//地图宽
	public static final int			MAP_HEIGHT		= MAP_WIDTH;		//地图高
	public static final int			FLOOR_NUM		= 22;				
	public static final int			TILE_NUM		= TILE_NUM_COL * TILE_NUM_ROW;//总的单元数

	//表示英雄一关起始和结束（0:begin,1:end）
	private int						curUpDown		= 0;

	//当前的关卡地图数组
	private int[]						curFloorArray	= new int[TILE_NUM];
	public int							curFloorNum		= 0;	//当前关卡
	public int							reachedHighest	= 0;

	private TiledLayer					floorMap		= null;	//地图
	private HeroSprite					hero			= null;
	private int						aheadIndex		= 0;
	/**
	 * hero前方当前所在的行
	 */
	private int						aheadCol		= 0;
	/**
	 * hero前方当前所在的列
	 */
	private int						aheadRow		= 0;

	public GameMap(HeroSprite hero, Bitmap bmap)
	{
		this.hero = hero;
		floorMap = new TiledLayer(TILE_NUM_COL, TILE_NUM_ROW, bmap, TILE_WIDTH, TILE_HEIGHT);
		setMap(curFloorNum);
	}


	/**
	 * 设置指定关卡的地图(地图，和英雄位置)
	 * @param floorNum 关卡编号
	 */
	public void setMap(int floorNum)
	{
		for (int i = 0; i < TILE_NUM; i++)
		{
			curFloorArray[i] = floorArray[floorNum][i];
		}
		//根据行列下标设置地图
		for (int i = 0; i < TILE_NUM; i++)
		{
			int[] colrow = getColRow(i);
			//填充地图
			floorMap.setCell(colrow[0], colrow[1], floorArray[curFloorNum][i]);

		}
		//根据已知索引 获取英雄开始或者结束的行列位置
		int[] colrow = getColRow(heroPosition[curUpDown][floorNum]);
		int x = (int) (colrow[0] * TILE_WIDTH + TILE_WIDTH / 2);
		int y = (int) (colrow[1] * TILE_HEIGHT + TILE_HEIGHT / 2);
		//在地图上设置英雄的位置(绘制出来)
		hero.setRefPixelPosition(x, y);
	}


	/**
	 * 根据索引获取行列值
	 * @param index 当前数组中的索引
	 * @return 所在行列下表
	 */
	private int[] getColRow(int index)
	{
		int[] result = new int[2];
		//行
		result[0] = index % TILE_NUM_COL;
		//列
		result[1] = (index - result[0]) / TILE_NUM_ROW;
		return result;
	}


	/**
	 * 完成图元对象的动画效果
	 * 在该游戏中的动画只有两张图片，然后动画效果就是不断的在两张图片进行切换
	 * 装换就是如果图片1，则装换为和他相同的另张图片2，如果是2则装换为1
	 * 1<->2不断切换完成动画
	 */
	public void animateMap()
	{
		int switchedCell;
		//遍历整个地图单元
		for (int i = 0; i < TILE_NUM; i++)
		{
			switchedCell = 0;
			int type = floorArray[curFloorNum][i];
			//MAP_SHOP1<-->MAP_SHOP2
			if (type == MAP_SHOP1)
			{
				switchedCell = MAP_SHOP2;
			}
			else if (type == MAP_SHOP2)
			{
				switchedCell = MAP_SHOP1;
			}
			//MAP_STAR1<-->MAP_STAR2
			else if (type == MAP_STAR1)
			{
				switchedCell = MAP_STAR2;
			}
			else if (type == MAP_STAR2)
			{
				switchedCell = MAP_STAR1;
			}
			//MAP_WATER1<-->MAP_WATER2
			else if (type == MAP_WATER1)
			{
				switchedCell = MAP_WATER2;
			}
			else if (type == MAP_WATER2)
			{
				switchedCell = MAP_WATER1;
			}
			//45-80这些的动画切换
			else if ((type >= MAP_ANGLE) && (type <= MAP_ORGE31))
			{
				switchedCell = type + SWITCH_OFFSET;
			}
			else if (type > MAP_ORGE31)
			{
				switchedCell = type - SWITCH_OFFSET;
			}
			if (switchedCell != 0)
			{
				changeCell(i, switchedCell);
			}
		}
	}

	/**
	 * 获取当前地图
	 * @return
	 */
	public TiledLayer getFloorMap()
	{
		return floorMap;
	}

	/**
	 * 下一个关卡
	 * @return
	 */
	public int upstair()
	{
		if ((curFloorNum + 1) < FLOOR_NUM)
		{
			curUpDown = 0;//初始下一关英雄位置
			setMap(++curFloorNum);
			if (curFloorNum > reachedHighest)
				reachedHighest = curFloorNum;
		}
		return curFloorNum;
	}

	/**
	 * 上一关卡
	 * @return
	 */
	public int downstair()
	{
		if ((curFloorNum - 1) >= 0)
		{
			curUpDown = 1;
			setMap(--curFloorNum);
		}
		return curFloorNum;
	}

	/**
	 * 检测hero前方当前物品，主要是通过检测该单元是否是门，墙, 物品等
	 * @param direction
	 * @return 物体类型编号（可以对照最下面定义的静态变量确定类型）
	 */
	public int canPass(int direction)
	{
		int col = hero.getRefPixelX() / TILE_WIDTH;
		int row = hero.getRefPixelY() / TILE_HEIGHT;
		int result;
		boolean isBound = true;
		switch (direction)
		{
			case GameScreen.UP://检测上面
				if (row - 1 >= 0)
				{
					row--;
					isBound = false;
				}
				break;
			case GameScreen.DOWN://下面
				if (row + 1 < TILE_NUM_ROW)
				{
					row++;
					isBound = false;
				}
				break;
			case GameScreen.LEFT://左边
				if (col - 1 >= 0)
				{
					col--;
					isBound = false;
				}
				break;
			case GameScreen.RIGHT://右边
				if (col + 1 < TILE_NUM_COL)
				{
					col++;
					isBound = false;
				}
				break;
		}
		if (isBound == true)
		{
			result = 0;
		}
		//根据行列值判断当前单元cell是那种类型
		else
		{
			aheadCol = col;
			aheadRow = row;
			aheadIndex = TILE_NUM_ROW * row + col;
			//当前的单元所在的物体编号
			result = floorArray[curFloorNum][aheadIndex];
		}
		return result;
	}


	/**
	 * 改变当前floorArray[curFloorNum][index]的单元类型
	 * @param index 改变单元的索引
	 * @param type 要改变的类型
	 */
	public void changeCell(int index, int type)
	{
		int[] colrow = getColRow(index);
		//改变地图数组
		floorArray[curFloorNum][index] = type;
		//改变地图
		floorMap.setCell(colrow[0], colrow[1], type);
	}


	/**
	 * 设置地图指定位置（当前hero的正前方的单元位置）为道路（）
	 * floorArray[floor][aheadIndex]的类型为1(即道路)
	 * 如捡到某物品，然后将该地图块设置为道路（捡到物品）
	 */
	public void remove()
	{
		//设置单元数组
		floorArray[curFloorNum][aheadIndex] = 1;
		//重新设置该单元为道路
		floorMap.setCell(aheadCol, aheadRow, 1);
	}

	/**
	 * 设置地图指定位置floorArray[floor][index]的类型为1(即道路)
	 * @param floor
	 * @param index
	 */
	public void remove(int floor, int index)
	{
		floorArray[floor][index] = 1;
	}

	/**
	 * 设置地图指定位置floorArray[floor][index]的类型为type
	 * @param floor	关卡
	 * @param index 关卡具体位置
	 * @param type	指定替换的类型
	 */
	public void remove(int floor, int index, int type)
	{
		floorArray[floor][index] = type;
	}

	/**
	 * 跳转到指定关卡
	 * @param floor
	 */
	public void jump(int floor)
	{
		//小于最高的关卡
		if (reachedHighest >= floor)
		{
			//如果大于当前关卡--往前跳
			if (floor >= curFloorNum)
				curUpDown = 0;	//到关卡的起始位置
			//往回走--往后跳，就调到floor的结束位置
			else
				curUpDown = 1;	//到该关结束位置
			curFloorNum = floor;
			setMap(curFloorNum);
		}
	}

	/**
	 * 获取恶魔数组
	 * @return
	 */
	public int[] getOrgeArray()
	{
		int[] result = new int[TILE_NUM];
		int type, num = 0;
		boolean repeated = false;
		//遍历整个地图查询是否是恶魔(编号在50-80)
		for (int i = 0; i < TILE_NUM; i++)
		{
			repeated = false;
			if ((type = floorArray[curFloorNum][i]) >= FightCalc.MIN_ORGE_INDEX)
			{
				//如果大于80说明是图元的另一个对象，把它装换到0-80的范围，减去45即可
				if (type > FightCalc.MAX_ORGE_INDEX)
					type -= SWITCH_OFFSET;
				//小于50不是恶魔
				if (type < MAP_ORGE1)
					continue;
				//检查是否已经存在和type同类的恶魔，是则repeated=true
				for (int j = 0; j < num; j++)
				{
					if (result[j] == type)
					{
						repeated = true;
						break;
					}
				}
				//如果不存在同类，则添加到数组
				if (repeated == false)
				{
					result[num] = type;
					num++;
				}
			}

		}
		result[TILE_NUM - 1] = num;
		return result;
	}


	/**
	 * 获取某关卡的数据
	 * @param floor 要获取的关卡编号
	 * @return
	 */
	public byte[] getFloorArray(int floor)
	{
		byte[] result = new byte[TILE_NUM];
		int type;
		for (int i = 0; i < TILE_NUM; i++)
		{
			type = floorArray[floor][i];
			if (type > FightCalc.MAX_ORGE_INDEX)
				type -= SWITCH_OFFSET;
			result[i] = (byte) type;
		}
		return result;
	}


	/**
	 * 设置指定关卡的地图数组
	 * @param floor	哪个关卡
	 * @param data	当前关卡的数据
	 */
	public void setFloorArray(int floor, byte[] data)
	{
		for (int i = 0; i < TILE_NUM; i++)
		{
			floorArray[floor][i] = data[i];
		}
	}
	/**
	 * 每个关卡地图元素数组(每个数字代表不同的类型如墙，道路等)
	 * 关卡设置，这里是每个关卡的地图设置，数字代表的是显示的类型
	 * 譬如或这里：2表示墙，1表示路，13表示门
	 */
	private int[][] floorArray =
	{
	//floor0
	{
		2,6,6,6,6,11,6,6,6,6,2,
		2,6,6,6,6,1,6,6,6,6,2,
		2,6,6,6,6,1,6,6,6,6,2,
		2,6,6,6,6,1,6,6,6,6,2,
		2,6,6,6,6,1,6,6,6,6,2,
		2,6,6,6,6,1,6,6,6,6,2,
		2,2,6,6,6,1,6,6,6,2,2,
		2,2,2,2,2,13,2,2,2,2,2,
		8,2,8,2,1,45,1,2,8,2,8,
		8,8,8,8,8,1,8,8,8,8,8,
		8,8,8,8,8,1,8,8,8,8,8
	},
	//floor1 
	{
		11,1,19,50,51,50,1,1,1,1,1,
		2,2,2,2,2,2,2,2,2,2,1,
		23,1,53,13,1,2,23,19,23,2,1,
		19,53,25,2,1,2,23,19,23,2,1,
		2,13,2,2,1,2,2,2,54,2,1,
		19,55,1,2,1,13,56,50,52,2,1,
		26,1,20,2,1,2,2,2,2,2,1,
		2,13,2,2,1,1,1,1,1,1,1,
		1,55,1,2,2,15,2,2,2,13,2,
		23,24,19,2,21,1,1,2,19,58,20,
		23,36,19,2,1,12,1,2,19,19,19,		
	},
	//floor2
	{
		12,2,1,74,1,2,25,26,19,21,2,
		1,2,26,2,24,2,25,26,19,20,2,
		1,2,19,2,19,2,25,26,19,67,2,
		1,2,19,2,19,2,2,2,2,13,2,
		1,2,1,2,1,1,1,13,1,1,2,
		1,2,13,2,2,13,2,2,13,2,2,
		1,5,1,1,1,1,2,1,67,1,2,
		1,2,13,2,2,14,2,16,2,16,2,
		1,2,19,2,24,23,2,1,2,1,2,
		1,2,19,2,24,23,2,1,2,1,2,
		11,2,25,2,24,23,2,48,2,49,2
	},
	//floor3
	{
		27,51,19,2,3,17,4,2,2,2,2,
		51,19,1,2,1,1,1,2,1,52,1,
		19,53,1,2,2,13,2,2,1,2,1,
		2,13,2,2,1,53,1,2,19,2,51,
		1,1,1,2,2,2,1,2,19,2,52,
		50,2,1,52,51,52,1,2,19,2,51,
		50,2,2,2,2,2,1,1,1,2,1,
		1,1,1,1,1,2,2,13,2,2,1,
		2,2,2,2,52,2,51,1,51,2,1,
		2,1,1,1,1,2,26,52,19,2,1,
		12,1,2,2,2,2,25,24,19,2,11
	},
	//floor4
	{
		1,1,1,2,1,46,1,2,1,54,1,
		13,2,13,2,1,1,1,2,13,2,13,
		1,2,1,2,2,16,2,2,1,2,1,
		1,2,53,2,57,63,57,2,53,2,1,
		52,2,23,2,26,57,26,2,23,2,52,
		52,2,23,2,2,15,2,2,23,2,52,
		51,2,1,2,58,62,58,2,1,2,51,
		1,2,1,2,25,58,25,2,1,2,1,
		1,2,1,2,2,14,2,2,1,2,1,
		1,2,1,2,19,1,19,2,1,2,1,
		11,2,1,1,54,1,54,1,1,2,12
	},
	//floor5
	{
		22,2,23,2,24,56,1,1,56,19,20,
		1,2,25,2,56,1,1,1,1,56,19,
		57,2,1,2,55,1,2,2,13,2,2,
		1,13,1,2,32,55,2,1,58,55,49,
		57,2,1,2,2,2,2,1,1,1,55,
		25,2,1,1,1,52,53,1,1,1,1,
		26,2,2,54,2,2,2,2,1,1,1,
		1,48,2,54,2,1,1,1,58,62,1,
		2,2,2,52,2,13,2,14,2,13,2,
		1,1,2,1,2,52,2,26,13,1,2,
		12,1,52,1,1,1,2,19,2,11,2
	},
	//floor6
	{
		38,59,2,26,2,19,65,40,2,24,24,
		59,19,2,25,2,1,19,65,2,60,24,
		19,63,14,1,14,63,1,19,2,1,60,
		1,1,2,62,2,1,1,1,2,69,1,
		2,2,2,15,2,2,2,2,2,13,2,
		1,1,64,1,19,19,19,1,64,1,1,
		1,2,2,2,2,2,2,2,2,2,2,
		1,2,57,13,57,1,1,1,1,1,1,
		1,2,13,2,13,2,2,2,2,2,14,
		1,2,57,2,1,1,2,2,1,1,1,
		1,1,1,2,11,1,13,13,1,1,12
	},
	//floor7
	{
		11,1,1,1,1,1,1,1,2,2,2,
		2,2,1,63,2,14,2,59,1,2,2,
		2,1,63,26,2,66,2,25,59,1,2,
		1,1,2,2,2,10,2,2,2,1,1,
		1,1,14,66,16,42,10,66,14,1,1,
		1,2,2,2,2,10,2,2,2,2,1,
		1,2,23,25,2,66,2,26,23,2,1,
		1,2,19,23,2,14,2,23,19,2,1,
		1,2,2,20,20,24,20,20,2,2,1,
		1,1,2,2,2,15,2,2,2,1,1,
		2,1,1,13,12,1,1,13,1,1,2
	},
	//floor8
	{
		12,2,1,1,1,1,2,1,19,59,1,
		1,2,1,2,2,13,2,13,2,2,1,
		1,2,1,2,1,1,14,1,1,2,25,
		1,2,1,2,61,2,2,2,57,2,54,
		57,2,1,2,23,2,11,1,1,2,54,
		63,2,26,2,23,2,2,2,2,2,1,
		57,2,54,2,1,1,1,2,1,63,1,
		1,2,54,2,2,2,62,2,13,2,2,
		1,2,1,59,1,2,59,2,1,1,1,
		1,2,2,2,13,2,1,2,2,2,1,
		1,1,61,1,1,2,1,65,66,65,1
	},
	//floor9
	{
		37,19,1,2,2,2,1,1,1,2,1,
		19,1,69,13,1,1,1,2,1,13,59,
		2,13,2,2,1,2,2,2,1,2,19,
		1,1,1,2,1,2,1,1,1,2,19,
		1,1,1,2,1,2,12,2,1,2,23,
		2,14,2,2,1,2,2,2,1,2,2,
		26,68,25,2,61,2,11,2,1,2,23,
		2,13,2,2,1,1,1,13,1,2,19,
		59,23,59,2,2,14,2,2,1,2,19,
		20,59,23,2,60,61,60,2,1,13,59,
		28,20,59,13,24,60,24,2,1,2,1
	},
	//floor10
	{
		1,2,2,26,69,2,69,25,2,2,1,
		1,1,2,2,13,2,13,2,2,1,60,
		1,1,1,1,1,2,1,1,1,60,24,
		1,2,1,2,2,2,2,2,1,2,2,
		57,2,1,1,19,19,19,1,1,2,19,
		63,2,1,2,2,2,2,13,2,2,19,
		57,2,1,16,1,12,2,1,13,63,1,
		1,2,2,2,2,2,2,13,2,2,1,
		1,2,23,26,25,2,1,63,1,2,19,
		1,2,23,26,25,15,61,2,61,2,19,
		11,2,23,26,25,2,20,2,20,2,23
	},
	//floor11
	{
		23,2,19,2,20,2,21,2,24,33,24,
		23,2,19,2,20,2,21,2,62,62,62,
		23,2,19,2,20,2,21,2,1,62,1,
		13,2,13,2,13,2,13,2,2,14,2,
		1,1,1,1,1,2,1,1,1,1,1,
		13,2,2,14,2,2,2,14,2,2,13,
		26,2,1,70,24,72,24,70,1,2,25,
		26,2,69,2,2,2,2,2,69,2,25,
		26,2,69,2,3,17,4,2,69,2,25,
		2,2,15,2,23,1,23,2,15,2,2,
		12,1,1,1,1,1,1,1,1,1,11
	},
	//floor12
	{
		49,26,2,1,67,74,67,1,2,24,43,
		25,1,2,1,2,13,2,1,2,1,24,
		1,1,2,1,2,74,2,1,2,1,1,
		1,72,2,1,2,19,2,1,2,77,1,
		72,73,2,1,2,19,2,1,2,75,77,
		2,14,2,1,2,23,2,1,2,14,2,
		1,1,1,1,2,23,2,1,1,1,1,
		2,2,2,1,2,2,2,1,2,2,2,
		26,72,13,70,70,71,70,70,13,72,25,
		2,2,2,2,2,14,2,2,2,2,2,
		11,1,1,1,1,1,1,1,1,1,12
	},
	//floor13
	{
		1,72,1,1,1,1,1,2,1,73,1,
		1,2,2,2,2,2,13,2,1,2,1,
		1,2,1,1,70,1,1,2,1,2,1,
		24,2,15,2,2,2,1,2,1,2,1,
		67,2,1,1,73,2,70,2,25,2,1,
		74,2,1,76,10,2,71,2,25,2,1,
		67,2,73,16,48,2,70,2,25,2,26,
		1,2,2,2,2,2,1,2,1,2,26,
		1,67,1,2,1,1,1,73,1,2,26,
		2,2,1,2,24,2,2,2,2,2,1,
		12,1,1,14,1,11,2,39,76,13,1
	},
	//floor14
	{
		2,1,77,22,11,1,1,1,1,1,2,
		2,1,24,2,2,2,2,2,24,1,2,
		2,1,2,2,2,2,2,2,2,1,2,
		2,1,2,2,2,41,2,2,2,1,2,
		2,1,2,2,2,16,2,2,2,1,2,
		2,1,23,2,2,73,2,2,23,1,2,
		2,1,6,6,2,76,2,6,6,1,2,
		2,1,6,6,2,73,2,6,6,1,2,
		2,1,6,6,2,14,2,6,6,1,2,
		2,70,71,70,14,1,14,70,71,70,2,
		2,2,2,2,2,12,2,2,2,2,2
	},
	//floor15
	{
		1,1,1,1,12,6,11,1,1,1,1,
		1,6,6,6,6,6,6,6,6,6,1,
		1,6,6,2,2,2,2,2,6,6,1,
		1,6,2,2,48,2,49,2,2,6,1,
		1,6,2,2,26,2,26,2,2,6,1,
		1,6,2,2,25,2,25,2,2,6,1,
		1,6,6,2,1,2,1,2,6,6,1,
		1,6,6,2,13,2,13,2,6,6,1,
		1,6,6,6,1,1,1,6,6,6,1,
		1,6,6,6,6,15,6,6,6,6,1,
		1,1,1,1,1,1,1,1,1,1,1
	},
	//floor16
	{
		6,6,6,6,6,1,12,6,6,6,6,
		6,6,6,6,6,1,6,6,6,6,6,
		6,6,6,6,6,1,6,6,6,6,6,
		6,6,6,6,2,15,2,6,6,6,6,
		6,6,6,2,2,1,2,2,6,6,6,
		6,6,6,2,2,79,2,2,6,6,6,
		6,6,6,2,2,1,2,2,6,6,6,
		6,6,6,2,2,11,2,2,6,6,6,
		6,6,6,6,2,2,2,6,6,6,6,
		6,6,6,6,6,6,6,6,6,6,6,
		6,6,6,6,6,6,6,6,6,6,6
	},
	//floor17
	{
		6,77,76,1,1,1,1,1,1,1,77,
		6,76,6,6,6,6,6,6,6,6,1,
		6,1,6,77,1,1,1,1,1,1,77,
		6,1,6,1,6,6,6,6,6,6,6,
		6,1,6,1,6,77,1,1,1,77,6,
		6,1,6,77,1,1,6,6,6,1,6,
		6,1,6,6,6,6,6,77,1,77,6,
		6,76,6,6,6,12,6,1,6,6,6,
		6,77,76,1,78,1,6,77,1,1,77,
		6,6,6,6,6,6,6,6,6,6,1,
		11,1,78,1,1,1,1,1,1,1,77
	},
	//floor18
	{
		6,6,6,6,6,6,6,6,6,6,6,
		6,6,6,6,6,6,6,6,6,6,6,
		6,6,6,6,6,6,6,6,6,6,6,
		6,6,6,6,2,2,2,6,6,6,6,
		6,6,6,2,2,2,2,2,6,6,6,
		6,6,6,2,2,2,2,2,6,6,6,
		6,6,6,2,2,2,2,2,6,6,6,
		6,6,6,6,2,2,2,6,6,6,6,
		6,6,6,6,6,1,6,6,6,6,6,
		6,6,6,6,6,1,6,6,6,6,6,
		12,1,1,1,1,1,1,1,1,1,1
	},
	//floor19
	{
		1,1,1,1,1,1,1,1,1,1,1,
		1,6,1,6,6,6,6,6,1,6,1,
		1,6,1,6,6,6,6,6,1,6,1,
		1,6,1,6,6,11,6,6,1,6,1,
		1,6,1,6,6,1,6,6,1,6,1,
		1,6,79,6,6,1,6,6,79,6,1,
		1,6,16,6,6,80,6,6,16,6,1,
		1,6,31,6,6,1,6,6,35,6,1,
		1,6,6,6,6,1,6,6,6,6,1,
		1,6,6,6,6,1,6,6,6,6,1,
		1,1,1,1,1,1,1,1,1,1,12
	},
	//floor20
	{
		78,25,66,23,76,21,76,23,66,25,78,
		24,6,19,6,20,6,20,6,19,6,24,
		6,26,66,1,77,1,77,1,66,26,6,
		23,6,19,6,1,12,1,6,19,6,23,
		76,20,77,1,1,1,1,1,77,20,76,
		21,6,6,6,1,6,1,6,6,6,21,
		76,20,77,1,1,1,1,1,77,20,76,
		23,6,19,6,1,11,1,6,19,6,23,
		6,26,66,1,77,1,77,1,66,26,6,
		24,6,19,6,20,6,20,6,19,6,24,
		78,25,66,23,76,21,76,23,66,25,78
	},
	//floor21
	{
		6,6,6,6,6,6,6,6,6,6,6,
		6,6,1,1,6,80,6,1,1,6,6,
		6,1,1,6,6,75,6,6,1,1,6,
		6,1,1,1,6,75,6,1,1,1,6,
		6,6,1,1,1,1,1,1,1,6,6,
		6,6,1,1,1,1,1,1,1,6,6,
		6,6,6,1,1,6,1,1,6,6,6,
		6,6,6,6,10,12,10,6,6,6,6,
		6,6,6,6,6,6,6,6,6,6,6,
		6,6,6,6,6,6,6,6,6,6,6,
		6,6,6,6,6,6,6,6,6,6,6
	}
	};

	/**
	 * 具体每个数字所表示的含义,然后上面的关卡就是依据下面的
	 * 注：在编号大于80（从89-124）的图像都是和45-80的图像一样的
	 * 是属于同一个对象动作不同，目的是为了实现动画的效果！（他们的差是44，即45所对应的另一个动作编号是45+44=89）
	 */
	public static final int MAP_ROAD = 1,
							MAP_WALL = 2,
							MAP_LEFT_SHOP = 3,
							MAP_RIGHT_SHOP = 4,
							MAP_LOCKED_DOOR = 5,
							MAP_STAR1 = 6,
							MAP_STAR2 = 7,
							MAP_WATER1 = 8,
							MAP_WATER2 = 9,
							MAP_LOCKED_BARRIER = 10,
							MAP_UPSTAIR = 11,
							MAP_DOWNSTAIR = 12,
							MAP_YELLOW_DOOR = 13,
							MAP_BLUE_DOOR = 14,
							MAP_RED_DOOR = 15,
							MAP_BARRIER = 16,
							MAP_SHOP1 = 17,
							MAP_SHOP2 = 18,
							//
							MAP_YELLOW_KEY = 19,
							MAP_BLUE_KEY = 20,
							MAP_RED_KEY = 21,
							MAP_KEY_RING = 22,
							MAP_SMALL_BLOOD = 23,
							MAP_BIG_BLOOD = 24,
							MAP_RED_GEM = 25,
							MAP_BLUE_GEM = 26,
							MAP_SWORD1 = 27,
							MAP_SWORD2 = 28,
							MAP_SWORD3 = 29,
							MAP_SWORD4 = 30,
							MAP_SWORD5 = 31,
							MAP_SHIELD1 = 32,
							MAP_SHIELD2 = 33,
							MAP_SHIELD4 = 34,//3
							MAP_SHIELD5 = 35,//4
							MAP_LOOKUP = 36,
							MAP_JUMP = 37,
							MAP_LEVEL_UP1 = 38,
							MAP_LEVEL_UP3 = 39,
							MAP_COIN = 40,
							MAP_DOUBLE_BLOOD = 41,
							MAP_CROSS = 42,
							MAP_AX = 43,
							MAP_SHIELD3 = 44,//5
							//人物角色在地图的编号
							MAP_ANGLE = 45,
							MAP_THIEF = 46,
							MAP_PRINCESS = 47,
							MAP_BLUE_GEEZER = 48,
							MAP_RED_GEEZER = 49,
							//不同恶魔的编号从50-80都是
							MAP_ORGE1 = 50,
							MAP_ORGE2 = 51,
							MAP_ORGE3 = 52,
							MAP_ORGE4 = 53,
							MAP_ORGE5 = 54,
							MAP_ORGE6 = 55,
							MAP_ORGE7 = 56,
							MAP_ORGE8 = 57,
							MAP_ORGE9 = 58,
							MAP_ORGE10 = 59,
							MAP_ORGE11 = 60,
							MAP_ORGE12 = 61,
							MAP_ORGE13 = 62,
							MAP_ORGE14 = 63,
							MAP_ORGE15 = 64,
							MAP_ORGE16 = 65,
							MAP_ORGE17 = 66,
							MAP_ORGE18 = 67,
							MAP_ORGE19 = 68,
							MAP_ORGE20 = 69,
							MAP_ORGE21 = 70,
							MAP_ORGE22 = 71,
							MAP_ORGE23 = 72,
							MAP_ORGE24 = 73,
							MAP_ORGE25 = 74,
							MAP_ORGE26 = 75,
							MAP_ORGE27 = 76,
							MAP_ORGE28 = 77,
							MAP_ORGE29 = 78,
							MAP_ORGE30 = 79,
							MAP_ORGE31 = 80;
}

