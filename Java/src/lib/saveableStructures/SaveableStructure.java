package lib.saveableStructures;

import java.io.File;

/**
 *
 * @author Jonathan Crockett
 */
public interface SaveableStructure<T extends SaveableData>{
    
    public abstract boolean save(File file);
    
    public abstract boolean load(File file, CreateData<T> func);
}
