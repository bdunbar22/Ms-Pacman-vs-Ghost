package pacman.pathFinding;

import java.io.*;
import java.util.*;

/**
 * Dijkstra's Algorithm class
 *
 * Created by Ben on 2/4/17.
 */
public class Dijkstra {

    /**
     * Take a maze and then compute the distance from every node to every other node.
     * @param fileName maze to compute dijkstra on.
     *
     * @return the file location which was created
     */
    public String dijkstraOnMaze(String fileName) {
        // Create output file
        List<Vertex> nodes = new ArrayList<>();
        loadVertices(fileName, nodes);

        int[] distances = new int[((nodes.size()*(nodes.size()-1))/2)+nodes.size()];
        // Set all distances to -1 to start
        for(int i = 0; i < distances.length; i++) {
            distances[i] = -1;
        }

        int index;

        for(Vertex fromNode : nodes) {
            List<Vertex> closedList = dijkstraAlgorithmFullList(nodes, fromNode.getIndex());

            for(Vertex visited : closedList) {
                if(fromNode.getIndex() <= visited.getIndex()) {
                    index = ((visited.getIndex() * (visited.getIndex() + 1)) / 2) + fromNode.getIndex();
                } else {
                    index = ((fromNode.getIndex() * (fromNode.getIndex() + 1)) / 2) + visited.getIndex();
                }
                if(visited.getDistance() > -1)
                    distances[index] = visited.getDistance();
            }
        }

        // Create 1D output file, with the values in distances separated by new lines.
        try{
            String distanceFileName = "data/distances/distances_for_" + fileName.replace("/",
                "_");
            PrintWriter writer = new PrintWriter(distanceFileName, "UTF-8");

            // Loop through distances and print
            for (int d : distances) {
                writer.println(Integer.toString(d));
            }

            writer.close();
            return distanceFileName;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Takes a maze, start node and end node, and creates an output file with the distance from
     * the start and end node. Uses dijkstra algorithm.
     *
     * @param nodes the list of nodes from a file.
     * @param start the index of the start node.
     * @param end the index of the end node.
     */
    public int dijkstraAlgorithm(List<Vertex> nodes, int start, int end) {
        // Create a set
        // Chose a sorted set so we always have shortest at front
        List<Vertex> Q = new ArrayList<>();
        for(Vertex n : nodes) {
            n.setDistance(Integer.MAX_VALUE);
            if(n.getIndex() == start) {
                n.setDistance(0);
            }
            Q.add(n);
        }

        while (!Q.isEmpty()) {
            Collections.sort(Q, Vertex.VertexDistanceComparator);

            // Take node u in Q with least distance
            Vertex u = Q.get(0);

            // Remove u from Q since it's best path has been found （visited）
            Q.remove(u);

            // If u is the end node then terminate algorithm
            if(u.getIndex() == end) {
                // Output the results
                if(u.getDistance() == Integer.MAX_VALUE) {
                    return -1;
                }
                return u.getDistance();
            }

            // for each neighbor (v) of u
            int[] neighbors = u.getNeighbors();

            for(Vertex v : Q) {
                for (int index = 0; index < neighbors.length; index++) {
                    if(v.getIndex() == neighbors[index]) {
                        // If a better distance is found, update distance and previous node.
                        if(v.getDistance() > (u.getDistance() + 1)) {
                            v.setDistance(u.getDistance() + 1);
                            v.setPrevious(u);
                        }
                    }
                }
            }
        }

        // If no path is found, return -1 to indicate no route.
        return -1;
    }

    /**
     * Takes a maze and start node and outputs a list of nodes which each store their distance
     * from start. Uses dijkstra algorithm.
     *
     * @param nodes the list of nodes from a file.
     * @param start the index of the start node.
     */
    public List<Vertex> dijkstraAlgorithmFullList(List<Vertex> nodes, int start) {
        // Create a set
        // Chose a sorted set so we always have shortest at front
        List<Vertex> Q = new ArrayList<>();
        for(Vertex n : nodes) {
            n.setDistance(Integer.MAX_VALUE);
            if(n.getIndex() == start) {
                n.setDistance(0);
            }
            Q.add(n);
        }

        List<Vertex> closedList = new ArrayList<>();

        while (!Q.isEmpty()) {
            Collections.sort(Q, Vertex.VertexDistanceComparator);

            // Take node u in Q with least distance
            Vertex u = Q.get(0);

            // Remove u from Q since it's best path has been found （visited）
            Q.remove(u);
            closedList.add(u);

            // for each neighbor (v) of u
            int[] neighbors = u.getNeighbors();

            for(Vertex v : Q) {
                for (int index = 0; index < neighbors.length; index++) {
                    if(v.getIndex() == neighbors[index]) {
                        // If a better distance is found, update distance and previous node.
                        if(v.getDistance() > u.getDistance() + 1) {
                            v.setDistance(u.getDistance() + 1);
                            v.setPrevious(u);
                        }
                    }
                }
            }
        }

        for(Vertex v : closedList) {
            if(v.getDistance() == Integer.MAX_VALUE)
                v.setDistance(-1);
        }
        return closedList;
    }

    /**
     * Loads all the nodes from files into a set of vertices.
     * @param fileName to read
     * @param Q set to insert vertices to
     */
    private void loadVertices(String fileName, List<Vertex> Q)
    {
        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream
                (fileName + ".txt")));

            /* Don't need to use preamble line */
            String preableLine = br.readLine();

            String input = br.readLine();

            while(input!=null)
            {
                String[] values = input.split("\t");

                int index = Integer.parseInt(values[0]);
                int x = Integer.parseInt(values[1]);
                int y = Integer.parseInt(values[2]);

                int[] neighbors = new int[]{Integer.parseInt(values[3]), Integer.parseInt
                    (values[4]),
                    Integer.parseInt(values[5]),Integer.parseInt(values[6])};

                // Initialize vertices with an index and neighbors
                // Leave distance as max value and previous vertex as null
                Vertex vertex = new Vertex(index, x, y, neighbors);

                Q.add(vertex);

                input = br.readLine();
            }
        }
        catch(Exception errorSent)
        {
            errorSent.printStackTrace();
        }
    }
}
