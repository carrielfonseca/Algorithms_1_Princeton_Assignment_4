
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedStack;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
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
public class Solver {    
       
    private MinPQ<SearchNode> priorityQueue = new MinPQ<SearchNode>(new SearchNode()); //important to specify the comparator (check later if Comparable is good too
    private MinPQ<SearchNode> priorityQueueTwin = new MinPQ<SearchNode>(new SearchNode()); 
    private LinkedStack<Board> solution = new LinkedStack<Board>();
    private boolean isSolvable;
    private int n; // number of moves;
    
    private class SearchNode implements Comparator<SearchNode> {        
        private Board board;
        private SearchNode previous;  
        private int n;          // number of moves
        
        public int compare(SearchNode a, SearchNode b) {
           int i;
           if ((a.n + a.board.manhattan()) - (b.n + b.board.manhattan()) < 0) {
               i = -1;
           }
           else if ((a.n + a.board.manhattan()) - (b.n + b.board.manhattan()) > 0)  {
               i = 1;
           }
           else {
               i = 0;
           }
        return i;   
       }
    }
    
    public Solver(Board initial)  {
        SearchNode searchNode = new SearchNode();
        searchNode.n = 0;
        searchNode.board = initial;
        // Twin
        SearchNode searchNodeTwin = new SearchNode();
        searchNodeTwin.n = 0;
        searchNodeTwin.board = initial.twin();        
       // Original
        priorityQueue.insert(searchNode);      
        SearchNode oldSearchNode = new SearchNode();
        oldSearchNode.board = initial;
        // Twin
        priorityQueueTwin.insert(searchNodeTwin);      
        SearchNode oldSearchNodeTwin = new SearchNode();
        oldSearchNodeTwin.board = initial.twin();
        
        
        int i = 0;
        while (oldSearchNode.board.manhattan() != 0 && oldSearchNodeTwin.board.manhattan() !=0) {
            if (i % 2 == 0) {
                oldSearchNode =  priorityQueue.delMin();                
                Iterator<Board> it = oldSearchNode.board.neighbors().iterator();
                while (it.hasNext()) {
                    searchNode = new SearchNode();
                    searchNode.board = it.next();
                    searchNode.previous = oldSearchNode;
                    searchNode.n = oldSearchNode.n + 1;           
                    if (oldSearchNode.previous != null && !oldSearchNode.previous.board.equals(searchNode.board)) {
                        priorityQueue.insert(searchNode);
                    }  
                    else if (oldSearchNode.previous == null) {
                        priorityQueue.insert(searchNode);                    
                    }
                    //System.out.println(searchsNode.board);            
                }   
                
            }
            else {
                oldSearchNodeTwin =  priorityQueueTwin.delMin();
                Iterator<Board> it = oldSearchNodeTwin.board.neighbors().iterator();
                while (it.hasNext()) {
                    searchNodeTwin = new SearchNode();
                    searchNodeTwin.board = it.next();
                    searchNodeTwin.previous = oldSearchNodeTwin;
                    searchNodeTwin.n = oldSearchNodeTwin.n + 1;           
                    if (oldSearchNodeTwin.previous != null && !oldSearchNodeTwin.previous.board.equals(searchNodeTwin.board)) {
                        priorityQueueTwin.insert(searchNodeTwin);
                    }  
                    else if (oldSearchNodeTwin.previous == null) {
                        priorityQueueTwin.insert(searchNodeTwin);                    
                    }                        
                }   
                /*System.out.println("Manhattan is: ");
                    System.out.println(oldSearchNode.board.manhattan());
                System.out.println("n is: ");
                    System.out.println(oldSearchNode.n);  
                */
            }
            i++;
        }
        
        if (oldSearchNodeTwin.board.manhattan() == 0) {
            isSolvable = false;
        }       
        else {
            isSolvable = true;
            n = oldSearchNode.n;
            // puts the solution in the queue
            while(oldSearchNode != null) {
                solution.push(oldSearchNode.board);
                //System.out.println(oldSearchNode.board);
                oldSearchNode = oldSearchNode.previous;
            }
        }       
    }
    
    public boolean isSolvable() {
       return isSolvable;
    }
    
    public int moves() {
        int i;
        if (isSolvable) {
            i = n;
        }
        else  {
           i = -1;
        }
        return i;        
    }
    
    public Iterable<Board> solution()  {
        if (isSolvable()) {
           return solution;  
        }
        else {
            return null;
        }              
    }
    
    
     public static void main(String[] args) {
         int[][] block = new int[2][2];
         block[0][0] = 2;
         block[0][1] = 0;
         
         block[1][0] = 1;
         block[1][1] = 3;
         
         Board initial = new Board(block);
          // solve the puzzle
        Solver solver = new Solver(initial);
         System.out.println("moves is " + solver.moves());
         
         // prints the path to the solution
        Iterator<Board> it;
         it = solver.solution().iterator();
         
         while(it.hasNext()) {
             System.out.println(it.next());
         }
         
         
        /*int[][] block = new int[3][3];       
        block[0][0] = 8;
        block[0][1] = 4;
        block[0][2] = 2;
        
        block[1][0] = 6; 
        block[1][1] = 5; 
        block[1][2] = 3; 
        
        block[2][0] = 7; 
        block[2][1] = 0; 
        block[2][2] = 1; 
        */
        /*
        Board board = new Board(block); 
        */
        /*
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }   
                */
    }
}
