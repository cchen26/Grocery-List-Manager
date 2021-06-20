
package edu.qc.seclass.glm.dao;

import androidx.room.*;

import edu.qc.seclass.glm.entities.GroceryItem;

import java.util.List;

@Dao
public abstract class GroceryItemDao {

    @Query("SELECT * FROM GROCERYITEMS " +
            "WHERE GROCERYLISTID = :groceryListId")
    public abstract List<GroceryItem> getGroceryItems(long groceryListId);

    @Insert
    public abstract long addGroceryItem(GroceryItem groceryItem);

    @Update
    public abstract void updateGroceryItem(GroceryItem groceryItem);

    @Delete
    public abstract void deleteGroceryItem(GroceryItem groceryItem);

}
