package teefyl.wastlee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;

import static teefyl.wastlee.R.string.view;


public class SettingsActivity extends AppCompatActivity {
    private TextView switchStatus;
    private Switch mySwitch;
    private Button mapsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        mapsButton = (Button) findViewById(R.id.mapsButton);
        mapsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonActivity(v);
            }
        });
        switchStatus = (TextView) findViewById(R.id.switch1);
        mySwitch = (Switch) findViewById(R.id.switch1);
        //set the switch to ON
        mySwitch.setChecked(true);
        //attach a listener to check for changes in state
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    switchStatus.setText("Barcode");
                }else{
                    switchStatus.setText("QR");
                }

            }
        });


        //check the current state before we display the screen
        if(mySwitch.isChecked()){
            switchStatus.setText("Barcode");
        }
        else {
            switchStatus.setText("QR");
        }
    }

    public void buttonActivity(View v) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivityForResult(intent,1);
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
