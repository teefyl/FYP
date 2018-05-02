package teefyl.wastlee;

import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.content.*;
import android.widget.Button;
import android.widget.Toast;
import android.app.Activity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Button buttonScan;
    static DBManager manager;

    @RequiresApi(26)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        manager = new DBManager(this,null);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Activity activity = this;


        Date date = new Date();
        String todaysdate= new SimpleDateFormat("dd/MM/yyyy").format(date);
        Food_Notifications notifications = new Food_Notifications(todaysdate, this);
        String[] foods = notifications.getTodaysFoods(notifications);


        if (foods.length !=0) {
            foods = notifications.getTodaysFoods(notifications);
            notifications.send_todays_notifs(foods);
        }
        foods=notifications.getTodaysReminders(notifications);
        if (foods!=null) {
            foods = notifications.getTodaysReminders(notifications);
            notifications.send_todays_reminders(foods);
        }

        buttonScan = (Button) findViewById(R.id.buttonScan);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            //simple parameters for the viewing, post button press
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     *Method to process the data for whenever a code has been scanned/not found
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {//as long as code is found or test in cancelled
            if (result.getContents() == null) {
                //user has exited the scanner and exit code will be printed to screen
                Toast.makeText(this, "Scan cancelled", Toast.LENGTH_LONG).show();
            } else {
                //if not already in DB, create item and add it
                String bID=""; //barcode ID
                if(manager.checkBarcode(result.getContents())==null){
                    Intent i = new Intent(MainActivity.this,FormActivity.class);
                    i.putExtra("bID", result.getContents());
                    startActivity(i);
                }
                //if in the DB
                else{
                    FoodItem item = manager.getFoodItem(result.getContents());
                    String s= item.toString();
                    Intent i = new Intent(MainActivity.this,ViewActivity.class);
                    i.putExtra("item", s);
                    startActivity(i);
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_food) {
            Intent intent = new Intent(MainActivity.this, FoodListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_maps) {
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_tip) {
            Intent intent = new Intent(MainActivity.this, TipOfTheDay.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_articles) {
            Intent intent = new Intent(MainActivity.this, ArticlesActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
