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
package lib.menuSystem;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

public abstract class Menu {
    private Node currentNode;
    private Node startingNode;
    private boolean running = true;
    private Scanner in = new Scanner(System.in);
    private String introduction;
    
    public Menu(String intro){;
        introduction = intro;
    }
    
    public final void start() {
        
        currentNode = assembleNodes();
        startingNode = currentNode;
        
        System.out.println(introduction);
        
        while(running){
           System.out.println(currentNode);
           currentNode.runEntry(in.nextLine());
        }
        
    }
    
    public final void setCurrentNode(Node node) {
        currentNode = node;
    }
    
    public final void goToStartingNode() {
        currentNode = startingNode;
    }
    
    public final void quit() {
        running = false;
    }
    
    //logic for assembly of menu nodes, returns a node to be used as a starting point
    public abstract Node assembleNodes();
    
    public static class ActionGoToNode<T extends Menu> implements Action<T> {
        
        private Node goToNode;
        
        public ActionGoToNode(Node node) {
            goToNode = node;
        }
        
        public void execute(T menuRef) {
            menuRef.setCurrentNode(goToNode);
        }
    }
    
    public static class ActionGoToNodeAfter<T extends Menu> implements Action<T> {
        
        private Action<T> action;
        private Action<T> actionGoToNode;
        
        public ActionGoToNodeAfter(Node node, Action<T> act){
            action = act;
            actionGoToNode = new ActionGoToNode(node);
        }
        
        @Override
        public void execute(T menuRef) {
            action.execute(menuRef);
            actionGoToNode.execute(menuRef);
        }
    
}
    
    public static class ActionQuit<T extends Menu> implements Action<T> {
        
        public void execute(T menuRef) {
            menuRef.quit();
        
        }
    }
    
    public interface Action<T extends Menu> {
        
        void execute(T menuRef);
    }
    
    public class Node {
        private LinkedList<Entry> entries = new LinkedList<>();
        private int infoLineLength = 0;
        private String nodeDescription;
        private Menu menuRef = Menu.this;
        
        public Node(String description) {
            nodeDescription = description;
        }
        
        public Entry[] toArray() {
            
            return (Entry[]) entries.toArray();
        }
        
        public boolean add(Entry e) {
            
            if(menuRef == e.menuRef){
                e.setEntryString(Integer.toString(entries.size()));
                entries.add(e);
            }
            else{
                System.out.println("ERROR: Entry not added, was not created by the same menu?");
                return false;
            }
            
            //update the infoLineLength of the entries if need be
            if ((e.entryString.length() + e.entryInfo.length() + 3) > infoLineLength)
                infoLineLength = e.entryString.length() + e.entryInfo.length() + 3;
            
            return true;
        }
        
        public boolean remove(Entry e) {
            
            if(entries.remove(e)) {
                ListIterator<Entry> i = entries.listIterator();
                
                while(i.hasNext()) {
                    int index = i.nextIndex();
                    Entry entry = i.next();
                    if(entry.getEntryString().equals(Integer.toString(index + 1)))
                        entry.setEntryString(Integer.toString(index));
                }
                
                return true;
            }
            
            return false;
        }
        
        private void runEntry(String entryStr) {
            
            for(Entry e: entries){
                if(e.entryString.equals(entryStr)){
                    e.run();
                    return;
                }
            }
            
            System.out.println("That entry does not exist. Please try again.");
        }
        
        public String toString() {
            String border = String.format("%" + (infoLineLength + 2) + "s", "").replace(' ', '=');
            
            String str = "\n" + nodeDescription + "\n" + border + "\n";
            
            for(Entry e : entries) {
                int trail = infoLineLength - e.entryString.length() - e.entryInfo.length();
                
                //this line prints a line of the format "entryString...EntryInfo"
                //where "..." has enough points such that lines are in
                //vertical alignment with one another for user readability
                str += ("|" + e.entryString + String.format("%" + trail + "s", "").replace(' ', '.') + e.entryInfo + "|\n");
                
            }
            
            return str + border + "\n";
        }
    }
    
    public class Entry {
        private String entryString;
        private String entryInfo;
        private Action entryAction;
        private Menu menuRef = Menu.this;
        
        public Entry(String entStr, String entInf, Action entAct){
            entryString = entStr;
            entryInfo = entInf;
            entryAction = entAct;
        }
        
        public Entry(String entInf, Action entAct){
            entryInfo = entInf;
            entryAction = entAct;
        }
        
        public void run(){
            entryAction.execute(menuRef);
        }
        
        public Menu getMenu() {
            return menuRef;
        }
        
        private void setEntryString(String entStr) {
            if(entryString == null)
                entryString = entStr;
        }
        
        private String getEntryString() {
            return entryString;
        }
    }
}