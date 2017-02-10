package pacman.pathFinding;

import org.junit.Test;

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
        test.dijkstraOnMaze("b");

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
        test.dijkstraOnMaze("c");
    }

    /**
     * Test Maze D.
     * The output distance from node 1 to the last node should be the
     * same as given in the static distance d file.
     */
    @Test
    public void testMazeD() {
        Dijkstra test = new Dijkstra();
        test.dijkstraOnMaze("d");

        // Compare output file made to original output file
    }
}
