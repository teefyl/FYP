package teefyl.wastlee;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.NotificationCompat;
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
    static Food_Notifications notifications;
    //private Button btn;
    @RequiresApi(26)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Activity activity = this;
        manager = new DBManager(this,null,null,1);
        Date date = new Date();
        String todaysdate= new SimpleDateFormat("dd/MM/yyyy").format(date);
        Food_Notifications notifications = new Food_Notifications(todaysdate, this);
        String[] foods = new String[1];
                if (notifications.getTodaysFoods(notifications).length !=0) {
                    foods = notifications.getTodaysFoods(notifications);
                    notifications.send_todays_notifs(foods);
                }
                else
                    foods[0] = "No foods expire today yay!";
        System.out.println("TODAYS FIRST FOOD BRUH"+ foods[0]);

        buttonScan = (Button) findViewById(R.id.buttonScan);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //btn = (Button)findViewById(R.id.button1);
        //DELETED NOTIFICATION CODE HERE

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
                    FoodItem item = manager.getEquipmentItem(result.getContents());
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
