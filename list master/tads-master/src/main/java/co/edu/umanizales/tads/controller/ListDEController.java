package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.model.LocationDE;
import co.edu.umanizales.tads.model.Pet;
import co.edu.umanizales.tads.controller.dto.*;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.service.ListDEService;
import co.edu.umanizales.tads.service.LocationDEService;
import co.edu.umanizales.tads.exception.ListaDEException;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.NoSuchElementException;


import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping(path = "/listde")
public class ListDEController {
    @Autowired
    private ListDEService listDEService;
    @Autowired
    private LocationDEService locationDEService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getPets() {
        return new ResponseEntity<>(new ResponseDTO(
                200, listDEService.getPets(), null), HttpStatus.OK);
    }
    @GetMapping(path = "/addpet")
    public ResponseEntity<ResponseDTO> addPet(@RequestBody @Valid Pet pet) {
        try {
            if (pet == null) {
                throw new ListaDEException("Tiene que ingresar los datos de la mascota");
            }
            listDEService.getPets().addPet(pet);
            return new ResponseEntity<>(new ResponseDTO(200, "la mascota ha sido añadida",null),HttpStatus.OK);

        } catch (ListaDEException e) {
            return new ResponseEntity<>(new ResponseDTO(500,"error al añadir la mascota"+e.getMessage(),null),HttpStatus.OK);
        }
    }

