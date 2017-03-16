package pacman.entries.pacman;

import pacman.controllers.Controller;
import pacman.decisionMaking.ActionType;
import pacman.decisionMaking.DecisionTree;
import pacman.decisionMaking.Util;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

/**
 * This is the class uses a decision tree to decide which move to take next.
 * The class has a tree called decisionTree and calls decisionTree.makeDecision(game) to
 * find the best course of action.
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
		myMove = this.decisionTree.makeDecision(game, myMove);

		// Perform the move chosen by the tree.
		return myMove;
	}
}
