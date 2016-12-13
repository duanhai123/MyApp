package com.geebit.app1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.geebit.app1.R;
import com.geebit.app1.fragment.FragmentCategory;
import com.geebit.app1.fragment.FragmentDown;
import com.geebit.app1.fragment.FragmentHome;
import com.geebit.app1.fragment.FragmentSetting;
import com.geebit.app1.fragment.FragmentUser;

/**
 * 
 * @author yechao
 * @功能说明 自定义TabHost
 *
 */
public class MainActivity extends FragmentActivity {
	//百度推送key
	private String APP_Key = "VS3maAGhd8Hi2Lg0SQMy65K66HbApmoZ";
	private static final String TAG = "tag";
	// 定义FragmentTabHost对象
	private FragmentTabHost mTabHost;

	// 定义一个布局
	private LayoutInflater layoutInflater;

	// 定义数组来存放Fragment界面
	private Class fragmentArray[] = { FragmentHome.class,
			FragmentCategory.class, FragmentDown.class, FragmentUser.class,
			FragmentSetting.class };



	// Tab选项卡的文字
	private String mTextviewArray[] = { "登录", "TGB-消息", "TGB-I", "TGB-II", "我" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		// 实例化布局对象
		layoutInflater = LayoutInflater.from(this);

		// 实例化TabHost对象，得到TabHost
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		//初始化百度推送
		PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY,APP_Key);
		// 得到fragment的个数
		int count = fragmentArray.length;

		for (int i = 0; i < count; i++) {
			// 为每一个Tab按钮设置图标、文字和内容
			TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i])
					.setIndicator(getTabItemView(i));
			// 将Tab按钮添加进Tab选项卡中
			mTabHost.addTab(tabSpec, fragmentArray[i], null);
			// 设置Tab按钮的背景
			mTabHost.getTabWidget().getChildAt(i)
					.setBackgroundResource(R.drawable.main_tab_item_bg);
			mTabHost.setCurrentTab(fragmentArray.length-1);
             if (i==0) {
				 mTabHost.getTabWidget().getChildAt(i).setOnClickListener(new View.OnClickListener() {
					 @Override
					 public void onClick(View view) {

							 startActivity(new Intent(MainActivity.this, LoginActivity.class));
							 finish();

					 }
				 });
			 }
			}
		}



	/**
	* 给Tab按钮设置图标和文字
	 */
	private View getTabItemView(int index) {
		View view = layoutInflater.inflate(R.layout.tab_item_view, null);

		//ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
		//imageView.setImageResource(mImageViewArray[index]);

		TextView textView = (TextView) view.findViewById(R.id.textview);
		textView.setText(mTextviewArray[index]);

		return view;
	}


}
