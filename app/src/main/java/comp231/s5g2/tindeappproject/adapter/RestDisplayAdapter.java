package comp231.s5g2.tindeappproject.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import comp231.s5g2.tindeappproject.R;
import comp231.s5g2.tindeappproject.models.Dish;

public class RestDisplayAdapter extends ArrayAdapter<Dish> {

    private Context context;
    private List<Dish> dishes;
    ImageView dishImage;

    StorageReference dishImageRef = FirebaseStorage.getInstance().getReference();


    public RestDisplayAdapter(@NonNull Context context, List<Dish> dishes) {
        super(context, 0, dishes);
        this.context = context;
        this.dishes = dishes;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        //checks cashed
        View view = convertView;

        if (view == null) {
            //initialize the layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_dishes, parent, false);
        }
        if (dishes.size() > 0) {

            dishImage = (ImageView) convertView.findViewById(R.id.dishImageAdapter);
            dishImageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                Log.e("picDish", "success");
                if (uri != null) {
                    Glide.with(context)
                            .load(uri)
                            .into(dishImage);

                }

            });
        }
        return view;
    }
}


