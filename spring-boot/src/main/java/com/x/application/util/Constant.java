package com.x.application.util;

import java.util.Map;

public class Constant {
    public final static String COLUMN_TYPE_STRING = "string";
    public final static String COLUMN_TYPE_ENUM = "enum";
    public final static String COLUMN_TYPE_ENUMS = "enums";
    public final static String COLUMN_TYPE_INTEGER = "integer";
    public final static String COLUMN_TYPE_FLOAT = "float";
    public final static String COLUMN_TYPE_DATE = "date";
    public final static String COMMA = ",";
    public final static String YYYY_MM_DD = "yyyy-MM-dd";
    public final static String PHOTO_TEMPLATE_URL = "/upload/photo/template/";
    public final static String PHOTO_HEAD_URL = "/upload/photo/head/";
    public final static String MINUS = "-";
    public final static String VLINE= "\\|";
    public final static String EQUALS= "=";
    public final static String ZERO= "0";
    public final static String DOT_PNG= ".png";
    public final static String REDUCE_CRM_ORG = "redcube.crm.org";
    public final static String CRM_ORG = "CrmOrg";
    public final static String ORG_FULLNAME_PINYIN = "orgFullName.pinyin";
    public final static String REDUCE_DC_ESTATE = "redcube.dc.estate";
    public final static String DC_ESTATE = "DcEstate";
    public final static String ESTATE_ID = "estateId";
    public final static String ESTATE_NAME_PINYIN = "estateName.pinyin";
    public final static String ESTATE_ALIAS_PINYIN = "estateAlias.pinyin";
    public final static String ESTATE_ADDRESS_PINYIN = "estateAddress.pinyin";
    public final static String DISTRICT_ID = "districtId";

    private String smsUrl;
    private Map<String, String> smsParams;

    public String getSmsUrl() {
        return this.smsUrl;
    }

    public void setSmsUrl(String smsUrl) {
        this.smsUrl = smsUrl;
        System.out.println("dddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
    }

    public Map<String, String> getSmsParams() {
        return this.smsParams;
    }

    public void setSmsParams(Map<String, String> smsParams) {
        this.smsParams = smsParams;
    }


    public static void swap(Integer a, Integer b){
        int c = a;
        a = b;
        b =c;
    }

}
