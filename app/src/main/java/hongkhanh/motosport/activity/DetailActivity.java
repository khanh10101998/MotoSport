package hongkhanh.motosport.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import hongkhanh.motosport.R;
import hongkhanh.motosport.model.Product;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvAddToCart, tvCount, tvName, tvPrice;
    ImageView ivPlus, ivMinus, ivImage;
    int number =1;
    int id;
    String name;
    String price;
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initData();
        initControl();
        initDisplay();
        initEvent();
    }

    private void initData() {
        Intent i = getIntent();
        id =  i.getIntExtra("ID",1);
        name  = i.getStringExtra("NAME");
        price = i.getStringExtra("PRICE");
        image = i.getStringExtra("IMAGE");

    }

    private void initControl() {
        tvAddToCart = findViewById(R.id.tv_add_to_cart_detail);
        tvCount = findViewById(R.id.tv_count_detail);
        ivMinus = findViewById(R.id.iv_minus_detail);
        ivPlus = findViewById(R.id.iv_plus_detail);
        tvName = findViewById(R.id.tv_name_detail);
        tvPrice = findViewById(R.id.tv_price_detail);
        ivImage= findViewById(R.id.iv_image_detail);


//        1 lat bo
//        MainActivity.arrayListCartMain = new ArrayList<>();
    }


    private void initDisplay() {
        tvName.setText(name);

        tvPrice.setText(FormatNumber(price));
        Picasso.with(this).load(image)
                .placeholder(R.drawable.ic_place_holder)
                .error(R.drawable.ic_error)
                .into(ivImage);
    }

    private void initEvent() {
        tvAddToCart.setOnClickListener(this);
        ivMinus.setOnClickListener(this);
        ivPlus.setOnClickListener(this);
    }

    private String FormatNumber(String price){

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
       return  (decimalFormat.format(Integer.valueOf(price)) + "VND");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_add_to_cart_detail:
            {
                if(MainActivity.arrayListCartMain.size() > 0){
                    int sl = Integer.parseInt(tvCount.getText().toString());
                    boolean exists = false;
                    for (int i =0; i < MainActivity.arrayListCartMain.size(); i++ ){
                        if(MainActivity.arrayListCartMain.get(i).getId() == id){
                            MainActivity.arrayListCartMain.get(i).setCount(MainActivity.arrayListCartMain.get(i).getCount() + sl );
                            int sum = Integer.valueOf(price) * MainActivity.arrayListCartMain.get(i).getCount();
                            MainActivity.arrayListCartMain.get(i).setPrice(String.valueOf(sum));
                            exists = true;
                            Toast.makeText(this, MainActivity.arrayListCartMain.get(i).getPrice() + "|", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if ( exists == false ){
                        int count = Integer.parseInt(tvCount.getText().toString());
                        int priceNew1 = count * Integer.parseInt(price);
                        Log.d("HongKhanh", "count 1: " + priceNew1  );
                        MainActivity.arrayListCartMain.add(new Product(id,name,priceNew1+"",image,count));
                    }
                }else {
                    int count = Integer.parseInt(tvCount.getText().toString());
                    int priceNew = count * Integer.parseInt(price);
                    Log.d("HongKhanh", "count 2: " + priceNew  );
                    MainActivity.arrayListCartMain.add(new Product(id,name,priceNew+"",image,count));
                }
                Intent intentCart = new Intent(DetailActivity.this, CartActivity.class);
                startActivity(intentCart);

            }
                break;
            case R.id.iv_plus_detail:
            {                    number ++;
                    tvCount.setText(number + "");
            }
                break;
            case R.id.iv_minus_detail:
            {
                if(number >1) {
                    --number;
                    tvCount.setText(number + "");
                }else {}
            }
                break;
        }
    }
}
