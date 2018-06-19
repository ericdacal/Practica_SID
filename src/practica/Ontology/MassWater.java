/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica.Ontology;

import jade.content.Concept;


/**
 *
 * @author edacal
 */
public class MassWater implements Concept{
    private float volumen;
    private float SS;
    private float DBO;
    private float TN; 
    private float TS;
    
    public float getVolumen() {
        return volumen;
    }
    
    public void setVolumen(float volumen) {
        this.volumen = volumen;
    }
    public float getSS() {
        return SS;
    }
    public void setSS(float SS) {
        this.SS = SS;
    }
    public float getDBO() {
        return DBO;
    }
    public void setDBO(float DBO) {
        this.DBO = DBO;
    }
    public float getTN() {
        return TN;
    }
    public void setTN(float TN) {
        this.TN = TN;
    }
    public float getTS() {
        return TS;
    }
    public void setTS(float TS) {
        this.TS = TS;
    }
}
