package co.edu.umanizales.tads.model;
import co.edu.umanizales.tads.exception.ListaDEException;
import lombok.Data;
@Data
public class ListDE {
    private NodeDE head;
    private NodeDE current;
    private NodeDE tail;
    private int size;

    public void addPet(Pet pet) throws ListaDEException {
        if (pet == null) {
            throw new ListaDEException("Pet object can not be null");
        }
        if (this.head != null) {
            NodeDE temp = this.head;
            while (temp.getNext() != null) {
                if (temp.getData().getID().equals(pet.getID())) {
                    throw new ListaDEException("La mascota con la identificación " + pet.getID() + " ya existe en esta lista");
                }
                temp = temp.getNext();
            }
            NodeDE newPet = new NodeDE(pet);
            temp.setNext(newPet);
            newPet.setPrevious(temp);
        } else {
            this.head = new NodeDE(pet);
        }
        size++;
    }

    public void addPetToStart(Pet pet) {
        NodeDE newNode = new NodeDE(pet);
        if (head != null) {
            head.setPrevious(newNode); // Establecer el enlace anterior de la cabeza al nuevo nodo
            newNode.setNext(head);
        }
        head = newNode;
        size++;
    }

    public void addPetByPosition(Pet pet, int position) throws ListaDEException {
        if (pet == null) {
            throw new ListaDEException("Pet no puede ser null");
        }
        if (position < 0 || position > size) {
            throw new ListaDEException("Invalid position: " + position);
        }

        NodeDE newNode = new NodeDE(pet);
        if (position == 0) {
            newNode.setNext(head);
            if (head != null) {
                head.setPrevious(newNode);
            }
            head = newNode;
        } else {
            NodeDE temp = head;
            for (int i = 0; i < position - 1; i++) {
                temp = temp.getNext();
            }
            newNode.setNext(temp.getNext());
            if (temp.getNext() != null) {
                temp.getNext().setPrevious(newNode);
            }
            temp.setNext(newNode);
            newNode.setPrevious(temp);
        }
        size++;
    }

    public void deleteById(String ID) throws ListaDEException {
        if (head == null) {
            throw new ListaDEException("ERROR: La lista está vacía");
        }

        boolean found = false;
        NodeDE current = head;

        while (current != null) {
            if (current.getData().getID().equals(ID)) {
                found = true;
                break;
            }
            current = current.getNext();
        }

        if (!found) {
            throw new ListaDEException("ERROR: No se encontró una mascota con el código ingresado");
        }

        // Verificar si el primer elemento coincide con el código a eliminar
        if (current == head) {
            // Eliminar primer elemento
            head = head.getNext();
            if (head != null) head.setPrevious(null);
        } else {
            // Eliminar elemento en otra posición
            NodeDE previous = current.getPrevious();
            previous.setNext(current.getNext());
            if (current.getNext() != null) {
                current.getNext().setPrevious(previous);
            }
        }

        size--;
    }

    public void invertPets() throws ListaDEException {
        if (this.head == null) {
            throw new ListaDEException("ERROR: La lista está vacía");
        }

        ListDE listCP = new ListDE();
        NodeDE temp = this.head;
        while (temp != null) {
            listCP.addPetToStart(temp.getData());
            temp = temp.getNext();
        }
        this.head = listCP.getHead();
    }

    public void getMaleToStart() throws ListaDEException {

        ListDE tempList = new ListDE();
        NodeDE temp = head;
        if (head == null) {
            throw new ListaDEException("ERROR: La lista está vacía.");
        }
        int count = 0;
        int countFamle = 0;
        while (temp != null) {
            if (temp.getData().getSex() == 'M') {
                tempList.addPetToStart(temp.getData());
                count++;
            } else {
                tempList.addPet(temp.getData());
                countFamle++;

            }
            temp = temp.getNext();

        }
        if (count == 0 || countFamle == 0) throw new ListaDEException("ERROR: solo hay mascotas de un solo sexo");

        head = tempList.getHead();


    }

    public void intercalatePetsByGender() throws ListaDEException {
        ListDE listPetMale = new ListDE();
        ListDE listPetFemale = new ListDE();
        NodeDE temp = this.head;

        if (temp == null) {
            throw new ListaDEException("La lista está vacía");
        }

        while (temp != null) {
            if (temp.getData().getSex() == 'M') {
                listPetMale.addPet(temp.getData());
            }
            if (temp.getData().getSex() == 'F') {
                listPetFemale.addPet(temp.getData());
            }
            temp = temp.getNext();
        }
        ListDE newListPetsFemale = new ListDE();
        NodeDE petMaleNode = listPetMale.getHead();
        NodeDE petFemaleNode = listPetFemale.getHead();
        while (petMaleNode != null || petFemaleNode != null) {
            if (petMaleNode != null) {
                newListPetsFemale.addPet(petMaleNode.getData());
                petMaleNode = petMaleNode.getNext();
            }
            if (petFemaleNode != null) {
                newListPetsFemale.addPet(petFemaleNode.getData());
                petFemaleNode = petFemaleNode.getNext();
            }
        }
        this.head = newListPetsFemale.getHead();
    }

    public void deleteByPetAge(byte petage) throws ListaDEException {
        ListDE tempList = new ListDE();
        ListDE tempList2 = new ListDE();

        NodeDE temp = head;
        if (temp == null) throw new ListaDEException("ERROR: La lista esta vacía");


        while (temp != null) {
            if (temp.getData().getPetage() != petage) {
                size = 1;
                tempList.addPet(temp.getData());
                size++;

            } else {
                tempList2.addPet(temp.getData());
            }


            temp = temp.getNext();

        }
        if (tempList2.head == null)
            throw new ListaDEException("ERROR: en la lista no hay ningúna mascota con esa edad ");

        head = tempList.getHead();

    }

