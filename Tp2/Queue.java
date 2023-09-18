package queue;

import java.util.ArrayList;
import java.util.Arrays;

public class Queue {
	
    private ArrayList<Object> listaObjetos = new ArrayList<>();
    private ArrayList<Estado> memoriaDeEstados = new ArrayList<>(Arrays.asList(new estadoVacio()));
    
    public boolean isEmpty() {
        return estadoActual().estaVacioONo(listaObjetos);
    }

    public Queue add(Object cargo) {
        memoriaDeEstados.add( new estadoNoVacio() );
        listaObjetos.add(cargo);
        return this;
    }

    public Object take() {	
    	return memoriaDeEstados.remove( ultimoElemento() ).quitar(listaObjetos);
    }
	
    public Object head() {
        return estadoActual().mostrar(listaObjetos);
    }

    public int size() {
        return estadoActual().tamaño(listaObjetos);
    }

	private Estado estadoActual() {
		Estado estadoActual = memoriaDeEstados.get( ultimoElemento() );
		return estadoActual;
	}
	
    private int ultimoElemento() {
		return memoriaDeEstados.size()-1;
	}

}