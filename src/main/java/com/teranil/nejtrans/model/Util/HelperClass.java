package com.teranil.nejtrans.model.Util;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


public class HelperClass {

    public static final Integer EnAttente =1;
    public static final Integer EnTraitement =2;
    public static final Integer Terminer =3;

    @Data
    @NoArgsConstructor
    public static class RapportHelper{
        public String month;
        public Integer countImport=0;
        public Integer countExport=0;
        public Integer countTotal=0;
    }

    @Data
    @NoArgsConstructor
    public static class RapportHelperEmployee{
        public String month;
        public List<data> data=new ArrayList<>();
    }

    @Data
    @NoArgsConstructor
    public static class ChartperYear{
        public String month;
        public Integer expo = 0;
        public Integer impo = 0;
        public Integer total = 0;

    }



    @Data
    public static class data{
        public Integer countImport=0;
        public Integer countExport=0;
        public Integer countTotal=0;
        public String username="";
    }

    @Data
    public static class DossierForm {
        private String typeDossier;
        private String operation;
        private String username;

    }

    @Data
    public static class DossierByUserAndYear{
        private Integer year;
        private String Month;
        private Integer Count ;
    }

    @Data
    public static class PasswordResetForm{
        private String email;
        private String code;
        private String newPassword;
        private String confirmPassword;
    }

    @Data
    public static class UserEventForm {
        private String dateStart;
        private String dateEnd;
        private String description;
        private String title;
        private String username;
    }


    @Data
    public static class CounterClass {
        private Integer LastMounth = 0;
        private Integer ThisMounth = 0;
        private Integer Result = 0;
    }
    @Data
    public static class CounterClassYear {
        private Integer LastYear = 0;
        private Integer ThisYear = 0;
        private Integer Result = 0;
    }

}