    public float calculateAveragepetage() throws ListaDEException {
        if (head == null) {
            throw new ListaDEException("La lista está vacía");
        }

        NodeDE temp = head;
        int contador = 0;
        int ages = 0;

        while (temp != null) {
            contador++;
            ages = ages + temp.getData().getPetage();
            temp = temp.getNext();
        }

        return (float) ages / contador;
    }

    public int getCountPetsByLocationCode(String code) throws ListaDEException {
        int count = 0;
        if (this.head != null) {
            NodeDE temp = this.head;
            while (temp != null) {
                if (temp.getData().getLocationDE().getCode().equals(code)) {
                    count++;
                }
                temp = temp.getNext();
            }
            return count;
        } else {
            throw new ListaDEException("La lista está vacía");
        }
    }

    public int getCountPetsByDepartmentCode(String code) throws ListaDEException {
        int count = 0;
        if (this.head != null) {
            NodeDE temp = this.head;
            while (temp != null) {
                if (temp.getData().getLocationDE().getCode().equals(code)) {
                    count++;
                }
                temp = temp.getNext();
            }
            return count;
        } else {
            throw new ListaDEException("La lista está vacía");
        }
    }

    public void forwardPosition(String ID, int motion) throws ListaDEException {

        NodeDE temp = this.head;
        if (temp == null) throw new ListaDEException("ERROR: La lista esta vacía");
        if (motion > size || motion < 0)
            throw new ListaDEException("ERROR: EL movimiento " + motion + " no se puedo hacer");


        int count = 1;

        while (temp != null && !temp.getData().getID().equals(ID)) {

            temp = temp.getNext();
            count++;

        }

        if (temp != null) {
            int diferencia = count - motion;
            Pet petcopy = temp.getData();
            deleteById(temp.getData().getID());
            if (diferencia > 0) {
                addPetByPosition(petcopy, diferencia);
            } else {
                addPetToStart(petcopy);
            }
        }

    }

    public void afterwardPosition(String ID, int motion) throws ListaDEException {

        NodeDE temp = this.head;
        if (temp == null) throw new ListaDEException("ERROR: La lista esta vacía");
        if (motion > size || motion < 0)
            throw new ListaDEException("ERROR: EL movimiento " + motion + " no se puedo hacer");

        int count = 1;

        while (temp != null && !temp.getData().getID().equals(ID)) {

            temp = temp.getNext();
            count++;

        }
        int sum = motion + count;
        if (temp != null) {
            Pet petCopy = temp.getData();
            deleteById(temp.getData().getID());
            addPetByPosition(petCopy, sum);
        }

    }

    public void sendButtomByInitial(char initial) throws ListaDEException {

        if (this.head == null) {
            throw new ListaDEException("La lista está vacía");
        }

        ListDE listCP = new ListDE();
        NodeDE temp = this.head;

        while (temp != null) {
            if (temp.getData().getPetName().charAt(0) != Character.toUpperCase(initial)) {
                listCP.addPet(temp.getData());
            }
            temp = temp.getNext();
        }

        temp = this.head;

        while (temp != null) {
            if (temp.getData().getPetName().charAt(0) == Character.toUpperCase(initial)) {
                listCP.addPet(temp.getData());
            }
            temp = temp.getNext();
        }

        this.head = listCP.getHead();
    }

    /*
    metodo eliminar kamikaze
        primero verificamos si el primer dato esta vacio, si si lo esta, no se hace nada mas y tira la excepcion.
        segundo inicializamos un bucle mientras el nodo current no sea nulo y el ID no coincida con el nodo especificado.
            Si despues de recorrer toda la lista current es nulo significa que no encontramos ninguna ID que coincida
            y arrojamos la excepcion.
           Si current es igual a head, se actualiza head para que apunte al siguiente nodo en la lista. Si head no es nulo,
            se actualiza el puntero prev del nuevo nodo head para que apunte a null.
            Si current es igual a tail, se actualiza tail para que apunte al nodo anterior en la lista. Si tail no es nulo,
             se actualiza el puntero next del nuevo nodo tail para que apunte a null.
            Si current no es igual a head ni a tail, entonces el nodo a eliminar tiene nodos adyacentes.
            Se actualizan los punteros prev y next de los nodos adyacentes para que apunten el uno al otro, omitiendo el nodo que se va a eliminar.
            Se libera el nodo eliminado de los punteros prev y next, estableciendo ambos punteros en null.
       Se actualiza current para que apunte al siguiente nodo en la lista (el nodo que sigue al nodo eliminado).
       Si el nodo eliminado es el último nodo en la lista, entonces current apuntará a null.
     */
    public void deleteKamikazeById(String ID) throws ListaDEException {
        if (current == null) {
            throw new ListaDEException("La lista esta vacia");
        }

        while (current != null && current.getData().getID() != ID) {
            current = current.getNext();
        }

        if (current == null) {
            throw new ListaDEException("No se encontró una mascota con la identificación proporcionada.");
        }

        if (current == head) {
            head = current.getNext();
            if (head != null) {
                head.setPrevious(null);
            }
        } else if (current == tail) {
            tail = current.getPrevious();
            if (tail != null) {
                tail.setNext(null);
            }
        } else {
            current.getPrevious().setNext(current.getNext());
            current.getNext().setPrevious(current.getPrevious());
        }

        NodeDE temp = current;
        current = current.getNext();
        temp.setPrevious(null);
        temp.setNext(null);
    }
}

