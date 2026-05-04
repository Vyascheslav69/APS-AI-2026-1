package estruturas_dados.listas;

import solver.estado.Estado;

public interface EstadoAbertos {
	
	public void push(Estado estado);
	public Estado pop();
	public int size();
	default public boolean isEmpty() {return size()==0;}

}
