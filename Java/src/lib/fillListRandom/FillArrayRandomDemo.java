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
package lib.fillListRandom;

import java.util.Random;
import java.util.ArrayList;

public class FillArrayRandomDemo {
    
    private static final Random RAND_NUM_GEN = new Random();
    
    public static <T> void fillArrayListRandom(ArrayList<T> arr, RandValue<T> randFunction, int elements){
        for(int i = 0; i < elements; i++){
            arr.add(randFunction.nextValue(RAND_NUM_GEN));
        }
    }
    
    public static void printArrayList(ArrayList<?> arr){
        for(int i = 0; i < arr.size(); i++){
            System.out.print(arr.get(i) + " ");
        }
        System.out.println();
    }
    
    public static void main(String[] args){
        
        System.out.println("\nThe fillListRandom package demonstrates the use of an API for \n"
                         + "generating random instances of anything. It's extremely useful \n"
                         + "for testing generics in data structures to ensure they truly\n"
                         + "work for as many different reference types as possible. \n"
                         + "To use, create a class that implements the RandValue interface\n"
                         + "with a parameterized type of the reference type you're looking \n"
                         + "to create, then provide some implementation of the nextValue \n"
                         + "method that uses the provided instance of Random to instantiate and \n"
                         + "return an instance of the type.\n\n"
                         + "Demonstration:");
        
        ArrayList<Integer> intArr = new ArrayList<>();
        ArrayList<String> strArr = new ArrayList<>();
        ArrayList<Random> ranArr = new ArrayList<>();
        
        fillArrayListRandom(intArr, new RandInteger(1000), 10);
        fillArrayListRandom(strArr, new RandString(8), 10);
        fillArrayListRandom(ranArr, new RandRandom(), 10);
        
        System.out.println("\nList of random Integers:");
        printArrayList(intArr);
        System.out.println("\nList of random Strings:");
        printArrayList(strArr);
        System.out.println("\nList of random Randoms:");
        printArrayList(ranArr);
    }
}
