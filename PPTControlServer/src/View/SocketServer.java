package View;



import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;

import java.util.logging.Logger;

import javax.bluetooth.RemoteDevice;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.google.zxing.WriterException;
import com.sun.jna.Function;
/**
 * 提供对本地库资源的管理，该类的实例对象对应于一个已经被加载的本地库
 */
import com.sun.jna.NativeLibrary;

import Thread.*;
import assit.ByteAndInt;
import assit.Constant;
import assit.TextZXing;



public class SocketServer  {
	 private byte recvBuffer[] = new byte[Constant.bufferSize];
	 private byte cmdBuffer[] = new byte[Constant.bufferSize];
	 private  DatagramSocket socket=null;
	 //定义客户端的IP信息
	 private SocketAddress socketaddress;
	 //使用log
	 static Logger logger = Logger.getLogger("my log");
	 private StartSocket socketrun = null;
	 private String iptext="111.111.111.111";
	 private String condition="未连接";
	 private JFrame f;
	 private JLabel label2;

	 private boolean ThreadFlag=true;
	 private ControlMethod controlMethod;
	 public SocketServer(){ 
		 controlMethod=new ControlMethod();
			socketrun = new StartSocket();	
			socketrun.run();	
       }	
/**
 * 显示socket服务器窗口
 */
	public void showframe(){
		//North放置一个Panel
		JPanel p=new JPanel();
		p.setLayout(new GridLayout(2,1,4,4));//网格布局，
		// panel里有：连接状态；IP
		label2 = new JLabel(condition);
		System.out.println("showframe:   "+condition);
		label2.setFont(new Font("宋体", Font.PLAIN, 16));
		JLabel label = new JLabel("连接该IP:    "+iptext);
		label.setFont(new Font("宋体", Font.PLAIN, 16));
		//添加
		p.add(label2);
		p.add(label);
		//Center：二维码
		JLabel label3 = new JLabel();
		try {
			label3.setIcon(new ImageIcon(TextZXing.createQRCode(iptext, 250)));
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
			
		//Sourth：退出按钮
		JButton exitBtn = new JButton("退出服务端");
		exitBtn.setFont(new Font("宋体", Font.PLAIN, 16));
		exitBtn.addActionListener(listener);
		//向容器添加组件
		/*
		 * 关于BorderLayout，
		 * 分为五个方向，向容器添加组件时，要指明哪个方向，同一个方向多个组件，后放的覆盖之前的，只能显示一个组件
		 * Frame(窗口，window子类，Panel容器正常放在该容器中),Dialog,ScrollPane默认使用该布局管理器
		 * 最多放5个组件，但我们可以在某一方向区域放Panel，在Panel里放多个其他组件，这样就远远多于5个
		 */
		f=new JFrame("PPT远程控制服务器端");
		f.add(p, BorderLayout.NORTH);
		f.add(label3, BorderLayout.CENTER);
		f.add(exitBtn, BorderLayout.SOUTH);
		//测试用，返回容器内组件数量
		System.out.println("组件数量："+f.getComponentCount());	
		//440 宽度刚好
		f.setSize(320, 400);
		//f.setMinimumSize(new Dimension(440,180));
		//f.pack();
		//用户是否调整窗口大小
	//	f.setResizable(false);	
		//设置窗口相对于指定组件的位置。如果组件当前未显示或者 c 为 null，则此窗口将置于屏幕的中央。
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
	}
	public void showBlueToothFrame(){
		
	}
	ActionListener listener=new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			exitSystem();
		}
	};

	//退出服务器
	private void exitSystem(){
		int n = JOptionPane.showConfirmDialog(null, "你真的要退出服务器吗？","退出确认", JOptionPane.YES_NO_OPTION);  
        if (n == JOptionPane.YES_OPTION) {  
        	System.out.println("Server being exiting ...");		
			socketrun.closeSocket();
			System.exit(0);
        } 
    
	}
	//socket初始化类
	private class StartSocket implements Runnable{
		/**
		 * 第一个字节用来区分事件类型（1为鼠标，2为键盘）
		 * 第二个字节用来存放操作指令
		 * 第三个字节用来存放操作指令值
		 * 第四个字节为预留
		 * 第五-12字节为座标值
		 */
			   
	    //打开socket以侦听数据
		@Override
		public void run() {
			try {
				 System.out.println("socket run()");
				 
		
				socket=new DatagramSocket(Constant.commPort);
				
				iptext=InetAddress.getLocalHost().getHostAddress();
			
				
//     			String ipText=socket.getInetAddress().getHostAddress();
//				System.out.println("Socket ip..."+ipText);
				
		        System.out.println("Socket started...");
		        System.out.println("连接该IP:"+iptext);
		      
		        
		        condition="连接状态:    "+"未连接";
		        showframe();

		    	new Thread(){
					
					public void run(){
						while (!socket.isClosed()) { 
						for (int i=0;i<Constant.bufferSize;i++){recvBuffer[i]=0;}
			        	DatagramPacket rdp = new DatagramPacket(recvBuffer, recvBuffer.length);
			        	try {
							socket.receive(rdp);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        	socketaddress=rdp.getSocketAddress();
			        	System.out.println("Phone IP: "+socketaddress.toString());
			        	if (recvBuffer[0]==Constant.RULE_CONDITION) {
			        		System.out.println("解析：状态事件");
			        		excuteConditionCMD(recvBuffer);
						}else if (recvBuffer[1]==Constant.Screen_init) {
							if (ThreadFlag) {
								 new Send(rdp.getAddress()).start();
								 ThreadFlag=false;
							}
						   
							
						}
			        	else{
							controlMethod.parsePackage(recvBuffer);
						}
			        	
					}
					}
				}.start();
			} catch (Exception e) {
				try {
					if(null!=socket && !socket.isClosed()){
				       socket.close();
					}
				} catch (Exception e1) {
				}
			}
			
		}
		//关闭socket连接
		public void closeSocket(){
			try{
				if(null!=socket && !socket.isClosed()){
				//	multicastSocket.leaveGroup(inetAddress);
					//multicastSocket.close();
					socket.close();
					System.out.println("Socket is closed ...");
				}
			}catch(Exception e){
			}
		}
		
	}
	
	//收到初始连接信息，服务器反馈
	public void excuteConditionCMD(byte[] cmd){
		switch(cmd[1]){
		case Constant.CONDITION_CONNECT:
			//更新服务器窗口
			condition="连接状态:    "+"已连接";
			System.out.println("excuteConditionCMD()：收到初始连接信息");
			label2.setText(condition);
			f.repaint();
			//发送反馈到客户端
			cmdBuffer[0]=Constant.RULE_FEEDBACK;
			cmdBuffer[1]=Constant.FEEDBACK_INIT;
			DatagramPacket pack = new DatagramPacket(cmdBuffer, Constant.bufferSize,socketaddress);
			logger.info("即将发送反馈");
			try {
				socket.send(pack);
				logger.info("反馈发送成功");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.info("反馈发送失败");
				e.printStackTrace();
			}
			
		break;
		}
		}
}
