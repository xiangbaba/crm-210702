package com.bjpowernode.crm.workbench.domain;

import java.io.Serializable;


/**
 * tbl_contacts_activity_relation
 * @author 
 */

public class TblContactsActivityRelation implements Serializable {
    private String id;

    private String contactsId;

    private String activityId;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContactsId() {
        return contactsId;
    }

    public void setContactsId(String contactsId) {
        this.contactsId = contactsId;
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
}