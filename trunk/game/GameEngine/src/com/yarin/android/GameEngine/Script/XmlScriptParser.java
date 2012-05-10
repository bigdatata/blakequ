package com.yarin.android.GameEngine.Script;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import com.yarin.android.GameEngine.GameObject;
import com.yarin.android.GameEngine.GameObjectQueue;
import com.yarin.android.GameEngine.Events.Event;
import com.yarin.android.GameEngine.Events.EventQueue;
import com.yarin.android.GameEngine.Events.Message;
import com.yarin.android.GameEngine.Events.MessageQueue;
import com.yarin.android.GameEngine.Music.Music;
import com.yarin.android.GameEngine.Music.Musician;
import com.yarin.android.GameEngine.Screen.Camera;
import com.yarin.android.GameEngine.Screen.MapTransformer;
import com.yarin.android.GameEngine.Screen.SimpleLayer;
import com.yarin.android.GameEngine.Screen.SimpleLevel;
import com.yarin.android.GameEngine.Screen.SimpleMap;
import com.yarin.android.GameEngine.Screen.elements.Property.Property;
import com.yarin.android.GameEngine.Screen.elements.Property.PropertyManager;
import com.yarin.android.GameEngine.Screen.elements.biology.Actor;
import com.yarin.android.GameEngine.Screen.elements.biology.NPC;
import com.yarin.android.GameEngine.Util.StringExtension;

//xml�ű�������
public class XmlScriptParser
{
	private XmlReader xmlReader=null;
	
	//xml�ļ��е�Tag������REF��׺��ʾ������һ��Ԫ�ص�����
	//��ϢԪ����
	private String msgName="msg";
	//��ϢԪ������
	private String[] msgAttributes={"id","content"};
	
	//��Ϣ����Ԫ����
	private String msgQueueName="msgQueue";
	//��Ϣ����Ԫ������
	private String[] msgQueueAttributes={"id","msgsREF"};
	
	//�¼�Ԫ����
	private String eventName="event";
	//�¼�Ԫ������
	private String[] eventAttributes={"id","invoker",
			"responser","type","parameter"};
	//�¼�����Ԫ����
	private String eventQueueName="eventQueue";
	//�¼�����Ԫ������
	private String[] eventQueueAttributes={"id","eventsREF"};
	
	//����Ԫ����
	private String propertyName="property";
	//����Ԫ������
	private String[] propertyAttributes={"id","name",
			"description","buyPrice","salePrice",
			"lifeEffect","attackEffect","defenceEffect",
			"useTimes","type"};
	
	//������Ԫ����
	private String propBoxName="propertyBox";
	//������Ԫ������
	private String[] propBoxAttributes={"id","propsREF"};
	
	//NPCԪ����
	private String npcName="npc";
	//NPCԪ������
	private String[] npcAttributes={"id","name","life",
							"attack","defence",
							"imgURL","faceURL",
							 "col","row",
							 "speed","direction",
							 "animationLoopTime",
							 "animationFrameWidth",
							 "animationFrameHeight",
							 "frameSwtichSequence",
							 "eventQueueREF","propertiesREF",
							 "type"};
	//ActorԪ����
	private String actorName="actor";
	//ActorԪ������
	private String[] actorAttributes={"id","name","life","attack",
							"defence","imgURL","faceURL",
							"col","row","speed","direction",
							"animationLoopTime",
							"animationFrameWidth",
							"animationFrameHeight",
							"frameSwtichSequence",
							"propertiesREF"};
	//ͼ��Ԫ����
	private String layerName="layer";
	//ͼ��Ԫ������
	private String[] layerAttributes={"id","tileWidth","tileHeight",
							"tileCols","tileRows","type","imgURL",
							"mapData"};
	//��ͼת����Ԫ������
	private String transformerName="transformer";
	//��ͼת����Ԫ������
	private String[] transformerAttributes={"id","nextLevel","nextMap",
			"location_col","location_row",
			"nextMapEntry_col","nextMapEntry_row","imgURL",
			"tileWidth","tileHeight"};
	
