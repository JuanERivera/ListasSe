package co.edu.umanizales.tads.controller.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RangePetAgeDTO {
    private int Start;
    private int End;
    private int quantity;
}
