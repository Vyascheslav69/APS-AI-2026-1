package dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;
import estruturas_dados.grafos.Graph;
import solver.instance.Instance;

public class DAO {
    
    static private final String EOF = "EOF";
    static private final String NODE_COORD_SECTION = "NODE_COORD_SECTION";
    static private final String DIMENSION = "DIMENSION";
    static private final Instance inst = Instance.inst;
    static private final String NAME = "NAME";
    static private final String COMMENT = "COMMENT";
    static private final String EDGE_WEIGHT_TYPE = "EDGE_WEIGHT_TYPE";
    static private final String EDGE_WEIGHT_FORMAT = "EDGE_WEIGHT_FORMAT";
    static private final String EDGE_WEIGHT_SECTION = "EDGE_WEIGHT_SECTION";
            
    // Abre o arquivo .tsp e mapeia as chaves para a memoria
    public static void readInstance(String path) {
        inst.reset(); // Limpa dados anteriores
        File file = new File(path);

        if (!file.exists()) {
            System.err.println("ERRO: Arquivo não encontrado em: " + file.getAbsolutePath());
            System.err.println("Certifique-se de que a pasta 'instancias' está no diretório raiz do projeto.");
            return;
        }

        try (Scanner myReader = new Scanner(file).useLocale(Locale.US)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine().trim();
                if (data.isEmpty()) continue;
                
                // Ignora linhas que não são pares chave:valor mas também não são seções
                if (!data.contains(":") && !data.equals(NODE_COORD_SECTION) && !data.equals(EDGE_WEIGHT_SECTION)) {
                    continue;
                }

                String[] parts = data.split(":", 2);
                String key = parts[0].trim();
                String value = parts.length > 1 ? parts[1].trim() : "";

                // Preenche metadados da instancia
                if(key.equals(NAME)) { inst.NAME = value; continue; }
                if(key.equals(COMMENT)) { inst.COMMENT = value; continue; }
                
                if(key.equals(EDGE_WEIGHT_TYPE)) {
                    if(value.contains("EUC_2D")) inst.EdgeWeightType = Instance.EDGE_WEIGHT_TYPE.EUC_2D;
                    else if(value.contains("EXPLICIT")) inst.EdgeWeightType = Instance.EDGE_WEIGHT_TYPE.EXPLICIT;
                    continue;
                }
                
                if(key.equals(EDGE_WEIGHT_FORMAT)) {
                    if(value.contains("UPPER_ROW")) inst.EdgeWeightFormat = Instance.EDGE_WEIGHT_FORMAT.UPPER_ROW;
                    else if(value.contains("LOWER_DIAG_ROW")) inst.EdgeWeightFormat = Instance.EDGE_WEIGHT_FORMAT.LOWER_DIAG_ROW;
                    continue;
                }

                // Define o tamanho do grafo ao ler a dimensao
                if(key.startsWith(DIMENSION)) {
                    String dimVal = value.isEmpty() ? key.split("\\s+")[1] : value;
                    try {
                        inst.GRAPH = new Graph(Integer.parseInt(dimVal.replaceAll("[^0-9]", "")));
                    } catch (Exception e) {
                        // Tenta pegar o segundo token se o primeiro falhar
                        String[] tokens = data.split("\\s+");
                        inst.GRAPH = new Graph(Integer.parseInt(tokens[tokens.length-1]));
                    }
                    continue;
                }
                
                // Inicia leitura das coordenadas das cidades
                if(data.equals(NODE_COORD_SECTION)) {
                    readNodeCoordSectionEuc2D(myReader);
                    continue;
                }

                // Inicia leitura de pesos explícitos
                if(data.equals(EDGE_WEIGHT_SECTION)) {
                    readEdgeWeightSection(myReader);
                    continue;
                }
            }
        } catch (FileNotFoundException e) { 
            System.err.println("Erro ao abrir arquivo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro durante a leitura da instância: " + e.getMessage());
            e.printStackTrace();
        }
    }

    static private void readEdgeWeightSection(Scanner reader) {
        int n = inst.GRAPH.getSize();
        if (inst.EdgeWeightFormat == Instance.EDGE_WEIGHT_FORMAT.UPPER_ROW) {
            for (int i = 0; i < n - 1; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (reader.hasNextDouble()) {
                        double w = reader.nextDouble();
                        inst.GRAPH.setWeight(i, j, w);
                    }
                }
            }
        } else if (inst.EdgeWeightFormat == Instance.EDGE_WEIGHT_FORMAT.LOWER_DIAG_ROW) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j <= i; j++) {
                    if (reader.hasNextDouble()) {
                        double w = reader.nextDouble();
                        inst.GRAPH.setWeight(i, j, w);
                    }
                }
            }
        }
    }
    
    // Le coordenadas X/Y e calcula as distancias euclidianas entre cidades
    static private void readNodeCoordSectionEuc2D(Scanner reader) {
        while (reader.hasNextLine()) {
            String linha = reader.nextLine();
            if(linha.strip().equals(EOF)) break;
            
            try(Scanner toNextNumber = new Scanner(linha).useLocale(Locale.US)){
                int v = toNextNumber.nextInt();
                double x = toNextNumber.nextDouble();
                double y = toNextNumber.nextDouble();
                inst.GRAPH.setVertexCoord(v-1, x, y); // Salva coordenadas
            }catch(InputMismatchException e) { break; }
        }
        inst.GRAPH.calcEuclidianFromCoords(); // Preenche matriz de pesos
    }
}
