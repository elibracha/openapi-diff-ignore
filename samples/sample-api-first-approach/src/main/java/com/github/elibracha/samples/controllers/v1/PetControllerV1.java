package com.github.elibracha.samples.controllers.v1;

import com.github.elibracha.samples.petstore.api.PetApi;
import com.github.elibracha.samples.petstore.model.ModelApiResponse;
import com.github.elibracha.samples.petstore.model.Pet;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class PetControllerV1 implements PetApi {

    @Override
    public ResponseEntity<Pet> addPet(@Valid Pet pet) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deletePet(Long petId, String apiKey) {
        return null;
    }

    @Override
    public ResponseEntity<List<List<Pet>>> findPetsByStatus(@Valid String status) {
        return null;
    }

    @Override
    public ResponseEntity<List<List<Pet>>> findPetsByTags(@Valid List<String> tags) {
        return null;
    }

    @Override
    public ResponseEntity<Pet> getPetById(Long petId) {
        return null;
    }

    @Override
    public ResponseEntity<Pet> updatePet(@Valid Pet pet) {
        return null;
    }

    @Override
    public ResponseEntity<Void> updatePetWithForm(Long petId, @Valid String name, @Valid String status) {
        return null;
    }

    @Override
    public ResponseEntity<ModelApiResponse> uploadFile(Long petId, @Valid String additionalMetadata, @Valid Resource body) {
        return null;
    }
}
