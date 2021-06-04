package com.axehome.www.guojinapp.ui.message;

import android.content.Context;
import android.content.Intent;
import android.graphics.Outline;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.beans.BannerBean;
import com.axehome.www.guojinapp.beans.GoodClassBean;
import com.axehome.www.guojinapp.ui.activitys.SearchGoodActivity;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
/*商铺*/
public class MessageFragment extends Fragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_lishi_zhangdan)
    TextView tvLishiZhangdan;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.et_search)
    TextView etSearch;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.b_list)
    Banner cbBanner;
    @BindView(R.id.right_title)
    TextView rightTitle;
    @BindView(R.id.rec_left)
    RecyclerView recLeft;
    @BindView(R.id.rec_right)
    RecyclerView recRight;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;

    private List<String> left;
    private List<ScrollBean> right;
    private ScrollLeftAdapter leftAdapter;
    private ScrollRightAdapter rightAdapter;
    //右侧title在数据中所对应的position集合
    private List<Integer> tPosition = new ArrayList<>();
    private Context mContext;
    //title的高度
    private int tHeight;
    //记录右侧当前可见的第一个item的position
    private int first = 0;
    private GridLayoutManager rightManager;
    private MessageViewModel homeViewModel;
    private List<GoodClassBean> classBeanList = new ArrayList<>();
    private List<BannerBean> listBanner = new ArrayList<BannerBean>();
    private List<String> listAdvert = new ArrayList<>();
    private MyLoader mMyLoader;
    private Unbinder unbinder;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(MessageViewModel.class);
        View root = inflater.inflate(R.layout.fragment_msg, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getGoodList().observe(this, new Observer<List<GoodClassBean>>() {
            @Override
            public void onChanged(@Nullable List<GoodClassBean> mClassList) {
                if (mClassList != null && mClassList.size() > 0) {
                    classBeanList.clear();
                    classBeanList.addAll(mClassList);
                    initView();
                }
            }
        });
        homeViewModel.getBannerList().observe(this, new Observer<List<BannerBean>>() {
            @Override
            public void onChanged(List<BannerBean> bannerBeans) {
                if (listBanner != null && listBanner.size() > 0) {

                } else {
                    listAdvert.clear();
                    listBanner.addAll(bannerBeans);
                    for (int i = 0; i < listBanner.size(); i++) {
                        listAdvert.add(NetConfig.baseurl + listBanner.get(i).getBanner_path());
                    }
                    startBanner();
                }
            }
        });
        unbinder = ButterKnife.bind(this, root);
        mContext = getContext();
        return root;
    }

    private void initView() {
        initData();
        initLeft();
        initRight();
    }


    private void initRight() {

        rightManager = new GridLayoutManager(mContext, 3);

        if (rightAdapter == null) {
            rightAdapter = new ScrollRightAdapter(R.layout.scroll_right, R.layout.layout_right_title, null, getContext());
            recRight.setLayoutManager(rightManager);
            recRight.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.set(dpToPx(mContext, getDimens(mContext, R.dimen.dp3))
                            , 0
                            , dpToPx(mContext, getDimens(mContext, R.dimen.dp3))
                            , dpToPx(mContext, getDimens(mContext, R.dimen.dp3)));
                }
            });
            recRight.setAdapter(rightAdapter);
        } else {
            rightAdapter.notifyDataSetChanged();
        }

        rightAdapter.setNewData(right);

        //设置右侧初始title
        if (right.get(first).isHeader) {
            rightTitle.setText(right.get(first).header);
        }

        recRight.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //获取右侧title的高度
                tHeight = rightTitle.getHeight();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                //判断如果是header
                if (right.get(first).isHeader) {
                    //获取此组名item的view
                    View view = rightManager.findViewByPosition(first);
                    if (view != null) {
                        //如果此组名item顶部和父容器顶部距离大于等于title的高度,则设置偏移量
                        if (view.getTop() >= tHeight) {
                            rightTitle.setY(view.getTop() - tHeight);
                        } else {
                            //否则不设置
                            rightTitle.setY(0);
                        }
                    }
                }

                //因为每次滑动之后,右侧列表中可见的第一个item的position肯定会改变,并且右侧列表中可见的第一个item的position变换了之后,
                //才有可能改变右侧title的值,所以这个方法内的逻辑在右侧可见的第一个item的position改变之后一定会执行
                int firstPosition = rightManager.findFirstVisibleItemPosition();
                if (first != firstPosition && firstPosition >= 0) {
                    //给first赋值
                    first = firstPosition;
                    //不设置Y轴的偏移量
                    rightTitle.setY(0);

                    //判断如果右侧可见的第一个item是否是header,设置相应的值
                    if (right.get(first).isHeader) {
                        rightTitle.setText(right.get(first).header);
                    } else {
                        rightTitle.setText(right.get(first).t.getType());
                    }
                }

                //遍历左边列表,列表对应的内容等于右边的title,则设置左侧对应item高亮
                for (int i = 0; i < left.size(); i++) {
                    if (left.get(i).equals(rightTitle.getText().toString())) {
                        leftAdapter.selectItem(i);
                    }
                }

                //如果右边最后一个完全显示的item的position,等于bean中最后一条数据的position(也就是右侧列表拉到底了),
                //则设置左侧列表最后一条item高亮
                if (rightManager.findLastCompletelyVisibleItemPosition() == right.size() - 1) {
                    leftAdapter.selectItem(left.size() - 1);
                }
            }
        });
    }

    private void initLeft() {
        if (leftAdapter == null) {
            leftAdapter = new ScrollLeftAdapter(R.layout.scroll_left, null);
            recLeft.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
            //recLeft.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
            recLeft.setAdapter(leftAdapter);
        } else {
            leftAdapter.notifyDataSetChanged();
        }

        leftAdapter.setNewData(left);

        leftAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    //点击左侧列表的相应item,右侧列表相应的title置顶显示
                    //(最后一组内容若不能填充右侧整个可见页面,则显示到右侧列表的最底端)
                    case R.id.item:
                        leftAdapter.selectItem(position);
                        rightManager.scrollToPositionWithOffset(tPosition.get(position), 0);
                        break;
                }
            }
        });
    }

    //获取数据(若请求服务端数据,请求到的列表需有序排列)
    private void initData() {
        if (classBeanList != null && classBeanList.size() > 0) {
            left = new ArrayList<>();
            right = new ArrayList<>();
            for (int i = 0; i < classBeanList.size(); i++) {

                if (classBeanList.get(i).getGoodsBeanList() != null && classBeanList.get(i).getGoodsBeanList().size() > 0) {
                    left.add(classBeanList.get(i).getGood_class_name());
                    right.add(new ScrollBean(true, classBeanList.get(i).getGood_class_name()));
                    for (int j = 0; j < classBeanList.get(i).getGoodsBeanList().size(); j++) {
                        right.add(new ScrollBean(new ScrollBean.ScrollItemBean("", classBeanList.get(i).getGood_class_name(), classBeanList.get(i).getGoodsBeanList().get(j))));
                    }
                }
            }
        }
        for (int i = 0; i < right.size(); i++) {
            if (right.get(i).isHeader) {
                //遍历右侧列表,判断如果是header,则将此header在右侧列表中所在的position添加到集合中
                tPosition.add(i);
            }
        }
    }

    /**
     * 获得资源 dimens (dp)
     *
     * @param context
     * @param id      资源id
     * @return
     */
    public float getDimens(Context context, int id) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float px = context.getResources().getDimension(id);
        return px / dm.density;
    }

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    private void startBanner() {

        cbBanner.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 10);
            }
        });
        cbBanner.setClipToOutline(true);
        mMyLoader = new MyLoader();
        //设置样式，里面有很多种样式可以自己都看看效果
        cbBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        /*//轮播图片的文字
        mConvenBanner.setBannerTitles(listAdvertId);*/
        //设置图片加载器
        cbBanner.setImageLoader(mMyLoader);
        //设置是否为自动轮播，默认是true
        cbBanner.isAutoPlay(true);
        //设置指示器的位置，小点点，居中显示
        cbBanner.setIndicatorGravity(BannerConfig.CENTER);
        cbBanner.setDelayTime(3000);

        cbBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                /*if (listBanner != null) {
                    Log.e("aaa", "---listAdvertId.size()---->" + listBanner.size());
                    Intent intent = new Intent(getActivity(), WebViewTxtActivity.class);
                    intent.putExtra("htmlText", listBanner.get(position).getUe_content())
                            .putExtra("name", listBanner.get(position).getBanner_order());
                    startActivity(intent);
                }*/
            }
        });
        //网络图片
        cbBanner.setImages(listAdvert)
                .start();
    }

    @OnClick({R.id.ll_search, R.id.tv_lishi_zhangdan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_search:
                getActivity().startActivity(new Intent(getContext(), SearchGoodActivity.class)
                        .putExtra("type", "1"));
                break;
            case R.id.tv_lishi_zhangdan:
                getActivity().startActivity(new Intent(getContext(), SearchGoodActivity.class)
                        .putExtra("type", "2"));
                break;
        }
    }

    /**
     * 网络加载图片
     * 使用了Glide图片加载框架
     */
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(getActivity())
                    .load((String) path)
                    .into(imageView);
        }
    }

}