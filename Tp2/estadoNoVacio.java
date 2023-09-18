package queue;

import java.util.ArrayList;

class estadoNoVacio extends Estado {
	
	public boolean estaVacioONo(ArrayList<Object> lista) {
		return false;
	}

    public Object quitar(ArrayList<Object> lista) {
    	return lista.remove(primerElemento);
    }

	public Object mostrar(ArrayList<Object> lista) {
        return lista.get(primerElemento);
    }
	
	public int tamaño(ArrayList<Object> lista) {
		return lista.size();
	}
	
	private static final int primerElemento = 0;
}