/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica.Agents;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;
import java.util.ArrayList;
import java.util.Random;
import practica.Ontology.MassWater;
import practica.Ontology.RiverOntology;

/**
 *
 * @author edacal
 */
public class River extends Agent{
    public River() {
        ontology = RiverOntology.getInstance(); 
        codec = new SLCodec();
        sections = new ArrayList<>();
        ran = new Random();
    } 
    
    @Override
    protected void setup() {
        getContentManager().registerLanguage(codec);
        getContentManager().registerOntology(ontology);
        MessageTemplate mt = AchieveREResponder.createMessageTemplate(FIPANames.InteractionProtocol.FIPA_REQUEST); 
        addBehaviour(new AchieveREResponder(this, mt) { 
            @Override
            protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage resp) { 
                
                ACLMessage informDone = request.createReply(); 	
                informDone.setPerformative(ACLMessage.INFORM); 
                informDone.setContent("inform done"); 
                return informDone; 
            } 
        });   
        addBehaviour(new WaterRun());
    }
    private void rain() {
        if(sections.isEmpty()) {
            for(int i = 0; i < 10; ++i) {
                MassWater mw = new MassWater();
                mw.setVolumen(ran.nextFloat()*20);
                mw.setDBO(ran.nextFloat());
                mw.setSS(ran.nextFloat());
                mw.setTN(ran.nextFloat());
                mw.setTS(ran.nextFloat());
                sections.add(mw);
            }
        }
        else {
            MassWater mw = new MassWater();
            mw.setVolumen(ran.nextFloat()*20);
            mw.setDBO(ran.nextFloat());
            mw.setSS(ran.nextFloat());
            mw.setTN(ran.nextFloat());
            mw.setTS(ran.nextFloat());
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
    private final Random ran;
    private final Ontology ontology;
    private final Codec codec;
    private static ArrayList<MassWater> sections;
}
    
    

