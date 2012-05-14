import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import util.FileLogInfo;
import util.StreamTool;



public class FileServer {
	 private ExecutorService executorService;//�̳߳�
	 private int port;//�����˿�
	 private boolean quit = false;//�˳�
	 private ServerSocket server;
	 private Map<Long, FileLogInfo> datas = new HashMap<Long, FileLogInfo>();//��Ŷϵ�����,�Ժ��Ϊ���ݿ���
	 public FileServer(int port)
	 {
		 this.port = port;
		 //�����̳߳أ����о���(cpu����*50)���߳�
		 executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 50);
	 }
	 
	/**
	  * �˳�
	  */
	 public void quit()
	 {
		this.quit = true;
		try 
		{
			server.close();
		}catch (IOException e) 
		{
			e.printStackTrace();
		}
	 }
	 
	 /**
	  * ��������
	  * @throws Exception
	  */
	 public void start() throws Exception
	 {
		 server = new ServerSocket(port);//ʵ�ֶ˿ڼ���
		 while(!quit)
		 {
	         try 
	         {
	           Socket socket = server.accept();
	           executorService.execute(new SocketTask(socket));//Ϊ֧�ֶ��û��������ʣ������̳߳ع���ÿһ���û�����������
	         }catch (Exception e) 
	         {
	             e.printStackTrace();
	         }
	     }
	 }
	 
	 private final class SocketTask implements Runnable
	 {
		private Socket socket = null;
		public SocketTask(Socket socket) 
		{
			this.socket = socket;
		}
		@Override
		public void run() 
		{
			try 
			{
				System.out.println("FileServer accepted connection "+ socket.getInetAddress()+ ":"+ socket.getPort());
				//�õ��ͻ��˷����ĵ�һ��Э�����ݣ�Content-Length=143253434;filename=xxx.3gp;sourceid=
				//����û������ϴ��ļ���sourceid��ֵΪ�ա�
				InputStream inStream = socket.getInputStream();
				String head = StreamTool.readLine(inStream);
				System.out.println("FileServer head:"+head);
				if(head!=null)
				{
					//�����Э����������ȡ�������ֵ
					String[] items = head.split(";");
					String filelength = items[0].substring(items[0].indexOf("=")+1);
					String filename = items[1].substring(items[1].indexOf("=")+1);
					String sourceid = items[2].substring(items[2].indexOf("=")+1);		
					//������Դid�������ҪΨһ�ԣ����Բ���UUID
					long id = System.currentTimeMillis();
					FileLogInfo log = null;
					if(sourceid!=null && !"".equals(sourceid))
					{
						id = Long.valueOf(sourceid);
						//�����ϴ����ļ��Ƿ�����ϴ���¼
						log = find(id);
					}
					File file = null;
					int position = 0;
					//����ϴ����ļ��������ϴ���¼,Ϊ�ļ���Ӹ��ټ�¼
					if(log==null)
					{
						//���ô�ŵ�λ���뵱ǰӦ�õ�λ���й�
						File dir = new File("c:/temp/");
						if(!dir.exists()) dir.mkdirs();
						file = new File(dir, filename);
						//����ϴ����ļ�����������Ȼ����и���
						if(file.exists())
						{
							filename = filename.substring(0, filename.indexOf(".")-1)+ dir.listFiles().length+ filename.substring(filename.indexOf("."));
							file = new File(dir, filename);
						}
						save(id, file);
					}
					// ����ϴ����ļ������ϴ���¼,��ȡ�ϴεĶϵ�λ��
					else
					{
						System.out.println("FileServer have exits log not null");
						//���ϴ���¼�еõ��ļ���·��
						file = new File(log.getPath());
						if(file.exists())
						{
							File logFile = new File(file.getParentFile(), file.getName()+".log");
							if(logFile.exists())
							{
								Properties properties = new Properties();
								properties.load(new FileInputStream(logFile));
								//��ȡ�ϵ�λ��
								position = Integer.valueOf(properties.getProperty("length"));
							}
						}
					}
					//***************************�����Ƕ�Э��ͷ�Ĵ���������ʽ��������***************************************
					//��ͻ�������������
					OutputStream outStream = socket.getOutputStream();
					String response = "sourceid="+ id+ ";position="+ position+ "%";
					//�������յ��ͻ��˵�������Ϣ�󣬸��ͻ��˷�����Ӧ��Ϣ��sourceid=1274773833264;position=position
					//sourceid�ɷ������ɣ�Ψһ��ʶ�ϴ����ļ���positionָʾ�ͻ��˴��ļ���ʲôλ�ÿ�ʼ�ϴ�
					outStream.write(response.getBytes());
					RandomAccessFile fileOutStream = new RandomAccessFile(file, "rwd");
					//�����ļ�����
					if(position==0) fileOutStream.setLength(Integer.valueOf(filelength));
					//�ƶ��ļ�ָ����λ�ÿ�ʼд������
					fileOutStream.seek(position);
					byte[] buffer = new byte[1024];
					int len = -1;
					int length = position;
					//���������ж�ȡ����д�뵽�ļ��У������Ѿ�������ļ�����д�������ļ���ʵʱ��¼�ļ�����󱣴�λ��
					while( (len=inStream.read(buffer)) != -1)
					{
						fileOutStream.write(buffer, 0, len);
						length += len;
						Properties properties = new Properties();
						properties.put("length", String.valueOf(length));
						FileOutputStream logFile = new FileOutputStream(new File(file.getParentFile(), file.getName()+".log"));
						//ʵʱ��¼�ļ�����󱣴�λ��
						properties.store(logFile, null);
						logFile.close();
					}
					//����������ȵ���ʵ�ʳ������ʾ�����ɹ�
					if(length==fileOutStream.length()){
						delete(id);
					}
					fileOutStream.close();					
					inStream.close();
					outStream.close();
					file = null;
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			finally{
	            try
	            {
	                if(socket!=null && !socket.isClosed()) socket.close();
	            } 
	            catch (IOException e)
	            {
	            	e.printStackTrace();
	            }
	        }
		}
	 }
	 
	 /**
	  * �����ڼ�¼���Ƿ���sourceid���ļ�
	  * @param sourceid
	  * @return
	  */
	 public FileLogInfo find(Long sourceid)
	 {
		 return datas.get(sourceid);
	 }
	 
	 /**
	  * �����ϴ���¼���պ���Ըĳ�ͨ�����ݿ���
	  * @param id
	  * @param saveFile
	  */
	 public void save(Long id, File saveFile)
	 {
		 System.out.println("save logfile "+id);
		 datas.put(id, new FileLogInfo(id, saveFile.getAbsolutePath()));
	 }
	 
	 /**
	  * ���ļ��ϴ���ϣ�ɾ����¼
	  * @param sourceid
	  */
	 public void delete(long sourceid)
	 {
		 System.out.println("delete logfile "+sourceid);
		 if(datas.containsKey(sourceid)) datas.remove(sourceid);
	 }
	 
}
