/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica.Agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import java.util.ArrayList;
import practica.Data.MassWater;

/**
 *
 * @author edacal
 */
public class River extends Agent{
    private static ArrayList<MassWater> sections; 
    private class Run extends CyclicBehaviour {
        @Override
        public void action() {
            for(int i = 0; i < sections.size() - 1; ++i) 
            {
                sections.set(i + 1, sections.get(i));
            }
        }   
    }
    private class Flow_into extends CyclicBehaviour {
        @Override
        public void action() {
            if(sections.get(sections.size() - 1).volumen > 0.5)sections.get(sections.size() - 1).volumen -= 0.5;
            else if(sections.get(sections.size() - 1).volumen > 0) sections.get(sections.size() - 1).volumen -= sections.get(sections.size() - 1).volumen;
            else sections.get(sections.size() - 1).volumen = 0;
        }
    }
}
