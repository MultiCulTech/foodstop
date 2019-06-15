package com.example.foodstop;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class FoodActivity extends AppCompatActivity {

    ImageView imgFoodBig, imgFood;
    TextView title, rate, type, description;
    String userid,foodid,userphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        imgFood = findViewById(R.id.foodImageView);
        imgFoodBig = findViewById(R.id.imageView4);
        title = findViewById(R.id.titleTextView);
        rate = findViewById(R.id.rateTextView);
        type = findViewById(R.id.typeTextView);
        description = findViewById(R.id.descriptionTextView);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        foodid = bundle.getString("foodid");
        String rtitle = bundle.getString("title");
        String rrate = bundle.getString("rate");
        String rtype = bundle.getString("type");
        String rdescription = bundle.getString("description");

        userid = bundle.getString("userid");
        userphone = bundle.getString("userphone");

        title.setText(rtitle);
        rate.setText(rrate);
        type.setText(rtype);
        description.setText(rdescription);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Picasso.get().load("http://funsproject.com/chindb/imageUpload/foodBig/"+foodid+".jpg")
                .fit().into(imgFoodBig);
        Picasso.get().load("http://funsproject.com/chindb/imageUpload/foodCover/"+foodid+".jpg")
                .fit().into(imgFood);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(FoodActivity.this,MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userid",userid);
                bundle.putString("phone",userphone);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
