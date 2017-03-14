Hello,

This repository is an exploration of Game Artificial Intelligence.

Our code for Assignment 3 can be found in the following files:

src/pacman/entries/pacman/DTPacman
src/pacman/entries/pacman/RAPPacman

src/pacman/decisionMaking/**

data/decisionMaking/**
data/decisionMaking/readme

test/pacman/entries/pacman/**
test/pacman/decisionMaking/**


Our decision tree works by having a enum for possible actions and an enum for entity
types to perform comparisons with. Comparisons are performed against a editable distance
in the text file for each decision node and the calculated distance from pacman to the
nearest entity of that type. Once the makeDecision function returns with a final action,
DTPacman converts that action into a move. In this way, the decision tree can easily be
edited to try out different distances at which pacman should run away from ghosts, chase
edible ghosts, go towards power pills and the rest. The tree can be simple, or could be
complex to fine tune pacman actions. The functionality of the provided run away action will
try to pick a logical move away from the nearest ghost(s), but is not perfect so it
has not be relied upon fully on the tree text file provided.

Best,
Ben & Mengling

For more information please see the included pdf document: GameAIHW3.pdf

This repo was originally forked from: http://joseatovar.github.io/Ms-Pacman-vs-Ghost/
