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
    Context context;

    public AdapterListDishes(Context context, List<Dish> dishModel) {

        this.context = context;
        this.dishList = dishModel;

    }


    public AdapterListDishes() {
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView dishName;
        TextView dishDescription;
        TextView dishPrice;
        ImageView dishImage;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            dishName = itemView.findViewById(R.id.dishNameText);
            dishDescription = itemView.findViewById(R.id.dishDescriptionText);
            dishPrice = itemView.findViewById(R.id.priceText);
            dishImage = itemView.findViewById(R.id.dishImage);

        }
    }


        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View dishList = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_dish_list, parent, false);

            return new MyViewHolder(dishList);

        }

        @SuppressLint("CheckResult")
        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            Dish dish = dishList.get(position);

           //MyViewHolder myViewHolder = (MyViewHolder) holder;
            holder.dishName.setText(dish.getName());
            holder.dishDescription.setText(dish.getDescription());

            Log.e("Adapter", "Inside adapter"+dish.getDishID());

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.ic_baseline_add_photo_alternate_24);
            requestOptions.error(R.drawable.ic_baseline_error_outline_24);

            StorageReference strPicRef = FirebaseStorage.getInstance().getReference();
            strPicRef.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(context)
                    .load(dish.getImageAcessToken())
                    .apply(requestOptions)
                    .into(holder.dishImage));
        }


    @Override
    public int getItemCount() {

        return dishList.size();
    }
}