	//��ͼԪ������
	private String mapName="map";
	//��ͼԪ������
	private String[] mapAttributes={"id","name","width","height",
			"layerREF","npcREF","maptransformerREF"};
	
	//�ؿ�Ԫ������
	private String levelName="level";
	//�ؿ�Ԫ������
	private String[] levelAttributes={"id","name","firstMapID","mapREF"};
	
	//�����Ԫ������
	private String cameraName="camera";
	//�����Ԫ������
	private String[] cameraAttributes={"id","x","y",
			"width","height","moveType","customSize"};
	
	//����Ԫ������
	private String musicName="music";
	//����Ԫ������
	private String[] musicAttributes={"id","resURL","musicType",
			"playModel","loopNumber"};
	
	//ȫ����������
	private String globalName="global";
	//ȫ����������
	private String[] globalAttributes={"carnieRunInterval"};
	
	//Ԫ�ض�Ӧ����Ϸ���������
	public static final int EVENT_OBJECT=1;
	public static final int EVENTQUEUE_OBJECT=2;
	public static final int MESSAGE_OBJECT=3;
	public static final int MESSAGEQUEUE_OBJECT=4;
	public static final int PROPERTY_OBJECT=5;
	public static final int PROPERTYBOX_OBJECT=6;
	public static final int NPC_OBJECT=7;
	public static final int ACTOR_OBJECT=8;
	public static final int LAYER_OBJECT=9;
	public static final int TRANSFORMER_OBJECT=10;
	public static final int MAP_OBJECT=11;
	public static final int LEVEL_OBJECT=12;
	public static final int CAMERA_OBJECT=13;
	public static final int MUSIC_OBJECT=14;
	public static final int GLOBAL=15;
	private final int NOTFOUND=1;
	private final int FOUND=2;
	private final int ELEMENTEND=3; 
	/**
	 * ������
	 */
	public void openConfigure(String resURL){
		InputStream in=this.getClass().getResourceAsStream(resURL);
		InputStreamReader reader=new InputStreamReader(in);
		try{
			xmlReader=new XmlReader(reader);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * �ҵ�����Ԫ�ص���һ��StartTagλ�ã��������ĸ�Ԫ�ص�START_TAGλ��
	 */
	private void findNextStartTag() throws Exception{
		//����StartTag����xml�ĵ���βʱ����
		while((xmlReader.getType()!=XmlReader.END_DOCUMENT)&&(xmlReader.next()!=XmlReader.START_TAG)){
//			System.out.println(xmlReader.getPositionDescription());
			;
		}
	}
	
	/**
	 * �ҵ�ָ��Ԫ�ص���һ��StartTagλ�ã����ز��ҽ������
	 * @param name �����ҵ�Ԫ����
	 * @return �����xml�ļ�����һ��λ�÷��ֱ�����Ԫ�ص�START_TAG���򷵻�FOUND;
	 * 			������ֵ�Ԫ�������Ǳ����ҵ�Ԫ�أ��򷵻�NOTFOUND;
	 * 			�����xml�ļ�����һ��λ�÷��ֱ�����Ԫ�ص�TEXT���򷵻�ELEMENTEND;
	 */
	private int findNextStartTag(String name) throws Exception{
		int result=NOTFOUND;

		//����StartTag����xml�ĵ���βʱ����
		while(xmlReader.getType()!=XmlReader.END_DOCUMENT){
			
			//����Ѿ�����START_TAGλ��
			if (xmlReader.next()==XmlReader.START_TAG){
				//���Ϊ��ǰ����ָ����nameԪ�أ��򷵻�FOUND
				if	(xmlReader.getName().equals(name)){
					System.out.println("�Ѿ��ҵ�"+name+"Ԫ�ص�START_TAGλ��:"+xmlReader.getPositionDescription());
//					xmlReader.require(XmlReader.START_TAG,name);
					result=FOUND;
					break;
				}
				//���򷵻�NOTFOUND
				else{
					result=NOTFOUND;
					break;
				}
			}
			//���û�ж���START_TAGλ��
			else{
				//�����������TEXTλ�ã�˵�������˲�ͬ����Ԫ��֮��Ŀ��У��򷵻�ELEMENTEND
				if (xmlReader.getType()==XmlReader.TEXT){
					System.out.println("���ҵ�Ԫ�ؽ�β");
					result=this.ELEMENTEND;
					break;
				}
				//���򷵻�NOTFOUND
				else{
					System.out.println("û���ҵ�"+name+"Ԫ�ص�START_TAGλ��:"+xmlReader.getPositionDescription());
					result=NOTFOUND;
				}
					
			}
		}
		return result;
	}
	
	/**
	 * ��ȡ����ͷ��
	 */
	public void readConfigureHeader(){
		try{
			//��ȡgmatrix������
			xmlReader.next();
			xmlReader.require(XmlReader.START_TAG,"gmatrix");
			System.out.println("gmatrix����--verion="+xmlReader.getAttributeValue("version"));
			
			//��ȡconfigure������
			xmlReader.next();
			xmlReader.require(XmlReader.START_TAG,"configure");
			System.out.println("��������ͷ");
			
			//�ҵ���һ��StartTag
			findNextStartTag();
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	/**
	 * ��ȡԪ����Ϊname������Ԫ��
	 * @param name Ԫ������
	 * @param attr Ԫ������������
	 * @param isOrdinal �Ƿ���˳���ȡ
	 * @return װ��Ԫ������ֵ��Vector
	 */
	public Vector readElement(String name,String[] attr,boolean isOrdinal){
		try{
			System.out.println("��ʼ��ȡ"+name+"Ԫ��...");
			Vector AttributesValueSet=new Vector();
			//��һ���ҵ������ҵ�Ԫ��START_TAGλ��ʱ����Ϊtrue
			boolean firstMatch=false;
			
			while(true){
//				System.out.println("type="+xmlReader.getType()+" name="+xmlReader.getName());
				
				//�����xml�ļ���ͷ�������ȶ�ȡ�ļ�ͷ
				if (xmlReader.getType()==XmlReader.START_DOCUMENT){
					readConfigureHeader();
				}
				
				//������˳����
				if (isOrdinal==false){
					//�ҵ�nameԪ�ص�START_TAGλ��
					int findResult=findNextStartTag(name);
					System.out.println("findResult="+findResult);
					
					//���ݲ��ҵķ���ֵ�ж��Ƿ������ѯ
					switch(findResult){
					//�������NOTFOUND������δ���ֹ�Ԫ�ص�START_TAGλ�ã��������ѯ
					//����Ѿ����ֹ�Ԫ�ص�START_TAGλ�ã��򷵻ز��ҵĽ��
					case NOTFOUND:
						if (firstMatch==false){
							continue;
						}
						else{
							return AttributesValueSet;
						}
					//�������ELEMENTEND������δ���ֹ�Ԫ�ص�START_TAGλ�ã��������ѯ
					//����Ѿ����ֹ�Ԫ�ص�START_TAGλ�ã��򷵻ز��ҵĽ��
					case ELEMENTEND:
						if (firstMatch==false){
							continue;
						}
						else{
							return AttributesValueSet;
						}
					//�������FOUND����firstMatch��־��Ϊtrue����������
					case FOUND:
						firstMatch=true;
						break;
					}
				}	
				
				//���xml�ļ����������˳�
				if (xmlReader.getType()==XmlReader.END_DOCUMENT){
					System.out.println("��ȡ"+name+"ʱ�ĵ�����...");
					break;
				}
				
				//�ж��Ƿ����ڵ�ǰ��Ԫ����
				if ((xmlReader.getName()!=null)&&(xmlReader.getName().equals(name)==false)){
					System.out.println("��ȡ"+name+"Ԫ�ؽ���...");
					break;
				}
				
				//����װ��Ԫ������ֵ��Vector
				Vector AttributesValue=new Vector();
				
				//�����ȡԪ�ص�START_TAGλ�õ�����
				xmlReader.require(XmlReader.START_TAG,name);
				
				//ȡ��Ԫ�ص�����
				for(int i=0;i<attr.length;i++){
					System.out.println(name+"����--"+attr[i]+"="+xmlReader.getAttributeValue(attr[i]));
					AttributesValue.addElement(xmlReader.getAttributeValue(attr[i]));
				}
				
				//���װ��Ԫ������ֵ��Vector
				AttributesValueSet.addElement(AttributesValue);
				
				//�������˳��Ļ�����Ҫ�ҵ�Ԫ�ص�START_Tag��ʼλ��
				if (isOrdinal){
					findNextStartTag();
				}

			}
			return AttributesValueSet; 
		}
		catch(Exception ex){
			System.out.println("��ȡ"+name+"Ԫ��ʱ���ִ���"+ex.getMessage());
			return null;
		}	
	}
	
	/**
	 * ��ȡԪ����Ϊname������Ԫ��
	 * @param name Ԫ������
	 * @param attr Ԫ������������
	 */
	public Vector readElement(String name,String[] attr){
		try{
			System.out.println("��ʼ��ȡ"+name+"Ԫ��...");
			Vector AttributesValueSet=new Vector();

			while(true){
//				System.out.println("type="+xmlReader.getType()+" name="+xmlReader.getName());
				//�����xml�ļ���ͷ�������ȶ�ȡ�ļ�ͷ
				if (xmlReader.getType()==XmlReader.START_DOCUMENT){
					readConfigureHeader();
				}
				//���xml�ļ����������˳�
				if (xmlReader.getType()==XmlReader.END_DOCUMENT){
					System.out.println("��ȡ"+name+"ʱ�ĵ�����...");
					break;
				}
				//�ж��Ƿ����ڵ�ǰ��Ԫ����
				if ((xmlReader.getName()!=null)&&(xmlReader.getName().equals(name)==false)){
					System.out.println("��ȡ"+name+"Ԫ�ؽ���...");
					break;
				}
				
				//����װ��Ԫ������ֵ��Vector
				Vector AttributesValue=new Vector();
				
				//�ҵ�Ԫ�ص�Tag��ʼλ��
				xmlReader.require(XmlReader.START_TAG,name);
				//ȡ��name������
				for(int i=0;i<attr.length;i++){
					System.out.println(name+"����--"+attr[i]+"="+xmlReader.getAttributeValue(attr[i]));
					AttributesValue.addElement(xmlReader.getAttributeValue(attr[i]));
				}
				
				//���װ��Ԫ������ֵ��Vector
				AttributesValueSet.addElement(AttributesValue);
				
				//�ƶ�����һ��λ��
				xmlReader.next();
				//��ȡԪ�ص�Tag����λ��
				xmlReader.require(XmlReader.END_TAG,name);
//				System.out.println(xmlReader.getPositionDescription());
				//�ҵ���һ��startTag
				findNextStartTag();
			}
			return AttributesValueSet; 
		}
		catch(Exception ex){
			System.out.println("��ȡ"+name+"Ԫ��ʱ���ִ���"+ex.getMessage());
			return null;
		}	
	}

	/**
	 * ʹ�ö��ŷָ��ַ���Ϊ����
	 * @param s ���ָ���ַ���
	 * @return �ָ�������
	 */
	private String[] splitByComma(String s){
		String[] result=StringExtension.objectArrayBatchToStringArray(
				StringExtension.split(new StringBuffer(s),
						",",StringExtension.STRING_ARRAY,false));
		return result;
	}
	

	/**
	 * ȡ��ָ��Ԫ�ص����ԣ�������Ӧ�Ķ������б�ķ�ʽ����
	 * @param elementName Ԫ������
	 * @param elementAttributes Ԫ��������������
	 * @param elementType Ԫ����˵������Ϸ��������� 
	 * @param associatedTableArray �뱾��Ϸ�����������Ϸ�����б�������Ϸ������Ҳ�������
	 * @param isOrdinal �Ƿ�˳���ȡxml
	 * @return װ����Ϸ�����б�������Ϸ���������Ϊ��Ϸ�����б�-����Ϣ���У�
	 */
	public GameObjectQueue readGameObjectConfigure(String elementName,
			String[] elementAttributes,int elementType,
			GameObjectQueue[] associatedTableArray,boolean isOrdinal){
		
		GameObjectQueue resultTable=null;
		GameObject go=null;
		GameObject findGo=null;
		GameObjectQueue findGq=null;
		String id=null;
		String objectIDList=null;
		
		//��ȡԪ������ֵ,���ذ���������Ե�ֵ���ϵ�Vector
		Vector AttributesValueSet=readElement(elementName,elementAttributes,isOrdinal);
		
		if ((AttributesValueSet!=null)&&(AttributesValueSet.size()>0)){
			//����ÿ������ֵ����
			for(int i=0;i<AttributesValueSet.size();i++){
				Vector attrValue=(Vector)AttributesValueSet.elementAt(i);
				switch(elementType){
				//����û�й������б������ֵ���ϣ�ֱ�ӹ������
				case EVENT_OBJECT:
					if (resultTable==null){
						resultTable=new GameObjectQueue();
					}
					go=new Event();
					go.loadProperties(attrValue);
					System.out.println("��װ�ص���Ϸ����:"+go);
					resultTable.put(go.getId(),go);
					break;
				case MESSAGE_OBJECT:
					if (resultTable==null){
						resultTable=new GameObjectQueue();
					}
					go=new Message();
					go.loadProperties(attrValue);
					System.out.println("��װ�ص���Ϸ����:"+go);
					resultTable.put(go.getId(),go);
					break;
				case PROPERTY_OBJECT:
					if (resultTable==null){
						resultTable=new GameObjectQueue();
					}
					go=new Property();
					go.loadProperties(attrValue);
					System.out.println("��װ�ص���Ϸ����:"+go);
					resultTable.put(go.getId(),go);
					break;
				case LAYER_OBJECT:
					if (resultTable==null){
						resultTable=new GameObjectQueue();
					}
					go=new SimpleLayer();
					go.loadProperties(attrValue);
					System.out.println("��װ�ص���Ϸ����:"+go);
					resultTable.put(go.getId(),go);
					break;
				case TRANSFORMER_OBJECT:
					if (resultTable==null){
						resultTable=new GameObjectQueue();
					}
					go=new MapTransformer();
					go.loadProperties(attrValue);
					System.out.println("��װ�ص���Ϸ����:"+go);
					resultTable.put(go.getId(),go);
					break;
				case CAMERA_OBJECT:
					if (resultTable==null){
						resultTable=new GameObjectQueue();
					}
					go=new Camera();
					go.loadProperties(attrValue);
					System.out.println("��װ�ص���Ϸ����:"+go);
					resultTable.put(go.getId(),go);
					break;
				case MUSIC_OBJECT:
					if (resultTable==null){
						resultTable=new Musician();
					}
					go=new Music();
					go.loadProperties(attrValue);
					System.out.println("��װ�ص���Ϸ����:"+go);
					resultTable.put(go.getId(),go);
					break;
				//���ڴ��й������б������ֵ���ϣ���Ҫ���й���
				case EVENTQUEUE_OBJECT:
					if (resultTable==null){
						resultTable=new EventQueue();
					}
				case MESSAGEQUEUE_OBJECT:
					if (resultTable==null){
						resultTable=new MessageQueue();
					}
				case PROPERTYBOX_OBJECT:
					if (resultTable==null){
						resultTable=new PropertyManager();
					}
					//���ID
					id=(String)attrValue.elementAt(0);
					//���������Ķ����ID�б�
					objectIDList=(String)attrValue.elementAt(1);
					//��������Ķ�����й���
					if (objectIDList.length()>0){
						GameObjectQueue gq=new GameObjectQueue();
						//�������Ķ����ID�б����Ϊ����
						String[] objectIDSet=splitByComma(objectIDList);
						//�ڹ���������б��в���������Ķ����ID��Ӧ�Ķ��󣬲�������
						for(int j=0;j<objectIDSet.length;j++){
							findGo=(GameObject)associatedTableArray[0].find(objectIDSet[j]);
							if (findGo!=null){
								gq.put(objectIDSet[j],findGo);
								gq.setId(id);
								resultTable.put(id,gq);
							}
						}
					}
					break;
				case NPC_OBJECT:
					if (resultTable==null){
						resultTable=new GameObjectQueue();
					}
					go=new NPC();
					go.loadProperties(attrValue);
					NPC npc=(NPC)go;
					//���ҹ��������б������������ID���Ӧ�Ķ��󣬼��뱾NPC��Ӧ���¼��б�
					findGq=(GameObjectQueue)associatedTableArray[0].find((String)attrValue.elementAt(15));
					if (findGo!=null){
						npc.setEventQueue((EventQueue)findGq);
					}
					else{
						npc.setEventQueue(new EventQueue());
					}
					//���ҹ��������б������������ID���Ӧ�Ķ��󣬼��뱾NPC��Ӧ�ĵ����б�
					findGq=(GameObjectQueue)associatedTableArray[1].find((String)attrValue.elementAt(16));
					if (findGq!=null){
						npc.setPropertyBox((PropertyManager)findGq);
					}
					else{
						npc.setPropertyBox(new PropertyManager());
					}
					npc.setType(Integer.parseInt((String)attrValue.elementAt(17)));
					System.out.println("��װ�ص���Ϸ����:"+go);
					resultTable.put(npc.getId(),npc);
					break;
				case ACTOR_OBJECT:
					if (resultTable==null){
						resultTable=new GameObjectQueue();
					}
					go=new Actor();
					go.loadProperties(attrValue);
					Actor actor=(Actor)go;
					//���ҹ��������б������������ID���Ӧ�Ķ��󣬼��뱾Actor��Ӧ�ĵ����б�
					findGq=(GameObjectQueue)associatedTableArray[0].find((String)attrValue.elementAt(14));
					if (findGq!=null){
						actor.setPropertyBox((PropertyManager)findGq);
					}
					else{
						actor.setPropertyBox(new PropertyManager());
					}
					System.out.println("��װ�ص���Ϸ����:"+go);
					resultTable.put(actor.getId(),actor);
					break;
				case MAP_OBJECT:
					if (resultTable==null){
						resultTable=new GameObjectQueue();
					}
					go=new SimpleMap();
					go.loadProperties(attrValue);
					SimpleMap simpleMap=(SimpleMap)go;
					
					int attrID=4;
					int associatedTableID=0;
					
					for(int m=attrID;m<attrValue.size();m++){
						//���Layer��npc��maptransformer
						//���������Ķ����ID�б�
						objectIDList=(String)attrValue.elementAt(attrID);
						//��������Ķ�����й���
						if (objectIDList.length()>0){
							GameObjectQueue gq=new GameObjectQueue();
							//�������Ķ����ID�б����Ϊ����
							String[] objectIDSet=splitByComma(objectIDList);
							//�ڹ���������б��в���������Ķ����ID��Ӧ�Ķ��󣬲�������
							for(int j=0;j<objectIDSet.length;j++){
								findGo=(GameObject)associatedTableArray[associatedTableID].find(objectIDSet[j]);
								if (findGo!=null){
									gq.put(objectIDSet[j],findGo);
									gq.setId(id);
								}
							}
							switch(attrID){
							case 4:
								simpleMap.setLayerSet(gq);
								break;
							case 5:
								simpleMap.setNpcSet(gq);
								break;
							case 6:
								simpleMap.setMapLink(gq);
								break;
							}
						}
						attrID++;
						associatedTableID++;
					}
					System.out.println("��װ�ص���Ϸ����:"+go);
					resultTable.put(go.getId(),go);
					break;
				case LEVEL_OBJECT:
					if (resultTable==null){
						resultTable=new GameObjectQueue();
					}
					go=new SimpleLevel();
					go.loadProperties(attrValue);
					SimpleLevel level=(SimpleLevel)go;
					//���map����
					GameObjectQueue gq=new GameObjectQueue();
					//�������Ķ����ID�б����Ϊ����
					String[] objectIDSet=splitByComma((String)attrValue.elementAt(3));
					//�ڹ���������б��в���������Ķ����ID��Ӧ�Ķ��󣬲�������
					for(int j=0;j<objectIDSet.length;j++){
						findGo=(GameObject)associatedTableArray[0].find(objectIDSet[j]);
						if (findGo!=null){
							gq.put(objectIDSet[j],findGo);
						}
					}
					level.setMapSet(gq);
					System.out.println("��װ�ص���Ϸ����:"+go);
					resultTable.put(go.getId(),go);
					break;
				}
				
					
				
			}
			return resultTable;
		}
		else{
			return new GameObjectQueue();
		}
	}
	
	/**
	 * ȡ��ȫ������Ԫ�ص����ԣ��������ֳ�Ҫʹ�õ�����ʱ����
	 * @param elementName Ԫ������
	 * @param elementAttributes Ԫ��������������
	 * @param isOrdinal �Ƿ�˳���ȡxml
	 * @return ���ֳ�Ҫʹ�õ�����ʱ����
	 */
	public int readGlobalConfigureForCarnieRunInterval(String elementName,
			String[] elementAttributes,boolean isOrdinal){
		
		//��ȡԪ������ֵ,���ذ���������Ե�ֵ���ϵ�Vector
		Vector AttributesValueSet=readElement(elementName,elementAttributes,isOrdinal);
		int result=10;
		if ((AttributesValueSet!=null)&&(AttributesValueSet.size()>0)){
			//����ÿ������ֵ����
			Vector attrValue=(Vector)AttributesValueSet.elementAt(0);
			result= Integer.valueOf((String)attrValue.elementAt(0)).intValue();	
		}
		return result;
	}
	
	public GameObjectQueue readMsgConfigure(boolean isOrdinal){
		return readGameObjectConfigure(msgName,msgAttributes,MESSAGE_OBJECT,null,isOrdinal);
	}
	
	public GameObjectQueue readMsgQueueConfigure(GameObjectQueue[] associatedTableArray,boolean isOrdinal){
		return readGameObjectConfigure(msgQueueName,msgQueueAttributes,MESSAGEQUEUE_OBJECT,associatedTableArray,isOrdinal);
	}
	
	public GameObjectQueue readEventConfigure(boolean isOrdinal){
		return readGameObjectConfigure(eventName,eventAttributes,EVENT_OBJECT,null,isOrdinal);
	}
	
	public GameObjectQueue readEventQueueConfigure(GameObjectQueue[] associatedTableArray,boolean isOrdinal){
		return readGameObjectConfigure(eventQueueName,eventQueueAttributes,EVENTQUEUE_OBJECT,associatedTableArray,isOrdinal);
	}
	
	public GameObjectQueue readPropertyConfigure(boolean isOrdinal){
		return readGameObjectConfigure(propertyName,propertyAttributes,PROPERTY_OBJECT,null,isOrdinal);
	}
	
	public GameObjectQueue readPropertyBoxConfigure(GameObjectQueue[] associatedTableArray,boolean isOrdinal){
		return readGameObjectConfigure(propBoxName,propBoxAttributes,PROPERTYBOX_OBJECT,associatedTableArray,isOrdinal);
	}
	
	public GameObjectQueue readNpcConfigure(GameObjectQueue[] associatedTableArray,boolean isOrdinal){
		return readGameObjectConfigure(npcName,npcAttributes,NPC_OBJECT,associatedTableArray,isOrdinal);
	}
	
	public GameObjectQueue readActorConfigure(GameObjectQueue[] associatedTableArray,boolean isOrdinal){
		return readGameObjectConfigure(actorName,actorAttributes,ACTOR_OBJECT,associatedTableArray,isOrdinal);
	}
	
	public GameObjectQueue readLayerConfigure(boolean isOrdinal){
		return readGameObjectConfigure(layerName,layerAttributes,LAYER_OBJECT,null,isOrdinal);
	}
	
	public GameObjectQueue readTransformerConfigure(boolean isOrdinal){
		return readGameObjectConfigure(transformerName,transformerAttributes,TRANSFORMER_OBJECT,null,isOrdinal);
	}
	
	public GameObjectQueue readMapConfigure(GameObjectQueue[] associatedTableArray,boolean isOrdinal){
		return readGameObjectConfigure(mapName,mapAttributes,MAP_OBJECT,associatedTableArray,isOrdinal);
	}
	
	public GameObjectQueue readLevelConfigure(GameObjectQueue[] associatedTableArray,boolean isOrdinal){
		return readGameObjectConfigure(levelName,levelAttributes,LEVEL_OBJECT,associatedTableArray,isOrdinal);
	}
	
	public GameObjectQueue readCameraConfigure(boolean isOrdinal){
		return readGameObjectConfigure(cameraName,cameraAttributes,CAMERA_OBJECT,null,isOrdinal);
	}
	
	public GameObjectQueue readMusicConfigure(boolean isOrdinal){
		return readGameObjectConfigure(musicName,musicAttributes,MUSIC_OBJECT,null,isOrdinal);
	}
	
	public int readCarnieRunInterval(boolean isOrdinal){
		return readGlobalConfigureForCarnieRunInterval(globalName,globalAttributes,isOrdinal);
	}
	
	public static void main(String[] argv){
		XmlScriptParser x=new XmlScriptParser();
		x.openConfigure("/configure/config.xml");
		GameObjectQueue msgTable=x.readMsgConfigure(true);
		System.out.println("msg����="+msgTable.size());
		GameObjectQueue mqTable=x.readMsgQueueConfigure(new GameObjectQueue[]{msgTable},true);
		System.out.println("msg Queue����="+mqTable.size());
		
		GameObjectQueue eventTable=x.readEventConfigure(true);
		System.out.println("event����="+eventTable.size());
		GameObjectQueue eqTable=x.readEventQueueConfigure(new GameObjectQueue[]{eventTable},true);
		System.out.println("event Queue����="+eqTable.size());
		
		GameObjectQueue propTable=x.readPropertyConfigure(true);
		System.out.println("property����="+propTable.size());
		GameObjectQueue propBoxTable=x.readPropertyBoxConfigure(new GameObjectQueue[]{propTable},true);
		System.out.println("propertyBox����="+propBoxTable.size());
		
		GameObjectQueue npcTable=x.readNpcConfigure(new GameObjectQueue[]{propTable,eventTable},true);
		System.out.println("npc����="+npcTable.size()+" npc[npc01].name="+(NPC)npcTable.get("npc01"));
		
		GameObjectQueue actorTable=x.readActorConfigure(new GameObjectQueue[]{propTable},true);
		System.out.println("actor����="+actorTable.size());
		
		GameObjectQueue layerTable=x.readLayerConfigure(true);
		System.out.println("layer����="+layerTable.size());
		
		GameObjectQueue transformerTable=x.readTransformerConfigure(true);
		System.out.println("transformer����="+transformerTable.size());
		
		GameObjectQueue mapTable=x.readMapConfigure(new GameObjectQueue[]{layerTable,npcTable,transformerTable},true);
		System.out.println("map����="+mapTable.size());
		
		GameObjectQueue levelTable=x.readLevelConfigure(new GameObjectQueue[]{mapTable},true);
		System.out.println("level����="+levelTable.size());
		
		GameObjectQueue cameraTable=x.readCameraConfigure(true);
		System.out.println("camera����="+cameraTable.size());
		
		GameObjectQueue musicTable=x.readMusicConfigure(true);
		System.out.println("music����="+musicTable.size());
		System.out.println("Carnie���м��="+x.readCarnieRunInterval(true));
	}
}

