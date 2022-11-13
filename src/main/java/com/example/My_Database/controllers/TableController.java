package com.example.My_Database.controllers;

import com.example.My_Database.Domain.Entity.AttributeRequest;
import com.example.My_Database.Domain.Entity.Database;
import com.example.My_Database.Domain.Entity.DatabaseManager;
import com.example.My_Database.Domain.Entity.Table;
import com.example.My_Database.utils.Hateoas;
import com.example.My_Database.utils.Resource;
import com.example.My_Database.utils.toGson.Deserializer;
import com.example.My_Database.utils.toGson.Serializer;
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
import java.util.HashMap;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "TableController")
@RestController
@RequestMapping(value = {"/db/{dbName}/table"}, produces = APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class TableController {

    private final DatabaseManager databaseManager = DatabaseManager.getInstance();

    @Operation(summary = "Find all tables in db",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully find all tables",
                            content = @Content(schema = @Schema(implementation = List.class)))
            })
    @GetMapping
    public ResponseEntity<?> getTablesList(@PathVariable String dbName) {
        log.info("Find list of table");
        HashMap<String, Resource> listTablesResources = new HashMap();
        listTablesResources.put("get database", new Resource("/db/" + dbName, "get"));
        var result = ResponseEntity
                .status(OK)
                .body(databaseManager.getDatabases()
                        .get(dbName).getTables().values());
        return ResponseEntity
                .status(OK)
                .body(Hateoas.formatResponse(Serializer.Serialize(result), listTablesResources));

    }

    @Operation(summary = "Find table by name",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully find table",
                            content = @Content(schema = @Schema(implementation = List.class))),
                    @ApiResponse(responseCode = "404", description = "Table not found",
                            content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class)))

            })
    @GetMapping(path = "/{tableName}")
    public ResponseEntity<?> getTableById(@PathVariable String dbName, @PathVariable String tableName) {
        log.info("Find table by name {}", tableName);
        HashMap<String, Resource> tableResources = linksForTable(dbName, tableName);
        var result = ResponseEntity
                .status(OK)
                .body(Serializer.Serialize(databaseManager.getDatabases()
                        .get(dbName).getTables().get(tableName)));
        return ResponseEntity
                .status(OK)
                .body(Hateoas.formatResponse(Serializer.Serialize(result), tableResources));
    }


    @Operation(summary = "Create table",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Successfully created table",
                            content = @Content(schema = @Schema(implementation = Database.class))),
                    @ApiResponse(responseCode = "400", description = "Table already exist in db",
                            content = @Content(schema = @Schema(implementation = DuplicateKeyException.class)))
            })
    @PostMapping
    public ResponseEntity<?> createNewTable(@PathVariable String dbName, @Valid @RequestBody String tableRequest) {
        log.info("Started to create new table");
        Table request = Deserializer.getGson().fromJson(tableRequest, Table.class);

        var result = ResponseEntity
                .status(CREATED)
                .body(databaseManager.getDatabases()
                        .get(dbName).addTable(request));
        HashMap<String, Resource> tableResources = linksForTable(dbName, request.getName());
        return ResponseEntity
                .status(OK)
                .body(Hateoas.formatResponse(Serializer.Serialize(result), tableResources));
    }


    @Operation(summary = "Update DB",
            responses =
                    {
                            @ApiResponse(responseCode = "201", description = "Successfully updated table", content = @Content(schema = @Schema(implementation = Database.class))),
                            @ApiResponse(responseCode = "404", description = "Table not found", content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class))),
                            @ApiResponse(responseCode = "400", description = "Attribute already exist in table",
                                    content = @Content(schema = @Schema(implementation = DuplicateKeyException.class)))
                    })

    @PutMapping(path = "/{tableName}")
    public ResponseEntity<?> update(@PathVariable String tableName, @PathVariable String dbName, @RequestBody String tableRequest) {
        log.info("Started to update Business Specification");

        Table table = databaseManager.getDatabases().get(dbName).getTables().get(tableName);
        Table request = Deserializer.getGson().fromJson(tableRequest, Table.class);
        HashMap<String, Resource> tableResources = linksForTable(dbName, tableName);

        return ResponseEntity
                .status(ACCEPTED)
                .body(Hateoas.formatResponse(Serializer.Serialize(table.update(request)), tableResources));
    }

    @Operation(summary = "Delete table by name",
            responses =
                    {
                            @ApiResponse(responseCode = "204", description = "Successfully delete table by name", content = @Content(schema = @Schema(implementation = String.class))),
                            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class)))
                    })

    @DeleteMapping("/{tableName}")
    public ResponseEntity<String> deleteTableByName(@PathVariable String tableName, @PathVariable String dbName) {
        log.info("Delete table by name = {}", tableName);
        databaseManager.getDatabases().get(dbName).deleteTable(tableName);
        HashMap<String, Resource> tableResources = new HashMap<>();
        tableResources.put("get all tables", new Resource("/db/" + dbName, "get"));

        return ResponseEntity
                .ok().body(Hateoas.formatResponse("Delete successful", tableResources));
    }

    @Operation(summary = "Add attribute to table ",
            responses =
                    {
                            @ApiResponse(responseCode = "200", description = "Successfully add attr", content = @Content(schema = @Schema(implementation = String.class))),
                            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class)))
                    })

    @PostMapping("/{tableName}/attr")
    public ResponseEntity<?> addAttribute(@PathVariable String tableName, @PathVariable String dbName, @RequestBody AttributeRequest attributeRequest) {
        log.info("Add attribute  {}", attributeRequest.getName());
        HashMap<String, Resource> tableResources = linksForTable(dbName, tableName);

        return ResponseEntity
                .status(CREATED)
                .body(Hateoas.formatResponse(Serializer.Serialize(databaseManager.getDatabases().get(dbName).getTables()
                        .get(tableName).addAttr(attributeRequest.getAttr())), tableResources));
    }

    @Operation(summary = "Delete attribute to table ",
            responses =
                    {
                            @ApiResponse(responseCode = "200", description = "Successfully delete attr", content = @Content(schema = @Schema(implementation = String.class))),
                            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class)))
                    })

    @DeleteMapping("/{tableName}/attr")
    public ResponseEntity<String> deleteAttribute(@PathVariable String tableName, @PathVariable String dbName, @RequestBody String nameOfAttr) {
        log.info("Delete attribute  {}", nameOfAttr);
        databaseManager.getDatabases().get(dbName).getTables().get(tableName).deleteAttr(nameOfAttr);
        HashMap<String, Resource> tableResources = linksForTable(dbName, tableName);

        return ResponseEntity
                .ok().body(Hateoas.formatResponse("Delete successful", tableResources));
    }

    public HashMap<String, Resource> linksForTable(String dbName, String tableName) {
        HashMap<String, Resource> tableResources = new HashMap();
        String tablesPath = "/db/" + dbName + "/table";

        tableResources.put("get table", new Resource(tablesPath, "get"));
        tableResources.put("get all tables", new Resource("/db/" + dbName, "get"));
        tableResources.put("table update", new Resource(tablesPath + "/" + tableName, "put"));
        tableResources.put("table add attr", new Resource(tablesPath + "/" + tableName + "/attr", "post"));
        tableResources.put("table delete attr", new Resource(tablesPath + "/" + tableName + "/attr", "delete"));
        return tableResources;
    }
}
