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

    public String getDateAdded(){
        return dateAdded;

    }

       public String getData() {
        String data;
        data = "Barcode ID:" + this.barcodeId + " Project End Date: " + expiryDate;
        return data;
    }


    public String getName() {
        return name;
    }

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



}
