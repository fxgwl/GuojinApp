package com.axehome.www.guojinapp.ui.activitys.viewpager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.ui.activitys.BaseActivity;
import com.axehome.www.guojinapp.ui.activitys.SplashActivity;
import com.axehome.www.guojinapp.ui.activitys.viewpager.adapters.ViewPagerAdapter;
import com.axehome.www.guojinapp.ui.activitys.viewpager.fragment.ViewPager1Fragment;
import com.axehome.www.guojinapp.ui.activitys.viewpager.fragment.ViewPager2Fragment;
import com.axehome.www.guojinapp.ui.activitys.viewpager.fragment.ViewPager3Fragment;
import com.axehome.www.guojinapp.utils.SPUtils;

import java.util.ArrayList;

public class ViewPagerActivity extends BaseActivity implements ViewPager1Fragment.OnFragmentInteractionListener {

    @BindView(R.id.vp_container)
    ViewPager vpContainer;
    @BindView(R.id.v_4)
    View v1;
    @BindView(R.id.v_2)
    View v2;
    @BindView(R.id.v_3)
    View v3;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ViewPager1Fragment fragment1;
    private ViewPager2Fragment fragment2;
    private ViewPager3Fragment fragment3;
    private ObjectAnimator objectAnimator;
    private float b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        ButterKnife.bind(this);
        String str= (String) SPUtils.get("YinSi","");
        if(str==""){
        }else{
            startActivity(new Intent(getApplicationContext(), SplashActivity.class));
            finish();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        initView();
    }

    private void initView() {

        fragment1 = new ViewPager1Fragment();
        fragment2 = new ViewPager2Fragment();
        fragment3 = new ViewPager3Fragment();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        vpContainer.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments));
        vpContainer.setPageTransformer(true, new MyDrawable2());
        vpContainer.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {
                //b = v1.getTranslationX();
                switch (i) {
                    case 0:
                        Log.d("state", i + "");
                        v1.setBackgroundColor(getResources().getColor(R.color.f6));
                        v2.setBackgroundColor(getResources().getColor(R.color.f6));
                        v3.setBackgroundColor(getResources().getColor(R.color.f6));
                        switch (vpContainer.getCurrentItem()) {

                            case 0:
                                v1.setBackgroundColor(getResources().getColor(R.color.title_color));
                                /*final AnimatorSet translationAnimatorSet = new AnimatorSet();

                                translationAnimatorSet.playTogether(
                                        objectAnimator.ofFloat(v1, "translationX", b, 0)
                                                .setDuration(300)
                                );
                                translationAnimatorSet.start();
*/
                                break;
                            case 1:
                                v2.setBackgroundColor(getResources().getColor(R.color.title_color));
                                /*final AnimatorSet translationAnimatorSet1 = new AnimatorSet();
                                translationAnimatorSet1.playTogether(
                                        objectAnimator.ofFloat(v1, "translationX", b, 60)
                                                .setDuration(300)
                                );
                                translationAnimatorSet1.start();*/
                                break;
                            case 2:
                                v3.setBackgroundColor(getResources().getColor(R.color.title_color));
                                /*final AnimatorSet translationAnimatorSet2 = new AnimatorSet();
                                translationAnimatorSet2.playTogether(
                                        objectAnimator.ofFloat(v1, "translationX", b, 120)
                                                .setDuration(300)
                                );
                                translationAnimatorSet2.start();*/
                                break;
                        }
                        break;
                    case 1:
                        Log.d("state", i + "");
                        break;
                    case 2:
                        Log.d("state", i + "");
                        break;
                }
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Toast.makeText(this, "this is：" + uri, Toast.LENGTH_SHORT).show();
    }

    public class MyDrawable implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        @Override
        public void transformPage(@NonNull View view, float position) {

            Log.d("position=", " >>>> " + position + "");
            int pageWidth = view.getWidth();
            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }

        }
    }

    public class MyDrawable2 implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {

            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
}
