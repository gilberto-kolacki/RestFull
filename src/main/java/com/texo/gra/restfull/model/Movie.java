package com.texo.gra.restfull.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "tb_movies")
public class Movie implements Serializable {

    private static final long serialVersionUID = 9058350955651091145L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String studios;

    @Column(nullable = false)
    private String producers;

    @Column(nullable = false)
    private Integer movieYear;

    @Column(nullable = false)
    private Boolean winner;

}
