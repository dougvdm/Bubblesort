import java.io.*;
import java.nio.file.*;
import java.util.*;

public class insertionsort {

    // Função de ordenação Insertion Sort
    public static void insertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;

            // Move os elementos de arr[0..i-1], que são maiores que a chave,
            // para uma posição à frente da sua posição atual
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }

    public static void main(String[] args) {
        // Informações da linguagem e sistema
        System.out.println("Linguagem: Java");
        System.out.println("Versão: " + System.getProperty("java.version"));

        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        System.out.println("Sistema Operacional: " + osName + " " + osVersion);

        String cpuInfo = getCpuInfo();
        System.out.println("CPU: " + cpuInfo);

        long totalMemory = Runtime.getRuntime().totalMemory();
        System.out.printf("Memória RAM: %.2f GB%n%n", totalMemory / (1024.0 * 1024.0 * 1024.0));

        // Arquivo de entrada e saída
        String inputFile = "/workspaces/Bubblesort/src/arq.txt";
        String outputFile = "insertionsort_Java.txt";

        try {
            // Lendo o arquivo
            List<String> lines = Files.readAllLines(Paths.get(inputFile));
            List<Integer> numbers = new ArrayList<>();

            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    numbers.add(Integer.parseInt(line.trim()));
                }
            }

            int[] arr = numbers.stream().mapToInt(Integer::intValue).toArray();

            // Insertion Sort
            long start = System.nanoTime();
            insertionSort(arr);
            long end = System.nanoTime();

            // Salvando em arquivo de saída
            List<String> sortedNumbers = new ArrayList<>();
            for (int num : arr) {
                sortedNumbers.add(String.valueOf(num));
            }
            Files.write(Paths.get(outputFile), sortedNumbers);

            // Informações de tempo e memória
            double tempoMs = (end - start) / 1e6;
            double memoriaKb = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            System.out.printf("Tempo de execução: %.2f ms%n", tempoMs);
            System.out.printf("Memória utilizada: %.2f KB%n", memoriaKb / 1024.0);

        } catch (IOException e) {
            System.err.println("Erro: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Erro ao converter número: " + e.getMessage());
        }
    }

    // Função para obter informações do CPU
    private static String getCpuInfo() {
        String cpuInfo = "Informação não disponível";
        try {
            Process process = Runtime.getRuntime().exec("lscpu");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Model name")) {
                    cpuInfo = line.split(":")[1].trim();
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao obter informações do CPU: " + e.getMessage());
        }
        return cpuInfo;
    }
}