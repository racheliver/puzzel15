package com.chenp.puzzle15;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;
import java.util.*;

public class GameBoard {
    private static final int BOARD_SIZE = 4;
    private int[][] board;
    private static final int nbTiles = (BOARD_SIZE * BOARD_SIZE) - 1;
    private static final int DIMENSION = (BOARD_SIZE * BOARD_SIZE);
    private int[] tilesList = new int[16] ;
    private Boolean gameOver = true;

    public GameBoard() {
        board = new int[BOARD_SIZE][BOARD_SIZE];
        init();
        scramble();
    }

    //public void shuffle(){}

    public int getNum(int row, int col){
        return board[row][col];
    }

    public boolean isGameOver()
    {
        int count=0;
        for(int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if(i==3 && j==3 &&  board[3][3] !=0)
                   return false;
                else if(board[i][j] == i*4+(j+1))
                   count++;
            }
            if(count == 15)
                return true;
        }
        return false;
    }


    public void scramble() {
        do{
            int n = DIMENSION;
            Random random = new Random();
            while (n > 1) {
                int r = random.nextInt(n--);
                int tmp = tilesList[r];
                tilesList[r] = tilesList[n];
                tilesList[n] = tmp;
            }
        }while (!isSolvable(tilesList));

        for(int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = tilesList[i*4+j];
            }
        }
    }

    public void init() {
        //tilesList = new Integer[DIMENSION];
        for (int i = 0; i < DIMENSION ; i++) {
            tilesList[i] = i;
        }
    }



    public boolean isSolvable(int[] puzzle)
    {
        int parity = 0;
        int gridWidth = (int) Math.sqrt(puzzle.length);
        int row = 0; // the current row we are on
        int blankRow = 0; // the row with the blank tile

        for (int i = 0; i < puzzle.length; i++)
        {
            if (i % gridWidth == 0) { // advance to next row
                row++;
            }
            if (puzzle[i] == 0) { // the blank tile
                blankRow = row; // save the row on which encountered
                continue;
            }
            for (int j = i + 1; j < puzzle.length; j++)
            {
                if (puzzle[i] > puzzle[j] && puzzle[j] != 0)
                {
                    parity++;
                }
            }
        }

        if (gridWidth % 2 == 0) { // even grid
            if (blankRow % 2 == 0) { // blank on odd row; counting from bottom
                return parity % 2 == 0;
            } else { // blank on even row; counting from bottom
                return parity % 2 != 0;
            }
        } else { // odd grid
            return parity % 2 == 0;
        }
    }


/*
    public boolean isSolvable() {
        int countInversions = 0;

        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < i; j++) {
                if ((tilesList[j])> (tilesList[i]))
                    countInversions++;
            }
        }
        return (countInversions % 2 == 0);
    }

    public boolean isSolved() {
        if (tilesList[DIMENSION-1] != 0) // if blank tile is not in the solved position ==> not solved
            return false;

        for (int i = nbTiles - 1; i >= 0; i--) {
            if (tilesList[i] != i + 1)
                return false;
        }
        return true;
    }
*/

    public boolean needToSwap(int row, int col){
        if((row-1)>=0 && board[row-1][col]==0)
        { swap(row,col,row-1,col);return true;}
        else if((row+1)<=3 && board[row+1][col]==0)
        { swap(row,col,row+1,col);return true;}
        else if((col-1)>=0 && board[row][col-1]==0)
        { swap(row,col, row, col-1);return true;}
        else if((col+1)<=3 && board[row][col+1]==0)
        { swap(row, col, row, col+1);return true;}
        else{return false;}
    }

    public void swap (int row1, int col1, int row2, int col2)
    {
        int tmp = board[row1][col1];
        board[row1][col1] = board[row2][col2];
        board[row2][col2] = tmp;
    }
}

/*
public static final int BOARD_SIZE = 4;
    public static final int nbTiles = (BOARD_SIZE * BOARD_SIZE) - 1;
    public static final int DIMENSION = (BOARD_SIZE * BOARD_SIZE);


    public int[][] tilesList;
    public TextView txv1, txv2, txv3, txv4, txv5, txv6, txv7, txv8, txv9, txv10, txv11, txv12, txv13, txv14, txv15, txv16;
    public TextView[][] textViews = {   {txv1, txv2, txv3, txv4},
                                        {txv5, txv6, txv7, txv8},
                                        {txv9, txv10, txv11, txv12},
                                        {txv13, txv14, txv15, txv16}};
    public final int TXV_ID[][] = { {R.id.txv1ID, R.id.txv2ID, R.id.txv3ID, R.id.txv4ID},
                                    {R.id.txv5ID, R.id.txv6ID, R.id.txv7ID, R.id.txv8ID},
                                    {R.id.txv9ID, R.id.txv10ID, R.id.txv11ID, R.id.txv12ID},
                                    {R.id.txv13ID, R.id.txv14ID, R.id.txv15ID, R.id.txv16ID}};

    private Boolean gameOver = true;


    public void init() {
        tilesList = new int[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE ; i++) {
            for (int j = 0; j < BOARD_SIZE ; j++) {
                tilesList[i][j] = TXV_ID[i][j];
            }
        }
        Log.d("debug", "in init");
    }

    public void scramble() {
        int n = nbTiles;
        Random random = new Random();

        while (n > 1){
            int r = random.nextInt(n--);
            int tmp = tilesList[r];
            tilesList[n] = tmp;
        }
    }

    public boolean isSolvable() {
        int countInversions = 0;

        for (int i = 0; i < nbTiles; i++) {
            for (int j = 0; j < i; j++) {
                if ((tilesList[j])> (tilesList[i]))
                    countInversions++;
            }
        }
        return countInversions % 2 == 0;
    }

    public boolean isSolved() {
        if (tilesList[DIMENSION-1] != 0) // if blank tile is not in the solved position ==> not solved
            return false;

        for (int i = nbTiles - 1; i >= 0; i--) {
            if (tilesList[i] != i + 1)
                return false;
        }
        return true;
    }






 */