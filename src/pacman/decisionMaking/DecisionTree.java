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

    private DecisionTreeNode[] decisionTreeNodes;
    private String fileName;

    public DecisionTree(String fileName) {
        this.fileName = fileName;
        this.loadDecisionTree();
    }

    /**
     * Loads all the lines from text file into a decision tree.
     * File should be of type: DecisionTreeText. See data/decisionMaking/readme.txt
     */
    private void loadDecisionTree()
    {
        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream
                (fileName + ".txt")));

            String preambleLine = br.readLine();

            int numberOfNodes = Integer.parseInt(preambleLine);
            decisionTreeNodes = new DecisionTreeNode[numberOfNodes];

            String input = br.readLine();
            int count = 0;

            while(input!=null)
            {
                String[] values = input.split("\t");

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
                    int trueChild = Integer.parseInt(values[4]);
                    int falseChild = Integer.parseInt(values[5]);
                    decisionTreeNodes[index] = new Decision(trueChild, falseChild);
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

    public ActionType makeDecision() {
        return decisionTreeNodes[0].makeDecision(decisionTreeNodes);
    }
}
