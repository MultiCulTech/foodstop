package com.example.foodstop;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ListView lvfood;
    Spinner sptype;
    String userid,name,phone;
    ArrayList<HashMap<String, String>> foodlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvfood = findViewById(R.id.listviewFood);
        sptype = findViewById(R.id.spinnerFood);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        userid = bundle.getString("userid");
        name = bundle.getString("name");
        phone = bundle.getString("phone");
        loadFood(sptype.getSelectedItem().toString());

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark)));

        lvfood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, restlist.get(position).get("restid"), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, FoodActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("foodid",foodlist.get(position).get("foodid"));
                bundle.putString("title",foodlist.get(position).get("title"));
                bundle.putString("rate",foodlist.get(position).get("rate"));
                bundle.putString("type",foodlist.get(position).get("type"));
                bundle.putString("description",foodlist.get(position).get("description"));

                bundle.putString("userid",userid);
                bundle.putString("userphone",phone);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        sptype.setSelection(0,false);
        sptype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadFood(sptype.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void loadFood(final String type) {
        class LoadFood extends AsyncTask<Void,Void,String>{

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("type",type);
                RequestHandler rh = new RequestHandler();
                foodlist = new ArrayList<>();
                String s = rh.sendPostRequest
                        ("http://funsproject.com/chindb/load_food.php",hashMap);
                return s;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                foodlist.clear();
                try{
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray foodarray = jsonObject.getJSONArray("food");
                    for (int i=0;i<foodarray.length();i++){
                        JSONObject c = foodarray.getJSONObject(i);
                        String mid = c.getString("foodid");
                        String rtitle = c.getString("title");
                        String rrate = c.getString("rate");
                        String rtype = c.getString("type");
                        String rdescription = c.getString("description");

                        HashMap<String,String> foodlisthash = new HashMap<>();
                        foodlisthash.put("foodid",mid);
                        foodlisthash.put("title",rtitle);
                        foodlisthash.put("duration",rrate);
                        foodlisthash.put("genre",rtype);
                        foodlisthash.put("description",rdescription);
                        foodlist.add(foodlisthash);
                    }
                }catch (final JSONException e){
                    Log.e("JSONERROR",e.toString());
                }

                ListAdapter adapter = new CustomAdapter(
                        MainActivity.this, foodlist,
                        R.layout.list_food, new String[]
                        {"title","rate","type"}, new int[]
                        {R.id.titleTextView,R.id.rateTextView,R.id.typeTextView});
                lvfood.setAdapter(adapter);
            }

        }
        LoadFood loadFood = new LoadFood();
        loadFood.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logo:
                Intent intent1 = new Intent(MainActivity.this,AboutActivity.class);
                startActivity(intent1);
                return true;
            case R.id.myprofile:
                Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userid",userid);
                bundle.putString("username",name);
                bundle.putString("phone",phone);
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            case R.id.logout:
                Intent intent2 = new Intent(MainActivity.this,LoginActivity.class);
                Toast.makeText(MainActivity.this, "Logout Success",
                        Toast.LENGTH_SHORT).show();
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
