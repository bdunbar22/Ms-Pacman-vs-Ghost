package pacman.Dijkstra;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit and functionality testing for Dijkstra code.
 *
 * Created by Ben on 2/1/16.
 */
public class DijkstraTest {

    /**
     * Test from node 3 to node 6 of maze a.
     * Based on our analysis, it should have a distance of 3.
     */
    @Test
    public void testShortDistance() {
        Dijkstra test = new Dijkstra();
        test.dijkstraAlgorithm("a", 3, 6);
    }

    /**
     * Test Maze A.
     * The output distance from node 1 to the last node should be the
     * same as given in the static distance a file.
     */
    @Test
    public void testMazeA() {
        Dijkstra test = new Dijkstra();
        test.dijkstraOnMaze("a");

        // Compare output file made to original output file
        // Read both files into a list of ints
        // compare the lists
    }

    /**
     * Test Maze B.
     * The output distance from node 1 to the last node should be the
     * same as given in the static distance b file.
     */
    @Test
    public void testMazeB() {
        Dijkstra test = new Dijkstra();
        test.dijkstraAlgorithm("b", 0, 1318);

        // Compare output file made to original output file
    }

    /**
     * Test Maze C.
     * The output distance from node 1 to the last node should be the
     * same as given in the static distance c file.
     */
    @Test
    public void testMazeC() {
        Dijkstra test = new Dijkstra();
        test.dijkstraAlgorithm("c", 0, 1379);
    }

    /**
     * Test Maze D.
     * The output distance from node 1 to the last node should be the
     * same as given in the static distance d file.
     */
    @Test
    public void testMazeD() {
        Dijkstra test = new Dijkstra();
        test.dijkstraAlgorithm("d", 0, 1308);

        // Compare output file made to original output file
    }
}
