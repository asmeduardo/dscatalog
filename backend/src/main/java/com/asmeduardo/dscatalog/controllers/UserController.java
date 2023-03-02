package com.asmeduardo.dscatalog.controllers;

import com.asmeduardo.dscatalog.dto.UserDTO;
import com.asmeduardo.dscatalog.dto.UserInsertDTO;
import com.asmeduardo.dscatalog.dto.UserUpdateDTO;
import com.asmeduardo.dscatalog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> getAllUsersPaged(Pageable pageable) {
        Page<UserDTO> dto = userService.getAllUsersPaged(pageable);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO dto = userService.getUserById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<UserDTO> insertUser(@Valid @RequestBody UserInsertDTO dto) {
        UserDTO newDto = userService.insertUser(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDto.getId()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO dto) {
        UserDTO newDTO = userService.updateUser(id, dto);
        return ResponseEntity.ok().body(newDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}

