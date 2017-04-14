package lib.saveableStructures.test;

import lib.saveableStructures.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Random;
import lib.fillListRandom.RandValue;
import lib.saveableStructures.StructureIO.CreateElement;

public class SaveableTest {
    
    private static final Random RAND_NUM_GEN = new Random();
    
    public static <T extends SaveableElement<U>, U extends ByteChunk> void fillCollectionRandom(SaveableCollection<T,U> arr, RandValue<T> randFunction, int elements){
        for(int i = 0; i < elements; i++){
            arr.add(randFunction.nextValue(RAND_NUM_GEN));
        }
    }
    
    public static void printArrayList(SaveableCollection<?, ?> arr){
        for(SaveableElement e: arr){
            System.out.print(e + " ");
        }
        System.out.println();
    }
    
    public static void main(String[] args){
        
        System.out.println("\nThe saveableStructures package is a work in progress that\n"
                         + "will be able to save multiple data structures to a single\n"
                         + "binary file regardless of implementation. All that is required\n"
                         + "is that the collection is a concrete subclass of SaveableCollection\n"
                         + "and the data types being stored are concrete subclasses of SaveableElement.\n"
                         + "The following demonstration is a proof of concept by saving an\n"
                         + "array of Integers to a bin file, and loading the same data back into \n"
                         + "memory without any loss of information.\n\n" 
                         + "Demonstration:\n");
        
        SaveableArray<SaveableInteger> arr = new SaveableArray<>(100);
        fillCollectionRandom(arr, new RandSaveableInteger(), 100);
        
        System.out.println("Before saving...");
        printArrayList(arr);
        
        File f;
        try{
            f = new File("testFile.bin");
            FileOutputStream fOut = new FileOutputStream(f);
            StructureIO.saveStructure(fOut, arr);
        } catch (Throwable ex) {
           System.out.println("Saving failed...");
           ex.printStackTrace();
        }
        
        arr = new SaveableArray<>(10);
        
        try{
            f = new File("testFile.bin");
            FileInputStream fIn = new FileInputStream(f);
            
            CreateElement<SaveableInteger> func = () -> {
                
                return new SaveableInteger(); 
            
            };
                    
            StructureIO.loadStructure(fIn, arr, func);
        } catch (Throwable ex) {
           System.out.println("loading failed...");
           ex.printStackTrace();
        }
        
        
        System.out.println("after loading...");
        printArrayList(arr);
        
    }
}
