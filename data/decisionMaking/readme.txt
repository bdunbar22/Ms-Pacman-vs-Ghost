The files we will use to load decision making into decision trees and raps
are described in this readme. Please see the two types below.

DecisionTreeText:
- The first line of the file is the number of decision tree nodes in the file.

- Each line of the file will represent a Decision tree node.

- The tree should be added to the file so that the root is the first node.

- The items in a line will be tab delimited.

- The first item is an id for the node, they should go in order from 0 to numberOfNodes - 1
- The second item is the node type, an enum with choices: ACTION, DECISION

The next items depend on the type.
ACTION
- The third item is the action itself
End Of Line

DECISION
- The third item is the condition
- The fourth item is the left node id
- The fifth item is the right node id
End Of Line


RapsText:
