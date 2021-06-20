package edu.qc.seclass.glm.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import edu.qc.seclass.glm.entities.converters.Converters;

@Entity(
        tableName = "GroceryItems"
)
@TypeConverters({Converters.class})
public class GroceryItem {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public long groceryListId;

    public String name;
    public GroceryItemType itemType;
    public int amount;
    public boolean isChecked;

    public GroceryItem(long groceryListId, String name, GroceryItemType itemType) {
        this.groceryListId = groceryListId;
        this.name = name;
        this.itemType = itemType;
        this.amount = 1;
        this.isChecked = false;
    }

    public GroceryItem() {
    }

}
