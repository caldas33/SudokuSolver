package org.example;

import java.util.Scanner;

/**
 * @author Duarte Cardoso
 * Java sudoku solver using backtracking logic.
 * Using these main methods:
 * -> sudokuSolver (boolean) which applies the other methods and runs the board
 * -> isValid (boolean) which runs all the validations for the given cell
 * -> validateRow (boolean) which runs the validations for the rows
 * -> validateColumn (boolean) which runs the validations for the columns
 * -> validateGroup (boolean) which runs the validations for the 9 cell group
 */
public class SudokuSolver {
    public static Scanner inputScanner = new Scanner(System.in);
    public static Integer START_INDEX = 0;
    public static Integer BOARD_SIZE = 9;
    public static Integer SMALLEST_NUMBER = 1;
    public static Integer BIGGEST_NUMBER = 9;

    public static void main(String[] args) {

        int[][] board = {
                {5, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        System.out.println("Welcome to sudoku solver. \n 0. Create and solve custom sudoku \n 1. Solve example sudoku");
        if (inputScanner.nextInt() == 0) {
            board = createCustomSudoku();
        }

        if (sudokuSolver(board)) {
            showBoard(board);
        } else {
            System.out.println("No solution exists.");
        }
    }

    /**
     * Utilitarian method to create your own custom sudoku board -> might not be the best approach,
     * but it gets the job done
     */
    public static int[][] createCustomSudoku() {

        int[][] returnableBoard = new int[BOARD_SIZE][BOARD_SIZE];

        for (int row = START_INDEX; row < BOARD_SIZE; row++) {
            for (int column = START_INDEX; column < BOARD_SIZE; column++) {
                showBoard(returnableBoard);
                System.out.println("Input next digit please");
                returnableBoard[row][column] = inputScanner.nextInt();
            }
        }

        return returnableBoard;
    }

    /**
     * Utilitarian method to demonstrate either the current state of the board
     * (when creating a custom one) or to show the finished board result
     */
    public static void showBoard(int[][] board) {
        for (int row = START_INDEX; row < BOARD_SIZE; row++) {
            for (int column = START_INDEX; column < BOARD_SIZE; column++) {
                System.out.print(board[row][column] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Main logic with checks for each cell of the board
     */
    public static boolean sudokuSolver(int[][] board) {
        for (int row = START_INDEX; row < BOARD_SIZE; row++) {
            for (int column = START_INDEX; column < BOARD_SIZE; column++) {
                if (board[row][column] == START_INDEX) {
                    for (int currentDigit = SMALLEST_NUMBER; currentDigit <= BIGGEST_NUMBER; currentDigit++) {
                        board[row][column] = currentDigit;
                        if (isValid(board, row, column) && sudokuSolver(board)) {
                            return true;
                        }
                        board[row][column] = START_INDEX;
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check over the three validation methods, in order to check if the given cell passes all the requirements
     */
    private static boolean isValid(int[][] board, int row, int column) {
        return validateRow(board, row) && validateColumn(board, column) && validateGroup(board, row, column);
    }

    /**
     * Validation over if the currentNumber is already present in the given row
     */
    public static boolean validateRow(int[][] board, int row) {
        boolean[] seenRowBooleanValues = new boolean[BOARD_SIZE + 1];
        for (int currentColumn = START_INDEX; currentColumn < BOARD_SIZE; currentColumn++) {
            int currentNumber = board[row][currentColumn];
            if (currentNumber != 0) {
                if (seenRowBooleanValues[currentNumber]) {
                    return false;
                }
                seenRowBooleanValues[currentNumber] = true;
            }
        }
        return true;
    }

    /**
     * Validation over if the currentNumber is already present in the given column
     */
    public static boolean validateColumn(int[][] board, int column) {
        boolean[] seenColumnBooleanValues = new boolean[BOARD_SIZE + 1];
        for (int currentRow = START_INDEX; currentRow < BOARD_SIZE; currentRow++) {
            int currentNumber = board[column][currentRow];
            if (currentNumber != 0) {
                if (seenColumnBooleanValues[currentNumber]) {
                    return false;
                }
                seenColumnBooleanValues[currentNumber] = true;
            }
        }
        return true;
    }

    /**
     * Validation over if the currentNumber is already present in the given group (9 number subset)
     */
    public static boolean validateGroup(int[][] board, int row, int column) {
        boolean[] seenGroupValues = new boolean[BOARD_SIZE + 1];

        int startRow = (row / 3) * 3;
        int startColumn = (column / 3) * 3;

        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startColumn; j < startColumn + 3; j++) {
                int num = board[i][j];
                if (num != 0) {
                    if (seenGroupValues[num]) {
                        return false;
                    }
                    seenGroupValues[num] = true;
                }
            }
        }
        return true;
    }
}
