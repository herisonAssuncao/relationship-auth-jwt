package br.com.herison.relationship.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class RouteDTO {
    private Long id;
    private String name;
    private String path;
    private List<RouteDTO> routes;
}
