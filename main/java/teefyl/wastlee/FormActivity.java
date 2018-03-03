package teefyl.wastlee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FormActivity extends AppCompatActivity {
    private EditText equipmentName;
    private EditText expiry;
    Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        equipmentName =(EditText) findViewById(R.id.foodText);
        expiry =(EditText) findViewById(R.id.expiryDate);
        createButton = (Button)findViewById(R.id.addFood);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String equipmentString = equipmentName.getText().toString();
                String expiryString = expiry.getText().toString();
                //String containing barcode name passed from scanner activity
                String barcodeID = getIntent().getStringExtra("bID");
                //test item to add
                FoodItem e = new FoodItem(equipmentString,barcodeID,expiryString);

                MainActivity.manager.addEquipment(e);
                Intent i = new Intent(FormActivity.this,FoodListActivity.class);
                i.putExtra("barcode",e.getBarcodeID());
                startActivity(i);
            }

        });
    }



    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
