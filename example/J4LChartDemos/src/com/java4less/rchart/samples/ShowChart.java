package com.java4less.rchart.samples;

import java.io.IOException;
import java.io.InputStream;

import com.java4less.rchart.Chart;
import com.java4less.rchart.ChartAdapter;
import com.java4less.rchart.ChartListener;
import com.java4less.rchart.ChartAdapter;
import com.java4less.rchart.ChartListener;
import com.java4less.rchart.ChartLoader;
import com.java4less.rchart.android.ChartPanel;
import com.java4less.rchart.gc.android.ChartAndroidImage;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class ShowChart extends Activity {
	
	private static final String TAG = "j4lchart";
	ChartLoader cha=new ChartLoader();
	ChartPanel chartPanel;
	

	// create chart listener 
	ChartAdapter chartAdapter=new ChartAdapter() {
					public void chartEvent(Chart c,int type) {

						// rebuild chart with new data
						if (type==ChartListener.EVENT_BEFORE_UPDATE) {
							// // get a new random value between 0 and 40 and update 3 of the values in the serie
							int newValue=(int) (System.currentTimeMillis() % 40); 
							c.plotters[0].getSerie(0).replaceYValueAt(1,newValue);
							c.plotters[0].getSerie(0).replaceYValueAt(2,newValue+10);
							c.plotters[0].getSerie(0).replaceYValueAt(3,newValue-10);							

							// do not allow the chart to be rebuilt from the original parameters
							c.autoRebuild=false;
							
						}						
						
					}
				};	
					
				
				// create chart listener for the drill down chart
				ChartAdapter chartAdapterRealtime=new ChartAdapter() {
									public void chartEvent(Chart c,int type) {
																			
										// load subchart
										if (type==ChartListener.EVENT_BEFORE_UPDATE) createRealtimedata(cha);
									}
								};	

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
    	 cha=new ChartLoader();
    		// this is to be able to load the images
    	    ChartAndroidImage.assets=getAssets();
    	    
    	    String chartfile=this.getIntent().getStringExtra("com.java4less.rchart.samples.file");
	        super.onCreate(icicle);

	        //setContentView(R.layout.main);
	        chartPanel = new ChartPanel(this);

	        try {
				chartPanel.setChart(loadFromAsset(chartfile));
			} catch (Exception e) {
				Log.e(ChartPanel.TAG,"Could not lod chart from file.",e);
			}
	        
	        setContentView(chartPanel);        
	    }
    
    	protected void onDestroy() {
    		
    		if (chartPanel!=null) 
    				if (chartPanel.getChart()!=null) chartPanel.getChart().stopUpdater();
    		
    		super.onDestroy();
    	}	
 
	    
        /**
         * load chart definition from file
         * @param name
         * @return
         */  
        public Chart loadFromAsset(String name) throws IOException {
        	
            InputStream is = getAssets().open(name);        	
            	
            cha.loadFromFile(is, true);
            
			Log.i(ChartPanel.TAG,"Building chart ...");

			Chart c=cha.build(false,false);	
			c.setWidth(300);
			c.setHeight(300);

			// set listener for realtime update example
			if (name.equals("barChart3D.txt")) c.addChartListener(chartAdapter);				
			if (name.equals("realtime.txt")) c.addChartListener(chartAdapterRealtime);
						

			
						
			
			// real time update example, start update thread
			if ((name.equals("barChart3D.txt")) || (name.equals("realtime.txt")))  c.startUpdater();				
			
			Log.i(ChartPanel.TAG,"Build ok");			
			
			return c;            
        }
    
	    public Chart createDefaultChart() {

			System.out.println("Creating loader ...");

			ChartLoader cha=new ChartLoader();			
			cha.clearParams();				
			
			cha.setParameter("TITLECHART","Sales 2006");
			cha.setParameter("TITLE_FONT","Serif|BOLD|12");
			cha.setParameter("LEGEND","NO");
			cha.setParameter("XSCALE_MIN","0");
			cha.setParameter("XSCALE_MAX","5.5");
			cha.setParameter("YSCALE_MIN","-15");
			cha.setParameter("BIG_TICK_INTERVALX","1");
			cha.setParameter("BIG_TICK_INTERVALY","1");
			cha.setParameter("TICK_INTERVALY","10");		
			cha.setParameter("XAXIS_LABELS","June| |Aug.| |Oct.| |Dec.");
			cha.setParameter("CERO_XAXIS","LINE");
			cha.setParameter("YAXIS_INTEGER","TRUE");
			cha.setParameter("SERIE_1","Products");
			cha.setParameter("SERIE_2","Services");
			cha.setParameter("SERIE_TYPE_1","BAR");
			cha.setParameter("SERIE_TYPE_2","BAR");
			cha.setParameter("SERIE_FONT_1","Arial|PLAIN|7");
			cha.setParameter("SERIE_FONT_2","Arial|BOLD|7");
			cha.setParameter("BOTTOM_MARGIN","0.18");
			cha.setParameter("LEFT_MARGIN","0.2");
			cha.setParameter("RIGHT_MARGIN","0");
			cha.setParameter("LEGEND_MARGIN","0");
			cha.setParameter("SERIE_DATA_2","-10|41|48|39|36");
			cha.setParameter("SERIE_BORDER_TYPE_1","RAISED");
			cha.setParameter("SERIE_BORDER_TYPE_2","RAISED");
			cha.setParameter("SERIE_BAR_STYLE_1","0xff00");
			cha.setParameter("SERIE_BAR_STYLE_2","0xff");
			cha.setParameter("BARCHART_BARSPACE","1");		
			cha.setParameter("CHART_FILL","0xffcc00");
			cha.setParameter("SERIE_NEGATIVE_STYLE_2","RED");
			cha.setParameter("YLABEL_VERTICAL","TRUE");
			cha.setParameter("SERIE_DATA_1","12|43|50|45|30");
			cha.setParameter("BARCHART_BARSPACE","5");
			cha.setParameter("SERIE_FORMAT_1","0");				
		

			Log.i(ChartPanel.TAG,"Building chart ...");

			Chart c=cha.build(false,false);	
			c.setWidth(300);
			c.setHeight(300);

			
			Log.i(ChartPanel.TAG,"Build ok");			
			
			return c;


		}
	    
	    /**
		 * this method creates realtime data for the realtime example every 2 seconds
		 *
		 */
	   private void createRealtimedata(ChartLoader loader) {
			//	create chart

			//	set initial value of the X Axis based on the time
			java.util.Date now=java.util.Calendar.getInstance().getTime();
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("dd/MM/yy hh:mm:ss");

			loader.setParameter("XAXIS_INITIAL_DATE",formatter.format(now));


			//	create random data based on the time
			java.util.Calendar cal=java.util.Calendar.getInstance();
			String data1="";
			String data2="";
			for (int i=0;i<9;i++) {		
				cal.add(java.util.Calendar.SECOND,-2);
				if (data1.length()>0) data1="|" + data1;
				data1=""+cal.get(java.util.Calendar.SECOND)+ data1;
				if (data2.length()>0) data2="|" + data2;
				data2=""+(cal.get(java.util.Calendar.SECOND)/6)+ data2;		
			}

			loader.setParameter("SERIE_DATA_1",data1);
			loader.setParameter("SERIE_DATA_2",data2);
			

	   }    
		
}