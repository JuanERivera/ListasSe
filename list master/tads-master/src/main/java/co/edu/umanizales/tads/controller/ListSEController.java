package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.GenderDTO;
import co.edu.umanizales.tads.controller.dto.KidDTO;
import co.edu.umanizales.tads.controller.dto.KidsByLocationDTO;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.service.ListSEService;
import co.edu.umanizales.tads.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ResponseDTO> getKids(){
        return new ResponseEntity<>(new ResponseDTO(
                200,listSEService.getKids().getHead(),null), HttpStatus.OK);
    }
    @GetMapping("/addtostart")
    public ResponseEntity<ResponseDTO> addToStart(@RequestBody Kid kid){
        listSEService.addToStart(kid);
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se ha añadido al inicio",
                null), HttpStatus.OK);
    }
    @GetMapping("/invert")
    public ResponseEntity<ResponseDTO> invert(){
        listSEService.invert();
        return new ResponseEntity<>(new ResponseDTO(
                200,"SE ha invertido la lista",
                null), HttpStatus.OK);

    }
    @DeleteMapping
    @GetMapping(path = "/change_extremes")
    public ResponseEntity<ResponseDTO> changeExtremes() {
        listSEService.getKids().changeExtremes();
        return new ResponseEntity<>(new ResponseDTO(
                200,"SE han intercambiado los extremos",
                null), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addKid(@RequestBody KidDTO kidDTO){
        Location location = locationService.getLocationByCode(kidDTO.getCodeLocation());
       if(location == null){
           return new ResponseEntity<>(new ResponseDTO(
                   404,"La ubicación no existe",
                   null), HttpStatus.OK);
       }
       listSEService.getKids().add(
               new Kid(kidDTO.getIdentification(),
                       kidDTO.getName(), kidDTO.getAge(),
                       kidDTO.getGender(), location));
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se ha adicionado el niño",
                null), HttpStatus.OK);

    }
    @PostMapping (path = "/kids/{position}")
    public ResponseEntity<ResponseDTO> addByPosition(@PathVariable int position, @RequestBody Kid kid){
        listSEService.addByPosition(position, kid);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "El niño se movió de posición", null),HttpStatus.OK);
        }



    @GetMapping(path = "/kidsbylocations")
    public ResponseEntity<ResponseDTO> getKidsByLocation(){
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocations()){
            int count = listSEService.getKids().getCountKidsByLocationCode(loc.getCode());
            if(count>0){
                kidsByLocationDTOList.add(new KidsByLocationDTO(loc,count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,kidsByLocationDTOList,
                null), HttpStatus.OK);
    }
    @GetMapping(path = "/kidsbydepartments")
    public ResponseEntity<ResponseDTO> getKidsByDepartmentCode(){
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocations()){
            int count = listSEService.getKids().getCountKidsByDepartmentCode(loc.getCode());
            if(count>0){
                kidsByLocationDTOList.add(new KidsByLocationDTO(loc, count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, kidsByLocationDTOList,
                null), HttpStatus.OK);
    }
    @GetMapping(path = "/sendtotheendbyinitial/{initialletter}")
    public ResponseEntity<ResponseDTO> sendToTheEndByInitial(@PathVariable char initialletter){
        listSEService.getKids().sendToTheEndByInitial(Character.toLowerCase(initialletter));
        return new ResponseEntity<>(new ResponseDTO(200, "Niños enviados al final de la lista", null)
        , HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<ResponseDTO> addKidDTO(@RequestBody KidDTO kidDTO){
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
    @DeleteMapping("/removeByCode")
    public String removeByCode(@RequestParam String identification) {
        listSEService.removeByCode(identification);
        return "Elemento con código " + identification + " eliminado de la lista";
    }
    @GetMapping(path = "reportkids")
    public ResponseEntity<ResponseDTO> reportkids(@PathVariable byte age){
        List<GenderDTO> GenderDTOlist = new ArrayList<>();
        for (Location loc:locationService.getLocations()){
            if (loc.getCode().length()==8){
                String nameCity = loc.getName();
                List<GenderDTO> genderDTOList = new ArrayList<>();
                genderDTOList.add(new GenderDTO('m', listSEService.getKids().CountKidsByCityAndGender(loc.getCode(),
                        'm', age)));
                genderDTOList.add(new GenderDTO('f', listSEService.getKids().CountKidsByCityAndGender(loc.getCode(),
                        'f', age)));
                int total = genderDTOList.get(0).getQuantity()+genderDTOList.get(1).getQuantity();
                genderDTOList.add(new GenderDTO(nameCity, genderDTOList, total));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(200, GenderDTOlist, null ),HttpStatus.OK)
    }

}

