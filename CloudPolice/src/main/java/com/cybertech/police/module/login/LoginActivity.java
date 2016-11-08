package com.cybertech.police.module.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.cybertech.police.R;
import com.cybertech.police.base.BaseActivity;
import com.cybertech.police.base.Constant;
import com.cybertech.police.module.login.model.LoginBaseResponse;
import com.cybertech.police.module.login.model.LoginParams;
import com.cybertech.police.module.login.model.LoginRequest;
import com.cybertech.police.module.login.model.LoginResponse;
import com.cybertech.police.module.main.MainActivity;
import com.cybertech.police.utils.SPBuild;
import com.cybertech.police.utils.SPUtils;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 云警务登录界面
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
    private boolean Loginflag = false;
    /**
     * 登录按钮
     */
    @ViewInject(R.id.btn_login)
    private com.dd.CircularProgressButton btnLogin;
    /**
     * 登录Task
     */
    private UserLoginTask mAuthTask = null;
    /**
     * 用户名
     */
    @ViewInject(R.id.et_username)
    private EditText mUsernameView;
    /**
     * 密码输入框
     */
    @ViewInject(R.id.et_password)
    private EditText mPasswordView;

    /**
     * 登录按钮 绑定 登录事件
     *
     * @param view
     */
    @Event(value = R.id.btn_login,
            type = View.OnClickListener.class)
    private void onBtnLoginClick(View view) {
        attemptLogin();
    }

    /**
     * 登录前验证
     */
    private void attemptLogin() {

        // 重置errors
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // 获取登录信息
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // 校验密码
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // 校验用户名
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            //校验不通过
            focusView.requestFocus();
        } else {
            //校验通过 开始网络连接 登录
            btnLogin.setIndeterminateProgressMode(true); // 激活一个未知时常的进度条
            btnLogin.setProgress(50); // 暂时给以50进度 作为过渡
            mAuthTask = new UserLoginTask(username, password);
            mAuthTask.execute((Void) null);
        }
    }

    /**
     * 校验密码位数是否超过6位
     *
     * @param password
     * @return
     */
    private boolean isPasswordValid(String password) {
        return password.length() > 6;
    }

    /**
     * 用户登录Task
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUsername;
        private final String mPassword;

        UserLoginTask(String userame, String password) {
            mUsername = userame;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            //创建登录请求
            LoginRequest loginRequest = new LoginRequest();
            //组装请求参数
            LoginParams loginParams = new LoginParams();
            loginParams.setUserName(mUsername);
            loginParams.setUserPwd(mPassword);
            //设置查询参数到BodyContent
            loginRequest.addBodyParameter("Params", JSON.toJSONString(loginParams));
            //发送请求
            Callback.Cancelable cancelable
                    = x.http().get(loginRequest,
                    new Callback.CommonCallback<LoginBaseResponse>() {
                        @Override
                        public void onSuccess(LoginBaseResponse result) {
                            if (result.getCode() == Constant.USER_LOGIN_SUCCESS) {
                                Loginflag = true;
                                btnLogin.setProgress(100); // set progress to 100 or -1 to indicate complete or error state
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                saveUserInfo(result.getData());
                                startActivity(intent);
                            } else {
                                new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("系统提示框")
                                        .setContentText(result.getMessage())
                                        .show();
                            }
                            btnLogin.setProgress(0); // set progress to 0 to switch back to normal state
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            if (ex instanceof HttpException) { // 网络错误
                                HttpException httpEx = (HttpException) ex;
                                int responseCode = httpEx.getCode();
                                String responseMsg = httpEx.getMessage();
                                String errorResult = httpEx.getResult();
                                new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("系统提示框")
                                        .setContentText(responseMsg + errorResult)
                                        .show();
                            } else { // 其他错误
                                new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("系统提示框")
                                        .setContentText(ex.getMessage())
                                        .show();
                            }
                            btnLogin.setProgress(0); // set progress to 0 to switch back to normal state
                        }

                        @Override
                        public void onCancelled(CancelledException cex) {
                            btnLogin.setProgress(0); // set progress to 0 to switch back to normal state
                            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("系统提示框")
                                    .setContentText("取消网络请求")
                                    .show();
                        }

                        @Override
                        public void onFinished() {
                        }
                    });
            return Loginflag;
        }

        /**
         * 保存用户信息到Sp
         * @param userResponse
         */
        private void saveUserInfo(LoginResponse userResponse){
            //保存先清空内容
            SPUtils.clear(getApplicationContext());
            new SPBuild(getApplicationContext())
                    .addData(Constant.ISLOGIN, Boolean.TRUE)//登陆志位
                    .addData(Constant.LOGINTIME, System.currentTimeMillis())//登陆时间
                    .addData(Constant.USERACCOUNT, userResponse.getUserName())//账号
                    .build();
        }
    }


}

