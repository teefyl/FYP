package teefyl.wastlee;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import static android.R.attr.path;


/**
 * Created by Laura on 27/01/2018.
 */

public class FoodItem {
    private String name;
    private String barcodeId;
    private String expiryDate;
    //private String category;

    FoodItem(String name, String barcodeContents, String expiry ){//String foodCategory){
        barcodeId = barcodeContents;
        expiryDate = expiry;
        this.name=name;
        // category = foodCategory;
    }

       public String getData() {
        String data;
        data = "Barcode ID:" + this.barcodeId + " Project End Date: " + expiryDate;
        return data;
    }


    public String getName() {
        MyTask gettingUrls = new MyTask();
        gettingUrls.execute();
        return name;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getBarcodeID() {
        return barcodeId;
    }

    // public String getCategory() {return category;}
    public String toString() {
        return "Barcode ID: " + this.barcodeId + "\n" + "Food Name: " + name + "\n" +"Expiry Date: " + expiryDate + "\n";
    }

    private class MyTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String title = "";
            String google = "http://www.google.com/search?q=";
            String search = getBarcodeID(); //your word to be search on google
            String userAgent = "ExampleBot 1.0 (+http://example.com/bot)";
            String charset = "UTF-8";
            Elements links = null;
            Elements link1 = null;
            try {
                links = Jsoup.connect(google +
                        URLEncoder.encode(search, charset)).
                        userAgent(userAgent).get().select(".g>.r>a");
                link1 = links.eq(1);
            } catch (UnsupportedEncodingException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            for (Element link : link1) {
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
                System.out.println("Food name: " + title);
                //new PrintStream(new BufferedOutputStream(new FileOutputStream("urlnames.txt")), true);
            }
            return title;
        }


    }

}
