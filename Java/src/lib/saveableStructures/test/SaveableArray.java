package lib.saveableStructures.test;

import java.util.ArrayList;
import java.util.Iterator;
import lib.saveableStructures.*;

public class SaveableArray<T extends SaveableElement<Chunk32Bit>> extends SaveableCollection<T, Chunk32Bit> {

    private ArrayList<T> arr;
    
    public SaveableArray(int size){
        arr = new ArrayList<>(size);
        ByteChunkBuffer = new Chunk32Bit((byte) 0, (byte) 0, (byte) 0, (byte) 0);
    }
    
    public void get(int i){
        arr.get(i);
    }
    
    public void set(int i, T e){
        arr.set(i, e);
    }

    @Override
    public void add(T e) {
        arr.add(e);
    }

    @Override
    public Iterator<T> iterator() {
        return arr.iterator();
    }
}
