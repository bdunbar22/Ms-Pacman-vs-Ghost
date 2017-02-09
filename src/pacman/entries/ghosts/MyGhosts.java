package pacman.entries.ghosts;

import java.util.EnumMap;
import pacman.controllers.Controller;
import pacman.game.Constants;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.internal.Node;

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



		return MOVE.NEUTRAL;
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
