package teefyl.wastlee;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;


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
        equipmentName.setText(getNameOnline(ID),TextView.BufferType.EDITABLE);
        expiry =(EditText) findViewById(R.id.editExpiryDate);
        expiry.setText(item.getExpiryDate(),TextView.BufferType.EDITABLE);
        editButton = (Button)findViewById(R.id.editFood);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(equipmentName.getText()==null){
                    Toast.makeText(EditActivity.this,"NOPE",Toast.LENGTH_LONG).show();
                }
                String equipmentString = equipmentName.getText().toString();
                String expiryString = expiry.getText().toString();
                String reminderString = getIntent().getStringExtra("reminder_date");
                String dateAdded = getIntent().getStringExtra("date_added");
                ID = getIntent().getStringExtra("barcode");
                FoodItem e = new FoodItem(equipmentString, ID , expiryString, reminderString, dateAdded);


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

    private String getNameOnline(String barcodeId){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        String title = "";
        String google = "http://www.google.com/search?q=";
        String search = barcodeId; //your word to be searched on google
        String userAgent = "ExampleBot 1.0 (+http://example.com/bot)";
        String charset = "UTF-8";
        Elements links = null;

        try {
            links = Jsoup.connect(google +
                    URLEncoder.encode(search, charset)).
                    userAgent(userAgent).get().select(".g>.r>a");

        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        for (Element link : links) {
            title = link.text();
            String url = link.absUrl("href");
            try {
                url = URLDecoder.decode(url.substring(url.indexOf('=') +
                        1, url.indexOf('&')), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if (!url.startsWith("http")) {
                continue; // Ads/news/etc.
            }
            //System.out.println("Food name of food with barcode "+barcodeId+" : " + title);
        }
        return title;
    }
}
