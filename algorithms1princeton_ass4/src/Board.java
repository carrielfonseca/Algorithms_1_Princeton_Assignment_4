
import edu.princeton.cs.algs4.LinkedStack;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Fabio
 */
public class Board /*implements Comparator<Board> */ {
    private final int[][] blocks;
    private final int dim; 
    
    
    public Board(int[][] blocks)    {       // construct a board from an n-by-n array of blocks
        this.blocks = copyArray(blocks);    // (where blocks[i][j] = block in row i, column j)
        dim = blocks.length;        
    }                                         

    @Override
    public String toString() {
        String s = dim + "\n";
        for(int i = 0; i < blocks.length; i++) {
            for(int j = 0; j < blocks.length; j++) {
                s = s + blocks[i][j] + " ";          
            }
            s = s + "\n";
        }
        return s;
    }
    
    public int dimension()  {
        return dim;
    }
    
    public int hamming()  {
        int n = 0;
        for(int i = 0; i < blocks.length; i++) {
            for(int j = 0; j < blocks.length; j++) {
                if (isInRightPlace(i, j) == false && blocks[i][j] != 0) {
                    n++;                    
                }
            }            
        } 
        return n;
    }
    
    public int manhattan() {
        int n = 0;
        int rowDistance;
        int colDistance;
        
        for(int i = 0; i < blocks.length; i++) {
            for(int j = 0; j < blocks.length; j++) {
                rowDistance = Math.abs(rowOfElement(blocks[i][j]) - (i+1));   
                colDistance = Math.abs(colOfElement(blocks[i][j]) - (j+1));    
                if (blocks[i][j] != 0) {
                    n = n + rowDistance + colDistance;
                }
            }            
        } 
        return n;      
    }
    
    public boolean isGoal() {
        boolean b = false;
        for(int i = 0; i < blocks.length; i++) {
            for(int j = 0; j < blocks.length; j++) {
                if (isInRightPlace(i, j) == false)  {
                    return false;
                }
            }
        }
        b = true;
        return b;
    }
   
    public Board twin()   {
       Board twin; 
       int col1, col2;
       if (blocks[0][0] != 0)  {
          col1 = 0;
       }
       else {
           col1 = 1;
       }
       if (blocks[1][0] != 0)  {
          col2 = 0;
       }
       else {
           col2 = 1;
       }
       exchange(blocks, 0, col1, 1, col2); 
       twin   = new Board(blocks);
       exchange(blocks, 0, col1, 1, col2);  //goes back to the original
       return twin;
    }
            
    
    
   public boolean equals(Object obj) {
        boolean b = true;
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Board y = (Board) obj;
        if (dim == y.dimension()) {
            for(int i = 0; i < blocks.length; i++) {
                for(int j = 0; j < blocks.length; j++) {
                    if (blocks[i][j] != y.blocks[i][j]) {  
                        b = false;
                        break;
                    }
                }
            }
           
        }
        else {
            b = false;
        }
        return b;      
    }
    
    
    //finds where the zero is and then put all the nei
    public Iterable<Board> neighbors() {
        LinkedStack<Board> neighbors = new LinkedStack<Board>();
        Board nb;
        int[][] blockOfNeighbor = copyArray(blocks);
        int indexRowOfZero = 0, indexColOfZero = 0;        
        for(int i = 0; i < blocks.length; i++) {
                for(int j = 0; j < blocks.length; j++) {
                    if (blocks[i][j] ==0) {                        
                        indexRowOfZero = i;
                        indexColOfZero = j;
                        break;
                    }
                }
        }      
        if ((indexRowOfZero - 1) >= 0 ) {
            nb = new Board(exchange(blocks, indexRowOfZero,indexColOfZero,indexRowOfZero - 1,indexColOfZero));
            neighbors.push(nb);
            exchange(blocks, indexRowOfZero,indexColOfZero,indexRowOfZero - 1,indexColOfZero);
        }
        if ((indexRowOfZero + 1) < blockOfNeighbor.length ) {
            nb = new Board(exchange(blocks, indexRowOfZero,indexColOfZero,indexRowOfZero + 1,indexColOfZero));
            neighbors.push(nb);
            exchange(blocks, indexRowOfZero,indexColOfZero,indexRowOfZero + 1,indexColOfZero);
        }
       if ((indexColOfZero - 1) >= 0 ) {
            nb = new Board(exchange(blockOfNeighbor, indexRowOfZero,indexColOfZero,indexRowOfZero, indexColOfZero - 1));
            neighbors.push(nb);
            exchange(blockOfNeighbor, indexRowOfZero,indexColOfZero,indexRowOfZero, indexColOfZero - 1);
        }
        if ((indexColOfZero + 1) < blockOfNeighbor.length ) {
            nb = new Board(exchange(blockOfNeighbor, indexRowOfZero,indexColOfZero,indexRowOfZero ,indexColOfZero + 1));
            neighbors.push(nb);
            exchange(blockOfNeighbor, indexRowOfZero,indexColOfZero,indexRowOfZero ,indexColOfZero + 1);
        }        
        return neighbors;
    }
    
    // exchanges the element i,j with the element k,l in the matrix x
    private int[][] exchange(int[][] x, int i, int j, int k, int l) {
        int temp;
        temp = x[i][j];
        x[i][j] = x[k][l];
        x[k][l] = temp;
        return x;
    }   

    private int[][] copyArray(int[][] x) {
        int[][] xCopy = new int[x.length][x.length];
        for(int i = 0; i < x.length; i++) {
            for(int j = 0; j < x.length; j++) {
                xCopy[i][j] = x[i][j];                
            }            
        }
        return xCopy;
    }    
    
    private boolean isInRightPlace(int i, int j) {
        boolean b;  
        int rightElement;
        if (i == (dim - 1) && j == (dim - 1)) {
            rightElement  = 0;
        }  
        else {
            rightElement  = i * dim + (j+1);
        }        
        if (blocks[i][j] == rightElement) b = true;
        else     b = false;
        return b;             
    }
    
    private int rowOfElement(int x) {
        int row;
        if (x % dim == 0) {
            row = x / dim;
        }
        else {
            row = x / dim + 1;               
        }
        return row; 
    }
    
    private int colOfElement(int x) {
        int col;
        if ( x % dim == 0) {
            col = dim;
        }
        else {
            col = x % dim;    
        }       
        return col;       
    }
    
    
    public static void main(String[] args) {
      /*  int[][] block = new int[3][3];
        block[0][0] = 1;
        block[0][1] = 2;
        block[0][2] = 3;
        
        block[1][0] = 7; // 1
        block[1][1] = 0; //
        block[1][2] = 8; // 2
        
        block[2][0] = 4; // 1
        block[2][1] = 5; // 1
        block[2][2] = 6; // 1
        
        Board board = new Board(block); 
        Board board2 = new Board(block);
        System.out.println(board);
        board.exchange(board.blocks, 0, 0, 0, 1);   
              */
                    
    }
    
            
}