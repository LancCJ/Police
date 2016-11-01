package com.cybertech.police;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.cybertech.police.base.BaseActivity;
import com.cybertech.police.fragment.LoginFragment;
import org.xutils.view.annotation.ContentView;

/**
 * 警务云主界面
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    // Fragment管理器
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取Fragment管理器
        fragmentManager = getSupportFragmentManager();
        // Create new fragment and transaction
        LoginFragment loginFragment = new LoginFragment();
        fragmentTransaction= fragmentManager.beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        fragmentTransaction.replace(R.id.fragments_main, loginFragment);
        fragmentTransaction.addToBackStack(null);
        // Commit the transaction
        fragmentTransaction.commit();
    }
}

