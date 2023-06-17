package com.killiann.paintify.controllers;

import com.killiann.paintify.exceptions.UserNotFoundException;
import com.killiann.paintify.models.User;
import com.killiann.paintify.payloads.LoginResponse;
import com.killiann.paintify.payloads.errors.GenericError;
import com.killiann.paintify.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "All", description = "Get all users", tags = "Get")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(array = @ArraySchema(schema = @Schema(implementation = User.class)))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized request",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GenericError.class))})
    })
    @GetMapping("/users")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    List<User> all() {
        return this.userRepository.findAll();
    }

    @Operation(summary = "By Id", description = "Get user by id", tags = "Get")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized request",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GenericError.class))}
            )
    })
    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    User one(@PathVariable Long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Operation(summary = "By Username", description = "Get user by username", tags = "Get")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized request",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GenericError.class))}
            )
    })
    @GetMapping("/users/username/{username}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    User one(@PathVariable String username) {
        return this.userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }
}