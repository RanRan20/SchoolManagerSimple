/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.school;


import GuiSchool.GuiSch;
import javax.swing.JPanel;


/**
 *
 * @author DELL
 */
public interface Escuela {
   
     public abstract void save();
    
    public abstract void delete(JPanel panelc);
    
    public abstract void update(JPanel panelc);
    
    public abstract void view(JPanel panelc);
    
    public abstract void showInfo(JPanel panelc);
    
    public abstract void getInfo();
    
    public abstract void search(JPanel panelc);
    
    
    public static void main(String[] args) {
        GuiSch FrameSch = new GuiSch();
        FrameSch.setVisible(true);
    }
   
    
    
}
