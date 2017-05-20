package View;


import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Logger;

import org.omg.SendingContext.RunTime;

import com.sun.jna.Function;
import com.sun.jna.NativeLibrary;

import Thread.Send;
import assit.ByteAndInt;
import assit.Constant;

/**
 * 这里只将parsePackage函数暴露，具体操作私有
 * @author zqj
 *
 */
public class ControlMethod {
	 //使用log
	 static Logger logger = Logger.getLogger("my log");
	//win32api 声明
	private NativeLibrary win32Lib = null;//win32库对象
	/**
	 * 其实例表示指向某些本地函数的指针，是调用这些函数的主要方法
	 */
	private Function mouseFun = null;//鼠标操作函数
	private Function keyFun = null;//键盘操作函数
	public ControlMethod() {
		// TODO Auto-generated constructor stub
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
	}
	//数据包解析模块
	public void parsePackage(byte[] rec){
		int eventType = rec[0];
		System.out.println("解析：rec[0]");
		switch(eventType){
		case Constant.RULE_MOUSE_EVENT://鼠标事件
			excuteMouseCMD(rec);			
			//System.out.println("解析：鼠标事件");
			break;
		case Constant.RULE_KEY_EVENT://键盘事件
			excuteKeyCMD(rec);
			break;
//		case Constant.RULE_CONDITION://接受联系初始信息
//			System.out.println("解析：状态事件");
//			excuteConditionCMD(rec);
//			break;
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
		case Constant.RULE_SCreen:
			excuteScreen(rec);
			break;
		
		}
	}
	//模拟键盘操作
		private void excuteKeyCMD(byte[] cmd){
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
		private void excuteMouseCMD(byte[] cmd){
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

		/**
		 * 处理Media信息
		 */
		private void excuteMedia(byte[] cmd){
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
		private void excuteScreen(byte[] cmd) {
			switch(cmd[1]){
			case Constant.Screen_win:
				//处理:F9
				keyFun.invoke(new Object[]{Constant.KEY_WIN,0,0,0});//按下
		        keyFun.invoke(new Object[]{Constant.KEY_WIN,0,2,0});//弹起
				break;
			case Constant.Screen_ctrlalt:
				//处理:F9
				logger.info("Screen_ctrlalt");
				 try {
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ALT);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_DELETE);
					robot.keyRelease(KeyEvent.VK_ALT);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					robot.keyRelease(KeyEvent.VK_DELETE);
					logger.info("Screen_ctrlalt完成");
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				 
//				keyFun.invoke(new Object[]{Constant.KEY_CTRL,0,0,0});//按下
//				keyFun.invoke(new Object[]{Constant.KEY_ALT,0,0,0});//按下
//				keyFun.invoke(new Object[]{Constant.KEY_DEL,0,0,0});//按下	
//		        keyFun.invoke(new Object[]{Constant.KEY_CTRL,0,2,0});//弹起
//		        keyFun.invoke(new Object[]{Constant.KEY_ALT,0,2,0});//弹起
//		        keyFun.invoke(new Object[]{Constant.KEY_DEL,0,2,0});//弹起
		        logger.info("Screen_ctrlalt完成");
				break;
			case Constant.Screen_shutdown:
				try {
					Runtime.getRuntime().exec("shutdown -s -f -t 10");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				break;
			case Constant.Screen_cmd:
				try {
					Runtime.getRuntime().exec("cmd /k start cmd");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				break;
			case Constant.Screen_logoff:
				try {
					Runtime.getRuntime().exec("cmd /k start logoff");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				break;
			case Constant.Screen_explorer:
				try {
					Runtime.getRuntime().exec("cmd /k start explorer");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				break;
			case Constant.Screen_nptepad:
				try {
					Runtime.getRuntime().exec("cmd /k start notepad");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				break;
			case Constant.Screen_mspaint:
				try {
					Runtime.getRuntime().exec("cmd /k start mspaint");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				break;
			case Constant.Screen_taskmgr:
				try {
					Runtime.getRuntime().exec("cmd /k start taskmgr");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				break;
			case Constant.Screen_Nslookup:
				try {
					Runtime.getRuntime().exec("cmd /k start Nslookup");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				break;
				
		
			}
		}
		
}
