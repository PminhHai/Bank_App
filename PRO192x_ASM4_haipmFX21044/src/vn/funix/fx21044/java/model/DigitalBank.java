package vn.funix.fx21044.java.model;

import vn.funix.fx21044.java.dao.AccountDao;
import vn.funix.fx21044.java.dao.CustomerDao;
import vn.funix.fx21044.java.exception.CustomerIdNotValidException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class DigitalBank extends Bank{

    //Phương thức showCustomers() in ra thông tin khách hàng và cả tài khoản
    public static void showCustomer(){
        List<Customer> customerList = CustomerDao.list();
        if(customerList.size() == 0){
            System.out.println("Ngân hàng không có khách hàng");
            return;
        }

        List<Account> accountList = AccountDao.list();
        int countFailAttempt = 0;
        if(accountList.size() != 0){
            for(Account account : accountList){
                boolean addAccountSuccess = false;
                for(Customer customer: customerList){
                    if(customer.getCustomerID().equals(account.getCustomerID())){
                        customer.addAccount(account);
                        addAccountSuccess = true;
                        break;
                    }
                }
                if(!addAccountSuccess) countFailAttempt++;
            }
            if(countFailAttempt > 0) {
                System.out.println("Dữ liệu lỗi, có "+ countFailAttempt+" tài khoản không có chủ");
            }
        }

        for (Customer customer : customerList){
            if(customer instanceof DigitalCustomer){
                DigitalCustomer digitalCustomer = (DigitalCustomer) customer;
                digitalCustomer.displayInformation();
            } else {
                customer.displayInformation();
            }
        }
    }

    //Phương thức addCustomers(fileName) lấy đường dẫn đễ tìm file chứa thông tin khách hàng mới
    public static void addCustomer(String inputPath){
        List<Customer> customerList = CustomerDao.list();
        File f = new File("");
        String rootPath = f.getAbsolutePath() + "\\";
        inputPath = CheckFuntion.convertSlashesToDoubleBackSlash(inputPath);
        try (Scanner sc = new Scanner(new FileReader(rootPath + inputPath))){
            sc.useDelimiter(",");
            while (sc.hasNextLine()){
                String customerID = sc.next();
                sc.skip(sc.delimiter());
                String name = sc.nextLine();
                boolean existed = false;
                for(Customer customer : customerList){
                    if(customer.getCustomerID().equals(customerID)){
                        existed = true;
                        break;
                    }
                }
                if(!existed){
                    customerList.add(new DigitalCustomer(name, customerID));
                    existed = true;
                    break;
                }
            }
            CustomerDao.save(customerList);
        } catch (FileNotFoundException e) {
            System.out.println("Tệp không tồn tại");
            System.out.println(e);
        } catch (InputMismatchException e) {
            System.out.println(e);
        } catch (NoSuchElementException e) {
            System.out.println("File thiếu dấu ,");
            System.out.println(e);
        }
    }

    //Phương thức nhận danh sách khách hàng và kiểm tra CCCD nhận được có tồn tại trong danh sách đó ko
    public static Customer getCustomerByID(List<Customer> customerList, String customerID){
        Customer customer = null;
        for(Customer c : customerList){
            if(c.getCustomerID().equals(customerID)){
                customer = c;
                break;
            }
        }

        if(customer != null){
            customer.importDataAccounts();
        } else {
            throw new CustomerIdNotValidException("Không có khách hàng nào có số CCCD trên");
        }
        return customer;
    }

    //Phương thức thực hiện rút tiền
    public static boolean withdrawAccount(Customer customer, String accountNumber, double amount){
        if(!(customer instanceof DigitalCustomer)){
            return false;
        }
        DigitalCustomer digitalCustomer = (DigitalCustomer) customer;
        if(customer != null){
            return digitalCustomer.withdrawAccount(accountNumber, amount);
        } else {
            System.out.println("Không tìm thấy CCCD");
        }
        return false;
    }

    //Phương thức thực hiện lưu tài khoản ATM
    public static void addSavingAccount(String customerID, String accountNumber, double amount){
        SavingAccounts account = new SavingAccounts(customerID, accountNumber, amount);
        AccountDao.update(account);
    }

    //Phương thức kiểm tra danh sách tài khoản
    public static boolean isAccountNumberExisted(List<Account> accountList, String accountNumber){
        return accountList
                .stream()
                .anyMatch(account -> account.getAccountNumber().equals(accountNumber));
    }

    //Phương thức kiểm tra danh sách khách hàng
    public static boolean isCustomerIDExisted(List<Customer> customerList, String customerID){
        boolean result = customerList
                .stream()
                .anyMatch(customer -> customer.getCustomerID().equals(customerID));
        if(!result){
            throw new CustomerIdNotValidException("Không có khách hàng nào có số CCCD trên");
        }
        return true;
    }

    //Phương thức trả lại đối tượng khách hàng
    public static Account getAccountByAccountNumber(List<Account> accountList, String accountNumber){
        for(Account account : accountList){
            if(account.getAccountNumber().equals(accountNumber)){
                return account;
            }
        }
        return null;
    }

    public static void printReceipt(String senderAccount, String receiverAccount, double amount, double balanceRemain){
        System.out.println("-------------+-----------+---------------");
        System.out.printf("%30s\n", "BIÊN LAI GIAO DỊCH TRANFERS");
        System.out.printf("NGÀY G/D : %27s\n", CheckFuntion.getDateTime());
        System.out.printf("ATM ID: %30s\n", "DIGITAL-BANK-ATM 2022");
        System.out.printf("SỐ TK: %31s\n", senderAccount);
        System.out.printf("SỐ TK NHAN: %31s\n", receiverAccount);
        System.out.printf("SỐ TIEN CHUYEN: %29.1f\n", amount);
        System.out.printf("SỐ DƯ TK: %31.1f\n", balanceRemain);
        System.out.printf("PHÍ + VẶT: %27.1f\n", 0.0);
        System.out.println("-------------+-----------+---------------");
    }

    public static void showTransactionByCustomer(Customer customer){
        DigitalCustomer digitalCustomer;
        if(customer instanceof DigitalCustomer){
            digitalCustomer = (DigitalCustomer) customer;
        } else {
            System.out.println("error not digital");
            return;
        }
        if(digitalCustomer != null){
            digitalCustomer.displayTransactionsList();
        }
    }
}
