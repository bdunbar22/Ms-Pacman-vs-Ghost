package pacman.Dijkstra;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test the functionality of the Player
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
        test.dijkstraAlgorithm("a", 0, 1292);
    }

    /**
     * Test Maze B.
     * The output distance from node 1 to the last node should be the
     * same as given in the static distance a file.
     */
    @Test
    public void testMazeB() {
        Dijkstra test = new Dijkstra();
        test.dijkstraAlgorithm("b", 0, 1318);
    }
}

    /**
     * Test Maze C.
     * The output distance from node 1 to the last node should be the
     * same as given in the static distance a file.
     */
    @Test
    public void testMazeC() {
        Dijkstra test = new Dijkstra();
        test.dijkstraAlgorithm("c", 0, 1379);
    }
}

/**
 * Test Maze D.
 * The output distance from node 1 to the last node should be the
 * same as given in the static distance a file.
 */
@Test
public void testMazeD() {
        Dijkstra test = new Dijkstra();
        test.dijkstraAlgorithm("d", 0, 1308);
        }
}