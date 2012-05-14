package com.java4less.rchart.samples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.java4less.rchart.Chart;
import com.java4less.rchart.ChartAdapter;
import com.java4less.rchart.ChartListener;
import com.java4less.rchart.ChartLoader;
import com.java4less.rchart.android.ChartPanel;
import com.java4less.rchart.samples.ShowChart;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class J4LChartDemos extends ListActivity  {
	
	private static final String TAG = "J4LChartDemos";
	
	String[] examples={"combined2Positions.txt","patternFilling.txt","linechart.txt","barChart3D.txt","targetZones.txt","MultiCharts.txt","radarChart.txt","scatterChart.txt","areaChart.txt","areaCurves.txt","axisOnTop.txt","barChart.txt","barImages.txt","bubbleChart.txt","candleStick.txt",
			"combinedChart.txt","event.txt","gauge.txt","linechart3D.txt","linechartClipping.txt","linechartNULLS.txt",
			"pieChart2D.txt","piechart3D.txt","realtime.txt","stackedBar.txt",
			"StackedMultiAxis.txt","MultiAxis.txt"}; 	
	
	String[] labels={"Combined","Curves and notes","Linechart","Barchart 3D (update)","Target zones","Multicharts","Radarchart","Scatterchart","Areachart","Curves","Axis on Top","Barchart","Bars and Images","BubbleChart","Candlestick",
			"Combined 2","Events","Gauge","Linechart 3D","Linechart & Clipping","Linechart and nulls",
			"Piechart 2D","Piechart 3D","Realtime","Stacked bars",
			"Stacked Multiaxis","MultiAxis"}; 	
	


		
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {

        super.onCreate(icicle);
        
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        
        
        for (int i=0; i<examples.length;i++)
            addItem(list, labels[i], activityIntent(examples[i]));
      
        setListAdapter(new SimpleAdapter(this,list,android.R.layout.simple_list_item_1,new String[] { "title" },new int[] { android.R.id.text1 }));
        
   /*     setListAdapter(new SimpleAdapter((Context) this, list,
                android.R.layout.simple_list_item_1, new String[] { "title" },
                new int[] { android.R.id.text1 }));
                */       
	    }
    

    protected Intent activityIntent(String fileName) {
        Intent result = new Intent();
        result.setClass(this.getApplication(),ShowChart.class);
        result.putExtra("com.java4less.rchart.samples.file", fileName);
        return result;
    }

    protected void addItem(List<Map<String,Object>> data, String name, Intent intent) {
        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("title", name);
        temp.put("intent", intent);
        data.add(temp);
    }
    
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Map map = (Map) l.getItemAtPosition(position);

        Intent intent = (Intent) map.get("intent");
        startActivity(intent);
    }    
	  	   
    
   
}