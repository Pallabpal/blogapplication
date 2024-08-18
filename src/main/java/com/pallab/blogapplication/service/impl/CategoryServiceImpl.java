package com.pallab.blogapplication.service.impl;

import com.pallab.blogapplication.entities.Category;
import com.pallab.blogapplication.entities.User;
import com.pallab.blogapplication.exceptions.ResourceNotFoundException;
import com.pallab.blogapplication.payloads.CategoryDto;
import com.pallab.blogapplication.repositories.CategoryRepo;
import com.pallab.blogapplication.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CategoryDto createCategory(CategoryDto categoryDto){
        Category category =this.categoryRepo.save(dtoToCategory(categoryDto));

        return  categoryToDto(category);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId){

        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","Id",categoryId));
        category.setCategoryId(categoryDto.getCategoryId());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        category.setCategoryTitle(categoryDto.getCategoryTitle());

        Category updatedCategory=this.categoryRepo.save(category);
        return categoryToDto(updatedCategory);


    }

    @Override
    public void deleteCategory(Integer categoryId){
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","Id",categoryId));
        this.categoryRepo.delete(category);


    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId){

        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","Id",categoryId));

        return categoryToDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories(){
        List<Category> categories = this.categoryRepo.findAll();

        List<CategoryDto> categoriesDto = categories.stream().map(category -> this.categoryToDto(category)).collect(Collectors.toList());
        return categoriesDto;


    }

    public Category dtoToCategory(CategoryDto categoryDto){

        return this.modelMapper.map(categoryDto, Category.class);
    }

    public CategoryDto categoryToDto(Category category){

        return this.modelMapper.map(category, CategoryDto.class);
    }





}
