package pacman.Dijkstra;

import pacman.game.internal.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Ben on 2/4/17.
 */
public class Dijkstra {


    public void dijkstraAlgorithm( /* Accept the graph being used, start, and end node */) {
        // read file into a list of nodes
        // We can look at new Game(0) to see how this is done. But we will
        // convert them into our own Vertex class so it has a previous and distance value.

        // create node set Q
        Set<Node> Q = new HashSet<>(); // We don't necessarily need to use node here
        // we need an object with the following fields:
        // OUR_NODE_OBJECT
        //      previous
        //      distance
        //      List<OUR_NODE_OBJECT> neighbors



        // for each node:
        // set dist[v] to infinity
        // set prev[v] to undefined
        // add node to Q

        // set distance of start node to 0

        // While Q is not empty
        // take node u in Q with min dist[u]  (start is used first)
        // remove u from Q
        // if u is the end node then terminate algorithm
        //
            // for each neighbor (v) of u
            // dist[v] = min of (dist[v] and dist[u] + length from u to v)
            // NOTE: we use a distance of 1 between neighbors (length u to v = 1)

        // As long has end has been removed from Q then we found an answer:
        // return distance of end node and its prev node

        // NOTE:
        // Correct, there is no readme in data/distances. But you can find some explanation in
        // the comments right above Maze.java -> private void loadDistances(String fileName). You
        // can also refer to Game.java -> public int getShortestPathDistance(int fromNodeIndex,
        // int toNodeIndex) for how the pre-loaded distances get used. You can assume each node
        // is 1 unit from any neighbor.


        // write output:
        // To get path and distances
        // while prev[u] is defined:
        //  insert u at beginning of a path list.
        //  u = prev[u]
        // insert u at beginning of a path list. (last one isn't found in loop)

    }

    /* Private functions */

    private class Vertex {
        private Vertex previous;
        private int distance;
        private List<Vertex> neighbors;

        protected Vertex() {
            this.previous = null;
            this.distance = Integer.MAX_VALUE;
            this.neighbors = new ArrayList<>();
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

        public List<Vertex> getNeighbors() {
            return neighbors;
        }

        public void setNeighbors(List<Vertex> neighbors) {
            this.neighbors = neighbors;
        }
    }

}
