package br.com.herison.relationship.service;

import br.com.herison.relationship.dto.ResponseDTO;
import br.com.herison.relationship.dto.RoleDTO;
import br.com.herison.relationship.model.Role;
import br.com.herison.relationship.model.Route;
import br.com.herison.relationship.repository.RoleRepository;
import br.com.herison.relationship.repository.RouteRepository;
import br.com.herison.relationship.request.RoleRequest;
import br.com.herison.relationship.request.RouteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RouteRepository routeRepository;

    public ResponseDTO list() {
        List<Role> roles = roleRepository.findAll();
        List<RoleDTO> rolesDTO = RoleDTO.toList(roles);
        return new ResponseDTO(rolesDTO, HttpStatus.OK);
    }

    public ResponseDTO save(RoleRequest roleRequest) {
        try {
            if (CollectionUtils.isEmpty(roleRequest.getRoutes())) {
                return new ResponseDTO("Rotas são obrigatórias!", HttpStatus.UNPROCESSABLE_ENTITY);
            }
            Role role = new Role();
            role.setName(roleRequest.getName());
            role.setDescription(roleRequest.getDescription());
            List<Route> routes = saveListRoute(roleRequest.getRoutes());
            List<Route> routesForRole = prepareListRoutesForRole(routes);
            role.setRoutes(routesForRole);
            roleRepository.save(role);
            return new ResponseDTO("Perfil com Permissões salvo com sucesso!", HttpStatus.CREATED);
        } catch (Throwable exception) {
            return new ResponseDTO("Erro ao Salvar Perfil com permissões!", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public ResponseDTO update(Long id, RoleRequest roleRequest) {
        try {
            Optional<Role> existingRole = roleRepository.findById(id);

            if (existingRole.isEmpty()) {
                return new ResponseDTO("Perfil não existe!", HttpStatus.NOT_FOUND);
            }

            if (CollectionUtils.isEmpty(roleRequest.getRoutes())) {
                return new ResponseDTO("Rotas são obrigatórias!", HttpStatus.UNPROCESSABLE_ENTITY);
            }

            Role role = new Role();
            role.setId(existingRole.get().getId());
            role.setName(roleRequest.getName());
            role.setDescription(roleRequest.getDescription());
            List<Route> routes = saveListRoute(roleRequest.getRoutes());
            List<Route> routesForRole = prepareListRoutesForRole(routes);
            role.setRoutes(routesForRole);
            roleRepository.save(role);
            return new ResponseDTO("Perfil e Permissões atualizado com sucesso!", HttpStatus.CREATED);
        } catch (Throwable exception) {
            return new ResponseDTO("Erro ao Salvar Perfil com permissões!", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    private List<Route> prepareListRoutesForRole(List<Route> routes) {
        List<Route> routesList = new ArrayList<>();
        routes.forEach(route -> {
            routesList.add(route);
            if (!CollectionUtils.isEmpty(route.getRoutes())) {
                List<Route> routesChildren = prepareListRoutesForRole(route.getRoutes());
                routesList.addAll(routesChildren);
            }
        });

        return routesList;
    }

    private List<Route> saveListRoutesChildren(List<RouteRequest> routesChildren, Route RouteMain) {
        return routesChildren.stream().map(routeChildren -> {
            Route route = new Route();
            List<Route> existingRoute = routeRepository.findByPath(routeChildren.getPath());
            if (existingRoute.stream().findFirst().isPresent()) {
                route.setId(existingRoute.stream().findFirst().get().getId());
            }

            route.setName(routeChildren.getName());
            route.setPath(routeChildren.getPath());
            route.setRoute(RouteMain);

            if (!CollectionUtils.isEmpty(routeChildren.getRoutes())) {
                route.setRoutes(saveListRoutesChildren(routeChildren.getRoutes(), route));
            }

            return route;
        }).collect(Collectors.toList());
    }

    private List<Route> saveListRoute(List<RouteRequest> routesRequest) {
        List<Route> routes = routesRequest.stream().map(routeRequest -> {
            Route route = new Route();
            List<Route> existingRoute = routeRepository.findByPath(routeRequest.getPath());
            if (existingRoute.stream().findFirst().isPresent()) {
                route.setId(existingRoute.stream().findFirst().get().getId());
            }

            route.setName(routeRequest.getName());
            route.setPath(routeRequest.getPath());

            if (!CollectionUtils.isEmpty(routeRequest.getRoutes())) {
                route.setRoutes(saveListRoutesChildren(routeRequest.getRoutes(), route));
            }

            return route;
        }).collect(Collectors.toList());

        return routeRepository.saveAll(routes);
    }
}
