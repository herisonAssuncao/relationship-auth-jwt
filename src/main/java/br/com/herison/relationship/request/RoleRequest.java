package br.com.herison.relationship.request;

import lombok.Data;

import java.util.List;

@Data
public class RoleRequest {
    private String name;
    private String description;
    private List<RouteRequest> routes;
}
