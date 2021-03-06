package src.java.main.DP;
import java.io.*;
import java.util.*;
import java.lang.*;

/**
 Author : Ankur Upadhyay
 
 DP is the class containing the implementation of following Dynamic programming problems:
 
 1. Find minimum number of coins needed to make a required change value
 2. Get Incremental subsequence in the array
 3. Find the maximum gift value from the n*m board.
 4. Knapsack problem
 5. Edit Distance problem.
 
 Usage: Instantiate the class and call any dynamic programming implementation functions
        Please refer to individual implementation documentation for input and output parameters.
 */

public class DP{
    
    // main function
    public static void main(String args[]){
        System.out.println("A class of Dynamic programming problems");
        System.out.println("instantiate the class and call any dynamic programming implementations");
    }

    /*
     Problem: Given an array of value coins (sorted in descending order) and the change value,
     find the minimum number of coins needed from the array whose values sum up to the target change value.
     
     Solution: For every coin in the array, the function assumes that it is the part of the optimal solution and then
     iterates over the rest of the array to find the remaining coins which would make up the optimal solution. Thus,
     the function gets one optimal solution for every coin in the array. For example, for the array of n coins, it
     gets n solutions. The function then stores all n solutions and then finds the most optimal solution among them 
     i.e. the solution which has the minimum number of coins.
    
     input parameters : 
        changeCoins:  an array containing the values of the coins available
        change : the target value of the change
     return value:
        int[] : an array containing values of the coins needed to make up the change.
            The number of elements in the array is equal to the minimum number of coins 
            needed to make up the change.
     
     analysis:
        run time complexity : O(n^2) 
                For every element in the array, the function iterates over remaining elements
                to find the combination of the coins.
        space time complexity : O(n^2)
     */
    public int[] findMinimumCoins(int[] changeCoins, int change){
        // HashMap storage for storing the coin combinations.
        HashMap<Integer, ArrayList<Integer>> storage = new HasshMap<Integer, ArrayList<Integer>>();
        
        // store the coins combination in the hash map.
        for(int i=0; i<changeCoins.length; i++){
            int maxCoinsAllowed = change/changeCoins[i];
            ArrayList<Integer> coinsNeeded = new ArrayList<Integer>();
            if(maxCoinsAllowed > 0){
                int changeRemainder = change - (maxCoinsAllowed*changeCoins[i]);
                for(int k=0; k<maxCoinsAllowed; k++){
                    coinsNeeded.add(changeCoins[i]);
                }
                for(int j=(i+1); j<changeCoins.length; j++){
                    maxCoinsAllowed = changeRemainder/changeCoins[j];
                    for(int k=0; k<maxCoinsAllowed; k++){
                        coinsNeeded.add(changeCoins[j]);
                    }
                    changeRemainder = changeRemainder - (maxCoinsAllowed*changeCoins[j]);
                }
            }
            storage.put(i, coinsNeeded);
        }
        
        // find the combination with minimum number of coins.
        int minSize = 0;
        ArrayList<Integer> minCoins = new ArrayList<Integer>();
        for (Map.Entry<Integer, ArrayList<Integer>> entry : storage.entrySet()){
            int coin = entry.getKey();
            ArrayList<Integer> coins = entry.getValue();
            if(minSize == 0){
                minSize = coins.size();
                minCoins = coins;
            }else{
                if(minSize > coins.size()){
                    minSize = coins.size();
                    minCoins = coins;
                }
            }
        }
        return minCoins.toArray();
    }
    
    
    /*
     Problem: Given the array, find the longest subsequence of such that all the 
        elements in the subsequence are sorted in ascending order.
     
     Solution: The function assumes every element as the part of the optimal solution 
        and then iterates over the rest of the array to find the other elements that satisfy the
        requirements of increasing subsequence. It stores all the subsequences in ArrayList<ArrayList<Integer>>
        It then iterates over the arraylist to find the longest subsequence.
     
     input parameters: 
        int[] input : the array containing the elements.
     return value:
        array containing elements which are the part of longest subsequence.
     
     analysis:
        run time complexity : O(n^2)
            For every element in an array, the function iterates over the remaining elements in the array
            to find the increasing sequence.
        space time complexity : O(n^2)
            the algoritm stores the increasing subsequence for every element in an array.
     
     sample input: int[] input = {7, 2, 3, 1, 5, 8, 9, 6, 10, 11, 4, 12};
     
     */
    public int[] getMaxIncrementalSubsequence(int[] input){
        // Structure for storing all the increasing sequences
        ArrayList<ArrayList<Integer>> storage = new ArrayList<ArrayList<Integer>>();
        for(int i=0; i<input.length; i++){
            ArrayList<Integer> subList = new ArrayList<Integer>();
            subList.add(input[i]);
            for(ArrayList<Integer> existingList:storage){
                if(existingList.get(existingList.size()-1) < input[i]){
                    existingList.add(input[i]);
                }
            }
            storage.add(subList);
        }
        
        // find the longest increasing subsequence among all the sub-sequences.
        int max_size=0;
        ArrayList<Integer> max_sequence = new ArrayList<Integer>();
        for(ArrayList<Integer> subList:storage){
            if(subList.size() > max_size){
                max_size = subList.size();
                max_sequence = subList;
            }
        }
        
        return max_sequence.toArray();
    }
    
