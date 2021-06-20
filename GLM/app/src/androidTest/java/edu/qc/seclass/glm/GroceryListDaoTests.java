package edu.qc.seclass.glm;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import edu.qc.seclass.glm.dao.GroceryListDao;
import edu.qc.seclass.glm.entities.GroceryList;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class GroceryListDaoTests {

    private GroceryListDatabase db;
    private GroceryListDao dao;

    @Before
    public void setUpDatabase() {

        Context context = ApplicationProvider.getApplicationContext();
        this.db = GroceryListDatabase.getInstance(context);
        this.dao = db.getGroceryListDao();

    }

    @After
    public void cleanUpDatabase() {
        db.close();
    }

    @Test
    public void insertGroceryList_ExpectSuccess() {
        GroceryList t1 = new GroceryList("test1");
        GroceryList t2 = new GroceryList("test2");
        GroceryList t3 = new GroceryList("test3");
        Exception exception = null;

        try {
            dao.addGroceryList(t1);
        } catch (Exception e) {
            exception = e;
        }
        assertNull(exception);


        try {
            dao.addGroceryList(t2);
        } catch (Exception e) {
            exception = e;
        }
        assertNull(exception);

        try {
            dao.addGroceryList(t3);
        } catch (Exception e) {
            exception = e;
        }
        assertNull(exception);


    }

    @Test
    public void queryGroceryList_ExpectSuccess() {
        List<GroceryList> grocerylists = dao.getGroceryLists();
        assertEquals(grocerylists.size(), 3);

    }

    @Test
    public void deleteGroceryList_ExpectSuccess() {
        List<GroceryList> grocerylists = dao.getGroceryLists();
        for (GroceryList grocerylist : grocerylists) {
            dao.deleteGroceryList(grocerylist);
        }
        grocerylists = dao.getGroceryLists();
        assertEquals(grocerylists.size(), 0);
    }


}