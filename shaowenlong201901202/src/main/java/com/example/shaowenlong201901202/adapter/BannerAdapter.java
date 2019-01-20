package com.example.shaowenlong201901202.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.shaowenlong201901202.bean.ProductDetailsBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class BannerAdapter extends PagerAdapter {
    private Context context;
    private ProductDetailsBean.DataBean list;

    public BannerAdapter(Context context, ProductDetailsBean.DataBean list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        String images = list.getImages();
        Pattern compile = Pattern.compile("\\|");
        String[] split = compile.split(images);
        SimpleDraweeView draweeView = new SimpleDraweeView(context);
        draweeView.setImageURI(split[0%images.length()]);
        container.addView(draweeView);
        return draweeView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
