package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.controller.dto.ReportKidsLocationGenderDTO;
import co.edu.umanizales.tads.exception.ListaSEException;
import lombok.Data;

@Data
public class ListSE {
    private Node head;
    private int size;
    /*
    Algoritmo de adicionar al final
    Entrada
        un niño
    si hay datos
    si
        llamo a un ayudante y le digo que se posicione en la cabeza
        mientras en el brazo exista algo
            pasese al siguiente
        va estar ubicado en el ùltimo

        meto al niño en un costal (nuevo costal)
        y le digo al ultimo que tome el nuevo costal
    no
        metemos el niño en el costal y ese costal es la cabeza
     */
    public void add(Kid kid) throws ListaSEException {
        if(head != null){
            Node temp = head;
            while(temp.getNext() !=null)
            {
                if(temp.getData().getIdentification().equals(kid.getIdentification())) {
                    throw new ListaSEException("Ya existe un niño con esa identificación");
                }
                temp = temp.getNext();
            }
            /// Parado en el último
            Node newNode = new Node(kid);
            temp.setNext(newNode);
        }
        else {
            head = new Node(kid);
        }
        size ++;
    }

    /* Adicionar al inicio
    si hay datos
    si
        meto al niño en un costal (nuevocostal)
        le digo a nuevo costal que tome con su brazo a la cabeza
        cabeza es igual a nuevo costal
    no
        meto el niño en un costal y lo asigno a la cabez
     */
    public void addToStart(Kid kid) throws ListaSEException{
        if(head !=null)
        {
            Node newNode = new Node(kid);
            newNode.setNext(head);
            head = newNode;
        } else if(this.head == null){
            head = new Node(kid);
        }
        else{
            throw new ListaSEException("La lista esta vacia");
        }
        size++;
    }
    public void addByPosition(Kid kid, int i) throws ListaSEException {
        int position = 0;
        if(position == 1)
        {
            addToStart(kid);
        }
        else
        {

            int posTemp=1;
            Node temp = head;
            while(posTemp < (position -1))
            {
                temp = temp.getNext();
                posTemp++;
            }
            //Estoy parado uno antes de donde debe quedar
            Node newNode = new Node(kid);
            newNode.setNext(temp.getNext());
            temp.setNext(newNode);
            size++;
        }
    }
    public void invert() throws ListaSEException{
        if(this.head !=null){
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while(temp != null){
                listCp.addToStart(temp.getData());
                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        } else {
            throw new ListaSEException("No hay elementos para invertir.");
        }
    }

    public void orderBoysToStart() throws ListaSEException {
        if(this.head !=null) {
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getGender() == 'M') {
                    listCp.addToStart(temp.getData());
                } else {
                    listCp.add(temp.getData());
                }

                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }else{
                    throw new ListaSEException("Esta lista esta vacia");
                }
            }



    public void changeExtremes() throws ListaSEException {
        if(this.head !=null && this.head.getNext() !=null)
        {
            Node temp = this.head;
            while(temp.getNext()!=null)
            {
                temp = temp.getNext();
            }
            //temp está en el último
            Kid copy = this.head.getData();
            this.head.setData(temp.getData());
            temp.setData(copy);
        }
     else {
        throw new ListaSEException("La lista esta vacia o no tiene los elementos suficientes para cambiar");
    }
}


    public int getCountKidsByLocationCode(String code){
        int count =0;
        if( this.head!=null){
            Node temp = this.head;
            while(temp != null){
                if(temp.getData().getLocation().getCode().equals(code)){
                    count++;
                }
                temp = temp.getNext();
            }
        }
        return count;
    }
    public void addToFinal (Kid kid) {
        Node newNode = new Node(kid);

        if (head == null) {
            head = newNode;
            return;
        }

        Node current = head;
        while (current.getNext() != null) {
            current = current.getNext();
        }

        current.setNext(newNode);
    }
    public void sendToTheEndByInitial(char initialletter) throws ListaSEException {
        ListSE initialbuttonlist = new ListSE();
        Node temp = this.head;
        boolean found = false;
        if (temp == null) throw new ListaSEException("No hay elementos que enviar al final");
        while (temp != null){
            if (temp.getData().getName().charAt(0) != Character.toLowerCase(initialletter)){
                initialbuttonlist.addToFinal(temp.getData());
            } else {
                found = true;
            }
            temp = temp.getNext();
        }
    if (!found) {
        throw new ListaSEException("No hay niños que empiecen por la letra " + initialletter);
    }
        temp = this.head;
        while (temp != null){
            if (temp.getData().getName().charAt(0) == Character.toLowerCase(initialletter)){
                initialbuttonlist.addToFinal(temp.getData());
            }
            temp = temp.getNext();
        }
        this.head = initialbuttonlist.getHead();
    }


