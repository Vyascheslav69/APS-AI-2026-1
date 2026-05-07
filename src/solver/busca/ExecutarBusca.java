package solver.busca;

import dao.DAO;
import estruturas_dados.listas.Pilha;
import solver.estado.Estado;
import solver.instance.Instance;

public class ExecutarBusca {
    
    // Configura o algoritmo (1=Exaustiva, 2=BB, 3=Gulosa) e o arquivo
    private static final int TIPO_BUSCA = 2; 
    private static final String ARQUIVO_TSP = "instancias/brazil58.tsp";

    public static void main(String[] args) {
        DAO.readInstance(ARQUIVO_TSP); // Le o arquivo e monta o grafo
        
        if (Instance.inst.GRAPH == null) {
            System.err.println("Erro: Não foi possível carregar o grafo da instância: " + ARQUIVO_TSP);
            return;
        }
        
        System.out.println("Cidades: " + Instance.inst.GRAPH.getSize());
        
        Estado sol = null;
        long inicio = System.currentTimeMillis(); // Inicia cronometro

        // Executa o algoritmo escolhido na configuracao
        switch (TIPO_BUSCA) {
            case 1: sol = new EspacoDeBusca(new Pilha()).solve(); break;
            case 2: sol = new EspacoDeBuscaBB(new Pilha()).solve(); break;
            case 3: sol = new BuscaGulosa().solve(); break;
        }

        long fim = System.currentTimeMillis(); // Para cronometro
        
        // Exibe custo, rota e tempo total de execucao
        if (sol != null) {
            System.out.println("Custo: " + sol.getWeight());
            System.out.println("Rota: " + sol);
            System.out.println("Tempo: " + (fim - inicio) + " ms");
        }
    }
}
