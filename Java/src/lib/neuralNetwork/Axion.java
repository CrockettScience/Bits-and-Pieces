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

/**
 *
 * @author Jonathan Crockett
 */
class Axion{
    Neuron synapse = null;
    int strength = 0;

    public boolean equals(Object obj) {
        if(!(obj instanceof Axion))
            return false;
        Axion axion = (Axion) obj;
        return axion.synapse == synapse;
    }
}
