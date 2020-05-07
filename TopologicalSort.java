package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class TopologicalSort {

    int numberOfVertices;
    static LinkedList<Integer> adjacencyListForNumber[];
    static LinkedHashMap<Character, List<Character>> adjacencyListForChar = new LinkedHashMap<>();
    static LinkedHashMap<Character, Boolean> vertexVisit = new LinkedHashMap<>();
    static LinkedHashMap<Character, Integer> inDegree = new LinkedHashMap<>();
    static LinkedList<Character> charList = new LinkedList<>();
    private boolean hasCycle = false;
    private int cycleAtVertex;

    TopologicalSort(int vertices) {
        this.numberOfVertices = vertices;
        adjacencyListForNumber = new LinkedList[numberOfVertices];
        for (int n = 0; n < numberOfVertices; n++) {
            adjacencyListForNumber[n] = new LinkedList<>();
        }
    }

    public void addCharEdge(char vertex1, char vertex2) {
        if (adjacencyListForChar.containsKey(vertex1)) {
            adjacencyListForChar.get(vertex1).add(vertex2);
        } else {
            List<Character> valueList = new ArrayList<>();
            valueList.add(vertex2);
            adjacencyListForChar.put(vertex1, valueList);
        }
        vertexVisit.put(vertex1, false);
    }

    public void dfsTopologicalSortingForGraph1() {
        Stack<Character> dfsStack = new Stack<Character>();
        for (Character a : adjacencyListForChar.keySet()) {
            charList.add(a);
        }
        for (char g : adjacencyListForChar.keySet()) {
            if (!dfsStack.contains(g)) {
                dfs(g, vertexVisit, dfsStack);
            }
        }
        if (!hasCycle) {
            while (!dfsStack.empty()) {
                System.out.print(dfsStack.pop() + " ");
            }
        }
    }

    public void dfs(char vertex, HashMap<Character, Boolean> vertexVisit, Stack<Character> dfsStack) {
        if (!hasCycle) {
            vertexVisit.put(vertex, true);
            List<Character> valueList = new LinkedList<>();
            valueList = adjacencyListForChar.get(vertex);
            for (int e = 0; e < valueList.size(); e++) {
                char n = valueList.get(e);
                if (!valueList.contains(' ')) {
                    if (!vertexVisit.get(n)) {
                        dfs(n, vertexVisit, dfsStack);
                    } else if (vertexVisit.get(n) && dfsStack.search(n) < 0) {
                        hasCycle = true;
                        cycleAtVertex = n;
                        break;
                    }
                } else {
                    continue;
                }
            }
            if (!dfsStack.contains((char) vertex)) {
                dfsStack.push((char) vertex);
            }
        }
    }

    public void bfsTopologicalSortingForGraph1() {
        Queue<Character> inDegreeQueue = new LinkedList<Character>();
        Queue<Character> bfsTopoQueue = new LinkedList<Character>();
        List<Character> valueList = new LinkedList<>();
        List<Character> inDegreeList = new LinkedList<>();
        int totalVertexCount = 0;
        for (Character d : adjacencyListForChar.keySet()) {
            valueList = adjacencyListForChar.get(d);
            if (!valueList.contains(' ')) {
                for (Character r : valueList) {
                    int count = 0;
                    if (!inDegree.containsKey(r)) {
                        count++;
                        inDegree.put(r, count);
                    } else {
                        int value = inDegree.get(r);
                        value++;
                        inDegree.put(r, value);
                    }
                }
            }
        }

        for (Character a : inDegree.keySet()) {
            inDegreeList.add(a);
        }
        for (char t : charList) {
            if (!inDegreeList.contains(t)) {
                inDegreeQueue.add(t);
            }
        }
        while (!inDegreeQueue.isEmpty()) {
            char a = inDegreeQueue.poll();
            bfsTopoQueue.add(a);
            if (!adjacencyListForChar.get(a).contains(' ')) {
                for (char vertex : adjacencyListForChar.get(a)) {
                    int count = inDegree.get(vertex);
                    --count;
                    inDegree.put(vertex, count);
                    if (count == 0) {
                        inDegreeQueue.add(vertex);
                    }
                }
            }
            totalVertexCount++;
        }
        if (totalVertexCount == numberOfVertices) {
            for (char m : bfsTopoQueue) {
                System.out.print(m + " ");
            }
        } else {
            System.out.println("THERE EXISTS A CYCLE IN THE GRAPH");
        }
    }

    public void addNumberEdge(int vertex1, int vertex2) {
        adjacencyListForNumber[vertex1].add(vertex2);
    }

    public void dfsTopologicalSortingForGraph2() {
        Stack stack = new Stack();
        boolean visited[] = new boolean[numberOfVertices];
        boolean isCycleAtVertex = false;
        for (int i = 1; i <= numberOfVertices - 1; i++) {
            if (visited[i] == false && !hasCycle) {
                dfsNumbered(i, visited, stack);
            }
        }
        if (!hasCycle) {
            while (!stack.empty()) {
                System.out.print(stack.pop() + " ");
            }
        } else {
            String cycleStartedAt = "" + cycleAtVertex;
            while (!stack.empty()) {
                if (!isCycleAtVertex) {
                    if (stack.pop().equals(cycleAtVertex)) {
                        isCycleAtVertex = true;
                    }
                } else {
                    cycleStartedAt += "," + stack.pop();
                }
            }
            System.out.println("IN GRAPH 2, CYCLE IS AT : " + cycleStartedAt + " VERTEXES");
        }
    }

    public void dfsNumbered(int vertex, boolean visited[], Stack stack) {
        if (!hasCycle) {
            visited[vertex] = true;
            Iterator<Integer> iterator = adjacencyListForNumber[vertex].iterator();
            while (iterator.hasNext()) {
                int r = iterator.next();
                if (!visited[r]) {
                    dfsNumbered(r, visited, stack);
                } else if (visited[r] && stack.search(r) < 0) {
                    hasCycle = true;
                    cycleAtVertex = r;
                    break;
                }
            }
            stack.push((int) vertex);
        }
    }

    public void bfsTopologicalSortingForGraph2() {
        Queue<Integer> inDegreeQueue = new LinkedList<Integer>();
        Queue<Integer> bfsTopoQueue = new LinkedList<Integer>();
        int totalVertexCount = 0;
        int inDegree[] = new int[numberOfVertices];
        for (int i = 1; i < numberOfVertices; i++) {
            for (int vertex : adjacencyListForNumber[i]) {
                inDegree[vertex]++;
            }
        }
        for (int n = 1; n < numberOfVertices; n++) {
            if (inDegree[n] == 0)
                inDegreeQueue.add(n);
        }
        while (!inDegreeQueue.isEmpty()) {
            int a = inDegreeQueue.poll();
            bfsTopoQueue.add(a);
            for (int vertex : adjacencyListForNumber[a]) {
                if (--inDegree[vertex] == 0)
                    inDegreeQueue.add(vertex);
            }
            totalVertexCount++;
        }
        if (totalVertexCount == numberOfVertices) {
            for (int g : bfsTopoQueue) {
                System.out.print(g + " ");
            }
        } else {
            System.out.println("CYCLE IS PRESENT IN GRAPH 2");
        }
    }

    public static void main(String[] args) {
        TopologicalSort graph1 = new TopologicalSort(14);

        graph1.addCharEdge('M', 'Q');
        graph1.addCharEdge('M', 'R');
        graph1.addCharEdge('M', 'X');
        graph1.addCharEdge('N', 'O');
        graph1.addCharEdge('N', 'Q');
        graph1.addCharEdge('N', 'U');
        graph1.addCharEdge('O', 'R');
        graph1.addCharEdge('O', 'S');
        graph1.addCharEdge('O', 'V');
        graph1.addCharEdge('P', 'O');
        graph1.addCharEdge('P', 'S');
        graph1.addCharEdge('P', 'Z');
        graph1.addCharEdge('Q', 'T');
        graph1.addCharEdge('R', 'U');
        graph1.addCharEdge('R', 'Y');
        graph1.addCharEdge('S', 'R');
        graph1.addCharEdge('T', ' '); /* Represents No Out-going Edges */
        graph1.addCharEdge('U', 'T');
        graph1.addCharEdge('V', 'W');
        graph1.addCharEdge('V', 'X');
        graph1.addCharEdge('W', 'Z');
        graph1.addCharEdge('X', ' '); /* Represents No Out-going Edges */
        graph1.addCharEdge('Y', 'V');
        graph1.addCharEdge('Z', ' '); /* Represents No Out-going Edges */

        System.out.print("DEPTH-FIRST-SEARCH TOPOLOGICAL ORDER FOR GRAPH 1 : ");
        graph1.dfsTopologicalSortingForGraph1();
        System.out.println(" ");
        System.out.print("BREADTH-FIRST-SEARCH TOPOLOGICAL ORDER FOR GRAPH 1 : ");
        graph1.bfsTopologicalSortingForGraph1();

        TopologicalSort graph2 = new TopologicalSort(9);

        graph2.addNumberEdge(1, 2);
        graph2.addNumberEdge(1, 5);
        graph2.addNumberEdge(1, 6);
        graph2.addNumberEdge(2, 3);
        graph2.addNumberEdge(2, 5);
        graph2.addNumberEdge(2, 7);
        graph2.addNumberEdge(3, 4);
        graph2.addNumberEdge(4, 5);
        graph2.addNumberEdge(5, 7);
        graph2.addNumberEdge(5, 8);
        graph2.addNumberEdge(6, 5);
        graph2.addNumberEdge(6, 8);
        graph2.addNumberEdge(7, 4);
        graph2.addNumberEdge(7, 8);
        System.out.println("");
        graph2.dfsTopologicalSortingForGraph2();
        graph2.bfsTopologicalSortingForGraph2();
    }
}