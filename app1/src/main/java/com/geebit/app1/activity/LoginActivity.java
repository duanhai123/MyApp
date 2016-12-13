package com.geebit.app1.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.geebit.app1.R;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;


/**
 * Created by admin on 2016/11/26.
 */
public class LoginActivity extends Activity {
    // 弹出框
    private ProgressDialog mDialog;

    // username 输入框
    private EditText mUsernameEdit;
    // 密码输入框
    private EditText mPasswordEdit;

    // 注册按钮
    private TextView mSignUpBtn;
    // 登录按钮
    private Button mSignInBtn;
    //忘记密码按键
    private TextView mForgetPassword;
    private String username;
    private String password;
    private CheckBox cb_agree;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    /**
     * 初始化界面控件
     */
    private void initView() {

        mUsernameEdit = (EditText) findViewById(R.id.ec_edit_username);
        mPasswordEdit = (EditText) findViewById(R.id.ec_edit_password);
        mForgetPassword = (TextView) findViewById(R.id.ec_edit_forget_password);
        mSignUpBtn = (TextView) findViewById(R.id.ec_btn_sign_up);
        cb_agree = (CheckBox) findViewById(R.id.cb_agree);
        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //signUp();
                startActivity(new Intent(LoginActivity.this,LogupActivity.class));

            }
        });

        mSignInBtn = (Button) findViewById(R.id.ec_btn_sign_in);
        mSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        mForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,ForgetPasswordActivity.class));

            }
        });
    }



    /**
     * 登录方法
     */
    private void signIn() {
        username = mUsernameEdit.getText().toString().trim();
        password = mPasswordEdit.getText().toString().trim();
        String num = "[1][358]\\d{9}";
        boolean matches = username.matches(num);
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "用户或者密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }else if (!cb_agree.isChecked()){
            Toast.makeText(LoginActivity.this, "请勾选我已阅读并且同意服务条款", Toast.LENGTH_SHORT).show();
        }else if (!matches){
            Toast.makeText(LoginActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
        }
        else {
            mDialog = new ProgressDialog(this);
            mDialog.setMessage("正在登陆，请稍后...");
            mDialog.show();
            String username = mUsernameEdit.getText().toString().trim();
            String password = mPasswordEdit.getText().toString().trim();
            EMClient.getInstance().login(username, password, new EMCallBack() {
                /**
                 * 登陆成功的回调
                 */
                @Override
                public void onSuccess() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mDialog.dismiss();

                            // 加载所有会话到内存
                            EMClient.getInstance().chatManager().loadAllConversations();
                            // 加载所有群组到内存，如果使用了群组的话
                            // EMClient.getInstance().groupManager().loadAllGroups();

                            // 登录成功跳转界面


                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    });


                }

                /**
                 * 登陆错误的回调
                 *
                 * @param i
                 * @param s
                 */
                @Override
                public void onError(final int i, final String s) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mDialog.dismiss();
                            Log.d("lzan13", "登录失败 Error code:" + i + ", message:" + s);
                            /**
                             * 关于错误码可以参考官方api详细说明
                             * http://www.easemob.com/apidoc/android/chat3.0/classcom_1_1hyphenate_1_1_e_m_error.html
                             */
                            switch (i) {
                                // 网络异常 2
                                case EMError.NETWORK_ERROR:
                                    Toast.makeText(LoginActivity.this, "网络错误 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                    break;
                                // 无效的用户名 101
                                case EMError.INVALID_USER_NAME:
                                    Toast.makeText(LoginActivity.this, "无效的用户名 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                    break;
                                // 无效的密码 102
                                case EMError.INVALID_PASSWORD:
                                    Toast.makeText(LoginActivity.this, "无效的密码 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                    break;
                                // 用户认证失败，用户名或密码错误 202
                                case EMError.USER_AUTHENTICATION_FAILED:
                                    Toast.makeText(LoginActivity.this, "用户认证失败，用户名或密码错误 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                    break;
                                // 用户不存在 204
                                case EMError.USER_NOT_FOUND:
                                    Toast.makeText(LoginActivity.this, "用户不存在 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                    break;
                                // 无法访问到服务器 300
                                case EMError.SERVER_NOT_REACHABLE:
                                    Toast.makeText(LoginActivity.this, "无法访问到服务器 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                    break;
                                // 等待服务器响应超时 301
                                case EMError.SERVER_TIMEOUT:
                                    Toast.makeText(LoginActivity.this, "等待服务器响应超时 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                    break;
                                // 服务器繁忙 302
                                case EMError.SERVER_BUSY:
                                    Toast.makeText(LoginActivity.this, "服务器繁忙 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                    break;
                                // 未知 Server 异常 303 一般断网会出现这个错误
                                case EMError.SERVER_UNKNOWN_ERROR:
                                    Toast.makeText(LoginActivity.this, "未知的服务器异常 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                    break;
                                default:
                                    Toast.makeText(LoginActivity.this, "ml_sign_in_failed code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }
                    });
                }

                @Override
                public void onProgress(int i, String s) {

                }
            });
        }
    }
}
