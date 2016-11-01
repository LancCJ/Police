package com.cybertech.police.fragment;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.cybertech.police.R;
import com.cybertech.police.base.BaseFragment;
import com.cybertech.police.model.login.LoginParams;
import com.cybertech.police.model.login.LoginRequest;
import com.cybertech.police.model.login.LoginResponse;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by 健 on 2016/11/1.
 */

@ContentView(R.layout.fragment_login)
public class LoginFragment extends BaseFragment {
    private boolean LoginRequestflag=false;
    /**
     * 登录按钮
     */
    @ViewInject(R.id.btnLogin)
    private com.dd.CircularProgressButton btnLogin;
    /**
     * 登录Task
     */
    private UserLoginTask mAuthTask = null;
    /**
     * 用户名自动补全输入框
     */
    @ViewInject(R.id.email)
    private AutoCompleteTextView mUsernameView;
    /**
     * 密码输入框
     */
    @ViewInject(R.id.password)
    private EditText mPasswordView;

    /**
     * 登录按钮 绑定 登录事件
     * @param view
     */
    @Event(value = R.id.btnLogin,
            type = View.OnClickListener.class/*可选参数, 默认是View.OnClickListener.class*/)
    private void onBtnLoginClick(View view) {
        attemptLogin();
    }

    /**
     * 登录前验证
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

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
     * 校验密码位数是否超过8位
     * @param password
     * @return
     */
    private boolean isPasswordValid(String password) {
        return password.length() > 8;
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
                //组装请求参数
                LoginRequest loginRequest=new LoginRequest();
                LoginParams loginParams=new LoginParams();
                loginParams.setUserName(mUsername);
                loginParams.setUserPwd(mPassword);
                //查询参数设置
                loginRequest.setBodyContent(JSON.toJSONString(loginParams));
                //loginRequest.addParameter("test1",mUsername);
                //发送请求
                Callback.Cancelable cancelable
                        = x.http().get(loginRequest,
                        new Callback.CommonCallback<LoginResponse>() {
                            @Override
                            public void onSuccess(LoginResponse result) {
                                new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("系统提示框")
                                        .setContentText(result.getUserName())
                                        .show();
                                btnLogin.setProgress(0); // set progress to 0 to switch back to normal state
                                LoginRequestflag=true;
                            }
                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                                if (ex instanceof HttpException) { // 网络错误
                                    HttpException httpEx = (HttpException) ex;
                                    int responseCode = httpEx.getCode();
                                    String responseMsg = httpEx.getMessage();
                                    String errorResult = httpEx.getResult();
                                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                            .setTitleText("系统提示框")
                                            .setContentText(responseMsg+errorResult)
                                            .show();
                                } else { // 其他错误
                                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                            .setTitleText("系统提示框")
                                            .setContentText(ex.getMessage())
                                            .show();
                                }
                                btnLogin.setProgress(0); // set progress to 0 to switch back to normal state
                            }

                            @Override
                            public void onCancelled(CancelledException cex) {
                                btnLogin.setProgress(0); // set progress to 0 to switch back to normal state
                                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("系统提示框")
                                        .setContentText("取消网络请求")
                                        .show();
                            }

                            @Override
                            public void onFinished() {
                            }
                        });
                //cancelable.cancel(); // 取消请求
            return LoginRequestflag;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
                btnLogin.setProgress(100); // set progress to 100 or -1 to indicate complete or error state
                btnLogin.setProgress(0); // set progress to 0 to switch back to normal state

                new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("系统提示框")
                        .setContentText("登录成功")
                        .show();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}
