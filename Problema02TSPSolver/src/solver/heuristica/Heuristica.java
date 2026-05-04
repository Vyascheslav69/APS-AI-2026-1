package solver.heuristica;

import solver.estado.Estado;
import solver.instance.Instance;
import estruturas_dados.grafos.Graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

// HEURISTICA GREEDY - VIZINHO MAIS PROXIMO

public class Heuristica {

    private final Graph G = Instance.inst.GRAPH; // recebe o grafo

    public Estado solve() {

        Set<Integer> naoVisitados = new HashSet<>(); // set de cidades não visitadas
        for (int i = 0; i < G.getSize(); i++) { // adiciona todas as cidades ao set
            naoVisitados.add(i);
        }

        ArrayList<Integer> caminho = new ArrayList<>();

        // começa na cidade 0
        int atual = 0;
        caminho.add(atual);
        naoVisitados.remove(atual);

        // enquanto ainda tiver cidades
        while (!naoVisitados.isEmpty()) {

            int melhorCidade = -1;
            double menorDistancia = Double.MAX_VALUE;

            for (int prox : naoVisitados) { // percorre o set de cidades não visitadas
                double dist = G.getWeight(atual, prox); // calcula a distancia entre a cidade atual e a cidade não
                                                        // visitada

                if (dist < menorDistancia) {
                    menorDistancia = dist; // atualiza a menor distancia
                    melhorCidade = prox; // atualiza a cidade mais proxima
                }
            }

            caminho.add(melhorCidade); // adiciona a cidade mais proxima ao caminho
            naoVisitados.remove(melhorCidade); // remove a cidade mais proxima do set de cidades não visitadas
            atual = melhorCidade; // atualiza a cidade atual
        }

        // volta para o início
        caminho.add(0);

        return new Estado(caminho);
    }
}