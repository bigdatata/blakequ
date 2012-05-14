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
	 private ExecutorService executorService;//线程池
	 private int port;//监听端口
	 private boolean quit = false;//退出
	 private ServerSocket server;
	 private Map<Long, FileLogInfo> datas = new HashMap<Long, FileLogInfo>();//存放断点数据,以后改为数据库存放
	 public FileServer(int port)
	 {
		 this.port = port;
		 //创建线程池，池中具有(cpu个数*50)条线程
		 executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 50);
	 }
	 
	/**
	  * 退出
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
	  * 启动服务
	  * @throws Exception
	  */
	 public void start() throws Exception
	 {
		 server = new ServerSocket(port);//实现端口监听
		 while(!quit)
		 {
	         try 
	         {
	           Socket socket = server.accept();
	           executorService.execute(new SocketTask(socket));//为支持多用户并发访问，采用线程池管理每一个用户的连接请求
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
				//得到客户端发来的第一行协议数据：Content-Length=143253434;filename=xxx.3gp;sourceid=
				//如果用户初次上传文件，sourceid的值为空。
				InputStream inStream = socket.getInputStream();
				String head = StreamTool.readLine(inStream);
				System.out.println("FileServer head:"+head);
				if(head!=null)
				{
					//下面从协议数据中提取各项参数值
					String[] items = head.split(";");
					String filelength = items[0].substring(items[0].indexOf("=")+1);
					String filename = items[1].substring(items[1].indexOf("=")+1);
					String sourceid = items[2].substring(items[2].indexOf("=")+1);		
					//生成资源id，如果需要唯一性，可以采用UUID
					long id = System.currentTimeMillis();
					FileLogInfo log = null;
					if(sourceid!=null && !"".equals(sourceid))
					{
						id = Long.valueOf(sourceid);
						//查找上传的文件是否存在上传记录
						log = find(id);
					}
					File file = null;
					int position = 0;
					//如果上传的文件不存在上传记录,为文件添加跟踪记录
					if(log==null)
					{
						//设置存放的位置与当前应用的位置有关
						File dir = new File("c:/temp/");
						if(!dir.exists()) dir.mkdirs();
						file = new File(dir, filename);
						//如果上传的文件发生重名，然后进行改名
						if(file.exists())
						{
							filename = filename.substring(0, filename.indexOf(".")-1)+ dir.listFiles().length+ filename.substring(filename.indexOf("."));
							file = new File(dir, filename);
						}
						save(id, file);
					}
					// 如果上传的文件存在上传记录,读取上次的断点位置
					else
					{
						System.out.println("FileServer have exits log not null");
						//从上传记录中得到文件的路径
						file = new File(log.getPath());
						if(file.exists())
						{
							File logFile = new File(file.getParentFile(), file.getName()+".log");
							if(logFile.exists())
							{
								Properties properties = new Properties();
								properties.load(new FileInputStream(logFile));
								//读取断点位置
								position = Integer.valueOf(properties.getProperty("length"));
							}
						}
					}
					//***************************上面是对协议头的处理，下面正式接收数据***************************************
					//向客户端请求传输数据
					OutputStream outStream = socket.getOutputStream();
					String response = "sourceid="+ id+ ";position="+ position+ "%";
					//服务器收到客户端的请求信息后，给客户端返回响应信息：sourceid=1274773833264;position=position
					//sourceid由服务生成，唯一标识上传的文件，position指示客户端从文件的什么位置开始上传
					outStream.write(response.getBytes());
					RandomAccessFile fileOutStream = new RandomAccessFile(file, "rwd");
					//设置文件长度
					if(position==0) fileOutStream.setLength(Integer.valueOf(filelength));
					//移动文件指定的位置开始写入数据
					fileOutStream.seek(position);
					byte[] buffer = new byte[1024];
					int len = -1;
					int length = position;
					//从输入流中读取数据写入到文件中，并将已经传入的文件长度写入配置文件，实时记录文件的最后保存位置
					while( (len=inStream.read(buffer)) != -1)
					{
						fileOutStream.write(buffer, 0, len);
						length += len;
						Properties properties = new Properties();
						properties.put("length", String.valueOf(length));
						FileOutputStream logFile = new FileOutputStream(new File(file.getParentFile(), file.getName()+".log"));
						//实时记录文件的最后保存位置
						properties.store(logFile, null);
						logFile.close();
					}
					//如果长传长度等于实际长度则表示长传成功
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
	  * 查找在记录中是否有sourceid的文件
	  * @param sourceid
	  * @return
	  */
	 public FileLogInfo find(Long sourceid)
	 {
		 return datas.get(sourceid);
	 }
	 
	 /**
	  * 保存上传记录，日后可以改成通过数据库存放
	  * @param id
	  * @param saveFile
	  */
	 public void save(Long id, File saveFile)
	 {
		 System.out.println("save logfile "+id);
		 datas.put(id, new FileLogInfo(id, saveFile.getAbsolutePath()));
	 }
	 
	 /**
	  * 当文件上传完毕，删除记录
	  * @param sourceid
	  */
	 public void delete(long sourceid)
	 {
		 System.out.println("delete logfile "+sourceid);
		 if(datas.containsKey(sourceid)) datas.remove(sourceid);
	 }
	 
}
