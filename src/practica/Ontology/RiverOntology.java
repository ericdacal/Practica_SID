/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica.Ontology;

import jade.content.onto.BasicOntology;
import jade.content.onto.CFReflectiveIntrospector;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.ConceptSchema;
import jade.content.schema.ObjectSchema;
import jade.content.schema.PredicateSchema;
import jade.content.schema.PrimitiveSchema;

/**
 *
 * @author edacal
 */
public class RiverOntology extends Ontology{
      // The name identifying this ontology
      public static final String ONTOLOGY_NAME = "River_Ontology"; 
        // VOCABULARY 
      public static final String MASS_WATER = "Mass_Water";
      public static final String VOLUME = "Volume"; 
      public static final String SS = "SS"; 
      public static final String DBO = "DBO"; 
      public static final String TN = "TN"; 
      public static final String TS = "TS"; 
      public static final String HAVE = "Have"; 
      public static final String HAVE_VOLUME = "volume"; 
      public static final String TAKE = "Take"; 
      public static final String TAKE_MASS_WATER = "mass_water"; 
      
      // The singleton instance of this ontology
      private static final RiverOntology theInstance = new RiverOntology(); 
     
      // This is the method to access the river ontology object 
      public static RiverOntology getInstance() { 
          return theInstance; 
        } 
      // Private constructor  
       private RiverOntology() { 
           
               
          // The river ontology extends the basic ontology
          super(ONTOLOGY_NAME, BasicOntology.getInstance(), new CFReflectiveIntrospector()); 
          try
          { 
            add(new ConceptSchema(MASS_WATER), MassWater.class); 
            add(new PredicateSchema(HAVE),Have.class);
            
            
            
            // Structure of the schema for the MassWater concept 
            ConceptSchema cs = (ConceptSchema) getSchema(MASS_WATER); 
            cs.add(VOLUME, (PrimitiveSchema) getSchema(BasicOntology.FLOAT), ObjectSchema.OPTIONAL); 
            cs.add(SS, (PrimitiveSchema) getSchema(BasicOntology.FLOAT), ObjectSchema.OPTIONAL); 
            cs.add(DBO, (PrimitiveSchema) getSchema(BasicOntology.FLOAT), ObjectSchema.OPTIONAL);
            cs.add(TN, (PrimitiveSchema) getSchema(BasicOntology.FLOAT), ObjectSchema.OPTIONAL);
            cs.add(TS, (PrimitiveSchema) getSchema(BasicOntology.FLOAT), ObjectSchema.OPTIONAL);
            //Structure of the schema for the HAVE predicate
            PredicateSchema ps = (PredicateSchema) getSchema(HAVE); 
            ps.add(HAVE_VOLUME, (PrimitiveSchema) getSchema(BasicOntology.FLOAT)); 
            System.out.println("gola");
            // Structure of the schema for the Take agent action 
            //AgentActionSchema as = (AgentActionSchema) getSchema(TAKE); 
            //as.add(TAKE_MASS_WATER, (ConceptSchema) getSchema(MASS_WATER)); 
          }  
      catch(OntologyException oe) { 
          }  
        } 
} 

