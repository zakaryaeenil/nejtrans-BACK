package com.teranil.nejtrans.model.FormClass;

import lombok.Data;



public class FormClass {
    @Data
    public static class DossierForm {
        private String typeDossier;
        private String username;
    }
    @Data
    public static class DossierByUserAndYear{
        private Integer year;
        private String Month;
        private Integer Count;
        private Integer CountReserved;

    }

}
