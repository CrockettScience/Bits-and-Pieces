package lib.saveableStructures.demo;


/**
 *
 * @author Jonathan Crockett
 */
public class Map<K,V> {
    private static final int DEFAULT_TABLE_SIZE = 101;
    
    MapEntry<K,V>[] mapTable;
    private int occupied;
    private int currentSize;

    public Map(){
        clear();
    }
    
    public int size() {
        return currentSize;
    }

    public boolean isEmpty() {
        return currentSize == 0;
    }

    public boolean containsKey(K key) {
        int currentPos = findPos(key);
        K other = mapTable[currentPos] == null ? null : mapTable[currentPos].key;
        
        return key.equals(other);
    }

    public boolean put(K key, V value) {
        return add(constructMapEntry(key, value));
    }
    
    public V get(K key) {
        return mapTable[findPos(key)].value;
    }
    
    boolean add(MapEntry<K,V> entry) {        
        int currentPos = findPos(entry.key);
        
        if(mapTable[currentPos] != null){
            if(mapTable[currentPos].isActive)
                return false;
        }
        else
            occupied++;
        
        mapTable[currentPos] = entry;
        currentSize++;
        
        if(occupied > mapTable.length / 2)        
            rehash();
        
        return true;
    }

    public boolean remove(K key) {
        int currentPos = findPos(key);
        
        if(mapTable[currentPos] == null || !mapTable[currentPos].isActive)
            return false;
        
        mapTable[currentPos].isActive = false;
        currentSize--;
        
        return true;
    }

    public void clear() {
        allocateArray(DEFAULT_TABLE_SIZE);
        currentSize = 0;
        occupied = 0;
    }
    
    public void printAll(){
        System.out.println("Active Map Entries:\n");
        for(MapEntry entry : mapTable){
            if(entry != null && entry.isActive){
                System.out.println("Key: " + entry.key);
                System.out.println("Value: " + entry.value + "\n");
            }
        }
    }
    
    private int findPos(K key){
        int offset = 1;
        int currentPos = Math.abs(key.hashCode() % mapTable.length);

        while(mapTable[currentPos] != null){
            if(key.equals(mapTable[currentPos].key))   
                break;
            
            currentPos += offset;
            offset += 2;
            
            if(currentPos >= mapTable.length)
                currentPos -= mapTable.length;
        }
        return currentPos;
    }
    
    private void rehash(){
        MapEntry[] oldArray = mapTable;
        
        allocateArray(nextPrime(4 * currentSize));
        currentSize = 0;
        
        for (MapEntry entry : oldArray) {
            if (entry != null) {
                add(entry);
            }
        }
    }
    
    private static int nextPrime(int n){
        if(n % 2 == 0)
            n++;

        for( ;!isPrime(n) ;)
            n += 2;

        return n;
    }
    
    private static boolean isPrime(int n){
        if(n == 2 || n == 3)
            return true;

        if(n == 1 || n % 2 == 0)
            return false;

        for(int i = 3; i * i <= n; i += 2)
            if(n % i == 0)
                return false;

        return true;
    }
    
    private void allocateArray(int arraySize){
        mapTable = new MapEntry[nextPrime(arraySize)];
    }
    
    protected MapEntry<K,V> constructMapEntry(K key, V val){
        return new MapEntry(key, val);
    }
    
    protected class MapEntry<K,V> {
        protected V value;
        protected K key;
        boolean isActive;
        
        protected MapEntry(K k, V v) {
            key = k;
            value = v;
            isActive = true;
        }
    }
}
