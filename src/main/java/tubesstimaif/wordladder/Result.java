/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tubesstimaif.wordladder;

import java.util.ArrayList;
import java.util.List;

/**
 * Kelas yang digunakan untuk menyimpan hasil dari algoritma
 * Hasil ini berisi waktu eksekusi, memory yang digunakan, path yang ditemukan, dan node yang diakses
 * @author Ojan
 */
public class Result {
    public int executionTime;
    public int memory;
    public List<String> path;
    public int nodeAccessed;

    /**
     * Konstruktor Result
     */
    public Result(){
        this.executionTime = 0;
        this.memory = 0;
        this.path = new ArrayList<>();
        this.nodeAccessed = 0;
    }

    /**
     * Konstruktor Result
     * @param executionTime Waktu eksekusi
     * @param memory Memory yang digunakan
     * @param path Path yang ditemukan
     * @param nodeAccessed Node yang diakses
     */
    public Result(int executionTime, int memory, List<String> path, int nodeAccessed){
        this.executionTime = executionTime;
        this.memory = memory;
        this.path = path;
        this.nodeAccessed = nodeAccessed;
    }

    /**
     * Mendapatkan waktu eksekusi
     * @return Waktu eksekusi
     */
    static int getExecutionTime(long timeStart) {
        return (int) ((System.nanoTime() - timeStart) / 1000000);
    }

    /**
     * Mendapatkan memory yang digunakan
     * @param memoryStart Memory awal
     * @return Memory yang digunakan
     */
    static int getMemoryUsed(int memoryStart) {
        return (int) ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024) - memoryStart;
    }
}
