package dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

import estruturas_dados.grafos.Graph;
import solver.instance.Instance;
import solver.instance.Instance.EDGE_WEIGHT_FORMAT;
import solver.instance.Instance.EDGE_WEIGHT_TYPE;

public class DAO {
	
	static private final String EOF = "EOF";
	static private final String DISPLAY_DATA_SECTION = "DISPLAY_DATA_SECTION";
	
	
	static private final String NODE_COORD_SECTION = "NODE_COORD_SECTION";
	static private final String EDGE_WEIGHT_SECTION = "EDGE_WEIGHT_SECTION";
	static private final String EDGE_WEIGHT_FORMAT_TXT = "EDGE_WEIGHT_FORMAT";
	static private final String EDGE_WEIGHT_TYPE_TXT = "EDGE_WEIGHT_TYPE";
	
	static private final String DIMENSION = "DIMENSION";
	
	static private final Instance inst = Instance.inst;
	
	static private final String NAME = "NAME";
	static private final String COMMENT = "COMMENT";
			
	
	public static void readInstance(String path) {
		File file = new File(path);

	    try (Scanner myReader = new Scanner(file)) {
	        while (myReader.hasNextLine()) {
	        	String data = myReader.nextLine();
	        	// System.out.println(data);
	        	
	        	if(data.split(":")[0].strip().equals(NAME)) {
	        		inst.NAME = data.split(":")[1].strip();
	        		continue;
	        	}
	        	
	        	if(data.split(":")[0].strip().equals(COMMENT)) {
	        		inst.COMMENT = data.split(":")[1].strip();
	        		continue;
	        	}
	        	
	        	if(data.split(":")[0].strip().equals(EDGE_WEIGHT_FORMAT_TXT)) {
	        		switch (data.split(":")[1].strip()) {
					case "LOWER_DIAG_ROW":
						inst.EdgeWeightFormat = EDGE_WEIGHT_FORMAT.LOWER_DIAG_ROW;
						break;
					case "UPPER_ROW":
						inst.EdgeWeightFormat = EDGE_WEIGHT_FORMAT.UPPER_ROW;
						break;
					default:
						break;
					}
	        		continue;
	        	}
	        	
	        	
	        	if(data.split(":")[0].strip().equals(EDGE_WEIGHT_TYPE_TXT)) {
	        		switch (data.split(":")[1].strip()) {
					case "EUC_2D":
						inst.EdgeWeightType = EDGE_WEIGHT_TYPE.EUC_2D;
						break;
					case "EXPLICIT":
						inst.EdgeWeightType = EDGE_WEIGHT_TYPE.EXPLICIT;
						break;
					default:
						break;
					}
	        		continue;
	        	}
	        	
	        	if(data.split(":")[0].strip().equals(DIMENSION)) {
	        		int d = Integer.parseInt(data.split(":")[1].strip());
	        		inst.GRAPH = new Graph(d);
	        		continue;
	        	}
	        	
	        	if(data.strip().equals(EDGE_WEIGHT_SECTION)) {
	        		readEdgeWeightSection(myReader);
	        		continue;
	        	}
	        	
	        	if(data.strip().equals(NODE_COORD_SECTION)) {
	        		readNodeCoordSectionEuc2D(myReader);
	        		continue;
	        	}
	        	
	        }
	    } catch (FileNotFoundException e) {
	    	System.out.println("An error occurred.");
	    	e.printStackTrace();
	    }
	}	
	
	static private void readEdgeWeightSection(Scanner reader) {
		String edgeWeightSectionTxt = readEdgeWeightSectionToString(reader);
		switch (inst.EdgeWeightFormat) {
		case LOWER_DIAG_ROW:
			readEdgeWeightSectionLowerDiag(edgeWeightSectionTxt);
			break;
		case UPPER_ROW:
			readEdgeWeightSectionUpperDiag(edgeWeightSectionTxt);
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + inst.EdgeWeightFormat);
		}
	}
	
	static private void readEdgeWeightSectionLowerDiag(String edgeWeightSectionTxt) {
		try(Scanner toNextDouble = new Scanner(edgeWeightSectionTxt)){
			for(int i=0; i< inst.GRAPH.getSize(); i++) {
				for(int j=0; j<=i; j++) {
					double w = toNextDouble.nextDouble();
					inst.GRAPH.setWeight(i, j, w);
				}
			}
		}catch(InputMismatchException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	static private void readEdgeWeightSectionUpperDiag(String edgeWeightSectionTxt) {
		try(Scanner toNextDouble = new Scanner(edgeWeightSectionTxt)){
			for(int i=0; i< inst.GRAPH.getSize(); i++) {
				inst.GRAPH.setWeight(i, i, 0);
				for(int j=i+1; j<inst.GRAPH.getSize(); j++) {
					double w = toNextDouble.nextDouble();
					inst.GRAPH.setWeight(i, j, w);
				}
			}
		}catch(InputMismatchException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	static private void readNodeCoordSectionEuc2D(Scanner reader) {
		readNodeCoordSection(reader);
		inst.GRAPH.calcEuclidianFromCoords();
	}
	
	static private void readNodeCoordSection(Scanner reader) {
		while (reader.hasNextLine()) {
        	
			String linha = reader.nextLine();
    		if(linha.strip().equals(EOF)) return;
        	
    		try(Scanner toNextNumber = new Scanner(linha)){
    			int v = toNextNumber.nextInt();
    			double x = toNextNumber.nextDouble();
    			double y = toNextNumber.nextDouble();
    			inst.GRAPH.setVertexCoord(v-1, x, y);
			}catch(InputMismatchException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
	}
	
	static private String readEdgeWeightSectionToString(Scanner reader) {
        String res = "";
		while (reader.hasNextLine()) {
        	String linha = reader.nextLine();
        	if(linha.strip().equals(EOF)) return res;
        	if(linha.strip().equals(DISPLAY_DATA_SECTION)) { 
        		readNodeCoordSection(reader);
        		return res;
        	}
        	res += linha + " ";
        }
		return res;
	}
	
}
