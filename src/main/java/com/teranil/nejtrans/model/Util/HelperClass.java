package com.teranil.nejtrans.model.Util;

import lombok.Data;



public class HelperClass {

    public static final Integer EnAttente =1;
    public static final Integer EnTraitement =2;
    public static final Integer Terminer =3;



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
    }

    @Data
    public static class UserEventForm {
        private String dateStart;
        private String dateEnd;
        private String description;
        private String title;
        private String username;
    }





}
