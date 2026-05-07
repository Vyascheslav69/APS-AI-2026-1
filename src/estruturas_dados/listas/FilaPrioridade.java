package estruturas_dados.listas;

import java.util.PriorityQueue;
import solver.estado.Estado;

public class FilaPrioridade implements EstadoAbertos {
	
	private PriorityQueue<Estado> elementos = new PriorityQueue<>(new Estado.EstadoWeightComparator());

	@Override
	public void push(Estado estado) {
		this.elementos.add(estado);
	}

	@Override
	public Estado pop() {
		return this.elementos.poll();
	}

	@Override
	public int size() {
		return this.elementos.size();
	}

}
