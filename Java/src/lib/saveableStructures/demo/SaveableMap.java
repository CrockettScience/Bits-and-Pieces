package lib.saveableStructures.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import lib.saveableStructures.SaveableStructure;
import lib.saveableStructures.CreateData;
import lib.saveableStructures.SaveableData;

/**
 *
 * @author Jonathan Crockett
 */
public class SaveableMap<K extends SaveableData, V extends SaveableData> extends Map<K, V> implements SaveableStructure<SaveableMap.SaveableMapEntry<K,V>> {

    @Override
    public boolean save(File file) {
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            for(MapEntry entry : mapTable)
                if(entry != null)
                    fOut.write(((SaveableMapEntry) entry).saveState());
            
            fOut.close();
            return true;
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SaveableMap.class.getName()).log(Level.WARNING, file.toString(), ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(SaveableMap.class.getName()).log(Level.WARNING, "Could not save: an unexpected error has occurred", ex);
            return false;
        }
    }

    @Override
    public boolean load(File file, CreateData<SaveableMapEntry<K,V>> func) {
        try {
            FileInputStream fIn = new FileInputStream(file);
            byte[] buffer = new byte[func.createElement().byteSize()];
            
            while(fIn.available() > 0){
                fIn.read(buffer);
                SaveableMapEntry<K,V> entry = func.createElement();
                entry.loadState(buffer);
                add(entry);
            }
            
            fIn.close();
            return true;
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SaveableMap.class.getName()).log(Level.WARNING, file.toString(), ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(SaveableMap.class.getName()).log(Level.WARNING, "Could not load: an unexpected error has occurred", ex);
            return false;
        }
        
    }
    
    protected MapEntry<K, V> constructMapEntry(K key, V val){
        return new SaveableMapEntry(key, val);
    }
    
    protected static class SaveableMapEntry<K extends SaveableData, V extends SaveableData> extends MapEntry<K, V> implements SaveableData{
        
        protected SaveableMapEntry(K k, V v) {
            super(k, v);
        }
            
        @Override
        public byte[] saveState() {
            byte[] keyBytes = key.saveState();
            byte[] valueBytes = value.saveState();
            byte[] entryBytes = new byte[byteSize()];
            
            int i = 0;
            for(;i < key.byteSize(); i++)
                entryBytes[i] = keyBytes[i];

            for(int j = 0; j < value.byteSize(); i++, j++)
                entryBytes[i] = valueBytes[j];
            
            return entryBytes;
        }

        @Override
        public void loadState(byte[] bytes) {
            key.loadState(Arrays.copyOf(bytes, key.byteSize()));
            value.loadState(Arrays.copyOfRange(bytes, key.byteSize(), key.byteSize() + value.byteSize()));
        }

        @Override
        public int byteSize() {
            return key.byteSize() + value.byteSize();
        }
    }

    public static class CreateKeyValue<K extends SaveableData, V extends SaveableData> implements CreateData<SaveableMapEntry<K,V>>{
        
        CreateData<K> keyCreator;
        CreateData<V> valueCreator;
        
        public CreateKeyValue(CreateData<K> cKey, CreateData<V> cValue) {
            keyCreator = cKey;
            valueCreator = cValue;
        }
        
        @Override
        public SaveableMapEntry<K, V> createElement() {
            return new SaveableMapEntry(keyCreator.createElement(), valueCreator.createElement());
        }
    
    }
}
