package com.example.iratxe.rendecine.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aitor on 29/01/17.
 */
public class Frases {

    List<Frase> fraseList = new ArrayList<>();

    public List<Frase> getFraseList() {
        return fraseList;
    }

    public void setFraseList(List<Frase> fraseList) {
        this.fraseList = fraseList;
    }

    public static class Frase {
        String srcImg;
        String opc1;
        String opc2;
        String opc3;
        String sol;

        public String getSrcImg() {
            return srcImg;
        }

        public void setSrcImg(String srcImg) {
            this.srcImg = srcImg;
        }

        public String getOpc1() {
            return opc1;
        }

        public void setOpc1(String opc1) {
            this.opc1 = opc1;
        }

        public String getOpc2() {
            return opc2;
        }

        public void setOpc2(String opc2) {
            this.opc2 = opc2;
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
    }
}
