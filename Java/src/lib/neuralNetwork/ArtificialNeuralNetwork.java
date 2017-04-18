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
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Jonathan Crockett
 */
public interface ArtificialNeuralNetwork<T> {
    public void learn(List<T> knowledgeSource, T thoughtDelimiter) throws FileNotFoundException;
    public Thought<T> getThought(T initial);
}
