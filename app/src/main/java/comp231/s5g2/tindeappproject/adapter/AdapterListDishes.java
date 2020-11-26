package comp231.s5g2.tindeappproject.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import comp231.s5g2.tindeappproject.R;
import comp231.s5g2.tindeappproject.models.Dish;

public class AdapterListDishes extends RecyclerView.Adapter<AdapterListDishes.MyViewHolder> {

    List<Dish> dishList = new ArrayList<>();


    public AdapterListDishes( List<Dish> dishModel) {

        this.dishList = dishModel;

    }


    public AdapterListDishes() {
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View dishListView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_dish_list, parent, false);
        return new MyViewHolder(dishListView);

    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.setData(dishList.get(position));
    }


    @Override
    public int getItemCount() {

        return dishList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView dishName;
        TextView dishDescription;
        TextView dishPrice;
        ImageView dishImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dishName = itemView.findViewById(R.id.dishNameText);
            dishDescription = itemView.findViewById(R.id.dishDescriptionText);
            dishPrice = itemView.findViewById(R.id.priceText);
            dishImage = itemView.findViewById(R.id.dishImageAdapter);
        }

        @SuppressLint("SetTextI18n")
        void setData(Dish dish) {

            String uri = "gs://tinderappproject-59233.appspot.com"+dish.getImageAcessToken();

            FirebaseStorage storage = FirebaseStorage.getInstance();
            // Create a reference to a file from a Google Cloud Storage URI
            StorageReference gsReference = storage.getReferenceFromUrl(uri);
            Glide.with(dishImage.getContext())
                    .load(gsReference)
                    .into(dishImage);

            dishName.setText(dish.getName());
            dishDescription.setText(dish.getDescription());
            dishPrice.setText("$"+dish.getPrice().toString());
        }


        }

    }


