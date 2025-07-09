/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapersistenciamusica.Implementacion;

import com.github.javafaker.Faker;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mycompany.sistemapersistenciamusica.Interfaces.IUtilidadDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.bson.types.ObjectId;
import sistemadominiomusica.Dominio.Album;
import sistemadominiomusica.Dominio.Artista;
import sistemadominiomusica.Dominio.Cancion;
import sistemadominiomusica.Dominio.Genero;
import sistemadominiomusica.Dominio.Integrante;
import sistemadominiomusica.Dominio.IntegranteRol;
import sistemadominiomusica.Dominio.TipoArtista;

/**
 *
 * @author santi
 */
public class UtilidadDAO implements IUtilidadDAO {

    private final String COLECCION_ARTISTAS = "artistas";
    private final String COLECCION_ALBUMES = "albumes";
  
    /**
     * Metodo que inserta masivamente 60 artistas, siendo 30 de estos solistas y 30 bandas, por cada artista 
     * añade 2 albumes con 3 canciones
     */
  @Override
    public void insertarDatos() {
        MongoDatabase db = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Artista> colArtistas = db.getCollection(COLECCION_ARTISTAS, Artista.class);
        MongoCollection<Album> colAlbumes = db.getCollection(COLECCION_ALBUMES, Album.class);

        Faker faker = new Faker();
        Random random = new Random();

        List<Artista> artistas = new ArrayList<>();
        List<Album> albumes = new ArrayList<>();

        for (int i = 1; i <= 90; i++) {
            boolean esSolista = i <= 45;

            Artista artista = new Artista();
            artista.setId(new ObjectId());
            artista.setNombre(faker.artist().name());
            artista.setGenero(Genero.values()[random.nextInt(Genero.values().length)]);
            artista.setImagen("https://dummyimage.com/300x300");
            artista.setTipo(esSolista ? TipoArtista.SOLISTA : TipoArtista.GRUPO);

            if (!esSolista) {
                List<Integrante> integrantes = new ArrayList<>();
                int cantidadIntegrantes = 2 + random.nextInt(3);
                for (int j = 0; j < cantidadIntegrantes; j++) {
                    Integrante integrante = new Integrante();
                    integrante.setNombre(faker.name().fullName());
                    integrante.setRol(IntegranteRol.values()[random.nextInt(IntegranteRol.values().length)]);
                    integrante.setFechaIngreso(faker.date().past(3000, TimeUnit.DAYS));
                    integrante.setFechaSalida(null);
                    integrante.setActivo(true);
                    integrantes.add(integrante);
                }
                artista.setIntegrantes(integrantes);
            }

            artistas.add(artista);

            for (int j = 0; j < 2; j++) {
                Album album = new Album();
                album.setId(new ObjectId());
                album.setNombre(faker.book().title());
                album.setGenero(artista.getGenero());
                album.setFechaLanzamiento(faker.date().past(2000, TimeUnit.DAYS));
                album.setImagenDireccion("https://dummyimage.com/200x200");
                album.setIdArtista(artista.getId());

                List<Cancion> cancionesAlbum = new ArrayList<>();
                for (int k = 0; k < 3; k++) {
                    Cancion cancion = new Cancion();
                    cancion.setId(new ObjectId());
                    cancion.setTitulo(faker.rockBand().name() + " " + faker.music().instrument());
                    cancion.setDuracion(2 + random.nextDouble() * 4);
                    cancion.setIdArtista(artista.getId());

                    cancionesAlbum.add(cancion);
                }

                album.setCanciones(cancionesAlbum);
                albumes.add(album);
            }
        }

        colArtistas.insertMany(artistas);
        colAlbumes.insertMany(albumes);

        System.out.println("Datos de prueba insertados correctamente.");
    }



}
