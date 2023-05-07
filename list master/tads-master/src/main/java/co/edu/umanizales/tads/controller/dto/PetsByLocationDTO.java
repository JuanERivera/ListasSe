package co.edu.umanizales.tads.controller.dto;

import co.edu.umanizales.tads.model.LocationDE;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PetsByLocationDTO {
    private LocationDE locationPets;
    private int quantity;
}