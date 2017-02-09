package pacman.pathFinding;

import java.util.Comparator;

/**
 * Vertex class to be used to store data for the nodes during path finding.
 *
 * Created by Ben on 2/9/17.
 */
public class Vertex {
    private int index;
    private int xPosition;
    private int yPosition;
    private Vertex previous;
    private int distance;
    private int[] neighbors;

    public Vertex(int index, int x, int y, int[] neighbors) {
        this.index = index;
        this.xPosition = x;
        this.yPosition = y;
        this.previous = null;
        this.distance = Integer.MAX_VALUE;
        this.neighbors = neighbors;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public Vertex getPrevious() {
        return previous;
    }

    public void setPrevious(Vertex previous) {
        this.previous = previous;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int[] getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(int[] neighbors) {
        this.neighbors = neighbors;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            Vertex v = (Vertex) obj;
            return (v.getIndex() == this.getIndex());
        }
        catch (Exception e) {
            return false;
        }
    }

    public static Comparator<Vertex> VertexDistanceComparator = new Comparator<Vertex>() {
        public int compare(Vertex fruit1, Vertex fruit2) {
            if(fruit1.getDistance() > fruit2.getDistance()) {
                return 1;
            } else if(fruit1.getDistance() < fruit2.getDistance()){
                return -1;
            } else if(fruit1.getIndex() > fruit2.getIndex()) {
                return 1;
            } else {
                return -1;
            }
        }
    };
}
