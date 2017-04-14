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
package lib.fillListRandom;

import java.util.Random;

public class RandDouble implements RandValue<Double> {

    
    public Double nextValue(Random rand) {
        return rand.nextDouble();
    }
    
}
