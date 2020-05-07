package com.company;

import java.util.*;

public class TopoSortDFS {
    static LinkedHashMap<Character, List<Character>> hmap = new LinkedHashMap<>();
    static LinkedHashMap<Character, Boolean> vertexVisited = new LinkedHashMap<>();
    static LinkedList<Character> keylist = new LinkedList<>();
    static LinkedList<Integer> adjacencyList[];
    int numberVertices;
    boolean Cycle = false;
    int cycleVertex;

    TopoSortDFS(int vertices) {
        this.numberVertices = vertices;
        adjacencyList = new LinkedList[numberVertices];
        for (int i = 0; i < numberVertices; i++) {
            adjacencyList[i] = new LinkedList<>();
        }
    }


    public void dfs(char vertex, HashMap<Character, Boolean> d_visit, Stack<Character> d_stack){

        if(!Cycle){
            d_visit.put(vertex, true);
            List<Character> valueList;
            valueList = hmap.get(vertex);
            for (int i = 0; i< valueList.size();i++){
                char a = valueList.get(i);
                if(!valueList.contains(' ')){
                    if(!d_visit.get(a)){
                        dfs(a, d_visit, d_stack);
                    } else if (d_visit.get(a) && d_stack.search(a)<0){
                        Cycle = true;
                        cycleVertex = a;
                        break;
                    }
                } else {
                    continue;
                }
            }
            if (!d_stack.contains((char) vertex)) {
                d_stack.push((char) vertex);
            }
        }
    }

    //add the directed edge and mark the nodes as false for now.
    public void addEdge(char v1, char v2){
        if(hmap.containsKey(v1)){
            hmap.get(v1).add(v2);
        }
        else {
            List<Character> values = new ArrayList<>();
            values.add(v2);
            hmap.put(v1,values);
        }
        vertexVisited.put(v1,false);
    }

    //topological sorting of the graph using stack here
    public void dfsTopologicalSort(){
        Stack<Character> d_stack = new Stack<Character>();
        for(Character n : hmap.keySet()){
            keylist.add(n);
        }
        for(char k : hmap.keySet()){
            if(!d_stack.contains(k)){
                dfs(k, vertexVisited, d_stack);
            }
        }

        if(!Cycle){
            while(!d_stack.empty()){
                System.out.println(d_stack.pop() + " ");
            }
        } else {
            System.out.println("\nCycle Detected! for graph during DFS");
        }
    }



    public static void main(String[] args) {
	// write your code here
        TopoSortDFS graph = new TopoSortDFS(14);
        graph.addEdge('m', 'q');
        graph.addEdge('m', 'r');
        graph.addEdge('m', 'x');
        graph.addEdge('n', 'o');
        graph.addEdge('n', 'q');
        graph.addEdge('n', 'u');
        graph.addEdge('o', 'r');
        graph.addEdge('o', 's');
        graph.addEdge('o', 'v');
        graph.addEdge('p', 'o');
        graph.addEdge('p', 's');
        graph.addEdge('p', 'z');
        graph.addEdge('q', 't');
        graph.addEdge('r', 'u');
        graph.addEdge('r', 'y');
        graph.addEdge('s', 'r');
        graph.addEdge('t', ' '); // This node has no outgoing edges
        graph.addEdge('u', 't');
        graph.addEdge('v', 'w');
        graph.addEdge('v', 'x');
        graph.addEdge('w', 'z');
        graph.addEdge('x', ' '); //Node has no edges
        graph.addEdge('y', 'v');
        graph.addEdge('z', ' ');

        System.out.println("DFS Topological Order - ");
        graph.dfsTopologicalSort(); // graph -1 dfs


        TopoSortDFS graph2 = new TopoSortDFS(8);

        graph2.addEdge('1','2');
        graph2.addEdge('1','5');
        graph2.addEdge('1','6');
        graph2.addEdge('2','3');
        graph2.addEdge('2','5');
        graph2.addEdge('2','7');
        graph2.addEdge('3','4');
        graph2.addEdge('4','5');
        graph2.addEdge('5','7');
        graph2.addEdge('5','8');
        graph2.addEdge('6','5');
        graph2.addEdge('6','8');
        graph2.addEdge('7','4');
        graph2.addEdge('7','8');
        graph2.dfsTopologicalSort(); // dfs for graph 2

    }
}
