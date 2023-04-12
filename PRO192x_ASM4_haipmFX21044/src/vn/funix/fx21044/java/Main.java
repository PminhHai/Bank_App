package vn.funix.fx21044.java;

import vn.funix.fx21044.java.dao.AccountDao;
import vn.funix.fx21044.java.dao.CustomerDao;
import vn.funix.fx21044.java.dao.TransactionDao;
import vn.funix.fx21044.java.exception.CustomerIdNotValidException;
import vn.funix.fx21044.java.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static final String AUTHOR = "FX21044";
    static final String VERSION = "v4.0.0";
    private static DigitalBank digitalBank;
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws FileNotFoundException {
        digitalBank = new DigitalBank();

         initialTestData();
        int input = -1;
        boolean firstInput = true;
        //Vong lap menu
        while (input != 0) {
            if (firstInput) displayMenu();
            input = CheckFuntion.integerInput(sc, "Nhap menu:");
            firstInput = false;
            switch (input) {
                case 1:
                    showCustomerList();
                    firstInput = true;
                    break;
                case 2:
                    importNewCustomers();
                    firstInput = true;
                    break;
                case 3:
                    addNewATMAccount();
                    firstInput = true;
                    break;
                case 4:
                    transferAccount();
                    firstInput = true;
                    break;
                case 5:
                    withdrawAccount();
                    firstInput = true;
                    break;
                case 6:
                    showListTransaction();
                    firstInput = true;
                    break;
                case 0:
                    System.out.println("Ket thuc chuong trinh");
                    break;
                default:
                    System.out.println("Nhap sai gia tri, hay nhap lai");
                    break;
            }
        }
    }

    public static void displayMenu() {
        System.out.println("+----------+-----------+-----------+");
        System.out.println("| NGÂN HÀNG ĐIỆN TỬ | " + AUTHOR + "@" + VERSION + "  |");
        System.out.println("+----------+-----------+-----------+");
        System.out.println("| 1. Danh sách khách hàng          |");
        System.out.println("| 2. Nhập danh sách khách hàng     |");
        System.out.println("| 3. Thêm tài khoản ATM            |");
        System.out.println("| 4. Chuyển tiền                   |");
        System.out.println("| 5. Rút tiền                      |");
        System.out.println("| 6. Tra cứu lịch sử giao dịch     |");
        System.out.println("| 0. Thoát                         |");
        System.out.println("+----------+-----------+-----------+");
    }

    public static void showCustomerList(){
        DigitalBank.showCustomer();
        sc.nextLine();
    }

    public static void importNewCustomers(){
        System.out.println("Nhập đường dẫn đến tệp: ");
        String inputPath = sc.next();
        DigitalBank.addCustomer(inputPath);

    }

    public static void addNewATMAccount(){
        List<Customer> customerList = CustomerDao.list();
        String customerID;
        boolean customerExisted = false;

        do{
            customerID = checkUserID();
            if(customerID == null){
                return;
            }
            try {
                customerExisted = DigitalBank.isCustomerIDExisted(customerList, customerID);
            } catch (CustomerIdNotValidException e){
                System.out.println("Không có khách hàng có số CCCD trên");
            }
        }while (!customerExisted);
        String accountNumber;
        double amount;
        boolean existed;
        List<Account> accountList = AccountDao.list();
        do{
            accountNumber = checkBankIDNumber(6,"");
            if(accountNumber == null){
                return;
            }
            existed = DigitalBank.isAccountNumberExisted(accountList,accountNumber);
            if(existed){
                System.out.println("Số tài khoản này đã tồn tài vui lòng nhập số khác");
            }
        }while (existed);

        do{
            amount = CheckFuntion.doubleInput(sc, "Nhập số dư:");
            if(amount == 0){
                return;
            }
            if(amount < 50000)
            {
                System.out.println("Số dư phải lớn hơn 50.000d");
            }
        }while (amount < 50000);
        DigitalBank.addSavingAccount(customerID,accountNumber,amount);
        System.out.println("Tạo tài khoản ATM thành công");
        sc.nextLine();
    }

    public static void transferAccount(){
        List<Customer> customerList = CustomerDao.list();
        List<Account> accountList = AccountDao.list();
        if(customerList.size() < 1 || accountList.size() <2){
            System.out.println("Không có đủ khách hàng/ tài khoản để thực hiện");
            return;
        }
        String customerID;
        boolean customerExisted = false;
        do {
            customerID = checkUserID();
            if(customerID  == null){
                return;
            }
            try {
                customerExisted = DigitalBank.isCustomerIDExisted(customerList,customerID);
            }catch (CustomerIdNotValidException e){
                System.out.println("Không có khách hàng có số CCCD trên");
            }
        }while (!customerExisted);

        Customer customer = DigitalBank.getCustomerByID(customerList,customerID);
        customer.displayInformation();
        String accountNumber;
        boolean existed;
        do{
            accountNumber = checkBankIDNumber(6, " của người gửi ");
            if(accountNumber == null){
                return;
            }
            existed = customer.isAccountExisted(accountNumber);
            if(!existed){
                System.out.println("Khách hàng không có số tài khoản này");
            }
        }while (!existed);

        String accountNumber2;
        do{
            accountNumber2 = checkBankIDNumber(6, "của người nhận");
            if(accountNumber2 == null){
                return;
            }
            existed = DigitalBank.isAccountNumberExisted(accountList, accountNumber2);
            if(!existed){
                System.out.println("Khách hàng không có số tài khoản này");
            }
            if(accountNumber2.equals(accountNumber)){
                existed = false;
                System.out.println("Tài khoản nhận không được trùng với tài khoản gửi");
            }
        }while (!existed);

        Account senderAccount = DigitalBank.getAccountByAccountNumber(accountList, accountNumber);
        double amount;

        do{
            amount = CheckFuntion.doubleInput(sc, "Nhập số tiền: ");
            if(amount == 0){
                return;
            }
            if((senderAccount.getBalance() - amount) < 50000){
                System.out.println("Số dư còn lại phải lớn hơn 50.000d");
            }
        }while ((senderAccount.getBalance() - amount) < 50000);

        System.out.println("Nhập Y để xác nhận chuyển " + amount + " đ từ TK " + accountNumber + " đến TK " +accountNumber2);
        sc.nextLine();
        String confirmInput = sc.nextLine();
        if(!confirmInput.equalsIgnoreCase("y")){
            return;
        }
        Account receiveAccount = DigitalBank.getAccountByAccountNumber(accountList,accountNumber2);
        senderAccount.setBalance(senderAccount.getBalance() - amount);
        AccountDao.update(senderAccount);
        receiveAccount.setBalance(receiveAccount.getBalance() + amount);
        AccountDao.update(receiveAccount);

        List<Transaction> transactionList = TransactionDao.list();
        transactionList.add(new Transaction(accountNumber, -(amount),true,"Transfers"));
        transactionList.add(new Transaction(accountNumber2, amount, true, "Transfers"));
        TransactionDao.save(transactionList);

        System.out.println("Đã chuyển tiền thành công, biên lai giao dịch:");
        DigitalBank.printReceipt(accountNumber, accountNumber2, amount, senderAccount.getBalance());
        sc.nextLine();
        sc.nextLine();
    }

    public static void withdrawAccount(){
        List<Customer> customerList = CustomerDao.list();
        String customerID;
        boolean customerExisted = false;
        do{
            customerID = checkUserID();
            if(customerID == null){
                return;
            }
            try {
                customerExisted = DigitalBank.isCustomerIDExisted(customerList, customerID);
            }catch (CustomerIdNotValidException e){
                System.out.println("Không có khách nào có số CCCD trên");
            }
        }while (!customerExisted);

        Customer customer = DigitalBank.getCustomerByID(customerList, customerID);
        customer.displayInformation();

        String accountNumber;
        double amount;
        boolean existed;
        do {
            accountNumber = checkBankIDNumber(6, "");
            if (accountNumber == null) {
                return;
            }
            existed = customer.isAccountExisted(accountNumber);
            if (!existed) {
                System.out.println("số TK này không tồn tại");
            }
        } while (!existed);

        do{
            amount = CheckFuntion.doubleInput(sc, "Nhâp số tiền rút: ");
            if(amount == 0) {
                return;
            }
        }while (!DigitalBank.withdrawAccount(customer,accountNumber,amount));
        sc.nextLine();
        sc.nextLine();
    }

    public static void showListTransaction(){
        List<Customer> customerList = CustomerDao.list();
        String customerID;
        boolean customerExisted;
        do {
            customerID = checkUserID();
            if(customerID == null){
                return;
            }
            customerExisted = DigitalBank.isCustomerIDExisted(customerList,customerID);
            if(!customerExisted){
                System.out.println("Không có khách nào có số CCCD trên");
            }
        }while (!customerExisted);

        Customer customer = DigitalBank.getCustomerByID(customerList,customerID);
        customer.displayInformation();
        DigitalBank.showTransactionByCustomer(customer);
        sc.nextLine();
        sc.nextLine();
    }

    public static String checkBankIDNumber(int length, String extraDescription){
        String input;
        do{
            System.out.print("Vui lòng nhập mã STK " + extraDescription + " gồm " + length + " chữ số:");
            input = sc.next();
            if(input.equalsIgnoreCase("no")){
                return null;
            }
            if(!input.matches("[0-9]+")){
                System.out.println("Vui lòng nhập số!! (hoặc nhập NO để thoát)");
            }
            if(input.length() != length){
                System.out.println("Vui lòng nhập đủ " + length + " so (hoặc nhập NO để thoát)");
            }
        }while (input == null || !input.matches("[0-9]+") || input.length() != length );
        return input;
    }

    public static String checkUserID(){
        String input;
        do{
            System.out.println("Vui lòng nhập số CCCD");
            input = sc.next();
            if (input.equalsIgnoreCase("no")) {
                return null;
            }
            if(!input.matches("[0-9]+")){
                System.out.println("Vui lòng nhập số!! (hoặc nhập NO để thoát)");
            }
            if(input.length() != 12){
                System.out.println("Vui lòng nhập đủ 12 số (hoặc nhập NO để thoát)");
            }
        } while (input == null || !input.matches("[0-9]+") || input.length() != 12 );
        return input;
    }

    public static void initialTestData(){
        List<Customer> customerList = new ArrayList<>();
        customerList.add(new DigitalCustomer("John Cena", "085654123456"));
        CustomerDao.save(customerList);
        DigitalBank.addSavingAccount("085654123456","123456",50000000);
        DigitalBank.addSavingAccount("085654123456","123745",50000000);
    }


}