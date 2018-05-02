package teefyl.wastlee;

/**
 * Created by Laura on 27/01/2018.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBManager extends SQLiteOpenHelper {
    private static int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "Foods.db";
    private static final String TABLE_FOOD = "food";
    private static final String COLUMN_FOOD_NAME = "food_name";
    private static final String COLUMN_BARCODE = "barcode";
    private static final String COLUMN_EXPIRY = "expiry_date";
    private static final String COLUMN_REMINDER = "reminder_date";
    private static final String COLUMN_DATEADDED = "date_added";


    public DBManager(Context context, SQLiteDatabase.CursorFactory factory){
        super(context,DATABASE_NAME,factory,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String query2 = "CREATE TABLE "+TABLE_FOOD+ "("+
                " INT, " +
                COLUMN_FOOD_NAME+" TEXT, "
                +COLUMN_BARCODE+" TEXT, "
                +COLUMN_EXPIRY+" INT, "
                +COLUMN_REMINDER+" TEXT, "
                +COLUMN_DATEADDED+" TEXT );" ;
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_FOOD);
        onCreate(db);
    }


    public void deleteFoodItem(String barcode){
        if(checkFood(barcode)){
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("DELETE FROM "+TABLE_FOOD+" WHERE "+COLUMN_BARCODE+"="+"'"+barcode+"'");
            db.close();
        }

    }


    //removes data with the same barcode ID, replaces with new data
    public void editFood(FoodItem item){
        deleteFoodItem(item.getBarcodeID());
        addFood(item);
    }

    //adds new
    public void addFood(FoodItem food){//adds new food to food table
        ContentValues c = new ContentValues();
        c.put(COLUMN_FOOD_NAME,food.getName());
        c.put(COLUMN_BARCODE,food.getBarcodeID());
        c.put(COLUMN_EXPIRY,food.getExpiryDate());
        c.put(COLUMN_REMINDER,food.getReminderDate());
        c.put(COLUMN_DATEADDED,food.getDateAdded());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_FOOD,null,c);
        db.close();
    }
    //returns object from name
    public FoodItem getFoodItem(String item) {
        FoodItem food;
        String query = "SELECT * FROM " + TABLE_FOOD + " WHERE " + COLUMN_BARCODE + "=" + "'"+item+"'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if(!c.moveToFirst()){
            c.close();
            return null;
        }

        food = new FoodItem(c.getString(c.getColumnIndex(COLUMN_FOOD_NAME)),
                c.getString(c.getColumnIndex(COLUMN_BARCODE)),
                c.getString(c.getColumnIndex(COLUMN_EXPIRY)),
                c.getString(c.getColumnIndex(COLUMN_REMINDER)),
                c.getString(c.getColumnIndex(COLUMN_DATEADDED))
        );
        c.close();
        return food;
    }

    //Function to check if food is already in the DB
    public boolean checkFood(String barcode){
        String query = "SELECT * FROM " + TABLE_FOOD + " WHERE " + COLUMN_BARCODE + " = "
                +"'"+barcode+"'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(query,null);
        boolean ret = c.moveToFirst();
        c.close();
        return ret;
    }

    public FoodItem checkBarcode(String barcode){
        String query = "SELECT * FROM " + TABLE_FOOD + " WHERE " + COLUMN_BARCODE + " = '"+barcode+"'";
        SQLiteDatabase db = getReadableDatabase();
        FoodItem food;
        Cursor c = db.rawQuery(query,null);
        if(!c.moveToFirst()){
            c.close();
            return null;
        }
        else{
            food = new FoodItem(c.getString(c.getColumnIndex(COLUMN_FOOD_NAME)),
                    c.getString(c.getColumnIndex(COLUMN_BARCODE)),
                    c.getString(c.getColumnIndex(COLUMN_EXPIRY)),
                    c.getString(c.getColumnIndex(COLUMN_REMINDER)),
                    c.getString(c.getColumnIndex(COLUMN_DATEADDED))
            );
            c.close();
            return food;
        }
    }


    public FoodItem[] searchItems(String search){
        FoodItem[] items;
        String query = "SELECT * FROM " + TABLE_FOOD + " WHERE " + COLUMN_FOOD_NAME + " LIKE '"+search+"%"+"'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(query,null);
        items = new FoodItem[c.getCount()];
        if(!c.moveToFirst()){
            return null;
        }
        else{
            for(int i=0;i<items.length;i++){
                items[i] = new FoodItem(c.getString(c.getColumnIndex(COLUMN_FOOD_NAME)),
                        c.getString(c.getColumnIndex(COLUMN_BARCODE)),
                        c.getString(c.getColumnIndex(COLUMN_EXPIRY)),
                        c.getString(c.getColumnIndex(COLUMN_REMINDER)),
                        c.getString(c.getColumnIndex(COLUMN_DATEADDED))
                );
                c.moveToNext();
            }
        }
        return items;
    }

}
