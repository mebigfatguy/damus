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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;

import com.mebigfatguy.damus.bundle.DamusBundle;
import com.mebigfatguy.damus.data.ForecastDataType;
import com.mebigfatguy.damus.data.TrainingData;
import com.mebigfatguy.damus.data.TrainingItem;
import com.mebigfatguy.damus.gui.actions.AddTrainingAndFinishAction;
import com.mebigfatguy.damus.gui.actions.AddTrainingAndNextAction;
import com.mebigfatguy.damus.gui.actions.CancelTrainingAction;
import com.mebigfatguy.damus.gui.model.TrainingTableModel;
import com.mebigfatguy.damus.main.Context;

public class TrainingPanel extends JPanel {

    private static final long serialVersionUID = -4289076135864687250L;

    private final PanelViewer panelViewer;
    private final boolean isNew;
    private JTable trainingTable;
    private JTable resultsTable;
    private JTextField descriptionField;
    private JButton cancelButton;
    private JButton addAndNextButton;
    private JButton addAndFinishButton;

    public TrainingPanel(PanelViewer viewer, boolean isNewItem) {
        panelViewer = viewer;
        isNew = isNewItem;
        initComponents();
    }

    public boolean populateTrainingItem(TrainingItem item) {
        String itemDesc = descriptionField.getText().trim();
        if (itemDesc.length() > 0) {
            item.setDescription(itemDesc);
            return true;
        }
        return false;
    }

    public void reset() {
        descriptionField.setText("");
    }

    private void initComponents() {
        setLayout(new BorderLayout(4, 4));

        add(createDescriptionPanel(), BorderLayout.NORTH);
        add(createTrainingPanel(), BorderLayout.CENTER);
        add(createCtrlPanel(), BorderLayout.SOUTH);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                descriptionField.requestFocus();
            }
        });
    }

    private JPanel createDescriptionPanel() {
        JPanel descPanel = new JPanel();

        descPanel.setLayout(new BorderLayout(4, 4));
        JLabel descLabel = new JLabel(DamusBundle.getString(DamusBundle.DESCRIPTION));
        descPanel.add(descLabel, BorderLayout.WEST);
        descriptionField = new JTextField(20);
        descLabel.setLabelFor(descriptionField);
        descPanel.add(descriptionField, BorderLayout.CENTER);
        descPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Context context = Context.instance();
        TrainingData data = context.getTrainingData();
        TrainingItem item = data.getItem(data.getCurrentIndex());
        descriptionField.setText(item.getDescription());
        return descPanel;
    }

    private JPanel createTrainingPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        {
            JPanel metricsPanel = new JPanel();
            metricsPanel.setLayout(new BorderLayout(4, 4));
            trainingTable = new JTable(new TrainingTableModel(ForecastDataType.Metric));
            trainingTable.setRowHeight(trainingTable.getRowHeight() + 14);
            TableColumn column = trainingTable.getColumnModel().getColumn(1);
            column.setCellRenderer(new TrainingValueCellRenderer(ForecastDataType.Metric));
            column.setCellEditor(new TrainingValueCellEditor(ForecastDataType.Metric));
            metricsPanel.add(new JScrollPane(trainingTable), BorderLayout.CENTER);
            metricsPanel.setBorder(BorderFactory.createTitledBorder(DamusBundle.getString(DamusBundle.METRICS)));
            panel.add(metricsPanel);
        }

        {
            JPanel resultsPanel = new JPanel();
            resultsPanel.setLayout(new BorderLayout(4, 4));
            resultsTable = new JTable(new TrainingTableModel(ForecastDataType.Result));
            resultsTable.setRowHeight(resultsTable.getRowHeight() + 14);
            TableColumn column = resultsTable.getColumnModel().getColumn(1);
            column.setCellRenderer(new TrainingValueCellRenderer(ForecastDataType.Result));
            column.setCellEditor(new TrainingValueCellEditor(ForecastDataType.Result));
            resultsPanel.add(new JScrollPane(resultsTable), BorderLayout.CENTER);
            resultsPanel.setBorder(BorderFactory.createTitledBorder(DamusBundle.getString(DamusBundle.RESULTS)));
            panel.add(resultsPanel);
        }

        return panel;
    }

    private JPanel createCtrlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        cancelButton = new JButton(new CancelTrainingAction(panelViewer));
        addAndNextButton = new JButton(new AddTrainingAndNextAction(panelViewer, this));
        addAndFinishButton = new JButton(new AddTrainingAndFinishAction(panelViewer, this));

        GUIUtils.sizeUniformly(cancelButton, addAndNextButton, addAndFinishButton);

        panel.add(cancelButton);
        panel.add(Box.createHorizontalGlue());
        panel.add(addAndNextButton);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(addAndFinishButton);

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return panel;
    }
}
