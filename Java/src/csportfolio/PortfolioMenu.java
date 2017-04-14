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
package csportfolio;
import lib.fillListRandom.FillArrayRandomDemo;
import lib.menuSystem.Menu;
import lib.saveableStructures.test.SaveableTest;

/**
 *
 * @author Jonathan Crockett
 */
public class PortfolioMenu extends Menu{
    
    public PortfolioMenu(String intro) {
        super(intro);
    }

    public Node assembleNodes() {
        Node main = new Node("Welcome to Jonathan Crockett's Portfolio. Please type in one of the numbers below and press enter to read about the corresponding package from the list:");
        
        Action<PortfolioMenu> fillRand = (menu) -> {
            FillArrayRandomDemo.main(new String[0]);
            menu.setCurrentNode(main);
        };
        
        Action<PortfolioMenu> menuSys = (menu) -> {
            System.out.println("\nThe menuSystem package is the framework responsible for this console menu\n"
                             + "The framework operates using a couple moving parts: nodes, entries, and actions.\n"
                             + "Nodes are simple objects that contain a number of entries. It's constructor takes\n"
                             + "an instance of String representing the prompt that comes right before the list of\n"
                             + "options in the output. Entries are objects that represent the user's possible choices.\n"
                             + "It's constructor requires instance of String to represent a short of what\n"
                             + "the entry does once selected and a reference to an instance of an object that implements the\n"
                             + "Action interface (This will be the instance whose execute() method is called when the user\n"
                             + "wants to select this entry). And actions are objects that implement\n"
                             + "the Action Interface and act like a functor. They must override one method called\n"
                             + "execute(), which is meant to be called when the user correctly types a string that\n"
                             + "matches an entry. ");
            menu.setCurrentNode(main);
        };
        
        Action<PortfolioMenu> saveable = (menu) -> {
            SaveableTest.main(new String[0]);
            menu.setCurrentNode(main);
        };
        
        Entry a = new Entry("Package fillListRandom", fillRand);
        Entry b = new Entry("Package menuSystem", menuSys);
        Entry c = new Entry("Package saveableStructures", saveable);
        Entry d = new Entry("Exit", new ActionQuit<>());
        
        main.add(a);
        main.add(b);
        main.add(c);
        main.add(d);
        
        return main;
    }
    
}