package com.bjpowernode.crm.settings.domain;

/**
 * tbl_dic_type
 * @author 
 */

public class TblDicType{

    private String code;

    private String name;

    @Override
    public String toString() {
        return "TblDicType{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

}