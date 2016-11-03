package com.cybertech.police;

import android.os.Handler;
import android.content.Intent;
import android.os.Bundle;
import com.cybertech.police.base.BaseActivity;
import org.xutils.view.annotation.ContentView;

/**
 * Created by 健 on 2016/11/3.
 */
@ContentView(R.layout.activity_splash)
public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent intent = new Intent (SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }, 1000);
    }
}
