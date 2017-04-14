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

import lib.menuSystem.Menu;

/**
 *
 * @author Jonathan Crockett
 */
public class CSPortfolio {
    public static void main(String[] args) {
        Menu menu = new PortfolioMenu("Please remember to check https://github.com/CrockettScience/CS-Portfolio for updates.");
        menu.start();
    }
    
}
