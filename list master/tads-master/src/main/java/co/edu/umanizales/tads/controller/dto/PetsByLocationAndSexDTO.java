package co.edu.umanizales.tads.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor

public class PetsByLocationAndSexDTO {
    private int total;
    private String city;
    private List<PetSexDTO> sex;

 
}
