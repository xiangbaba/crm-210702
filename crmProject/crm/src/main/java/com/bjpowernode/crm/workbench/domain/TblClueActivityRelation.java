package com.bjpowernode.crm.workbench.domain;

import java.io.Serializable;


/**
 * tbl_clue_activity_relation
 * @author 
 */

public class TblClueActivityRelation implements Serializable {
    private String id;

    private String clueId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClueId() {
        return clueId;
    }

    public void setClueId(String clueId) {
        this.clueId = clueId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    private String activityId;

    private static final long serialVersionUID = 1L;
}