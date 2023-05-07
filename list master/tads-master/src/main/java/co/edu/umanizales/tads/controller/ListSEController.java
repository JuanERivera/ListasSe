package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.*;
import co.edu.umanizales.tads.exception.ListaSEException;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.service.ListSEService;
import co.edu.umanizales.tads.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/listse")
public class ListSEController {
    @Autowired
    private ListSEService listSEService;
    @Autowired
    private LocationService locationService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getKids() {
        return new ResponseEntity<>(new ResponseDTO(
                200, listSEService.getKids().getHead(), null), HttpStatus.OK);
    }

    @GetMapping("/addtostart")
    public ResponseEntity<ResponseDTO> addToStart(@RequestBody Kid kid) throws ListaSEException {
        listSEService.addToStart(kid);
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha añadido al inicio",
                null), HttpStatus.OK);
    }

    @GetMapping("/invert")
    public ResponseEntity<ResponseDTO> invert() {
        try {
            listSEService.invert();
        } catch (ListaSEException e) {
            return new ResponseEntity<>(new ResponseDTO(409, e.getMessage(), null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "SE ha invertido la lista",
                null), HttpStatus.OK);

    }

    @DeleteMapping
    @GetMapping(path = "/change_extremes")
    public ResponseEntity<ResponseDTO> changeExtremes() {
        try {
            listSEService.getKids().changeExtremes();
            return new ResponseEntity<>(new ResponseDTO(
                    200, "SE han intercambiado los extremos",
                    null), HttpStatus.OK);
        } catch (ListaSEException e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Error al cambiar los extremos" + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Error al cambiar los extremos: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
    public ResponseEntity<ResponseDTO> addKid(@RequestBody @Valid KidDTO kidDTO) throws ListaSEException {
        Location location = locationService.getLocationByCode(kidDTO.getCodeLocation());
        if (location == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "La ubicación no existe",
                    null), HttpStatus.OK);
        }
        listSEService.getKids().add(
                new Kid(kidDTO.getIdentification(),
                        kidDTO.getName(), kidDTO.getAge(),
                        kidDTO.getGender(), location));
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha adicionado el niño",
                null), HttpStatus.OK);

    }

    @PostMapping(path = "/kids/{position}")
    public ResponseEntity<ResponseDTO> addByPosition(@PathVariable int position, @RequestBody Kid kid) {
        try {
            listSEService.addByPosition(position, kid);
        } catch (ListaSEException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "El niño se movió de posición", null), HttpStatus.OK);
    }


    @GetMapping(path = "/kidsbylocations")
    public ResponseEntity<ResponseDTO> getKidsByLocation() {
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        for (Location loc : locationService.getLocations()) {
            int count = listSEService.getKids().getCountKidsByLocationCode(loc.getCode());
            if (count > 0) {
                kidsByLocationDTOList.add(new KidsByLocationDTO(loc, count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, kidsByLocationDTOList,
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/kidsbydepartments")
    public ResponseEntity<ResponseDTO> getKidsByDepartmentCode() {
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        for (Location loc : locationService.getLocations()) {
            int count = listSEService.getKids().getCountKidsByDepartmentCode(loc.getCode());
            if (count > 0) {
                kidsByLocationDTOList.add(new KidsByLocationDTO(loc, count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, kidsByLocationDTOList,
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/sendtotheendbyinitial/{initialletter}")
    public ResponseEntity<ResponseDTO> sendToTheEndByInitial(@PathVariable char initialletter) throws ListaSEException {
        try {
            listSEService.getKids().sendToTheEndByInitial(Character.toLowerCase(initialletter));
        } catch (ListaSEException e) {

            return new ResponseEntity<>(new ResponseDTO(409, e.getMessage(), null)
                    , HttpStatus.OK);
        }
        listSEService.getKids().sendToTheEndByInitial(initialletter);
        return new ResponseEntity<>(
                new ResponseDTO(200, "Se envió al final los niños que su nombre inicia con la letra "
                        + initialletter, null),
                HttpStatus.OK);

    }


    @PostMapping
    public ResponseEntity<ResponseDTO> addKidDTO(@RequestBody KidDTO kidDTO) throws ListaSEException {
        Location location = locationService.getLocationByCode(kidDTO.getCodeLocation());
        if(location == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "La ubicación no existe ", null), HttpStatus.OK);
        }
        else if( location != null) {
            listSEService.getKids().add(new Kid(kidDTO.getIdentification(),
                    kidDTO.getName(), kidDTO.getAge(),
                    kidDTO.getGender(), location));
            return new ResponseEntity<>(new ResponseDTO(
                    200, "Se ha añadido", null), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new ResponseDTO(
                    409, "Ya existe un niño con ese código", null
            ), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping(path = "/removebyidentificacion/{identification}")
    public ResponseEntity<ResponseDTO> removeByIdentificacion(@PathVariable String identification) {
        try {
            listSEService.getKids().removeByIdentificacion(identification);
            return new ResponseEntity<>(new ResponseDTO(
                    200,"Se ha removido al niño con identificación: " + identification,
                    null), HttpStatus.OK);
        } catch (ListaSEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    400, "No se encontró un niño con la identificación proporcionada."
                    +e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping(path="/boystostart")
    public ResponseEntity<ResponseDTO> orderBoysToStart(){
        try {
            listSEService.getKids().orderBoysToStart();
        } catch (ListaSEException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(new ResponseDTO(200, "Los niños salen al inicio, las niñas al final",
                null), HttpStatus.OK);
    }

    @GetMapping(path="/intercalatebygender")
    public ResponseEntity<ResponseDTO> intercalatebygender() throws ListaSEException {
        try {
            listSEService.getKids().intercalateBoysandGirls();
        } catch (ListaSEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    409,e.getMessage(),
                    null), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResponseDTO(
                200,"Se intercalo la lista por genero",
                null), HttpStatus.OK);
    }


    @GetMapping(path = "/deletebyage/{age}")
    public ResponseEntity<ResponseDTO> deleteByAge(@PathVariable int age) {
        try {
            listSEService.getKids().deleteByAge(age);
            return new ResponseEntity<>(new ResponseDTO(200, "Los niños han sido eliminados", null), HttpStatus.OK);
        } catch (ListaSEException e) {
            return new ResponseEntity<>(new ResponseDTO(400, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(new ResponseDTO(404, e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }



    @GetMapping("/averageage")
    public ResponseEntity<ResponseDTO> averageAge() {
        float result;
        try {
            result = listSEService.getKids().averageAge();
        } catch (ListaSEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    400, "Error al calcular el promedio de edad: " + e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "El promedio de edad es: " + result, null), HttpStatus.OK);
    }


    @GetMapping(path = "/forwardpositions/{identification}/{positions}")
    public ResponseEntity<ResponseDTO> forwardPositions(@PathVariable String identification, @PathVariable int positions){
        try {
            listSEService.getKids().forwardPositions(identification, positions);
            return new ResponseEntity<>(new ResponseDTO(
                    200,"Posiciones avanzadas con éxito",
                    null), HttpStatus.OK);
        }
        catch (ListaSEException e){
            return new ResponseEntity<>(new ResponseDTO(
                    400,e.getMessage(),
                    null), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping(path = "/afterwardspostions/{identification}/{positions}")
    public ResponseEntity<ResponseDTO> afterwardsPositions(@PathVariable String identification, @PathVariable int positions){
        try{
            listSEService.getKids().afterwardsPositions(identification, positions);
            return new ResponseEntity<>(new ResponseDTO(200, "Las posiciones han sido actualizadas", null), HttpStatus.OK);
        } catch (ListaSEException e) {
            return new ResponseEntity<>(new ResponseDTO(400, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Ocurrió un error en el servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/ranges")
    public List<RangeDTO> getRanges()
    {
        List<RangeDTO> ranges = new ArrayList<>();
        ranges.add(new RangeDTO(1,4,0));
        ranges.add(new RangeDTO(5,9,0));
        ranges.add(new RangeDTO(10,14,6));
        return ranges;
    }

    @GetMapping(path = "/kidsbylocationgenders/{age}")
    public ResponseEntity<ResponseDTO> getReportKisLocationGenders(@PathVariable byte age) {
        ReportKidsLocationGenderDTO report =
                new ReportKidsLocationGenderDTO(locationService.getLocationsByCodeSize(8));
        listSEService.getKids()
                .getReportKidsByLocationGendersByAge(age,report);
        return new ResponseEntity<>(new ResponseDTO(
                200,report,
                null), HttpStatus.OK);
    }

}

