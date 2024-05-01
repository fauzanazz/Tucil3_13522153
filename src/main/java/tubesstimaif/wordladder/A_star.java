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
    
    @Override
    public Result solve(String start, String end){
        long startTime = System.nanoTime();
        openList.add(new Node(start));

        while (!openList.isEmpty()) {
            Node current = openList.poll();
            closedList.add(current.getWord());

            if (current.getWord().equals(end)) {
                return new Result((int) ((System.nanoTime() - startTime) / 1000000), (int) ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024), getPath(current), nodeAccessed);
            } else {
                addToOpen(current);
            }
        }
        return null;
    }

    private List<String> getPath(Node n) {
        List<String> path = new ArrayList<>();
        path.add(n.getWord());
        Node parent;
        while ((parent = n.getParent()) != null) {
            path.addFirst(parent.getWord());
            n = parent;
        }
        return path;
    }

    private void addToOpen(Node current) {
        nodeAccessed++;
        List<String> words = MapParser.getWordList(current.getWord());
        for (String word : words ) {
            if (!closedList.contains(word)) {
                Node node = new Node(word, current, current.getG() + 1, hammingDistance(word, end));
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
