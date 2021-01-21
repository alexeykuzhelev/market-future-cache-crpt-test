package ru.crpt.controller;

import io.swagger.annotations.*;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import ru.crpt.model.Document;
import ru.crpt.model.ErrorResponse;
import ru.crpt.service.DocumentService;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Web-service")
@ApiResponses(value = {
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "Internal error", response = ErrorResponse.class)
})
@SwaggerDefinition(tags = {
        @Tag(name = "Web-service", description = "Веб-сервис")
})
@RestController
@Validated
@RequestMapping("/crpt")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @ApiOperation("Получение списка существующих документов")
    @GetMapping(path = "documents/{inCache}")
    public ResponseEntity<List<Document>> getDocuments(@PathVariable  Boolean inCache) {
        return ResponseEntity.ok(documentService.getAllDocuments(inCache));
    }

    @ApiOperation("Создание документа")
    @PostMapping(path = "create")
    public void createDocument(@RequestBody @Valid Document document) {
            documentService.createDocument(document);
    }

}
