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
package com.mebigfatguy.damus.gui;

import java.awt.Container;
import java.util.EnumMap;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.mebigfatguy.damus.bundle.DamusBundle;
import com.mebigfatguy.damus.gui.actions.CloseAction;
import com.mebigfatguy.damus.gui.actions.ModelAction;
import com.mebigfatguy.damus.gui.actions.NewAction;
import com.mebigfatguy.damus.gui.actions.OpenAction;
import com.mebigfatguy.damus.gui.actions.PredictAction;
import com.mebigfatguy.damus.gui.actions.QuitAction;
import com.mebigfatguy.damus.gui.actions.ReviewAction;
import com.mebigfatguy.damus.gui.actions.SaveAction;
import com.mebigfatguy.damus.gui.actions.SaveAsAction;
import com.mebigfatguy.damus.gui.actions.TrainNewAction;

public class DamusFrame extends JFrame implements PanelViewer {

    private static final long serialVersionUID = 69030272254787759L;

    private JTabbedPane tabPane;
    private JMenuItem newItem;
    private JMenuItem openItem;
    private JMenuItem closeItem;
    private JMenuItem saveItem;
    private JMenuItem saveAsItem;

    private JMenu dataMenu;
    private JCheckBoxMenuItem modelItem;
    private JCheckBoxMenuItem reviewItem;
    private JCheckBoxMenuItem trainItem;
    private JCheckBoxMenuItem predictItem;

    private final EnumMap<PanelType, JPanel> panels = new EnumMap<PanelType, JPanel>(PanelType.class);

    public DamusFrame() {
        initComponents();
        initMenus();
        setTitle(DamusBundle.getString(DamusBundle.TITLE));
        setSize(800, 600);
        //TODO: For now just close
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private final void initComponents() {
        Container cp = getContentPane();
        tabPane = new JTabbedPane();
        cp.add(tabPane);
    }

    private final void initMenus() {
        JMenuBar mb = new JMenuBar();

        JMenu fileMenu = new JMenu(DamusBundle.getString(DamusBundle.FILE));
        newItem = new JMenuItem(new NewAction(this));
        fileMenu.add(newItem);
        openItem = new JMenuItem(new OpenAction(this));
        fileMenu.add(openItem);
        closeItem = new JMenuItem(new CloseAction(this));
        fileMenu.add(closeItem);
        fileMenu.addSeparator();
        saveItem = new JMenuItem(new SaveAction());
        fileMenu.add(saveItem);
        saveAsItem = new JMenuItem(new SaveAsAction());
        fileMenu.add(saveAsItem);
        fileMenu.addSeparator();
        JMenuItem quitItem = new JMenuItem(new QuitAction());
        fileMenu.add(quitItem);
        mb.add(fileMenu);

        dataMenu = new JMenu(DamusBundle.getString(DamusBundle.DATA));
        modelItem = new JCheckBoxMenuItem(new ModelAction(this));
        dataMenu.add(modelItem);
        reviewItem = new JCheckBoxMenuItem(new ReviewAction(this));
        dataMenu.add(reviewItem);
        trainItem = new JCheckBoxMenuItem(new TrainNewAction(this));
        dataMenu.add(trainItem);
        predictItem = new JCheckBoxMenuItem(new PredictAction(this));
        dataMenu.add(predictItem);
        dataMenu.setEnabled(false);
        mb.add(dataMenu);

        markModelClosed();

        setJMenuBar(mb);
    }

    @Override
    public void openPanel(PanelType type, JPanel panel, boolean shared) {
        if (!shared) {
            tabPane.removeAll();
        }

        tabPane.add(type.toString(), panel);
        panels.put(type, panel);

        if (type == PanelType.NEWMODEL) {
            markModelOpen();
        }

        selectPanelMenus();

        tabPane.setSelectedComponent(panel);

    }

    @Override
    public void closePanel(PanelType type) {
        JPanel panel = panels.get(type);
        if (panel != null) {
            tabPane.remove(panel);
            panels.remove(type);
        }

        selectPanelMenus();
    }

    @Override
    public void closeAllPanels() {
        for (PanelType type : panels.keySet()) {
            closePanel(type);
        }

        selectPanelMenus();
        markModelClosed();
    }

    private void selectPanelMenus() {
        newItem.setSelected(panels.containsKey(PanelType.NEWMODEL));
        reviewItem.setSelected(panels.containsKey(PanelType.REVIEW));
        trainItem.setSelected(panels.containsKey(PanelType.TRAINMODEL));
        predictItem.setSelected(panels.containsKey(PanelType.PREDICTMODEL));
    }

    public void markModelOpen() {
        newItem.setEnabled(false);
        openItem.setEnabled(false);
        closeItem.setEnabled(true);
        saveItem.setEnabled(true);
        saveAsItem.setEnabled(true);
        dataMenu.setEnabled(true);
        modelItem.setSelected(true);
    }

    public void markModelClosed() {
        newItem.setEnabled(true);
        openItem.setEnabled(true);
        closeItem.setEnabled(false);
        saveItem.setEnabled(false);
        saveAsItem.setEnabled(false);
        dataMenu.setEnabled(false);
        modelItem.setSelected(false);
    }
}
