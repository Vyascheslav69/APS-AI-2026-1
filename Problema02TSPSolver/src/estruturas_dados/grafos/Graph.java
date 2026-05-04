package estruturas_dados.grafos;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.IntStream;

public class Graph {
	
	private int size;
	private double weights[][];
	private Map<Integer, Coord> coordenadas = new TreeMap<>();
	
	public Graph(int aSize) {
		this.size = aSize;
		weights = new double[size][size];
	}
	
	public int getSize() {return size;}
	
	public void setVertexCoord(int label, double x, double y) {
		coordenadas.put(label, new Coord(x, y));
	}
	
	public void setWeight(int u, int v, double weight) {
		weights[u][v] = weight;
		weights[v][u] = weight;
	}
	
	public int[] getVertices(){
		return IntStream.range(0, size).toArray();
	}
	
	public int[] getAdjacentVertices(int v){
		return IntStream.range(0, size).filter(c -> c!=v).toArray();
	}
	
	public double getWeight(int u, int v) {
		return weights[u][v];
	}
	
	public Coord getCoord(int v) {
		return coordenadas.get(v);
	}
	
	public void calcEuclidianFromCoords() {
		for(int i : getVertices())
			for(int j: getVertices())
				setWeight(i, j, Coord.dist(getCoord(i), getCoord(j)));
	}
	
	@Override
	public String toString() {
		String res = "";
		res += "Size: " + size + "\n";
		res += "Weights\n";
		for(double[] linha: weights) {
			for(double w : linha) {
				res += String.format("%10.2f ", w);
			}
			res += "\n";
		}
		for(Entry<Integer, Coord> pair:  coordenadas.entrySet())
			res += pair.getKey() + ": " + pair.getValue() + "\n"; 
		
		return res;
	}
	

}
