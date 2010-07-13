/*
 * Beanfabrics Framework Copyright (C) 2010 by Michael Karneim, beanfabrics.org
 * Use is subject to license terms. See license.txt.
 */
// TODO javadoc - remove this comment only when the class and all non-public
// methods and fields are documentedpackage
// org.beanfabrics.swing.table.celleditor;
package org.beanfabrics.swing.table.celleditor;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.TableCellEditor;

import org.beanfabrics.model.ITextPM;
import org.beanfabrics.swing.BnComboBox;

/**
 * @author Michael Karneim
 */
@SuppressWarnings("serial")
public class BnComboBoxCellEditor extends AbstractCellEditor implements TableCellEditor {
    private transient ActionListener stopAction = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            fireEditingStopped();
        }
    };
    private transient PopupMenuListener popupListener = new PopupMenuListener() {
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            // do nothing special
        }

        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            fireEditingStopped();
        }

        public void popupMenuCanceled(PopupMenuEvent e) {
            fireEditingCanceled();
        }
    };

    public BnComboBoxCellEditor() {
        super();
    }

    private BnComboBox createBnComboBox() {
        BnComboBox comboBox = new BnComboBox();
        comboBox.addPopupMenuListener(popupListener);
        comboBox.registerKeyboardAction(stopAction, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_FOCUSED);
        return comboBox;
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value instanceof ITextPM) {
            if (((ITextPM)value).getOptions() != null) {
                BnComboBox comboBox = createBnComboBox();
                comboBox.setPresentationModel((ITextPM)value);
                return comboBox;
            }
        }
        return null;
    }

    public Object getCellEditorValue() {
        // in Beanfabrics we don't need to return a value
        return null;
    }
}