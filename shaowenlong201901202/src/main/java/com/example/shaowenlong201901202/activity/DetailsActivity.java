package com.example.shaowenlong201901202.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shaowenlong201901202.R;
import com.example.shaowenlong201901202.adapter.BannerAdapter;
import com.example.shaowenlong201901202.apis.Apis;
import com.example.shaowenlong201901202.bean.ProductDetailsBean;
import com.example.shaowenlong201901202.presenter.PresenterImpl;
import com.example.shaowenlong201901202.view.Iview;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * date 2019/01/20
 * user 邵文龙
 */
public class DetailsActivity extends AppCompatActivity implements Iview {

    @BindView(R.id.details_image_head)
    SimpleDraweeView details_image_head;
    @BindView(R.id.details_viewpager)
    ViewPager details_viewpager;
    @BindView(R.id.details_product_name)
    TextView details_product_name;
    @BindView(R.id.details_product_price)
    TextView details_product_price;
    @BindView(R.id.details_button)
    Button details_button;
    private PresenterImpl presenter;
    private int pid;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int currentItem = details_viewpager.getCurrentItem();
            currentItem++;
            details_viewpager.setCurrentItem(currentItem);
            handler.sendEmptyMessageDelayed(1,2000);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        presenter = new PresenterImpl(this);
        Intent intent = getIntent();
        pid = intent.getIntExtra("pid", 0);
        getData();
        handler.sendEmptyMessageDelayed(1,2000);
        //点击监听
        details_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailsActivity.this,"点击了加入购物车",Toast.LENGTH_SHORT).show();
            }
        });
    }
    //请求数据
    public void getData(){
        Map<String,String> map = new HashMap<>();
        map.put("pid",pid+"");
        presenter.startRequestPost(Apis.URL_PRODUCT_DETAILS,map,ProductDetailsBean.class);
    }
    @Override
    public void showData(Object data) {
        if (data instanceof ProductDetailsBean){
            ProductDetailsBean detailsBean = (ProductDetailsBean) data;
            ProductDetailsBean.DataBean result = detailsBean.getData();
            String title = result.getTitle();
            double price = result.getPrice();
            BannerAdapter bannerAdapter = new BannerAdapter(DetailsActivity.this, result);
            details_viewpager.setAdapter(bannerAdapter);
            details_product_name.setText(title);
            details_product_price.setText("￥"+price+"");
        }
    }
    //解绑
    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}
