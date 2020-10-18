/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pdcassignment2;

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;


/**
 *
 * @author Roy
 */
public class DFS {
    
    public static void iterativeDepthFirst(){
        
    }
    public static void breadthFirst(Node startNode){
        Queue<Node> queue = new LinkedList();
        queue.add(startNode);
        
        while (!queue.isEmpty()){
            Node cur = queue.poll();
            out.print(cur + "->");
            //cur.visited = true;
            for (Edge e : (ArrayList<Edge>) cur.edgeset){
                if (e.visited == false){
                    
                    e.visited = true;
                    queue.add(e.getOppositeNodeof(cur));
                }
                
            }
            
        }
    }
    public static void search(Node startNode){
        if (!startNode.visited){
            
        startNode.visited = true;
        out.println(startNode + "-->");
        
        for (Edge edge : (ArrayList<Edge>) startNode.edgeset){
            if (edge.visited == false){
                
                edge.visited = true;
                
                //search(edge.getOppositeNodeof());
                
            }
        }
        }
    }
    
        
        public static void main(String args[]){
            Node a = new Node("A");
             Node b = new Node("B");
            
              Node c = new Node("C");
            
               Node d = new Node("D");
                Node e = new Node("E");
            
                
                a.addEdge(b);
                a.addEdge(c);
                d.addEdge(c);
                d.addEdge(b);
                e.addEdge(d);
                e.addEdge(b);
                breadthFirst(a);
                out.println("iterative: ");
                
                Stack<Node> vStack = new Stack();
                 vStack.add(e);
                 LinkedList<Node> visitedNode = new LinkedList();
                 
                 while (!vStack.isEmpty()){
                
                 Node parent = vStack.pop();
                 visitedNode.add(parent);
                 
                 out.println(parent);
                 
                 for (Edge edge : (ArrayList<Edge>) parent.edgeset){
                     Node v = edge.getOppositeNodeof(parent);
                     
                     if (!visitedNode.contains(v)){
                         visitedNode.add(v);
                         vStack.push(v);
                         
                     }
                 }
                 
                 
                
                 }
        }
}

class priorityQ<E extends Comparable> {
   
    Node top;
    Node last;
    
   
    public E findMin(){
        return top.elem;
        
    }
    
    public void swap(Node from, Node to){
        Node temp;
        if (from.left == to){
            temp = to.right;
            to.left = from.left;
            to.right = from.right;
            
        } else{
            temp = to.left;
            to.right = from.right;
            to.left = from.left;
        }
        
        from.left = to.left;
            from.right = to.right;
        
    }
    
    public void dequeMin(){
        
    }
    public void downHeapBubbling(Node startNode){
       Node left = startNode.left;
       Node right = startNode.right;
       
       if (left != null){
           
           
           if (right != null){
               if (startNode.compareTo(left) != 0 || startNode.compareTo(right) != 0){
                    if (left.compareTo(right) < 0){
                         if (left.compareTo(startNode) < 0){
                             swap(startNode, left);
                             this.downHeapBubbling(startNode);

                         }
                    } else if (right.compareTo(startNode) < 0){
                        swap(startNode, right);
                        this.downHeapBubbling(startNode);

                    }
               }
               
           } else if (left.compareTo(startNode) < 0)
               swap(startNode, left);
               this.downHeapBubbling(startNode);
           
       }
       
       
    }
    
  
    
    class Node implements Comparable<Node>{
        E elem;
        Node left, right, leftSib;
        
        public int compareTo(Node other){
            return elem.compareTo(other.elem);
            
        }
    } 
}

class Edge{
    boolean visited = false;
    Node v;
    Node e;
    Edge(Node n1, Node n2){
        v = n1;
        e = n2;
    }
    
   
    public Node getOppositeNodeof(Node from){
        return v == from ? e : v;
        
    }
}
class Node<E>{
    E elem;
    ArrayList<Edge> edgeset = new ArrayList<Edge>();

     public String toString(){
        return elem.toString();
    }
    public void addEdge(Node e){
        Edge undirected = new Edge(this, e);
        edgeset.add(undirected);
        e.edgeset.add(undirected);
        
        
    }
    boolean visited = false;
    Node(E elem){
        this.elem = elem;
    }
    
    
}
