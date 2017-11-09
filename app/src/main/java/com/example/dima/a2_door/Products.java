package com.example.dima.a2_door;

public class Products {
    private String date;
    private String customer_Name;
    private String address;
    private String phone_Number;
    private String deliver;
    private String gov;
    private String product;
    private String more;

    public Products(String date, String customer_Name, String address, String phone_Number, String deliver, String gov, String product, String more) {
        this.date = date;
        this.customer_Name = customer_Name;
        this.address = address;
        this.phone_Number = phone_Number;
        this.deliver = deliver;
        this.gov = gov;
        this.product = product;
        this.more = more;
    }

    public Products() {

    }


    public String getDate() {
        return date;
    }

    public String getCustomer_Name() {
        return customer_Name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone_Number() {
        return phone_Number;
    }

    public String getDeliver() {
        return deliver;
    }

    public String getGov() {
        return gov;
    }

    public String getProduct() {
        return product;
    }

    public String getMore() {
        return more;
    }
}
