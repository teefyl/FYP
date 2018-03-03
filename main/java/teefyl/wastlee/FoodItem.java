package teefyl.wastlee;

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

    public String getData(){
        String data;
        data = "Barcode ID:" + this.barcodeId +  " Project End Date: " + expiryDate;
        return data;
    }

    public String getName(){
        return name;
    }
    public String getExpiryDate(){return expiryDate;}
    public String getBarcodeID(){return barcodeId;}
    // public String getCategory() {return category;}
    public String toString(){
        return "Barcode ID: " + this.barcodeId + "\n"+ "Expiry Date: " + expiryDate+ "\n";
    }
}
