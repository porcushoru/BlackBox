package com.BlackBox.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.List;

public class FileDropExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("File Drop Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create a text area to receive dropped files
        JTextArea textArea = new JTextArea();
        textArea.setTransferHandler(new FileTransferHandler()); // Assign custom TransferHandler
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);

        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Custom TransferHandler subclass
    static class FileTransferHandler extends TransferHandler {
        @Override
        public boolean canImport(TransferSupport support) {
            // Allow dropping if the data flavor is supported
            return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
        }

        @Override
        public boolean importData(TransferSupport support) {
            if (!canImport(support)) {
                return false;
            }

            // Get the list of dropped files
            Transferable transferable = support.getTransferable();
            try {
                List<File> files = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);

                // Process the dropped files
                for (File file : files) {
                    // Do something with each dropped file (e.g., display its path in the text area)
                    JTextArea textArea = (JTextArea) support.getComponent();
                    textArea.append(file.getAbsolutePath() + "\n");
                }

                return true; // Import successful
            } catch (Exception e) {
                e.printStackTrace();
                return false; // Import failed
            }
        }
    }
}
