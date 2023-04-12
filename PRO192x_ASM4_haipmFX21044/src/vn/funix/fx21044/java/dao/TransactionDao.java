package vn.funix.fx21044.java.dao;

import vn.funix.fx21044.java.model.Transaction;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class TransactionDao implements Serializable {
    private final static String FILE_PATH = "store/transactions.dat";

    public static void save(List<Transaction> transactions){
        File file = new File(FILE_PATH);
        System.out.println("Transaction data saved in: " + file.getAbsolutePath());
        BinaryFileService.writeFile(file.getAbsolutePath(),transactions);
    }

    public static List<Transaction> list(){
        File yourFile = new File(FILE_PATH);
        return BinaryFileService.readFile(yourFile.getAbsolutePath());
    }

}
