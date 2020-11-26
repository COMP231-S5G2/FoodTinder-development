package comp231.s5g2.tindeappproject.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.List;

import comp231.s5g2.tindeappproject.R;
import comp231.s5g2.tindeappproject.adapter.CardStackAdapter;
import comp231.s5g2.tindeappproject.interfaces.CardStackCallback;
import comp231.s5g2.tindeappproject.models.Dish;
import comp231.s5g2.tindeappproject.models.ItemModel;
import comp231.s5g2.tindeappproject.models.Owner;
import comp231.s5g2.tindeappproject.models.Restaurant;

public class FindingMatchActivity extends AppCompatActivity {
    private static final String TAG = "FindingMatchHome";
    private CardStackAdapter adapter;
    private CardStackLayoutManager manager;
    private Owner owner = new Owner();
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    List<Dish> dishesList = new ArrayList<>();
    Restaurant restaurant = new Restaurant();
    private ArrayList<ItemModel> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finding_match_activity);

        //Id to the restaurant on the view should go here V
        owner.setOwnerID("3");

        DatabaseReference ownerRef = dbRef.child("Restaurants")
                .child(owner.getOwnerID());

        CardStackView cardStackView = findViewById(R.id.card_stack_view);
        ownerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null) {
                    Log.e("owner ref", "in Dishes");
                    owner = snapshot.getValue(Owner.class);
                    restaurant = owner.getRestaurant();
                    dishesList = restaurant.getDishes();
                    if (dishesList.size() > 0) {
                        adapter = new CardStackAdapter(addList(dishesList));
                        cardStackView.setAdapter(adapter);
                    }

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        manager = new CardStackLayoutManager(this, new CardStackListener() {


            @Override
            public void onCardDragging(Direction direction, float ratio) {
                Log.d(TAG, "onCardDragging: d=" + direction.name() + " ratio=" + ratio);

            }


            @Override
            public void onCardSwiped(Direction direction) {
                Log.d(TAG, "onCardSwipe: p=" + manager.getTopPosition() + " d=" + direction);
                if (direction == Direction.Right) {
                    ItemModel selectedDish = items.get(manager.getTopPosition() - 1);

                    Toast.makeText(FindingMatchActivity.this, selectedDish.getName(), Toast.LENGTH_SHORT).show();

                }
                if (direction == Direction.Left) {
                    Toast.makeText(FindingMatchActivity.this, "Pass", Toast.LENGTH_SHORT).show();
                }

                //Paginating
                if (manager.getTopPosition() == adapter.getItemCount() - 5) {
                    paginate();
                }
            }

            @Override
            public void onCardRewound() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardCanceled() {
                Log.d(TAG, "onCardCancel: " + manager.getTopPosition());
            }

            @Override
            public void onCardAppeared(View view, int position) {
                TextView textView = view.findViewById(R.id.item_name);
                Log.d(TAG, "onCardAppeared" + position + ", name: " + textView.getText());
            }

            @Override
            public void onCardDisappeared(View view, int position) {
                TextView textView = view.findViewById(R.id.item_name);
                Log.d(TAG, "onCardAppeared" + position + ", name: " + textView.getText());
            }
        });
        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setDirections(Direction.HORIZONTAL);
        manager.setCanScrollHorizontal(true);
        manager.setSwipeableMethod(SwipeableMethod.Manual);
        manager.setOverlayInterpolator(new LinearInterpolator());
        //adapter = new CardStackAdapter(addList(dishesList));
        cardStackView.setLayoutManager(manager);
        //cardStackView.setAdapter(adapter);
        cardStackView.setItemAnimator(new DefaultItemAnimator());
    }

    private void paginate() {
        List<ItemModel> oldList = adapter.getItems();
        List<ItemModel> newList = new ArrayList<>(addList(dishesList));
        CardStackCallback callback = new CardStackCallback(oldList, newList);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        adapter.setItems(newList);
        result.dispatchUpdatesTo(adapter);
    }

    private List<ItemModel> addList(List<Dish> dishesImgs) {
        items = new ArrayList<ItemModel>();
        for (Dish dish : dishesImgs) {
            Log.e("dish picture", dish.getImageAcessToken());
            items.add(new ItemModel(dish.getImageAcessToken(), dish.getName(), restaurant.getRestaurantAddress(), dish.getDishID()));
        }
        return items;
    }

}