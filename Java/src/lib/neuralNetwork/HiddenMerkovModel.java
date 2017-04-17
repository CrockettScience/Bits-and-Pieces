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

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 *
 * @author Jonathan Crockett
 */
public class HiddenMerkovModel<T> implements ArtificialNeuralNetwork<T> {
    private HashMap<T, Neuron<T>> neurons = new HashMap<>();
    
    public HiddenMerkovModel(List<T> knowledgeSource, String thoughtDelimiter) throws FileNotFoundException {
        learn(knowledgeSource, thoughtDelimiter);
    }
    
    public final void learn(List<T> knowledgeSource, String thoughtDelimiter) throws FileNotFoundException {
        
        Iterator<T> itr = knowledgeSource.iterator();
        
        if(!itr.hasNext())
            return;
        
        Neuron<T> last = getNeuron(itr.next());
        putNeuron(last);
        
        if(!itr.hasNext())
            return;
        
        while(itr.hasNext()) {
            Neuron current = getNeuron(itr.next());
            
            if(!current.data.equals(thoughtDelimiter)) {
                last.addConnection(current);
                putNeuron(current);
                last = current;
            }
            else {
                last.addConnection(null);
                if(itr.hasNext()) {
                    last = getNeuron(itr.next());
                    putNeuron(last);
                }
            }
            
        }
    }
    
    public Iterator getThought(T initial) {
        return (Iterator) new Thought(neurons.get(initial));
    }
    
    private Neuron getNeuron(T key) {
        return neurons.containsKey(key) ? neurons.get(key) : new Neuron(key);
    }
    
    private void putNeuron(Neuron<T> neuron) {
        if(!neurons.containsKey(neuron.data))
            neurons.put(neuron.data, neuron);
    }
    
    private class Thought<T> implements Iterator<T> {
        Neuron<T> current;
        Random rand = new Random();

        public Thought(Neuron<T> startThought) {
            current = startThought;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        public T next() {
            if(current == null)
                throw new NoSuchElementException();

            T data = current.data;
            current = current.AdvanceThought(rand);
            return data;
        }

    }
    
}
