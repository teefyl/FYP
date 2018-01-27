package com.example.kevin.design;

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



public class EditEquipActivity extends AppCompatActivity {
    private EditText equipmentName;
    private EditText expiry;
    Button editButton;
    String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_equip);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();

        ID = getIntent().getStringExtra("barcode");
        EquipmentItem item = MainActivity.manager.getEquipmentItem(ID);
        equipmentName =(EditText) findViewById(R.id.editEquipName);
        equipmentName.setText(item.getName(),TextView.BufferType.EDITABLE);
        expiry =(EditText) findViewById(R.id.editExpiryDate);
        expiry.setText(item.getExpiryDate(),TextView.BufferType.EDITABLE);
        editButton = (Button)findViewById(R.id.editEquipment);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(equipmentName.getText()==null){
                    Toast.makeText(EditEquipActivity.this,"NOPE",Toast.LENGTH_LONG).show();
                }
                String expiryString = expiry.getText().toString();
                String nameString = equipmentName.getText().toString();
                //test item to add
                ID = getIntent().getStringExtra("barcode");

                EquipmentItem e = new EquipmentItem(nameString,ID,expiryString);
                MainActivity.manager.editEquipment(e);
                Intent i = new Intent(EditEquipActivity.this,EquipmentListActivity.class);
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