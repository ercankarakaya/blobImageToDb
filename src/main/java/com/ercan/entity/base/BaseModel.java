package com.ercan.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public class BaseModel extends BaseIdModel{

    private static final long serialVersionUID = 1L;


   // @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_updated", nullable = false)
    private Date lastUpdated;

   // @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @Column(name = "user_id", length = 32)
    private String userId;

    @Column(name = "record_status")
    private Integer recordStatus;

//    @JsonIgnore
//    public String getCustomCreatedDate(){
//        Date dateCreated=getCreatedDate().getTime();
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'  'HH:mm:ss");
//        return dateFormat.format(dateCreated);
//    }
//
    @PrePersist
    public void prePersist() {
        this.createdDate = new Date();
        this.lastUpdated = new Date();
//        this.createdDate = Calendar.getInstance();
//        this.lastUpdated = Calendar.getInstance();
    }

    @PreUpdate
    public void preUpdate() {
        this.lastUpdated=new Date();
        //this.lastUpdated = Calendar.getInstance();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
        result = prime * result + ((recordStatus == null) ? 0 : recordStatus.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        BaseModel other = (BaseModel) obj;
        if (createdDate == null) {
            if (other.createdDate != null)
                return false;
        } else if (!createdDate.equals(other.createdDate))
            return false;
        if (lastUpdated == null) {
            if (other.lastUpdated != null)
                return false;
        } else if (!lastUpdated.equals(other.lastUpdated))
            return false;
        if (recordStatus == null) {
            if (other.recordStatus != null)
                return false;
        } else if (!recordStatus.equals(other.recordStatus))
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        return true;
    }

}
