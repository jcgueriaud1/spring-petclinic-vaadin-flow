package org.springframework.samples.petclinic.ui;

public interface HasTestView {


    String getUrl();

    default String getView() {
        return "";
    }
}
