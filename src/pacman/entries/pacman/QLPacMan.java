package pacman.entries.pacman;

import pacman.controllers.Controller;
import pacman.decisionMaking.DecisionTree;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.qLearning.QState;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the class uses a decision tree to decide which move to take next.
 * The class has a tree called decisionTree and calls decisionTree.makeDecision(game) to
 * find the best course of action.
 */
public class QLPacMan extends Controller<MOVE>
{
	private MOVE myMove = MOVE.NEUTRAL;
	private Map<QState, List<Integer>> qMap;

	public QLPacMan(String qMapFileLocation) {
		this.loadQMap(qMapFileLocation);
	}
	
	public MOVE getMove(Game game, long timeDue) 
	{
		// Determine state

		// Find Q Map entry and chose an action.

		// Perform the move chosen by the tree.
		return myMove;
	}

	private void loadQMap(String qMapFileLocation) {
		this.qMap = new HashMap<>();
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream
				(qMapFileLocation + ".txt")));

			String preambleLine = br.readLine();

			int numberOfNodes = Integer.parseInt(preambleLine);
			decisionTreeNodes = new DecisionTreeNode[numberOfNodes];

			String input = br.readLine();
			int count = 0;

			while(input!=null && count < numberOfNodes)
			{
				String[] values = input.split(",");

				int index = Integer.parseInt(values[0]);
				if(index != count) {
					throw new Exception("Malformed decision tree text file. Index error: " +
						fileName);
				}

				String nodeType = values[1];
				if(nodeType.equals("ACTION")) {
					String actiontype = values[2];
					decisionTreeNodes[index] = new Action(actiontype);
				} else if(nodeType.equals("DECISION")) {
					String conditionEntity = values[2];
					int conditionDistance = Integer.parseInt(values[3]);
					int falseChild = Integer.parseInt(values[4]);
					int trueChild = Integer.parseInt(values[5]);
					decisionTreeNodes[index] = new Decision(trueChild, falseChild,
						conditionEntity, conditionDistance);
				} else {
					throw new Exception("Malformed decision tree text file. Format error: " +
						fileName);
				}

				input = br.readLine();
				count ++;
			}
		}
		catch(Exception errorSent)
		{
			errorSent.printStackTrace();
		}
	}
}
