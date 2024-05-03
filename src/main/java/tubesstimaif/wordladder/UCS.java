package tubesstimaif.wordladder;

import java.util.*;

class UCS implements Solver {
    private final Queue<List<String>> path;
    private final List<List<String>> result;
    private final Map<String,List<String>> hashMap;
    private final Set<String> visited;

    /**
     * Constructor for UCS
     */
    public UCS() {
        this.hashMap = MapParser.wordList;
        this.visited = new HashSet<>();
        this.path = new LinkedList<>();
        this.result = new ArrayList<>();
    }

    /**
     * Solve the word ladder from start to end using UCS Algorithm
     * @param start Start Word
     * @param end End Word
     * @return Result containing path, execution time, memory used, and node accessed
     */
    public Result solve(String start, String end) {
        System.gc();
        int memoryStart = (int) ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024);
        path.add(new ArrayList<>(Collections.singletonList(start)));
        long timeStart = System.nanoTime();

        while (!path.isEmpty()) {
            List<String> currentPath = path.poll();
            String currentWord = currentPath.getLast();

            if (visited.contains(currentWord)) continue;

            if (currentWord.equals(end)){
                result.add(currentPath);
                break;
            }

            else {
                visited.add(currentWord);
                processNextWords(currentWord, currentPath);
            }
        }

        if ( getShortestPath() == null ){
            System.gc();
            return null;
        }

        int memory = Result.getMemoryUsed(memoryStart);
        Result output = new Result(Result.getExecutionTime(timeStart), memory, getShortestPath(), visited.size());
        return output;
    }

    /**
     * Process the next words from the current word
     * @param currentWord Current Word
     * @param currentPath Current Path
     */
    private void processNextWords(String currentWord, List<String> currentPath) {
        List<String> nextProcessedWords = hashMap.get(currentWord);
        for (String nextWord : nextProcessedWords) {
            if (visited.contains(nextWord)) continue;
            List<String> newPath = new ArrayList<>(currentPath);
            newPath.add(nextWord);
            path.add(newPath);
        }
    }

    /**
     * Get the shortest path from the result
     * @return List of String containing the shortest path
     */
    private List<String> getShortestPath() {
        return result.stream().min(Comparator.comparingInt(List::size)).orElse(null);
    }
}