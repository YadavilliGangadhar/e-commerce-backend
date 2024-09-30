package com.gangadhar.e_commerce.controller;

import com.gangadhar.e_commerce.entity.Product;
import com.gangadhar.e_commerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Add a new product
    @PostMapping("/admin/add")
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    // Get all products
    @GetMapping("/user/getproducts")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // Get product by ID
    @GetMapping("/user/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }

    //upload image
    @PostMapping("/{id}/uploadImage")
    public ResponseEntity<String> uploadProductImage(@PathVariable Long id, @RequestParam("image") MultipartFile file) {
        try {
            // Define the location to save the file
            String folder = "uploads/";
            byte[] bytes = file.getBytes();
            Path path = Paths.get(folder + file.getOriginalFilename());
            Files.write(path, bytes);

            // Update the product image path in the database
            Product product = productService.getProductById(id);
            product.setImagePath(path.toString());
            productService.saveProduct(product);

            return new ResponseEntity<>("Image uploaded successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to upload image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //get image
    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        String imagePath = product.getImagePath();

        if (imagePath == null || !Files.exists(Paths.get(imagePath))) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            byte[] image = Files.readAllBytes(Paths.get(imagePath));
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Update product details
    @PutMapping("/admin/update/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product productDetails) {
        Product updatedProduct = productService.updateProduct(productId, productDetails);
        return ResponseEntity.ok(updatedProduct);
    }

    // Delete a product
    @DeleteMapping("/admin/delete/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
