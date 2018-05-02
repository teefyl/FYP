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
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FormActivity extends AppCompatActivity {
    private EditText foodName;
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
        foodName =(EditText) findViewById(R.id.foodText);
        expiry =(EditText) findViewById(R.id.expiryDate);
        reminder= (EditText) findViewById(R.id.reminderDate);
        createButton = (Button)findViewById(R.id.addFood);
        String getName = getNameOnline(getIntent().getStringExtra("bID"));
        String getNameFiltered = stringFilter(getName);
        foodName.setText(getNameFiltered, TextView.BufferType.EDITABLE);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String foodString = foodName.getText().toString();
                String expiryString = expiry.getText().toString();
                String reminderString = reminder.getText().toString();

                //String containing barcode name passed from scanner activity
                String barcodeID = getIntent().getStringExtra("bID");

                Date date = new Date();
                String todaysdate= new SimpleDateFormat("dd/MM/yyyy").format(date);
                //test item to add
                FoodItem e = new FoodItem(foodString,barcodeID, expiryString, reminderString, todaysdate);

                int daysToAdd = Integer.parseInt(reminderString);
                String dateNew = addDates(todaysdate, daysToAdd);
                MainActivity.manager.addFood(e);
                Toast.makeText(getBaseContext(), "You will be reminded about this on "+dateNew , Toast.LENGTH_SHORT ).show();

                Intent i = new Intent(FormActivity.this,FoodListActivity.class);
                i.putExtra("barcode",e.getBarcodeID());
                startActivity(i);
            }

        });

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
        }

        return title;
    }

    private String stringFilter(String input)
    {
        String filteredString = input;
        if(input.contains("Tesco"))
            filteredString= filteredString.replace("Tesco","");
        if(input.contains("tesco"))
            filteredString= filteredString.replace("tesco","");
        if(input.contains("eBay"))
            filteredString= filteredString.replace("eBay","");
        if(input.contains("CODECHECK.INFO"))
            filteredString= filteredString.replace("CODECHECK.INFO","");
        filteredString = filteredString.replaceAll("[^\\w\\s]", "");
        // Filtering Out Special Symbols
        filteredString = filteredString.replaceAll("\\d+(?:[.,]\\d+)*\\s*", "");
        // Filtering Out Numbers

        return filteredString;
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
                Intent intent = new Intent(FormActivity.this, MainActivity.class);
                startActivity(intent);
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
