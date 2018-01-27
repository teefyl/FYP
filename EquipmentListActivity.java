package com.example.kevin.design;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EquipmentListActivity extends AppCompatActivity {
    EquipmentItem[] items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        items = MainActivity.manager.searchItems("");
        if(items==null || items.length==0){
            Toast.makeText(this,"Database contains no food",Toast.LENGTH_LONG).show();
            Intent i = new Intent(EquipmentListActivity.this,MainActivity.class);
            startActivity(i);
        }else{
            String[] list = new String[items.length];
            for(int i=0;i<items.length;i++){
                list[i]=items[i].getName() +"    " + items[i].getExpiryDate();
            }
            ListView lv = (ListView)findViewById(R.id.equipmentList);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.equip_list_item, R.id.txtEquip,list);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new ItemList());
        }
    }

    class ItemList implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent,View view, int position,long id){
            ViewGroup vg = (ViewGroup) view;
            TextView tv = (TextView) vg.findViewById(R.id.txtEquip);

            String s="FOOD ITEM TEXT ";
            for(int i=0;i<items.length;i++){
                //if(tv.getText().toString().equals(items[i].getBarcodeID())){
                    s=items[i].toString();
                //}
            }
            Intent i = new Intent(EquipmentListActivity.this,viewActivityEquip.class);
            i.putExtra("item",s);
            startActivity(i);
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
