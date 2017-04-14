/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.saveableStructures.test;

import java.util.Random;
import lib.fillListRandom.RandValue;

public class RandSaveableInteger implements RandValue<SaveableInteger>{

    @Override
    public SaveableInteger nextValue(Random rand) {
        SaveableInteger i = new SaveableInteger();
        i.value = rand.nextInt(10);
        return i;
    }
    
}
