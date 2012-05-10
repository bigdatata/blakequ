package com.yarin.android.GameEngine.Screen;

import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.TiledLayer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.yarin.android.GameEngine.GameActivity;
import com.yarin.android.GameEngine.Screen.elements.biology.Actor;
import com.yarin.android.GameEngine.Screen.elements.biology.NPC;
//ͼ�������:�������ͼ
public class SimpleLayerManager extends LayerManager
{
	public SimpleLayerManager(){
		super();
	}
	
	/**
	 * �����ͼ
	 * @param level �ؿ�����
	 * @param actor ���Ƕ���
	 * @param mapID ��Ҫ����ĵ�ͼID
	 * @return �����ĵ�ͼ����
	 */
	public SimpleMap constructMap(
			SimpleLevel level,
			Actor actor,
			String mapID){
		SimpleMap map=level.findMap(mapID);
		if (map==null){
			System.out.println("�����ͼʧ��,ԭ����û���ҵ�ƥ��mapID�ĵ�ͼ����");
		}
		//��װ��NPC��Actor���������ǽ��������ͼ���ס
		Object[] npcSet=map.getNpcSet().list();
		for(int i=0;i<npcSet.length;i++){
			NPC npc=(NPC)npcSet[i];
			this.append(npc.getAnimator());
		}
		System.out.println("װ��NPC��ϣ�");
		
		this.append(actor.getAnimator());
		System.out.println("װ��Actor��ϣ�");
		
		Object[] layerSet=map.getLayerSet().list();
		//����SimpleLayerװ��tiledLayer
		for(int i=0;i<layerSet.length;i++){
			Bitmap img=null;
			SimpleLayer simpleLayer=(SimpleLayer)layerSet[i];
			try{
				img=BitmapFactory.decodeResource(GameActivity.mContext.getResources(), Integer.parseInt(simpleLayer.getImgURL()));
				//����TiledLayer
				TiledLayer layer=new TiledLayer(simpleLayer.getTileCols(),simpleLayer.getTileRows(),
						img,simpleLayer.getTileWidth(),simpleLayer.getTileHeight());
				//���ͼ������
				int[] mapData=simpleLayer.getMapData();
				//��������Ԫ�����ô�ͼ��ĵ�Ԫ��
				for(int j=0;j<mapData.length;j++){
					int columnNum=j%simpleLayer.getTileCols();
					int rowNum=(j-columnNum)/simpleLayer.getTileCols();
					layer.setCell(columnNum,rowNum,mapData[j]);
				}
				
				simpleLayer.setLayer(layer);
				this.append(layer);
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}	
		System.out.println("װ��ͼ����ϣ�");
		
		Object[] transformer=map.getMapLink().list();
		for(int i=0;i<transformer.length;i++){
			MapTransformer trans=(MapTransformer)transformer[i];
			this.append(trans.getSprite());
		}
		System.out.println("װ�ص�ͼת������ϣ�");
		return map;
	}
}

