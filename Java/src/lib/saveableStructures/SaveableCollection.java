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

public abstract class SaveableCollection<T extends SaveableElement<U>, U extends ByteChunk> implements Iterable<T>{
    protected U ByteChunkBuffer;
    
    public abstract void add(T e);
}
