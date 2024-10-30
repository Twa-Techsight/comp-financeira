package com.webservices.resouces;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.persistence.model.SysPayload;
import com.twa.financeira.dto.FinanceiraParametroDTO;
import com.twa.financeira.dto.FinanceiraRequestDTO;
import com.twa.financeira.dto.FinanceiraResponseDTO;
import com.twa.financeira.dto.FinanceiraUpdateDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;

public interface FinanceiraResource {

//@formatter:off
    
/* ------------------------------------------------------------------------------------------------------------------------------------*/        
    
    @Operation(summary = "Pegar financeira", responses = {
		@ApiResponse(responseCode = "200", description = "Pegar financeira", 
				content = @Content(mediaType = "application/json", 
				schemaProperties = {  @SchemaProperty(name = "object", 
					schema = @Schema(implementation = FinanceiraResponseDTO.class ))}
			))}, 
			parameters = {
				@Parameter(hidden = true,  name = "client", required = true, in = ParameterIn.HEADER), 
				@Parameter(hidden = true, name = "client_token", required = true, in = ParameterIn.HEADER),
				@Parameter(hidden = true, name = "authorization", required = true, in = ParameterIn.HEADER),
		})
    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SysPayload<FinanceiraResponseDTO>> get(
            @PathVariable(value = "id", required = true) Long id);

/* ------------------------------------------------------------------------------------------------------------------------------------*/
    
    	    @Operation(summary = "Salvar financeira")
    	    @Parameters({@Parameter(hidden = true, name = "authorization", required = true),
    	    @Parameter(hidden = true, name = "client", required = true),
            @Parameter(hidden = true, name = "client_token", required = true)} )
            @ApiResponses({@ApiResponse(description = "Salvar financeira", responseCode = "200"),
            @ApiResponse(description = "Parameter invalid", responseCode = "400")})
            @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SysPayload<FinanceiraResponseDTO>> save(
            @RequestBody(required = true) FinanceiraRequestDTO dto, HttpServletResponse response);

   
/* ------------------------------------------------------------------------------------------------------------------------------------*/
    
    	    @Operation(summary = "Atualizar financeira", responses = {
            @ApiResponse(responseCode = "200", description = "Atualizar financeira", content = @Content(mediaType = "application/json", schemaProperties = {  
            @SchemaProperty(name = "object", schema = @Schema(implementation = FinanceiraResponseDTO.class ))}))}, parameters = {
	    @Parameter(hidden = true,  name = "client", required = true, in = ParameterIn.HEADER), 
	    @Parameter(hidden = true, name = "client_token", required = true, in = ParameterIn.HEADER),
	    @Parameter(hidden = true, name = "authorization", required = true, in = ParameterIn.HEADER),})
            @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SysPayload<FinanceiraResponseDTO>> update(
            @PathVariable(value = "id", required = true) Long id,
            @RequestBody(required = true) FinanceiraUpdateDTO dto);

    
/* ------------------------------------------------------------------------------------------------------------------------------------*/
    
    @Operation(summary = "Deletar financeira", responses = {
		@ApiResponse(responseCode = "200", description = "Deletar financeira", 
				content = @Content(mediaType = "application/json", 
				schemaProperties = {  @SchemaProperty(name = "object", 
					schema = @Schema(implementation = FinanceiraResponseDTO.class ))}
			))}, 
			parameters = {
				@Parameter(hidden = true,  name = "client", required = true, in = ParameterIn.HEADER), 
				@Parameter(hidden = true, name = "client_token", required = true, in = ParameterIn.HEADER),
				@Parameter(hidden = true, name = "authorization", required = true, in = ParameterIn.HEADER),
		})
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<SysPayload<FinanceiraResponseDTO>> delete(
            @PathVariable(value = "id", required = true) Long id);
/* ------------------------------------------------------------------------------------------------------------------------------------*/
    
    
    @Operation(summary = "Listar financeiraes por parametros")
    @ApiResponses({@ApiResponse(description = "Encontrar financeiraes por parametros", responseCode ="200"),
    @ApiResponse(description = "Parameter invalid", responseCode ="400")})
    @Parameters({@Parameter(hidden = true, name = "authorization", required = true),
    @Parameter(hidden = true, name = "client", required = true),
    @Parameter(hidden = true, name = "client_token", required = true)})
    @PutMapping(value = "/{grupoEmpresaId}/{page}/{size}", produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<SysPayload<FinanceiraResponseDTO>> listByParam(
    @PathVariable(value = "grupoEmpresaId", required = true) Long grupoEmpresaId,
    @PathVariable(value = "page", required = true) Integer page,
    @PathVariable(value = "size", required = true) Integer size, 
    @RequestHeader("authorization") String authorization,
    @RequestBody(required = true) FinanceiraParametroDTO params);    
}

/* ------------------------------------------------------------------------------------------------------------------------------------*/

//@formatter:on