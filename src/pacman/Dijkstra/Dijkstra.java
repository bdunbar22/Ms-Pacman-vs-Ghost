package pacman.Dijkstra;

/**
 * Created by Ben on 2/4/17.
 */
public class Dijkstra {


    public void dijkstraAlgorithm( /* Accept the graph being used, start, and end node */) {
        // read file into a list of nodes
        // create node set Q

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

}
