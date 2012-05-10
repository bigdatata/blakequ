package com.yarin.android.GameEngine.Screen;

import com.yarin.android.GameEngine.GameObjectQueue;
import com.yarin.android.GameEngine.Script.XmlScriptParser;

//��Ϸװ���������𽫹ؿ�(��ͼ��NPC����ͼת����������)�����ǡ��������װ���ڴ�
public class GameLoader
{
	//��������ģʽ���������еĶ���
	public static final int POSITIVE_LOAD_MODEL=1;
	//�������ģʽ��ֻ���ص�һ���ؿ��õ��Ķ��������ؿ��õĶ���û�м���ʱ�����أ������汾��δ֧��
	public static final int LAZY_LOAD_MODEL=2;
	//�ؿ��б�ÿ���ؿ����������ͼ��ÿ����ͼ�������ͼ�㡢���NPC�������ͼת������
	private GameObjectQueue globalLevelTable=null;
	//������б�
	private GameObjectQueue globalCameraTable=null;
	//�����б�
	private GameObjectQueue globalMusicTable=null;
	//�����б�
	private GameObjectQueue globalActorTable=null;
	//����cache�Ķ����б�-�¼��б�
	private GameObjectQueue eventTable=null;
	//����cache�Ķ����б�-��Ϣ�б�
	private GameObjectQueue msgTable=null;
	//����cache�Ķ����б�-�����б�
	private GameObjectQueue propertyTable=null;
	//����cache�Ķ����б�-NPC�б�
	private GameObjectQueue npcTable=null;
	//����cache�Ķ����б�-ͼ���б�
	private GameObjectQueue layerTable=null;
	//����cache�Ķ����б�-��ͼת�����б�
	private GameObjectQueue transformerTable=null;
	//����cache�Ķ����б�-��ͼ�б�
	private GameObjectQueue mapTable=null;
	//����cache�Ķ����б�-�ؿ��б�
	private GameObjectQueue levelTable=null;
	//���ֳ����м��
	private int carnieRunInterval=10;
	
	//����ģʽ
	private int type=0;
	
	//����Ŀǰ��֧�ֻ�������ģʽ�������ڹ��췽���н�type��ֵΪPOSITIVE_LOAD_MODEL
	public GameLoader(){
		type=POSITIVE_LOAD_MODEL;
	}
	/**
	 * �������ļ�ʹ�û�������ģʽװ��������Ϸ����
	 * @param gameConfigureResURL ��Ϸ������Դ·��
	 */
	public void load(String gameConfigureResURL){
		long startTime=System.currentTimeMillis();
		
		XmlScriptParser x=new XmlScriptParser();
		//��xml�����ļ�
		x.openConfigure(gameConfigureResURL);
		
		//װ����Ϣ����
		GameObjectQueue msgTable=x.readMsgConfigure(true);
		System.out.println("msg����="+msgTable.size());
		
		//װ����Ϣ���У�������Ϣ����������
		GameObjectQueue mqTable=x.readMsgQueueConfigure(new GameObjectQueue[]{msgTable},true);
		System.out.println("msg Queue����="+mqTable.size());
		
		//װ���¼���������
		GameObjectQueue eventTable=x.readEventConfigure(true);
		System.out.println("event����="+eventTable.size());
		
		//װ���¼��б������¼�����������
		GameObjectQueue eqTable=x.readEventQueueConfigure(new GameObjectQueue[]{eventTable},true);
		System.out.println("event Queue����="+eqTable.size());
		
		//װ�ص��߶���
		GameObjectQueue propTable=x.readPropertyConfigure(true);
		System.out.println("property����="+propTable.size());
		
		//װ�ص����䣬������߶���������
		GameObjectQueue propBoxTable=x.readPropertyBoxConfigure(new GameObjectQueue[]{propTable},true);
		System.out.println("propertyBox����="+propBoxTable.size());
		
		//װ��NPC���󣬹�������������¼�����
		GameObjectQueue npcTable=x.readNpcConfigure(new GameObjectQueue[]{propTable,eventTable},true);
		System.out.println("npc����="+npcTable.size());
		
		//װ�����ǣ��������������
		GameObjectQueue actorTable=x.readActorConfigure(new GameObjectQueue[]{propTable},true);
		System.out.println("actor����="+actorTable.size());
		
		//װ��ͼ�����
		GameObjectQueue layerTable=x.readLayerConfigure(true);
		System.out.println("layer����="+layerTable.size());
		
		//װ�ص�ͼת��������
		GameObjectQueue transformerTable=x.readTransformerConfigure(true);
		System.out.println("transformer����="+transformerTable.size());
		
		//װ�ص�ͼ���󣬹���NPC��ͼ�㡢��ͼת����
		GameObjectQueue mapTable=x.readMapConfigure(new GameObjectQueue[]{layerTable,npcTable,transformerTable},true);
		System.out.println("map����="+mapTable.size());
		
		//װ�عؿ����󣬲�������ͼ����
		GameObjectQueue levelTable=x.readLevelConfigure(new GameObjectQueue[]{mapTable},true);
		System.out.println("level����="+levelTable.size());
		
		//װ�����������
		GameObjectQueue cameraTable=x.readCameraConfigure(true);
		System.out.println("camera����="+cameraTable.size());
		
		//װ�����ֶ���
		GameObjectQueue musicTable=x.readMusicConfigure(true);
		System.out.println("music����="+musicTable.size());
		
		System.out.println("װ�ع�������ʱ��:"+(System.currentTimeMillis()-startTime)+" ms");
		
		this.globalActorTable=actorTable;
		this.globalCameraTable=cameraTable;
		this.globalMusicTable=musicTable;
		this.globalLevelTable=levelTable;
	}

