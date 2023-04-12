package vn.funix.fx21044.java.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class CheckFuntion {
    public static String getDateTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }

    public static int integerInput(Scanner sc, String  inputMessage){
        int number;
        System.out.println(inputMessage);
        while (!sc.hasNextInt()){
            System.out.println("Vui long nhap so !!");
            System.out.print(inputMessage);
            sc.next();
        }
        number = sc.nextInt();
        return number;
    }

    public static double doubleInput(Scanner sc, String inputMessage){
        double number;
        System.out.println(inputMessage);
        while (!sc.hasNextDouble()){
            System.out.println("Vui long nhap so !!");
            System.out.print(inputMessage);
            sc.next();
        }
        number = sc.nextDouble();
        return number;
    }

    public static String randomNumberString (int length){
        double code1 = Math.random() * Math.pow(10, length);
        int code = (int) code1;
        String string = code+"";
        if(string.length() < length){
            int n = length - string.length();
            for(int i = 0; i < n; i++){
                string = "0" + string;
            }
        }
        return string;
    }

    public static String convertSlashesToDoubleBackSlash(String input) {
        return input.replace('/','\\');

    }
}
