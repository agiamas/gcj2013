package com.google.codejam.palindrome;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Scanner;
/**
 * small input could work with int's , large 1 needed long's large 2 needed Big Java Numbers :) 
 */
public class Palindrome {
  Palindrome(String fileName) {

    File file = new File(fileName);
    try {
      Scanner scanner = new Scanner(file);
      File output = new File("output/palindrome/output.txt");
      PrintWriter printer = new PrintWriter(output);
      String firstLine = scanner.nextLine();
      Integer linesOfInput = Integer.parseInt(firstLine);
      checkInputConstraints(linesOfInput);
      for(int loop=0;loop<linesOfInput;loop++) {
        String nextLine = scanner.nextLine();
        String[] lineTokens = nextLine.split(" ");
        BigInteger minimumValue = new BigInteger(lineTokens[0]);
        BigInteger maximumValue = new BigInteger(lineTokens[1]);
        solve(minimumValue, maximumValue, loop+1, printer);
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
