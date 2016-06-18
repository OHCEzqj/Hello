package com.example.sql;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//声明MyDatebaseHelper
		final MyDatebaseHelper dbHelper=new MyDatebaseHelper(this, "student", null, 1);
		//获得SQLiteDatabase
		 SQLiteDatabase db=dbHelper.getReadableDatabase();
		
		//插入
		Button insert=(Button)findViewById(R.id.insert);
		insert.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 SQLiteDatabase db=dbHelper.getWritableDatabase();
				ContentValues values=new ContentValues();
				values.put("id", 2);
				values.put("name", "Jack");
				values.put("age", 19);
				
				
				values.put("id", 1);
				values.put("name", "OHCE");
				values.put("age", 18);
				db.insert("student", null, values);
				
			}});
		
		//更新数据
		Button update=(Button)findViewById(R.id.update);
		update.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 SQLiteDatabase db=dbHelper.getWritableDatabase();
				ContentValues values=new ContentValues();
				values.put("name", "Tom");
				db.update("student", values, "id=?", new String[]{"2"});
				
			}});
		 //查询
		Button query=(Button)findViewById(R.id.query);
		query.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SQLiteDatabase db=dbHelper.getReadableDatabase();
				Cursor cursor=db.query("Student",new String[]{"id","name","age"},"id=?",new String[]{"2"},null,null,null);
			    while(cursor.moveToNext()){
			    	String name=cursor.getString(cursor.getColumnIndex("name"));
			    	int age=cursor.getInt(cursor.getColumnIndex("age"));
			    	new AlertDialog.Builder(MainActivity.this)
			    	.setTitle("message").
			    	setMessage("name"+name+"  age"+age)
			    	.setPositiveButton("关闭",null)
			    	.show();
			    }
			}});
		
		
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
}
