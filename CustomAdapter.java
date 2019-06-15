package com.example.foodstop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomAdapter extends SimpleAdapter {

    private Context mContext;
    public LayoutInflater inflater=null;
    public CustomAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        mContext = context;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        try{
            if(convertView==null)
                vi = inflater.inflate(R.layout.list_food, null);
            HashMap<String, Object> data = (HashMap<String, Object>) getItem(position);
            TextView tvtitle = vi.findViewById(R.id.titleTextView);
            TextView tvrate = vi.findViewById(R.id.rateTextView);
            TextView tvtype = vi.findViewById(R.id.typeTextView);

            ImageView imgfood =vi.findViewById(R.id.foodImageView);

            String dtitle = (String) data.get("title");
            String drate =(String) data.get("rate");
            String dtype =(String) data.get("type");
            String dmid=(String) data.get("foodid");

            tvtitle.setText(dtitle);
            tvrate.setText(drate);
            tvtype.setText(dtype);

            String image_url = "http://funsproject.com/chindb/imageUpload/foodCover/"+dmid+".jpg";
            Picasso.get().load(image_url).fit().into(imgfood);

        }catch (IndexOutOfBoundsException e){

        }

        return vi;
    }
}
