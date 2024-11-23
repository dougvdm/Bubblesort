import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class bubblesort {
    // Caminhos dos arquivos
    private static final String inputPathArquivo = "arq.txt";
    private static final String outputPathArquivo = "arquivo_ordenadoJava.txt";

    // Lista para armazenar os números
    private static List<Double> numerosList = new ArrayList<>();

    // Main para rodar tudo
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Runtime runtime = Runtime.getRuntime();
        long startMemory = runtime.totalMemory() - runtime.freeMemory();

        lerNumeros(inputPathArquivo);
        
        bubbleSort(numerosList);

        escreverArquivo(outputPathArquivo);

        long endTime = System.currentTimeMillis();
        long endMemory = runtime.totalMemory() - runtime.freeMemory();

        printPerformance(startTime, endTime, startMemory, endMemory);

        System.out.println("Processo concluído.");
    }

    // Função para ler os numeros do arquivo e colocar no ArrayList
    private static void lerNumeros(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                try {
                    double numeros = Double.parseDouble(linha.trim());
                    numerosList.add(numeros);
                } catch (NumberFormatException e) {
                    // Ignorar linhas que não são números
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    // Função para ordenar o ArrayList
    private static void bubbleSort(List<Double> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j) > list.get(j + 1)) {
                    Collections.swap(list, j, j + 1);
                }
            }
        }
    }

    // Função para escrever no arquivo novo
    private static void escreverArquivo(String filePath) {
        try (FileWriter fw = new FileWriter(filePath)) {
            for (Double numero : numerosList) {
                fw.write(numero + "\n");
            }
        } catch (IOException e) {
            System.err.println("Erro ao gravar o arquivo: " + e.getMessage());
        }
    }

    // Função para printar o tempo de execução e a memoria usada
    private static void printPerformance(long startTime, long endTime, long startMemory, long endMemory) {
        long elapsedTime = endTime - startTime;
        long usedMemory = (endMemory - startMemory) / 1024; // em KB

        System.out.println("Tempo de execução: " + (elapsedTime / 1000) + "s " + (elapsedTime % 1000) + "ms");
        System.out.println("Memória usada: " + usedMemory + " KB");
    }
    
}