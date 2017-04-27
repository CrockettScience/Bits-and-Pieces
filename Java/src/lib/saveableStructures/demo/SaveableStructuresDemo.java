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
package lib.saveableStructures.demo;

import java.io.File;
import java.util.Random;
import lib.fillListRandom.RandString;
import lib.fillListRandom.RandValue;
import lib.saveableStructures.CreateData;
import lib.saveableStructures.SaveableData;

/**
 *
 * @author Jonathan Crockett
 */
public class SaveableStructuresDemo {
    
    public static Random RAND_NUM_GEN = new Random();
    
    public static <K extends SaveableData, V extends SaveableData> void fillMapRandom(SaveableMap<K,V> map, RandValue<K> randKey, RandValue<V> randValue, int elements){
        for(int i = 0; i < elements; i++){
            map.put(randKey.nextValue(RAND_NUM_GEN),randValue.nextValue(RAND_NUM_GEN));
        }
    }
    
    public static void main(String[] args) {
        System.out.println("\nThe saveableStructures package is a project that supplies a couple\n"
                         + "interfaces and functors that work together to save any data structure\n"
                         + "to a single binary file regardless of ADT implementation. All that is\n"
                         + "required is that the structure properly implement SaveableStructure and\n"
                         + "the data types being stored properly implement SaveableData. In the\n"
                         + "following demonstration, I have a class called Map, a simple HashMap\n"
                         + "implementation, and a subclass of Map called SaveableMap, which is all set\n"
                         + "up to save it's key-value pairs to file, clear the map using the clear()\n"
                         + "method, and and load the same data back into the map without any loss of\n"
                         + "information. Note that, as per the definition of any HashMap, the elements\n"
                         + "are not guarenteed to preserve order, even after loading, so the output's\n"
                         + "order of key-value pairs will change, but will all have the same pairs nontheless.\n\n" 
                         + "Demonstration:\n");
        
        SaveableMap<SaveableString, SaveableInteger> map = new SaveableMap<>();
        
        fillMapRandom(map, new RandSaveableString(5), new RandSaveableInteger(100), 5);
        
        File f = new File("saveTest");
        
        System.out.println("Before Saving to file...");
        map.printAll();
        
        map.save(f);
        map.clear();
        map.load(f, new SaveableMap.CreateKeyValue<>(new CreateSaveableString(), new CreateSaveableInteger()));
        
        System.out.println("After Loading from file...");
        map.printAll();
        
    }
    
    public static class RandSaveableInteger implements RandValue<SaveableInteger> {
        int max;

        public RandSaveableInteger(int maxInt){
            max = maxInt;
        }

        @Override
        public SaveableInteger nextValue(Random rand){
            return new SaveableInteger(rand.nextInt(max));
        }
    }
    
    public static class RandSaveableString implements RandValue<SaveableString> {
        private int length;
        
        public RandSaveableString(int size){
            length = size;
        }
        
        @Override
        public SaveableString nextValue(Random rand) {
            return new SaveableString(new RandString(length).nextValue(rand));
        }
    }
    
    public static class CreateSaveableInteger implements CreateData<SaveableInteger> {

        @Override
        public SaveableInteger createElement() {
            return new SaveableInteger(0);
        }
    }
    
    public static class CreateSaveableString implements CreateData<SaveableString> {

        @Override
        public SaveableString createElement() {
            return new SaveableString("");
        }
    }
}
