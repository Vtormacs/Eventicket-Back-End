package com.Eventicket.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "category")
@Table(name = "category")
public class CategoryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @NotEmpty
    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ ]+$", message = "A categoria deve conter apenas letras e espaços.")
    @Column(unique = true)
    private String nome;

    @ManyToMany(mappedBy = "categories")
    @JsonIgnoreProperties("categories")
    private Set<EventEntity> events;
}
