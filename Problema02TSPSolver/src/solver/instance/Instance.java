package solver.instance;

import estruturas_dados.grafos.Graph;

public class Instance {
	
	static final public Instance inst = new Instance();
	
	public Graph GRAPH;
	
	public String NAME;
	public String TYPE;
	public String COMMENT;
	
	public enum EDGE_WEIGHT_TYPE {EXPLICIT, EUC_2D};
	public EDGE_WEIGHT_TYPE EdgeWeightType;
	
	public enum EDGE_WEIGHT_FORMAT {LOWER_DIAG_ROW, UPPER_ROW};
	public EDGE_WEIGHT_FORMAT EdgeWeightFormat;
	
	@Override
	public String toString() {
		return "Instance [" + 
				",\nNAME=" + NAME + 
				",\nTYPE=" + TYPE + 
				",\nCOMMENT=" + COMMENT + 
				",\nEdgeWeightType=" + EdgeWeightType + 
				",\nEdgeWeightFormat=" + EdgeWeightFormat + 
				",\nGRAPH=" + GRAPH + 
				"]";
	}
	
	
}
