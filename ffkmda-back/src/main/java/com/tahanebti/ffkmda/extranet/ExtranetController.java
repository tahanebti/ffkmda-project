package com.tahanebti.ffkmda.extranet;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tahanebti.ffkmda.role.Role;
import com.tahanebti.ffkmda.specification.SpecificationsBuilder;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/structures")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ExtranetController {
	
	
	   private final ExtranetService extranetService;
		private final SpecificationsBuilder<Object> spec;

		
	   @GetMapping
	   public ResponseEntity<?> getStructurs(
			   @Parameter(description = "Elements souhaités suivis d'une virgule (!! sans espace !! ) -- instances,disciplines,lieux_de_pratique,derniere_affiliation") @RequestParam(required = false) String include,
			   @Parameter(description = "Retourne les informations des structures du type choisi -- CLU, DEP, LIG etc")  @RequestParam(required = false) String type_structure, 
			   @Parameter(description = "les informations des structures enfants du parent (Code) Exemple : N° de Département pour lister les clubs du département") @RequestParam(required = false) String parent_code,
			   @Parameter(description = "Filtre les structures en fonction du code postal") @RequestParam(required = false) String code_postal,
			   @Parameter(description = "Filtre les structures en fonction de la commune") @RequestParam(required = false) String commune,
			   @Parameter(description = "Filtre les structures en fonction de la latitude") @RequestParam(required = false) String latitude,
			   @Parameter(description = "Filtre les structures en fonction de la longitude") @RequestParam(required = false) String longitude,
			   @Parameter(description = "Ajoute un rayon de recherche sur le code_postal ou les coordonnes renseignés") @RequestParam(required = false) String rayon,
			   @Parameter(description = "Retourne les informations des structures possédant les disciplines. code souhaitées suivis d'une virgule (!! sans espace !! ) --") @RequestParam(required = false) String discipines_code,
			   @Parameter(description = "Retourne les informations des structures possédant la discipline (Code)") @RequestParam(required = false) String discipline,
			   @Parameter(description = "Retourne clubs affiliés sur la saison ou saison N-1 suivant règle de la Fédération (0 ou 1)") @RequestParam(required = false) String affilie,
				@RequestParam(required = false, defaultValue = "1") Integer _offset,
				@RequestParam(required = false, defaultValue = "10") Integer _limit,
				@RequestParam(required = false, defaultValue = "id") String _sort,
				@RequestParam(required = false, defaultValue = "asc") String direction
			   ) throws ClientProtocolException, IOException {
		   return ResponseEntity.ok(extranetService.fetchStructures(
				   include,
				   type_structure,
				   parent_code,
				   code_postal,
				   commune,
		    		latitude,
		    		longitude,
		    		rayon,
		    		discipines_code,
		    		discipline,
		    		affilie,

				   _limit, _offset, _sort, direction));
	   }
	   
	   @GetMapping(value ="/token")
	   public ResponseEntity<?> gettoken() throws ClientProtocolException, IOException {
		   return ResponseEntity.ok(extranetService.getAccessTokenFromExtranetFfkmda());
	   }
	   
	   @GetMapping(value = "{code}")
	   public ResponseEntity<?> getStructurs(@PathVariable String code) throws IOException{
		   return ResponseEntity.ok(extranetService.fetchStructureCode(code));
	   }

}