	public GameObjectQueue getGlobalActorTable() {
		return globalActorTable;
	}

	public GameObjectQueue getGlobalCameraTable() {
		return globalCameraTable;
	}

	public GameObjectQueue getGlobalLevelTable() {
		return globalLevelTable;
	}

	public GameObjectQueue getGlobalMusicTable() {
		return globalMusicTable;
	}
	public int getType() {
		return type;
	}
	public void setGlobalActorTable(GameObjectQueue globalActorTable) {
		this.globalActorTable = globalActorTable;
	}
	public void setGlobalCameraTable(GameObjectQueue globalCameraTable) {
		this.globalCameraTable = globalCameraTable;
	}
	public void setGlobalLevelTable(GameObjectQueue globalLevelTable) {
		this.globalLevelTable = globalLevelTable;
	}
	public void setGlobalMusicTable(GameObjectQueue globalMusicTable) {
		this.globalMusicTable = globalMusicTable;
	}
	public GameObjectQueue getEventTable() {
		return eventTable;
	}
	public void setEventTable(GameObjectQueue eventTable) {
		this.eventTable = eventTable;
	}
	public GameObjectQueue getLayerTable() {
		return layerTable;
	}
	public void setLayerTable(GameObjectQueue layerTable) {
		this.layerTable = layerTable;
	}
	public GameObjectQueue getLevelTable() {
		return levelTable;
	}
	public void setLevelTable(GameObjectQueue levelTable) {
		this.levelTable = levelTable;
	}
	public GameObjectQueue getMapTable() {
		return mapTable;
	}
	public void setMapTable(GameObjectQueue mapTable) {
		this.mapTable = mapTable;
	}
	public GameObjectQueue getMsgTable() {
		return msgTable;
	}
	public void setMsgTable(GameObjectQueue msgTable) {
		this.msgTable = msgTable;
	}
	public GameObjectQueue getNpcTable() {
		return npcTable;
	}
	public void setNpcTable(GameObjectQueue npcTable) {
		this.npcTable = npcTable;
	}
	public GameObjectQueue getPropertyTable() {
		return propertyTable;
	}
	public void setPropertyTable(GameObjectQueue propertyTable) {
		this.propertyTable = propertyTable;
	}
	public GameObjectQueue getTransformerTable() {
		return transformerTable;
	}
	public void setTransformerTable(GameObjectQueue transformerTable) {
		this.transformerTable = transformerTable;
	}
	public int getCarnieRunInterval() {
		return carnieRunInterval;
	}
	public void setCarnieRunInterval(int carnieRunInterval) {
		this.carnieRunInterval = carnieRunInterval;
	}
}

