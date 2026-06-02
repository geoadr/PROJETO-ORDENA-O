package ordenacao;
import java.io.*;
import java.util.*;

public class Main {
    static long trocas;
    static long comparacoes;

    static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int chave = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > chave) {
                comparacoes++;
                arr[j + 1] = arr[j];
                trocas++;
                j--;
            }
            if (j >= 0) comparacoes++; 
            arr[j + 1] = chave;
        }
    }

    static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                comparacoes++;
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                    trocas++;
                }
            }
        }
    }

    static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                comparacoes++;
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            if (minIdx != i) {
                int tmp = arr[minIdx];
                arr[minIdx] = arr[i];
                arr[i] = tmp;
                trocas++;
            }
        }
    }
    static void combSort(int[] arr) {
        int n = arr.length;
        int gap = n;
        double fator = 1.3;
        boolean trocou = true;

        while (gap != 1 || trocou) {
            gap = (int) (gap / fator);
            if (gap < 1) gap = 1;
            trocou = false;

            for (int i = 0; i + gap < n; i++) {
                comparacoes++;
                if (arr[i] > arr[i + gap]) {
                    int tmp = arr[i];
                    arr[i] = arr[i + gap];
                    arr[i + gap] = tmp;
                    trocas++;
                    trocou = true;
                }
            }
        }
    }

    static void shellSort(int[] arr) {
        int n = arr.length;
        int gap = n / 2;

        while (gap > 0) {
            for (int i = gap; i < n; i++) {
                int tmp = arr[i];
                int j = i;
                while (j >= gap && arr[j - gap] > tmp) {
                    comparacoes++;
                    arr[j] = arr[j - gap];
                    trocas++;
                    j -= gap;
                }
                if (j >= gap) comparacoes++;
                arr[j] = tmp;
            }
            gap /= 2;
        }
    }

    static void gnomeSort(int[] arr) {
        int i = 0;
        int n = arr.length;
        while (i < n) {
            if (i == 0) {
                i++;
            } else {
                comparacoes++;
                if (arr[i] >= arr[i - 1]) {
                    i++;
                } else {
                    int tmp = arr[i];
                    arr[i] = arr[i - 1];
                    arr[i - 1] = tmp;
                    trocas++;
                    i--;
                }
            }
        }
    }

    static void cocktailSort(int[] arr) {
        int inicio = 0;
        int fim = arr.length - 1;
        boolean trocou = true;

        while (trocou) {
            trocou = false;
            for (int i = inicio; i < fim; i++) {
                comparacoes++;
                if (arr[i] > arr[i + 1]) {
                    int tmp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = tmp;
                    trocas++;
                    trocou = true;
                }
            }
            if (!trocou) break;
            fim--;
            trocou = false;
            for (int i = fim; i > inicio; i--) {
                comparacoes++;
                if (arr[i] < arr[i - 1]) {
                    int tmp = arr[i];
                    arr[i] = arr[i - 1];
                    arr[i - 1] = tmp;
                    trocas++;
                    trocou = true;
                }
            }
            inicio++;
        }
    }

    static void quickSort(int[] arr, int baixo, int alto) {
        if (baixo < alto) {
            int pi = particiona(arr, baixo, alto);
            quickSort(arr, baixo, pi - 1);
            quickSort(arr, pi + 1, alto);
        }
    }

    static int particiona(int[] arr, int baixo, int alto) {
        int pivo = arr[alto];
        int i = baixo - 1;
        for (int j = baixo; j < alto; j++) {
            comparacoes++;
            if (arr[j] <= pivo) {
                i++;
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
                trocas++;
            }
        }
        int tmp = arr[i + 1];
        arr[i + 1] = arr[alto];
        arr[alto] = tmp;
        trocas++;
        return i + 1;
    }

    static void mergeSort(int[] arr, int esq, int dir) {
        if (esq < dir) {
            int meio = (esq + dir) / 2;
            mergeSort(arr, esq, meio);
            mergeSort(arr, meio + 1, dir);
            merge(arr, esq, meio, dir);
        }
    }

    static void merge(int[] arr, int esq, int meio, int dir) {
        int n1 = meio - esq + 1;
        int n2 = dir - meio;
        int[] L = new int[n1];
        int[] R = new int[n2];
        System.arraycopy(arr, esq, L, 0, n1);
        System.arraycopy(arr, meio + 1, R, 0, n2);
        int i = 0, j = 0, k = esq;
        while (i < n1 && j < n2) {
            comparacoes++;
            if (L[i] <= R[j]) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
                trocas++;
            }
        }
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    static void radixSort(int[] arr) {
        int max = Arrays.stream(arr).map(Math::abs).max().getAsInt();
       
        int min = Arrays.stream(arr).min().getAsInt();
        int offset = (min < 0) ? -min : 0;
        int[] positivo = new int[arr.length];
        for (int i = 0; i < arr.length; i++) positivo[i] = arr[i] + offset;
        int maxPos = max + offset;

        for (int exp = 1; maxPos / exp > 0; exp *= 10) {
            countingSortPorDigito(positivo, exp);
            comparacoes += arr.length;
        }
        for (int i = 0; i < arr.length; i++) arr[i] = positivo[i] - offset;
    }

    static void countingSortPorDigito(int[] arr, int exp) {
        int[] saida = new int[arr.length];
        int[] count = new int[10];
        for (int val : arr) count[(val / exp) % 10]++;
        for (int i = 1; i < 10; i++) count[i] += count[i - 1];
        for (int i = arr.length - 1; i >= 0; i--) {
            saida[--count[(arr[i] / exp) % 10]] = arr[i];
            trocas++;
        }
        System.arraycopy(saida, 0, arr, 0, arr.length);
    }

    static void radixSortLSD(int[] arr) {
        int min = Arrays.stream(arr).min().getAsInt();
        int offset = (min < 0) ? -min : 0;
        int[] buf = new int[arr.length];
        for (int i = 0; i < arr.length; i++) buf[i] = arr[i] + offset;
        int maxVal = Arrays.stream(buf).max().getAsInt();

        for (int exp = 1; maxVal / exp > 0; exp *= 10) {
            int[] saida = new int[arr.length];
            int[] count = new int[10];
            for (int v : buf) count[(v / exp) % 10]++;
            for (int i = 1; i < 10; i++) count[i] += count[i - 1];
            for (int i = buf.length - 1; i >= 0; i--) {
                saida[--count[(buf[i] / exp) % 10]] = buf[i];
                trocas++;
            }
            comparacoes += arr.length;
            System.arraycopy(saida, 0, buf, 0, buf.length);
        }
        for (int i = 0; i < arr.length; i++) arr[i] = buf[i] - offset;
    }

    static void radixSortMSD(int[] arr) {
        int min = Arrays.stream(arr).min().getAsInt();
        int offset = (min < 0) ? -min : 0;
        int[] buf = new int[arr.length];
        for (int i = 0; i < arr.length; i++) buf[i] = arr[i] + offset;
        int maxVal = Arrays.stream(buf).max().getAsInt();
        int maxExp = 1;
        while (maxVal / maxExp >= 10) maxExp *= 10;

        radixMSDRecursivo(buf, 0, buf.length - 1, maxExp);
        for (int i = 0; i < arr.length; i++) arr[i] = buf[i] - offset;
    }

    static void radixMSDRecursivo(int[] arr, int esq, int dir, int exp) {
        if (esq >= dir || exp == 0) return;
        int[] count = new int[11];
        for (int i = esq; i <= dir; i++) {
            count[(arr[i] / exp) % 10 + 1]++;
            comparacoes++;
        }
        for (int i = 1; i < 11; i++) count[i] += count[i - 1];
        int[] saida = new int[dir - esq + 1];
        int[] idx = Arrays.copyOf(count, count.length);
        for (int i = esq; i <= dir; i++) {
            int digito = (arr[i] / exp) % 10;
            saida[idx[digito]++] = arr[i];
            trocas++;
        }
        System.arraycopy(saida, 0, arr, esq, saida.length);
        for (int d = 0; d < 10; d++) {
            radixMSDRecursivo(arr, esq + count[d], esq + count[d + 1] - 1, exp / 10);
        }
    }

    static void bucketSort(int[] arr) {
        if (arr.length == 0) return;
        int min = Arrays.stream(arr).min().getAsInt();
        int max = Arrays.stream(arr).max().getAsInt();
        int range = max - min + 1;
        int numBuckets = (int) Math.sqrt(arr.length);
        if (numBuckets < 1) numBuckets = 1;

        @SuppressWarnings("unchecked")
        List<Integer>[] buckets = new ArrayList[numBuckets];
        for (int i = 0; i < numBuckets; i++) buckets[i] = new ArrayList<>();

        for (int val : arr) {
            int idx = (int) ((long)(val - min) * (numBuckets - 1) / range);
            buckets[idx].add(val);
            trocas++;
        }

        int k = 0;
        for (List<Integer> bucket : buckets) {
            Integer[] b = bucket.toArray(new Integer[0]);
            for (int i = 1; i < b.length; i++) {
                int chave = b[i];
                int j = i - 1;
                while (j >= 0 && b[j] > chave) {
                    comparacoes++;
                    b[j + 1] = b[j];
                    j--;
                }
                if (j >= 0) comparacoes++;
                b[j + 1] = chave;
            }
            for (int v : b) arr[k++] = v;
        }
    }

    static final int TIM_RUN = 32;

    static void timSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i += TIM_RUN) {
            insertionSortTim(arr, i, Math.min(i + TIM_RUN - 1, n - 1));
        }
        for (int size = TIM_RUN; size < n; size *= 2) {
            for (int esq = 0; esq < n; esq += 2 * size) {
                int meio = Math.min(esq + size - 1, n - 1);
                int dir = Math.min(esq + 2 * size - 1, n - 1);
                if (meio < dir) {
                    merge(arr, esq, meio, dir);
                }
            }
        }
    }

    static void insertionSortTim(int[] arr, int esq, int dir) {
        for (int i = esq + 1; i <= dir; i++) {
            int chave = arr[i];
            int j = i - 1;
            while (j >= esq && arr[j] > chave) {
                comparacoes++;
                arr[j + 1] = arr[j];
                trocas++;
                j--;
            }
            if (j >= esq) comparacoes++;
            arr[j + 1] = chave;
        }
    }
    static int[] carregarArray(String caminho) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(caminho));
        String linha = br.readLine();
        br.close();
        String[] partes = linha.split(",");
        int[] arr = new int[partes.length];
        for (int i = 0; i < partes.length; i++) {
            arr[i] = Integer.parseInt(partes[i].trim());
        }
        return arr;
    }

    static class Resultado {
        String algoritmo;
        int tamanho;
        long tempoMs;
        long trocasTotal;
        long comparacoesTotal;

        Resultado(String alg, int tam, long tempo, long tr, long comp) {
            this.algoritmo = alg;
            this.tamanho = tam;
            this.tempoMs = tempo;
            this.trocasTotal = tr;
            this.comparacoesTotal = comp;
        }

        @Override
        public String toString() {
            return String.format("%-20s | %7d | %8d ms | %15d | %15d",
                    algoritmo, tamanho, tempoMs, trocasTotal, comparacoesTotal);
        }
    }

    static Resultado executar(String nome, int[] original, int tamanho) {
        int[] arr = Arrays.copyOf(original, original.length);
        trocas = 0;
        comparacoes = 0;

        long inicio = System.currentTimeMillis();

        switch (nome) {
            case "Insertion Sort":   insertionSort(arr); break;
            case "Bubble Sort":      bubbleSort(arr); break;
            case "Selection Sort":   selectionSort(arr); break;
            case "Comb Sort":        combSort(arr); break;
            case "Shell Sort":       shellSort(arr); break;
            case "Gnome Sort":       gnomeSort(arr); break;
            case "Cocktail Sort":    cocktailSort(arr); break;
            case "Quick Sort":       quickSort(arr, 0, arr.length - 1); break;
            case "Merge Sort":       mergeSort(arr, 0, arr.length - 1); break;
            case "Radix Sort":       radixSort(arr); break;
            case "Radix Sort LSD":   radixSortLSD(arr); break;
            case "Radix Sort MSD":   radixSortMSD(arr); break;
            case "Bucket Sort":      bucketSort(arr); break;
            case "Tim Sort":         timSort(arr); break;
        }

        long tempo = System.currentTimeMillis() - inicio;
        return new Resultado(nome, tamanho, tempo, trocas, comparacoes);
    }

    public static void main(String[] args) throws IOException {
        String pasta = "dados/";

        int[] tamanhos = {5, 100, 1000, 10000, 50000, 100000, 500000};
        String[] arquivos = {
            "array_5.txt", "array_100.txt", "array_1000.txt",
            "array_10000.txt", "array_50000.txt",
            "array_100000.txt", "array_500000.txt"
        };

        Set<String> lentos = new HashSet<>(Arrays.asList(
            "Bubble Sort", "Selection Sort", "Insertion Sort",
            "Gnome Sort", "Cocktail Sort"
        ));

        String[] algoritmos = {
            "Insertion Sort", "Bubble Sort", "Selection Sort",
            "Comb Sort", "Shell Sort", "Gnome Sort", "Cocktail Sort",
            "Quick Sort", "Merge Sort", "Radix Sort",
            "Radix Sort LSD", "Radix Sort MSD", "Bucket Sort", "Tim Sort"
        };

        List<Resultado> resultados = new ArrayList<>();

        System.out.println("=".repeat(80));
        System.out.println("       COMPARATIVO DE ALGORITMOS DE ORDENACAO");
        System.out.println("=".repeat(80));
        System.out.printf("%-20s | %7s | %10s | %15s | %15s%n",
                "Algoritmo", "Tam.", "Tempo", "Trocas", "Comparacoes");
        System.out.println("-".repeat(80));

        for (int t = 0; t < tamanhos.length; t++) {
            int tam = tamanhos[t];
            System.out.println("\n[Tamanho: " + tam + " elementos]");

            int[] base = carregarArray(pasta + arquivos[t]);

            for (String alg : algoritmos) {
                if (tam > 10000 && lentos.contains(alg)) {
                    System.out.printf("%-20s | %7d | %8s    | %15s | %15s%n",
                            alg, tam, "pulado", "-", "-");
                    continue;
                }
                Resultado r = executar(alg, base, tam);
                resultados.add(r);
                System.out.println(r);
            }
        }
        PrintWriter csv = new PrintWriter("resultados.csv");
        csv.println("Algoritmo,Tamanho,Tempo(ms),Trocas,Comparacoes");
        for (Resultado r : resultados) {
            csv.printf("%s,%d,%d,%d,%d%n",
                    r.algoritmo, r.tamanho, r.tempoMs, r.trocasTotal, r.comparacoesTotal);
        }
        csv.close();

        System.out.println("\n" + "=".repeat(80));
        System.out.println("Resultados exportados para: resultados.csv");
        System.out.println("=".repeat(80));
    }
}
