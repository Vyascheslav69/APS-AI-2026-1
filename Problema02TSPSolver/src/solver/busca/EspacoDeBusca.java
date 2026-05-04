package solver.busca;

import estruturas_dados.listas.EstadoAbertos;
import solver.estado.Estado;

public class EspacoDeBusca {

	Estado estadoInicial = new Estado();
	Estado bestSolucao = null;
	double bestCusto = Double.MAX_VALUE;

	EstadoAbertos estadosAbertos = null;

	public EspacoDeBusca(EstadoAbertos aEstadosAbertos) {
		this.estadosAbertos = aEstadosAbertos;
		estadosAbertos.push(estadoInicial);
	}

	public Estado solve() {
		while (!estadosAbertos.isEmpty()) {
			Estado estadoAtual = estadosAbertos.pop();
			System.out.print(String.format("estadosAbertos: %10d", estadosAbertos.size()));
			System.out.println(" Testando " + estadoAtual);
			if (estadoAtual.isSolution()) {
				if (estadoAtual.getWeight() < bestCusto) {
					bestSolucao = estadoAtual;
					bestCusto = estadoAtual.getWeight();
					continue;
				}
			}
			Iterable<Estado> filhos = estadoAtual.getChildren();
			for (Estado e : filhos) {
				// System.out.println("Gerou " + e);
				estadosAbertos.push(e);
			}
		}
		return bestSolucao;
	}

}
