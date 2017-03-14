package pacman.entries.pacman;

import pacman.controllers.Controller;
import pacman.decisionMaking.IRap;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/*
 * This is the class you need to modify for your entry. In particular, you need to
 * fill in the getAction() method. Any additional classes you write should either
 * be placed in this package or sub-packages (e.g., game.entries.pacman.mypackage).
 */
public class RAPPacMan extends Controller<MOVE>
{
	private IRap[] raps;
	private Queue<Object> executionQueue = new LinkedList<>();
	private MOVE myMove = MOVE.NEUTRAL;

	public RAPPacMan(String rapFileLocation) {
		this.loadRaps(rapFileLocation);
	}
	
	public MOVE getMove(Game game, long timeDue) 
	{
		//Place your game logic here to play the game as Ms Pac-Man
		return myMove;
	}

	/**
	 * Loads all the lines from text file into Raps
	 * File should be of type: RapsText. See data/decisionMaking/readme.txt
	 *
	 * @param fileName to read
	 */
	private void loadRaps(String fileName)
	{
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream
				(fileName + ".txt")));

            /* Don't need to use preamble line */
			String preambleLine = br.readLine();

			String input = br.readLine();

			while(input!=null)
			{
				String[] values = input.split("\t");

				int index = Integer.parseInt(values[0]);
				int x = Integer.parseInt(values[1]);
				int y = Integer.parseInt(values[2]);


				input = br.readLine();
			}
		}
		catch(Exception errorSent)
		{
			errorSent.printStackTrace();
		}
	}
}
