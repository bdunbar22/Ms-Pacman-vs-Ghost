package pacman.entries.pacman;

import pacman.controllers.Controller;
import pacman.decisionMaking.ActionType;
import pacman.decisionMaking.DecisionTree;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

/*
 * This is the class you need to modify for your entry. In particular, you need to
 * fill in the getAction() method. Any additional classes you write should either
 * be placed in this package or sub-packages (e.g., game.entries.pacman.mypackage).
 */
public class DTPacMan extends Controller<MOVE>
{
	private MOVE myMove = MOVE.NEUTRAL;
	
	public MOVE getMove(Game game, long timeDue) 
	{
		//Place your game logic here to play the game as Ms Pac-Man
		DecisionTree decisionTree = new DecisionTree("data/decisionMaking/decisionTree");
		ActionType action = decisionTree.makeDecision();

		// Perform the action chosen by the tree.
		switch (action) {
			case NEAREST_PILL:
				break;
			case ATTACK:
				break;
			case RUN:
				break;
			default:
				break;
		}

		return MOVE.NEUTRAL;
	}
}
