package com.pallab.blogapplication.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {


    private Integer categoryId;

    @NotEmpty
    @Size(min = 4, message = "title must have minimum of 4 characters")
    private String categoryTitle;

    @NotEmpty
    private String categoryDescription;
}
