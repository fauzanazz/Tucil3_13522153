/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tubesstimaif.wordladder;

import java.util.List;

/**
 *
 * @author Ojan
 */
public class Result {
    public int executionTime;
    public int memory;
    public List<String> path;
    public int nodeAccessed;

    public Result(int executionTime, int memory, List<String> path, int nodeAccessed){
        this.executionTime = executionTime;
        this.memory = memory;
        this.path = path;
        this.nodeAccessed = nodeAccessed;
    }

    static int getExecutionTime(long timeStart) {
        return (int) ((System.nanoTime() - timeStart) / 1000000);
    }

    static int getMemoryUsed(int memoryStart) {
        return (int) ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024) - memoryStart;
    }
}
