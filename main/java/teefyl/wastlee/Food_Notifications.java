package teefyl.wastlee;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.graphics.Color;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.content.*;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by Laura on 01/03/2018.
 */

public class Food_Notifications extends AppCompatActivity{

    private String today;
    private Context mContext;

    public Food_Notifications(String todaysdate, Context mContext){
        today = todaysdate;
        this.mContext = mContext;
    }

    public String[] getTodaysReminders(Food_Notifications notif)
    {
        FoodItem[] items;
        String[] list;
        items = MainActivity.manager.searchItems("");
        if (items == null || items.length == 0) {
            list = null;
        } else {
            list = new String[items.length];
            for (int i = 0; i < items.length; i++) {
                list[i] = items[i].getName() + "*" + items[i].getDateAdded()+"$"+ items[i].getReminderDate();
            }
        }


        String[] todaysfoods = new String[0];
        int howmany = 0;
        //CHECK IF ANY OF THE FOODS HAVE TODAY's REMINDER DATE
        if (list != null) {
            List<String> foods = new ArrayList();
            for (int i = 0; i < list.length; i++) {
                if (list[i].contains(notif.today)) { //Need to change this if statement

                    String[] parts = list[i].split("[*]");
                    String[] dates = parts[1].split("[$]");
                    //dates[0] = date the food was added
                    //dates[1] = the amount of days to wait after then to be notified
                    String calcDate = addDates(dates[0], Integer.parseInt(dates[1]));
                    if(calcDate==notif.today)
                    {
                        howmany++;
                        foods.add(parts[0]);
                    }
                }

            }
            todaysfoods = new String[howmany];
            for(int j=0; howmany>0; howmany--)
            {
                todaysfoods[j]=foods.get(j);
                j++;
            }
        }
        return todaysfoods;
    }

    public String[] getTodaysFoods(Food_Notifications notif) {

        FoodItem[] items;
        String[] list;
        items = MainActivity.manager.searchItems("");
        if (items == null || items.length == 0) {
            list = null;
        } else {
            list = new String[items.length];
            for (int i = 0; i < items.length; i++) {
                list[i] = items[i].getName() + "*" + items[i].getExpiryDate();
            }
        }


        String[] todaysfoods = new String[0];
        int howmany = 0;
        //CHECK IF ANY OF THE FOODS HAVE TODAY's EXPIRY DATE
        if (list != null) {
            List<String> foods = new ArrayList();
            for (int i = 0; i < list.length; i++) {
                if (list[i].contains(notif.today)) {
                    howmany++;
                    String[] parts = list[i].split("[*]");
                    foods.add(parts[0]);
                }
            }
            todaysfoods = new String[howmany];
            for(int j=0; howmany>0; howmany--)
            {
                todaysfoods[j]=foods.get(j);
                j++;
            }
        }

        return todaysfoods;
    }

    @RequiresApi(26)
    public void send_todays_notifs(String[] todaysfoods){
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        String id1 = "channel_01";
        CharSequence name = "CHANNEL ONE";
        String description = "NOTIFICATIONS";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel(id1, name, importance);
        mChannel.setDescription(description);
        mChannel.enableLights(true);
        mChannel.setLightColor(Color.RED);
        mChannel.enableVibration(true);
        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        notificationManager.createNotificationChannel(mChannel);

        String MyNotificationText = "This expires today! Don't forget to use it!";

        Intent MyIntent = new Intent(Intent.ACTION_VIEW);
        PendingIntent StartIntent = PendingIntent.getActivity(mContext.getApplicationContext(), 0, MyIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        Notification.Builder builder = new Notification.Builder(mContext.getApplicationContext(), id1);
        long when = System.currentTimeMillis();
        for(int i=0; i<todaysfoods.length; i++) {
            builder.setSmallIcon(R.drawable.notification_icon)
                    .setContentTitle(todaysfoods[i])
                    .setContentText(MyNotificationText)
                    .setContentIntent(StartIntent)
                    .setAutoCancel(true).setWhen(when);
            builder.setChannelId(id1);

            Notification notification = builder.build();
            notificationManager.notify(i, notification);
        }

    }

    @RequiresApi(26)
    public void send_todays_reminders(String[] todaysfoods){
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        String id1 = "channel_01";
        CharSequence name = "CHANNEL ONE";
        String description = "NOTIFICATIONS";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel(id1, name, importance);
        mChannel.setDescription(description);
        mChannel.enableLights(true);
        mChannel.setLightColor(Color.RED);
        mChannel.enableVibration(true);
        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        notificationManager.createNotificationChannel(mChannel);

        String MyNotificationText = "You asked us to remind you to eat this!";

        Intent MyIntent = new Intent(Intent.ACTION_VIEW);
        PendingIntent StartIntent = PendingIntent.getActivity(mContext.getApplicationContext(), 0, MyIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        Notification.Builder builder = new Notification.Builder(mContext.getApplicationContext(), id1);
        long when = System.currentTimeMillis();
        for(int i=0; i<todaysfoods.length; i++) {
            builder.setSmallIcon(R.drawable.notification_icon)
                    .setContentTitle(todaysfoods[i])
                    .setContentText(MyNotificationText)
                    .setContentIntent(StartIntent)
                    .setAutoCancel(true).setWhen(when);
            builder.setChannelId(id1);

            Notification notification = builder.build();
            notificationManager.notify(i, notification);
        }

    }

    public String addDates(String date, int daysToAdd)
    {
        String newDate = "00/00/0000";
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
