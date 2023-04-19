package co.edu.umanizales.tads.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@AllArgsConstructor
public class Pet {
    private String code;
    private String name;
    private String tipo;
}
