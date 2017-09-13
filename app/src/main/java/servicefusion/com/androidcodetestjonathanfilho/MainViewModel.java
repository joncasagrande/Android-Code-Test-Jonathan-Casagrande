package servicefusion.com.androidcodetestjonathanfilho;


import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;



/**
 * Created by joncasagrande on 13/09/17.
 */

public class MainViewModel {

    public final ObservableList<String> items = new ObservableArrayList<>();
    public final ItemBinding<String> itemBinding = ItemBinding.of(BR.viewModel, R.layout.row_contact);

    ObservableField<Boolean>noContactAdded;
    public MainViewModel() {
        noContactAdded = new ObservableField<>(true);
    }

    public final BindingRecyclerViewAdapter.ViewHolderFactory viewHolder =
            binding -> new StationIssueViewHolder(binding.getRoot());
    private static class StationIssueViewHolder extends RecyclerView.ViewHolder {
        public StationIssueViewHolder(View itemView) {
            super(itemView);
        }
    }

}
