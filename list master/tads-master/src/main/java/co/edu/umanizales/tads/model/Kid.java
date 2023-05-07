package co.edu.umanizales.tads.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import javax.validation.constraints.*;
@Data
@AllArgsConstructor
public class Kid {
    @NotNull
    @NotBlank(message = "Este campo no puede ir vacio")
    private String identification;
    @NotNull
    @NotBlank(message = "Este campo no puede ir vacio.")
    @Size(min=2, max = 30, message = "El nombre solo puede tener un maximo de 30 caracteres")
    private String name;
    @NotNull(message = "Este campo no puede ir vacio y tiene que ser positivo.")
    @Positive
    @Min(value = 1, message = "El niño debe ser mayor a 0")
    @Max(value = 15, message = "La edad máxima debe ser menor o igual a 15.")
    private byte age;
    @NotNull
    @NotBlank(message = "Este campo no puede ir vacio")
    private char gender;
    @NotNull
    @NotBlank(message = "Este campo no puede ir vacio")
    private Location location;
}
