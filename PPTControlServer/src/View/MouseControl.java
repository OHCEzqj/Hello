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

import assit.ByteAndInt;
import assit.Constant;
import assit.TextZXing;



public class MouseControl  {
	private byte cmdBuffer[] = new byte[Constant.bufferSize];
	private byte recvBuffer[] = new byte[Constant.bufferSize];
	 private  DatagramSocket socket=null;
	 //定义客户端的IP信息
	 private SocketAddress socketaddress;
	 //使用log
	 static Logger logger = Logger.getLogger("my log");
	 
	 //蓝牙
	 // 流连接通知 用于创建下面流连接  
	 private StreamConnectionNotifier myPCConnNotifier = null;  
	 // 流连接  
	 private StreamConnection streamConn = null;  
	 // 读取（输入）流  
	 private InputStream inputStream = null; 
	 private BlueToothSocket bluetoothsocket=null;
	

	//win32api 声明
	private NativeLibrary win32Lib = null;//win32库对象
	/**
	 * 其实例表示指向某些本地函数的指针，是调用这些函数的主要方法
	 */
	private Function mouseFun = null;//鼠标操作函数
	private Function keyFun = null;//键盘操作函数
	private StartSocket socketrun = null;
	 private String iptext="111.111.111.111";
	 private String condition="未连接";
	 private JFrame f;
	 private JLabel label2;
	/**
	 * 测试
	 */
	public MouseControl(String connectMode){        
	/**
	 * NativeLibrary类的getInstance函数
	 * 对于特定的参数返回一个本地库的实例对象，如果该本地库没有被加载，那么会加载该本地库，如果已经加载，那么直接返回已经存在的实例对象
	 * 参数：要被加载的库名称
	 */
	win32Lib = NativeLibrary.getInstance("User32");
	/**
	 * getFunction(String functionName)
	 * 创建一个与本地库相关联的新的函数，该实例代表一个指针，指向本地库中指定的函数
	 * 参数：需要指定的函数的名称
	 */
	/**
	 * mouse_event
	 * windows API中的mouse_event函数，存在user32.dll这个库文件里面
	 */
	mouseFun = win32Lib.getFunction("mouse_event");
	keyFun = win32Lib.getFunction("keybd_event");
	System.out.println("MouseControl()");
	if(connectMode.equals("NetWork")){
		System.out.println("connectMode:NetWork");
		//showframe();
		socketrun = new StartSocket();	
		socketrun.run();	
	}else if (connectMode.equals("BlueTooth")) {
		System.out.println("connectMode:BlueTooth");
		//蓝牙
		  try {  
	            // 得到流连接通知，下面的UUID必须和手机客户端的UUID相一致。
			  //创建并打开蓝牙服务
	            myPCConnNotifier = (StreamConnectionNotifier) Connector  
	                    .open("btspp://localhost:0000110100001000800000805F9B34FB"); 
	            System.out.println("连接成功！");
	        } catch (IOException e) {  
	            // TODO Auto-generated catch block 
	        	System.out.println("创建并打开蓝牙服务失败！");
	            e.printStackTrace();  
	        } 
		  bluetoothsocket=new BlueToothSocket();
		  bluetoothsocket.run();
	}//else if
		        
}

