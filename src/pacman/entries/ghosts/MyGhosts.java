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
 * MyGhosts implemented with A*, always chases if not edible and PacMan not
 * near power pill.
 */
public class MyGhosts extends Controller<EnumMap<GHOST, MOVE>>
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
					//System.out.println("Ghost: " + ghost.toString() + " Move: " + move.toString());

					myMoves.put(ghost, move);
				}
			}
		}

		return myMoves;
	}

	/**
	 * Convert from provided data into a list of vertices, call A start calculation.
	 *
	 * @param graph list of nodes
	 * @param pacManNodeIndex pacman location
	 * @param ghostNodeIndex ghost location
	 * @return direction to travel
	 */
	public MOVE getNextMoveAStar(Node[] graph, int pacManNodeIndex, int ghostNodeIndex) {
		try {
			List<Vertex> nodes = new ArrayList<>();
			Vertex start = null;
			Vertex goal = null;
			for (Node n : graph) {
				Vertex v = new Vertex(n);
				nodes.add(v);
				if(v.getIndex() == ghostNodeIndex)
					start = v;
				else if(v.getIndex() == pacManNodeIndex)
					goal = v;
			}

			Vertex position = aStarAlgorithm(nodes, start, goal);

			// Back trace from end position to start
			while (position != null && position.getPrevious() != null
				&& position.getPrevious().getIndex() != ghostNodeIndex) {
				position = position.getPrevious();
			}

			if (position == null) {
				throw new Exception("Path error.");
			}

			start = position.getPrevious();
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

	/**
	 * Takes a maze, start node and end node, and adds previous node fields to find a path
	 * between the two using A* algorithm.
	 *
	 * @param nodes the list of nodes from a file.
	 * @param start the start vertex.
	 * @param goal the end vertex.
	 * @throws Exception exception if path can't be found.
	 */
	public Vertex aStarAlgorithm(List<Vertex> nodes, Vertex start, Vertex goal) throws Exception {
		if(start == null || goal == null) {
			throw new Exception("Extrema of search not found.");
		}
		// Create a set
		// Chose a sorted set so we always have shortest at front
		List<Vertex> openList = new ArrayList<>();
		List<Vertex> closedList = new ArrayList<>();
		for(Vertex n : nodes) {
			n.setDistance(Integer.MAX_VALUE);
			// Take heuristic from manhattan distance (delta x + delta y)
			int heuristic = (goal.getxPosition() - n.getxPosition()) + (goal.getyPosition() + n.getyPosition());
			n.setHeuristic(heuristic);

			if(n.equals(start)) {
				n.setDistance(0);
				openList.add(n);
			}
		}

		while (!openList.isEmpty()) {
			Collections.sort(openList, Vertex.VertexDistanceWithHeuristicComparator);

			// Take node u in Q with least distance
			Vertex u = openList.get(0);

			// Remove u from Q since it's best path has been found （visited）
			openList.remove(u);
			closedList.add(u);

			// If u is the end node then terminate algorithm
			if(u.equals(goal)) {
				// Output the results
				return u;
			}

			// for each neighbor (v) of u
			int[] neighbors = u.getNeighbors();

			for(Vertex vertex : nodes) {
				for (int index = 0; index < neighbors.length; index++) {
					if(vertex.getIndex() == neighbors[index]) {
						// If a better distance is found, update distance and previous node.
						if(vertex.getDistance() > (u.getDistance() + 1)) {
							vertex.setDistance(u.getDistance() + 1);
							vertex.setPrevious(u);

							// Update lists
							if(!openList.contains(vertex)) {
								if(!closedList.contains(vertex)) {
									openList.add(vertex);
								} else {
									closedList.remove(vertex);
									openList.add(vertex);
								}
							}
						}
					}
				}
			}
		}

		return null;
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
