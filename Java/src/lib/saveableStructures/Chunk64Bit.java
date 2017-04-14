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
package lib.saveableStructures;

public class Chunk64Bit extends ByteChunk{
    
    public Chunk64Bit(byte b0, byte b1, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7){
        b = new byte[8];
        
        b[0] = b0;
        b[1] = b1;
        b[2] = b2;
        b[3] = b3;
        b[4] = b4;
        b[5] = b5;
        b[6] = b6;
        b[7] = b7;
    }
    
}
