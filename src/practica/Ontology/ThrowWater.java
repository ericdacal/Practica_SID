/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica.Ontology;

import jade.content.AgentAction;

/**
 *
 * @author eric.dacal
 */
public class ThrowWater implements AgentAction {
    private MassWater mw;
    private int section;
    public MassWater getMassWater() {
        return mw;
    }
    public void setMassWater(MassWater mw) {
        this.mw = mw;
    }
    public int getSection()
    {
        return section;
    }
    public void setSection(int section)
    {
        this.section = section;
    }
    
}
