package co.edu.umanizales.tads.model;

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
    public void add(Kid kid){
        if(head != null){
            Node temp = head;
            while(temp.getNext() !=null)
            {
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
    public void addToStart(Kid kid){
        if(head !=null)
        {
            Node newNode = new Node(kid);
            newNode.setNext(head);
            head = newNode;
        }
        else {
            head = new Node(kid);
        }
        size++;
    }
    public void addByPosition(Kid kid)
    {
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
    public void invert(){
        if(this.head !=null){
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while(temp != null){
                listCp.addToStart(temp.getData());
                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }
    }

    public void orderBoysToStart(){
        if(this.head !=null){
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while(temp != null){
                if(temp.getData().getGender()=='M')
                {
                    listCp.addToStart(temp.getData());
                }
                else{
                    listCp.add(temp.getData());
                }

                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }
    }

    public void changeExtremes(){
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
    public void sendToTheEndByInitial(char initialletter) {
        ListSE initialbuttonlist = new ListSE();
        Node temp = this.head;

        while (temp != null){
            if (temp.getData().getName().charAt(0) != Character.toLowerCase(initialletter)){
                initialbuttonlist.addToFinal(temp.getData());
            }
            temp = temp.getNext();
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
    public void removeByCode(String identification) {
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
        while (current.getNext() != null) {
            if (current.getNext().getData().getIdentification() == identification) {
                size--;
                return;
            }
            current = current.getNext();
        }
    }
}
