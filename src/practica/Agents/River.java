/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica.Agents;

import jade.content.Concept;
import jade.content.ContentElement;
import jade.content.abs.AbsPredicate;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.lang.sl.SLVocabulary;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import practica.Ontology.Have;
import practica.Ontology.MassWater;
import practica.Ontology.RiverOntology;
import practica.Ontology.TakeWater;
import practica.Ontology.ThrowWater;

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
        MessageTemplate mt = AchieveREResponder.createMessageTemplate(FIPANames.InteractionProtocol.FIPA_QUERY); 
        addBehaviour(new AchieveREResponder(this, mt) { 
            @Override
            protected ACLMessage prepareResponse(ACLMessage request) {
                ACLMessage informDone = request.createReply(); 
                ContentElement ce = null; 
                if (request.getPerformative() == ACLMessage.QUERY_IF) {
                    try { 
                        ContentElement content = getContentManager().extractContent(request);
                        Concept action = ((Action)content).getAction();
                        if(action instanceof TakeWater) {
                            TakeWater cw = (TakeWater)((Action)content).getAction();
                            int section = cw.getSection();
                            float demandVolume = cw.getVolume();
                            float actualVolume = sections.get(section).getVolume(); 
                            if(actualVolume >= demandVolume) 
                            {
                                sections.get(section).setVolume(actualVolume - demandVolume);
                                Have h = new Have();
                                h.setDBO(sections.get(section).getDBO());
                                h.setSS(sections.get(section).getSS());
                                h.setTN(sections.get(section).getTN());
                                h.setTS(sections.get(section).getTS());
                                h.setVolume(demandVolume);
                                informDone.setPerformative(ACLMessage.INFORM); 
                                getContentManager().fillContent(informDone, h);    
                            }
                            else {
                                AbsPredicate fail = new AbsPredicate(SLVocabulary.NOT);
                                Have h = new Have();
                                h.setVolume(demandVolume);
                                fail.set(SLVocabulary.NOT_WHAT, ontology.fromObject(h));
                                getContentManager().fillContent(informDone, fail);
                                informDone.setPerformative(ACLMessage.REFUSE);
                            }

                        }   
                    }
                    catch (Codec.CodecException | OntologyException ex) 
                    {
                        Logger.getLogger(River.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                return informDone;
            }
        });
       
        MessageTemplate mtreq = AchieveREResponder.createMessageTemplate(FIPANames.InteractionProtocol.FIPA_REQUEST); 
        addBehaviour(new AchieveREResponder(this, mtreq) { 
            @Override
            protected ACLMessage prepareResponse(ACLMessage request) {
                ContentElement content;
                try {
                    content = getContentManager().extractContent(request);
                    Concept action = ((Action)content).getAction();
                    if(action instanceof ThrowWater) {
                        ThrowWater cw = (ThrowWater)((Action)content).getAction();
                        cw.getMassWater();
                        int section = cw.getSection();
                        MassWater mw = cw.getMassWater();
                        sections.set(section, mw);
                        
                    }
                } catch (Codec.CodecException | OntologyException ex) {
                    Logger.getLogger(River.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
            }
           
            @Override
            protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) {
                return null;
            }
        });
        addBehaviour(new WaterRun());
    }
    private void rain() {
        if(sections.isEmpty()) {
            for(int i = 0; i < 10; ++i) {
                MassWater mw = new MassWater();
                mw.setVolume(ran.nextFloat()*20);
                mw.setDBO(0.0f);
                mw.setSS(0.0f);
                mw.setTN(0.0f);
                mw.setTS(0.0f);
                sections.add(mw);
            }
        }
        else {
            MassWater mw = new MassWater();
            mw.setVolume(ran.nextFloat()*20);
            mw.setDBO(0.0f);
            mw.setSS(0.0f);
            mw.setTN(0.0f);
            mw.setTS(0.0f);
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
    
    

