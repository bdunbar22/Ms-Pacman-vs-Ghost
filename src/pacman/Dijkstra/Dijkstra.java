package pacman.Dijkstra;

import java.io.*;
import java.util.*;

import static pacman.game.Constants.pathMazes;

/**
 * Dijkstra's Algorithm class
 *
 * Created by Ben on 2/4/17.
 */
public class Dijkstra {

    /**
     * Take a maze and then compute the distance from every node to every other node.
     * @param fileName maze to compute dijkstra on.
     */
    public void dijkstraOnMaze(String fileName) {
        // Create output file
        List<Vertex> nodes = new ArrayList<>();
        loadVertices(fileName, nodes);

        int[] distances = new int[((nodes.size()*(nodes.size()-1))/2)+nodes.size()];
        // Set all distances to -1 to start
        for(int i = 0; i < distances.length; i++) {
            distances[i] = -1;
        }

        int distance;
        int index;

        for(Vertex fromNode : nodes) {
            for(Vertex toNode : nodes) {
                if(fromNode.getIndex() <= toNode.getDistance()) {
                    index = ((toNode.getIndex() * (toNode.getIndex() +1))/2) + fromNode.getIndex();
                    distance = distances[index];

                    // If the distance hasn't been found, run dijkstra
                    if(distance == -1) {
                        distances[index] = dijkstraAlgorithm(fileName, fromNode.getIndex(),
                            toNode.getIndex());
                    }
                } else {
                    index = (( fromNode.getIndex() * (fromNode.getIndex() + 1))/2) + toNode.getIndex();
                    distance = distances[index];

                    // If the distance hasn't been found, run dijkstra
                    if(distance == -1) {
                        distances[index] = dijkstraAlgorithm(fileName, fromNode.getIndex(),
                            toNode.getIndex());
                    }
                }
            }
        }

        // Create 1D output file, with the values in distances separated by new lines.
        try{
            String distanceFileName = "distances_for_" + fileName;
            PrintWriter writer = new PrintWriter(distanceFileName, "UTF-8");

            // Loop through distances and print
            for (int d : distances) {
                writer.println(Integer.toString(d));
            }

            writer.close();
        } catch (IOException e) {
            // do something
        }
    }

    /**
     * Takes a maze, start node and end node, and creates an output file with the distance from
     * the start and end node. Uses dijkstra algorithm.
     *
     * @param fileName the filename of the maze being used.
     * @param start the index of the start node.
     * @param end the index of the end node.
     */
    public int dijkstraAlgorithm(String fileName, int start, int end) {
        System.out.println("Dijkstra on file " + fileName + ".txt. Start: " + start + ". End: "
            + end);

        // Create a set
        // Chose a sorted set so we always have shortest at front
        List<Vertex> Q = new ArrayList<>();
        loadVertices(fileName, Q);

        // Set distance of start node to 0
        for(Vertex v : Q) {
            if(v.getIndex() == start) {
                v.setDistance(0);
            }
        }

        while (!Q.isEmpty()) {
            Collections.sort(Q, VertexDistanceComparator);

            // Take node u in Q with least distance
            Vertex u = Q.get(0);

            // Remove u from Q since it's best path has been found （visited）
            Q.remove(u);

            // If u is the end node then terminate algorithm
            if(u.getIndex() == end) {
                // Output the results
                return u.getDistance();
            }

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

        // If no path is found, return -1 to indicate no route.
        return -1;
    }


    /* Private functions */
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
                (pathMazes+System.getProperty("file.separator")+fileName+".txt")));

            /* Don't need to use preamble line */
            String preableLine = br.readLine();

            String input = br.readLine();

            while(input!=null)
            {
                String[] values = input.split("\t");

                int index = Integer.parseInt(values[0]);

                int[] neighbors = new int[]{Integer.parseInt(values[3]), Integer.parseInt
                    (values[4]),
                    Integer.parseInt(values[5]),Integer.parseInt(values[6])};

                // Initialize vertices with an index and neighbors
                // Leave distance as max value and previous vertex as null
                Vertex vertex = new Vertex(index, neighbors);

                Q.add(vertex);

                input = br.readLine();
            }
        }
        catch(Exception errorSent)
        {
            errorSent.printStackTrace();
        }
    }

    /**
     * Outputs the results upon success.
     *
     * @param end vertex
     */
    private void endVertexFound(Vertex end) {
        // write output:
        // To get path and distances
        // while prev[u] is defined:
        //  insert u at beginning of a path list.
        //  u = prev[u]
        // insert u at beginning of a path list. (last one isn't found in loop)
        System.out.println("End vertex found! Distance: " + end.getDistance());
    }


    /* Vertex class to be used to store data for the nodes. */
    private class Vertex {
        private int index;
        private Vertex previous;
        private int distance;
        private int[] neighbors;

        private Vertex(int index, int[] neighbors) {
            this.index = index;
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
    }

    private static Comparator<Vertex> VertexDistanceComparator = new Comparator<Vertex>() {

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
