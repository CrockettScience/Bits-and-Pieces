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
package lib.saveableStructures.demo;

import java.util.Arrays;
import lib.saveableStructures.SaveableData;

/**
 *
 * @author Jonathan Crockett
 */
public class SaveableString implements SaveableData {
    
    public static int TOTAL_BYTES = 8;
    private String string;

    public SaveableString(String str) {
        string = str;
    }
    
    public byte[] saveState() {
        return Arrays.copyOf(string.getBytes(), TOTAL_BYTES);
    }

    public void loadState(byte[] bytes) {
        bytes = Arrays.copyOf(bytes, TOTAL_BYTES);
        string = new String(bytes);
    }

    public int byteSize() {
        return TOTAL_BYTES;
    }

    public String getString() {
        return string;
    }

    public void setString(String str) {
        string = str;
    }
    
    public String toString() {
        return string;
    }
    
}