package pacman.decisionMaking;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Allow for reading in a text file and making a decision tree.
 *
 * Created by Ben on 2/27/17.
 */
public class DecisionTree {

    private DecisionTreeNode[] decisionTree;
    private String fileName;

    public DecisionTree(String fileName) {
        this.fileName = fileName;
        this.loadDecisionTree();
    }

    /**
     * Loads all the lines from text file into a decision tree.
     * File should be of type: DecisionTreeText. See data/decisionMaking/readme.txt
     *
     */
    private void loadDecisionTree()
    {
        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream
                (fileName + ".txt")));

            String preambleLine = br.readLine();

            int numberOfNodes = Integer.parseInt(preambleLine);
            decisionTree = new DecisionTreeNode[numberOfNodes];

            String input = br.readLine();

            while(input!=null)
            {

                //TODO INPUT NODES!!!!!!
                //TODO BOTH TYPES!!!
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

    public ActionType makeDecision() {
        return decisionTree[0].makeDecision();
    }
}
