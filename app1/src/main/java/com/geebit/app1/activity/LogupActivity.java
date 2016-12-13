package com.geebit.app1.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.geebit.app1.R;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import butterknife.ButterKnife;


/**
 * Created by DEll on 2016-12-05.
 */
public class LogupActivity extends Activity {


    TextView tvMessage;
    // 弹出框
    private ProgressDialog mDialog;
    // username 输入框
    private EditText mUsernameEdit;
    // 短信密码输入框
    private EditText mPasswordEdit;
    // 登录密码输入框
    private EditText mPasswordEdit1;

    // 注册按钮
    private TextView mSignUpBtn;
    private Button mSignUp;
    private String username;
    private String password;
    private String password1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logup);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

    }

    private void initView() {
        mUsernameEdit = (EditText) findViewById(R.id.ec_edit_username);
        mPasswordEdit = (EditText) findViewById(R.id.ec_edit_password);
        mPasswordEdit1 = (EditText) findViewById(R.id.ec_edit_password1);
        mSignUp = (Button) findViewById(R.id.ec_btn_sign_up);
    }

    /**
     * 注册方法
     */
    private void signUp() {
        // 注册是耗时过程，所以要显示一个dialog来提示下用户
        username = mUsernameEdit.getText().toString().trim();
        password = mPasswordEdit.getText().toString().trim();
        password1 = mPasswordEdit1.getText().toString().trim();
        String num = "[1][358]\\d{9}";
        boolean matches = username.matches(num);


        if (username.isEmpty() || password.isEmpty() || password1.isEmpty()) {
            Toast.makeText(LogupActivity.this, "账号密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (!(password.equals(password1))) {
            Toast.makeText(LogupActivity.this, "两次密码输入不一致请重新输入", Toast.LENGTH_SHORT).show();
            return;
        } else if (!matches) {
            Toast.makeText(LogupActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
        } else {
            mDialog = new ProgressDialog(this);
            mDialog.setMessage("注册中，请稍后...");
            mDialog.show();


            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {


                        EMClient.getInstance().createAccount(username, password);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!LogupActivity.this.isFinishing()) {
                                    mDialog.dismiss();
                                }
                                Toast.makeText(LogupActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(LogupActivity.this, LoginActivity.class));
                                finish();
                            }
                        });

                    } catch (final HyphenateException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!LogupActivity.this.isFinishing()) {
                                    mDialog.dismiss();
                                }
                                /**
                                 * 关于错误码可以参考官方api详细说明
                                 * http://www.easemob.com/apidoc/android/chat3.0/classcom_1_1hyphenate_1_1_e_m_error.html
                                 */
                                int errorCode = e.getErrorCode();
                                String message = e.getMessage();
                                Log.d("lzan13", String.format("sign up - errorCode:%d, errorMsg:%s", errorCode, e.getMessage()));
                                switch (errorCode) {
                                    // 网络错误
                                    case EMError.NETWORK_ERROR:
                                        Toast.makeText(LogupActivity.this, "网络错误 code: " + errorCode + ", message:" + message, Toast.LENGTH_LONG).show();
                                        break;
                                    // 用户已存在
                                    case EMError.USER_ALREADY_EXIST:
                                        Toast.makeText(LogupActivity.this, "用户已存在 code: " + errorCode + ", message:" + message, Toast.LENGTH_LONG).show();
                                        break;
                                    // 参数不合法，一般情况是username 使用了uuid导致，不能使用uuid注册
                                    case EMError.USER_ILLEGAL_ARGUMENT:
                                        Toast.makeText(LogupActivity.this, "参数不合法，一般情况是username 使用了uuid导致，不能使用uuid注册 code: " + errorCode + ", message:" + message, Toast.LENGTH_LONG).show();
                                        break;
                                    // 服务器未知错误
                                    case EMError.SERVER_UNKNOWN_ERROR:
                                        Toast.makeText(LogupActivity.this, "服务器未知错误 code: " + errorCode + ", message:" + message, Toast.LENGTH_LONG).show();
                                        break;
                                    case EMError.USER_REG_FAILED:
                                        Toast.makeText(LogupActivity.this, "账户注册失败 code: " + errorCode + ", message:" + message, Toast.LENGTH_LONG).show();
                                        break;
                                    default:
                                        Toast.makeText(LogupActivity.this, "ml_sign_up_failed code: " + errorCode + ", message:" + message, Toast.LENGTH_LONG).show();
                                        break;
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }


}
