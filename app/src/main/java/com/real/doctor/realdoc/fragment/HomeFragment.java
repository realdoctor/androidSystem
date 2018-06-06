package com.real.doctor.realdoc.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.real.doctor.realdoc.R;
import com.real.doctor.realdoc.activity.DoctorsListActivity;
import com.real.doctor.realdoc.activity.LoginActivity;
import com.real.doctor.realdoc.activity.ProductShowByCategoryActivity;
import com.real.doctor.realdoc.activity.DocContentActivity;
import com.real.doctor.realdoc.activity.RecordListActivity;
import com.real.doctor.realdoc.activity.RegistrationsActivity;
import com.real.doctor.realdoc.activity.ScannerActivity;
import com.real.doctor.realdoc.activity.SearchHistoryListActivity;
import com.real.doctor.realdoc.adapter.HomeRecordAdapter;
import com.real.doctor.realdoc.base.BaseFragment;
import com.real.doctor.realdoc.greendao.table.SaveDocManager;
import com.real.doctor.realdoc.model.SaveDocBean;
import com.real.doctor.realdoc.util.DocUtils;
import com.real.doctor.realdoc.util.EmptyUtils;
import com.real.doctor.realdoc.util.ScreenUtil;
import com.real.doctor.realdoc.util.ToastUtil;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGABannerUtil;

/**
 * user：lqm
 * desc：第一个模块，主页Fragment
 */

public class HomeFragment extends BaseFragment {

    private Unbinder unbinder;
    @BindView(R.id.home_search)
    RelativeLayout homeSearch;
    @BindView(R.id.save_doc_linear)
    LinearLayout saveDocLinear;
    @BindView(R.id.appoint_icon)
    LinearLayout appointIconLinear;
    @BindView(R.id.doctor_online)
    LinearLayout doctorOnline;
    @BindView(R.id.search_text)
    TextView searchText;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.bga_banner)
    BGABanner bgaBanner;
    @BindView(R.id.scan_icon)
    ImageView scanIcon;
    @BindView(R.id.title_linear)
    LinearLayout titleLinear;
    private HomeRecordAdapter adapter;
    private SaveDocManager instance = null;
    private List<SaveDocBean> recordList;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_home;
    }

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        //加上沉浸式状态栏高度
        int statusHeight = ScreenUtil.getStatusHeight(getActivity());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) titleLinear.getLayoutParams();
            lp.topMargin = statusHeight;
            titleLinear.setLayoutParams(lp);
        }
        searchText.getBackground().setAlpha(180);
    }

    @Override
    public void doBusiness(Context mContext) {
        //滚轮
        List<View> views = new ArrayList<>();
        views.add(BGABannerUtil.getItemImageView(getActivity(), R.mipmap.useravator_bg));
        views.add(BGABannerUtil.getItemImageView(getActivity(), R.mipmap.login_bg));
        views.add(BGABannerUtil.getItemImageView(getActivity(), R.mipmap.bg_healthy));
        bgaBanner.setData(views);
        bgaBanner.setDelegate(new BGABanner.Delegate<ImageView, String>() {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position) {
                ToastUtil.showLong(banner.getContext(), "点击了" + position);
            }
        });
        instance = SaveDocManager.getInstance(getActivity());
        if (EmptyUtils.isNotEmpty(instance)) {
            recordList = instance.querySaveDocList(getActivity());
            recycleView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            //添加Android自带的分割线
            recycleView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
            adapter = new HomeRecordAdapter(R.layout.home_record_item, recordList);
            recycleView.setAdapter(adapter);
        }
        initEvent();
        localBroadcast();
    }

    private void localBroadcast() {
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LoginActivity.RECORD_LIST_HOME);
        BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (EmptyUtils.isNotEmpty(instance)) {
                    recordList = instance.querySaveDocList(getActivity());
                    recycleView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    //添加Android自带的分割线
                    recycleView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
                    adapter = new HomeRecordAdapter(R.layout.home_record_item, recordList);
                    recycleView.setAdapter(adapter);
                }
            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);
    }

    private void initEvent() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //点击item事件
                SaveDocBean bean = (SaveDocBean) adapter.getItem(position);
                Intent intent = new Intent(getActivity(), DocContentActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putParcelable("SaveDocBean", bean);
                intent.putExtras(mBundle);
                startActivity(intent);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    @OnClick({R.id.home_search, R.id.save_doc_linear, R.id.base_cure, R.id.doctor_online, R.id.scan_icon, R.id.appoint_icon})
    @Override
    public void widgetClick(View v) {
        if (DocUtils.isFastClick()) {
            Intent intent;
            switch (v.getId()) {
                case R.id.home_search:
                    intent = new Intent(getActivity(), SearchHistoryListActivity.class);
                    intent.putExtra("requestCode", RegistrationsActivity.REGISTRATION_EVENT_REQUEST_CODE);;
                    startActivity(intent);
                    break;
                case R.id.save_doc_linear:
                    intent = new Intent(getActivity(), RecordListActivity.class);
                    startActivity(intent);
                    break;
                case R.id.base_cure:
                    intent = new Intent(getActivity(), ProductShowByCategoryActivity.class);
                    startActivity(intent);
                    break;
                case R.id.doctor_online:
                    //在线复诊
                    intent = new Intent(getActivity(), DoctorsListActivity.class);
                    startActivity(intent);
                    break;
                case R.id.appoint_icon:
                    intent = new Intent(getActivity(), RegistrationsActivity.class);
                    startActivity(intent);
                    break;
                case R.id.scan_icon:
                    intent = new Intent(getActivity(), ScannerActivity.class);
                    startActivity(intent);
                    break;

            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}