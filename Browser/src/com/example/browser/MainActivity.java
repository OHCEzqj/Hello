package com.example.browser;


import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.ZoomDensity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
    EditText ed_url;
    Button bt_jump;
    WebView wb_show;
    Activity activity=this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ed_url=(EditText)findViewById(R.id.id_input);
		bt_jump=(Button)findViewById(R.id.button1);
		wb_show=(WebView)findViewById(R.id.webView1);
		
		// 得到WebSetting对象，设置支持Javascript的参数,支持javascript自定义对象
		wb_show.getSettings().setJavaScriptEnabled(true);
		// 设置可以支持缩放
		wb_show.getSettings().setSupportZoom(true);
		// 设置默认缩放方式尺寸是far
		wb_show.getSettings().setDefaultZoom(ZoomDensity.FAR);
		// 设置出现缩放工具
		wb_show.getSettings().setBuiltInZoomControls(true);
		// 载入URL
		wb_show.loadUrl("http://www.baidu.com");
		// 使页面获得焦点
		wb_show.requestFocus();
		
		// 给按钮绑定单击监听器
		bt_jump.setOnClickListener(new View.OnClickListener() {
		@Override
	    	public void onClick(View v) {
			// 访问编辑框中的网址
			wb_show.loadUrl("http://" + ed_url.getText().toString());
					}
				});		
	
	// 创建webviewclient，实现在只有webview响应url，不使用默认浏览器
	//如果页面中链接，如果希望点击链接继续在当前browser中响应，而不是新开Android的系统browser中响应该链接，必须覆盖 webview的WebViewClient对象。
		wb_show.setWebViewClient(new WebViewClient() {
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					// TODO Auto-generated method stub
					// return super.shouldOverrideUrlLoading(view, url);					
					view.loadUrl(url);
					return true;
				}

			});
		
		// 进度条
		// 进度条
		wb_show.setWebChromeClient(new WebChromeClient() {

					@Override
					public void onProgressChanged(WebView view, int newProgress) {
						// TODO Auto-generated method stub						
						activity.setTitle("Loading..."+newProgress+"%" );
					//	activity.setProgress(newProgress * 100);
						if (newProgress == 100) {
							activity.setTitle(R.string.app_name);
						}
					}
				});
		
		// 设置默认后退按钮为返回前一页面
		 wb_show.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						// TODO Auto-generated method stub
						if (event.getAction() == KeyEvent.ACTION_DOWN) {
							if ((keyCode == KeyEvent.KEYCODE_BACK)&& wb_show.canGoBack()) {
								wb_show.goBack();
								return true;
							}
						}
						return false;
					}
				});
		
	
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	/*
	public boolean onKey(View v, int keyCode, KeyEvent event)
	{
		// TODO Auto-generated method stub	
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			if(wb_show.canGoBack()){
				wb_show.goBack();
				return true;
			}
			else
				return super.onKeyDown(keyCode, event);                      		 
		}
		else return super.onKeyDown(keyCode, event);
	 }
	 */	                                             
}
