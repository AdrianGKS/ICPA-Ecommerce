package com.api.ICPAEcommerce.controllers;

import com.api.ICPAEcommerce.domain.image.ImageDTO;
import com.api.ICPAEcommerce.repositories.ImageRepository;
import com.api.ICPAEcommerce.repositories.ProductRepository;
import com.api.ICPAEcommerce.services.ImageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/products")
@Validated
@SecurityRequirement(name = "bearer-key")
public class ImageController {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageService service;

    @PostMapping("/{id}/create-image")
    @ResponseBody
    public ResponseEntity createImage(@RequestBody @Valid ImageDTO imageDTO, @PathVariable Long id) {

        return ResponseEntity.ok(service.saveImage(imageDTO, id));
    }

    @GetMapping("/{productId}/list-images")
    public ResponseEntity listImagesById(@PathVariable Long productId) {
        var findProduct = productRepository.findById(productId);

        if (findProduct.isPresent()) {
            var listImage = imageRepository.findByProductId(productId).stream().map(ImageDTO::new).toList();
            return ResponseEntity.ok(listImage);
        }

        return ResponseEntity.ok("Produto não encontrado");
    }

    @PutMapping("/update-image/{id}")
    @Transactional
    public ResponseEntity updateImage(@RequestBody @Valid ImageDTO imageDTO, @PathVariable Long id) {
        var image = imageRepository.getReferenceById(id);

        image.update(imageDTO);
        imageRepository.save(image);

        return ResponseEntity.ok(new ImageDTO(image));
    }

    @DeleteMapping("/delete-image/{id}")
    public ResponseEntity deleteImage(@PathVariable Long id) {
        service.deleteImage(id);

        return ResponseEntity.ok("Imagem excluída com sucesso!");
    }

}
