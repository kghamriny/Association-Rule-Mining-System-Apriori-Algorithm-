Implementation of the Apriori Algorithm for Association Rule Mining

Program Name: RuleMining.java
Author: Kareem Elghamriny
Date: April 2026

REQUIRED FILES:
RuleMining.java - Main program file (Apriori implementation)
Weather.java - Class representing individual transactions
WeatherData.java - Data structure for storing and processing transactions
Play_Tennis_Data_Set.csv - Input transactional dataset (must be in same directory as RuleMining.java)

REQUIREMENTS:
Java Development Kit (JDK 8 or higher)
No external libraries required
Standard Java libraries used:
java.io.*
java.util.*

COMPILATION & EXECUTION:
Ensure all .java files and Play_Tennis_Data_Set.csv are in the same directory
Open a terminal in that directory

Compile the program using:

javac RuleMining.java Weather.java WeatherData.java

Run the program using:

java RuleMining

The program will prompt the user to enter:
Minimum Support
Minimum Confidence

Output will be written to a file called:

Rules.txt

CONFIGURATION PARAMETERS:

The following parameters can be adjusted in RuleMining.java:

Input dataset file name (default: "Play_Tennis_Data_Set.csv")
Minimum support (user input at runtime)
Minimum confidence (user input at runtime)

Additional internal behaviors:

Automatic Apriori candidate generation
Dynamic pruning based on support threshold
Rule generation based on confidence threshold

Sample Output (Rules.txt):
1. User Input:
Support=0.3
Confidence=0.7

2. Rules:

Rule#1: {Outlook=Sunny}=>{PlayTennis=No}
(Support=0.40, Confidence=0.80)

Rule#2: {Humidity=High}=>{PlayTennis=No}
(Support=0.35, Confidence=0.75)

Rule#3: {Windy=FALSE}=>{PlayTennis=Yes}
(Support=0.45, Confidence=0.82)
