/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica.Ontology;

import jade.content.Predicate;


/**
 *
 * @author edacal
 */
public class Have implements Predicate{
    private float volume;
    private MassWater mass;
    
    public float getVolume() { 
        return volume; 
    } 
    public void setVolume(float volume) { 
        this.volume = volume; 
    } 
    public MassWater getMassWater() { 
        return mass; 
    } 
    public void setMassWater(MassWater mass) { 
        this.mass = mass; 
    } 
    
    
}
