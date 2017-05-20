package Thread;

//
import java.io.*;
import javax.microedition.io.*;

import View.ControlMethod;
import assit.Constant;


public class BTServerThread extends Thread {
	private StreamConnection conn = null;
	private byte recvBuffer[] = new byte[Constant.bufferSize];
	ControlMethod controlMethod=new ControlMethod();
	//-------------------------------------------------------------------------
	public BTServerThread(StreamConnection __conn) {
		conn = __conn;		
	}
	
	public void run() {
		receive(); //获取请求信息
	//	sendFile(fileName); //发送答复						
	}

	//从客户端读取
	private void receive() {
		try	{
			InputStream is = conn.openInputStream();				
			while(is.read(recvBuffer)!=-1){
				controlMethod.parsePackage(recvBuffer);
				System.out.println("即将调用解析函数-From BTServerThread");
			}				
		}
		catch (IOException e) {
			e.printStackTrace();
		}	
	}
};