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

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import com.mebigfatguy.damus.bundle.DamusBundle;
import com.mebigfatguy.damus.gui.actions.FinishReviewAction;
import com.mebigfatguy.damus.gui.actions.TrainAction;
import com.mebigfatguy.damus.gui.model.ReviewItemsTableModel;

public class ReviewPanel extends JPanel {

    private static final long serialVersionUID = -8696144138294129488L;
    private final PanelViewer panelViewer;
    private JTable reviewTable;
    private JButton deleteButton;
    private JButton trainButton;
    private JButton finishButton;

    public ReviewPanel(PanelViewer viewer) {
        panelViewer = viewer;
        initComponents();

        ReviewItemsTableModel model = (ReviewItemsTableModel)reviewTable.getModel();
        if (model.getRowCount() > 0) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    reviewTable.getSelectionModel().setSelectionInterval(0, 0);
                }
            });
        }
    }

    private void initComponents() {
        setLayout(new BorderLayout(4, 4));

        add(createReviewPanel(), BorderLayout.CENTER);
        add(createCtrlPanel(), BorderLayout.SOUTH);

        GUIUtils.sizeUniformly(deleteButton, trainButton, finishButton);
    }

    private JPanel createReviewPanel() {

        JPanel panel = new JPanel();
        ReviewItemsTableModel rtm = new ReviewItemsTableModel();
        reviewTable = new JTable(rtm);
        panel.setLayout(new BorderLayout(4, 4));
        panel.add(new JScrollPane(reviewTable), BorderLayout.CENTER);

        panel.setBorder(BorderFactory.createTitledBorder(DamusBundle.getString(DamusBundle.TRAINEDITEMS)));
        return panel;
    }

    private JPanel createCtrlPanel() {
        JPanel ctrlPanel = new JPanel();
        ctrlPanel.setLayout(new BoxLayout(ctrlPanel, BoxLayout.X_AXIS));

        deleteButton = new JButton(DamusBundle.getString(DamusBundle.DELETE));
        trainButton = new JButton(new TrainAction(panelViewer, reviewTable));
        finishButton = new JButton(new FinishReviewAction(panelViewer));

        GUIUtils.sizeUniformly(deleteButton, trainButton, finishButton);

        ctrlPanel.add(deleteButton);
        ctrlPanel.add(Box.createHorizontalGlue());
        ctrlPanel.add(trainButton);
        ctrlPanel.add(Box.createHorizontalGlue());
        ctrlPanel.add(finishButton);

        ctrlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        return ctrlPanel;
    }
}
