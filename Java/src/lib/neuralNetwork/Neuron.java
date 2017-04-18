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

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 *
 * @author Jonathan Crockett
 */
class Neuron<T> {
    T data;
    private ArrayList<Axion> axions = new ArrayList<>();
    
    public Neuron(T value) {
        data = value;
    }
    
    Neuron AdvanceThought(Random rand) {
        if(axions.isEmpty())
            return null;
        return axions.get(rand.nextInt(axions.size())).synapse;
    }
    
    void addConnection(Neuron synapse) {
        Axion axion = new Axion();
        axion.synapse = synapse;
        
        for(Axion a: axions) {
            if(axion.equals(a)){
                
                for(int i = 0; i < a.strength; i++)
                    axions.add(a);
                    
                a.strength = a.strength << 1;
                return; 
            }
        }
        
        axions.add(axion);
    }
    
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Neuron)) {
            return false;
        }
        Neuron other = (Neuron) obj;
        
        return data.equals(other.data);
    }
    
    public int hashCode() {
        return data.hashCode();
    }
}
