/**
 * CSCI 4144 – Assignment 4
 * @author: Kareem Elghamriny, B000940361, March 27, 2026
 * @description: Uses Apriori for Association Rule Mining
 **/

import java.io.*;
import java.util.*;

public class RuleMining {
    public static void main(String[] args) throws FileNotFoundException {
        File inFile = new File("Play_Tennis_Data_Set.csv");
        Scanner input = new Scanner(inFile);
        Scanner in = new Scanner(System.in);

        WeatherData weatherList = new WeatherData();

        //since the first line in the file is a header explaining attributes, we should skip it
        if (input.hasNextLine()) {
            input.nextLine();
        }
        //now, we go into the data
        while (input.hasNext()) {

            /**
             * I learned how to use String tokenizer from Geeks4Geeks:
             * Source: https://www.geeksforgeeks.org/java/stringtokenizer-class-in-java/
             */

            String line = input.nextLine();
            //we can take each attribute as a token
            StringTokenizer token = new StringTokenizer(line, ",");
            String outlook = token.nextToken();
            String temperature = token.nextToken();
            String humidity = token.nextToken();
            String windy = token.nextToken();
            String play = token.nextToken();

            //create the product to add to the list
            Weather weather = new Weather(outlook, temperature, humidity, windy, play);
            weatherList.add(weather);

        }
        input.close();

        /**
         * I learned how to use output to files from StackOverflow:
         * Source: https://stackoverflow.com/questions/2885173/how-do-i-create-a-file-and-write-to-it
         */

        //create output file to print to
        PrintWriter outputFile = new PrintWriter("Rules.txt");

        //ask user for input
        System.out.println("Enter minimum support:");
        double userMinSupport = in.nextDouble();
        System.out.println("Enter minimum confidence:");
        double minConfidence = in.nextDouble();

        int totalAmount = weatherList.getWeatherList().size();

        //As mentioned by Professor Ye in the assignment file,"multiplying min_sup by the total number of
        // tuples in the data set, and thereafter rounding the product up to the nearest integer"
        int computatedMinSupport = (int) Math.ceil(userMinSupport * totalAmount);

        ArrayList<ArrayList<String>> attributeList = new ArrayList<>();
        ArrayList<Integer> attributeListCount = new ArrayList<>();

        for (int i = 0; i < weatherList.getWeatherList().size(); i++) {
            String singleAttribute[] = weatherList.getWeatherList().get(i).toArray();

            for (int j = 0; j < singleAttribute.length; j++) {
                ArrayList<String> attribute = new ArrayList<>();
                attribute.add(singleAttribute[j]);
                int location = containsAttribute(attributeList, attribute);

                if (location == -1) {
                    attributeList.add(attribute);
                    attributeListCount.add(1);
                } else {
                    attributeListCount.set(location,attributeListCount.get(location) + 1);
                }
            }
        }

        /**
         * I learned more about the Apriori Algorithm through Geek4Geeks:
         * Source: https://www.geeksforgeeks.org/machine-learning/apriori-algorithm/
         */

        ArrayList<ArrayList<String>> repetitions = new ArrayList<>();
        ArrayList<Integer> repetitionCounts = new ArrayList<>();

        for (int i = 0; i < attributeList.size(); i++) {
            if (attributeListCount.get(i) >= computatedMinSupport) {
                repetitions.add(attributeList.get(i));
                repetitionCounts.add(attributeListCount.get(i));
            }
        }

        ArrayList<ArrayList<ArrayList<String>>> totalRepetitions = new ArrayList<>();
        ArrayList<ArrayList<Integer>> totalCounts = new ArrayList<>();
        totalRepetitions.add(repetitions);
        totalCounts.add(repetitionCounts);

        while (!repetitions.isEmpty()) {

            ArrayList<ArrayList<String>> newRepetitions = new ArrayList<>();
            ArrayList<Integer> newCounts = new ArrayList<>();

            for (int i = 0; i < repetitions.size(); i++) {
                for (int j = i + 1; j < repetitions.size(); j++) {

                    ArrayList<String> repetition = new ArrayList<>(repetitions.get(i));

                    for (int a = 0; a < repetitions.get(j).size(); a++) {
                        if (!repetition.contains(repetitions.get(j).get(a))) {
                            repetition.add(repetitions.get(j).get(a));
                        }
                    }

                    if (repetition.size() == repetitions.get(i).size() + 1) {
                        int count = weatherList.count(repetition);

                        if (count >= computatedMinSupport && containsAttribute(newRepetitions, repetition) == -1) {
                            newRepetitions.add(repetition);
                            newCounts.add(count);
                        }
                    }
                }
            }
            if (newRepetitions.isEmpty()) {
                break;
            }
            totalRepetitions.add(newRepetitions);
            totalCounts.add(newCounts);
            repetitions = newRepetitions;
        }

        ArrayList<String> firstAttributePrintedList = new ArrayList<>();
        ArrayList<String> resultsList = new ArrayList<>();
        ArrayList<Double> supportList = new ArrayList<>();
        ArrayList<Double> confidenceList = new ArrayList<>();

        int ruleNumber = 1;

        for (int i = 0; i < totalRepetitions.size(); i++) {
            for (int j = 0; j < totalRepetitions.get(i).size(); j++) {

                ArrayList<String> weathers = totalRepetitions.get(i).get(j);
                //since there needs to be atleast 2 items for a rule to be formed
                if (weathers.size() < 2) {
                    continue;
                }

                int itemsetCount = totalCounts.get(i).get(j);

                for (int a = 0; a < weathers.size(); a++) {
                    String result = weathers.get(a);

                    ArrayList<String> firstAttributePrinted = new ArrayList<>(weathers);
                    firstAttributePrinted.remove(result);

                    int firstAttributePrintedCount = weatherList.count(firstAttributePrinted);
                    double support = (double) itemsetCount / totalAmount;
                    double confidence = (double) itemsetCount / firstAttributePrintedCount;

                    if (confidence >= minConfidence) {
                        firstAttributePrintedList.add(toStringList(firstAttributePrinted));
                        resultsList.add(result);
                        supportList.add(support);
                        confidenceList.add(confidence);
                    }
                }
            }
        }

        //I was having issues trying to sort the results to match the format in the test case,
        //and found that this was the best option to do so (It is swapping around rules for correct format).
        for (int i = 0; i < resultsList.size() - 1; i++) {
            for (int j = 0; j < resultsList.size() - i - 1; j++) {
                boolean swap = false;

                if (confidenceList.get(j) < confidenceList.get(j + 1)) {
                    swap = true;
                }
                else if (confidenceList.get(j).equals(confidenceList.get(j + 1))) {
                    if (firstAttributePrintedList.get(j).compareTo(firstAttributePrintedList.get(j + 1)) > 0) {
                        swap = true;
                    }
                    else if (firstAttributePrintedList.get(j).equals(firstAttributePrintedList.get(j + 1)) &&
                            resultsList.get(j).compareTo(resultsList.get(j + 1)) > 0) {
                        swap = true;
                    }
                }

                /**
                 * I referred to this StackOverflow thread because I was facing errors with swapping elements:
                 * (Without Collections just in case it was not allowed in the assignment)
                 * Source: https://stackoverflow.com/questions/40118518/how-two-swap-two-elements-in-a-array-list-without-using-collections-swap-method
                 */

                if (swap) {
                    String tempResults = resultsList.get(j);
                    resultsList.set(j, resultsList.get(j + 1));
                    resultsList.set(j + 1, tempResults);
                    String tempFirstAttributePrinted = firstAttributePrintedList.get(j);
                    firstAttributePrintedList.set(j, firstAttributePrintedList.get(j + 1));
                    firstAttributePrintedList.set(j + 1, tempFirstAttributePrinted);
                    double tempSupport = supportList.get(j);
                    supportList.set(j, supportList.get(j + 1));
                    supportList.set(j + 1, tempSupport);
                    double tempConfidence = confidenceList.get(j);
                    confidenceList.set(j, confidenceList.get(j + 1));
                    confidenceList.set(j + 1, tempConfidence);
                }
            }
        }
        outputFile.println("1. User Input:");
        outputFile.println("Support=" + userMinSupport);
        outputFile.println("Confidence=" + minConfidence);
        outputFile.println("");
        outputFile.println("2. Rules:");

        //using the same format as in assignment here
        for (int i = 0; i < firstAttributePrintedList.size(); i++) {
            outputFile.println("Rule#" + ruleNumber + ": {" + firstAttributePrintedList.get(i) + "}=>{" + resultsList.get(i) + "}");
            outputFile.printf("(Support=%.2f, Confidence=%.2f) \n ", supportList.get(i), confidenceList.get(i));
            ruleNumber++;
        }
        outputFile.close();
    }

    /**
     * Loops through list to see if the desired attribute is present in said list or not
     */
    public static int containsAttribute(ArrayList<ArrayList<String>> list, ArrayList<String> attribute) {
        for (int i = 0; i < list.size(); i++) {
            ArrayList<String> curr = list.get(i);
            if (curr.size() == attribute.size() && curr.containsAll(attribute)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Helps with output (builds toString)
     */
    public static String toStringList(ArrayList<String> list) {
        String output = "";
        for (int i = 0; i < list.size(); i++) {
            output = output + list.get(i);
            if (i != list.size() - 1) {
                output += ", ";
            }
        }
        return output;
    }
}