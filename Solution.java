import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        Trie myTrie = new Trie();
        
        Scanner in = new Scanner(System.in);
        int numberOfLines = in.nextInt();
        String inputString = in.nextLine();
        for(int i = 0; i < numberOfLines; i++){

            inputString = in.nextLine();
            String[] inputArray = inputString.split(" ");
            if(inputArray[0].compareTo("add") == 0){
                 myTrie.AddWord(inputArray[1],myTrie.topLevelNode);
            }
            else{
                int prefixCount = myTrie.GetPrefixCount(inputArray[1], myTrie.topLevelNode);
                System.out.println(prefixCount);
            }
        } 
    }
}

class Node{
    public int wordCount;
    public int prefixCount;
    
    public Node[] childNodes;
}

class Trie{
    public Node topLevelNode;
    
    Trie(){
        topLevelNode = AddNewNode();
    }
    
    public Node AddNewNode(){
        Node newNode = new Node();
        newNode.wordCount = 0;
        newNode.prefixCount = 0;
        newNode.childNodes = new Node[26];
        for(int i = 0; i< 26; i++){
            newNode.childNodes[i] = null;
        }

        return newNode;
        
    }
    
    public void AddWord(String word, Node currentNode){
        //if the word is empty, we are done
        if(word.isEmpty()){
            currentNode.wordCount++;
            currentNode.prefixCount++;
            return;
        }
        else{
             //if not empty, add nodes and prefix count as necessary
            currentNode.prefixCount++;

            int nextLetter = GetIndexValue(word.charAt(0)); //convert ascii into 0-25 value
            String remainingLetters = word.substring(1, word.length());

            //new postfix
            if(currentNode.childNodes[nextLetter] == null){
                currentNode.childNodes[nextLetter] = AddNewNode();
                
            }
            
            //continue adding the remianing charcaters to teh trie
            AddWord(remainingLetters,currentNode.childNodes[nextLetter]);
        }
    }
    
    public int GetPrefixCount(String word,Node currentNode){
        //end of prefix, return current count
        if(word.isEmpty()){
            return currentNode.prefixCount;
        }
        
        Node nextNode = currentNode.childNodes[GetIndexValue(word.charAt(0))];
        //prefix does not yet exist in trie
        if(nextNode == null){
            return 0;
        }
        //continue working down prefix
        else{
            String remainingLetters = word.substring(1, word.length());
            return GetPrefixCount(remainingLetters,nextNode);
        }
        
    }
    
    public int GetIndexValue(char character){
        return (int) character - 97;
    }
    
}
