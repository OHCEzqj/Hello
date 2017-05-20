package View;


import javax.bluetooth.*;
import javax.microedition.io.*;
import javax.naming.InitialContext;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Thread.BTServerThread;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.*;


public class BTServer {
	 public static final UUID FILES_SERVER_UUID =
		        new UUID("1234567890ABCDEF1234567890ABCDEF", false);  
	 private JLabel conditionLable;
	 private JFrame jFrame;
	 private JPanel jPanel;
	 JLabel deviceLable;
	 JLabel nameLable;
	 JLabel addressLable;
	//通过连接字符串获得连接通知者
		StreamConnectionNotifier notifier = null;	
	 public static void main(String[] args)  {
		new BTServer();
	 }  
	 public BTServer() {
		init();
		
	 }

	 
	 public void init() {
		//蓝牙通信协议连接字符串
		//RFCOMM的URl规范为
		//btspp://hostname:[CN | UUID];parameters
		//L2CAPConnection的URL规范为：
		//btl2cap://hostname:[PSM | UUID];parameters

		//scheme部分
		StringBuffer url = new StringBuffer("btspp://");
		//指定服务主机（为本地机器）
		url.append("localhost").append(':');
		//服务UUID
		url.append(FILES_SERVER_UUID.toString() );
		//服务名称
		url.append(";name=FileServer");
		//安全参数（授权），还可以是：authenticate，encrypt
		url.append(";authorize=false");
		try	{
			//获取流连接通知者
			notifier = (StreamConnectionNotifier)Connector.open(url.toString() );
			//直接调用不显示组件，这里新建线程
			showframe();
			new Mythread().start();
		}
		catch (Exception e) {
//			JOptionPane.showMessageDialog(null,"请打开蓝牙"
//					,"消息对话框",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	 public void showframe(){
		 jPanel=new JPanel();
		 jPanel.setLayout(new GridLayout(3,1));//网格布局，
		 deviceLable=new JLabel();
		 nameLable=new JLabel();
		 addressLable=new JLabel();
		 jPanel.add(deviceLable);
		 jPanel.add(nameLable);
		 jPanel.add(addressLable);
		 JLabel jLabel=new JLabel("服务端已启动");
		 conditionLable=new JLabel("等待连接...");
		 
		 jFrame=new JFrame("BlueToothServer");
		 jFrame.add(jLabel, BorderLayout.NORTH);
		 jFrame.add(conditionLable, BorderLayout.CENTER);
		 jFrame.add(jPanel, BorderLayout.SOUTH);
		 jFrame.pack();
		 jFrame.setLocationRelativeTo(null);	
		 jFrame.setVisible(true);
	 }

	 class Mythread extends Thread{
		 @Override		 
			public void run() {
				
					StreamConnection conn = null;
					//这里添加一个线程			
					try	{
						//System.out.println("Wait for client connection...");
						//等待接受客户端的连接
						conn = notifier.acceptAndOpen();
						
					}
					catch (IOException e) {
						e.printStackTrace();
								
					}
					//获取远程设备地址
					RemoteDevice remoteDevice;
					try {
						remoteDevice = RemoteDevice.getRemoteDevice(conn);
						//从远程设备获取信息
						System.out.println("FriendlyName: " + remoteDevice.getFriendlyName(false) );
						System.out.println("Address: " + remoteDevice.getBluetoothAddress() );			 
							conditionLable.setText("已连接"); 
						    deviceLable.setText("设备信息:");
							nameLable.setText("Name:  "+remoteDevice.getFriendlyName(false));
							addressLable.setText("Mac Address:  "+remoteDevice.getBluetoothAddress());						
							jFrame.repaint();
							jFrame.pack();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//启动服务线程
					new BTServerThread(conn).start();
				
			}
	 }
};
