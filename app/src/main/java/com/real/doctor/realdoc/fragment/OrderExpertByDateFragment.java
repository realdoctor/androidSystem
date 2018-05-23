package com.real.doctor.realdoc.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.real.doctor.realdoc.R;
import com.real.doctor.realdoc.activity.ProductShowActivity;
import com.real.doctor.realdoc.adapter.ExpertAdapter;
import com.real.doctor.realdoc.adapter.OrderDateAdapter;
import com.real.doctor.realdoc.adapter.ProductAdapter;
import com.real.doctor.realdoc.base.BaseFragment;
import com.real.doctor.realdoc.model.BrandBean;
import com.real.doctor.realdoc.model.ExpertBean;
import com.real.doctor.realdoc.model.ProductInfo;
import com.real.doctor.realdoc.model.WeekModel;
import com.real.doctor.realdoc.rxjavaretrofit.entity.BaseObserver;
import com.real.doctor.realdoc.rxjavaretrofit.http.HttpRequestClient;
import com.real.doctor.realdoc.util.DocUtils;
import com.real.doctor.realdoc.view.HorizontalListView;
import com.real.doctor.realdoc.wxapi.WXEntryActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/4/18.
 */

public class OrderExpertByDateFragment extends BaseFragment {
    @BindView(R.id.hlv_date)
    HorizontalListView horizontalListView;
    @BindView(R.id.lv_expert)
    ListView lv_expert;
    ArrayList<ExpertBean> arrayList=new ArrayList<ExpertBean>();
    ExpertAdapter expertAdapter;
    ArrayList<WeekModel> weekList=new ArrayList<WeekModel>();
    OrderDateAdapter orderDateAdapter;
    String hospitalId;
    String deptName;
    private Unbinder unbinder;
    public static OrderExpertByDateFragment newInstance(String hospitalId, String deptName) {
        OrderExpertByDateFragment fragment=new OrderExpertByDateFragment();
        Bundle bundel=new Bundle();
        bundel.putString("hospitalId",hospitalId);
        bundel.putString("deptName",deptName);
        fragment.setArguments(bundel);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_expert_by_date;
    }

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void doBusiness(Context mContext) {
        if(getArguments()!=null) {
            hospitalId =(String)getArguments().get("hospitalId");
            deptName=(String)getArguments().get("deptName");
            expertAdapter=new ExpertAdapter(getContext(),arrayList);
            lv_expert.setAdapter(expertAdapter);
            orderDateAdapter=new OrderDateAdapter(getContext(),weekList);
            horizontalListView.setAdapter(orderDateAdapter);
            horizontalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final int location = position;
                    orderDateAdapter.setSelectedPosition(position);
                    orderDateAdapter.notifyDataSetInvalidated();
                    WeekModel model=(WeekModel) parent.getAdapter().getItem(position);
                    getExpert(hospitalId,deptName,model.worktimeWeek);
                }
            });
            getExpertDate(hospitalId,deptName);
        }
    }

    @Override
    public void widgetClick(View v) {

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    private void getExpert(String hospitalId,String deptName,String orderDay){
        HashMap<String,Object> param=new HashMap<>();
        param.put("hospitalId",hospitalId);
        param.put("deptName",deptName);
        param.put("orderDay",orderDay);
        HttpRequestClient.getInstance(getContext()).createBaseApi().get(" guahao/hospital/orderDateExpert/"
                , param, new BaseObserver<ResponseBody>(getContext()) {

                    @Override

                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    protected void onHandleSuccess(ResponseBody responseBody) {
                        String data = null;
                        String msg = null;
                        String code = null;
                        try {
                            data = responseBody.string().toString();
                            try {
                                JSONObject object = new JSONObject(data);
                                if (DocUtils.hasValue(object, "msg")) {
                                    msg = object.getString("msg");
                                }
                                if (DocUtils.hasValue(object, "code")) {
                                    code = object.getString("code");
                                }
                                if (msg.equals("ok") && code.equals("0")) {
                                    arrayList.clear();
                                    JSONArray jsonObject=object.getJSONArray("data");
                                    Gson localGson = new GsonBuilder()
                                            .create();
                                    arrayList.addAll((ArrayList<ExpertBean>)localGson.fromJson(jsonObject.toString(),
                                            new TypeToken<ArrayList<ExpertBean>>() {
                                            }.getType()));
                                    expertAdapter.notifyDataSetChanged();

                                } else {
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                });
    }
    private void getExpertDate(String hospitalId,String deptName){
        HashMap<String,Object> param=new HashMap<>();
        param.put("hospitalId",hospitalId);
        param.put("deptName",deptName);
        HttpRequestClient.getInstance(getContext()).createBaseApi().get(" guahao/hospital/orderDate/"
                , param, new BaseObserver<ResponseBody>(getContext()) {

                    @Override

                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    protected void onHandleSuccess(ResponseBody responseBody) {
                        String data = null;
                        String msg = null;
                        String code = null;
                        try {
                            data = responseBody.string().toString();
                            try {
                                JSONObject object = new JSONObject(data);
                                if (DocUtils.hasValue(object, "msg")) {
                                    msg = object.getString("msg");
                                }
                                if (DocUtils.hasValue(object, "code")) {
                                    code = object.getString("code");
                                }
                                if (msg.equals("ok") && code.equals("0")) {
                                    JSONArray jsonObject=object.getJSONArray("data");
                                    Gson localGson = new GsonBuilder()
                                            .create();
                                    weekList.addAll((ArrayList<WeekModel>)localGson.fromJson(jsonObject.toString(),
                                            new TypeToken<ArrayList<WeekModel>>() {
                                            }.getType()));
                                    orderDateAdapter.notifyDataSetChanged();
                                    selectDefault();
                                } else {
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                });
    }
    //默认选中
    private void selectDefault(){
        final int location=0;
        orderDateAdapter.setSelectedPosition(0);
        orderDateAdapter.notifyDataSetInvalidated();
        final WeekModel bean=	(WeekModel) orderDateAdapter.getItem(0);
        getExpert(hospitalId,deptName,bean.worktimeWeek);
    }
}