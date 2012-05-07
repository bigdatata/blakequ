package wyf.wpf.role;				//声明包语句
/*
 * 该类代表足球运动员，其中主要封装了球员的一些信息
 * 另外还包括一个成员方法levelUp负责在玩家赢得一场
 * 比赛的胜利之后进行升级操作。
 */
public class Player{
	public int x;						//球员中心的x坐标
	public int y;						//球员中心的y坐标
	public int movingDirection=-1;		//运动员的运动方向，12左4右
	public int movingSpan = 1;			//移动步进
	public int attackDirection;		//进攻方向，0上8下
	public int power=10;				//踢球时给球的速度大小
	
	public void levelUp(){		//升级后调用
		movingSpan+=1;			//每次移动的步进增大
		if(movingSpan > 5){
			movingSpan = 5;
		}
	}
}