package gui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SimpleTextFieldChangeListener implements DocumentListener {

    private Runnable action;

    public SimpleTextFieldChangeListener(Runnable action) {
        this.action = action;
    }

    public static void addTo(JTextField textField, Runnable action) {
        textField.getDocument()
                .addDocumentListener(new SimpleTextFieldChangeListener(action));
    }

    @Override
    public void changedUpdate(DocumentEvent arg0) {
        this.action.run();
    }

    @Override
    public void insertUpdate(DocumentEvent arg0) {
        this.action.run();
    }

    @Override
    public void removeUpdate(DocumentEvent arg0) {
        this.action.run();
    }
}
