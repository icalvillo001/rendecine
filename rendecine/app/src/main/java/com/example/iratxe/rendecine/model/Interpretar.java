package com.example.iratxe.rendecine.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aitor on 28/01/17.
 */
public class Interpretar  {

    List<inter> interList = new ArrayList<>();

    public List<inter> getInterList() {
        return interList;
    }

    public void setInterList(List<inter> interList) {
        this.interList = interList;
    }

    public static  class inter{

        String srcVideo;

        public String getSrcVideo() {
            return srcVideo;
        }

        public void setSrcVideo(String srcVideo) {
            this.srcVideo = srcVideo;
        }
    }
}
