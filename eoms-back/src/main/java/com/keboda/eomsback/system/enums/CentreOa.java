package com.keboda.eomsback.system.enums;

public enum CentreOa {

    JXKEAO("0105","嘉兴KEM"), GLPT("010203","管理平台"), ZJKBDJD("010204","机电中心"),
    ZJKBDDZ("010208","电子制造中心"), JXKSS("0124","嘉兴科赛思"), JXZBZX("010221","工程技术");

    private final String code;

    private final String name;
    CentreOa(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode(){
        return this.code;
    }

    public String getName(){
        return this.name;
    }

}
