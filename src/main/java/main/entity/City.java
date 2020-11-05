package main.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "city")
@Data
public class City {

    @Id
    private Integer id;

    @Column(name = "city")
    private String city;

    @Column(name = "geo_lat")
    private Double lat;

    @Column(name = "geo_lon")
    private Double lon;
}
