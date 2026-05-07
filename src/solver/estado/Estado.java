package solver.estado;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import estruturas_dados.grafos.Graph;
import solver.instance.Instance;

public class Estado {
    
    private List<Integer> caminho; // Lista de cidades na rota atual
    private double currentWeight = 0; // Peso acumulado
    private final Graph G = Instance.inst.GRAPH;
    
    public Estado() {
        caminho = new ArrayList<>();
        caminho.add(0); // Toda rota comeca na cidade 0
        currentWeight = 0;
    }
    
    public Estado(Estado pai) {
        this.caminho = new ArrayList<>(pai.caminho);
        this.currentWeight = pai.currentWeight;
    }
    
    public boolean contains(int v) { return caminho.contains(v); }
    
    public double getWeight() {
        return currentWeight;
    }
    
    public int getLastVertex() { return caminho.get(caminho.size() - 1); }
    
    public int getCaminhoSize() { return caminho.size(); }
    
    public void addVertex(int v) { 
        int last = getLastVertex();
        caminho.add(v);
        currentWeight += G.getWeight(last, v);
    }
    
    // Gera novos estados a partir da cidade atual para cidades nao visitadas
    public Iterable<Estado> getChildren(){
        Collection<Estado> children = new HashSet<>();
        for(int v : G.getAdjacentVertices(getLastVertex())) {
            if(contains(v)) continue;
            
            Estado child = new Estado(this);
            child.addVertex(v);
            
            // Se visitou todas as cidades, fecha o ciclo voltando a origem
            if(child.caminho.size() == G.getSize()) {
                child.addVertex(0);
            }
            children.add(child);
        }
        return children;
    }
    
    // Solucao completa quando tem todas as cidades e retornou a origem
    public boolean isSolution() { return caminho.size() == G.getSize() + 1; }
    
    @Override
    public String toString() {
        return "Custo: " + String.format("%.2f", getWeight()) + " | Rota: " + caminho;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Estado other = (Estado) obj;
        return Objects.equals(caminho, other.caminho);
    }

    @Override
    public int hashCode() { return Objects.hash(caminho); }

    // Usado pela Fila de Prioridade para ordenar pelo menor custo
    static public class EstadoWeightComparator implements java.util.Comparator<Estado>{
        @Override
        public int compare(Estado o1, Estado o2) {
            return Double.compare(o1.getWeight(), o2.getWeight());
        }
    }
}
