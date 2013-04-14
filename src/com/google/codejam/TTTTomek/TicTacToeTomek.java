package com.google.codejam.TTTTomek;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Scanner;

public class TicTacToeTomek {
  private static final String outputFile = "output/tttt/outputALarge.txt";

  TicTacToeTomek(String fileName) {
    File file = new File(fileName);
    try {
      Scanner scanner = new Scanner(file);
      File output = new File(outputFile);
      PrintWriter printer = new PrintWriter(output);
      String firstLine = scanner.nextLine();
      Integer linesOfInput = Integer.parseInt(firstLine);
      checkInputConstraints(linesOfInput, printer);
      for(int loop=0;loop<linesOfInput;loop++) {
        int[][] curBoard = new int[4][4]; 
        for(int board=0;board<4;board++) {
          // each line of input
          String nextLine = scanner.nextLine();
          for(int c=0; c<nextLine.length(); c++) {
            char currentChar = nextLine.charAt(c);
            if(currentChar=='X') {
              curBoard[board][c] = 1; 
            } else if(currentChar=='O') {
              curBoard[board][c] = 2;
            } else if(currentChar=='T') {
              curBoard[board][c] = 0; // 0 for the joker value
            } else {
              curBoard[board][c] = -1; // -1 for empty value
            }
          }
        }
        solve(curBoard,loop+1, printer);
        // read that empty line
        scanner.nextLine();
      }
      scanner.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

  }

  private void solve(int[][] curBoard, int testCase, PrintWriter writer) {
    //human debug info
    printHuman(curBoard);
    if(checkRows(curBoard, 1, 0) || checkColumns(curBoard, 1, 0) || checkDiagonals(curBoard, 1, 0)) {
      // winner X
      writer.println("Case #" + testCase + ": X won");
    } else
      if(checkRows(curBoard, 2, 0) || checkColumns(curBoard, 2, 0) || checkDiagonals(curBoard, 2, 0)) {
        // winner O
        writer.println("Case #" + testCase + ": O won");
      } else
        if(getEmptySpots(curBoard)==0) {
          // draw
          writer.println("Case #" + testCase + ": Draw");
        } else {
          writer.println("Case #" + testCase + ": Game has not completed");
        }
    writer.flush();
  }


  private boolean checkDiagonals(int[][] board, int i, int j) {
    if(checkNormalDiagonal(board, i, j)) {
      return true;
    }
    if(checkOtherDiagonal(board, i, j)) {
      return true;
    }
    return false;

  }

  private boolean checkOtherDiagonal(int[][] board, int i, int j) {
    int boardSize = board.length-1;
    boolean winnerDiagonal = true;
    for(int otherDiag=boardSize;otherDiag>=0; otherDiag--) {
      if(! (board[otherDiag][boardSize-otherDiag] == i || board[otherDiag][boardSize-otherDiag] == j ) ) {
        winnerDiagonal = false;
        break;
      }
    }
    return winnerDiagonal;
  }

  private boolean checkNormalDiagonal(int[][] board, int i, int j) {
    int boardSize = board.length;
    boolean winnerDiagonal = true;
    // check one diagonal 0,0 1,1 2,2 .. n-1,n-1
    for(int diagonal=0;diagonal<boardSize;diagonal++) {
      if(! ( board[diagonal][diagonal] == i || board[diagonal][diagonal] == j ) ) {
        winnerDiagonal = false;
        break;
      }
    }
    return winnerDiagonal;
  }

  /*
   * check columns is the same as checking rows in a transposed matrix (they are N*N so we can always transpose them)
   */
  private boolean checkColumns(int[][] board, int i, int j) {
    int [][] transposedBoard = transposeMatrix(board);
    return checkRows(transposedBoard, i, j);
  }

  private boolean checkRows(int[][] board, int i, int j) {
    int boardSize = board.length;
    boolean winnerRow = true;
    for(int row=0;row<boardSize;row++) {
      if(row>0 && winnerRow) {
        break;
      }
      for(int column=0;column<boardSize;column++) {
        if(! (board[row][column] == i || board[row][column] == j)) {
          winnerRow = false;
          break;
        } else {
          winnerRow = true;
        }
      }
    }
    return winnerRow;
  }


  public int [][] transposeMatrix(int [][] m){
    int r = m.length;
    int c = m[r-1].length;
    int [][] t = new int[c][r];
    for(int i = 0; i < r; ++i){
      for(int j = 0; j < c; ++j){
        t[j][i] = m[i][j];
      }
    }
    return t;
  }

  public Integer getEmptySpots(int [][]board) {
    int emptySpots=0;
    int boardSize = board.length;
    for(int row=0; row<boardSize; row++) {
      for(int col=0; col<boardSize; col++) {
        if(board[row][col] == -1) emptySpots++;
      }
    }
    return emptySpots;
  }


  private void printHuman(int[][] curBoard) {
    System.out.println("printing board");
    for(int row=0;row<curBoard.length;row++) {
      System.out.print("[");
      for(int column=0;column<curBoard.length;column++) {
        System.out.print(" " + curBoard[row][column]);
      }
      System.out.println("]");
    }
    System.out.println("===============");
  }

  private void checkInputConstraints(Integer linesOfInput, PrintWriter writer) {
    if(linesOfInput<1) writer.print("invalid input!");
  }
}
