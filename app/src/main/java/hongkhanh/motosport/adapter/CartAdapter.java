package hongkhanh.motosport.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import hongkhanh.motosport.R;
import hongkhanh.motosport.model.Product;

public class CartAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> productArrayList ;

    public CartAdapter (ArrayList<Product> products,Context context){
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
        public TextView tv_name, tv_price, tv_count;
        public ImageView iv_product, iv_plus;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CartAdapter.ViewHolder viewHolder = null;
        if (viewHolder == null){
            viewHolder = new CartAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_listview_cart, null);
            viewHolder.tv_name = convertView.findViewById(R.id.tv_product_name_cart);
            viewHolder.tv_price = convertView.findViewById(R.id.tv_product_price_cart);
            viewHolder.iv_product = convertView.findViewById(R.id.iv_product);
            viewHolder.tv_count = convertView.findViewById(R.id.tv_product_count_cart);


           viewHolder.iv_plus =  convertView.findViewById(R.id.iv_plus_cart);


            convertView.setTag(viewHolder);
        }else {
            viewHolder = (CartAdapter.ViewHolder) convertView.getTag();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        final Product product = (Product) getItem(position);
        viewHolder.tv_name.setText(product.getName());
        viewHolder.tv_price.setText(product.getPrice());
        viewHolder.tv_count.setText(String.valueOf(product.getCount()));
        Picasso.with(context).load(product.getImage())
                .placeholder(R.drawable.ic_place_holder)
                .error(R.drawable.ic_error)

                .into(viewHolder.iv_product);

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.iv_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalViewHolder.tv_price.setText(product.getPrice());
                product.setCount(product.getCount() + 1);
                Toast.makeText(context, "so luong: " + product.getCount() , Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
}
