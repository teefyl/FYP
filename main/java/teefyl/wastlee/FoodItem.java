package teefyl.wastlee;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Laura on 27/01/2018.
 */

public class FoodItem extends Service {
    private String name;
    private String barcodeId;
    private String expiryDate;
    private String reminderDate;
    private String dateAdded;
    //private String category;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    FoodItem(String name, String barcodeContents, String expiry, String reminder, String date ){//String foodCategory){
        barcodeId = barcodeContents;
        expiryDate = expiry;
        this.name=name;
        reminderDate = reminder;
        dateAdded = date;

        // category = foodCategory;
    }

    public String getDateAdded(){return dateAdded;}
    public String getName() {return name;}
    public String getExpiryDate() {
        return expiryDate;
    }
    public String getReminderDate() {
        return reminderDate;
    }
    public String getBarcodeID() {
        return barcodeId;
    }


    // public String getCategory() {return category;}
    public String toString() {
        return "Barcode ID: " + this.barcodeId + "\n" + "Food Name: " + name + "\n" +"Expiry Date: " + expiryDate + "\n";
    }

    public String toStringExtended(){
        return "Barcode ID: " + this.barcodeId + "\n" + "Food Name: " + name + "\n" +"Expiry Date: " + expiryDate + "\n"
                +"Date Added: " + dateAdded + "\n" +"Reminder Date: " + addDates(dateAdded, Integer.parseInt(reminderDate));

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
        if(sdf.format(c.getTime())!=null)
             newDate = sdf.format(c.getTime());
        System.out.println("THIS IS THE DATE YOU WANT TO BE REMINDED ON RIGHT "+newDate);

        return newDate;
    }



}
