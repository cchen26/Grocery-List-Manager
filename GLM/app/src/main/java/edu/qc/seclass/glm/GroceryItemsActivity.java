package edu.qc.seclass.glm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import edu.qc.seclass.glm.adapters.GroceryItemsAdapter;
import edu.qc.seclass.glm.dao.GroceryItemDao;
import edu.qc.seclass.glm.entities.GroceryItem;
import edu.qc.seclass.glm.entities.GroceryItemType;

import java.util.Locale;

public class GroceryItemsActivity extends AppCompatActivity {

    long groceryListId;
    GroceryItemDao groceryItemDao;
    EditText searchBar;
    ListView groceryItemsView;
    GroceryItemsAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        groceryListId = getIntent().getLongExtra("id", 0);

        setContentView(R.layout.grocery_items);
        setTitle(getIntent().getStringExtra("name"));

        groceryItemDao = GroceryListDatabase.getInstance(this).getGroceryItemDao();
        searchBar = findViewById(R.id.search_bar);
        groceryItemsView = findViewById(R.id.list);
        adapter = new GroceryItemsAdapter(this, groceryItemDao.getGroceryItems(groceryListId));

        groceryItemsView.setAdapter(adapter);
        setListeners();

    }

    private void setListeners() {
        setEditTextChangedListener();
        setAddGroceryItemListener();
        setGroceryItemLongClickListener();
    }

    private void setEditTextChangedListener() {

        searchBar.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

        });

    }

    private void setAddGroceryItemListener() {

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
                            else if(adapter.contains(name)){

                                Toast.makeText(GroceryItemsActivity.this, "Item already exists!", Toast.LENGTH_LONG).show();
                                return;

                            }

                            GroceryItem groceryItem = new GroceryItem(groceryListId, name, GroceryItemType.NONE);
                            groceryItem.id = groceryItemDao.addGroceryItem(groceryItem);

                            adapter.getGroceryItems().add(groceryItem);
                            adapter.notifyDataSetChanged();

                            dialog.dismiss();

                        });

            });

            dialog.show();

        });

    }

    private void setGroceryItemLongClickListener() {

        final String[] menu = new String[]{"Edit", "Delete"};

        groceryItemsView.setOnItemLongClickListener((adapterView, view, groceryItemIndex, l) -> {

            GroceryItem groceryItem = adapter.getGroceryItems().get(groceryItemIndex);
            AlertDialog dialog = new AlertDialog.Builder(GroceryItemsActivity.this)
                    .setItems(menu, (di1, menuIndex) -> {

                        String selection = menu[menuIndex];

                        switch (selection) {

                            case "Edit": {

                                final String[] editMenu = new String[]{"Rename", "Change category", "Change amount"};

                                AlertDialog editMenuDialog = new AlertDialog.Builder(GroceryItemsActivity.this)
                                        .setItems(editMenu, (d, editMenuIndex) -> editMenuListener(editMenu, editMenuIndex, groceryItem))
                                        .create();

                                editMenuDialog.show();

                                break;

                            }
                            case "Delete":
                            default: {

                                adapter.getGroceryItems().remove(groceryItem);
                                adapter.notifyDataSetChanged();
                                groceryItemDao.deleteGroceryItem(groceryItem);

                                break;

                            }

                        }

                    })
                    .create();

            dialog.show();

            return true;

        });

    }

    private void editMenuListener(String[] menu, int i, GroceryItem groceryItem) {

        String selection = menu[i];

        switch (selection) {

            case "Rename": {

                EditText inputField = new EditText(this);

                inputField.setText(groceryItem.name);
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

                                groceryItem.name = name;

                                adapter.notifyDataSetChanged();
                                groceryItemDao.updateGroceryItem(groceryItem);
                                renameDialog.dismiss();

                            });

                });

                renameDialog.show();

                break;

            }
            case "Change category": {

                GroceryItemType[] itemTypes = GroceryItemType.values();
                String[] itemTypeNames = new String[itemTypes.length];

                for (int n = 0; n < itemTypes.length; n++)
                    itemTypeNames[n] = itemTypes[n].getName();

                AlertDialog categoryDialog = new AlertDialog.Builder(GroceryItemsActivity.this)
                        .setItems(itemTypeNames, ((dialogInterface, itemTypesIndex) -> {

                            groceryItem.itemType = itemTypes[itemTypesIndex];

                            groceryItemDao.updateGroceryItem(groceryItem);
                            adapter.notifyDataSetChanged();

                        }))
                        .create();

                categoryDialog.show();

                break;

            }
            case "Change amount": {

                EditText inputField = new EditText(GroceryItemsActivity.this);

                inputField.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
                inputField.setText(String.format(Locale.US, "%d", groceryItem.amount));
                inputField.setSelectAllOnFocus(true);

                AlertDialog amountDialog = new AlertDialog.Builder(GroceryItemsActivity.this)
                        .setMessage("Enter an amount")
                        .setView(inputField)
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Ok", null)
                        .create();

                amountDialog.setOnShowListener(dialogInterface -> {

                    amountDialog.getButton(Dialog.BUTTON_POSITIVE)
                            .setOnClickListener((button) -> {

                                String amount = inputField.getText().toString();

                                if (amount.isEmpty())
                                    return;

                                groceryItem.amount = Integer.parseInt(amount);

                                adapter.notifyDataSetChanged();
                                groceryItemDao.updateGroceryItem(groceryItem);
                                amountDialog.dismiss();

                            });

                });

                amountDialog.show();


            }

        }

    }

}
