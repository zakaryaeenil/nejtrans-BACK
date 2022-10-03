package com.teranil.nejtrans.model.Util;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.teranil.nejtrans.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
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
    @NoArgsConstructor
    public static class ByRoleChartperYearPie{
        public String username;
        public Integer total = 0;
    }
    @Data
    @NoArgsConstructor
    public static class ChartOperationsperYear{
        public String month;
        public Integer tr = 0;
        public Integer ou = 0;
        public Integer on = 0;

    }

    @Data
    @NoArgsConstructor
    public static class ChartDispoperYear{
        public String month;
        public Integer enAttente = 0;
        public Integer enTraitement = 0;
        public Integer terminer = 0;

    }

    @Data
    public static class data{
        public Integer countImport=0;
        public Integer countExport=0;
        public Integer countTotal=0;
        public String username="";
    }

    @Data
    public static class rapportNejtransStat{
        public Integer countImport=0;
        public Integer countExport=0;
        public Integer countTotal=0;

        public Integer countEnTraitement=0;
        public Integer countEnAttente=0;
        public Integer countTerminer=0;

        public Integer countTr=0;
        public Integer countOu=0;
        public Integer countOn=0;
    }

    @Data
    public static class betweenToDate{
        private LocalDateTime startAt;
        private LocalDateTime endAt;
    }
    @Data
    public static class DossierForm {
        private String typeDossier;
        private String operation;
        private String username;

    }

    @Data
    public static class DossierBRD {
        private String bureau;
        private String regime;
        private String dum;

    }
    @Data
    public static class SignIngMethod {
        private String username;
        private String newusername;
        private String pass;
        private String currentPass;

    }
    @Data
    public static class DossierProForm {
        private LocalDateTime createdAt;
        private LocalDateTime endAt;
        private Long user_id;
    }
    @Data
    public static class AddDossierProForm {
        private Long folder_id;
        private Long folder_Pro_id;
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
        private Integer ResultMounth = 0;

        private Integer LastYear = 0;
        private Integer ThisYear = 0;
        private Integer Resultyear = 0;
    }
    @Data
    public static class CounterClassYear {
        private Integer LastYear = 0;
        private Integer ThisYear = 0;
        private Integer Result = 0;
    }

}
