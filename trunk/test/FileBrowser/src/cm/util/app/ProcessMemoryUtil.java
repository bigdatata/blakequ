package cm.util.app;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.util.Log;

/**
 * 获取所有系统中的进程运行情况
 * @author Administrator
 *
 */
public class ProcessMemoryUtil {
    
    private static final int INDEX_FIRST = -1;
    private static final int INDEX_PID = INDEX_FIRST + 1;
    private static final int INDEX_CPU = INDEX_FIRST + 2;
    private static final int INDEX_STAT = INDEX_FIRST + 3;
    private static final int INDEX_THR = INDEX_FIRST + 4;
    private static final int INDEX_VSS = INDEX_FIRST + 5;
    private static final int INDEX_RSS = INDEX_FIRST + 6;
    private static final int INDEX_PCY = INDEX_FIRST + 7;
    private static final int INDEX_UID = INDEX_FIRST + 8;
    private static final int INDEX_NAME = INDEX_FIRST + 9;
    private static final int Length_ProcStat = 9;
    
    private List<String[]> PMUList = null;
    
    public ProcessMemoryUtil() {
    	initPMUtil();
    }
    
    /**
     * 通过命令获取当前正在运行的进程信息
     * @return
     */
    private String getProcessRunningInfo() {
        Log.i("fetch_process_info", "start. . . . ");
        String result = null;
        CMDExecute cmdexe = new CMDExecute();
        try {
            String[] args = {"/system/bin/top", "-n", "1"};
            //相当于在/system/bin目录下执行top -n 1命令然后会列出所有正在运行的进程信息
            result = cmdexe.run(args, "/system/bin/");
        } catch (IOException ex) {
            Log.i("fetch_process_info", "ex=" + ex.toString());
        }
        return result;
    }
    
    /**
     * 解析获取的进程信息
     * @param infoString
     * @return
     */
    private int parseProcessRunningInfo(String infoString) {
        String tempString = "";
        boolean bIsProcInfo = false;
        
        String[] rows = null;
        String[] columns = null;
        rows = infoString.split("[\n]+");        // 使用正则表达式分割字符串（回车分割）
        
        for (int i = 0; i < rows.length; i++) {
            tempString = rows[i];
            if (tempString.indexOf("PID") == -1) {
                if (bIsProcInfo == true) {
                    tempString = tempString.trim();
                    columns = tempString.split("[ ]+");//表示匹配一个或多个空格（以空格为分割）
                    if (columns.length == Length_ProcStat) {
                        PMUList.add(columns);    
                    }
                }
            } else {
                bIsProcInfo = true;
            }
        }
        
        return PMUList.size();
    }
    
    /**
     *  初始化所有进程的CPU和内存列表，用于检索每个进程的信息
     */
    public void initPMUtil() {
        PMUList = new ArrayList<String[]>();
        String resultString = getProcessRunningInfo();
        parseProcessRunningInfo(resultString);
    }
    
    /**
     *  根据进程名获取CPU和内存信息
     * @param procName 进程名
     * @return 
     */
    public String getMemInfoByName(String procName) {
        String result = "";
        
        String tempString = "";
        for (Iterator<String[]> iterator = PMUList.iterator(); iterator.hasNext();) {
            String[] item = (String[]) iterator.next();
            tempString = item[INDEX_NAME];
            if (tempString != null && tempString.equals(procName)) {
            	String s = item[INDEX_RSS].substring(0, item[INDEX_RSS].length()-1);
            	double size = Double.valueOf(s);
            	String file_size;
    			if(size >=0 && size < 1024){
    				file_size = size+"KB";
    			}else{
    				file_size = new DecimalFormat("####.##").format(size/1024)+"M";
    			}
                result = "CPU:" + item[INDEX_CPU]
                       + "  内存:" + file_size;
                break;
                
            }
        }
        return result;
    }

    /**
     * 根据进程ID获取CPU和内存信息
     * @param pid
     * @return
     */
    public String getMemInfoByPid(int pid) {
        String result = "";
        
        String tempPidString = "";
        int tempPid = 0;
        int count = PMUList.size();
        for (int i = 0; i < count; i++) {
            String[] item = PMUList.get(i);
            tempPidString = item[INDEX_PID];
            if (tempPidString == null) {
                continue;
            }
            tempPid = Integer.parseInt(tempPidString);
            if (tempPid == pid) {
            	String s = item[INDEX_RSS].substring(0, item[INDEX_RSS].length()-1);
            	double size = Double.valueOf(s);
            	String file_size;
    			if(size >=0 && size < 1024){
    				file_size = size+"KB";
    			}else{
    				file_size = new DecimalFormat("####.##").format(size/1024)+"M";
    			}
                result = "CPU:" + item[INDEX_CPU]
                       + "  内存:" + file_size;
                break;
            }
        }
        return result;
    }
    
    /**
     * 根据进程ID获取内存信息
     * @param pid
     * @return
     */
    public String getMemorySizeByPid(int pid) {
        String result = "";
        
        String tempPidString = "";
        int tempPid = 0;
        int count = PMUList.size();
        for (int i = 0; i < count; i++) {
            String[] item = PMUList.get(i);
            tempPidString = item[INDEX_PID];
            if (tempPidString == null) {
                continue;
            }
            tempPid = Integer.parseInt(tempPidString);
            if (tempPid == pid) {
            	int size = item[INDEX_RSS].length(); //得到的内存大小是43K（单位k）
                result = item[INDEX_RSS].substring(0,size-1);
                break;
            }
        }
        return result;
    }
    
    /**
     *  根据进程ID获取CPU信息
     */
    public String getCPUSizeByPid(int pid) {
        String result = "";
        String tempPidString = "";
        int tempPid = 0;
        int count = PMUList.size();
        for (int i = 0; i < count; i++) {
            String[] item = PMUList.get(i);
            tempPidString = item[INDEX_PID];
            if (tempPidString == null) {
                continue;
            }
            tempPid = Integer.parseInt(tempPidString);
            if (tempPid == pid) {
                result = item[INDEX_CPU];
                break;
            }
        }
        return result;
    }
}