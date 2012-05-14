import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import util.ConstantValues;


public class ServerWindow extends Frame {
	private FileServer s = new FileServer(ConstantValues.PORT);
	private Label label;
	
	public ServerWindow(String title)
	{
		super(title);
		label = new Label();
		add(label, BorderLayout.PAGE_START);
		label.setText("服务器已经启动");
		this.addWindowListener(new WindowListener() 
		{
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				s.quit();
				 System.exit(0);
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				new Thread(new Runnable()
				{			
					@Override
					public void run() 
					{
						try 
						{
							s.start();
						}
						catch (Exception e) 
						{
							e.printStackTrace();
						}
					}
				}).start();
			}
		});
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		ServerWindow window = new ServerWindow("文件上传服务端"); 
		window.setSize(300, 300); 
		window.setVisible(true);
	}
}
