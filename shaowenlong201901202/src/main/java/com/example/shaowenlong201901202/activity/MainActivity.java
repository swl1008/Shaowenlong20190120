package com.example.shaowenlong201901202.activity;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.shaowenlong201901202.R;
import com.example.shaowenlong201901202.adapter.ProductShowAdapter;
import com.example.shaowenlong201901202.apis.Apis;
import com.example.shaowenlong201901202.bean.ProductBean;
import com.example.shaowenlong201901202.presenter.PresenterImpl;
import com.example.shaowenlong201901202.view.Iview;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * date 2019/01/20
 * user 邵文龙
 */

public class MainActivity extends AppCompatActivity implements Iview {

    @BindView(R.id.product_show_recycle)
    RecyclerView product_show_recycle;
    private PresenterImpl presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = new PresenterImpl(this);
        getData();
    }
    //请求网络
    public void getData(){
        Map<String,String> map = new HashMap<>();
        presenter.startRequestPost(Apis.URL_PRODUCT_SHOW,map,ProductBean.class);
    }
    //获取数据
    @Override
    public void showData(Object data) {
        if (data instanceof ProductBean){
            ProductBean productBean = (ProductBean) data;
            final ProductShowAdapter showAdapter = new ProductShowAdapter(this);
            //布局
            product_show_recycle.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));
            //设置分割线
            product_show_recycle.addItemDecoration(new DividerItemDecoration(MainActivity.this,DividerItemDecoration.VERTICAL));
            //设置适配器
            product_show_recycle.setAdapter(showAdapter);
            showAdapter.setData(productBean.getData());
            //长按条目删除
            showAdapter.setLongClick(new ProductShowAdapter.onItemLongClickListener() {
                @Override
                public void onLongClick(int position) {
                    showAdapter.delData(position);
                }

                @Override
                public void onClick(int pid) {

                }
            });
        }
    }
    //销毁
    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}
