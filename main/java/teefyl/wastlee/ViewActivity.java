package teefyl.wastlee;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class ViewActivity extends AppCompatActivity {
    Button edit;
    Button delete;
    String stringDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();

        TextView tv = (TextView) findViewById(R.id.viewFoodText);
        String output = getIntent().getStringExtra("item");
        stringDelete = output;
        tv.setText(output);

        edit = (Button) findViewById(R.id.equipEdit);
        delete = (Button) findViewById(R.id.deleteFood);
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
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra("barcode",result);
            startActivity(intent);
        }
        else if(v == delete){
            String result = stringDelete.substring(stringDelete.indexOf(":") + 2, stringDelete.indexOf('\n'));
            MainActivity.manager.deleteEquipmentItem(result);
            Intent intent = new Intent(this, FoodListActivity.class);
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

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

}