package tubesstimaif.wordladder;

import java.util.*;

public class GreedyBFS implements Solver {

    private final PriorityQueue<Node> openList;
    private final Set<String> closedList;
    private String end;

    public GreedyBFS() {
        this.closedList = new HashSet<>();
        this.openList = new PriorityQueue<>(Comparator.comparingDouble(Node::getG));
    }

    @Override
    public Result solve(String start, String end) {
        System.gc();
        this.end = end;
        int memoryStart = (int) ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024);
        long startTime = System.nanoTime();

        openList.add(new Node(start, null, A_star.hammingDistance(start, end), 0));

        while (!openList.isEmpty()) {
            Node current = openList.poll();

            if (current.getWord().equals(end)) {
                int memoryUsed = Result.getMemoryUsed(memoryStart);
                return new Result(Result.getExecutionTime(startTime), memoryUsed, getPath(current), closedList.size());
            } else {
                closedList.add(current.getWord());
                ProcessNode(current);
            }
        }
        return null;
    }

    private void ProcessNode(Node current) {
        for (String nextWord : MapParser.wordList.get(current.getWord())) {
            if (!closedList.contains(nextWord)) {
                Node nextNode = new Node(nextWord, current, A_star.hammingDistance(current.getWord(), end), 0);
                openList.add(nextNode);
            }
        }
    }
    private List<String> getPath(Node n) {
        LinkedList<String> path = new LinkedList<>();
        while (n != null) {
            path.addFirst(n.getWord());
            n = n.getParent();
        }
        return path;
    }
}