/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.saveableStructures.test;
import lib.saveableStructures.*;

public class SaveableInteger implements SaveableElement<Chunk32Bit> {
    
    public int value;
    
    @Override
    public Chunk32Bit saveState() {
        int temp = value;
        byte[] b = new byte[4];
        
        b[0] = (byte) ((temp >> 24) & 0xFF);
        b[1] = (byte) ((temp >> 16) & 0xFF);
        b[2] = (byte) ((temp >> 8) & 0xFF);
        b[3] = (byte) (temp & 0xFF);
        
        return new Chunk32Bit(b[0], b[1], b[2], b[3]);
    }

    @Override
    public void loadState(Chunk32Bit bytes) {
        byte[] b = bytes.getBytes();
        value = b[3] & 0xFF |
               (b[2] & 0xFF) << 8 |
               (b[1] & 0xFF) << 16 |
               (b[0] & 0xFF) << 24;
    }
    
    public String toString(){
        return String.valueOf(value);
    }
    
}
