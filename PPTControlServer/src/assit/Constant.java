package assit;




public final class Constant {
	//事件识别码:鼠标事件，键盘事件，滑动事件，状态事件,反馈事件,Media码
	public static final int RULE_MOUSE_EVENT = 1;
	public static final int RULE_KEY_EVENT = 2;
	public static final int RULE_SCREEN_PIX = 3;
	public static final int RULE_CONDITION=4;
	public static final int RULE_FEEDBACK=5;
	public static final int RULE_MEDIA=6;
	public static final int RULE_SCreen=7;
	 
	//以下鼠标事件码，由系统提供
	public static final int MOUSEEVENT_LEFTDOWN = 0x2;//左键按下
	public static final int MOUSEEVENT_LEFTUP = 0x4;//左键释放，左键按下与左键释放组合就是一个有意义的集合，那么就代表着一次左键点击
	public static final int MOUSEEVENT_MIDDLEDOWN = 0x20;//中键按下
	public static final int MOUSEEVENT_MIDDLEUP = 0x40;//中键释放
	public static final int MOUSEEVENT_RIGHTDOWN = 0x8;//右键按下
	public static final int MOUSEEVENT_RIGHTUP = 0x10;//右键释放
	public static final int MOUSEEVENT_MOVE = 0x1;//鼠标移动，表示相对于上次移动dx,dy。若与下面一个组合，表示鼠标移到dx,dy绝对坐标处
	public static final int MOUSEEVENT_ABSOLUTE = 0x8000;//指定dx,dy为绝对坐标值，若未指定，则为上次鼠标坐标值的偏移量
	public static final int MOUSEEVENT_WHELL=0x800;//鼠标滚轮，

	//以下为键盘键值，由系统提供
	public static final int KEY_ARROW_LEFT = 37;
	public static final int KEY_ARROW_RIGHT = 39;
	public static final int KEY_ARROW_UP = 38;
	public static final int KEY_ARROW_DOWN = 40;
	public static final int KEY_ESC = 27;
	public static final int KEY_F5 = 116;
	
	public static final int KEY_B=66;
	public static final int KEY_F=70;
	public static final int KEY_P=80;
	public static final int KEY_F7=118;
	public static final int KEY_F8=119;
	public static final int KEY_F9=120;
	public static final int KEY_CTRL=17;
	public static final int KEY_ALT=18;
	public static final int KEY_SHIFT=16;
	public static final int KEY_ENTER=13;
	public static final int KEY_WIN=91;
	public static final int KEY_DEL=46;
	
	//鼠标移动方向指令值，这个为自定义
	public static final int MOVE_LEFT = 'L';//76
	public static final int MOVE_RIGHT = 'R';//82
	public static final int MOVE_UP = 'U';//85
	public static final int MOVE_DOWN = 'D';//68
	
	//鼠标按键指令值，这个为自定义
	public static final int MOUSE_LEFT = 0x11;//17
	public static final int MOUSE_RIGHT = 0x22;//34
	public static final int MOUSE_CENTER = 0x33;//51
	public static final int MOUSE_WHELL_UP=0x44;
	public static final int MOUSE_WHELL_DOWN=0x55;
	
   //状态事件码,自己定义
	public static final int CONDITION_CONNECT='A';//65
	public static final int CONDITION_BREAKE='B';//66
	
	//反馈事件码
	public static final int FEEDBACK_INIT='Z';//90
	
	//mediaplayer 码
	public static final int MEDIA_VOLUME_UP='E';
	public static final int MEDIA_VOLUME_SLIENCE='F';
	public static final int MEDIA_VOLUME_DOWN='G';
	public static final int MEDIA_PLAY='H';
	public static final int MEDIA_FF='I';
	public static final int MEDIA_REW='J';
	public static final int MEDIA_NEXT='K';
	public static final int MEDIA_PREVIOUS='L';
	public static final int MEDIA_SCREEN='M';
	
	
	//其它
	public static final int MOUSE_GESTURE = 'C';//67
	public static final int bufferSize = 12;
	public static String multicastIp=null;//不需要，客户端用的
    public static int commPort = 8600;

    //电脑码
    public  static  final  int Screen_init='O';
    public  static  final  int Screen_win='P';
    public  static  final  int Screen_shutdown='Q';
    public  static  final  int Screen_ctrlalt='R';
    public  static  final  int Screen_cmd='S';
    public  static  final  int Screen_logoff='T';
    public  static  final  int Screen_explorer='U';
    public  static  final  int Screen_nptepad='V';
    public  static  final  int Screen_mspaint='W';
    public  static  final  int Screen_taskmgr='X';
    public  static  final  int Screen_Nslookup='Y';
}
