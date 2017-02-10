package pacman.entries.ghosts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

import pacman.controllers.Controller;
import pacman.game.Constants;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.internal.Node;
import pacman.pathFinding.Vertex;

/*
 * This is the class you need to modify for your entry. In particular, you need to
 * fill in the getActions() method. Any additional classes you write should either
 * be placed in this package or sub-packages (e.g., game.entries.ghosts.mypackage).
 */
public class MyGhosts extends Controller<EnumMap<GHOST,MOVE>>
{
	private final static int PILL_PROXIMITY = 15;		//if Ms Pac-Man is this near a power pill, run

	private EnumMap<GHOST, MOVE> myMoves=new EnumMap<GHOST, MOVE>(GHOST.class);
	
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue)
	{
		myMoves.clear();
		int pacManNodeIndex = game.getPacmanCurrentNodeIndex();
		int ghostNodeIndex;

		for(GHOST ghost : GHOST.values())	//for each ghost
		{
			//NOTE: make own require action so that ghosts can turn around
			if(game.doesGhostRequireAction(ghost))		//if ghost requires an action
			{
				// Reuse existing code for running away
				if(game.getGhostEdibleTime(ghost) > 0 || closeToPower(game))
					myMoves.put(ghost,game.getApproximateNextMoveAwayFromTarget(game.getGhostCurrentNodeIndex(ghost),
						game.getPacmanCurrentNodeIndex(),game.getGhostLastMoveMade(ghost), Constants.DM.PATH));
				else
				{
					// attack Ms. Pacman with self written code.
					ghostNodeIndex = game.getGhostCurrentNodeIndex(ghost);

					MOVE move = getNextMoveAStar(game.getCurrentMaze().graph, pacManNodeIndex, ghostNodeIndex);

					myMoves.put(ghost, move);
				}
			}
		}
		
		return myMoves;
	}

	/**
	 * Run A* algorithm on the list of nodes to get short path from the ghost to the pacman.
	 * Return the direction the ghost has to go to start down the path.
	 *
	 * @param graph list of nodes
	 * @param pacManNodeIndex pacman location
	 * @param ghostNodeIndex ghost location
	 * @return direction to travel
	 */
	public MOVE getNextMoveAStar(Node[] graph, int pacManNodeIndex, int ghostNodeIndex) {
		try {

			// List of nodes in ascending order of distance estimations
			List<Vertex> fringe = new ArrayList<>();

			// List of evaluated nodes
			List<Vertex> closedList = new ArrayList<>();

			// Convert nodes into the vertex class we decided to use.
			Vertex goal = null;
			List<Vertex> nodes = new ArrayList<>();
			for (Node n : graph) {
				Vertex node = new Vertex(n);
				nodes.add(node);
				if (node.getIndex() == ghostNodeIndex) {
					fringe.add(node);
				}
				if (node.getIndex() == pacManNodeIndex) {
					goal = node;
				}
			}

			// Send neutral if goal wasn't found.
			if (goal == null) {
				throw new Exception("Goal not found.");
			}

			// Get the end position after A* find route
			Vertex position = findRoute(nodes, closedList, fringe, goal);

			// Back trace from end position to start
			while (position != null && position.getPrevious() != null
				&& position.getPrevious().getIndex() != ghostNodeIndex) {
				position = position.getPrevious();
			}

			if (position == null) {
				throw new Exception("Path error.");
			}

			Vertex start = position.getPrevious();
			int[] neighbors = start.getNeighbors();

			// Order of neighbors: UP, RIGHT, DOWN, LEFT
			if (neighbors[0] == position.getIndex()) {
				return MOVE.UP;
			} else if (neighbors[1] == position.getIndex()) {
				return MOVE.RIGHT;
			} else if (neighbors[2] == position.getIndex()) {
				return MOVE.DOWN;
			} else if (neighbors[3] == position.getIndex()) {
				return MOVE.LEFT;
			}
			return MOVE.NEUTRAL;
		}
		catch (Exception error) {
			// On error send a neutral move
			return MOVE.NEUTRAL;
		}
	}

	private Vertex findRoute(List<Vertex> nodes, List<Vertex> closedList, List<Vertex>
		fringe, Vertex goal) {

		if(fringe.isEmpty()) {
			return null;
		}

		Collections.sort(fringe, Vertex.VertexDistanceWithHeuristicComparator);
		// Get shortest estimated distance vertex
		Vertex vertex = fringe.get(0);

		if(vertex.equals(goal)) {
			return vertex;
		}

		if(!closedList.contains(vertex)) {
			closedList.add(vertex);
			addChildrenToFringe(nodes, closedList, fringe, vertex, goal);
		}

		return findRoute(nodes, closedList, fringe, goal);
	}

	/**
	 * Examine children of a node, if needed update their prev value and distance and heuristic
	 * values. If they aren't in the open list then add them to the open list.
	 *
	 * @param nodes full node list
	 * @param closedList list of closed values
	 * @param fringe open list
	 * @param parentNode the node being analyzed
	 * @param goal to reach
	 * */
	private void addChildrenToFringe(List<Vertex> nodes, List<Vertex> closedList, List<Vertex>
		fringe, Vertex parentNode, Vertex goal) {
		int[] neighbors = parentNode.getNeighbors();
		for(Vertex v : nodes) {
			for (int index = 0; index < neighbors.length; index++) {
				if(v.getIndex() == neighbors[index] && !closedList.contains(v)) {
					if(v.getDistance() > parentNode.getDistance() + 1) {
						v.setDistance(parentNode.getDistance() + 1);
						v.setPrevious(parentNode);
					}

					int heuristic = (goal.getxPosition() - v.getxPosition()) +
						(goal.getyPosition() + v.getyPosition());
					v.setHeuristic(heuristic);

					if(!fringe.contains(v)) {
						fringe.add(v);
					}
				}
			}
		}
	}
















	//This helper function checks if Ms Pac-Man is close to an available power pill
	private boolean closeToPower(Game game)
	{
		int[] powerPills=game.getPowerPillIndices();

		for(int i=0;i<powerPills.length;i++)
			if(game.isPowerPillStillAvailable(i) &&
				 game.getShortestPathDistance(powerPills[i],game.getPacmanCurrentNodeIndex()) < PILL_PROXIMITY)
				return true;

		return false;
	}
}
