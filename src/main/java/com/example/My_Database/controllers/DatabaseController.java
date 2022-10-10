package com.example.My_Database.controllers;

import com.example.My_Database.Domain.Entity.Database;
import com.example.My_Database.Domain.Entity.DatabaseManager;
import com.example.My_Database.Domain.Entity.Table;
import com.example.My_Database.utils.toGson.Deserializer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "DatabaseController")
@RestController
@RequestMapping(value = {"/db"}, produces = APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class DatabaseController {

    private final DatabaseManager databaseManager = DatabaseManager.getInstance();

    @Operation(summary = "Find all db",
          responses = {
                @ApiResponse(responseCode = "200", description = "Successfully find db",
                      content = @Content(schema = @Schema(implementation = List.class)))
          })
    @GetMapping
    public ResponseEntity<Collection<Database>> getDBList() {
        log.info("Find list of db");
        return ResponseEntity
              .status(OK)
              .body(databaseManager.getDatabases().values());
    }

    @Operation(summary = "Find db by name",
          responses = {
                @ApiResponse(responseCode = "200", description = "Successfully find db",
                      content = @Content(schema = @Schema(implementation = List.class))),
                @ApiResponse(responseCode = "404", description = "DB not found",
                      content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class)))

          })
    @GetMapping(path = "/{name}")
    public ResponseEntity<?> getDBById(@PathVariable String name) {
        log.info("Find db");
        return ResponseEntity
              .status(OK)
              .body(databaseManager.getDatabases().get(name));
    }


    @Operation(summary = "Create db",
          responses = {
                @ApiResponse(responseCode = "201", description = "Successfully created db",
                      content = @Content(schema = @Schema(implementation = Database.class))),
                @ApiResponse(responseCode = "400", description = "Db already exist in db",
                      content = @Content(schema = @Schema(implementation = DuplicateKeyException.class)))
          })
    @PostMapping
    public ResponseEntity<?> createNewDB(@Valid @RequestBody String databaseRequest) {
        log.info("Started to create new database");
        Database request = Deserializer.getGson().fromJson(databaseRequest, Database.class);

        return ResponseEntity
              .status(CREATED)
              .body(databaseManager.add(request));
    }

    @Operation(summary = "load db",
          responses = {
                @ApiResponse(responseCode = "201", description = "Successfully load db",
                      content = @Content(schema = @Schema(implementation = Database.class))),
                @ApiResponse(responseCode = "400", description = "Failed to load db",
                      content = @Content(schema = @Schema(implementation = DuplicateKeyException.class)))
          })
    @GetMapping(path = "/file/{name}")
    public ResponseEntity<?> loadDB(@PathVariable String name) {
        log.info("Started to create new database");

        return ResponseEntity
              .status(CREATED)
              .body(databaseManager.add(Objects.requireNonNull(Database.ReadFromFile(name))));
    }

    @Operation(summary = "Update DB",
          responses =
                {
                      @ApiResponse(responseCode = "201", description = "Successfully updated db", content = @Content(schema = @Schema(implementation = Database.class))),
                      @ApiResponse(responseCode = "404", description = "DB not found", content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class))),
                      @ApiResponse(responseCode = "400", description = "Attribute already exist in db",
                            content = @Content(schema = @Schema(implementation = DuplicateKeyException.class)))
                })

    @PutMapping(path = "/{name}")
    public ResponseEntity<?> update(@PathVariable String name, @RequestBody String databaseRequest) {
        log.info("Started to update Business Specification");

        Database db = databaseManager.getDatabases().get(name);
        Database request = Deserializer.getGson().fromJson(databaseRequest, Database.class);
        return ResponseEntity
              .status(ACCEPTED)
              .body(db.update(request));
    }

    @Operation(summary = "Delete db by name",
          responses =
                {
                      @ApiResponse(responseCode = "204", description = "Successfully delete db by id", content = @Content(schema = @Schema(implementation = String.class))),
                      @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class)))
                })

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteDBById(@PathVariable String name) {
        log.info("Delete db by name = {}", name);
        databaseManager.delete(name);
        return ResponseEntity
              .noContent()
              .build();
    }
}
