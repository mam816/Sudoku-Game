
package sudoku.game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class SudokuGame {
 
   private int[][] gameBoard;
   private Scanner keyboard;
    
   public static void main(String[] args) throws FileNotFoundException {
      new SudokuGame();   
   }
    
   public SudokuGame() throws FileNotFoundException {
   // initialize game
      initializeGame();
      printGameBoard();
      while(!hasGameBeenSolved()) {
         int[] cellLocation = getNextCell();
         if (cellLocation != null) {
            System.out.print("Enter number in (" + cellLocation[0] + ","
                        + cellLocation[1] + "): ");
            Scanner keyboard = new Scanner(System.in);
            gameBoard[cellLocation[0]][cellLocation[1]] = keyboard.nextInt();
            gameBoard[cellLocation[0]][cellLocation[1]] = cellLocation[2];
            printGameBoard();
            if(hasGameBeenSolved()){
                System.out.println("You have solved the board");
            }
         } else {
            System.out.println("Difficult Level Sudoku Game. \n"
                        + "You need to implement more rules to solve it...");
            printGameBoard();
            break;
         }
      }
   }
    
   private void initializeGame() throws FileNotFoundException {
      
      Scanner sodukuFileScanner = new Scanner(new BufferedReader(new FileReader("C:\\Users\\mamil.DESKTOP-TMOJG4N\\OneDrive\\Documents\\NetBeansProjects\\Sudoku Game\\src\\SodukuGame\\SodukuGame.txt")));
      int rows = 9;
      int columns = 9;
      gameBoard = new int[rows][columns];
      while(sodukuFileScanner.hasNextLine())
      {
         for (int[] gameBoard1 : gameBoard) {
            String [] line = sodukuFileScanner.nextLine().trim().split(" ");
            for (int col = 0; col < line.length; col++) {
               gameBoard1[col] = Integer.parseInt(line[col]);
            }
         }
      }
   }
    
   private void printGameBoard(){
        
      for (int[] gameBoard1 : gameBoard) {
         for (int col = 0; col < gameBoard1.length; col++) {
            System.out.print(gameBoard1[col] + " ");
         }
         System.out.println();
      }
      System.out.print("\n");
   }
    
    
   private boolean hasGameBeenSolved(){
        
      for (int[] gameBoard1 : gameBoard) {
         for (int col = 0; col < gameBoard1.length; col++) {
            if (gameBoard1[col] == 0) {
               return false;
            }
         }
      }  
      return true;
   }
    
   private int[] getNextCell(){
        
      for (int row = 0; row < 9; row++) {
         for (int col = 0; col < 9; col++) {
            if (gameBoard[row][col] == 0) {
               Set<Integer> firstRuleElimination = getFirstRuleElimination(row);
               Set<Integer> secondRuleElimination = getSecondRuleElimination(col);
               Set<Integer> thirdRuleElimination = getThirdRuleElimination(row, col);
               Set<Integer> survivors = getSurvivors(firstRuleElimination,
                            secondRuleElimination, thirdRuleElimination);
               if (survivors.size() == 1) {
                  List<Integer> list = new ArrayList(survivors);
                  int uniqueValue = list.get(0);
                  return new int[]{row, col, uniqueValue};
               }
            }
         }
      }
      return null;
   }
    
   private Set<Integer>getFirstRuleElimination(int row){
        
      Set<Integer> setOfRowNumbers = new HashSet();
      for(int number = 0; number < 9; number++){
         int num = gameBoard[row][number];
         if(num > 0)
         {
            setOfRowNumbers.add(num);       
         }           
      }
      System.out.println("Set of row numbers: "+setOfRowNumbers);
      return setOfRowNumbers;
   }
    
   private Set<Integer>getSecondRuleElimination(int col){
      Set<Integer> setOfColumnNumbers = new HashSet();
      for(int number = 0; number < 9; number++){
         int num = gameBoard[number][col];
         if(num > 0)
         {
            setOfColumnNumbers.add(num);       
         }           
      }
      System.out.println("Set of column numbers: "+setOfColumnNumbers);
      return setOfColumnNumbers;
   }
    
   private Set<Integer>getThirdRuleElimination(int row, int col){
        
      Set<Integer> group = new HashSet();
      int[] rows = getRowRangeForGroup(row);
      int[] cols = getColRangeForGroup(col);
      for (int rowArray = rows[0]; rowArray <= rows[1]; rowArray++) {
         for (int colArray = cols[0]; colArray <= cols[1]; colArray++) {
            if(gameBoard[rowArray][colArray] > 0){
               group.add(gameBoard[rowArray][colArray]);
            }  
         }     
      }
      System.out.println("All numbers in 9 cell region: "+group+" ");
      return group;
   }
    
   private Set<Integer> getSurvivors(Set<Integer>firstRuleElimination,Set<Integer>secondRuleElimination,Set<Integer>thirdRuleElimination){
     
      Set<Integer> notAllowed = new HashSet();
      Set<Integer> allowed = new HashSet();
      notAllowed.addAll(firstRuleElimination);
      notAllowed.addAll(secondRuleElimination);
      notAllowed.addAll(thirdRuleElimination);
      System.out.println("Set of not allowed numbers: "+notAllowed);
      for(int goThroughSet = 1; goThroughSet <= 9; goThroughSet++){
             
         if(!(notAllowed.contains(goThroughSet))){
            allowed.add(goThroughSet);
         }
      }
      System.out.println("Set of allowed numbers: "+allowed);
      System.out.println();
      return allowed;   
   }
    
   private int[] getRowRangeForGroup(int row){
        
      int min;
      int max;
        
      if(row == 0 || row == 1 || row == 2){
         min = 0;
         max = 2;
      }
      else if(row == 3|| row == 4 || row ==5){
         min = 3;
         max = 5;
      }
      else{
         min = 6; 
         max = 8;
      } 
      return new int[]{min,max};
   }
    
   private int[] getColRangeForGroup(int col){
        
      int min;
      int max;
        
      if(col == 0 || col == 1 || col == 2){
         min = 0;
         max = 2;
      }
      else if(col == 3|| col == 4 || col ==5){
         min = 3;
         max = 5;
      }
      else{
         min = 6; 
         max = 8;
      } 
      return new int[]{min,max};    }
   
}


    

