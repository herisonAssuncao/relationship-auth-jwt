package br.com.herison.relationship.controller;

import br.com.herison.relationship.dto.ResponseDTO;
import br.com.herison.relationship.request.RoleRequest;
import br.com.herison.relationship.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping
    public ResponseEntity<ResponseDTO> list() {
        ResponseDTO responseDTO = roleService.list();
        return ResponseEntity.status(responseDTO.getStatus()).body(responseDTO);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> save(@RequestBody RoleRequest roleRequest) {
        ResponseDTO responseDTO = roleService.save(roleRequest);
        return ResponseEntity.status(responseDTO.getStatus()).body(responseDTO);
    }

    @PutMapping("{id}")
    @ResponseStatus
    public ResponseEntity<ResponseDTO> update(@PathVariable Long id, @RequestBody RoleRequest roleRequest) {
        ResponseDTO responseDTO = roleService.update(id, roleRequest);
        return ResponseEntity.status(responseDTO.getStatus()).body(responseDTO);
    }
}
