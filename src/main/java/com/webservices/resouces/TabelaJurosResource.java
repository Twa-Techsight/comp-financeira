package com.webservices.resouces;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.persistence.model.SysPayload;
import com.twa.financeira.dto.TabelaIdResponseDTO;
import com.twa.financeira.dto.TabelaJurosParametroDTO;
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


/* ------------------------------------------------------------------------------------------------------------------------------------*/


    	    @Operation(summary = "Deletar tabela juros", responses = {
		@ApiResponse(responseCode = "200", description = "Deletar tabela juros", 
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
public ResponseEntity<SysPayload<TabelaJurosViewResponseDTO>> delete(
		@PathVariable(value = "id", required = true) Long id);
/* ------------------------------------------------------------------------------------------------------------------------------------*/

    	    @Operation(summary = "Listar tabelaJuroses por parametros")
    	    @ApiResponses({@ApiResponse(description = "Encontrar tabelaJuros por parametros", responseCode ="200"),
    	    @ApiResponse(description = "Parameter invalid", responseCode ="400")})
            @Parameters({@Parameter(hidden = true, name = "authorization", required = true),
            @Parameter(hidden = true, name = "client", required = true),
            @Parameter(hidden = true, name = "client_token", required = true)})
            @PutMapping(value = "/coeficiente/{tabelaJurosId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SysPayload<TabelaJurosResponseDTO>> processaCoenficientes(
            @PathVariable(value = "tabelaJurosId", required = true) Long tabelaJurosId,
            @RequestBody(required = true) TabelaJurosParametroDTO params);
    	    
/* ------------------------------------------------------------------------------------------------------------------------------------*/
    	    
        @Operation(summary = "Pegar Id nova tabelaJuros", responses = {
    	@ApiResponse(responseCode = "200", description = "Pegar Id nova tabelaJuros",content = 
    	@Content(mediaType = "application/json",schemaProperties = {  
    	@SchemaProperty(name = "object",schema = 
    	@Schema(implementation = TabelaJurosResponseDTO.class ))}))},parameters = {
    	@Parameter(hidden = true,  name = "client", required = true, in = ParameterIn.HEADER), 
    	@Parameter(hidden = true, name = "client_token", required = true, in = ParameterIn.HEADER),
    	@Parameter(hidden = true, name = "authorization", required = true, in = ParameterIn.HEADER),})
        @GetMapping(value = "/tabelaid/{empresaId}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<SysPayload<TabelaIdResponseDTO>> getTabelaId(
            @PathVariable(value = "empresaId", required = true) Long empresaID);


    /* ------------------------------------------------------------------------------------------------------------------------------------*/
    	@Operation(summary = "Pegar tabelaJuros e seus itens", responses = {
	@ApiResponse(responseCode = "200", description = "Pegar tabelaJuros e seus itens",content = 
	@Content(mediaType = "application/json",schemaProperties = {  
	@SchemaProperty(name = "object",schema = 
	@Schema(implementation = TabelaJurosResponseDTO.class ))}))},parameters = {
	@Parameter(hidden = true,  name = "client", required = true, in = ParameterIn.HEADER), 
	@Parameter(hidden = true, name = "client_token", required = true, in = ParameterIn.HEADER),
	@Parameter(hidden = true, name = "authorization", required = true, in = ParameterIn.HEADER),})
    	@GetMapping(value = "/{empresaId}/{tabelaId}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SysPayload<TabelaJurosResponseDTO>> getByTabelaId(
        @PathVariable(value = "empresaId", required = true) Long empresaId,  
    	@PathVariable(value = "tabelaId", required = true) Long tabelaId);  
        

    /* ------------------------------------------------------------------------------------------------------------------------------------*/
//@formatter:on
}