package com.stw.sieve.impl;

import com.stw.sieve.service.*;
import java.util.Scanner;

public class consoleApp {
    public static void main(String[] args) {
        System.out.println("Enter a positive integer value");
        Scanner sc = new Scanner(System.in);
        String lineIn=null;
        boolean continuing = true;
        try {
            if (args.length > 0) {
                lineIn = args[0];
            } else {
                if (args == null) {
                    System.out.println("You did not enter a positive integer value. The program will exit gracefully.");
                    sc.close();
                    System.exit(-1);
                } else
                    lineIn = sc.nextLine();
            }
            int num = 0;
            do {
                if (lineIn.equals("")) {
                    System.out.println("No more input!");
                    continuing = false;
                    break;
                }
                try {
                    num = Integer.parseInt(lineIn);
                    if (num > 0) {
                        switch (num) {
                            case 1:
                                System.out.println("There are no positive prime numbers less than 1");
                                break;
                            case 2:
                                System.out.println("The only positive prime number up to " + num + " is 1");
                                break;
                            default:
                                System.out.println(sieve.getPrimes(num));
                        }
                    } else {
                        System.out.println("You did not enter a positive integer value. The program will exit gracefully.");
                        continuing = false;
                    }
                } catch (NegativeArraySizeException | NumberFormatException nase) {
                    System.out.println("You did not enter a positive integer value. The program will exit gracefully.");
                    continuing = false;
                } catch (RuntimeException e) { //this should not happen, but it's here just in case
                    sc.close();
                    throw new RuntimeException(e);
                }
                if (continuing && args.length == 0) {
                    System.out.println("Enter a positive integer value");
                    lineIn = sc.nextLine();
                } else continuing = false; //either we are running unit tests or an empty or null input was detected
            } while (continuing);
            sc.close();
        } catch (NullPointerException npe) {
            System.out.println("You did not enter a positive integer value. The program will exit gracefully.");
            sc.close();
        }
    } //end main
}
