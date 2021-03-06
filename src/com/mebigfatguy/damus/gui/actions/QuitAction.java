/*
 * damus - a forecasting tool
 * Copyright (C) 2010-2014 Dave Brosius
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations
 * under the License.
 */
package com.mebigfatguy.damus.gui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.mebigfatguy.damus.bundle.DamusBundle;
import com.mebigfatguy.damus.io.DamusIO;

public class QuitAction extends AbstractAction {

    private static final long serialVersionUID = -5869190042629507638L;

    public QuitAction() {
        super(DamusBundle.getString(DamusBundle.QUIT));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (DamusIO.checkSave()) {
            System.exit(0);
        }
    }
}
