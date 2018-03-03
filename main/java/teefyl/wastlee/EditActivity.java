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
import android.widget.Toast;
import android.widget.TextView;



public class EditActivity extends AppCompatActivity {
    private EditText equipmentName;
    private EditText expiry;
    Button editButton;
    String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();

        ID = getIntent().getStringExtra("barcode");
        FoodItem item = MainActivity.manager.getEquipmentItem(ID);
        equipmentName =(EditText) findViewById(R.id.editFoodName);
        equipmentName.setText(item.getName(),TextView.BufferType.EDITABLE);
        expiry =(EditText) findViewById(R.id.editExpiryDate);
        expiry.setText(item.getExpiryDate(),TextView.BufferType.EDITABLE);
        editButton = (Button)findViewById(R.id.editFood);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(equipmentName.getText()==null){
                    Toast.makeText(EditActivity.this,"NOPE",Toast.LENGTH_LONG).show();
                }
                String expiryString = expiry.getText().toString();
                String nameString = equipmentName.getText().toString();
                //test item to add
                ID = getIntent().getStringExtra("barcode");

                FoodItem e = new FoodItem(nameString,ID,expiryString);
                MainActivity.manager.editEquipment(e);
                Intent i = new Intent(EditActivity.this, FoodListActivity.class);
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
