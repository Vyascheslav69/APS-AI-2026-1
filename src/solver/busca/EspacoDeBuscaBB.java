package solver.busca;

import estruturas_dados.listas.EstadoAbertos;
import solver.estado.Estado;

public class EspacoDeBuscaBB {
    
    private final Estado estadoInicial = new Estado(); // Inicia na cidade 0
    private Estado bestSolucao = null; // Armazena a melhor rota encontrada
    private double bestCusto = Double.MAX_VALUE; // Custo infinito inicial
    private EstadoAbertos estadosAbertos = null; // Pilha para busca
    
    public EspacoDeBuscaBB(EstadoAbertos aEstadosAbertos) {
        this.estadosAbertos = aEstadosAbertos;
        estadosAbertos.push(estadoInicial);
    }
    
    // Busca otimizada que corta caminhos ja ineficientes
    public Estado solve() {
        while(!estadosAbertos.isEmpty()) {
            Estado estadoAtual = estadosAbertos.pop(); // Pega proximo estado
            
            // PODA: se o custo atual ja passou do melhor, ignora este caminho
            if (estadoAtual.getWeight() >= bestCusto) {
                continue;
            }

            // Se for solucao completa, atualiza o melhor custo
            if(estadoAtual.isSolution()) {
                if(estadoAtual.getWeight() < bestCusto) {
                    bestSolucao = estadoAtual;
                    bestCusto = estadoAtual.getWeight();
                }
                continue;
            }
            
            // Gera proximos passos a partir da cidade atual
            Iterable<Estado> filhos = estadoAtual.getChildren();
            for(Estado e : filhos) {
                estadosAbertos.push(e);
            }
        }
        return bestSolucao; // Retorna a melhor solucao otimizada
    }
}
