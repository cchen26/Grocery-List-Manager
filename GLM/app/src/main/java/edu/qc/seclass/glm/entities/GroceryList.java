package edu.qc.seclass.glm.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "GroceryLists")
public class GroceryList {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;

    public GroceryList(String name) {
        this.name = name;
    }

}
