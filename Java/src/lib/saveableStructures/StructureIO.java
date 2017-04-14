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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class StructureIO{
    
    public class StructureFileReader {

        private File file;
        private FileInputStream input;
        private FileSize size;

        private HeaderEntry[] entries;

        public StructureFileReader(String fileName, FileSize fileSize) throws FileNotFoundException, IOException {
            file = new File(fileName);
            input = new FileInputStream(file);
            size = fileSize;
            entries = loadHeader();
        }
        
        public <T extends SaveableElement<U>, U extends ByteChunk> void readStructure(String id, SaveableCollection<T,U> coll, CreateElement<T> creator) throws IOException{
            if(coll.ByteChunkBuffer == null)
                System.err.println("Could not load; could not acquire byte buffer from structure");
            
            HeaderEntry entry = findEntry(id);
            
            if(entry == null){
                System.err.println("Could not load; structure not found");
                return;
            }
            
            if(input.skip((long) entry.startIndex) != (long) entry.startIndex){
                System.err.println("Could not load; unexpected number of bytes skipped");
                return;
            }
            
            int bytesToRead = entry.endIndex - entry.startIndex;
            
            for(int i = 0; i < bytesToRead; i++){
                input.read(coll.ByteChunkBuffer.getBytes());
                T e = creator.createElement();
                e.loadState(coll.ByteChunkBuffer);
                coll.add(e);
            }
            
            resetReader();
            
        }

        private HeaderEntry[] loadHeader() throws IOException {
            byte[] entryBuffer = new byte[(size.size * 2) + 8];

            int entryCount = Byte.toUnsignedInt((byte) input.read());

            if(entryCount == 0)
                throw new IOException("ERROR: invalid file header");

            HeaderEntry[] entries = new HeaderEntry[entryCount];

            for(int i = 0; i < entryCount; i++){
                input.read(entryBuffer);
                entries[i] = new HeaderEntry(entryBuffer);
            }

            return entries;
        }
        
        private HeaderEntry findEntry(String id){
            for(HeaderEntry e: entries){
                if(e.identifier.equals(id))
                    return e;
            }
            
            return null;
        }
        
        private void resetReader() throws IOException {
            input.close();
            input = new FileInputStream(file);
            
            if(input.skip((long) 2 * size.size * entries.length + 1) != (long) 2 * size.size * entries.length + 1){
                System.err.println("Could not reset reader; unexpected number of bytes skipped");
                return;
            }
        }
    }
    
    public class StructureFileWriter {
        
    }
    
    private class HeaderEntry {
        private int startIndex;
        private int endIndex;
        private byte[] rawBytes;
        private String identifier;
        
        public HeaderEntry (byte[] entryBytes) {
            
            int indexCount = (entryBytes.length - 8) / 2;
            
            startIndex = 0;
            endIndex = 0;
            
            int i = 0;
            for( ; i < indexCount; i++) {
                startIndex += Byte.toUnsignedInt(entryBytes[i]);
            }
            
            indexCount *= 2;
            
            for( ; i < indexCount; i++) {
                endIndex += Byte.toUnsignedInt(entryBytes[i]);
            }
            
            byte[] id = {entryBytes[i++],
                         entryBytes[i++],
                         entryBytes[i++],
                         entryBytes[i++],
                         entryBytes[i++],
                         entryBytes[i++],
                         entryBytes[i++],
                         entryBytes[i]};
            
            identifier = new String(id);
            
            rawBytes = entryBytes;
        }
        
        public byte[] recordEntry() {
            return rawBytes;
        }
    }
    
    public static void saveStructure(FileOutputStream fOut, SaveableCollection<?,?> coll) throws IOException{
        if(coll.ByteChunkBuffer == null){
            throw(new IOException("ERROR: Cannot acquire temporary chunk"));
        }
        
        for(SaveableElement e: coll){
            fOut.write(e.saveState().getBytes());
        }
    }
    
    public static <T extends SaveableElement<U>, U extends ByteChunk> void loadStructure(FileInputStream fIn, SaveableCollection<T,U> coll, CreateElement<T> func) throws IOException{
        if(coll.ByteChunkBuffer == null){
            throw(new IOException("ERROR: Cannot acquire temporary chunk"));
        }        
        
        while(fIn.read(coll.ByteChunkBuffer.getBytes()) >= 0){
            T e = func.createElement();
            e.loadState(coll.ByteChunkBuffer);
            coll.add(e);
        }
    }
    
    public static enum FileSize{
        Short(8), Medium(16), Long(32), Maximum(64);
        
        FileSize(int s){
            size = s;
        }
        
        public final int size;
    }
    
    public static interface CreateElement<T extends SaveableElement>{
        public T createElement();
    }
    
}
