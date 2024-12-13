import java.io.*;
import java.nio.file.*;
import java.util.*;

public class bubblesort {

    // Função de ordenação Bubble Sort
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // Troca os elementos
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
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
        String inputFile = "/src/arq.txt";
        String outputFile = "bubblesort_Java.txt";

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

            // Bubble Sort
            long start = System.nanoTime();
            bubbleSort(arr);
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