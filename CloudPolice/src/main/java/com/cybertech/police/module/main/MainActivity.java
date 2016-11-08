package com.cybertech.police.module.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.cybertech.police.R;
import com.cybertech.police.base.BaseActivity;
import com.cybertech.police.base.Constant;
import com.cybertech.police.utils.CompatUtils;
import com.cybertech.police.utils.SPUtils;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import static com.cybertech.police.utils.SPUtils.FILE_NAME;
import static com.cybertech.police.utils.SPUtils.MODE;

/**
 * 主界面
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String TAG = "MainActivity";
    private FragmentManager fragmentManager;
    private Boolean isLogin;
    private String mUserName = Constant.EMPTY_STRING;
    private String mUserId = Constant.EMPTY_STRING;

    private final int mDrawableList[] = {R.drawable.ic_loyalty_black_36dp, R.drawable.ic_camera_black_36dp,
            R.drawable.ic_message_black_36dp, R.drawable.ic_people_black_36dp};

   // @ViewInject(R.id.userAccount)
   // private TextView userAccount;

    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.drawer_layout)
    private DrawerLayout drawer;

    @ViewInject(R.id.navigation_view)
    private NavigationView navigationView;




    /**
     * 右下角消息事件
     *
     * @param view
     */
    @Event(value = R.id.fab,
            type = View.OnClickListener.class)
    private void onFabClick(View view) {
        Snackbar.make(view, "自行替换消息界面", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        intiDrawer(toolbar);//初始化DrawerLayout
        initHeadView();//为Drawer添加头部
        intiMenuView();//为Drawer添加menu菜单项目


        //设置toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //设置
        navigationView.setNavigationItemSelectedListener(this);
        //获取Fragment管理器
        fragmentManager = getSupportFragmentManager();
        //获取登录用户数据
        getData();
        getSharedPreferences(FILE_NAME, MODE).registerOnSharedPreferenceChangeListener(this);
        //userAccount.setText(mUserName);
        // Log.i(TAG, "onCreate: "+userAccount.getText());
    }


    //@ViewInject(value = R.id.ll_nav_operation,parentId =R.layout.nav_header_main )
   // private LinearLayout group;


    private void initHeadView() {
        /**
         * 代码手动填充 view作为头部布局
         * 得到view之后 就可以对headView进行操作
         */
        /**
         * 代码手动填充 view作为头部布局
         * 得到view之后 就可以对headView进行操作
         */
       // View headView = navigationView.inflateHeaderView(R.layout.nav_header_main);
//        tv_nav_username = ButterKnife.findById(headView, tv_nav_username);
//        tv_nav_email = ButterKnife.findById(headView, tv_nav_email);
//        img_nav_head = ButterKnife.findById(headView, img_nav_head);

        //addButtonDrawable(group);
    }


    /**
     * 取出父视图中的button 动态添加的Drawable资源
     * 使用了V4兼容包的Tint方法
     *
     * @param group
     */
    private void addButtonDrawable(LinearLayout group) {
//        Button btn = null;
        for (int i = 0, size = group.getChildCount(); i < size; i++) {
            Button btn = (Button) group.getChildAt(i);
            btn.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    CompatUtils.getTintListDrawable(mContext, mDrawableList[i], R.color.tint_list_pink),
                    null,
                    null);
            // btn.setOnClickListener(this);
        }
    }

    private void intiDrawer(Toolbar toolbar) {
        //设置toolbar
        setSupportActionBar(toolbar);
    }

    /**
     * 手动填充Menu 方便以后对menu的调整
     */
    private void intiMenuView() {

        Menu menu = navigationView.getMenu();
        String titleList[] = getResources().getStringArray(R.array.title_array);
        int order = 0;
        for (String title : titleList) {
//            menu.add(Menu.NONE, order++, Menu.NONE, title).setIcon(R.drawable.ic_menu_share).setCheckable(true);
            menu.add(R.id.menu_group_type, order++, Menu.NONE, title).setIcon(mDrawableList[0]).setCheckable(true);
        }
//        menu.addSubMenu("text");
        menu.getItem(0).setChecked(true);//默认选中第一项

    }

    //取出各种需要用的全局变量
    private void getData() {
        isLogin = (Boolean) SPUtils.get(mContext, Constant.ISLOGIN, false);
        if (isLogin) {
            //如果登录才有取以下值的意义
            getDataByLogin();
        }
    }

    private void getDataByLogin() {
        mUserName = (String) SPUtils.get(mContext, Constant.USERNAME, mUserName);
        mUserId = (String) SPUtils.get(mContext, Constant.USERID, mUserId);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSharedPreferences(FILE_NAME, MODE).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (Constant.ISLOGIN.equals(key)) {
            isLogin = sharedPreferences.getBoolean(Constant.ISLOGIN, false);
        }
    }
}
