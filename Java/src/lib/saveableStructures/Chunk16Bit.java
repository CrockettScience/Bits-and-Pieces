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

import java.io.IOException;

public class Chunk16Bit extends ByteChunk{
    
    public Chunk16Bit(byte b0, byte b1){
        b = new byte[2];
        
        b[0] = b0;
        b[1] = b1;
    }
}
