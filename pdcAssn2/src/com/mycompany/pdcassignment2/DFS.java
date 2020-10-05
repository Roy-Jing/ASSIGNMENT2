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

/**
 *
 * @author Roy
 */
public class DFS {
    
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
                
                //search(a);
                
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
