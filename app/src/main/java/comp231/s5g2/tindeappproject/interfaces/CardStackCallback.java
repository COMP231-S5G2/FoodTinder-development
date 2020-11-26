package comp231.s5g2.tindeappproject.interfaces;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

import comp231.s5g2.tindeappproject.models.ItemModel;

public class CardStackCallback extends DiffUtil.Callback {

    private List<ItemModel> oldList, newList;

    public CardStackCallback(List<ItemModel> oldList, List<ItemModel> newList){
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
        return oldList.get(oldItemPosition).getImage() == newList.get(newItemPosition).getImage();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition) == newList.get(newItemPosition);
    }
}
