package com.glebushnik.company_service.controller;

import com.glebushnik.company_service.domain.DTO.CompanyClientDTO;
import com.glebushnik.company_service.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/company/")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping("/create")
    @Operation(
            summary = "Создать новую компанию.",
            description = "Создает новую компанию на основе переданных данных. Если данные невалидны или произошла ошибка, возвращает соответствующее сообщение.",
            tags = { "company", "create" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Компания успешно создана.", content = { @Content(schema = @Schema(implementation = CompanyClientDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Невалидные данные или ошибка при создании компании.", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<?> createCompany(@Valid @RequestBody CompanyClientDTO clientDTO) {
        try {
            return ResponseEntity.ok().body(companyService.createCompany(clientDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    @Operation(
            summary = "Получить список всех компаний.",
            description = "Возвращает список всех компаний. Если список пуст или произошла ошибка, возвращает соответствующее сообщение.",
            tags = { "company", "read" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список компаний успешно получен.", content = { @Content(schema = @Schema(implementation = List.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Ошибка при получении списка компаний.", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<?> getAllCompanies() {
        try {
            return ResponseEntity.ok().body(companyService.getAllCompanies());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{companyId}")
    @Operation(
            summary = "Получить компанию по ID.",
            description = "Возвращает компанию с указанным ID. Если компания не найдена или произошла ошибка, возвращает соответствующее сообщение.",
            tags = { "company", "read" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Компания успешно найдена.", content = { @Content(schema = @Schema(implementation = CompanyClientDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Компания не найдена или произошла ошибка.", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<?> getCompanyById(@PathVariable UUID companyId) {
        try {
            return ResponseEntity.ok().body(companyService.getCompanyById(companyId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{companyId}")
    @Operation(
            summary = "Обновить компанию по ID.",
            description = "Обновляет данные компании с указанным ID. Если компания не найдена или данные невалидны, возвращает соответствующее сообщение.",
            tags = { "company", "update" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Компания успешно обновлена.", content = { @Content(schema = @Schema(implementation = CompanyClientDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Невалидные данные, компания не найдена или произошла ошибка.", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<?> updateCompanyById(@PathVariable UUID companyId, @Valid @RequestBody CompanyClientDTO clientDTO) {
        try {
            return ResponseEntity.ok().body(companyService.updateCompanyById(clientDTO, companyId));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{companyId}")
    public ResponseEntity<?> deleteCompanyById(@PathVariable UUID companyId) {
        try {
            companyService.deleteCompanyById(companyId);
            return ResponseEntity.ok().body("Компания с id: " + companyId + " успешно удалена.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

