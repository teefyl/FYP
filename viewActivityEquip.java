package com.example.kevin.design;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class viewActivityEquip extends AppCompatActivity {
    Button edit;
    Button delete;
    String stringDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_equip);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView tv = (TextView) findViewById(R.id.viewEquipText);
        String output = getIntent().getStringExtra("item");
        stringDelete = output;
        tv.setText(output);

        edit = (Button) findViewById(R.id.equipEdit);
        delete = (Button) findViewById(R.id.deleteEquip);
        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonActivity(v);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonActivity(v);
            }
        });

    }

    public void buttonActivity(View v) {
        if (v == edit) {
            String result = stringDelete.substring(stringDelete.indexOf(":") + 2, stringDelete.indexOf('\n'));
            Intent intent = new Intent(this, EditEquipActivity.class);
            intent.putExtra("barcode",result);
            startActivity(intent);
        }
        else if(v == delete){
            String result = stringDelete.substring(stringDelete.indexOf(":") + 2, stringDelete.indexOf('\n'));
            MainActivity.manager.deleteEquipmentItem(result);
            Intent intent = new Intent(this, EquipmentListActivity.class);
            startActivity(intent);
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
