package com.real.doctor.realdoc.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.real.doctor.realdoc.R;
import com.real.doctor.realdoc.activity.AddAdviceActivity;
import com.real.doctor.realdoc.activity.SaveRecordActivity;
import com.real.doctor.realdoc.model.ImageBean;
import com.real.doctor.realdoc.model.ImageListBean;
import com.real.doctor.realdoc.photopicker.PhotoPicker;
import com.real.doctor.realdoc.util.EmptyUtils;
import com.real.doctor.realdoc.view.ImageCardDialog;
import com.real.doctor.realdoc.view.SpaceItemDecoration;

import java.util.List;

public class ImageCardAdapter extends BaseQuickAdapter<ImageListBean, BaseViewHolder> {

    //替换图片
    public static final int REQUEST_CODE_REPLACE_IMAGE = 0x120;
    public static final int REQUEST_CODE_CHANGE_ADVICE = 0x130;
    private ImageGridAdapter imageGridAdapter;
    private ImageCardDialog dialog;
    private Context context;
    private View mView;
    private BaseQuickAdapter mAdapter;
    private List<ImageListBean> data;
    private boolean flag = false;

    public ImageCardAdapter(Context context, int layoutResId, @Nullable List<ImageListBean> data) {
        super(layoutResId, data);
        this.context = context;
        this.data = data;
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, ImageListBean item) {
        final String content = item.getContent();
        if (EmptyUtils.isNotEmpty(content)) {
            viewHolder.getView(R.id.advice).setVisibility(View.VISIBLE);
            viewHolder.setText(R.id.advice, item.getContent());
        } else {
            viewHolder.getView(R.id.advice).setVisibility(View.GONE);
        }
        final int groupPos = viewHolder.getLayoutPosition();// 获取当前item的position
        RecyclerView recyclerView = viewHolder.getView(R.id.grid_recycle_chird_view);
//        if (EmptyUtils.isEmpty(imageGridAdapter)) {
        if (!flag) {//此处只能用flag判断，如果直接判断imageGridAdapter为空，则会出现一个第二个item添加不进去的bug
            recyclerView.addItemDecoration(new SpaceItemDecoration(10));
            //GridLayout 3列
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
            //添加进recycleview，并将"+"按钮放在recycleview下面
            recyclerView.setLayoutManager(gridLayoutManager);
            //给RecyclerView设置适配器
            final List<ImageBean> bean = item.getmImgUrlList();
            imageGridAdapter = new ImageGridAdapter(R.layout.image_grid_item, bean);
            //给RecyclerView设置适配器
            recyclerView.setAdapter(imageGridAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
        imageGridAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                //弹出框界面
                dialog = new ImageCardDialog(context).builder()
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(true)
                        .setClickBtn(new ImageCardDialog.ReplaceListener() {
                            @Override
                            public void onReplaceListener() {
                                mAdapter = adapter;
                                //点击替换图片按钮
                                PhotoPicker.builder()
                                        .setPhotoCount(1)
                                        .setShowCamera(false)
                                        .setShowGif(false)
                                        .setPreviewEnabled(true)//是否可以预览
                                        .setPostion(String.valueOf(groupPos), String.valueOf(position))
                                        .start((Activity) mContext, REQUEST_CODE_REPLACE_IMAGE);
                            }
                        }).setAdviceClickBtn(new ImageCardDialog.AdviceListener() {
                            @Override
                            public void onAdviceClick() {
                                mView = view;
                                mAdapter = adapter;
                                ImageBean bean = (ImageBean) adapter.getItem(position);
                                //进入医院标签页
                                Intent intent = new Intent(context, AddAdviceActivity.class);
                                intent.putExtra("pos", String.valueOf(position));
                                intent.putExtra("change", bean.getAdvice());
                                ((Activity) context).startActivityForResult(intent, 0x1000);
                            }
                        }).show();

            }
        });
        TextView textView = viewHolder.getView(R.id.advice);
        if (textView.getVisibility() == View.VISIBLE) {
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //进入医院标签页
                    Intent intent = new Intent(context, AddAdviceActivity.class);
                    intent.putExtra("change", content);
                    intent.putExtra("pos", String.valueOf(groupPos));
                    ((Activity) context).startActivityForResult(intent, REQUEST_CODE_CHANGE_ADVICE);
                }
            });
        }
        ImageView imageView = viewHolder.getView(R.id.delete_icon);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击删除按钮，删除item
                data.remove(groupPos);
                //删除动画
                notifyItemRemoved(groupPos);
                notifyDataSetChanged();
            }
        });
        TextView labelText = viewHolder.getView(R.id.label_text);
        ImageView labelIcon = viewHolder.getView(R.id.label_icon);
        List<ImageBean> imageList = item.getmImgUrlList();
        if (EmptyUtils.isNotEmpty(imageList)) {
            int spare = imageList.get(0).getSpareImage();
            String path = "android.resource://" + mContext.getApplicationContext().getPackageName() + "/";
            if (spare == 0) {
                labelText.setVisibility(View.GONE);
                labelIcon.setVisibility(View.GONE);
            } else if (spare == 1) {
                labelText.setVisibility(View.VISIBLE);
                labelIcon.setVisibility(View.VISIBLE);
                labelText.setText("您选择了处方标签");
                Uri uri = Uri.parse(path + R.mipmap.add);
                Glide.with(mContext).load(uri).crossFade().into(labelIcon);
            } else if (spare == 2) {
                labelText.setVisibility(View.VISIBLE);
                labelIcon.setVisibility(View.VISIBLE);
                labelText.setText("您选择了医嘱标签");
                Uri uri = Uri.parse(path + R.mipmap.bg_healthy);
                Glide.with(mContext).load(uri).crossFade().into(labelIcon);
            } else if (spare == 3) {
                labelText.setVisibility(View.VISIBLE);
                labelIcon.setVisibility(View.VISIBLE);
                labelText.setText("您选择了报告检查标签");
                Uri uri = Uri.parse(path + R.mipmap.avatar_bg);
                Glide.with(mContext).load(uri).crossFade().into(labelIcon);
            } else if (spare == 4) {
                labelText.setText("您选择了处方标签");
                Uri uri = Uri.parse(path + R.mipmap.arrow_white);
                Glide.with(mContext).load(uri).crossFade().into(labelIcon);
            }
        }
    }

    public void updateGridView(int pos, String advice) {
        if (EmptyUtils.isNotEmpty(mView)) {
            mView.findViewById(R.id.advice).setVisibility(View.VISIBLE);
            if (EmptyUtils.isNotEmpty(mAdapter)) {
                ((ImageGridAdapter) mAdapter).getItem(pos).setAdvice(advice);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    public void replaceImg(boolean flag) {
        this.flag = flag;
    }
}
