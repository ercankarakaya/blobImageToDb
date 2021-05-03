package com.ercan.entity;

import com.ercan.entity.base.BaseModel;
import lombok.*;
import org.hibernate.annotations.Columns;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "product")
public class Product extends BaseModel {

    @NotNull(message = "Product name is required.")
    @Size(min = 2, max = 30, message = "Product name must be between 2 and 30 characters.")
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull(message = "Product description is required.")
    //@Size(min = 10, max = 1000, message = "Product description must be between 10 and 1000 characters.")
    @Column(name = "description", nullable = false)
    private String description;

    //@Size(min = 2, max = 10)
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private double price;

    @Lob
    @Column(name = "image", length = Integer.MAX_VALUE, nullable = true)
    private byte[] image;


}
