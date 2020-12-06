package comp231.s5g2.tindeappproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import comp231.s5g2.tindeappproject.R;
import comp231.s5g2.tindeappproject.adapter.CardStackAdapter;
import comp231.s5g2.tindeappproject.interfaces.CardStackCallback;
import comp231.s5g2.tindeappproject.interfaces.IRestaurantData;
import comp231.s5g2.tindeappproject.models.Dish;
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
    private ArrayList<Dish> items;
    private IRestaurantData listener;

    //Toolbar
    private Toolbar toolbar;


    //layout Food Restrictions
    private TextView resHalala, resNuts, resVegan, resVegatarian, resPetSafe;
    private LinearLayout layoutFoodRestriction, layoutRadius;

    private FirebaseAuth user = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finding_match_activity);

        //toolbar
        toolbar = findViewById(R.id.AppBar);
        setSupportActionBar(toolbar);

        if (user.getCurrentUser() != null){
            Log.i("User", "User logged in!");
        }

        init();

        //loading dialog
        final CustomFindingMatchDialog findingMatchDialog = new CustomFindingMatchDialog(FindingMatchActivity.this);

        //Id to the restaurant on the view should go here V

        DatabaseReference ownerRef = dbRef.child("Restaurants");
        CardStackView cardStackView = findViewById(R.id.card_stack_view);
        ownerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null) {

                  for (DataSnapshot snap : snapshot.getChildren()) {
                      Owner ownerDB = snap.getValue(Owner.class);
                      //Log.e("Snp", "owner :"+ownerDB.getOwnerID());
                      if (ownerDB != null) {
                          if (ownerDB.getRestaurant() != null) {
                              restaurant = ownerDB.getRestaurant();
                              Log.e("Snap", "owner :" + ownerDB.getRestaurant().getRestaurantName());
                              if (restaurant.getDishes().size() > 0) {
                                  dishesList.addAll(restaurant.getDishes());
                              }
                          }
                      }

                  }
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
                    Dish selectedDish = dishesList.get(manager.getTopPosition() - 1);
                    Toast.makeText(FindingMatchActivity.this, selectedDish.getName(), Toast.LENGTH_SHORT).show();

                    Intent  intent = new Intent(getApplicationContext(), DisplayRestaurantActivity.class);
                    intent.putExtra("SelectedDish", selectedDish.getDishID());
                    intent.putExtra("SelectedOwner", selectedDish.getOwnerID());

                    findingMatchDialog.startLoading();
                    //dismissed alert dialog after 2 secs
                    Handler handler = new Handler();
                    handler.postDelayed(findingMatchDialog::dismissDialog,5000);
                    startActivity(intent);
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

    }// end onCreate

    private void init() {
        //LinearLayout from item_card.xml
        layoutFoodRestriction = findViewById(R.id.layout_foodRestriction);
        layoutRadius = findViewById(R.id.layout_radius);
    }

    private void paginate() {
        List<Dish> oldList = adapter.getItems();
        List<Dish> newList = new ArrayList<>(addList(dishesList));
        CardStackCallback callback = new CardStackCallback(oldList, newList);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        adapter.setItems(newList);
        result.dispatchUpdatesTo(adapter);
    }

    private List<Dish> addList(List<Dish> dishesImgs) {
        items = new ArrayList<>();
        for (Dish dish : dishesImgs) {
            Log.e("dish picture", dish.getImageAcessToken());
            items.add(dish);
        }
        //mixing up all the dishes
        Collections.shuffle(items);
        return items;
    }

    //method for menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_info, menu);
        return true;
    }

    //select item menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.viewUser:
                startActivity(new Intent(getApplicationContext(),ViewProfileActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }//end onOptionsItemSelected


}