package com.geebit.app1.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.geebit.app1.R;


/**
 * Created by DEll on 2016-12-05.
 */
public class ForgetPasswordActivity extends Activity {

    private Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgetPasswordActivity.this,SecondActivity.class));
                //finish();
            }
        });
    }
}
