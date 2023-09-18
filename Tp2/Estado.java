package queue;

import java.util.ArrayList;

public abstract class Estado {
	
    public abstract boolean estaVacioONo(ArrayList<Object> lista);
    public abstract Object quitar(ArrayList<Object> lista);
    public abstract Object mostrar(ArrayList<Object> lista);
    public abstract int tamaño(ArrayList<Object> lista);
}