package com.example.My_Database.controllers;

import com.example.My_Database.Domain.Entity.Database;
import com.example.My_Database.storage.MySQL.MySQLClient;
import com.example.My_Database.storage.Storage;
import com.example.My_Database.storage.mongo.MongoDBClient;
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
import java.util.*;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "DatabaseController")
@RestController
@RequestMapping(value = {"/{type}/db"}, produces = APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class DBSQLController {

    @Operation(summary = "Find all db",
          responses = {
                @ApiResponse(responseCode = "200", description = "Successfully find db",
                      content = @Content(schema = @Schema(implementation = List.class)))
          })
    @GetMapping
    public ResponseEntity<ArrayList<String>> getDBList(@PathVariable String type) {
        log.info("Find list of db");
        Storage client = getStorageClient(type);
        if (client == null) {
            return null;
        }
        return ResponseEntity
              .status(OK)
              .body(client.getDBNames());
    }

    @Operation(summary = "Find db by name",
          responses = {
                @ApiResponse(responseCode = "200", description = "Successfully find db",
                      content = @Content(schema = @Schema(implementation = List.class))),
                @ApiResponse(responseCode = "404", description = "DB not found",
                      content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class)))

          })
    @GetMapping(path = "/{name}")
    public ResponseEntity<?> getDBById(@PathVariable String type, @PathVariable String name) {
        log.info("Find db");
        Storage client = getStorageClient(type);
        if (client == null) {
            return null;
        }
        return ResponseEntity
              .status(OK)
              .body(client.loadDB(name));
    }


    @Operation(summary = "Create db",
          responses = {
                @ApiResponse(responseCode = "201", description = "Successfully created db",
                      content = @Content(schema = @Schema(implementation = Database.class))),
                @ApiResponse(responseCode = "400", description = "Db already exist in db",
                      content = @Content(schema = @Schema(implementation = DuplicateKeyException.class)))
          })
    @PostMapping
    public ResponseEntity<?> createNewDB(@PathVariable String type, @Valid @RequestBody String databaseRequest) {
        log.info("Started to create new database");
        Storage client = getStorageClient(type);
        if (client == null) {
            return null;
        }
        Database request = Deserializer.getGson().fromJson(databaseRequest, Database.class);

        return ResponseEntity
              .status(CREATED)
              .body(client.saveDB(request));
    }


    @Operation(summary = "Delete db by name",
          responses =
                {
                      @ApiResponse(responseCode = "204", description = "Successfully delete db by id", content = @Content(schema = @Schema(implementation = String.class))),
                      @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class)))
                })

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteDBById(@PathVariable String type, @PathVariable String name) {
        log.info("Delete db by name = {}", name);
        Storage client = getStorageClient(type);
        if (client == null) {
            return null;
        }
        client.dropDB(name);
        return ResponseEntity
              .noContent()
              .build();
    }

    private static Storage getStorageClient(String storageType) {
        if (storageType.equals("mongo")) {
            return new MongoDBClient();
        } else if (storageType.equals("mysql")) {
            return new MySQLClient();
        }
        return null;
    }
}
