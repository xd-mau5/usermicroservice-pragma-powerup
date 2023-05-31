package com.pragma.powerup.usermicroservice.adapters.driving.http.controller;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.UserRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.PersonResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IUserHandler;
import com.pragma.powerup.usermicroservice.configuration.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.pragma.powerup.usermicroservice.configuration.Constants.EMPLOYEE_ROLE_ID;
import static com.pragma.powerup.usermicroservice.configuration.Constants.OWNER_ROLE_ID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@SecurityRequirement(name = "jwt")
public class UserRestController {
    private final IUserHandler userHandler;

    @Operation(summary = "Add a new user",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "User already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "403", description = "Role not allowed for user creation",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PostMapping("")
    public ResponseEntity<Map<String, String>> saveUser(@RequestBody UserRequestDto userRequestDto) {
        userHandler.saveUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.USER_CREATED_MESSAGE));
    }

    @Operation(summary = "Add a new owner",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Owner created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Owner already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "403", description = "Role not allowed for owner creation",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "400", description = "User have a invalid mail",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "422", description = "User is not over 18 years old",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PatchMapping("/owner/{ownerId}")
    public ResponseEntity<Map<String, String>> saveOwner(@PathVariable Long ownerId) {
        UserRequestDto userRequestDto = new UserRequestDto(ownerId, OWNER_ROLE_ID);
        userHandler.saveOwner(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.OWNER_CREATED_MESSAGE));
    }
    @Operation(summary = "Add a new employee",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Employee created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Employee already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "403", description = "Role not allowed for employee creation",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "400", description = "User have a invalid mail",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PatchMapping("/employee/{userId}")
    public ResponseEntity<Map<String, String>> saveEmployee(@PathVariable Long userId) {
        UserRequestDto userRequestDto = new UserRequestDto(userId, EMPLOYEE_ROLE_ID);
        userHandler.saveEmployee(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.EMPLOYEE_CREATED_MESSAGE));
    }
    @Operation(summary = "Delete an user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User deleted",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "404", description = "User not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @DeleteMapping("")
    public ResponseEntity<Map<String, String>> deleteUser(@RequestBody UserRequestDto userRequestDto) {
        userHandler.deleteUser(userRequestDto);
        return ResponseEntity.ok(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.USER_DELETED_MESSAGE));
    }
    @Operation(summary = "Get all the providers",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All providers returned",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PersonResponseDto.class)))),
                    @ApiResponse(responseCode = "404", description = "No data found",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @GetMapping("/provider")
    public ResponseEntity<List<PersonResponseDto>> getAllProviders(@Parameter(description = "Number of the page to list providers") @RequestParam int page) {
        return ResponseEntity.ok(userHandler.getProvider(page));
    }
    @Operation(summary = "Get a provider user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Provider user returned",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "User not found with provider role",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @GetMapping("/provider/{id}")
    public ResponseEntity<PersonResponseDto> getProvider(@PathVariable Long id) {
        return ResponseEntity.ok(userHandler.getProvider(id));
    }
    @Operation(summary = "Get a employee user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee user returned",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "User not found with employee role",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @GetMapping("/employee/{id}")
    public ResponseEntity<PersonResponseDto> getEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(userHandler.getEmployee(id));
    }
    @Operation(summary = "Get a client user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Client user returned",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "User not found with client role",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @GetMapping("/client/{id}")
    public ResponseEntity<PersonResponseDto> getClient(@PathVariable Long id) {
        return ResponseEntity.ok(userHandler.getClient(id));
    }
}