    /*
        Problem:  A board has n*m cells, and there is a gift with some value (value is greater than 0) in every cell. 
                 You can get gifts starting from the top-left cell, and move right or down in each step, and finally 
                reach the cell at the bottom-right cell. What’s the maximal value of gifts you can get from the board?
        
        Solution: Let the value of the gift at cell (i,j) be denoted by function V(i, j)
                At any cell (i, j) of the board, the optimal solution is as follows:
                max((V(i-1, j) + V(i, j)), (V(i, j-1) + V(i, j)))
                when i=0 (i.e first row), the cell (0, j) can ony be reached by (i-1, j)
                when j=0 (i.e. first column) the cell (i, 0) can only be reached by (0, j-1)
    
                The solution iterates through all the cells and calculates the value of optimal solution
                and stores in the 2-D array.
     
        analysis:
                time complexity : O(n^2).
                                 the solution iterates through the n*m cells
                space complexity : O(n^2)
                                the solution stoeres the optimal gift value for each n*m cells
     
        input parameters: A 2-D array representing the board of gifts
        sample input : int[][] gifts = new int [][] {
                                        {1, 10, 3, 8},
                                        {12, 2, 9, 6},
                                        {5, 7, 4, 11},
                                        {3, 7, 16, 5}
                                    };
        output : array containing the selected gifts
     
     */
    public int[] getMaximumGiftsValue(int[][] gifts){
        
        int rows = gifts.length;
        int columns = gifts[0].length;

        int[][] working_array = new int[rows][columns];
        String[][] path_array = new String[rows][columns];
        
        // initialize the working array for first row and first column
        working_array[0][0] = gifts[0][0];
        path_array[0][0] = "bottom";
        for(int i=1; i<rows; ++i){
            working_array[i][0] = working_array[i-1][0] + gifts[i][0];
            path_array[i][0] = "bottom";
        }
        
        for(int j=1; j<columns; ++j){
            working_array[0][j] = working_array[0][j-1] + gifts[0][j];
            path_array[0][j] = "right";
        }
        
        for(int i=1; i<rows; ++i){
            for(int j=1; j<columns; ++j){
                int bottom_value = gifts[i][j] + working_array[i-1][j];
                int right_value = gifts[i][j] + working_array[i][j-1];
                int max_cost = Math.max(bottom_value, right_value);
                if(max_cost == bottom_value){
                    path_array[i][j] = "bottom";
                }else{
                    path_array[i][j] = "right";
                }
                working_array[i][j] = max_cost;
            }
        }
        
        System.out.println("maximum value of gifts :: " + working_array[rows-1][columns-1]);
        // array list to to store the gifts selecteds
        ArrayList<Integer> selected_gifts = new ArrayList<Integer>();
        int i = rows-1;
        int j = columns-1;
        
        selected_gifts.add(gifts[i][j]);
        while(i > 0 || j > 0){
            if(path_array[i][j] == "bottom"){
                selected_gifts.add(gifts[i-1][j]);
                i--;
            }
            if(path_array[i][j] == "right"){
                selected_gifts.add(gifts[i][j-1]);
                j--;
            }
        }
        
        return selected_gifts.toArray();
    }
    
    
    /*
     problem :  Knapsack problem:
                Given a set of items, each with the weight and value, determine the number of each item
                to include in a collection so that the total weight is less than  or equal to a given limit
                and the total value is as large as possible
     
     solution: The algorithm constructs n solutions, one for each elment, and then finally selects the most optimal solution
                For every element (i) in the weight array, the algrithm iterates over the array to select the elements such that
                the sum of weights of selected elements and sum of weight of element i, is less than or equal to target weight.
                Finally, of all the solutions, the algorithm selects the solution in which the sum of values of the elements is maximum.
                The algorithm stores all the solutions in a Hashmap<Integer, ArrayList<Integer>> where key is the index of array elements
                and value is the ArrayList<Integer> containing the index of the array elements consitituing the solution respective to key.
     
     analysis:
            time complexity : O(n^2)
            space complexity : O(n^2)
     
     input parameters : int[] values :  the array containing the values of the elements
                        int[] weights : the array containing the values of the elements (sorted in increasing order)
                        int max_weight :  the max allowable weight
     
                        sample inputs : 
                         int[] values = {15, 9, 5, 10, 20};
                         int[] weights = {1, 3, 4, 5, 6};
                         int max_weight = 15;
     
     output : int[] containing the index of array elements constituting the index of the optimal solution
     */
    public int[] Knapsack(int[] values, int[] weights, int max_weight){
        int number_of_elements = 5;
        
        int number_of_elements = values.length;
        
        HashMap<Integer, ArrayList<Integer>> storage = new HashMap<Integer, ArrayList<Integer>>();
        
        for (int i = (number_of_elements-1); i>= 0; i--){
            ArrayList<Integer> elements = new ArrayList<Integer>();
            int current_weight = weights[i];
            elements.add(i);
            for(int j = (i-1); j>=0; j--){
                int new_weight = current_weight;
                new_weight += weights[j];
                if(new_weight > max_weight){
                    continue;
                }
                current_weight = new_weight;
                elements.add(j);
            }
            storage.put(i, elements);
        }
        
        int max_value = 0;
        int counter = 0;
        ArrayList<Integer> selected_elements = new ArrayList<Integer>();
        for (Map.Entry<Integer, ArrayList<Integer>> entry : storage.entrySet()){
            ArrayList<Integer> elements = entry.getValue();
            int weight = 0;
            int value = 0;
            for(int element:elements){
                weight += weights[element];
                value += values[element];
            }
            if(weight <= max_weight){
                if(counter==0){
                    max_value = value;
                    selected_elements = elements;
                    counter++;
                }else{
                    if(value >= max_value){
                        max_value = value;
                        selected_elements = elements;
                    }
                }
            }
        }
        
        selected_elements.toArray();
    }
    
    
    
