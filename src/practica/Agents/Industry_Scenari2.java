/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica.Agents;

import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;
import jade.proto.ContractNetInitiator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import practica.Ontology.CleanWater;
import practica.Ontology.Have;
import practica.Ontology.MassWater;
import practica.Ontology.RiverOntology;
import practica.Ontology.TakeWater;

/**
 *
 * @author edacal
 */
public class Industry_Scenari2 extends Agent{
    
    public Industry_Scenari2() {
        ontology = RiverOntology.getInstance(); 
        codec = new SLCodec();
        ran = new Random();
    }
    @Override
    protected void setup() {
        getContentManager().registerLanguage(codec);
        getContentManager().registerOntology(ontology);
        MAX_STORED = ran.nextFloat() * 250;
        stored = 0.0f;
        DBO_affect = ran.nextFloat();
        TN_affect = ran.nextFloat();
        TS_affect = ran.nextFloat();
        SS_affect = ran.nextFloat();
        DBO_affect = ran.nextFloat();
        section = ran.nextInt(10);
        addBehaviour(new DirtyWater());
    }
    private class DirtyWater extends CyclicBehaviour {
        @Override
        public void action() {
            ACLMessage notifyDirty = new ACLMessage(ACLMessage.QUERY_IF); 
            notifyDirty.setProtocol(FIPANames.InteractionProtocol.FIPA_QUERY); 
            notifyDirty.setLanguage(codec.getName());
            notifyDirty.setOntology(ontology.getName());
            notifyDirty.addReceiver(new AID("River", AID.ISLOCALNAME)); 
            TakeWater tw = new TakeWater();
            tw.setSection(section);
            tw.setVolume(10f);
            try {
                getContentManager().fillContent(notifyDirty, new Action(new AID("River", AID.ISLOCALNAME), tw));
            } catch (Codec.CodecException | OntologyException ex) {
                Logger.getLogger(Industry_Scenari2.class.getName()).log(Level.SEVERE, null, ex);
            }
            myAgent.addBehaviour(new AchieveREInitiator(myAgent, notifyDirty)
            {
                @Override
               protected void handleInform(ACLMessage inform) { 
                   ContentElement ce = null; 
                   try {
                       ce = getContentManager().extractContent(inform);
                       if(ce instanceof Have) { 
                           Have h = (Have) ce;
                           extractWater = new MassWater();
                           extractWater.setVolume(h.getVolume());
                           extractWater.setDBO(h.getDQO()+ DQO_affect);
                           extractWater.setDBO(h.getDBO() + DBO_affect);
                           extractWater.setTN(h.getTN() + TN_affect);
                           extractWater.setSS(h.getSS() + SS_affect);
                           extractWater.setTS(h.getTS() + TS_affect);
                           if(extractWater.getVolume() + stored > MAX_STORED) {
                                float sprov = stored;
                                stored = extractWater.getVolume();
                                extractWater.setVolume(sprov);
                                ACLMessage clearMessage = new ACLMessage(ACLMessage.PROPOSE); 
                                clearMessage.setProtocol(FIPANames.InteractionProtocol.FIPA_PROPOSE); 
                                clearMessage.setLanguage(codec.getName());
                                clearMessage.setOntology(ontology.getName());
                                clearMessage.addReceiver(new AID("Plant", AID.ISLOCALNAME));
                                CleanWater cw = new CleanWater();
                                cw.setWater(extractWater);
                                getContentManager().fillContent(clearMessage, new Action(new AID("Plant", AID.ISLOCALNAME), cw));
                                myAgent.addBehaviour(new ContractNetInitiator(myAgent, clearMessage) 
                                {
                                    @Override
                                    protected void handleRefuse(ACLMessage refuse) {
                                        System.out.println(refuse);

                                    }
                                    
                                    @Override
                                    protected void handleInform(ACLMessage inform) {
                                        System.out.println(inform);
                                    }
                           
                                });
                                
                           }
                           else 
                           {
                                stored += extractWater.getVolume();
                           }
                           
                           
                           
                              
                       }
                   } catch (Codec.CodecException | OntologyException ex) {
                       Logger.getLogger(Industry_Scenari2.class.getName()).log(Level.SEVERE, null, ex);
                   }
               }
               
               @Override
               protected void handleRefuse(ACLMessage refuse) 
               {
               }
                    
                    
            });
        }
    } 
    private int section;
    private float MAX_STORED;
    private float stored;
    private final Ontology ontology;
    private final Codec codec;
    private final Random ran;
    private MassWater extractWater;
    private float DBO_affect;
    private float DQO_affect;
    private float TN_affect;
    private float TS_affect;
    private float SS_affect;
}
