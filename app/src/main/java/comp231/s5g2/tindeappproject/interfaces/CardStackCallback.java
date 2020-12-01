package comp231.s5g2.tindeappproject.interfaces;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

import comp231.s5g2.tindeappproject.models.Dish;

public class CardStackCallback extends DiffUtil.Callback {

    private final List<Dish> oldList;
    private final List<Dish> newList;

    public CardStackCallback(List<Dish> oldList, List<Dish> newList){
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getImageAcessToken().equals(newList.get(newItemPosition).getImageAcessToken());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition) == newList.get(newItemPosition);
    }
}
