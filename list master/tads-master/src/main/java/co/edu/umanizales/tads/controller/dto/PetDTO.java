package co.edu.umanizales.tads.controller.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class PetDTO {
    @NotNull
    @NotBlank(message = "Este campo no puede ir vacio")
    private String ID;
    @NotNull
    @NotBlank(message = "Este campo no puede ir vacio.")
    @Size(min=2, max = 30, message = "El nombre solo puede tener un maximo de 30 caracteres")
    private String petName;
    private String type;
    @NotNull
    @NotBlank(message = "Este campo no puede ir vacio")
    private char sex;
    @Positive
    @Min(value = 1, message = "La mascota debe ser mayor a 0")
    @Max(value = 15, message = "La edad máxima debe ser menor o igual a 15.")
    private byte petage;
    @NotNull
    @NotBlank(message = "Este campo no puede ir vacio")
    private String codelocationde;
}
