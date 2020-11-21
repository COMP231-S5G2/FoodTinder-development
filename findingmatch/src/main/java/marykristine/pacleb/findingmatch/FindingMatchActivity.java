package marykristine.pacleb.findingmatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.List;

public class FindingMatchActivity extends AppCompatActivity {
    private static final String TAG = "FindingMatchHome";

    private CardStackAdapter adapter;
    private CardStackLayoutManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finding_match_activity);

        CardStackView cardStackView = findViewById(R.id.card_stack_view);
        manager = new CardStackLayoutManager(this, new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {
                Log.d(TAG, "onCardDragging: d=" + direction.name() +" ratio=" + ratio);

            }

            @Override
            public void onCardSwiped(Direction direction) {
                Log.d(TAG, "onCardSwipe: p=" + manager.getTopPosition() +" d=" + direction);
                if(direction == Direction.Right){
                    Toast.makeText(FindingMatchActivity.this, "Change activity", Toast.LENGTH_SHORT).show();
                }
                if(direction == Direction.Left){
                    Toast.makeText(FindingMatchActivity.this, "Pass", Toast.LENGTH_SHORT).show();
                }

                //Paginating
                if(manager.getTopPosition() == adapter.getItemCount() - 5){
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
                Log.d(TAG, "onCardAppeared" + position +", name: "+ textView.getText());
            }

            @Override
            public void onCardDisappeared(View view, int position) {
                TextView textView = view.findViewById(R.id.item_name);
                Log.d(TAG, "onCardAppeared" + position +", name: "+ textView.getText());
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
        adapter = new CardStackAdapter(addList());
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        cardStackView.setItemAnimator(new DefaultItemAnimator());
    }

    private void paginate() {
        List<ItemModel> oldList = adapter.getItems();
        List<ItemModel> newList = new ArrayList<>(addList());
        CardStackCallback callback = new CardStackCallback(oldList, newList);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        adapter.setItems(newList);
        result.dispatchUpdatesTo(adapter);
    }

    private List<ItemModel> addList() {
        List<ItemModel> items = new ArrayList<>();
        items.add(new ItemModel(R.drawable.beef_wellington,"Beef Wellington", "1500 Yonge Street"));
        items.add(new ItemModel(R.drawable.grilled_lobster,"Grilled Lobster", "2639 Eglinton East Ave"));
        items.add(new ItemModel(R.drawable.hamburger,"Hamburger", "789 This Road"));
        items.add(new ItemModel(R.drawable.macaroons,"Macaroons", "2904 Dundas Street West"));
        items.add(new ItemModel(R.drawable.makisushi,"Maki Sushi", "1890 College Street"));
        items.add(new ItemModel(R.drawable.fried_chicken,"Fried Drumstick Chicken", " 883 That Road"));

        items.add(new ItemModel(R.drawable.beef_wellington,"Beef Wellington", "1500 Yonge Street"));
        items.add(new ItemModel(R.drawable.grilled_lobster,"Grilled Lobster", "2639 Eglinton East Ave"));
        items.add(new ItemModel(R.drawable.hamburger,"Hamburger", "789 This Road"));
        items.add(new ItemModel(R.drawable.macaroons,"Macaroons", "2904 Dundas Street West"));
        items.add(new ItemModel(R.drawable.makisushi,"Maki Sushi", "1890 College Street"));
        items.add(new ItemModel(R.drawable.fried_chicken,"Fried Drumstick Chicken", " 883 That Road"));
        return items;
    }
}