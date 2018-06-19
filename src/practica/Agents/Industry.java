/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica.Agents;

import jade.content.abs.AbsConcept;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import practica.Ontology.Have;
import practica.Ontology.MassWater;
import practica.Ontology.RiverOntology;

/**
 *
 * @author edacal
 */
public class Industry extends Agent{
    
    public Industry() {
        ontology = RiverOntology.getInstance(); 
        codec = new SLCodec();
        ran = new Random();
    }
    @Override
    protected void setup() {
        getContentManager().registerLanguage(codec);
        getContentManager().registerOntology(ontology);
        section = ran.nextInt(10);
        addBehaviour(new DirtyWater());
    }
    private class DirtyWater extends CyclicBehaviour {
        @Override
        public void action() {
            ACLMessage request = new ACLMessage(ACLMessage.REQUEST); 
            request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST); 
            request.setLanguage(codec.getName());
            request.setOntology(ontology.getName());
            request.addReceiver(new AID("River", AID.ISLOCALNAME)); 
            MassWater mw = new MassWater();
            Have h = new Have();
            h.setMassWater(mw);
            h.setVolume(10);
            try { 
                getContentManager().fillContent(request, h);
            } catch (Codec.CodecException | OntologyException ex) {
                Logger.getLogger(Industry.class.getName()).log(Level.SEVERE, null, ex);
            }
            myAgent.addBehaviour(new AchieveREInitiator(myAgent, request) { 
               @Override
               protected void handleInform(ACLMessage inform) { 
                       
                       System.out.println(inform); 
         
               } 
            });
        }
    } 
    private int section;
    private final Ontology ontology;
    private final Codec codec;
    private final Random ran;
}
