package vn.funix.fx21044.java.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Bank {
    private String id;
    private List<Customer> customers;

    private void setCustomers(List<Customer> customers){
        this.customers = customers;
    }

    public Bank(){
        this.id = String.valueOf(UUID.randomUUID());
        this.customers = new ArrayList<>();
    }

    public List<Customer> getCustomers(){
        return customers;
    }

    public String getId(){
        return id;
    }

    public boolean addCustomer(Customer newCustomer){
        for(Customer c : customers){
            if(c.getCustomerID().equalsIgnoreCase(newCustomer.getCustomerID())){
                System.out.println("So Can Cuoc Da Ton Tai");
                return false;
            }
        }
        customers.add(newCustomer);
        return true;
    }

    public Customer getCustomerByID(String customerID){
        if(customers.size() == 0){
            return null;
        }
        for(Customer c : customers){
            if(c.getCustomerID().equalsIgnoreCase(customerID)){
                return c;
            }
        }
        return null;
    }

    public ArrayList<Customer> getCustomerByName(String name){
        ArrayList<Customer> result = new ArrayList<>();
        for(Customer c: customers){
            if(c.getName().toLowerCase().contains(name.toLowerCase())){
                result.add(c);
            }
        }
        return result;
    }

    public String createNewAccountNumber(){
        boolean unique;
        String number;

        do{
            unique = true;
            number = CheckFuntion.randomNumberString(6);
            for(Customer c : customers){
                for(Account a : c.getAccounts()){
                    if(a.getAccountNumber().equalsIgnoreCase(number)){
                        unique = false;
                        break;
                    }
                }
                if(!unique){
                    break;
                }
            }
        }while (!unique);
        return number;
    }

    public boolean isAccountNumberExisted(String accountNumber){
        for(Customer customer: customers){
            for(Account account : customer.getAccounts()){
                if(account.getAccountNumber().equalsIgnoreCase(accountNumber)){
                    return true;
                }
            }
        }
        return false;
    }
}
