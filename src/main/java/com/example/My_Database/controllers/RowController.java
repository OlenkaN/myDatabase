package com.example.My_Database.controllers;

import com.example.My_Database.Domain.Entity.Database;
import com.example.My_Database.Domain.Entity.DatabaseManager;
import com.example.My_Database.Domain.Entity.Row;
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
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "RowController")
@RestController
@RequestMapping(value = {"/db/{dbName}/table/{tableName}/row"}, produces = APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class RowController {

    private final DatabaseManager databaseManager = DatabaseManager.getInstance();

    @Operation(summary = "Find all rows in table",
          responses = {
                @ApiResponse(responseCode = "200", description = "Successfully find all rows",
                      content = @Content(schema = @Schema(implementation = List.class)))
          })
    @GetMapping
    public ResponseEntity<ArrayList<Row>> getTablesList(@PathVariable String dbName, @PathVariable String tableName) {
        log.info("Find list of table");
        return ResponseEntity
              .status(OK)
              .body(databaseManager.getDatabases()
                    .get(dbName).getTables().get(tableName).getRows());
    }


    @Operation(summary = "Add row",
          responses = {
                @ApiResponse(responseCode = "201", description = "Successfully add row",
                      content = @Content(schema = @Schema(implementation = Database.class))),
                @ApiResponse(responseCode = "400", description = "Bad request",
                      content = @Content(schema = @Schema(implementation = DuplicateKeyException.class)))
          })
    @PostMapping
    public ResponseEntity<?> addNewRow(@PathVariable String dbName, @PathVariable String tableName, @Valid @RequestBody Row row) {
        log.info("Started to add new row");
        return ResponseEntity
              .status(CREATED)
              .body(databaseManager.getDatabases()
                    .get(dbName).get(tableName).addRow(row));
    }

    @Operation(summary = "Delete row by id",
          responses =
                {
                      @ApiResponse(responseCode = "204", description = "Successfully delete table by name", content = @Content(schema = @Schema(implementation = String.class))),
                      @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class)))
                })

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTableByName(@PathVariable String tableName, @PathVariable String dbName, @PathVariable String id) {
        log.info("Delete row by id = {}", id);
        databaseManager.getDatabases().get(dbName).get(tableName).deleteRow(Integer.parseInt(id));
        return ResponseEntity
              .noContent()
              .build();
    }

}