package com.cybertech.police;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.cybertech.police.base.BaseActivity;
import com.cybertech.police.module.login.LoginActivity;

import org.xutils.view.annotation.ContentView;

/**启动界面
 * Created by 健 on 2016/11/3.
 */
@ContentView(R.layout.activity_splash)
public class StartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent intent = new Intent (StartActivity.this,LoginActivity.class);
                startActivity(intent);
                StartActivity.this.finish();
            }
        }, 1000);
    }
}
