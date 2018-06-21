/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica.Agents;

import practica.Graphics.MainGui;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;

/**
 *
 * @author edacal
 */
public class Main extends GuiAgent{
    transient protected MainGui myGui; // Reference to the gui
    private int command = -1;
    
    @Override
     protected void setup() {
        myGui = new MainGui(this);
        myGui.setLocationRelativeTo(null);
        myGui.setVisible(true);
    }
    @Override
    protected void takeDown() {
       System.out.println(getLocalName() + " is now shutting down.");
       if (myGui!=null) {
          myGui.setVisible(false);
          myGui.dispose();
       }
    }
    
    @Override
    protected void onGuiEvent(GuiEvent ev) 
    {
      command = ev.getType();
      System.out.println(command);
      if (command == 0) {
         alertGui("Bye!");
         doDelete();
         System.exit(0);
      }
      /*if (command == NEW_ACCOUNT) {
         createAccount();
      }
      else if (command == DEPOSIT || command == WITHDRAWAL) {
         command = ev.getType();
         Account acc = (Account)ev.getParameter(0);
         float amount = ((Float)ev.getParameter(1)).floatValue();
         requestOperation(acc, amount);
      }
      else if (command == BALANCE || command == OPERATIONS) {
         Account acc = (Account)ev.getParameter(0);
         queryInformation(acc);
      }*/
    }
    void alertGui(Object response) {
      //myGui.alertResponse(response);
    }
    
    
    
}
