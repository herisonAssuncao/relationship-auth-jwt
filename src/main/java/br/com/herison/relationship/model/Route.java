package br.com.herison.relationship.model;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "routes")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    @Column(unique = true)
    private String path;

    @ManyToMany(mappedBy = "routes")
    private List<Role> roles;

    @OneToMany(mappedBy = "route", cascade=CascadeType.ALL)
    private List<Route> routes;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;
}
