/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Vector;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.MutableComboBoxModel;

/**
 *
 * @author youser
 */
public class MyComboBoxModel extends AbstractListModel implements MutableComboBoxModel {

    Vector objects = new Vector();
    Object item;

    @Override
    public void addElement(Object ob) {
        objects.addElement(ob);
    }

    @Override
    public void insertElementAt(Object ob, int index) {
        objects.add(index, ob);
    }

    @Override
    public void removeElement(Object ob) {
        objects.removeElement(ob);
    }

    @Override
    public void removeElementAt(int index) {
        objects.removeElementAt(index);
    }

    @Override
    public void setSelectedItem(Object itm) {
        item = itm;
    }

    @Override
    public Object getSelectedItem() {
        return item;
    }
    
    @Override
    public int getSize() {
        return objects.size();
    }

    @Override
    public Object getElementAt(int index) {
        return objects.elementAt(index);
    }

}
