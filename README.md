## Instructions

The purpose of this test is to see how you write and read Java code. 
Test assignment consists of 4 tasks 1 of which is done during live coding session.

After you do remaining 3 tasks -
 deliver an updated javatest-<yourname>.zip, 
 which consists of the original javatest.zip plus all your modifications and additions. 

When you are done, **please also write down how long time you spent on each question**, 
if there were any unclear points and what assumptions you made in those cases.

Feel free to ask any questions to your tech contact at Tain.

Also you are free to deliver your results via public VCS(GitHub, BitBucket etc). 

### 1: Refactoring

#### Summary:

Refactor & debug poorly designed classes

#### What to do:
Study the code in the "1-refactoring" module.

Refactor the code and fix any obvious bugs. 
You are free to change the code as much as you like, as long as the overall behaviour of the classes doesn't change.

### 2: Multithreading

#### Summary:

Write a multi-threaded performance test for a fibonacci calculator

#### What to do:

Study the code in the "2-multithreading" module.
Create a class FibCalcImpl which implements FibCalc
Create a class PerformanceTesterImpl which implements PerformanceTester
Write a command line application that runs performance tests on your FibCalcImpl using your PerformanceTesterImpl.
The application should take the following arguments: <n> <calculationCount> <threadPoolSize>
n = which fibonacci number to calculate
calculationCount = how many fibonacci calculations to run in total during the test
threadPoolSize = how many threads should be used to run the calculations
Example: 25, 5, 1 means "calculate fib(25) five times using a single thread".
Example: 25, 40, 10 means "calculate fib(25) forty times using ten threads".
The application should output the three values in the PerformanceTestResult to System.out.
Requirements:

Your code should be easy to read and understand.
You should be able to verbally explain your design considerations.

### 3: PokerHands

#### Summary:

Implement a program which maps a stream of dealer cards in 'Texas Hold'em' -
 like poker to a list of winning hands.

#### What to do:

You have a poker table with next rules: each player is dealt n cards(2 by default) at the beginning(such cards are called 'hole cards').
Then dealer starts drawing cards from the deck of N cards (52 by default) one by one face up. You should write a method which effectively 
maps each drawn card to a list of winning hands. 
 
Differences from classic Texas Hold'em:
1. In this version - dealer draws cards one by one. There are no pre-flop
2. Dealer continues to draw cards after there are 5 cards on a table.
3. Players always stand
4. Once winners are identified - game continues(dealer draws another card) **until dealer draws all cards from a deck**. Players are not kicked out from a table

Winning combinations are standard. You can refer this article for reference:
[Poker winning combinations](https://www.pokerstars.com/poker/games/rules/hand-rankings/)
 
Also make sure to cover cases with tie/kickers:
[Kickers And Ties](https://en.wikipedia.org/wiki/Texas_hold_%27em#Kickers_and_ties)
 
 
Here are some examples of draws with outputs:

```

 Players: John[holeCards=2C 5D], Pete[holeCards=AC TS]
 Turn 1: Dealer picks 4S; Winner:Pete(High card)
 Turn 2: Dealer picks 2D; Winner:John(Pair)
 Turn 3: Dealer picks AH; Winner:Pete(Pair)
 Turn 4: Dealer picks 3D; Winner:John(Straight)
 Turn 5: Dealer picks QD; Winner:John(Straight)
 Turn 6: Dealer picks KS; Winner:John(Straight)
 Turn 7: Dealer picks JD; Winner:Pete(Straight)
 .........

```