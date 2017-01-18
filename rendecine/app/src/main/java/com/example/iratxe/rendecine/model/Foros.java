package com.example.iratxe.rendecine.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aitor on 18/01/17.
 */
public class Foros {

    List<Foro> foroList = new ArrayList<>();

    public List<Foro> getForoList() {
        return foroList;
    }

    public void setForoList(List<Foro> foroList) {
        this.foroList = foroList;
    }

    static class Foro{

        int idForo;
        String asunto;
        String mensaje;

        public int getIdForo() {
            return idForo;
        }

        public void setIdForo(int idForo) {
            this.idForo = idForo;
        }

        public String getAsunto() {
            return asunto;
        }

        public void setAsunto(String asunto) {
            this.asunto = asunto;
        }

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }
    }


}
