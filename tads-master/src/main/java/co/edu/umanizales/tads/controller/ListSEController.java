package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.service.ListSEService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping(path = "/listse")
public class ListSEController {
    @Autowired
    private ListSEService listSEService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getKids() {
        return new ResponseEntity<>(new ResponseDTO(
                200, listSEService.getKids(), null), HttpStatus.OK);
    }

    @PostMapping(path = "/addboys")
    public ResponseEntity<ResponseDTO> add(@RequestBody List<Kid> kids) {
        return new ResponseEntity<>(new ResponseDTO(200, "niño agregado", null), HttpStatus.OK);
    }

    @GetMapping(path = "/invert")
    public ResponseEntity<ResponseDTO> invert() {
        listSEService.invert();
        return new ResponseEntity<>(
                new ResponseDTO(200,"Lista Invertida", null),
                HttpStatus.OK);
    }

    @GetMapping(path = "/sendToTheEndByInitial/{initialletter}")
    public ResponseEntity<ResponseDTO> sendToTheEndByInitial(@PathVariable char initialletter){
        listSEService.sendToTheEndByInitial(Character.toLowerCase(initialletter));
        return new ResponseEntity<>(
                new ResponseDTO(200, "Esos niños han sido enviados al final", null),
        HttpStatus.OK);
    }
}
