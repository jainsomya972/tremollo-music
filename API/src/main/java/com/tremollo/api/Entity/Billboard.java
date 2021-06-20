package com.tremollo.api.Entity;

//import com.tremollo.api.Types.ContentJson;
import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "\"billboards\"")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Billboard {

    @Id
    @Column(name = "billboard_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer billboardId;

    @Column(name = "month")
    @Temporal(TemporalType.DATE)
    Date month;

    @Column(name = "contents", columnDefinition = "jsonb")
    @Type(type = "jsonb")
    JsonNode contents;

    public Integer getBillboardId() {
        return billboardId;
    }

    public void setBillboardId(Integer billboardId) {
        this.billboardId = billboardId;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return "Billboard{" +
                "billboardId=" + billboardId +
                ", month=" + month +
                ", contents=" + contents +
                '}';
    }

    public JsonNode getContents() {
        return contents;
    }

    public void setContents(JsonNode contents) {
        this.contents = contents;
    }
}
