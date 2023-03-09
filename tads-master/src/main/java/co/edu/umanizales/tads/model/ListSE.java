package co.edu.umanizales.tads.model;

import lombok.Data;

@Data
public class ListSE {
    private Node head;
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
    public int size() {
        int size = 0;
        Node temp = head;
        while (temp != null){
            size++;
            temp = temp.getNext();
        }
        return size;
    }
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
    }

    public void addInPosition(int position, Kid kid){
        if (size() >= position) {

            if (position == 0){
                addToStart(kid);
            }else {
                Node temp = head;
                for (int i = 0; i < position - 1; i++) {
                    temp = temp.getNext();
                }
            }
            } else {
                add(kid);
            }
        }

    public void deletekid(String identification) {
        Node temp = head;
        Node beforeNode = null;
        while (temp!= null && temp.getData().getIdentification()!=(identification)){
            beforeNode = temp;
            temp = temp.getNext();
        }
            if (temp != null){
                if (beforeNode == null){
                    head=temp.getNext();
                }
                else{
                    beforeNode.setNext(temp.getNext());
                }
            }


    }
}




