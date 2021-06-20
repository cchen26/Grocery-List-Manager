package edu.qc.seclass.glm;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import edu.qc.seclass.glm.adapters.GroceryListsAdapter;
import edu.qc.seclass.glm.dao.GroceryListDao;
import edu.qc.seclass.glm.entities.GroceryList;

import java.io.File;

public class GroceryListsActivity extends AppCompatActivity {

    GroceryListDao groceryListDao;
    ListView groceryListView;
    GroceryListsAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.grocery_list);

        groceryListView = findViewById(R.id.list);

        setListeners();

    }

    @Override
    protected void onResume() {

        super.onResume();

        groceryListDao = GroceryListDatabase.getInstance(this).getGroceryListDao();

        if (adapter == null) {
            adapter = new GroceryListsAdapter(this, groceryListDao.getGroceryLists());
        } else
            adapter.setGroceryLists(groceryListDao.getGroceryLists());

        groceryListView.setAdapter(adapter);

    }

    public void setListeners() {
        setAddGroceryListListener();
        setGroceryListItemClickedListener();
        setGroceryListLongClickListener();
    }

    public void setAddGroceryListListener() {

        findViewById(R.id.add_button).setOnClickListener(view -> {

            EditText inputField = new EditText(this);
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setMessage("Enter a name")
                    .setView(inputField)
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Ok", null)
                    .create();

            dialog.setOnShowListener((dialogInterface) -> {

                dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        .setOnClickListener((button) -> {

                            String name = inputField.getText().toString();

                            if (name.isEmpty())
                                return;

                            GroceryList groceryList = new GroceryList(name);
                            groceryList.id = groceryListDao.addGroceryList(groceryList);

                            adapter.getGroceryLists().add(groceryList);
                            adapter.notifyDataSetChanged();

                            dialog.dismiss();

                        });

            });

            dialog.show();

        });

    }

    private void setGroceryListLongClickListener() {

        final String[] editMenu = new String[]{"Rename", "Delete"};

        groceryListView.setOnItemLongClickListener((adapterView, view, groceryListIndex, l) -> {

            GroceryList groceryList = adapter.getGroceryLists().get(groceryListIndex);
            AlertDialog dialog = new AlertDialog.Builder(GroceryListsActivity.this)
                    .setItems(editMenu, (di1, menuIndex) -> {

                        String selection = editMenu[menuIndex];

                        switch (selection) {

                            case "Rename": {

                                EditText inputField = new EditText(this);

                                inputField.setText(groceryList.name);
                                inputField.setSelectAllOnFocus(true);

                                AlertDialog renameDialog = new AlertDialog.Builder(this)
                                        .setMessage("Enter a name")
                                        .setView(inputField)
                                        .setNegativeButton("Cancel", null)
                                        .setPositiveButton("Ok", null)
                                        .create();

                                renameDialog.setOnShowListener((di2) -> {

                                    renameDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                                            .setOnClickListener((button) -> {

                                                String name = inputField.getText().toString();

                                                if (name.isEmpty())
                                                    return;

                                                groceryList.name = name;

                                                adapter.notifyDataSetChanged();
                                                groceryListDao.updateGroceryList(groceryList);
                                                renameDialog.dismiss();

                                            });

                                });

                                renameDialog.show();

                                break;

                            }
                            case "Delete":
                            default: {

                                adapter.getGroceryLists().remove(groceryList);
                                adapter.notifyDataSetChanged();
                                groceryListDao.deleteGroceryList(groceryList);

                            }

                        }

                    })
                    .create();

            dialog.show();

            return true;

        });

    }

    private void setGroceryListItemClickedListener() {

        groceryListView.setOnItemClickListener((parent, view, position, id) -> {

            GroceryList groceryList = (GroceryList) adapter.getItem(position);
            Intent intent = new Intent(this, GroceryItemsActivity.class);

            intent.putExtra("id", groceryList.id);
            intent.putExtra("name", groceryList.name);

            startActivity(intent);

        });

    }

    private void deleteDatabase() {

        File dbFile = this.getDatabasePath(Constants.DB_NAME);
        dbFile.delete();

    }
}