    /*
     problem : edit distance problem
                Given two strings, find the minimum cost to convert source_string into destination_string.
                There are only three operations: insertion, deletion, replacement. Every operation has its
                own cost associated with it.
     
     solution: Its a dynamic programming problem, where the optimal solution is a superset of the optimal solutions
                of smaller inputs. 
                lets say cost[i][j] is the minimum cost of coverting source string of length i to destination string
                of length j. The dynamic programming module is as follows:
                    if charAt(i+1) == charAt(j+1):
                        cost[i+1][j+1] = cost[i][j] (No operation is required since next character of source and destination is same)
                    else:
                        (If next character of source is not equal to the next character of destination)
                        cost[i+1][j+1] = min(cost[i-1][j] + insertion_cost,
                                            cost[i-1][j-1] + replacement_cost,
                                            cost[i][j-1] + deletion_cost)
                The algorithm stores the costs in a 2-D array.
     
     analysis: time complexity :  O(n^2)
                space complexity :  O(n^2)
     
     input parameters:  String source :  source_string
                        String destination :  destination_string
     
     output : min cost to convert source_string into destination_string.
     
     sample input parameters :  String source = "saturday";
                                String destination = "sunday";
     */
    public int editDistance(String source, String destination){
     
        
        int source_length = source.length();
        int destination_length = destination.length();
        
        // source is on the Y axis
        // destination is on the X axis
        int[][] costs = new int[destination_length][source_length];
        
        int insertion_cost = 1;
        int deletion_cost = 1;
        int replacement_cost = 1;
        
        // if the source string is null, then we have to do nothing
        // in this case all the costs will be 0
        for(int i=0; i<destination_length; i++){
            costs[i][0] = i * insertion_cost;
        }
        
        // if the destination string is null, then in order to convert
        // source to destination we need to delete the characters from the source
        for(int j=0; j<source_length; j++){
            costs[0][j] = j * deletion_cost;
        }
        
        for(int i=1; i<destination_length; i++){
            for(int j=1; j<source_length; j++){
                
                if(destination.charAt(i) == source.charAt(j)){
                    costs[i][j] = costs[i-1][j-1];
                }else{
                    int ioc = costs[i-1][j] + insertion_cost;
                    int roc = costs[i-1][j-1] + replacement_cost;
                    int doc = costs[i][j-1] + deletion_cost;
                    
                    int min_cost = Math.min(ioc, Math.min(roc, doc));
                    costs[i][j] = min_cost;
                }
            }
        }
        
        return costs[destination_length-1][source_length-1];
    }
}