/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.prueba02;



/**
 *
 * @author anton
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Neo4jClass mineo = new Neo4jClass ("bolt://localhost:7687","neo4j","123");
        mineo.inserCaballo("pepe","13/08/2002","1");
        mineo.insertGanaederia("real ganaderia de ronda", "1974");
        mineo.inserConcurso("SICAP", 347,"A");
        mineo.relacionarcaballoGanaderia(1, "real ganaderia de ronda");
     
        
    }
      
}
