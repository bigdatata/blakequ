package util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;


public class StreamTool {
	public static void save(File file, byte[] data) throws Exception   
    {  
        FileOutputStream outStream = new FileOutputStream(file);  
        outStream.write(data);  
        outStream.close();  
    }  
      
    public static String readLine(InputStream in) throws IOException   
    {  
           char buf[] = new char[128];  
           int room = buf.length;  
           int offset = 0;  
           int c;  
           while((c=in.read()) != -1){
        	   if(c == 37) break;
        	   buf[offset++] = (char) c;
           }
           return String.copyValueOf(buf, 0, offset);  
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
           while( (len=inStream.read(buffer)) != -1){  
               outSteam.write(buffer, 0, len);  
           }  
           outSteam.close();  
           inStream.close();  
           return outSteam.toByteArray();  
   }  
}
