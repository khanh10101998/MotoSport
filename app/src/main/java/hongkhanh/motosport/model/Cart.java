package hongkhanh.motosport.model;

public class Cart {
    public String customer_id;
    public String product_id;
    public String date_created;

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public Cart(String customer_id, String product_id) {
        this.customer_id = customer_id;
        this.product_id = product_id;
    }
}
