package com.google.codejam.treasure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Treasure {
 
    Treasure(String fileName) {

      File file = new File(fileName);
      try {
        Scanner scanner = new Scanner(file);
        File output = new File("output/treasure/smallOutput.txt");
        PrintWriter printer = new PrintWriter(output);
        String firstLine = scanner.nextLine();
        Integer linesOfInput = Integer.parseInt(firstLine);
        checkInputConstraints(linesOfInput);
        for(int loop=0;loop<linesOfInput;loop++) {
          String keysAndChestsLine = scanner.nextLine();
          String[] keysAndChestsLineArray = keysAndChestsLine.split(" ");
          // K, N
          Integer numberOfKeys = Integer.parseInt(keysAndChestsLineArray[0]);
          Integer numberOfChests = Integer.parseInt(keysAndChestsLineArray[1]);
          
          String typesOfKeysIStartWith = scanner.nextLine();
          String[] typesOfKeysIStartWithArray = typesOfKeysIStartWith.split(" ");
          // a list of the types of keys i start with
          List<Integer> typesOfKeysIStartWithInteger = new ArrayList<Integer>();
          for(int i=0;i<typesOfKeysIStartWithArray.length;i++) {
            typesOfKeysIStartWithInteger.add(Integer.parseInt(typesOfKeysIStartWithArray[i]));
          }
          // go over each chest
          for(int j=0;j<numberOfChests;j++) {
            String nextChest = scanner.nextLine();
            String[] nextChestArray = nextChest.split(" ");
            
            Integer keyNeededToOpenCurrentChest = Integer.parseInt(nextChestArray[0]);
            Integer numberOfKeysInCurrentChest = Integer.parseInt(nextChestArray[1]);
            List<Integer> keysInCurrentChest = new ArrayList<Integer>();
            for(int k=2;k<nextChestArray.length;k++) {
              keysInCurrentChest.add(Integer.parseInt(nextChestArray[k]));
            }
          }
        }
        scanner.close();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }

    }

    private void checkInputConstraints(Integer linesOfInput) {
      if(linesOfInput<=0) {
        System.out.println("no test cases!");
      }
    }

    private void solve(BigInteger minimumValue, BigInteger maximumValue, Integer lineOfInput, PrintWriter printer) {
      int countFairAndSquare = 0;
      BigInteger loop=minimumValue;
      while(loop.compareTo(maximumValue) <= 0) {
        if(isPalindrome(loop) && isSquareOfAPalindrome(loop)) countFairAndSquare++;
        loop = loop.add(new BigInteger("1"));
      }
      String s = "Case #" + (lineOfInput) + ": " + countFairAndSquare+"\n";
      System.out.print(s);
      printer.write(s);
      printer.flush();
    }

    private boolean isPalindrome(BigInteger input) {
      String str = input.toString();
      return str.equals(new StringBuffer().append(str).reverse().toString());
    }

    private boolean isSquareOfAPalindrome(BigInteger input) {
      // first check if it's a square
      Long longInput = input.longValue();
      Double squareInput = Math.floor(Math.sqrt(longInput));
      Long squareInputInt = squareInput.longValue();
      if(Math.pow(squareInputInt, 2) != longInput) return false;
      
      // then check if the square root is a palindrome itself
      return isPalindrome(new BigInteger(squareInputInt.toString()));
    }
  
}
