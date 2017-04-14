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

public class RandString implements RandValue<String>{
    private RandChar rChar = new RandChar();
    private int length;
    
    public RandString(int len){
        length = len;
    }
    
    public String nextValue(Random Rand){
        String str = "";
        for(int i = 0; i < getLength(); i++){
            str += rChar.nextValue(Rand);
        }
        return str;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
