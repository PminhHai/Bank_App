package vn.funix.fx21044.java.dao;

import vn.funix.fx21044.java.model.Customer;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao implements Serializable {
    private final static String FILE_PATH = "store/customers.dat";
    public static void save(List<Customer> customers)  {
        File file = new File(FILE_PATH);
        System.out.println("Customers data saved in: " + file.getAbsolutePath());
        for (Customer customer:customers) {
            customer.setAccounts(new ArrayList<>());
        }
        BinaryFileService.writeFile(FILE_PATH,customers);
    }
    public static List<Customer> list() {
        File yourFile = new File(FILE_PATH);
        return BinaryFileService.readFile(FILE_PATH);
    }
}
