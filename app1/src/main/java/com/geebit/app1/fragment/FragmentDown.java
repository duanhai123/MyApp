package com.geebit.app1.fragment;

import android.view.View;
import android.widget.ListView;

import com.geebit.app1.R;


public class FragmentDown extends BaseFragment {

	private View view;
	private ListView lv_phone;

	@Override
	public View initView() {
		view = View.inflate(mActivity, R.layout.fragment_down,null);
		return view;
	}

	@Override
	public void initData() {
	}
}
