package vn.funix.fx21044.java.model;

import vn.funix.fx21044.java.dao.AccountDao;
import vn.funix.fx21044.java.dao.TransactionDao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DigitalCustomer extends Customer implements Serializable {
    private List<Transaction> transactions = new ArrayList<>();
    public DigitalCustomer(String name, String customerID) {
        super(name, customerID);
    }

    @Override
    public void displayInformation() {
        System.out.printf("%-12s%-3s%-16s%-1s%7s%-1s%13.2f%1s\n", getCustomerID(), " | ", getName(), " | ", isPremium(), " | ", getBalance(), "đ");
        if (getAccounts().size() == 0) {
            System.out.println("Khách hàng này không có tài khoản");
        }
        else
        {
            for (int i = 0; i < getAccounts().size(); i++) {
                System.out.printf("%-6d%6s%3s%28s%14.2f%1s\n", i + 1, getAccounts().get(i).getAccountNumber(), " | ", getAccountType(getAccounts().get(i)) + " |", getAccounts().get(i).getBalance(), "đ");
            }
        }
    }

    public String getAccountType(Account account) {
        if (account.getType() == 0) {
            return "LOAN";
        }
        if (account.getType() == 1) {
            return "SAVING";
        }

        return "normal";
    }

    public boolean withdrawAccount(String accountNumber, double amount){
        Account account = null;
        for(Account a : getAccounts()){
            if(a.getAccountNumber().equalsIgnoreCase(accountNumber)){
                account = a;
            }
        }

        if(account != null){
            boolean success = false;
            if(account instanceof LoanAccounts){
                LoanAccounts loanAccounts = (LoanAccounts) account;
                success = loanAccounts.withdraw(amount);
            } else if (account instanceof SavingAccounts) {
                SavingAccounts savingAccounts = (SavingAccounts) account;
                success = savingAccounts.withdraw(amount);
            } else {
                System.out.println("Không phải saving hoặc loan account");
                return false;
            }

            if(success){
                AccountDao.update(account);
                List<Transaction> transactionList = TransactionDao.list();
                transactionList.add(new Transaction(accountNumber,amount,true,"Withdraw"));
                TransactionDao.save(transactionList);
                System.out.println("Rút tiền thành công");
                return true;
            }
        }
        else {
            System.out.println("Không tìm được số tài khoản ");
        }
        return false;
    }

    public void displayTransactionsList(){
        List<Transaction> transactionList = TransactionDao.list();
        for(Transaction transaction:transactionList){
            for(Account account: getAccounts()){
                if(account.getAccountNumber().equals(transaction.getAccountNumber())){
                    transactions.add(transaction);
                    break;
                }
            }
        }
        for(Transaction t : transactions){
            System.out.printf("[GD]  %6s | %9s | %10.2f d | %s\n", t.getAccountNumber(), t.getType(), t.getAmount(), t.getTime());
        }
    }
}
