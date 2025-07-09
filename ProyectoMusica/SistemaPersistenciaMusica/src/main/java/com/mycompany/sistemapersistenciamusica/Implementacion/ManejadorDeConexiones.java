/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapersistenciamusica.Implementacion;


import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/**
 *
 * @author santi
 */
public class ManejadorDeConexiones {
    private static final String BASE_DATOS = "bibliotecaMusical";

    public static MongoDatabase obtenerBaseDatos() {
        
            return crearMongoDatabase(BASE_DATOS);
        
    }

   
    public static MongoDatabase crearMongoDatabase(String nombreBaseDatos) {

        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()));


        MongoClientSettings configuraciones = MongoClientSettings.builder().codecRegistry(pojoCodecRegistry).build();

        MongoClient cliente = MongoClients.create(configuraciones);


        MongoDatabase baseDatos = cliente.getDatabase(nombreBaseDatos);
        return baseDatos;
    }

  

}
