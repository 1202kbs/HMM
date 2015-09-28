# HMM

This library implements 4 types of Hidden Markov Model:

1. Ergodic Model: The most basic type of HMM where it is possible to go from one state to another.

2. Bakis Model: A HMM where no transitions are allowed to states whose indices are lower than the current state.

3. General Left-to-Right Model

4. Linear Model: A Model where transitions are allowed only to oneself or one state higher.

#Architecture

The Strategy Pattern has been used to designate different types of training, decoding, and evlauation.
The Composite and Iterator Pattern has been used for efficient retrieval of States and Observations.
Applications will be updated.

#Updates
All types of structures have been implemented in Java.

Planning to:

1. Add a method for training with multiple observations.

2. Add an implementation of HMM in OCR.
