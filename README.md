# minesweeper-solver

A java project, that tries to solve a minesweeper game. Firstly, it connects to the game itself, which is placed on a website (http://minesweeperonline.com/), waits 5 seconds and then do its "magic".

The success rates and solving times are as following:
- beginner: ~95%, 1 - 2 seconds,
- intermediate: ~90%, 2 - 3 seconds,
- expert: ~2% (hard to tell, this level demands a lot of guesses and I wasn't able to figure out a better algorithm), 6 - 8 seconds if the try is actually successful.

Unfortunately, the program to work everywhere needs a proper coordinates of a middle of top-left square, as well as the distance to the middle of a next square.
