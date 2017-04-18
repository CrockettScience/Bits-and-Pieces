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

import java.util.NoSuchElementException;
import java.util.Random;

/**
 *
 * @author Jonathan Crockett
 * @param <T>
 */
public class Thought<T> {
    Neuron<T> current;
    Random rand = new Random();

    Thought(Neuron<T> startThought) {
        current = startThought;
    }

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
