package com.api.ICPAEcommerce.services;

import com.api.ICPAEcommerce.domain.models.Product;
import com.api.ICPAEcommerce.domain.models.dtos.ImageDTO;
import com.api.ICPAEcommerce.domain.models.Image;
import com.api.ICPAEcommerce.repositories.ImageRepository;
import com.api.ICPAEcommerce.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ImageService {

    private ImageRepository imageRepository;
    private ProductRepository productRepository;

    @Transactional
    public ImageDTO saveImage(ImageDTO imageDTO, Long id) {
        Product product = productRepository.findById(id).orElseThrow(RuntimeException::new);
        Image image = new Image(imageDTO);
        image.setProduct(product);
        imageRepository.save(image);

        return new ImageDTO(image);
    }

    @Transactional
    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }

}
