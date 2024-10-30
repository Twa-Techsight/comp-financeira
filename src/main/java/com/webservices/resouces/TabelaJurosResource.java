package com.webservices.resouces;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.persistence.model.SysPayload;
import com.twa.financeira.dto.TabelaJurosRequestDTO;
import com.twa.financeira.dto.TabelaJurosResponseDTO;
import com.twa.financeira.dto.TabelaJurosUpdateDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface TabelaJurosResource {

    //@formatter:off
    
/* ------------------------------------------------------------------------------------------------------------------------------------*/        
    
    @Operation(summary = "Listar tabelaJuross por empresae", responses = {
		@ApiResponse(responseCode = "200", description = "Encontrar tabelaJuroses por empresa", 
				content = @Content(mediaType = "application/json", 
				schemaProperties = {  @SchemaProperty(name = "object", 
					schema = @Schema(implementation = TabelaJurosResponseDTO.class ))}
			))}, 
			parameters = {
				@Parameter(hidden = true,  name = "client", required = true, in = ParameterIn.HEADER), 
				@Parameter(hidden = true, name = "client_token", required = true, in = ParameterIn.HEADER),
				@Parameter(hidden = true, name = "authorization", required = true, in = ParameterIn.HEADER),
		})
    @GetMapping(value = "/{idEmpresa}/{page}/{size}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SysPayload<TabelaJurosResponseDTO>> list(
            @PathVariable(value = "idEmpresa", required = true) Long idEmpresa,
            @PathVariable(value = "page", required = true) Integer page,
            @PathVariable(value = "size", required = true) Integer size);

/* ------------------------------------------------------------------------------------------------------------------------------------*/
    
    @Operation(summary = "Pegar tabelaJuros", responses = {
		@ApiResponse(responseCode = "200", description = "Pegar tabelaJuros", 
				content = @Content(mediaType = "application/json", 
				schemaProperties = {  @SchemaProperty(name = "object", 
					schema = @Schema(implementation = TabelaJurosResponseDTO.class ))}
			))}, 
			parameters = {
				@Parameter(hidden = true,  name = "client", required = true, in = ParameterIn.HEADER), 
				@Parameter(hidden = true, name = "client_token", required = true, in = ParameterIn.HEADER),
				@Parameter(hidden = true, name = "authorization", required = true, in = ParameterIn.HEADER),
		})
    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SysPayload<TabelaJurosResponseDTO>> get(
            @PathVariable(value = "id", required = true) Long id);

/* ------------------------------------------------------------------------------------------------------------------------------------*/
    
    @Operation(summary = "Salvar tabelaJuros", responses = {
		@ApiResponse(responseCode = "200", description = "Salvar tabelaJuros", 
				content = @Content(mediaType = "application/json", 
				schemaProperties = {  @SchemaProperty(name = "object", 
					schema = @Schema(implementation = TabelaJurosResponseDTO.class ))}
			))}, 
			parameters = {
				@Parameter(hidden = true,  name = "client", required = true, in = ParameterIn.HEADER), 
				@Parameter(hidden = true, name = "client_token", required = true, in = ParameterIn.HEADER),
				@Parameter(hidden = true, name = "authorization", required = true, in = ParameterIn.HEADER),
		})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SysPayload<TabelaJurosResponseDTO>> save(
            @RequestBody(required = true) TabelaJurosRequestDTO dto, HttpServletResponse response);

/* ------------------------------------------------------------------------------------------------------------------------------------*/
    
    @Operation(summary = "Atualizar tabelaJuros", responses = {
		@ApiResponse(responseCode = "200", description = "Atualizar tabelaJuros", 
				content = @Content(mediaType = "application/json", 
				schemaProperties = {  @SchemaProperty(name = "object", 
					schema = @Schema(implementation = TabelaJurosResponseDTO.class ))}
			))}, 
			parameters = {
				@Parameter(hidden = true,  name = "client", required = true, in = ParameterIn.HEADER), 
				@Parameter(hidden = true, name = "client_token", required = true, in = ParameterIn.HEADER),
				@Parameter(hidden = true, name = "authorization", required = true, in = ParameterIn.HEADER),
		})
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SysPayload<TabelaJurosResponseDTO>> edit(
            @PathVariable(value = "id", required = true) Long id,
            @RequestBody(required = true) TabelaJurosUpdateDTO dto);

/* ------------------------------------------------------------------------------------------------------------------------------------*/
    
    @Operation(summary = "Deletar tabelaJuros", responses = {
		@ApiResponse(responseCode = "200", description = "Deletar tabelaJuros", 
				content = @Content(mediaType = "application/json", 
				schemaProperties = {  @SchemaProperty(name = "object", 
					schema = @Schema(implementation = TabelaJurosResponseDTO.class ))}
			))}, 
			parameters = {
				@Parameter(hidden = true,  name = "client", required = true, in = ParameterIn.HEADER), 
				@Parameter(hidden = true, name = "client_token", required = true, in = ParameterIn.HEADER),
				@Parameter(hidden = true, name = "authorization", required = true, in = ParameterIn.HEADER),
		})
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<SysPayload<TabelaJurosResponseDTO>> delete(
            @PathVariable(value = "id", required = true) Long id);
/* ------------------------------------------------------------------------------------------------------------------------------------*/
    
    //@formatter:on
}
