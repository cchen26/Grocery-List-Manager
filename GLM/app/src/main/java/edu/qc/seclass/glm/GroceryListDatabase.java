package edu.qc.seclass.glm;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import edu.qc.seclass.glm.dao.GroceryItemDao;
import edu.qc.seclass.glm.dao.GroceryListDao;
import edu.qc.seclass.glm.entities.GroceryItem;
import edu.qc.seclass.glm.entities.GroceryList;

@Database(version = 1, entities = {GroceryList.class, GroceryItem.class})
public abstract class GroceryListDatabase extends RoomDatabase {

    private static volatile GroceryListDatabase INSTANCE;

    public abstract GroceryListDao getGroceryListDao();

    public abstract GroceryItemDao getGroceryItemDao();

    public static GroceryListDatabase getInstance(Context context) {

        if (INSTANCE == null)
            INSTANCE = Room
                    .databaseBuilder(context.getApplicationContext(), GroceryListDatabase.class, Constants.DB_NAME)
                    .allowMainThreadQueries()
                    .build();

        return INSTANCE;

    }

}
