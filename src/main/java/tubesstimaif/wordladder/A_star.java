/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tubesstimaif.wordladder;

import java.util.*;

/**
 * Kelas yang merepresentasikan algoritma A* untuk menyelesaikan word ladder
 * Algoritma ini menggunakan priority queue untuk menyimpan node-node yang akan diakses
 * dan hashset untuk menyimpan node-node yang sudah diakses
 * Algoritma ini menggunakan fungsi heuristik Hamming Distance untuk menghitung nilai h(n)
 * @author Ojan
 */
public class A_star implements Solver {
    /*
     * openList adalah priority queue yang menyimpan node-node yang akan diakses
     * closedList adalah set yang menyimpan node-node yang sudah diakses
     * end adalah kata akhir yang ingin dicapai
     */
    private final PriorityQueue<Node> openList;
    private final Set<String> closedList;
    private String end;

    /**
     * Constructor untuk A*
     */
    public A_star() {
        this.openList = new PriorityQueue<>(Comparator.comparingDouble(Node::getF));
        this.closedList = new HashSet<>();
    }

    /**
     * Menyelesaikan word ladder dari start ke end menggunakan algoritma A*
     * @param start String kata awal
     * @param end String kata akhir
     * @return Result yang berisi path, waktu eksekusi, memory yang digunakan, dan node yang diakses
     */
    @Override
    public Result solve(String start, String end){
        // Setup
        System.gc();
        this.end = end;
        int memoryStart = (int) ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024);
        long startTime = System.nanoTime();

        // Start
        openList.add(new Node(start));

        while (!openList.isEmpty()) {
            Node current = openList.poll();
            closedList.add(current.getWord());

            if (current.getWord().equals(end)) {
                int memoryUsed = Result.getMemoryUsed(memoryStart);
                return new Result(Result.getExecutionTime(startTime), memoryUsed, Node.getPath(current), closedList.size());
            } else {
                ProcessNode(current);
            }
        }
        int memoryUsed = Result.getMemoryUsed(memoryStart);
        return new Result(Result.getExecutionTime(startTime), memoryUsed, null, closedList.size());
    }

    /**
     * Menambahkan node-node yang dapat diakses dari node current ke openList
     * @param n Node yang sedang diproses
     */
    private void ProcessNode(Node n) {
        List<String> words = Parser.getWordList(n.getWord());
        for (String word : words ) {
            if (!closedList.contains(word)) {
                Node node = new Node(word, n, n.getG() + 1, OtherAlgorithm.hammingDistance(word, end));
                openList.add(node);
            }
        }
    }
}
