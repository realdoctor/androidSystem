package com.real.doctor.realdoc.activity;

import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.real.doctor.realdoc.R;
import com.real.doctor.realdoc.util.RecordSQLiteOpenHelper;
import com.real.doctor.realdoc.view.MyListView;

public class SearchHistoryActivity extends Activity {

	private EditText et_search;
	private TextView tv_tip;
	private MyListView listView;
	private TextView tv_clear;
	private RecordSQLiteOpenHelper helper = new RecordSQLiteOpenHelper(this);;
	private SQLiteDatabase db;
	private BaseAdapter adapter;
	private ImageView back;
	private int requestCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search_history);

		Intent intent = getIntent();
		requestCode = intent.getIntExtra("requestCode", 0);
		// 初始化控件
		initView();

		// 清空搜索历史
		tv_clear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				deleteData();
				queryData("");
			}
		});

		// 搜索框的键盘搜索键点击回调
		et_search.setOnKeyListener(new View.OnKeyListener() {// 输入完后按键盘上的搜索键

			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {// 修改回车键功能
					// 先隐藏键盘
					((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
							getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
					// 按完搜索键后将当前查询的关键字保存起来,如果该关键字已经存在就不执行保存
					boolean hasData = hasData(et_search.getText().toString().trim());
					if (!hasData) {
						insertData(et_search.getText().toString().trim());
						queryData("");
					}
					// TODO 根据输入的内容模糊查询商品，并跳转到另一个界面，由你自己去实现
					Toast.makeText(SearchHistoryActivity.this, et_search.getText().toString(), Toast.LENGTH_SHORT).show();
					String value=et_search.getText().toString();
					setBackValue(value);
				}
				return false;
			}
		});

		// 搜索框的文本变化实时监听
		et_search.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString().trim().length() == 0) {
					tv_tip.setText("搜索历史");
				} else {
					tv_tip.setText("搜索结果");
				}
				String tempName = et_search.getText().toString();
				// 根据tempName去模糊查询数据库中有没有数据
				queryData(tempName);

			}
		});

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				TextView textView = (TextView) view.findViewById(android.R.id.text1);
				String name = textView.getText().toString();
				et_search.setText(name);
				setBackValue(name);
				// TODO 获取到item上面的文字，根据该关键字跳转到另一个页面查询，由你自己去实现
			}
		});
		// 第一次进入查询所有的历史记录
		queryData("");
	}

	public void setBackValue(String value){
		if(requestCode==RegistrationActivity.REGISTRATION_EVENT_REQUEST_CODE){
			Intent intent=new Intent(SearchHistoryActivity.this,SearchResultActivity.class);
			intent.putExtra("searchKey", value);
			startActivity(intent);
			SearchHistoryActivity.this.finish();
		}
	}

	/**
	 * 插入数据
	 */
	private void insertData(String tempName) {
		db = helper.getWritableDatabase();
		db.execSQL("insert into records(name) values('" + tempName + "')");
		db.close();
	}

	/**
	 * 模糊查询数据
	 */
	private void queryData(String tempName) {
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);
		// 创建adapter适配器对象
		adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[] { "name" },
				new int[] { android.R.id.text1 }, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		// 设置适配器
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
	/**
	 * 检查数据库中是否已经有该条记录
	 */
	private boolean hasData(String tempName) {
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select id as _id,name from records where name =?", new String[]{tempName});
		//判断是否有下一个
		return cursor.moveToNext();
	}

	/**
	 * 清空数据
	 */
	private void deleteData() {
		db = helper.getWritableDatabase();
		db.execSQL("delete from records");
		db.close();
	}

	private void initView() {
		back=(ImageView)findViewById(R.id.back);
		et_search = (EditText) findViewById(R.id.et_search);
		tv_tip = (TextView) findViewById(R.id.tv_tip);
		listView = (MyListView) findViewById(R.id.listView);
		tv_clear = (TextView) findViewById(R.id.tv_clear);
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SearchHistoryActivity.this.finish();
			}
		});

		// 调整EditText左边的搜索按钮的大小
		Drawable drawable = getResources().getDrawable(R.drawable.search);
		drawable.setBounds(0, 0, 60, 60);// 第一0是距左边距离，第二0是距上边距离，60分别是长宽
		et_search.setCompoundDrawables(drawable, null, null, null);// 只放左边
	}
}