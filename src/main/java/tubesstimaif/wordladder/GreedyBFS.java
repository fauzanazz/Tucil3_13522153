package tubesstimaif.wordladder;

import java.util.*;

public class GreedyBFS implements Solver {

    /*
     * openList adalah priority queue yang menyimpan node-node yang akan diakses
     * closedList adalah set yang menyimpan node-node yang sudah diakses
     * end adalah kata akhir yang ingin dicapai
     */
    private final PriorityQueue<Node> openList;
    private final Set<String> closedList;
    private String end;

    /**
     * Constructor untuk GreedyBFS
     */
    public GreedyBFS() {
        this.closedList = new HashSet<>();
        this.openList = new PriorityQueue<>(Comparator.comparingDouble(Node::getG));
    }

    /**
     * Menyelesaikan word ladder dari start ke end menggunakan algoritma Greedy Best First Search
     * @param start String kata awal
     * @param end String kata akhir
     * @return Result yang berisi path, waktu eksekusi, memory yang digunakan, dan node yang diakses
     */
    @Override
    public Result solve(String start, String end) {
        System.gc();
        this.end = end;
        int memoryStart = (int) ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024);
        long startTime = System.nanoTime();

        openList.add(new Node(start, null, OtherAlgorithm.hammingDistance(start, end), 0));

        while (!openList.isEmpty()) {
            Node current = openList.poll();

            if (current.getWord().equals(end)) {
                int memoryUsed = Result.getMemoryUsed(memoryStart);
                return new Result(Result.getExecutionTime(startTime), memoryUsed, Node.getPath(current), closedList.size());
            } else {
                closedList.add(current.getWord());
                ProcessNode(current);
            }
        }
        return null;
    }

    /**
     * Menambahkan node-node yang dapat diakses dari node current ke openList
     * @param current Node yang sedang diproses
     */
    private void ProcessNode(Node current) {
        for (String nextWord : MapParser.wordList.get(current.getWord())) {
            if (!closedList.contains(nextWord)) {
                Node nextNode = new Node(nextWord, current, OtherAlgorithm.hammingDistance(current.getWord(), end), 0);
                openList.add(nextNode);
            }
        }
    }
}