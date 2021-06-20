package edu.qc.seclass.glm.dao;

import androidx.room.*;

import edu.qc.seclass.glm.entities.GroceryList;

import java.util.List;

@Dao
public abstract class GroceryListDao {

    @Transaction
    @Query("SELECT * FROM GROCERYLISTS")
    public abstract List<GroceryList> getGroceryLists();

    @Insert
    public abstract long addGroceryList(GroceryList groceryList);

    @Update
    public abstract void updateGroceryList(GroceryList groceryList);

    @Delete
    public abstract void deleteGroceryList(GroceryList groceryList);

}
