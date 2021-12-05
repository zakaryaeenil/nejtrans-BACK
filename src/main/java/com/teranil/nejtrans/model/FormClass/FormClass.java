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
