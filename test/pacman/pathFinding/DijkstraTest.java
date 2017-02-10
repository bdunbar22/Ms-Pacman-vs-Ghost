package pacman.pathFinding;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Unit and functionality testing for Dijkstra code.
 *
 * Created by Ben on 2/1/16.
 */
public class DijkstraTest {

    /**
     * Test on a small set of nodes
     * Based on our analysis, it should have a distance of 3.
     */
    @Test
    public void testShortDistance() {
        Dijkstra test = new Dijkstra();
        List<Vertex> nodes = makeSmallGraph();
        int distance = test.dijkstraAlgorithm(nodes, 0, 9);
        assertTrue(distance == 5);
    }

    /**
     * Test on a small set of nodes
     * Based on our analysis, it should have a distance of 3.
     */
    @Test
    public void testNoRoute() {
        Dijkstra test = new Dijkstra();
        List<Vertex> nodes = makeSmallGraph();
        int distance = test.dijkstraAlgorithm(nodes, 0, 11);
        assertTrue(distance == -1);
    }

    private List<Vertex> makeSmallGraph() {
        List<Vertex> nodes = new ArrayList<>();
        // UP, RIGHT, DOWN, LEFT
        nodes.add(new Vertex(0, 0, 0, new int[] {-1, 1, -1, -1}));
        nodes.add(new Vertex(1, 1, 0, new int[] {4, 2, -1, 0}));
        nodes.add(new Vertex(2, 2, 0, new int[] {-1, 3, -1, 1}));
        nodes.add(new Vertex(3, 3, 0, new int[] {5, -1, -1, 2}));
        nodes.add(new Vertex(4, 1, 1, new int[] {6, -1, 1, -1}));
        nodes.add(new Vertex(5, 3, 1, new int[] {7, -1, 3, -1}));
        nodes.add(new Vertex(6, 1, 2, new int[] {8, -1, 4, -1}));
        nodes.add(new Vertex(7, 3, 2, new int[] {10, -1, 5, -1}));
        nodes.add(new Vertex(8, 1, 3, new int[] {-1, 9, 6, -1}));
        nodes.add(new Vertex(9, 2, 3, new int[] {-1, 10, -1, 8}));
        nodes.add(new Vertex(10, 3, 3, new int[] {-1, -1, 7, 9}));
        nodes.add(new Vertex(11, 0, 4, new int[] {-1, -1, -1, -1})); // No neighbors
        return nodes;
    }

    /**
     * Test Maze A.
     *
     * Compare the generated file to the existing file.
     */
    @Test
    public void testMazeA() {
        try
        {
            Dijkstra test = new Dijkstra();
            String fileLocation = test.dijkstraOnMaze("data/mazes/a");
            if(fileLocation == null) {
                throw new Exception("No file found.");
            }

            int[] old_distances = readIntFromFile("data/distances/da", 836571);
            int[] new_distances = readIntFromFile(fileLocation, 836571);

            // Check that the numbers are the same.
            for(int i = 0; i < old_distances.length; i++) {
                // System.out.println("Checking iteration: " + i);
                assertTrue(old_distances[i] == new_distances[i]);
            }
        }
        catch(Exception ioe)
        {
            // Fail the test
            assertTrue(false);
            ioe.printStackTrace();
        }
    }

    /**
     * Test Maze B.
     *
     * Compare the generated file to the existing file.
     */
    @Test
    public void testMazeB() {
        try
        {
            Dijkstra test = new Dijkstra();
            String fileLocation = test.dijkstraOnMaze("data/mazes/b");
            if(fileLocation == null) {
                throw new Exception("No file found.");
            }

            int[] old_distances = readIntFromFile("data/distances/db", 870540);
            int[] new_distances = readIntFromFile(fileLocation, 870540);


            // Check that the numbers are the same.
            for(int i = 0; i < old_distances.length; i++) {
                // System.out.println("Checking iteration: " + i);
                assertTrue(old_distances[i] == new_distances[i]);
            }
        }
        catch(Exception ioe)
        {
            // Fail the test
            assertTrue(false);
            ioe.printStackTrace();
        }
    }

    /**
     * Test Maze C.
     *
     * Compare the generated file to the existing file.
     */
    @Test
    public void testMazeC() {
        try
        {
            Dijkstra test = new Dijkstra();
            String fileLocation = test.dijkstraOnMaze("data/mazes/c");
            if(fileLocation == null) {
                throw new Exception("No file found.");
            }

            int[] old_distances = readIntFromFile("data/distances/dc", 952890);
            int[] new_distances = readIntFromFile(fileLocation, 952890);


            // Check that the numbers are the same.
            for(int i = 0; i < old_distances.length; i++) {
                // System.out.println("Checking iteration: " + i);
                assertTrue(old_distances[i] == new_distances[i]);
            }
        }
        catch(Exception ioe)
        {
            // Fail the test
            assertTrue(false);
            ioe.printStackTrace();
        }
    }

    /**
     * Test Maze D.
     *
     * Compare the generated file to the existing file.
     */
    @Test
    public void testMazeD() {
        try
        {
            Dijkstra test = new Dijkstra();
            String fileLocation = test.dijkstraOnMaze("data/mazes/d");
            if(fileLocation == null) {
                throw new Exception("No file found.");
            }

            int[] old_distances = readIntFromFile("data/distances/dd", 857395);
            int[] new_distances = readIntFromFile(fileLocation, 857395);


            // Check that the numbers are the same.
            for(int i = 0; i < old_distances.length; i++) {
                // System.out.println("Checking iteration: " + i);
                assertTrue(old_distances[i] == new_distances[i]);
            }
        }
        catch(Exception ioe)
        {
            // Fail the test
            assertTrue(false);
            ioe.printStackTrace();
        }
    }

    private int[] readIntFromFile(String filename, int fileLength) {
        try
        {
            int[] ints = new int[fileLength];

            BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream
                (filename)));
            String input=br.readLine();

            int index=0;

            while(input!=null)
            {
                ints[index++]=Integer.parseInt(input);
                input=br.readLine();
            }

            return ints;
        }
        catch(Exception ioe)
        {
            return new int[fileLength];
        }
    }
}
