package com.example.comfywishlist.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "WishItem")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "url")
    private String url;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
}

