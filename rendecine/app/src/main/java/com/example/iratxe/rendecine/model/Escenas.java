package com.example.iratxe.rendecine.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aitor on 15/01/17.
 */
public class Escenas {

    List<Escena> escenaList = new ArrayList<>();

    public List<Escena> getEscenaList() {
        return escenaList;
    }

    public void setEscenaList(List<Escena> escenaList) {
        this.escenaList = escenaList;
    }

    public static class Escena {

        public int idEscena;
        public String opc1;
        public String opc2;
        public String opc3;
        public String sol;
        public String srcVideo;

        public int getIdEscena() {
            return idEscena;
        }

        public void setIdEscena(int idEscena) {
            this.idEscena = idEscena;
        }

        public String getOpc1() {
            return opc1;
        }

        public void setOpc1(String opc1) {
            this.opc1 = opc1;
        }

        public String getOpc3() {
            return opc3;
        }

        public void setOpc3(String opc3) {
            this.opc3 = opc3;
        }

        public String getSol() {
            return sol;
        }

        public void setSol(String sol) {
            this.sol = sol;
        }

        public String getSrcVideo() {
            return srcVideo;
        }

        public void setSrcVideo(String srcVideo) {
            this.srcVideo = srcVideo;
        }

        public String getOpc2() {
            return opc2;
        }

        public void setOpc2(String opc2) {
            this.opc2 = opc2;
        }
    }

}
