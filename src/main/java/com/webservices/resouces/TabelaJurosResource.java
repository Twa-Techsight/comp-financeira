package com.webservices.resouces;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.persistence.model.SysPayload;
import com.twa.financeira.dto.TabelaJurosParametroDTO;
import com.twa.financeira.dto.TabelaJurosRequestDTO;
import com.twa.financeira.dto.TabelaJurosResponseDTO;
import com.twa.financeira.dto.TabelaJurosUpdateDTO;
import com.twa.financeira.dto.TabelaJurosViewResponseDTO;

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

public interface TabelaJurosResource {

//@formatter:off
    
/* ------------------------------------------------------------------------------------------------------------------------------------*/        
    
    	@Operation(summary = "Pegar tabelaJuros", responses = {
	@ApiResponse(responseCode = "200", description = "Pegar tabelaJuros",content = 
	@Content(mediaType = "application/json",schemaProperties = {  
	@SchemaProperty(name = "object",schema = 
	@Schema(implementation = TabelaJurosResponseDTO.class ))}))},parameters = {
	@Parameter(hidden = true,  name = "client", required = true, in = ParameterIn.HEADER), 
	@Parameter(hidden = true, name = "client_token", required = true, in = ParameterIn.HEADER),
	@Parameter(hidden = true, name = "authorization", required = true, in = ParameterIn.HEADER),})
    	@GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SysPayload<TabelaJurosResponseDTO>> get(
        @PathVariable(value = "id", required = true) Long id);

/* ------------------------------------------------------------------------------------------------------------------------------------*/
    
    	    @Operation(summary = "Salvar tabelaJuros")
    	    @Parameters({@Parameter(hidden = true, name = "authorization", required = true),
    	    @Parameter(hidden = true, name = "client", required = true),
            @Parameter(hidden = true, name = "client_token", required = true)} )
            @ApiResponses({@ApiResponse(description = "Salvar tabelaJuros", responseCode = "200"),
            @ApiResponse(description = "Parameter invalid", responseCode = "400")})
            @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SysPayload<TabelaJurosResponseDTO>> save(
            @RequestBody(required = true) TabelaJurosRequestDTO dto, HttpServletResponse response);

   
/* ------------------------------------------------------------------------------------------------------------------------------------*/
    
    	    @Operation(summary = "Atualizar tabelaJuros", responses = {
            @ApiResponse(responseCode = "200", description = "Atualizar tabelaJuros", content = @Content(mediaType = "application/json", schemaProperties = {  
            @SchemaProperty(name = "object", schema = @Schema(implementation = TabelaJurosResponseDTO.class ))}))}, parameters = {
	    @Parameter(hidden = true,  name = "client", required = true, in = ParameterIn.HEADER), 
	    @Parameter(hidden = true, name = "client_token", required = true, in = ParameterIn.HEADER),
	    @Parameter(hidden = true, name = "authorization", required = true, in = ParameterIn.HEADER),})
            @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SysPayload<TabelaJurosResponseDTO>> update(
            @PathVariable(value = "id", required = true) Long id,
            @RequestBody(required = true) TabelaJurosUpdateDTO dto);

    
/* ------------------------------------------------------------------------------------------------------------------------------------*/
    
    	    @Operation(summary = "Listar tabelaJuroses por parametros")
    	    @ApiResponses({@ApiResponse(description = "Encontrar tabelaJuros por parametros", responseCode ="200"),
    	    @ApiResponse(description = "Parameter invalid", responseCode ="400")})
            @Parameters({@Parameter(hidden = true, name = "authorization", required = true),
            @Parameter(hidden = true, name = "client", required = true),
            @Parameter(hidden = true, name = "client_token", required = true)})
            @PutMapping(value = "/{empresaId}/{page}/{size}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SysPayload<TabelaJurosViewResponseDTO>> listByParam(
            @PathVariable(value = "empresaId", required = true) Long empresaId,
            @PathVariable(value = "page", required = true) Integer page,
            @PathVariable(value = "size", required = true) Integer size, 
            @RequestHeader("authorization") String authorization,
            @RequestBody(required = true) TabelaJurosParametroDTO params);

}

/* ------------------------------------------------------------------------------------------------------------------------------------*/

//@formatter:on