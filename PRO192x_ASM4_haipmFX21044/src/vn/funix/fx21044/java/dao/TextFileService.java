package vn.funix.fx21044.java.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class TextFileService {
    private static final String COMMA_DELIMITER = ",";
    public static List<List<String>> readFile(String filePath){
        List<List<String>> listOfItem = new ArrayList<>();

        Scanner sc = null;

        try {
            sc = new Scanner(new FileReader(filePath));
            sc.useDelimiter(",");
            while (sc.hasNextLine()){
                List<String> listOfDetails = new ArrayList<>();
                System.out.println("new");
                String s = sc.next();
                listOfDetails.add(s);
                sc.skip(sc.delimiter());
                listOfItem.add(listOfDetails);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Tệp không tồn tại");
            System.out.println(e);
        } catch (InputMismatchException e) {
            System.out.println(e);
        } catch (NoSuchElementException e) {
            System.out.println(e);
        } finally {
            if (sc != null){
                sc.close();
            }
        }
        return listOfItem;
    }
}
