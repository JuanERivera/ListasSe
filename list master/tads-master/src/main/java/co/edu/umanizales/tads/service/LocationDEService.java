package co.edu.umanizales.tads.service;


import co.edu.umanizales.tads.model.LocationDE;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class LocationDEService {
    private List<LocationDE> locationsdepets;

    public LocationDEService() {
        //Conectar a una base de datos
        locationsdepets = new ArrayList<>();
        locationsdepets.add(new LocationDE("169", "Colombia"));
        locationsdepets.add(new LocationDE("16905", "Antioquia"));
        locationsdepets.add(new LocationDE("16917", "Caldas"));
        locationsdepets.add(new LocationDE("16963", "Risaralda"));
        locationsdepets.add(new LocationDE("16905001", "Medellín"));
        locationsdepets.add(new LocationDE("16963001", "Pereira"));
        locationsdepets.add(new LocationDE("16917001", "Manizales"));
        locationsdepets.add(new LocationDE("16917003", "Chinchiná"));

    }

    public List<LocationDE> getLocationsDEByCodeSize(int size) {
        List<LocationDE> listLocationsDE = new ArrayList<>();
        for (LocationDE loc : locationsdepets) {
            if (loc.getCode().length() == size) {
                listLocationsDE.add(loc);
            }
        }
        return listLocationsDE;
    }

    public LocationDE getLocationDEByCode(String code) {

        for (LocationDE loc : locationsdepets) {
            if (loc.getCode().equals(code)) {
                return loc;
            }
        }
        return null;
    }
}



