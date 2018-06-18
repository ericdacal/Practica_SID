/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica.Agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;
import java.util.ArrayList;
import java.util.Random;
import practica.Ontology.MassWater;

/**
 *
 * @author edacal
 */
public class River extends Agent{
    public River() {
        sections = new ArrayList<>();
        ran = new Random();
    } 
    
    @Override
    protected void setup() {
        MessageTemplate mt = AchieveREResponder.createMessageTemplate(FIPANames.InteractionProtocol.FIPA_REQUEST); 
        addBehaviour(new WaterExtract(this,mt));
        addBehaviour(new WaterRun());
    }
    private void rain() {
        if(sections.isEmpty()) {
            for(int i = 0; i < 10; ++i) {
                MassWater mw = new MassWater();
                mw.volumen = ran.nextFloat()*20;
                mw.DBO = ran.nextFloat();
                mw.SS = ran.nextFloat();
                mw.TN = ran.nextFloat();
                mw.TS = ran.nextFloat();
                sections.add(mw);
            }
        }
        else {
            MassWater mw = new MassWater();
            mw.volumen = ran.nextFloat()*10;
            mw.DBO = ran.nextFloat();
            mw.SS = ran.nextFloat();
            mw.TN = ran.nextFloat();
            mw.TS = ran.nextFloat();
            sections.set(0,mw);
        }
    }
    private class WaterRun extends CyclicBehaviour {
        @Override
        public void action() {
            rain();
            for(int i = sections.size() - 1; i > 0; --i) {
                sections.set(i, sections.get(i - 1));
            }
        }   
    }
    private class WaterExtract extends AchieveREResponder {
        public WaterExtract(Agent a,MessageTemplate mt) {
            super(a,mt);
        }
        
        @Override
        protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage resp) { 
            String [] demand = request.getContent().split(" ");
            ACLMessage informDone = request.createReply(); 
            int section = Integer.parseInt(demand[0]);
            if(section < sections.size()) {
                informDone.setPerformative(ACLMessage.INFORM); 
                informDone.setContent(String.valueOf(sections.get(section).volumen) + " " + String.valueOf(sections.get(section).DBO) + " " + String.valueOf(sections.get(section).SS) + " " + String.valueOf(sections.get(section).TS)); 
                sections.get(section).volumen = 0;
                return informDone; 
            }
            else {
                informDone.setPerformative(ACLMessage.CANCEL); 
                informDone.setContent("Not enough water"); 
                return informDone; 
            
            }
            	
            
        }    
    }   
    private final Random ran;
    private static ArrayList<MassWater> sections;
}
    
    

