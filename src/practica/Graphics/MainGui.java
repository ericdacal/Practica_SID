/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica.Graphics;

import jade.gui.GuiEvent;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import practica.Agents.Main;

/**
 *
 * @author edacal
 */
public class MainGui  extends JFrame implements ActionListener{
    private final JLabel numFac;
    private final JLabel numSec;
    private JTextField FactoryNum = new JTextField();
    private JTextField RiverSections = new JTextField();
    
    
    public MainGui(Main a) {
        myAgent = a;
        numFac = new JLabel();
        numFac.setText("Number of factories: ");
        numSec = new JLabel();
        numSec.setText("River Sections: ");
        FactoryNum = new JTextField();
        RiverSections = new JTextField();
        this.setMinimumSize(new Dimension(400,80));
        getContentPane().setLayout(new GridLayout(2,2));
        getContentPane().add(numFac);
        getContentPane().add(FactoryNum);
        getContentPane().add(numSec);
        getContentPane().add(RiverSections);
        FactoryNum.addActionListener(this);
        RiverSections.addActionListener(this);
    }
    

    @Override
    public void actionPerformed(ActionEvent ae) {    
        try{
            int IntnumFac = Integer.parseInt(FactoryNum.getText());
            System.out.println(IntnumFac);
            int IntnumSec = Integer.parseInt(SectionNum.getText());
            GuiEvent ge = new GuiEvent(this, 0);
            ge.addParameter(IntnumFac);
            ge.addParameter(IntnumSec);
            myAgent.postGuiEvent(ge);
        }
        catch (NumberFormatException e) {
            JOptionPane.showConfirmDialog(null, "Los valores deben ser enteros", "Error!", JOptionPane.DEFAULT_OPTION);
        }
        
        
        //GuiEvent ge = new GuiEvent(this, NEW_ACCOUNT);
        //myAgent.postGuiEvent(ge);
    }
    
    private final Main myAgent; 
    
    

}
