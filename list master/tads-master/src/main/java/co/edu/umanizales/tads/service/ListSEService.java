package co.edu.umanizales.tads.service;

import co.edu.umanizales.tads.exception.ListaSEException;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.ListSE;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class ListSEService {
    private ListSE kids;

    public ListSEService() {
        kids = new ListSE();

    }

    public void invert() throws ListaSEException {
        kids.invert();
    }


    public void addToStart(Kid kid) throws ListaSEException {
        kids.addToStart(kid);
    }
    public void addByPosition(int position, Kid kid) throws ListaSEException {
        int positions = 0;
        kids.addByPosition(kid, positions + 1);
    }
    public void removeByCode(String identification) throws ListaSEException {
        kids.removeByIdentificacion(identification);
    }
}