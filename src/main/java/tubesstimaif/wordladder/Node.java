package tubesstimaif.wordladder;

import java.util.LinkedList;
import java.util.List;

/**
 * Node class
 * word : the word in the node
 * parent : the parent node of the node
 * f : the total cost of the node
 * g : the cost from the start node to the node
 * h : the heuristic cost from the node to the goal node
 */
public class Node {
    private final String word;
    private Node parent;
    private final double f;
    private final double g;
    private final double h;

    /**
     * Constructor
     * @param word the word in the node
     */
    public Node(String word) {
        this.word = word;
        this.parent = null;
        this.f = 0;
        this.g = 0;
        this.h = 0;
    }

    /**
     * Constructor
     * @param word the word in the node
     * @param parent the parent node of the node
     * @param g the cost from the start node to the node
     * @param h the heuristic cost from the node to the goal node
     */
    public Node(String word,Node parent, double g, double h){
        this.word = word;
        this.parent = parent;
        this.g = g;
        this.h = h;
        this.f = g + h;
    }

    /**
     * Get the word in the node
     * @return the word in the node
     */
    public String getWord(){
        return word;
    }

    /**
     * Get the total cost of the node
     * @return the total cost of the node
     */
    public double getF(){
        return f;
    }

    /**
     * Get the cost from the start node to the node
     * @return the cost from the start node to the node
     */
    public double getG(){
        return g;
    }

    /**
     * Get the heuristic cost from the node to the goal node
     * @return the heuristic cost from the node to the goal node
     */
    public double getH() {
        return h;
    }

    /**
     * Get the parent node of the node
     * @return the parent node of the node
     */
    public Node getParent(){
        return parent;
    }

    /**
     * Set the parent node of the node
     * @param parent the parent node of the node
     */
    public void setParent(Node parent){
        this.parent = parent;
    }

    /**
     * Get the path from the start node to the node
     * @param n the node
     * @return the path from the start node to the node
     */
    public static List<String> getPath(Node n) {
        LinkedList<String> path = new LinkedList<>();
        while (n != null) {
            path.addFirst(n.getWord());
            n = n.getParent();
        }
        return path;
    }
}
