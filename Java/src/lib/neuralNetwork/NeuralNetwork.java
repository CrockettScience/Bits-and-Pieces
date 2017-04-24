/*
 * Copyright (c) 2017 Jonathan Crockett.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Jonathan Crockett - initial API and implementation and/or initial documentation
 */

package lib.neuralNetwork;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.swing.JFileChooser;

/**
 *
 * @author Jonathan Crockett
 */
public class NeuralNetwork {
    public static void main(String[] args) {
        
        System.out.printf("%-64s",
                          "This is a very simple implementation of a neural network that I made for fun\n"
                        + "as a personal experiment. It uses a design model known as the\n"
                        + "\"Hidden Markov Model,\" which works by objectifying neurons within the\n"
                        + "network as nodes of a procedurally generated directed graph, each\n"
                        + "of which has a discreet number of connections to one another (called\n"
                        + "\"Axions\" in my design) that represent statistical likelihoods to\n"
                        + "other nodes in the graph, much like a Markov Chain. In this example,\n"
                        + "the neural network operates with String data types, and a prompt will\n"
                        + "ask for a file to read. An excerpt from \"A Tale of Two Cities\" has\n"
                        + "been supplied for convenience, located in java\\src\\lib\\neuralNetwork\\sample.txt.\n"
                        + "After the network reads the file, another prompt will ask you to type\n"
                        + "part of a sentence, and then the network will try to complete the\n"
                        + "sentence for you.\n"
                      + "\nThe network's output is rarely a truly cohesive thought, but amazingly\n"
                        + "still tends to output syntactically sensible language with absolutely\n"
                        + "zero natural language processing. The main trouble with this program\n"
                        + "results from the use of synonyms and other words with uses in different\n"
                        + "contexts. Consider these two sentences:\n"
                        + "\"Look, that is a dog.\"\n"
                        + "\"What is that.\"\n"
                        + "The word 'that' is used in both sentences, but the network can potentially\n"
                        + "output a sentence like \"Look at that is a dog,\" which is not cohesive.\n"
                        + "Press enter to continue...");
        
        
        Scanner in = new Scanner(System.in);
        
        if(in.nextLine() != null){

            ArtificialNeuralNetwork<String> ai = null;
            JFileChooser jfc = new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);

            if(jfc.showOpenDialog(null) != JFileChooser.APPROVE_OPTION){
                System.exit(0);
            }

            try{
                ai = new HiddenMarkovModel(processFile(jfc.getSelectedFile(), "[]"), "[]");

            }catch(FileNotFoundException e) {
                System.out.println("Error: File not found. " + e);
                System.exit(0);    
            }

            boolean running = true;
            while(running) {
                System.out.println("Type part of a sentence and press enter and the network will try to finish your sentence, or type \"0\" to go back:");
                String line = in.nextLine();

                if(line.equals("0"))
                    running = false;

                else{
                    Scanner lineScan = new Scanner(line);
                    String lastWord = "";

                    while(lineScan.hasNext()) {
                        lastWord = lineScan.next();
                        System.out.print(lastWord + " ");
                    }

                    Thought thought = ai.getThought(lastWord);

                    try{
                        thought.next();

                    }catch(NoSuchElementException e){
                        System.out.println("...sorry, I don't know that word");

                    }

                    while(thought.hasNext()) {
                            System.out.print(thought.next() + " ");
                    }
                    System.out.println();
                }
            }
        }
    }
    
    public static List<String> processFile(File file, String delimiter) throws FileNotFoundException {
        LinkedList<String> wordList = new LinkedList<>();
        
        Scanner scn = new Scanner(file);
        
        while(scn.hasNext()){
            String word = scn.next().replaceAll("[\\p{Punct}&&[^0-9]&&[^.]&&[^?]&&[^!]]", "").toLowerCase();
            
            if(word.endsWith(".") || word.endsWith("?") || word.endsWith("!")){
                wordList.add(word);
                word = delimiter;
            }
                
            wordList.add(word);
        }
        
        return wordList;
    }
}