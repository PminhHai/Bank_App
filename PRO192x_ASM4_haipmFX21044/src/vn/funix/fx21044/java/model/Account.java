package vn.funix.fx21044.java.model;

import java.io.Serializable;

public class Account implements Serializable {
    private String customerID;
    private String accountNumber;
    private double balance;
    private int type;

    public Account(String customerID, String accountNumber, double balance) {
        this.customerID = customerID;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isPremium(){
        return this.balance >= 10000000;
    }
}
