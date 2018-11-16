package hongkhanh.motosport.model;

import java.io.Serializable;

public class Product implements Serializable {
    public int id;
    public String name;
    public String price;
    public String image;
    public String detail;
    public int count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Product(int id, String name, String price, String image, int count) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.count = count;
    }


}
