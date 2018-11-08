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
import hongkhanh.motosport.model.ProductType;

public class ProductTypeAdapter extends BaseAdapter {
    ArrayList<ProductType> arrayListProductType ;
    Context context;

    public ProductTypeAdapter(ArrayList<ProductType> arrayListProductType, Context context) {
        this.arrayListProductType = arrayListProductType;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListProductType.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListProductType.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        TextView txtNameProductType;
        ImageView imgProductType;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_listview_product,null);
            viewHolder.imgProductType = convertView.findViewById(R.id.iv_product_type);
            viewHolder.txtNameProductType = convertView.findViewById(R.id.tv_product_type);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ProductType productType = (ProductType) getItem(position);
        viewHolder.txtNameProductType.setText(productType.getNameType());
            Picasso.with(context).load(productType.getImageType())
                    .placeholder(R.drawable.ic_place_holder)
                    .error(R.drawable.ic_error)

                    .into(viewHolder.imgProductType);
        return convertView;
    }
}
