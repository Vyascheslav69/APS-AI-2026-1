package solver.busca;

import dao.DAO;
import estruturas_dados.listas.Pilha;
import solver.estado.Estado;
import solver.instance.Instance;

public class TrabalhoAPS {
    public static void main(String[] args) {
        String path = "instancias/test8.tsp";
        
        DAO.readInstance(path);
        System.out.println("APS - TSP Solver Comparativo");
        System.out.println("Instância: " + Instance.inst.NAME + " (" + Instance.inst.GRAPH.getSize() + " cidades)");
        System.out.println("---------------------------------");

        // 1. Exaustiva
        System.out.println("\n[1] Executando Busca Exaustiva...");
        long i1 = System.currentTimeMillis();
        Estado s1 = new EspacoDeBusca(new Pilha()).solve();
        long f1 = System.currentTimeMillis();
        printResult(s1, f1 - i1);

        // 2. Branch and Bound
        System.out.println("\n[2] Executando Branch and Bound...");
        long i2 = System.currentTimeMillis();
        Estado s2 = new EspacoDeBuscaBB(new Pilha()).solve();
        long f2 = System.currentTimeMillis();
        printResult(s2, f2 - i2);

        // 3. Heurística
        System.out.println("\n[3] Executando Heurística Gulosa...");
        long i3 = System.currentTimeMillis();
        Estado s3 = new BuscaGulosa().solve();
        long f3 = System.currentTimeMillis();
        printResult(s3, f3 - i3);
    }

    private static void printResult(Estado sol, long tempo) {
        if (sol != null) {
            System.out.println("Custo: " + sol.getWeight());
            System.out.println("Rota: " + sol);
        }
        System.out.println("Tempo: " + tempo + " ms");
    }
}
