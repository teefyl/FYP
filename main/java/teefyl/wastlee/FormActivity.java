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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FormActivity extends AppCompatActivity {
    private EditText equipmentName;
    private EditText expiry;
    private EditText reminder;
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
        reminder= (EditText) findViewById(R.id.reminderDate);
        createButton = (Button)findViewById(R.id.addFood);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String equipmentString = equipmentName.getText().toString();
                String expiryString = expiry.getText().toString();
                String reminderString = reminder.getText().toString();
                //String containing barcode name passed from scanner activity
                String barcodeID = getIntent().getStringExtra("bID");
                Date date = new Date();
                String dateAdded = new SimpleDateFormat("dd/MM/yyyy").format(date);
                //test item to add
                FoodItem e = new FoodItem(equipmentString,barcodeID, expiryString, reminderString, dateAdded);
                int daysToAdd = Integer.parseInt(reminderString);
                String dateNew = addDates(dateAdded, daysToAdd);
                MainActivity.manager.addEquipment(e);
                Toast.makeText(getBaseContext(), "You will be reminded about this on "+dateNew , Toast.LENGTH_SHORT ).show();

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

    public String addDates(String date, int daysToAdd)
    {
        String newDate = "00/00/0000";
        String[] dmy = date.split("[/]");
        //dmy[0] = day dmy[1]=month dmy[2]= year
        java.util.Date utilDate = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            utilDate = formatter.parse(date);
            System.out.println("utilDate:" + utilDate);
        }catch(ParseException e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(utilDate); // Now use today date.
        c.add(Calendar.DATE, daysToAdd); // Adding days
        newDate = sdf.format(c.getTime());
        System.out.println("THIS IS THE DATE YOU WANT TO BE REMINDED ON RIGHT "+newDate);

        return newDate;
    }
}
