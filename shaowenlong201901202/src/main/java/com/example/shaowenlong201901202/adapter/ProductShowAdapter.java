package com.example.shaowenlong201901202.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shaowenlong201901202.R;
import com.example.shaowenlong201901202.activity.DetailsActivity;
import com.example.shaowenlong201901202.bean.ProductBean;
import com.facebook.drawee.view.SimpleDraweeView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.http.Url;

/**
 * date 2019/01/20
 * user 邵文龙
 */
public class ProductShowAdapter extends RecyclerView.Adapter<ProductShowAdapter.ViewHolder> {
    private Context context;
    private List<ProductBean.DataBean> list;

    public ProductShowAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setData(List<ProductBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void delData(int position) {
        list.remove(position);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_show_item,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.product_show_item_name.setText(list.get(i).getTitle());
        viewHolder.product_show_item_price.setText("￥"+list.get(i).getPrice()+"");
        //截取图片
        Pattern compile = Pattern.compile("\\|");
        String[] split = compile.split(list.get(i).getImages());
        Uri uri = Uri.parse(split[0]);
        viewHolder.product_show_item_image.setImageURI(uri);

        final int position = viewHolder.getLayoutPosition();

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (itemLongClickListener != null){
                    itemLongClickListener.onLongClick(position);
                }
                return true;
            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemLongClickListener != null){
                    Intent intent = new Intent(context,DetailsActivity.class);
                    intent.putExtra("pid",list.get(i).getPid());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.product_show_item_image)
        SimpleDraweeView product_show_item_image;
        @BindView(R.id.product_show_item_name)
        TextView product_show_item_name;
        @BindView(R.id.product_show_item_price)
        TextView product_show_item_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private onItemLongClickListener itemLongClickListener;

    public void setLongClick(onItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    public interface onItemLongClickListener{
        void onLongClick(int position);
        void onClick(int pid);
    }
}
