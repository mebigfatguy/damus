/*
 * damus - a forecasting tool
 * Copyright (C) 2010 Dave Brosius
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
import java.io.IOException;
import java.text.MessageFormat;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import com.mebigfatguy.damus.bundle.DamusBundle;
import com.mebigfatguy.damus.io.DamusIO;
import com.mebigfatguy.damus.main.Context;

public class SaveAsAction extends AbstractAction {

    private static final long serialVersionUID = -1168013965884073458L;

    public SaveAsAction() {
        super(DamusBundle.getString(DamusBundle.SAVE_AS));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            DamusIO.saveFileAs();
        } catch (IOException ioe) {
            String msg = MessageFormat.format(DamusBundle.getString(DamusBundle.DAMUS_SAVE_FAILURE), "?");
            Context context = Context.instance();
            JOptionPane.showMessageDialog(context.getFrame(), msg);
        }
    }
}
