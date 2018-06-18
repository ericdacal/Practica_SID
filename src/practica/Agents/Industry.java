/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica.Agents;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;
import java.util.Random;

/**
 *
 * @author edacal
 */
public class Industry extends Agent{
    
    public Industry() {
        ran = new Random();
    }
    @Override
    protected void setup() {
        section = ran.nextInt(10);
        ACLMessage request = new ACLMessage(ACLMessage.REQUEST); 
        request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST); 
        request.addReceiver(new AID("River", AID.ISLOCALNAME)); 
        request.setContent(String.valueOf(section) + " 10");
        addBehaviour(new DirtyWater(this,request));
    }
    private class DirtyWater extends AchieveREInitiator {
        public DirtyWater(Agent a, ACLMessage request) {
            super(a,request);
        }
        @Override
        protected void handleInform(ACLMessage inform) { 		
            System.out.println(inform.getContent()); 
        } 
    } 
    private int section;
    private final Random ran;
}
