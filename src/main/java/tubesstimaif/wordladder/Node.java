package tubesstimaif.wordladder;

public class Node {
    private final String word;
    private Node parent;
    private final double f;
    private final double g;
    private final double h;

    public Node(String word) {
        this.word = word;
        this.parent = null;
        this.f = 0;
        this.g = 0;
        this.h = 0;
    }

    public Node(String word,Node parent, double g, double h){
        this.word = word;
        this.parent = parent;
        this.g = g;
        this.h = h;
        this.f = g + h;
    }

    public String getWord(){
        return word;
    }

    public double getF(){
        return f;
    }

    public double getG(){
        return g;
    }

    public double getH() {
        return h;
    }

    public Node getParent(){
        return parent;
    }

    public void setParent(Node parent){
        this.parent = parent;
    }


}
