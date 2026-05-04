package solver.estado;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import estruturas_dados.grafos.Graph;
import solver.instance.Instance;

public class Estado {

	List<Integer> caminho;
	private static final Graph G = Instance.inst.GRAPH;

	// Um Estado sempre comeca com um vertice
	// pq nao importa qual eh o primeiro vertice de uma solucao
	public Estado() {
		caminho = new ArrayList<>();
		caminho.add(0);
	}

	public Estado(List<Integer> aCaminho) {
		caminho = new ArrayList<>(aCaminho);
	}

	public boolean contains(int v) {
		return caminho.contains(v);
	}

	public double getWeight() {
		double weight = 0;
		for (int i = 1; i < caminho.size(); i++) {
			weight += G.getWeight(caminho.get(i - 1), caminho.get(i));
		}
		return weight;
	}

	public int getLastVertex() {
		return caminho.getLast();
	}

	public void addVertex(int v) {
		caminho.add(v);
	}

	public Iterable<Estado> getChildren() {
		Collection<Estado> children = new HashSet<>();
		for (Integer v : G.getAdjacentVertices(getLastVertex())) {
			if (contains(v))
				continue;
			Estado child = deepCopy();
			child.addVertex(v);
			child.checkCompleteSolution();
			children.add(child);
		}
		return children;
	}

	// faz ser solucao se falta apenas fechar o caminho
	private boolean checkCompleteSolution() {
		if (caminho.size() == G.getSize()) {
			caminho.add(caminho.getFirst());
			return true;
		}
		return false;
	}

	public boolean isSolution() {
		return caminho.size() == G.getSize() + 1;
	}

	@Override
	public int hashCode() {
		return Objects.hash(caminho);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estado other = (Estado) obj;
		return Objects.equals(caminho, other.caminho);
	}

	public Estado deepCopy() {
		return new Estado(caminho);
	}

	static public class EstadoWeightComparator implements Comparator<Estado> {

		@Override
		public int compare(Estado o1, Estado o2) {
			return Double.compare(o1.getWeight(), o2.getWeight());
		}

	}

	@Override
	public String toString() {
		return "Estado (" + getWeight() + ") [" + caminho + "]";
	}

}
