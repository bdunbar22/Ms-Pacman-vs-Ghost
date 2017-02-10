package pacman.pathFinding;

import pacman.game.Constants;
import pacman.game.internal.Node;

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
    private int heuristic;

    public Vertex(int index, int x, int y, int[] neighbors) {
        this.index = index;
        this.xPosition = x;
        this.yPosition = y;
        this.previous = null;
        this.distance = Integer.MAX_VALUE;
        this.neighbors = neighbors;
        this.heuristic = 0;
    }

    // Easily create a vertex from a node.
    public Vertex(Node n) {
        int[] neighbors = new int[4];
        if(n.neighbourhood.get(Constants.MOVE.UP) != null) {
            neighbors[0] = n.neighbourhood.get(Constants.MOVE.UP);
        } else {
            neighbors[0] = -1;
        }
        if(n.neighbourhood.get(Constants.MOVE.RIGHT) != null) {
            neighbors[1] = n.neighbourhood.get(Constants.MOVE.RIGHT);
        } else {
            neighbors[1] = -1;
        }
        if(n.neighbourhood.get(Constants.MOVE.DOWN) != null) {
            neighbors[2] = n.neighbourhood.get(Constants.MOVE.DOWN);
        } else {
            neighbors[2] = -1;
        }
        if(n.neighbourhood.get(Constants.MOVE.LEFT) != null) {
            neighbors[3] = n.neighbourhood.get(Constants.MOVE.LEFT);
        } else {
            neighbors[3] = -1;
        }

        this.index = n.nodeIndex;
        this.xPosition = n.x;
        this.yPosition = n.y;
        this.previous = null;
        this.distance = Integer.MAX_VALUE;
        this.neighbors = neighbors;
        this.heuristic = 0;
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

    public int getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
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
        public int compare(Vertex v1, Vertex v2) {
            if(v1.getDistance() > v2.getDistance()) {
                return 1;
            } else if(v1.getDistance() < v2.getDistance()){
                return -1;
            } else if(v1.getIndex() > v2.getIndex()) {
                return 1;
            } else {
                return -1;
            }
        }
    };

    public static Comparator<Vertex> VertexDistanceWithHeuristicComparator = new
        Comparator<Vertex>() {
        public int compare(Vertex v1, Vertex v2) {
            if(v1.getDistance() + v1.getHeuristic() >
                v2.getDistance() + v2.getHeuristic()) {
                return 1;
            } else if(v1.getDistance() + v1.getHeuristic() <
                v2.getDistance() + v2.getHeuristic()){
                return -1;
            } else if(v1.getIndex() > v2.getIndex()) {
                return 1;
            } else {
                return -1;
            }
        }
    };
}
