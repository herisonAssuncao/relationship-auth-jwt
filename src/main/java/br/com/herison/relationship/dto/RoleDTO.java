package br.com.herison.relationship.dto;

import br.com.herison.relationship.model.Role;
import br.com.herison.relationship.model.Route;
import lombok.*;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

    private Long id;
    private String name;
    private String description;
    private List<RouteDTO> routes;

    public static List<RoleDTO> toList(List<Role> items) {
        if (CollectionUtils.isEmpty(items)) {
            return Collections.emptyList();
        }
        return items.stream().map(
                item -> RoleDTO
                        .builder()
                        .id(item.getId())
                        .name(item.getName())
                        .description(item.getDescription())
                        .routes(toListRoutes(item.getRoutes()))
                        .build()
        ).collect(Collectors.toList());
    }

    public static List<RouteDTO> toListRoutes(List<Route> items) {
        if (CollectionUtils.isEmpty(items)) {
            return Collections.emptyList();
        }
        return items.stream().map(
                item -> RouteDTO
                        .builder()
                        .id(item.getId())
                        .name(item.getName())
                        .path(item.getPath())
                        .routes(toListRoutes(item.getRoutes()))
                        .build()
        ).collect(Collectors.toList());
    }

}
