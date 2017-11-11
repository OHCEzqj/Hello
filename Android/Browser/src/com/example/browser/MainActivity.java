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
		
		// �õ�WebSetting��������֧��Javascript�Ĳ���,֧��javascript�Զ������
		wb_show.getSettings().setJavaScriptEnabled(true);
		// ���ÿ���֧������
		wb_show.getSettings().setSupportZoom(true);
		// ����Ĭ�����ŷ�ʽ�ߴ���far
		wb_show.getSettings().setDefaultZoom(ZoomDensity.FAR);
		// ���ó������Ź���
		wb_show.getSettings().setBuiltInZoomControls(true);
		// ����URL
		wb_show.loadUrl("http://www.baidu.com");
		// ʹҳ���ý���
		wb_show.requestFocus();
		
		// ����ť�󶨵���������
		bt_jump.setOnClickListener(new View.OnClickListener() {
		@Override
	    	public void onClick(View v) {
			// ���ʱ༭���е���ַ
			wb_show.loadUrl("http://" + ed_url.getText().toString());
					}
				});		
	
	// ����webviewclient��ʵ����ֻ��webview��Ӧurl����ʹ��Ĭ�������
	//���ҳ�������ӣ����ϣ��������Ӽ����ڵ�ǰbrowser����Ӧ���������¿�Android��ϵͳbrowser����Ӧ�����ӣ����븲�� webview��WebViewClient����
		wb_show.setWebViewClient(new WebViewClient() {
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					// TODO Auto-generated method stub
					// return super.shouldOverrideUrlLoading(view, url);					
					view.loadUrl(url);
					return true;
				}

			});
		
		// ������
		// ������
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
		
		// ����Ĭ�Ϻ��˰�ťΪ����ǰһҳ��
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
