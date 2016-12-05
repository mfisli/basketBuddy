package com.example.fisli.basketbuddy;

import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

public class ItemChecklist extends AppCompatActivity {
    private static final String TAG = ItemChecklist.class.getName();
    private DBOpenHelper dbOpenHelper;
    private List<String> listValues;
    private String currentTrip;
    private String currentStore = "New Store";
    private int checkBoxCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,">>> Start of onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_checklist);
        setCurrentTripAndStore();
        populateCheckList();
        Log.d(TAG,"End of onCreate <<<");
    }
    void setCurrentTripAndStore(){
        Intent intent = getIntent();
        String currentTrip = intent.getStringExtra("currentTrip");
        String currentStore = intent.getStringExtra("selectedStore");
        this.currentTrip = currentTrip;
        this.currentStore = currentStore;
        TextView storeTitle   = (TextView)findViewById(R.id.storeTitle);
        storeTitle.setText(currentStore);
    }
    void populateCheckList(){
        Log.d(TAG,">>> Start of populateCheckList");
        dbOpenHelper = DBOpenHelper.getInstance(getApplicationContext());
        listValues = dbOpenHelper.getItem(currentStore);
        Log.d(TAG, "listValues: " + listValues);
        if (listValues != null) {
            int checkBoxes = listValues.size();
            Log.d(TAG, "Checklist count: " + checkBoxes);
            LinearLayout checkboxLayout = (LinearLayout)findViewById(R.id.checkboxLayout);
            for (int i = 0; i < checkBoxes; i++) {
                //dbOpenHelper = DBOpenHelper.getInstance(getApplicationContext());
                TableRow rowCheckBox = new TableRow(this);
                TableRow rowInfo = new TableRow(this);
                rowCheckBox.setId(i);
                rowCheckBox.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                CheckBox checkBox = new CheckBox(this);
                TextView textView = new TextView(this);
                textView.setText("Quantity: " + dbOpenHelper.getItemQuantity(currentStore,listValues.get(i))
                + " Aisle: " + dbOpenHelper.getItemAisle(currentStore,listValues.get(i)));
                textView.setTextSize(18);
                rowInfo.addView(textView);
                //checkBox.setOnCheckedChangeListener(this);
                checkBox.setId(i);
                checkBox.setTextSize(22);
                checkBox.setChecked(dbOpenHelper.getChecked(currentStore,listValues.get(i)));
                Log.d(TAG, "Setting CheckBox id: " + i);
                checkBox.setText(listValues.get(i));
                rowCheckBox.addView(checkBox);
                checkboxLayout.addView(rowCheckBox);
                checkboxLayout.addView(rowInfo);
                this.checkBoxCount = checkBoxes;
            }
        }
        Log.d(TAG,"End of populateCheckList <<<");
    }
    public void backToTripMenu(View v){
        Log.d(TAG,">> Start of backToTripMenu");
        checkCheckBoxes();
        Intent intent = new Intent(ItemChecklist.this, TripMenu.class);
        intent.putExtra("selectedStore", currentStore);
        intent.putExtra("currentTrip", currentTrip);
        Log.d(TAG,"Going to ItemChecklist with establishedStore:" +
                currentStore + " in trip " + currentTrip);
        ItemChecklist.this.startActivity(intent);
        Log.d(TAG,"<<<End of backToTripMenu");
    }
    public void checkCheckBoxes(){
        LinearLayout checkBoxLayout = (LinearLayout)findViewById(R.id.checkboxLayout);
        Log.d(TAG,">> Start of checkCheckBoxes counting: " + checkBoxLayout.getChildCount());
        final SQLiteDatabase db;
        dbOpenHelper = DBOpenHelper.getInstance(getApplicationContext());
        db = dbOpenHelper.getWritableDatabase();

        for(int i=0; i < checkBoxLayout.getChildCount(); i++) {
            TableRow nextChildTable = (TableRow)checkBoxLayout.getChildAt(i);
            View nextChild = nextChildTable.getChildAt(0);
            //Log.d(TAG, "Considering: " + nextChild);
            if(nextChild instanceof CheckBox ) {
                //Log.d(TAG, nextChild + " is a checkbox");
                CheckBox check = (CheckBox) nextChild;
                String checkBoxItem = check.getText().toString();
                if (check.isChecked()) {
                    Log.d(TAG, "Checked: " + checkBoxItem);
                    dbOpenHelper.updateCheckBox(db, currentStore, checkBoxItem, 1);
                    //(final SQLiteDatabase db, String store, String itemname, int mode)
                } else {
                    Log.d(TAG, "UnChecked: " + checkBoxItem);
                    dbOpenHelper.updateCheckBox(db, currentStore, checkBoxItem, 0);
                }
            }
        }
        Log.d(TAG,"End of checkCheckBoxes <<<");
    }

    public void deleteItems(View v){
        final SQLiteDatabase db;
        dbOpenHelper = DBOpenHelper.getInstance(getApplicationContext());
        db = dbOpenHelper.getWritableDatabase();
        dbOpenHelper.deleteItemName(db,currentStore);
        Intent intent = new Intent(ItemChecklist.this, TripMenu.class);
        intent.putExtra("selectedStore", currentStore);
        intent.putExtra("currentTrip", currentTrip);
        Log.d(TAG,"Going to ItemChecklist with establishedStore:" +
                currentStore + " in trip " + currentTrip);
        ItemChecklist.this.startActivity(intent);
    }



    public void startStoreEdit(View v) {
        Intent myIntent = new Intent(ItemChecklist.this, EditStore.class);
        ItemChecklist.this.startActivity(myIntent);
    }
}

