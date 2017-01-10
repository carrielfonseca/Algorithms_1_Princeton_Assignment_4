
import edu.princeton.cs.algs4.MinPQ;
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
public class SolverNoTwin {    
       
    private MinPQ priorityQueue = new MinPQ(new SearchNode()); //important to specify the comparator (check later if Comparable is good too
    
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
    
    public SolverNoTwin(Board initial)  {
        SearchNode searchNode = new SearchNode();
        searchNode.n = 0;
        searchNode.board = initial;
       // searchNode.previous = null; 
        if (searchNode != null) {
        priorityQueue.insert(searchNode);
        }
        SearchNode oldSearchNode = new SearchNode();
        oldSearchNode.board = initial;
        
        while (oldSearchNode.board.manhattan() != 0) {

            oldSearchNode = (SearchNode) priorityQueue.delMin();
            System.out.println(oldSearchNode.board);
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
            System.out.println("Manhattan is: ");
                System.out.println(oldSearchNode.board.manhattan());
            System.out.println("n is: ");
                System.out.println(oldSearchNode.n);    
        }
        
        System.out.println("the trace is");
        while(oldSearchNode != null) {
            System.out.println(oldSearchNode.board);
            oldSearchNode = oldSearchNode.previous;
        }
        
        
        //oldSearchNode = (SearchNode) priorityQueue.delMin();
    }
    
    
     public static void main(String[] args) {
        int[][] block = new int[3][3];
        block[0][0] = 1;
        block[0][1] = 2;
        block[0][2] = 3;
        
        block[1][0] = 7; // 1
        block[1][1] = 0; //
        block[1][2] = 8; // 2
        
        block[2][0] = 4; // 1
        block[2][1] = 5; // 1
        block[2][2] = 6; // 1
        


/*
        
        block[0][0] = 4;
        block[0][1] = 8;
        block[0][2] = 2;
        
        block[1][0] = 3; 
        block[1][1] = 6; 
        block[1][2] = 5; 
        
        block[2][0] = 1; 
        block[2][1] = 7; 
        block[2][2] = 0; 
        
        */
        
        Board board = new Board(block); 
        
        //System.out.println(board); 
        Iterator<Board> it = board.neighbors().iterator();
       /* while (it.hasNext()) {
            System.out.println(it.next());
        } 
               */
        
        SolverNoTwin solver = new SolverNoTwin(board);
        
        
                       
    }
}
