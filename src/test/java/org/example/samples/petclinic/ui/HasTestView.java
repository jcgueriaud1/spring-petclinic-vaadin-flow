package org.example.samples.petclinic.ui;

public interface HasTestView {


    String getUrl();

    default String getView() {
        return "";
    }
}
