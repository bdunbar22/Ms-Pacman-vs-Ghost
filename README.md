#Hello

This repository is an exploration of Game Artificial Intelligence.

Our code for Assignment 3 can be found in the following files:

src/pacman/entries/pacman/DTPacman
src/pacman/entries/pacman/RAPPacman

src/pacman/decisionMaking/**

data/decisionMaking/**
data/decisionMaking/readme

test/pacman/entries/pacman/**
test/pacman/decisionMaking/**


#Running
We have already set the number of lives to 10, but haven't written any code to stop after 5 min
or one leve.

Our


#Explanations
Our implementations work by having an enum for possible actions and an enum for entity
types to perform comparisons with. When the text files are read, comparisions and
actions are dictated by strings being converted into these enums so that clean switch
cases can be used later on. Comparisons are performed against an editable distance
in the text file for each made and the calculated distance from pacman to the
nearest entity of the entity type of the condition.


In our Decision tree implementation, our text file and classes allow the dynamic creation of
a binary tree. DTPacman is initialized with a text file location and loads the tree into a
variable. The tree is made of of two types of nodes, Decisions and Actions. Decision nodes run
a comparision and recursively call a child. Action nodes return an action type to be turned into
a move. When getMove is called on DTPacman a recursive makeDecision function is called on the
root of the tree and eventually returns with a final action.
DTPacman converts this action into a move using a util file which we created. In
this way, our text file can easily allow for the creation of different decision trees. Pacman
can chase edible ghosts, go towards power pills, go towards pill, and run away from ghosts. The
tree can be simple, or could be complex to fine tune pacmans actions. Users can add nodes to the
tree to make it more complex, or just edit the distances used for comparisons to try and optimize
a certain tree structure.


In our Raps implementation, our text file and classes allow for the dynamic creation of a list of
Raps. There are primitive and non-primitive raps. RAPPacman will load all of its non-primitive
raps onto an execution queue when getMove is called. A while loop will pop objects of the the
queue while the queue is not empty and there is still time.

In cases where the object popped of the the queue is a rap then the executeRap function of that
rap will be called. A non-primitive rap will first perform a goal check, then run a validity check
based on its preconditions. If needed then sub raps from a task net will be added to the
execution queue and the rap will be added back on to the queue after them so it can recheck its
goal later.

A primitive rap (which will be an eventual sub rap in our implementation) will also have
its executeRap called when it is popped off of the queue. The primitive rap will
first run its goal check and return true if the goal is satisfied. Then it will also run a
validity check just like a normal rap. The difference for a primitive rap is that if its
validity check passes then it will add a command (actionType) to the execution queue instead of
raps from a task net.

If the object popped off of the queue is an action type, a move is found from the action type
and the cycle will exit because a move is the end goal of the function. (In a more complex
game, the execution queue would send the command to hardware and keep going.)

After analysis of the execution queue results in a move, the getMove function will return with
that move.


Finally, the util file we created is slightly different from the starter pacman code
because it splits power pills and normal pills into two different goals. Additionally, we
slightly improved the functionality of running away. Our util file converts actions to moves as
follows:
NEAREST_POWER_PILL - give the move towards the nearest available power pill
NEAREST_PILL - give the move towards the nearest available pill
ATTACK - give the move towards the nearest edible ghost
RUN_AWAY - give a move away from the closest ghosts. If not possible, try to go to a junction.


#Conclusions
We created two text files for each method and then for each of these 4 implementations of
pacman we ran 250 games on each of the maps (with 10 lives). For each method the second test
file has the same structure/number of nodes, but tries different comparison distances.

In general our decisions trees had a little more logic added in and performed better.
However, with more work to the rap text files, they also have the capability of giving the same
or very similar logic. So for a simple game like pacman, either method is reasonable, and a
decision tree would be our preference because it is a little more straightforward and the
flexibility of Raps aren't necessary for a simple game which just desires one command per turn.
The tests we ran resulted in 1000 runs for each of 4 implementations. Summarized below:

Decision Tree
decisionTree.txt  - AVERAGE: 18579.83  MIN: 4250.0  MAX: 47520.0
decisionTree2.txt - AVERAGE: 18173.44  MIN: 4690.0  MAX: 47730.0


Raps
rapText.txt  - AVERAGE: 14923.19  MIN: 1340.0  MAX: 45320.0
rapText2.txt - AVERAGE: 16407.78  MIN: 3320.0  MAX: 40260.0


Best,
Ben & Mengling

#More Info
For more information on the provided trees based on the first two submitted text files,
please see the included pdf document: GameAIHW3.pdf

This repo was originally forked from: http://joseatovar.github.io/Ms-Pacman-vs-Ghost/
