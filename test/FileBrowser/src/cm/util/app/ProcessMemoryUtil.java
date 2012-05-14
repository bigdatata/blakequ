package cm.util.app;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.util.Log;

/**
 * ��ȡ����ϵͳ�еĽ����������
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
     * ͨ�������ȡ��ǰ�������еĽ�����Ϣ
     * @return
     */
    private String getProcessRunningInfo() {
        Log.i("fetch_process_info", "start. . . . ");
        String result = null;
        CMDExecute cmdexe = new CMDExecute();
        try {
            String[] args = {"/system/bin/top", "-n", "1"};
            //�൱����/system/binĿ¼��ִ��top -n 1����Ȼ����г������������еĽ�����Ϣ
            result = cmdexe.run(args, "/system/bin/");
        } catch (IOException ex) {
            Log.i("fetch_process_info", "ex=" + ex.toString());
        }
        return result;
    }
    
    /**
     * ������ȡ�Ľ�����Ϣ
     * @param infoString
     * @return
     */
    private int parseProcessRunningInfo(String infoString) {
        String tempString = "";
        boolean bIsProcInfo = false;
        
        String[] rows = null;
        String[] columns = null;
        rows = infoString.split("[\n]+");        // ʹ��������ʽ�ָ��ַ������س��ָ
        
        for (int i = 0; i < rows.length; i++) {
            tempString = rows[i];
            if (tempString.indexOf("PID") == -1) {
                if (bIsProcInfo == true) {
                    tempString = tempString.trim();
                    columns = tempString.split("[ ]+");//��ʾƥ��һ�������ո��Կո�Ϊ�ָ
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
     *  ��ʼ�����н��̵�CPU���ڴ��б����ڼ���ÿ�����̵���Ϣ
     */
    public void initPMUtil() {
        PMUList = new ArrayList<String[]>();
        String resultString = getProcessRunningInfo();
        parseProcessRunningInfo(resultString);
    }
    
    /**
     *  ���ݽ�������ȡCPU���ڴ���Ϣ
     * @param procName ������
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
                       + "  �ڴ�:" + file_size;
                break;
                
            }
        }
        return result;
    }

    /**
     * ���ݽ���ID��ȡCPU���ڴ���Ϣ
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
                       + "  �ڴ�:" + file_size;
                break;
            }
        }
        return result;
    }
    
    /**
     * ���ݽ���ID��ȡ�ڴ���Ϣ
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
            	int size = item[INDEX_RSS].length(); //�õ����ڴ��С��43K����λk��
                result = item[INDEX_RSS].substring(0,size-1);
                break;
            }
        }
        return result;
    }
    
    /**
     *  ���ݽ���ID��ȡCPU��Ϣ
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