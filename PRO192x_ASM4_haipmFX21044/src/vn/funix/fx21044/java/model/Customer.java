package vn.funix.fx21044.java.model;

import vn.funix.fx21044.java.dao.AccountDao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Customer extends User implements Serializable {
    private List<Account> accounts;
    public Customer(String name, String customerID) {
        super(name, customerID);
        accounts = new ArrayList<>();
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void importDataAccounts(){
        List<Account> accountList = AccountDao.list();
        List<Account> accountList1 = accountList
                .stream()
                .filter(account -> account.getCustomerID().equals(this.getCustomerID()))
                .collect(Collectors.toList());
        this.accounts = accountList1;
    }

    public Customer(List<String> values){
        this (values.get(1), values.get(0));
    }

    public String isPremium(){
        for(Account a : accounts){
            if(a.isPremium()){
                return "Premium";
            }
        }
        return "Normal";
    }

    public boolean addAccount(Account account){
        for(Account a : accounts){
            if(account.getAccountNumber().equalsIgnoreCase(a.getAccountNumber())){
                System.out.println("Loi, tai khoan da ton tai");
                return false;
            }
        }
        accounts.add(account);
        return true;
    }

    public double getBalance(){
        double total = 0;
        for(Account a: accounts){
            total += a.getBalance();
        }
        return total;
    }

    public void displayInformation(){
        System.out.printf("%-12s%-3s%16s%-1s%7s%-1s%13.2f%1s\n", getCustomerID(), " | ", getName(), " | ", isPremium(), " | ", getBalance(), "đ");
        if(accounts.size() == 0){
            System.out.println("Nguoi dung chua co tai khoan");
        }else {
            for(int i = 0; i < accounts.size(); i++){
                System.out.printf("%-6d%6s%3s%42.2f%1s\n",i + 1, accounts.get(i).getAccountNumber(), " | ", accounts.get(i).getBalance(), "đ");
            }
        }
    }

    public boolean isAccountExisted(String accountNumber){
        if(accounts.size() != 0){
            for(Account account : accounts){
                if(account.getAccountNumber().equals(accountNumber)){
                    return true;
                }
            }
        }
        return false;
    }
}
