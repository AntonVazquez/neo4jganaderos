/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.prueba02;

import java.util.ArrayList;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;
import static org.neo4j.driver.Values.parameters;


/**
 *
 * @author anton
 */
public class Neo4jClass implements AutoCloseable {
    
    private Driver driver;
    
    public Neo4jClass(String uri, String user, String password) {
        // Abrimos conexion
        this.driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    @Override
    public void close() throws Exception {
        driver.close();
        
    }
    
    public void inserCaballo(String nombreCaballo, String fechaNacimiento, String chip ) {
    Session sesion = driver.session();
    String strTrans =  sesion.writeTransaction(new TransactionWork<String>() {
        @Override
        public String execute(Transaction tx) {
            Result resultado = (Result) tx.run("CREATE(c:caballo {nombreCaballo: $nombreCaballo,fechaNacimiento: $fechaNacimiento,chip: $chip})", parameters("nombreCaballo",nombreCaballo,"fechaNacimiento",fechaNacimiento,"chip",chip));
            if (resultado.list().size() > 0) {
                System.out.println("nodo insertado");
            } else {
                System.out.println("nodo no insertado");
            }
            return "";
        }
    });
    }
    
     public void insertarCaballo(String nombreCaballo, String fechaNacimiento,String sexo,String chip, String nombreGanaderia, String nombreConcurso){
        
         
            String consultaCypher="MATCH (g.ganaderia) where g.nombreGanaderia='$nombreGanaderia' "+
          "MATCH (K:concurso) where k.nombreConcurso='$nombreConcurso' "+
          "CREATE (c:caballo{nombreCaballo:'$nombreCaballo', fechaNacimiento:'$fechaNacimiento', sexo:'$sexo', chip:'$chip'}) "+
          "CREATE(c)-[r1:pertenece{}]->(g) "+
          "CREATE(c)-[r2:aa ganado{}]->(k)";
          System.out.println(consultaCypher); 
          String consultaCypher2="MATCH (g:ganaderia) where g.nombreGanaderia='"+nombreGanaderia+"' "+
          "MATCH (k:concurso) where k.nombreConcurso='"+nombreConcurso+"' "+
          "CREATE (c:caballo{nombreCaballo:'"+nombreCaballo+"', fechaNacimiento:'"+fechaNacimiento+"', sexo:'"+sexo+"', chip:'"+chip+"'}) "+
          "CREATE(c)-[r1:pertenece{}]->(g) "+
          "CREATE(c)-[r2:A ganado{}]->(k)";
           System.out.println(consultaCypher2);
          Session sesion = driver.session();
          // Result resultado=sesion.run(consultaCypher,parameters("nombreGanaderia",nombreGanaderia,"nombreConcurso",nombreConcurso,"nommbreCaballo",NombreCaballo,"fechaNacimiento",fechaNacimiento,"sexo",sexo,"chip",chip));
          Result resultado2=sesion.run(consultaCypher2);
          sesion.close();
         
     }
    
      public void insertGanaederia (String nombreGanaderia, String fundacion ) {
    Session sesion = driver.session();
    String strTrans =  sesion.writeTransaction(new TransactionWork<String>() {
        @Override
        public String execute(Transaction tx) {
            Result resultado = (Result) tx.run("CREATE(g:ganaderia {nombreGanaderia: $nombreGanaderia,fundacion:$fundacion})", parameters("nombreGanaderia",nombreGanaderia,"fundacion",fundacion));
            if (resultado.list().size() > 0) {
                System.out.println("nodo insertado");
            } else {
                System.out.println("nodo no insertado");
            }
            return "";
        }
    });
  
    }
        public void inserConcurso(String nombreConcurso, int numeroParticipantes, String importanciaConcurso ) {
    Session sesion = driver.session();
    String strTrans =  sesion.writeTransaction(new TransactionWork<String>() {
        @Override
        public String execute(Transaction tx) {
            Result resultado = (Result) tx.run("CREATE(k:concurso {nombreConcurso: $nombreConcurso, numeroParticipantes:$numeroParticipantes, importanciaConcurso: $importanciaConcurso})", parameters("nombreConcurso",nombreConcurso,"numeroParticipantes",numeroParticipantes,"importanciaConcurso",importanciaConcurso));
            if (resultado.list().size() > 0) {
                System.out.println("nodo insertado");
            } else {
                System.out.println("nodo no insertado");
            }
            return "";
        }
    });
   
    sesion.close();
    }
   
        public void relacionarcaballoGanaderia(int chip, String nombreGanaderia) {
    Session sesion = driver.session();
    String strTrans =  sesion.writeTransaction(new TransactionWork<String>() {
        @Override
        public String execute(Transaction tx) {
            String texto ="MATCH (c:caballo),(g:ganaderia) where c.chip="+chip+" and g.nombreGanaderia='"+ nombreGanaderia+"' CREATE(c)-[r:pertenece]->(g)";
            System.out.println(texto);
            Result resultado = (Result) tx.run(texto);
           
             return "";
        }
    });
        }
        
         public void relacionarcaballoConcurso(String chip, String nombreConcurso) {
    Session sesion = driver.session();
    String strTrans =  sesion.writeTransaction(new TransactionWork<String>() {
        @Override
        public String execute(Transaction tx) {
            String texto = "MATCH (c:caballo{nombreCaballo:$nombreCaballo}),(k:concurso{nombreConcurso:$nombreConcurso})"+
            "CREATE (c)-[:gano]->(k)";
            System.out.println(texto);
            Result resultado = (Result) tx.run(texto);
           
             return "";
        }
    });
        }
         
         public ArrayList<Record> obtenerCaballo(){
        Session sesion = driver.session();
        ArrayList<Record> lista=new ArrayList<>();
        Result resultado=sesion.run("MATCH (c:caballo) return c.nombre,c.fechaNacimiento,c.chip");
        while(resultado.hasNext()){
            lista.add(resultado.next());
        }
       
        return lista;
       }
          public ArrayList<Record> obtenerGanaderia(){
        Session sesion = driver.session();
        ArrayList<Record> lista=new ArrayList<>();
        Result resultado=sesion.run("MATCH (g:ganaderia) return g.nombre,g.fundacion");
        while(resultado.hasNext()){
            lista.add(resultado.next());
        }
       
        return lista;
       }
             public ArrayList<Record> obtenerConcurso(){
        Session sesion = driver.session();
        ArrayList<Record> lista=new ArrayList<>();
        Result resultado=sesion.run("MATCH (co:concurso) return co.nombre,co.numeroParticipantes,co.importancioConcurso");
        while(resultado.hasNext()){
            lista.add(resultado.next());
        }
       
        return lista;
       }

            public void eliminarCaballo(String nombreCaballo){
        Session sesion = driver.session();
        String strTrans;
        strTrans = sesion.writeTransaction(new TransactionWork<String>(){
            @Override
            public String execute (Transaction tx) {
                Result resultado = (Result) tx.run("MATCH(c:caballo) WHERE c.nombreCaballo='" + nombreCaballo + "' DELETE c");
                return "";
                        }
            });
            sesion.close(); 
           }
           
            public void eliminarGanaderia(String nombreGanaderia){
        Session sesion = driver.session();
        String strTrans;
        strTrans = sesion.writeTransaction(new TransactionWork<String>(){
            @Override
            public String execute (Transaction tx) {
                String consulta = "MATCH(g:ganaderia) WHERE g.nombreGanaderia='" + nombreGanaderia + "' DELETE g";
                System.out.println(consulta);
                Result resultado = (Result) tx.run(consulta);
                return "";
                        }
            });
            sesion.close(); 
           }
            
             public void eliminarConcurso(String nombreConcurso){
        Session sesion = driver.session();
        String strTrans;
        strTrans = sesion.writeTransaction(new TransactionWork<String>(){
            @Override
            public String execute (Transaction tx) {
                Result resultado = (Result) tx.run("MATCH(k:concurso) WHERE k.nombreConcurso='" + nombreConcurso + "' DELETE k");
                return "";
                        }
            });
            sesion.close(); 
           }
             
             public void eliminarTodo(){
        Session sesion = driver.session();
        String strTrans;
        strTrans = sesion.writeTransaction(new TransactionWork<String>(){
            @Override
            public String execute (Transaction tx) {
                Result resultado = (Result) tx.run("MATCH(r) DETACH DELETE r");
                return "";
                        }
            });
            sesion.close();
        } 
            
             
         

           
}
             
