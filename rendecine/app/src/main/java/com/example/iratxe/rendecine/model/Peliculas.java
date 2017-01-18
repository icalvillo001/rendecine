package com.example.iratxe.rendecine.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aitor on 18/01/17.
 */
public class Peliculas {

    List<Peliculas> peliculasList = new ArrayList<>();

    public List<Peliculas> getPeliculasList() {
        return peliculasList;
    }

    public void setPeliculasList(List<Peliculas> peliculasList) {
        this.peliculasList = peliculasList;
    }

    public static class Pelicula{

        int idPelicula;
        String nombre;
        int totalMen;

        public int getIdPelicula() {
            return idPelicula;
        }

        public void setIdPelicula(int idPelicula) {
            this.idPelicula = idPelicula;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public int getTotalMen() {
            return totalMen;
        }

        public void setTotalMen(int totalMen) {
            this.totalMen = totalMen;
        }
    }
}
