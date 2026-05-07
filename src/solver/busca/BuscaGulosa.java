package solver.busca;

import estruturas_dados.grafos.Graph;
import solver.estado.Estado;
import solver.instance.Instance;

public class BuscaGulosa {
    private final Graph G;

    public BuscaGulosa() {
        this.G = Instance.inst.GRAPH; // Carrega o grafo da instancia atual
    }

    // Resolve escolhendo sempre o vizinho mais proximo disponivel
    public Estado solve() {
        Estado estadoAtual = new Estado(); // Comeca na cidade 0
        
        // Repete ate que todas as cidades sejam visitadas
        while (!estadoAtual.isSolution()) {
            int last = estadoAtual.getLastVertex(); // Cidade atual
            int melhorVizinho = -1;
            double menorDistancia = Double.MAX_VALUE;
            
            // Percorre vizinhos para achar o de menor custo imediato
            for (int v : G.getAdjacentVertices(last)) {
                if (!estadoAtual.contains(v)) {
                    double d = G.getWeight(last, v);
                    if (d < menorDistancia) {
                        menorDistancia = d;
                        melhorVizinho = v;
                    }
                }
            }
            
            // Se achou vizinho, move para ele; senao encerra
            if (melhorVizinho != -1) {
                estadoAtual.addVertex(melhorVizinho);
                // Se visitou todas, adiciona volta para a cidade 0
                if(estadoAtual.getCaminhoSize() == G.getSize()) {
                    estadoAtual.addVertex(0); 
                }
            } else {
                break;
            }
        }
        return estadoAtual; // Retorna rota aproximada rapida
    }
}
