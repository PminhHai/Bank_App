package vn.funix.fx21044.java.dao;

import vn.funix.fx21044.java.model.Account;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AccountDao implements Serializable {
    private final static String FILE_PATH = "store/accounts.dat";
    public static void save(List<Account> accounts){
        File file = new File(FILE_PATH);
        System.out.println("Accounts data saved in: " + file.getAbsolutePath());
        BinaryFileService.writeFile(file.getAbsolutePath(),accounts);
    }
    public static List<Account> list(){
        File yourFile = new File(FILE_PATH);
        return BinaryFileService.readFile(yourFile.getAbsolutePath());
    }

    public static void update(Account editAccount){
        boolean isNew = true;
        List<Account> accounts = new ArrayList<>();
        for(Account a : list()){
            if(a.getAccountNumber().equals(editAccount.getAccountNumber())){
                accounts.add(editAccount);
                isNew = false;
            } else {
                accounts.add(a);
            }
        }
        if(isNew){
            accounts.add(editAccount);
        }
        save(accounts);
    }
}
