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
 * ע��һ��ʼ���ļ���û��sourceid��
 * ���ɷ������������ģ�Ȼ��ͻ��˴������ݿ�
 * ������û���꣬�´δ���ʱ�����Ҫsourceid
 * �������ж�sourceid�������(��Ϊ����������id���ж��Ƿ��͹�)
 * @author Administrator
 *
 */
public class SocketClient {
	public static void main(String[] args)   
    {  
        try   
        {     
            //������׽��ָ���ʵ�ʷ���������  
            Socket socket = new Socket(ConstantValues.HOST, ConstantValues.PORT);  
            OutputStream outStream = socket.getOutputStream();
            File file = new File(ConstantValues.PATH+ConstantValues.FILE_NAME);  
            //�����ϴ��ļ�ͷ���ϴ���ʱ����ж��ϴ����ļ��Ƿ���ڣ��Ƿ�����ϴ���¼  
            //���ǲ���������������Զ�����һ��id,���ͻ��˷���  
            String head = "Content-Length="+ file.length() + ";filename="+ ConstantValues.FILE_NAME + ";sourceid=1278916111468%";
            System.out.println("SocketClient begin:"+head);
            outStream.write(head.getBytes());  
            
            //���������ص���Ϣ
//            PushbackInputStream inStream = new PushbackInputStream(socket.getInputStream());
            InputStream inStream = socket.getInputStream();
            String response = StreamTool.readLine(inStream);  
            System.out.println("SocketClient response:"+response);  
            String[] items = response.split(";");  
            //���쿪ʼ�ϴ��ļ���λ��  
            String position = items[1].substring(items[1].indexOf("=")+1);  
            //�Զ��ķ�ʽ��ʼ����  
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
    * ��ȡ�� 
    * @param inStream 
    * @return �ֽ����� 
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
