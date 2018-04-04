package teefyl.wastlee;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
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


public class FoodListActivity extends AppCompatActivity {
    FoodItem[] items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();


        //Tells the app what to show when Food list is clicked in the navbar
        items = MainActivity.manager.searchItems("");
        if(items==null || items.length==0){
            Toast.makeText(this,"Database contains no food",Toast.LENGTH_LONG).show();
            Intent i = new Intent(FoodListActivity.this,MainActivity.class);
            startActivity(i);
        }else{
            String[] list = new String[items.length];
            for(int i=0;i<items.length;i++){
                list[i]=items[i].getName() +"    " + items[i].getExpiryDate();
            }
            ListView lv = (ListView)findViewById(R.id.foodList);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.food_list_item, R.id.txtFood,list);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new ItemList());
        }
    }

    class ItemList implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent,View view, int position,long id){
            ViewGroup vg = (ViewGroup) view;
            TextView tv = vg.findViewById(R.id.txtFood);

            String s="";
            for(int i=0;i<items.length;i++){
                if(tv.getText().toString().equals(items[i].getName()+"    " + items[i].getExpiryDate())){
                s=items[i].toString();
                }
            }
            Intent intentional = new Intent(FoodListActivity.this, ViewActivity.class);
            intentional.putExtra("item",s);
            startActivity(intentional);
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

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

}
