package com.geebit.app1.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.geebit.app1.R;


/**
 * Created by DEll on 2016-12-05.
 */
public class SecondActivity extends Activity {


    private CheckBox cb_safe;
    private CheckBox cb_email;
    private Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initView();
        initData();
    }

    private void initData() {
       btn_next.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (cb_safe.isChecked()){
                   startActivity(new Intent(SecondActivity.this,ThreeActivity.class));
               }else if (cb_email.isChecked()){
                   Toast.makeText(SecondActivity.this, "已将链接发动至您的邮箱，点击登录邮箱进行验证.  ", Toast.LENGTH_SHORT).show();
               }
               else{
                   Toast.makeText(SecondActivity.this, "请至少选择一种方式找回密码", Toast.LENGTH_SHORT).show();
               }
           }
       });
    }

    /*
    初始化控件
     */
    private void initView() {
        cb_safe = (CheckBox) findViewById(R.id.cb_safe);
        cb_email = (CheckBox) findViewById(R.id.cb_email);
        btn_next = (Button) findViewById(R.id.btn_next);
    }

}
