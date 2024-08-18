package com.pallab.blogapplication.controller;


import com.pallab.blogapplication.payloads.ApiResponse;
import com.pallab.blogapplication.payloads.CategoryDto;
import com.pallab.blogapplication.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<CategoryDto> categoryDto(@Valid @RequestBody CategoryDto categoryDto){

        CategoryDto createdCategoryDto = this.categoryService.createCategory(categoryDto);

        return new ResponseEntity<>(createdCategoryDto, HttpStatus.CREATED);

    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto>updateCategory(@Valid  @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId){
        CategoryDto createdCategoryDto = this.categoryService.updateCategory(categoryDto,categoryId);

        return  ResponseEntity.ok(createdCategoryDto);
    }
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer categoryId){
        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(new ApiResponse("category deleted successfully", true),HttpStatus.OK);

    }


    @GetMapping("/{categoryId}")
    ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer categoryId){
        CategoryDto categoryDto = this.categoryService.getCategoryById(categoryId);

        return  ResponseEntity.ok(categoryDto);

    }
    @GetMapping("/")
    ResponseEntity<List<CategoryDto>> getCategory(){
        List<CategoryDto> categoryDto = this.categoryService.getAllCategories();

        return  ResponseEntity.ok(categoryDto);

    }


}
