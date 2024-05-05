package tubesstimaif.wordladder;

import java.util.*;

/**
 * UCS Algorithm
 * This class is used to solve the word ladder using Uniform Cost Search Algorithm
 * This class implements Solver interface
 * @see Solver
 * @author Ojan
 */
class UCS implements Solver {

    /*
    * openNodes is a priority queue that stores the nodes that are currently being processed
    * closedNodes is a set that stores the nodes that have been processed
     */
    private final Queue<Node> openNodes;
    private final Set<String> closedNodes;

    /**
     * Constructor for UCS
     */
    public UCS() {
        this.closedNodes = new HashSet<>();
        this.openNodes = new LinkedList<>();
    }

    /**
     * Solve the word ladder from start to end using UCS Algorithm
     * @param start Start Word
     * @param end End Word
     * @return Result containing path, execution time, memory used, and node accessed
     */
    public Result solve(String start, String end) {
        System.gc();
        long timeStart = System.nanoTime();
        int memoryStart = (int) ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024);

        openNodes.add(new Node(start));

        while (!openNodes.isEmpty()) {
            Node current = openNodes.poll();

            if (current.getWord().equals(end)){
                int memory = Result.getMemoryUsed(memoryStart);
                return new Result(Result.getExecutionTime(timeStart), memory, Node.getPath(current) , closedNodes.size());
            }

            else {
                closedNodes.add(current.getWord());
                processNode(current);
            }
        }

        int memory = Result.getMemoryUsed(memoryStart);
        return new Result(Result.getExecutionTime(timeStart), memory, null , closedNodes.size());
    }

    /**
     * Process the node by adding the next possible words to the openNodes
     * @param n Node to be processed
     */
    private void processNode(Node n) {
        List<String> nextProcessedWords = Parser.getWordList(n.getWord());
        for (String nextWord : nextProcessedWords) {
            if (closedNodes.contains(nextWord)) continue;
            Node nextNode = new Node(nextWord, n, 0, 0);
            openNodes.add(nextNode);
        }
    }
}