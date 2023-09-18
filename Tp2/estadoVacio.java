package queue;

import java.util.ArrayList;

public class estadoVacio extends Estado {
	
	public boolean estaVacioONo(ArrayList<Object> lista) {
		return true;
	}
	
    public Estado quitar(ArrayList<Object> lista) {
        throw new Error(queueIsEmpty);
    }

    public Estado mostrar(ArrayList<Object> lista) {
        throw new Error(queueIsEmpty);
    }
    
    public int tamaño(ArrayList<Object> lista) {
        return 0;
    }
    
	public static final String queueIsEmpty = "Queue is empty";
}