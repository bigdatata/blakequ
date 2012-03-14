import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;



public class DoPost {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String content = null;
		try {
			content = readFromFile("c:/3.txt");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
        
	    StringBuffer sbf = new StringBuffer();     
	    BufferedReader reader = null; 
	    URLConnection conn = null;
	    try {     

	    	// Construct data
	        String data = "data" + "=" + URLEncoder.encode(content, "UTF-8");
	    
	        // Send data
	        URL url = new URL("http://localhost:8080/crc_ip_manager_alpha/commit_data.do");
	        conn = url.openConnection();
	        conn.setDoOutput(true);
	        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	        wr.write(data);
	        wr.flush();
	        
	        reader = new BufferedReader(  
	                new InputStreamReader(conn.getInputStream()));
	        String line;     
	        while ((line = reader.readLine()) != null){     
	            sbf.append(line);     
	        }     
	        reader.close();     
	        System.out.println("___________end___________response:"+sbf.toString());
	    } catch (Exception e) {     
	        sbf.append("");
	    } 
	    finally{     
	    	if (reader != null) {  
		        try {  
		            reader.close();  
		            reader = null;  
		        } catch (Exception e) {  
		          
		        }  
		    }       
	    }     
	    sbf.toString().trim();
	}
	
	
	
	/**
	 * 读取文件内容
	 * @return
	 * @throws IOException 
	 */
	public static String readFromFile(String path) throws IOException{
		BufferedReader reader = null;
		StringBuffer sb = null;
		FileInputStream in = null;
		try { 
			File file = new File(path);
			if(!file.exists() || !file.canRead()){
				return null;
			}
			if(file.isDirectory()){
				return null;
			}
			in = new FileInputStream(file); 
			reader = new BufferedReader(new UnicodeReader(in, "UTF-8"));
            String line = new String();
            sb = new StringBuffer();
            while ((line = reader.readLine()) != null) {  
                sb.append(line);  
            }  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			reader.close();
			in.close();
		}
		return sb.toString().trim();
	}

}
