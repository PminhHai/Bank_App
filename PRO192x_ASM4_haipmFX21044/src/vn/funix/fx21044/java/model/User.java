package vn.funix.fx21044.java.model;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String customerID;

    public User(String name, String customerID) {
        if(customerID.length() == 12 && customerID.matches("[0-9]+")){
            this.customerID = customerID;
        } else {
            System.out.println("Loi , Can Cuoc Khong Hop Le");
            return;
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        if(customerID.length() == 12 && customerID.matches("[0-9]+")){
            this.customerID = customerID;
        }
        else {
            System.out.println("Loi , Can Cuoc Khong Hop Le");
        }
    }
}
