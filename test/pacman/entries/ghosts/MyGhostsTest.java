package pacman.entries.ghosts;

import org.junit.Test;
import pacman.controllers.Controller;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.GameView;
import pacman.pathFinding.Vertex;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import static org.junit.Assert.*;
import static pacman.game.Constants.DELAY;

/**
 * Unit and functionality testing for MyGhosts A* code.
 *
 * Created by Ben on 2/9/17.
 */
public class MyGhostsTest {

    /**
     * Test that the controller will function correctly with a timed game.
     *
     * @throws Exception
     */
    @Test
    public void getMove() throws Exception {
        Controller ghosts = new MyGhosts();
        Game game = new Game(0, 0);
        String testGameState = "0,280,470,280,0,600,RIGHT,2,false,153,0,0,UP,165,0,0,UP,177,0,0,"
            + "RIGHT,270,0,0,UP,1111111111111111111111111111111111011111111111111111111111011111011111011111011111111111111101010101010100000000000000000000000010011001111111110000000011111111111111111111111111111111111111111111111111111111111111111111,1111,-1,false,false,false,false,false,false,false";
        game.setGameState(testGameState);
        GameView gv = null;
        gv = new GameView(game).showGame();

        long timeDue = System.currentTimeMillis() + DELAY;
        ghosts.update(game, timeDue);
        Thread.sleep(DELAY);
        Object moves = ghosts.getMove(game, timeDue);
        EnumMap<Constants.GHOST, Constants.MOVE> map = (EnumMap<Constants.GHOST, Constants.MOVE>) moves;

        // Give a visual of the game state we created for a test.
        for(int i = 0; i < 10; i++) {
            Thread.sleep(1000);
            gv.repaint();
        }
        assertEquals(map.get(Constants.GHOST.BLINKY), Constants.MOVE.DOWN);
        assertEquals(map.get(Constants.GHOST.INKY), Constants.MOVE.RIGHT);
        assertEquals(map.get(Constants.GHOST.PINKY), Constants.MOVE.RIGHT);
    }


    /**
     * Unit test for A* algorithm.
     * test full path on a small graph.
     *
     * @throws Exception
     */
    @Test
    public void aStarAlgorithm() throws Exception {
        MyGhosts ghosts = new MyGhosts();
        List<Vertex> graph = makeSmallGraph();
        Vertex end = ghosts.aStarAlgorithm(graph, graph.get(0), graph.get(5));
        assertTrue(end.getIndex() == 5);
        Vertex position = end.getPrevious();
        assertTrue(position.getIndex() == 3);
        position = position.getPrevious();
        assertTrue(position.getIndex() == 2);
        position = position.getPrevious();
        assertTrue(position.getIndex() == 1);
        position = position.getPrevious();
        assertTrue(position.getIndex() == 0);
    }

    /**
     * Unit test for A* algorithm. Path not possible.
     *
     * @throws Exception
     */
    @Test
    public void aStarAlgorithmNoRoute() throws Exception {
        MyGhosts ghosts = new MyGhosts();
        List<Vertex> graph = makeSmallGraph();
        Vertex end = ghosts.aStarAlgorithm(graph, graph.get(0), graph.get(12));
        assertNull(end);
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
        nodes.add(new Vertex(12, 0, 5, new int[] {-1, -1, 11, -1})); // No neighbors
        return nodes;
    }
}
