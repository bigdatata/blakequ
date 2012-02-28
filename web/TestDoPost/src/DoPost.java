import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class DoPost {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String content = null;
		try {
			content = readFromFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}//传输的内容
		String urlPath = "http://localhost:8080/crc_ip_manager_alpha/update/commit.do"; //服务器地址     
	    StringBuffer sbf = new StringBuffer();     
	    BufferedWriter writer = null;  
	    BufferedReader reader = null;  
	    HttpURLConnection uc = null;  
	    try {     
	        URL url = new URL(urlPath);     
	        uc = (HttpURLConnection)url.openConnection();            
	        uc.setDoOutput(true);     
	        writer = new BufferedWriter(   
	                new OutputStreamWriter(uc.getOutputStream())); //向服务器传送数据     
	        writer.write(content); //传送的数据      
	        writer.flush();      
	        writer.close();      
	        reader = new BufferedReader(  
	                new InputStreamReader(uc.getInputStream()));//读取服务器响应信息     
	        String line;     
	        while ((line = reader.readLine()) != null){     
	            sbf.append(line);     
	        }     
	        reader.close();     
	        uc.disconnect();     
	        System.out.println("___________end___________");
	    } catch (Exception e) {     
	        sbf.append("服务器连接失败！请稍后重新操作");
	    } finally{     
	        closeIO(writer,reader); //关闭流     
	    }     
	    sbf.toString().trim();
	}
	
	/** 
	 * 关闭流 
	 */  
	private static void closeIO(BufferedWriter writer,BufferedReader reader){  
	    if (writer != null) {  
	        try {  
	            writer.close();  
	            writer = null;  
	        } catch (Exception e) {  
	          
	        }  
	    }  
	    if (reader != null) {  
	        try {  
	            reader.close();  
	            reader = null;  
	        } catch (Exception e) {  
	          
	        }  
	    }  
	}  
	
	private static String readFromFile() throws IOException{
		BufferedReader reader = null;
		StringBuffer sb = null;
		try { 
			reader = new java.io.BufferedReader(new FileReader("c:/2.txt"));
			String s =  reader.readLine();
			sb = new StringBuffer();
			while (s != null)
			{
			  sb.append(s);
			  sb.append("\r\n");
			  s = reader.readLine();
			}
			System.out.println("**sb:"+sb.toString());
			s = reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			reader.close();
		}
		return sb.toString();
	}

}
