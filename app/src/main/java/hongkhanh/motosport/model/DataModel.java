package hongkhanh.motosport.model;

/**
 * Created by anupamchugh on 11/02/17.
 */

public class DataModel {


    public String name;
    public String price;
    public String drawable;

    public DataModel(String t, String d, String p)
    {
        price = p;
        name=t;
        drawable=d;
    }
}
