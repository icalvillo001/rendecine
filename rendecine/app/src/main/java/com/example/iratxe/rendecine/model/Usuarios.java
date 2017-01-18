package com.example.iratxe.rendecine.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aitor on 18/01/17.
 */
public class Usuarios {

    List<Usuario> usuarios= new ArrayList<>();

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public static class Usuario{

        String nombre;
        String email;
        String password;

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
