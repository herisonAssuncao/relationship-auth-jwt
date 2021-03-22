package br.com.herison.relationship.request;

import lombok.Data;

import java.util.List;

@Data
public class RouteRequest {
    private String name;
    private String path;
    private List<RouteRequest> routes;
}
