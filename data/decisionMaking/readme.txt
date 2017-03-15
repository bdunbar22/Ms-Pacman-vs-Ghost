The files we will use to load decision making into decision trees and raps
are described in this readme. Please see the two types below.

DECISION TREE TEXT:
- The first line of the file is the number of decision tree nodes in the file.

- Each line of the file will represent a Decision tree node.

- The tree should be added to the file so that the root is the first node. The rest can be any
  order preferred by the user.

- The items in a line will be comma delimited.

- The first item is an id for the node, they should go in order from 0 to numberOfNodes - 1,
  increasing by 1 each line.

- The second item is the node type, an enum with choices: ACTION, DECISION

The next items depend on the type.
ACTION
- The third item is the action itself (See ActionType within src/pacman/decisionMaking folder)
End Of Line

DECISION
- The third item is the entity in question with condition: GHOST, EDIBLE_GHOST, POWER_PILL, PILL
- The fourth item is the distance we are checking for that item to be within
- The fifth item is the left node id
- The sixth item is the right node id
End Of Line


RAPS TEXT:
NOTE: the first raps added to the execution queue are all of the non-primitive raps. In order
to run a primitive rap, it will have to have been added by a non-primitive rap!
NOTE2: the non-primitive raps have both a min and max distance to use in comparisons!

- The first line of the file is a number, it represents the number of raps provided.

- Each subsequent line of the file will represent a rap.

- Order does not matter, as long as the task net list described below has the right index
numbers.

- The items in a line will be comma delimited.

- The first item is an id for the rap, they should go in order from 0 to numberOfRaps - 1,
  increasing by 1 each line.

- The second item is the rap type, choices: RAP, PRIMITIVE

The next items depend on the type.
RAP
- The third item is the entity in question for the condition: - see EntityTypes enum
- The fourth item is a min distance we are checking for that item to be within
- The fifth item is a max distance we are checking for that item to be within. Use -1 to get
Integer.MAX_VALUE

- The sixth item is the goal - see ActionTypes enum
- The next x items are the task net raps.

PRIMITIVE
- The third item is the entity in question for the condition: - see EntityTypes enum
- The fourth item is the distance we are checking for that item to be within
- The fifth item is the goal - see ActionTypes enum
End Of Line
