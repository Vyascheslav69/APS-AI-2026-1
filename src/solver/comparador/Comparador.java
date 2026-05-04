package solver.comparador;

import dao.DAO;
import estruturas_dados.listas.Fila;
import estruturas_dados.listas.Pilha;
import solver.busca.EspacoDeBusca;
import solver.estado.Estado;
import solver.heuristica.Heuristica;

public class Comparador {

    public static void main(String[] args) {
        DAO.readInstance("files/instances/test8.tsp/test8.tsp");

        // ==========================
        // 1. BUSCA DFS (PILHA)
        // ==========================
        long inicioDFS = System.currentTimeMillis();

        EspacoDeBusca dfs = new EspacoDeBusca(new Pilha());
        Estado solDFS = dfs.solve();

        long fimDFS = System.currentTimeMillis();

        // ==========================
        // 2. BFS (FILA)
        // ==========================
        long inicioBFS = System.currentTimeMillis();

        EspacoDeBusca bfs = new EspacoDeBusca(new Fila());
        Estado solBFS = bfs.solve();

        long fimBFS = System.currentTimeMillis();

        // ==========================
        // 3. HEURÍSTICA
        // ==========================
        long inicioH = System.currentTimeMillis();

        Heuristica h = new Heuristica();
        Estado solH = h.solve();

        long fimH = System.currentTimeMillis();

        // ==========================
        // RESUMO FINAL
        // ==========================
        System.out.println("\n==============================================");
        System.out.println("              RESUMO FINAL");
        System.out.println("==============================================");
        System.out.println(" DFS (Busca exaustiva):");
        System.out.println("   Custo: " + solDFS.getWeight());
        System.out.println("   Tempo: " + (fimDFS - inicioDFS) + " ms\n");

        System.out.println(" BFS (Busca em largura):");
        System.out.println("   Custo: " + solBFS.getWeight());
        System.out.println("   Tempo: " + (fimBFS - inicioBFS) + " ms\n");

        System.out.println(" Heurística (Vizinho mais próximo):");
        System.out.println("   Custo: " + solH.getWeight());
        System.out.println("   Tempo: " + (fimH - inicioH) + " ms");
        System.out.println("==============================================");
    }
}