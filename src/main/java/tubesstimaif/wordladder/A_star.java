/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tubesstimaif.wordladder;

import java.util.*;

/**
 *
 * @author Ojan
 */
public class A_star implements Solver {

    private final PriorityQueue<Node> openList;
    private final Set<String> closedList;
    private final String end;
    private int nodeAccessed;

    public A_star(String end) {
        this.openList = new PriorityQueue<>(Comparator.comparingDouble(Node::getF));
        this.closedList = new HashSet<>();
        this.end = end;
        this.nodeAccessed = 0;
    }

    /**
     * Menyelesaikan word ladder dari start ke end menggunakan algoritma A*
     * @param start String kata awal
     * @param end String kata akhir
     * @return Result yang berisi path, waktu eksekusi, memory yang digunakan, dan node yang diakses
     */
    @Override
    public Result solve(String start, String end){
        System.gc(); // Suggest garbage collection
        long startTime = System.nanoTime();
        openList.add(new Node(start));

        while (!openList.isEmpty()) {
            Node current = openList.poll();
            closedList.add(current.getWord());

            if (current.getWord().equals(end)) {
                Result result = new Result((int) ((System.nanoTime() - startTime) / 1000000), (int) ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024), getPath(current), nodeAccessed);
                current = null;
                System.gc();
                return result;
            } else {
                ProcessNode(current);
            }
        }
        return null;
    }

    /**
     * Mendapatkan path dari node start ke node n
     * @param n Node yang akan dicari pathnya
     * @return List of String yang berisi path dari node start ke node n
     */
    private List<String> getPath(Node n) {
        LinkedList<String> path = new LinkedList<>();
        while (n != null) {
            path.addFirst(n.getWord());
            n = n.getParent();
        }
        return path;
    }

    /**
     * Menambahkan node-node yang dapat diakses dari node current ke openList
     * @param n Node yang sedang diproses
     */
    private void ProcessNode(Node n) {
        nodeAccessed++;
        List<String> words = MapParser.getWordList(n.getWord());
        for (String word : words ) {
            if (!closedList.contains(word)) {
                Node node = new Node(word, n, n.getG() + 1, hammingDistance(word, end));
                openList.add(node);
            }
        }
    }


    /**
     * Menghitung jarak hamming antara dua kata, Fungsi ini digunakan sebagai fungsi heuristik
     * @param word1 Kata ke-1
     * @param word2 Kata ke-2
     * @return jarak hamming antara dua kata
     */
    public static int hammingDistance(String word1, String word2) {
        int count = 0;
        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                count++;
            }
        }
        return count;
    }
}
