package View;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main {
	private static  ComboxFrame frame;
	 public static void main(String[] args)  {
		 frame=new ComboxFrame();
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 frame.setVisible(true);
		 frame.pack();
		 frame.setLocationRelativeTo(null);//置于屏幕中间
	 }   
	 public void close(String ConnectMode){
		 frame.dispose();
		// MouseControl mouseControl=new MouseControl(ConnectMode);
		// SocketMode socketMode=new SocketMode();	 
		// BlueToothMode blueToothMode=new BlueToothMode();
		 if (ConnectMode.equals("NetWork")) {
			 System.out.println("选择NetWork，即将跳转");
			 SocketServer socketServer=new SocketServer();
		}else if (ConnectMode.equals("BlueTooth")) {
			System.out.println("选择BlueTooth，即将跳转");
			BTServer btServer=new BTServer();
		}
		 
	 }
	 
}
class ComboxFrame extends JFrame{
	//组件
	private JLabel label;
	private JComboBox<String> comboBox;
	//连接方式
	private String ConnectMode="NetWork";
	public ComboxFrame(){
		setTitle("PPTControl");
		//添加frame说明性lable
		label=new JLabel("请选择连接方式！");
		label.setFont(new Font("Serif", Font.PLAIN|Font.BOLD, 20));
		add(label, BorderLayout.NORTH);
		//构造组合框并增加选项
		comboBox=new JComboBox<String>();
		comboBox.setEditable(false);//不允许编辑选项
		comboBox.addItem("网络连接");
		comboBox.addItem("蓝牙连接");
		comboBox.setFont(new Font("楷书", Font.BOLD|Font.ITALIC, 18));
		comboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//comboBox.getSelectedIndex();得到选择的选项的位置
				switch (comboBox.getSelectedIndex()) {
				case 0:
					ConnectMode="NetWork";
					break;
				case 1:
					ConnectMode="BlueTooth";
					break;
				default:
					break;
				}
			}
		});
		//把组合框添加到JPanel中
		JPanel panel=new JPanel();
		panel.add(comboBox);
		//把JPanel添加到JFrame中
		add(panel, BorderLayout.CENTER);
		//确定按钮
		JButton connect=new JButton("确定");
		connect.setFont(new Font("楷书", Font.BOLD|Font.ITALIC, 18));
		add(connect, BorderLayout.SOUTH);
		//连接监听事件
		
		connect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Main main=new Main();
				main.close(ConnectMode);
				
				
			}//actionPerformed
		});//connect
	
	}//	ComboxFrame
	
	
}