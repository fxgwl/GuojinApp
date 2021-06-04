package com.axehome.www.guojinapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Toast;

//import com.axehome.www.jufuapp.service.FloatingService;
import com.axehome.www.guojinapp.ui.activitys.BaseActivity;
import com.axehome.www.guojinapp.ui.activitys.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.axehome.www.guojinapp.GuoJinApp.isLogin;

public class MainActivity extends BaseActivity {

    @BindView(R.id.nav_view)
    BottomNavigationView navView;
    @BindView(R.id.container)
    ConstraintLayout container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.next_in_translate, R.anim.next_out_translate);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);
        navView.setItemIconSize(dpToPx(this,20));

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_msg, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        initView();
        /*if(MyUtils.getUser()!=null && MyUtils.getUser().getUser_type()!=null && MyUtils.getUser().getUser_type().equals("4")){
            Menu menu = navView.getMenu();
            menu.removeItem(R.id.navigation_msg); //去掉一个底部按钮
            menu.removeItem(R.id.navigation_notifications);
        }else{
            Menu menu = navView.getMenu();
            menu.removeItem(R.id.navigation_myshopcentre);
        }*/
        /*Menu menu = navView.getMenu();
        menu.add("测试");*/
        /*navView.getMenu().removeItem(R.id.navigation_msg);*/ //去掉一个底部按钮
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                switch (destination.getId()) {
                    case R.id.navigation_home:
                        //Toast.makeText(getApplicationContext(), "请先登陆", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_dashboard:
                        if (!isLogin()) {
                            navController.navigate(R.id.navigation_home);
                            Toast.makeText(getApplicationContext(), "请先登陆", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.navigation_msg:
                        if (!isLogin()) {
                            navController.navigate(R.id.navigation_home);
                            Toast.makeText(getApplicationContext(), "请先登陆", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.navigation_notifications:
                        if (!isLogin()) {
                            navController.navigate(R.id.navigation_home);
                            Toast.makeText(getApplicationContext(), "请先登陆", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        }
                        break;
                }
            }
        });
    }

    private void initView() {

        /*navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                return true;
            }
        });*/

        //startFloatingService(getApplicationContext());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent.getStringExtra("from")!=null && intent.getStringExtra("from").equals("login")){
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            navController.navigate(R.id.navigation_home);
            /*if(MyUtils.getUser()!=null && MyUtils.getUser().getUser_type()!=null && MyUtils.getUser().getUser_type().equals("4")){
                Menu menu = navView.getMenu();
                menu.removeItem(R.id.navigation_msg);
                menu.removeItem(R.id.navigation_notifications);
                menu.add(Menu.NONE,R.id.navigation_myshopcentre,3,"我的").setIcon(R.drawable.icon_my_bar);
            }else{
                Menu menu = navView.getMenu();
                menu.removeItem(R.id.navigation_myshopcentre);
            }*/
        }
    }

    /*public void startFloatingService(Context view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "当前无权限，请授权", Toast.LENGTH_SHORT);
                startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 0);
            } else {
                startService(new Intent(MainActivity.this, FloatingService.class));
            }
        }
    }*/

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
                    startService(new Intent(MainActivity.this, FloatingService.class));
                }
            }
        }
    }*/

    /**
     * dp转px
     *
     * @param context
     * @param dp
     * @return
     */
    public int dpToPx(Context context, float dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) ((dp * displayMetrics.density) + 0.5f);
    }
}
