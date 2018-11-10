package hongkhanh.motosport.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import hongkhanh.motosport.R;
import hongkhanh.motosport.model.Product;

public class ProductAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> productArrayList ;

    public ProductAdapter(ArrayList<Product> products,Context context){
        this.productArrayList = products;
        this.context = context;
    }


    @Override
    public int getCount() {
        return productArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return productArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView tv_name, tv_price, tv_detail;
        public ImageView iv_product;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_listview_product, null);
            viewHolder.tv_name = convertView.findViewById(R.id.tv_product_name);
            viewHolder.tv_price = convertView.findViewById(R.id.tv_product_price);
            viewHolder.iv_product = convertView.findViewById(R.id.iv_product);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Product product = (Product) getItem(position);
        viewHolder.tv_name.setText(product.getName());
        viewHolder.tv_price.setText(product.getPrice());
        Picasso.with(context).load(product.getImage())
                .placeholder(R.drawable.ic_place_holder)
                .error(R.drawable.ic_error)

                .into(viewHolder.iv_product);
        return convertView;
    }
}