	//模拟键盘操作
	public void excuteKeyCMD(byte[] cmd){
		switch(cmd[1]){
		case Constant.KEY_ARROW_LEFT://左方向键
			//robot.keyPress(KeyEvent.VK_LEFT);
			//robot.keyRelease(KeyEvent.VK_LEFT);
			/**
			 * invoke(Object[] args)
			 * 调用该对象所代表的本地函数
			 */
			keyFun.invoke(new Object[]{Constant.KEY_ARROW_LEFT,0,0,0});//按下
	        keyFun.invoke(new Object[]{Constant.KEY_ARROW_LEFT,0,2,0});//弹起
			break;
		case Constant.KEY_ARROW_RIGHT://右方向键
			//robot.keyPress(KeyEvent.VK_RIGHT);
			//robot.keyRelease(KeyEvent.VK_RIGHT);
			keyFun.invoke(new Object[]{Constant.KEY_ARROW_RIGHT,0,0,0});
			keyFun.invoke(new Object[]{Constant.KEY_ARROW_RIGHT,0,2,0});
			break;
		case Constant.KEY_ARROW_UP://上方向键
			logger.info("robot 上方向键");
			//	robot.keyPress(KeyEvent.VK_UP);
			//robot.keyRelease(KeyEvent.VK_UP);
			keyFun.invoke(new Object[]{Constant.KEY_ARROW_UP,0,0,0});
			keyFun.invoke(new Object[]{Constant.KEY_ARROW_UP,0,2,0});
			break;
		case Constant.KEY_ARROW_DOWN://下方向键
			//robot.keyPress(KeyEvent.VK_DOWN);
			//robot.keyRelease(KeyEvent.VK_DOWN);
			keyFun.invoke(new Object[]{Constant.KEY_ARROW_DOWN,0,0,0});
			keyFun.invoke(new Object[]{Constant.KEY_ARROW_DOWN,0,2,0});
			break;
		case Constant.KEY_ESC://ESC键
			//robot.keyPress(KeyEvent.VK_ESCAPE);
			//robot.keyRelease(KeyEvent.VK_ESCAPE);
			keyFun.invoke(new Object[]{Constant.KEY_ESC,0,0,0});
			keyFun.invoke(new Object[]{Constant.KEY_ESC,0,2,0});
			break;
		case Constant.KEY_F5://F5键
			//robot.keyPress(KeyEvent.VK_F5);
			//robot.keyRelease(KeyEvent.VK_F5);
			keyFun.invoke(new Object[]{Constant.KEY_F5,0,0,0});
			keyFun.invoke(new Object[]{Constant.KEY_F5,0,2,0});
			break;
		}
	}
	//模拟鼠标操作
	public void excuteMouseCMD(byte[] cmd){
		switch(cmd[1]){
		case Constant.MOUSE_LEFT://鼠标左键
			mouseFun.invoke(new Object[]{Constant.MOUSEEVENT_LEFTDOWN | Constant.MOUSEEVENT_LEFTUP, 0, 0, 0, 0 });
			//mouseFun.invoke(new Object[]{Constant.MOUSEEVENT_LEFTDOWN});//按下
			//mouseFun.invoke(new Object[]{Constant.MOUSEEVENT_LEFTUP});//弹起
			break;
		case Constant.MOUSE_RIGHT://鼠标右键
			mouseFun.invoke(new Object[]{Constant.MOUSEEVENT_RIGHTDOWN});
			mouseFun.invoke(new Object[]{Constant.MOUSEEVENT_RIGHTUP});
			break;
		case Constant.MOUSE_CENTER://鼠标中键
			mouseFun.invoke(new Object[]{Constant.MOUSEEVENT_MIDDLEDOWN});
			mouseFun.invoke(new Object[]{Constant.MOUSEEVENT_MIDDLEUP});
			break;
		case Constant.MOUSE_GESTURE://把移动端传过来的滑动距离转换成鼠标移动
			byte[]xValue = new byte[4];
			byte[]yValue = new byte[4];
			System.arraycopy(cmd, 4, xValue, 0, 4);
			System.arraycopy(cmd, 8, yValue, 0, 4);
			int x = ByteAndInt.byteArray2Int(xValue);
			int y = ByteAndInt.byteArray2Int(yValue);
			mouseFun.invoke(new Object[]{Constant.MOUSEEVENT_MOVE,x,y,0,0});//屏幕相对坐标,鼠标相对当前位置移动
		//	mouseFun.invoke(new Object[]{Constant.MOUSEEVENT_ABSOLUTE|Constant.MOUSEENENT_MOVE,x*rateWidth,y*rateHeight,0,0});//绝对坐标系统,鼠标按绝对坐标移动
			break;
		case  Constant.MOUSE_WHELL_UP:
			logger.info("滚轮即将向上滑动");
			//robot.mouseWheel(2);
			mouseFun.invoke(new Object[]{Constant.MOUSEEVENT_WHELL,0,0,120,0});
			logger.info("滚轮向上滑动");
			break;
		case Constant.MOUSE_WHELL_DOWN:
			logger.info("滚轮即将向下滑动");
			mouseFun.invoke(new Object[]{Constant.MOUSEEVENT_WHELL,0,0,-120,0});
			break;
			
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
	/**
	 * 处理Media信息
	 */
	public void excuteMedia(byte[] cmd){
		switch(cmd[1]){
		case Constant.MEDIA_VOLUME_UP:
			//处理:F9
			keyFun.invoke(new Object[]{Constant.KEY_F9,0,0,0});//按下
	        keyFun.invoke(new Object[]{Constant.KEY_F9,0,2,0});//弹起
			break;
			
		case Constant.MEDIA_VOLUME_DOWN:
			//处理:F8
			keyFun.invoke(new Object[]{Constant.KEY_F8,0,0,0});//按下
	        keyFun.invoke(new Object[]{Constant.KEY_F8,0,2,0});//弹起
			break;
		case Constant.MEDIA_VOLUME_SLIENCE:
			//处理:F7
			keyFun.invoke(new Object[]{Constant.KEY_F7,0,0,0});//按下
	        keyFun.invoke(new Object[]{Constant.KEY_F7,0,2,0});//弹起
			break;
		case Constant.MEDIA_PLAY:
			//处理:Ctrl+P
			keyFun.invoke(new Object[]{Constant.KEY_CTRL,0,0,0});//按下
			keyFun.invoke(new Object[]{Constant.KEY_P,0,0,0});//按下
	        keyFun.invoke(new Object[]{Constant.KEY_CTRL,0,2,0});//弹起
	        keyFun.invoke(new Object[]{Constant.KEY_P,0,2,0});//弹起
	        logger.info("播放media");
	  
			break;
		case Constant.MEDIA_FF:
			//处理:Ctrl+Shift+F
			keyFun.invoke(new Object[]{Constant.KEY_CTRL,0,0,0});//按下
			keyFun.invoke(new Object[]{Constant.KEY_SHIFT,0,0,0});//按下
			keyFun.invoke(new Object[]{Constant.KEY_F,0,0,0});//按下
	        keyFun.invoke(new Object[]{Constant.KEY_CTRL,0,2,0});//弹起
	        keyFun.invoke(new Object[]{Constant.KEY_SHIFT,0,2,0});//弹起
	        keyFun.invoke(new Object[]{Constant.KEY_F,0,2,0});//弹起
			break;
		case Constant.MEDIA_REW:
			//处理:Ctrl+Shift+B
			keyFun.invoke(new Object[]{Constant.KEY_CTRL,0,0,0});//按下
			keyFun.invoke(new Object[]{Constant.KEY_SHIFT,0,0,0});//按下
			keyFun.invoke(new Object[]{Constant.KEY_B,0,0,0});//按下
	        keyFun.invoke(new Object[]{Constant.KEY_CTRL,0,2,0});//弹起
	        keyFun.invoke(new Object[]{Constant.KEY_SHIFT,0,2,0});//弹起
	        keyFun.invoke(new Object[]{Constant.KEY_B,0,2,0});//弹起
			break;
		case Constant.MEDIA_NEXT:
			//处理:Ctrl+B
			keyFun.invoke(new Object[]{Constant.KEY_CTRL,0,0,0});//按下
			keyFun.invoke(new Object[]{Constant.KEY_B,0,0,0});//按下
	        keyFun.invoke(new Object[]{Constant.KEY_CTRL,0,2,0});//弹起
	        keyFun.invoke(new Object[]{Constant.KEY_B,0,2,0});//弹起
			break;
		case Constant.MEDIA_PREVIOUS:
			//处理:Ctrl+F
			keyFun.invoke(new Object[]{Constant.KEY_CTRL,0,0,0});//按下
			keyFun.invoke(new Object[]{Constant.KEY_F,0,0,0});//按下
	        keyFun.invoke(new Object[]{Constant.KEY_CTRL,0,2,0});//弹起
	        keyFun.invoke(new Object[]{Constant.KEY_F,0,2,0});//弹起
			break;
		case Constant.MEDIA_SCREEN:
			//处理:Alt+Enter
			keyFun.invoke(new Object[]{Constant.KEY_ALT,0,0,0});//按下
			keyFun.invoke(new Object[]{Constant.KEY_ENTER,0,0,0});//按下
	        keyFun.invoke(new Object[]{Constant.KEY_ALT,0,2,0});//弹起
	        keyFun.invoke(new Object[]{Constant.KEY_ENTER,0,2,0});//弹起
			break;
		}
		
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
			win32Lib.dispose();
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
				 
			//	multicastSocket = new MulticastSocket(Constant.commPort);
			//	inetAddress = InetAddress.getByName(Constant.multicastIp);
		   //     multicastSocket.joinGroup(inetAddress);
				socket=new DatagramSocket(Constant.commPort);
				iptext=InetAddress.getLocalHost().getHostAddress();
		        System.out.println("Socket started...");
		        System.out.println("连接该IP:"+iptext);
		       // iptext=socket.getInetAddress().getLocalHost();
		        
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
			        	
			        	parsePackage(recvBuffer);
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
	ControlMethod controlMethod=new ControlMethod();
	//蓝牙接受线程
	private class BlueToothSocket implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("BlueToothSocket");
			try {          	
	            // 持续保持着监听客户端的连接请求  
	            while (true) {  
	                // 获取流连接  ，等待客户端的连接
	                streamConn = myPCConnNotifier.acceptAndOpen();  
	                RemoteDevice remoteDevice=RemoteDevice.getRemoteDevice(streamConn);
	                System.out.println("name:" +remoteDevice.getFriendlyName(false));
	                System.out.println("Address: "+remoteDevice.getBluetoothAddress());
	                // 获取流通道  
	                inputStream = streamConn.openInputStream();  	                
	                // 读取字节流  ，读取inputStream数据存放到acceptByteArray中             
	                while (inputStream.read(recvBuffer) != -1) { 
	                	System.out.println("蓝牙信息接收");
	                	controlMethod.parsePackage(recvBuffer);
	                	
	                                        
	                }  
	            }  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } 
		}
		}
	
	//数据包解析模块
			private void parsePackage(byte[] rec){
				System.out.println("类内解析");
				int eventType = rec[0];
				switch(eventType){
				case Constant.RULE_MOUSE_EVENT://鼠标事件
					excuteMouseCMD(rec);			
					//System.out.println("解析：鼠标事件");
					break;
				case Constant.RULE_KEY_EVENT://键盘事件
					excuteKeyCMD(rec);
					break;
				case Constant.RULE_CONDITION://接受联系初始信息
					System.out.println("解析：状态事件");
					excuteConditionCMD(rec);
					break;
				case Constant.RULE_SCREEN_PIX://鼠标滑动事件，计算屏幕象素与标准坐标系统的换算比例
					byte sw[]=new byte[4];
					byte sh[]=new byte[4];
					System.arraycopy(rec, 1, sw, 0, 4);//屏幕宽
					System.arraycopy(rec, 5, sh, 0, 4);//屏幕高
					Math.round(65535/(ByteAndInt.byteArray2Int(sw)));
					Math.round(65535/(ByteAndInt.byteArray2Int(sh)));
					break;
				case Constant.RULE_MEDIA:
					excuteMedia(rec);
					break;
				
				}
			}
}
