package pacman.entries.pacman;

import pacman.controllers.Controller;
import pacman.decisionMaking.ActionType;
import pacman.decisionMaking.DecisionTree;
import pacman.game.Constants;
import pacman.game.Constants.MOVE;
import pacman.game.Constants.GHOST;
import pacman.game.Game;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * This is the class you need to modify for your entry. In particular, you need to
 * fill in the getAction() method. Any additional classes you write should either
 * be placed in this package or sub-packages (e.g., game.entries.pacman.mypackage).
 */
public class DTPacMan extends Controller<MOVE>
{
	private MOVE myMove = MOVE.NEUTRAL;
	private DecisionTree decisionTree;

	public DTPacMan(String dtFileLocation) {
		this.decisionTree = new DecisionTree(dtFileLocation);
	}
	
	public MOVE getMove(Game game, long timeDue) 
	{
		int current = game.getPacmanCurrentNodeIndex();
		//Place your game logic here to play the game as Ms Pac-Man
		ActionType action = this.decisionTree.makeDecision(game);

		// Perform the action chosen by the tree.
		switch (action) {
			case NEAREST_PILL:
				int[] pills = game.getPillIndices();

				ArrayList<Integer> targets = new ArrayList<Integer>();

				for (int i = 0; i < pills.length; i++)
					if (game.isPillStillAvailable(i))
						targets.add(pills[i]);

				int[] targetsArray = new int[targets.size()];

				for (int i = 0; i < targetsArray.length; i++)
					targetsArray[i] = targets.get(i);

				myMove = game.getNextMoveTowardsTarget(current,
					game.getClosestNodeIndexFromNodeIndex(current, targetsArray, Constants.DM.PATH),
					Constants.DM.PATH);
				break;
			case NEAREST_POWER_PILL:
				int[] powerPills = game.getPowerPillIndices();

				ArrayList<Integer> powerTargets = new ArrayList<Integer>();

				for (int i = 0; i < powerPills.length; i++)
					if (game.isPowerPillStillAvailable(i))
						powerTargets.add(powerPills[i]);

				int[] powerTargetsArray = new int[powerTargets.size()];

				for (int i = 0; i < powerTargetsArray.length; i++)
					powerTargetsArray[i] = powerTargets.get(i);

				myMove = game.getNextMoveTowardsTarget(current,
					game.getClosestNodeIndexFromNodeIndex(current, powerTargetsArray, Constants.DM.PATH),
					Constants.DM.PATH);
				break;
			case ATTACK:
				int minDistance = Integer.MAX_VALUE;
				Constants.GHOST minGhost = null;

				for (Constants.GHOST ghost : Constants.GHOST.values())
					if (game.getGhostEdibleTime(ghost) > 0) {
						int distance = game.getShortestPathDistance(current, game.getGhostCurrentNodeIndex(ghost));

						if (distance < minDistance) {
							minDistance = distance;
							minGhost = ghost;
						}
					}

				if (minGhost != null)  //we found an edible ghost
					myMove = game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(minGhost),
						Constants.DM.PATH);
				break;
			case RUN_AWAY:
			default: //Made RUN_AWAY by default
				runAway(game, current);
			}
		return myMove;
	}

	/**
	 * Method to determine best route to run away from the ghosts.
	 */
	private void runAway(Game game, int current) {
		int closestGhostDistance = Integer.MAX_VALUE;
		for(GHOST ghost : GHOST.values()) {
			if (game.getGhostEdibleTime(ghost) == 0 && game.getGhostLairTime(ghost) == 0) {
				int currentGhostDistance = game.getShortestPathDistance(current, game.getGhostCurrentNodeIndex(ghost));
				if (currentGhostDistance < closestGhostDistance) {
					closestGhostDistance = currentGhostDistance;
				}
			}
		}

		MOVE[] moves = game.getPossibleMoves(current);
		ArrayList<MOVE> goodMoves = new ArrayList<>(Arrays.asList(moves));
		for (GHOST ghost : GHOST.values()) {
			if (game.getGhostEdibleTime(ghost) == 0 && game.getGhostLairTime(ghost) == 0) {
				int currentGhostDistance = game.getShortestPathDistance(current, game.getGhostCurrentNodeIndex(ghost));
				if (currentGhostDistance < closestGhostDistance + 15) {
					for (MOVE move : moves) {
						if (move.equals(game.getNextMoveTowardsTarget(
								current,
								game.getGhostCurrentNodeIndex(ghost),
								Constants.DM.PATH))) {
							goodMoves.remove(move);
						}
					}
				}
			}
		}

		if(!goodMoves.isEmpty()) {
			myMove = goodMoves.get(0);
		} else {
			int[] junctions = game.getJunctionIndices();
			int goal = game.getClosestNodeIndexFromNodeIndex(current, junctions, Constants.DM
				.PATH);
			myMove = game.getNextMoveTowardsTarget(current, goal, Constants.DM.PATH);
		}
	}
}
