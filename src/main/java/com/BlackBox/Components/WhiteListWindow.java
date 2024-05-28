package com.BlackBox.Components;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WhiteListWindow extends JPanel {
    public JList<File> fileList;

    public WhiteListWindow() {
        setLayout(new BorderLayout());
        setSize(300, 200);

        // Create list model
        DefaultListModel<File> listModel = new DefaultListModel<>();
        fileList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(fileList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void addFileLabel(JLabel fileLabel, File file) {
        // Check if the file already exists in the list
        if (isFileAlreadyAdded(file)) {
            // Display a dialog box indicating that the file already exists
            JOptionPane.showMessageDialog(this, "File '" + file.getAbsolutePath() + "' already exists in the list!", "Duplicate File", JOptionPane.WARNING_MESSAGE);
            return; // Exit the method without adding the file
        }

        // Get the existing list model or create a new one if it doesn't exist
        DefaultListModel<File> listModel = (DefaultListModel<File>) fileList.getModel();
        if (listModel == null) {
            listModel = new DefaultListModel<>();
            fileList.setModel(listModel);
        }

        // Add the file to the list model
        listModel.addElement(file);
    }

    private boolean isFileAlreadyAdded(File file) {
        DefaultListModel<File> listModel = (DefaultListModel<File>) fileList.getModel();
        if (listModel != null) {
            for (int i = 0; i < listModel.getSize(); i++) {
                if (file.equals(listModel.getElementAt(i))) {
                    return true; // File already exists in the list
                }
            }
        }
        return false; // File is not in the list
    }

    public void setFiles(File[] files) {
        DefaultListModel<File> listModel = (DefaultListModel<File>) fileList.getModel();
        listModel.clear(); // Clear existing files
        for (File file : files) {
            listModel.addElement(file);
        }
    }

    public List<File> getSelectedFiles() {
        List<File> selectedFiles = new ArrayList<>();
        int[] selectedIndices = fileList.getSelectedIndices();
        DefaultListModel<File> listModel = (DefaultListModel<File>) fileList.getModel();
        for (int index : selectedIndices) {
            selectedFiles.add(listModel.getElementAt(index));
        }
        return selectedFiles;
    }
}
