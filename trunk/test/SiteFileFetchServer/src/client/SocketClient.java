package client;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.io.RandomAccessFile;
import java.net.Socket;

import util.ConstantValues;
import util.StreamTool;

/**
 * 注意一开始传文件是没有sourceid的
 * 是由服务器传回来的，然后客户端存入数据库
 * 如果这次没传完，下次传的时候就需要sourceid
 * 服务器判断sourceid，如果有(因为服务器根据id来判断是否传送过)
 * @author Administrator
 *
 */
public class SocketClient {
	public static void main(String[] args)   
    {  
        try   
        {     
            //这里的套接字根据实际服务器更改  
            Socket socket = new Socket(ConstantValues.HOST, ConstantValues.PORT);  
            OutputStream outStream = socket.getOutputStream();
            File file = new File(ConstantValues.PATH+ConstantValues.FILE_NAME);  
            //构造上传文件头，上传的时候会判断上传的文件是否存在，是否存在上传记录  
            //若是不存在则服务器会自动生成一个id,给客户端返回  
            String head = "Content-Length="+ file.length() + ";filename="+ ConstantValues.FILE_NAME + ";sourceid=1278916111468%";
            System.out.println("SocketClient begin:"+head);
            outStream.write(head.getBytes());  
            
            //服务器返回的信息
//            PushbackInputStream inStream = new PushbackInputStream(socket.getInputStream());
            InputStream inStream = socket.getInputStream();
            String response = StreamTool.readLine(inStream);  
            System.out.println("SocketClient response:"+response);  
            String[] items = response.split(";");  
            //构造开始上传文件的位置  
            String position = items[1].substring(items[1].indexOf("=")+1);  
            //以读的方式开始访问  
            RandomAccessFile fileOutStream = new RandomAccessFile(file, "r");  
            fileOutStream.seek(Integer.valueOf(position));  
            byte[] buffer = new byte[1024];  
            int len = -1;  
            int i = 0;  
            while( (len = fileOutStream.read(buffer)) != -1)  
            {  
                outStream.write(buffer, 0, len);  
                i++;  
                //if(i==10) break;  
            }  
            fileOutStream.close();  
            outStream.close();  
            inStream.close();  
            socket.close();  
        }   
        catch (Exception e)   
        {                      
            e.printStackTrace();  
        }  
    }  
    /** 
    * 读取流 
    * @param inStream 
    * @return 字节数组 
    * @throws Exception 
    */  
    public static byte[] readStream(InputStream inStream) throws Exception  
    {  
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();  
            byte[] buffer = new byte[1024];  
            int len = -1;  
            while( (len=inStream.read(buffer)) != -1)  
            {  
                outSteam.write(buffer, 0, len);  
            }  
            outSteam.close();  
            inStream.close();  
            return outSteam.toByteArray();  
    }  
}