    @PostMapping(path = "/addpettostart")
    public ResponseEntity<ResponseDTO> addPetToStart(@RequestBody Pet pet) {
        listDEService.getPets().addPetToStart(pet);
        return new ResponseEntity<>(new ResponseDTO(200, "Mascota adicionada al inicio", null),
                HttpStatus.OK);

    }
    @PostMapping(path = "/addpetbyposition/{position}")
    public ResponseEntity<ResponseDTO> addPetByPosition(@RequestBody Pet pet, @PathVariable int position) {
        try {
            if (pet == null) {
                throw new ListaDEException("Debe ingresar los datos de la mascota");
            }
            if (position < 0) {
                throw new ListaDEException("La posición debe ser mayor o igual a cero");
            }
            if (position > listDEService.getPets().getSize()) {
                throw new ListaDEException("La posición no puede ser mayor al tamaño de la lista");
            }
            listDEService.getPets().addPetByPosition(pet, position);
            return new ResponseEntity<>(new ResponseDTO(200, "La mascota ha sido añadida en la posición " + position, null), HttpStatus.OK);

        } catch (ListaDEException e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Error al añadir la mascota: " + e.getMessage(), null), HttpStatus.OK);
        }
    }
    @DeleteMapping(path = "/deletebyid/{code}")
    public ResponseEntity<ResponseDTO> deleteById(@PathVariable String ID) {
        try {
            listDEService.getPets().deleteById(ID);
            return new ResponseEntity<>(new ResponseDTO(200, "La mascota ha sido eliminada", null), HttpStatus.OK);
        } catch (ListaDEException e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Error al eliminar la mascota: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(new ResponseDTO(404, "No existe una mascota con el código ingresado", null), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/invertpets")
    public ResponseEntity<ResponseDTO> invertPets() {
        try {
            listDEService.getPets().invertPets();
            return new ResponseEntity<>(new ResponseDTO(200, "Se han invertido las mascotas correctamente", null), HttpStatus.OK);
        } catch (ListaDEException e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Error al invertir las mascotas: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping (path = "/getmaletostart")
    public ResponseEntity<ResponseDTO> getMaleToStart() throws ListaDEException {
        try {
            listDEService.getPets().getMaleToStart();

        } catch (ListaDEException e) {
            return new ResponseEntity<>(new ResponseDTO(409, e.getMessage(), null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se organizo la lista niños primero despues niñas", null), HttpStatus.OK);
    }
    @GetMapping(path="/petsintercalate")
    public ResponseEntity<ResponseDTO> intercalatePetsByGender()  {
        try {
            listDEService.getPets().intercalatePetsByGender();
            return new ResponseEntity<>(new ResponseDTO(200, "Las mascotas se han intercalado.",
                    null), HttpStatus.OK);
        } catch (ListaDEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Ocurrió un error al intercalar el género de las mascotas",
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping (path="/deletepetsbyage/{age}")
    public ResponseEntity<ResponseDTO> deleteByPetAge(@PathVariable byte petage) throws ListaDEException {

        try {
            listDEService.getPets().deleteByPetAge(petage);

        } catch (ListaDEException e) {
            return new ResponseEntity<>(new ResponseDTO(409, e.getMessage(), null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se eliminaron las mascotas de "+petage+ " años",
                null), HttpStatus.OK);
    }

    @GetMapping("/averageage")
    public ResponseEntity<ResponseDTO> calculateAveragepetage() {
        try {
            float average = listDEService.getPets().calculateAveragepetage();
            return new ResponseEntity<>(new ResponseDTO(200, "El promedio de edad de las mascotas es: " + average, null), HttpStatus.OK);
        } catch (ListaDEException e) {
            return new ResponseEntity<>(new ResponseDTO(500, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/petsbylocations")
    public ResponseEntity<ResponseDTO> getPetsByLocationCode() {
        try {
            List<PetsByLocationDTO> petsByLocationDTOList = new ArrayList<>();
            for (LocationDE loc : locationDEService.getLocationsdepets()) {
                int count = listDEService.getPets().getCountPetsByLocationCode(loc.getCode());
                if (count > 0) {
                    petsByLocationDTOList.add(new PetsByLocationDTO(loc, count));
                }
            }
            return new ResponseEntity<>(new ResponseDTO(
                    200, petsByLocationDTOList,
                    null), HttpStatus.OK);
        } catch (ListaDEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Ocurrió un error al obtener las mascotas por ubicación.", null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/petsbydepartments")
    public ResponseEntity<ResponseDTO> getPetsByDepartmentCode() {
        List<PetsByLocationDTO> petsByLocationDTOList = new ArrayList<>();
        try {
            for (LocationDE loc : locationDEService.getLocationsdepets()) {
                int count = listDEService.getPets().getCountPetsByDepartmentCode(loc.getCode());
                if (count > 0) {
                    petsByLocationDTOList.add(new PetsByLocationDTO(loc, count));
                }
            }
            return new ResponseEntity<>(new ResponseDTO(
                    200, petsByLocationDTOList,
                    null), HttpStatus.OK);
        } catch (ListaDEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Error al obtener la lista de mascotas por departamento.",
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/advanceposition/{ID}/{move}")
    public ResponseEntity<ResponseDTO> forwardPosition(@PathVariable String ID, @PathVariable int move) throws ListaDEException {
        try {
            listDEService.getPets().forwardPosition(ID, move);

        } catch (ListaDEException e) {
            return new ResponseEntity<>(new ResponseDTO(409, e.getMessage(), null), HttpStatus.OK);
        }
        return new ResponseEntity<>(
                new ResponseDTO(200, "Se movio la mascota " + move + " posiciones adelante", null),
                HttpStatus.OK);
    }
    @GetMapping(path = "/lostposition/{ID}/{move}")
    public ResponseEntity<ResponseDTO> afterwardPosition(@PathVariable String ID, @PathVariable int move) throws ListaDEException {
        try {
            listDEService.getPets().afterwardPosition(ID, move);

        } catch (ListaDEException e) {
            return new ResponseEntity<>(new ResponseDTO(409, e.getMessage(), null), HttpStatus.OK);
        }
        return new ResponseEntity<>(
                new ResponseDTO(200, "la mascota peridio " + move + " posiciones", null),
                HttpStatus.OK);
    }
    //Implementar un método que me permita enviar al final de la lista a las mascotas que su nombre inicie con una letra dada
    @GetMapping(path="/sendbuttonbyinitial/{initial}")
    public ResponseEntity<ResponseDTO> sendButtomByInitial(@PathVariable char initial) {
        try {
            listDEService.getPets().sendButtomByInitial(Character.toUpperCase(initial));
            return new ResponseEntity<>(new ResponseDTO(200, "Las mascotas con esa letra se han enviado al final de la lista.", null), HttpStatus.OK);
        } catch (ListaDEException e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Ocurrió un error al enviar al final la lista de mascotas por la letra dada.",
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/ranges")
    public List<RangePetAgeDTO> getPETRanges()
    {
        List<RangePetAgeDTO> petranges = new ArrayList<>();
        petranges.add(new RangePetAgeDTO(1,4,0));
        petranges.add(new RangePetAgeDTO(5,9,0));
        petranges.add(new RangePetAgeDTO(10,14,6));
        return petranges;
    }
    @DeleteMapping("/deletekamikazebyid/{id}")
    public ResponseEntity<String> deleteKamikazeById(@PathVariable String ID) {
        try {
            listDEService.getPets().deleteKamikazeById(ID);
            return ResponseEntity.ok("Nodo eliminado correctamente.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró un nodo con el id proporcionado.");
        } catch (ListaDEException e) {
            throw new RuntimeException(e);
        }
    }

}
