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

public class Chunk8Bit extends ByteChunk{
    
    public Chunk8Bit(byte b0){
        b = new byte[1];
        
        b[0] = b0;
    }
    
}
