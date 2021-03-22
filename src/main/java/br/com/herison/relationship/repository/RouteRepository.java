package br.com.herison.relationship.repository;

import br.com.herison.relationship.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteRepository  extends JpaRepository <Route, Long> {

    List<Route> findByPath(String path);
}
