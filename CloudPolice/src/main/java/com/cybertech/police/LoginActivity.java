package com.cybertech.police;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import com.cybertech.police.base.BaseActivity;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 警务云登录界面
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
            btnLogin.setProgress(100); // set progress to 100 or -1 to indicate complete or error state
            btnLogin.setProgress(0); // set progress to 0 to switch back to normal state

            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.SUCCESS_TYPE)
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

