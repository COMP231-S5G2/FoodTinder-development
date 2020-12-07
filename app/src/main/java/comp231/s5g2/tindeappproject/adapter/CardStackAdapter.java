package comp231.s5g2.tindeappproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import comp231.s5g2.tindeappproject.R;
import comp231.s5g2.tindeappproject.location.GPSTracker;
import comp231.s5g2.tindeappproject.models.Dish;

import com.bumptech.glide.Glide;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder>{
    private List<Dish> items;
    GPSTracker gps = new GPSTracker();
    public CardStackAdapter(List<Dish> items)
    {
        this.items = items;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(items.get(position));
    }

    @Override
    public int getItemCount() { return items.size(); }


    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name, location, restriction;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_image);
            name = itemView.findViewById(R.id.item_name);
            location = itemView.findViewById(R.id.item_location);
            restriction = itemView.findViewById(R.id.item_restriction);


        }

        void setData(Dish data) {
            String uri = "gs://tinderappproject-59233.appspot.com"+data.getImageAcessToken();

            FirebaseStorage storage = FirebaseStorage.getInstance();
            // Create a reference to a file from a Google Cloud Storage URI
            StorageReference gsReference = storage.getReferenceFromUrl(uri);
            Glide.with(image.getContext())
                    .load(gsReference)
                    .into(image);

            name.setText(data.getName());
            restriction.setText(data.getRestriction());
        }
    }


    public List<Dish> getItems() {
        return items;
    }

    public void setItems(List<Dish> items) {
        this.items = items;
    }


}
