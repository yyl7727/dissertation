package com.gsm.dissertation.model;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "contenttype")
public class ContentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cid;

    @NotNull
    private String name;

    private Integer weight;

    @ManyToOne
    @Nullable
    @JoinColumn(name = "cid")
    private ContentType parent;

    @OneToMany(mappedBy = "parent")
    private List<ContentType> sublist;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Nullable
    public ContentType getParent() {
        return parent;
    }

    public void setParent(@Nullable ContentType parent) {
        this.parent = parent;
    }

    public List<ContentType> getSublist() {
        return sublist;
    }

    public void setSublist(List<ContentType> sublist) {
        this.sublist = sublist;
    }
}