    public int getCountKidsByDepartmentCode(String code) {
        int count =0;
        if( this.head!=null){
            Node temp = this.head;
            while(temp != null){
                if(temp.getData().getLocation().getCode().equals(code)){
                    count++;
                }
                temp = temp.getNext();
            }
        }
        return count;
    }
    public void removeByIdentificacion(String identification) throws ListaSEException {
        Node temp = head;
        Node prev = null;
        if (head == null) {
            return;
        }
        if (identification == head.getData().getIdentification()) {
            head = head.getNext();
            size--;
            return;
        }
        Node current = head;
        boolean found = false;
        while (current.getNext() != null) {
            if (current.getNext().getData().getIdentification() == identification) {
                found = true;
                size--;
                return;
            }
            current = current.getNext();
        }
        if (!found) {
            throw new ListaSEException("No se encontró un niño con la identificación proporcionada.");
        }
    }
    public int CountKidsByCityAndGender(String code, char gender, byte limiter){
        byte limit = limiter;
        int count = 0;
        if (this.head != null){
            Node temp = this.head;
            while (temp != null){
                if (temp.getData().getLocation().getCode().substring(0,8).equals(code)
                    && temp.getData().getGender() == gender
                    && temp.getData().getAge() > limit){
                    count++;
                }
                temp = temp.getNext();
            }
        }
        return count;
    }
    public void intercalateBoysandGirls() throws ListaSEException {
        ListSE listMale = new ListSE();
        ListSE listFemale = new ListSE();
        Node temp = this.head;
        if(temp==null) throw new ListaSEException("ERROR: La lista esta vacía");
        while (temp != null){
            if(temp.getData().getGender()=='M'){
                listMale.add(temp.getData());
            }
            if(temp.getData().getGender()=='F'){
                listFemale.add(temp.getData());
            }
            temp = temp.getNext();
        }
        // Verificar que hay al menos un niño de cada género
        if (listMale.equals(0) || listFemale.equals(0)) {
            throw new ListaSEException("La lista debe contener al menos un niño de cada género.");
        }
        /*A partir de las listas creadas vamos a generar una nueva lista donde vamos a ingresar
         * los kids de forma alternada*/
        ListSE sortedList = new ListSE();
        Node maleNode = listMale.getHead();
        Node femaleNode = listFemale.getHead();
        while (maleNode != null || femaleNode != null){
            if (maleNode != null){
                sortedList.add(maleNode.getData());
                maleNode = maleNode.getNext();
            }
            if (femaleNode != null){
                sortedList.add(femaleNode.getData());
                femaleNode = femaleNode.getNext();
            }
        }
        this.head = sortedList.getHead();
    }

    public void deleteByAge(int age) throws ListaSEException {
        if (head == null) {
            throw new ListaSEException("La lista está vacía");
        }
        Node temp = head;
        Node prev = null;
        boolean found = false;
        while (temp != null) {
            if (temp.getData().getAge() == age) {
                found = true;
                if (prev == null) {
                    head = temp.getNext();
                } else {
                    prev.setNext(temp.getNext());
                }
            }
            prev = temp;
            temp = temp.getNext();
        }
        if (!found) {
            throw new ListaSEException("No se encontró ningún niño con la edad especificada");
        }
    }
    public float averageAge() throws ListaSEException {
        if (head != null){
            Node temp = head;
            int contador = 0;
            int ages = 0;
            while(temp.getNext() != null) {
                contador++;
                ages = ages + temp.getData().getAge();
                temp = temp.getNext();
            }
            return (float) ages/contador;
        } else {
            throw new ListaSEException("La lista está vacía, no se puede calcular la edad promedio");
        }
    }

    public void forwardPositions(String identification, int positions) throws ListaSEException {
        if (head != null){
            if(positions<size){
                if(head.getData().getIdentification()==identification){
                    //Como es la cabeza, entonces no puede subir posiciones
                }
                else{
                    int count = 1;
                    Node temp = head;
                    while(temp.getNext() != null && temp.getNext().getData().getIdentification() != identification){
                        temp = temp.getNext();
                        count++;
                    }
                    if(temp.getNext() == null){
                        throw new ListaSEException("No se encontró un niño con la identificación dada");
                    }
                    Node temp2=new Node(temp.getNext().getData());
                    temp.setNext(temp.getNext().getNext());
                    if(positions >= count+1){
                        addToStart(temp2.getData());
                    }
                    else{
                        addByPosition(temp2.getData(), (count+1) - positions);
                    }
                }
            }
            else{
                return;
            }
        }
        else{
            throw new ListaSEException("La lista está vacía");
        }
    }

    public void afterwardsPositions(String identification, int positions) throws ListaSEException {
        if (head!=null){
            if(positions<size){
                if(head.getData().getIdentification()==identification){
                    Node node = new Node(head.getNext().getData());
                    addByPosition(node.getData(), positions+1);
                    head = head.getNext();
                }
                else{
                    int count = 1;
                    Node temp = head;
                    while(temp.getNext()!=null && temp.getNext().getData().getIdentification()!=identification){
                        temp = temp.getNext();
                        count++;
                    }
                    if (temp.getNext() == null) {
                        throw new ListaSEException("El niño con la identificación " + identification + " no se encuentra en la lista.");
                    }
                    Node temp2=new Node(temp.getNext().getData());
                    temp.setNext(temp.getNext().getNext());
                    addByPosition(temp2.getData(), count+1+positions);
                }
            }
            else{
                return;
            }
        }
    }
    public void getReportKidsByLocationGendersByAge(byte age, ReportKidsLocationGenderDTO report){
        if(head !=null){
            Node temp = this.head;
            while(temp!=null){
                if(temp.getData().getAge()>age){
                    report.updateQuantity(
                            temp.getData().getLocation().getName(),
                            temp.getData().getGender());
                }
                temp = temp.getNext();
            }
        }
    }

}

