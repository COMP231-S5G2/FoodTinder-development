package comp231.s5g2.tindeappproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import comp231.s5g2.tindeappproject.R;
import comp231.s5g2.tindeappproject.models.Dish;

public class AdapterListDishes extends RecyclerView.Adapter<AdapterListDishes.MyViewHolder> {
    public AdapterListDishes() {
    }

    private List<Dish> dishList;
    //private List<String> dishName;

   public AdapterListDishes(List<Dish> list) {
        this.dishList = list;

    }
/*
    public AdapterListDishes(List<String> dishName){
        this.dishName = dishName;
    }
    */


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View dishList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_dish_list, parent, false);

        return new MyViewHolder(dishList);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Dish dish = dishList.get(position);

        holder.dishName.setText(dish.getNome());
        holder.dishDescription.setText(dish.getDescription());
        holder.dishPrice.setText("$"+dish.getPrice().toString());
        holder.


    }

    @Override
    public int getItemCount() {

        return dishList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView dishName;
        TextView dishDescription;
        TextView dishPrice;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            dishName = itemView.findViewById(R.id.dishNameText);
            dishDescription=itemView.findViewById(R.id.dishDescriptionText);
            dishPrice = itemView.findViewById(R.id.priceText);

        }
    }


}
