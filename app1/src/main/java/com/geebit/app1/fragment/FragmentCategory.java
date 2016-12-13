package com.geebit.app1.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.geebit.app1.R;


public class FragmentCategory extends BaseFragment {

	private MyAdapter mAdapter;
	private View view;
	private ListView lv_phone;



	@Override
	public View initView() {
		view = View.inflate(mActivity, R.layout.fragment_category,null);
		lv_phone = (ListView)view. findViewById(R.id.lv_phone);

		return view;
	}

	@Override
	public void initData() {
		//add.setOnClickListener(this);
		mAdapter = new MyAdapter();
		lv_phone.setAdapter(mAdapter);
	}



	class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return 10;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView textView = new TextView(mActivity);
			textView.setText(1+"");
			textView.setTextSize(100);
			return textView;
		}
	}

}
