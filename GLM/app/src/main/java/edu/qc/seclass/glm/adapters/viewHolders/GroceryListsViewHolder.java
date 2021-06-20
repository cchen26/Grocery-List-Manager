package edu.qc.seclass.glm.adapters.viewHolders;

import android.view.View;
import android.widget.TextView;

import edu.qc.seclass.glm.R;

public class GroceryListsViewHolder {

    public TextView name;

    public GroceryListsViewHolder(View view) {
        this.name = view.findViewById(R.id.list_name);
    }

}
