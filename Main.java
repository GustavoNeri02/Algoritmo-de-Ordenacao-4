import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static long movimentos = 0;

    public static void main(String[] args) {
        StringBuilder texto = new StringBuilder("[");

        //---Coloque aqui seu documento .txt (Ex: 2, 42, -23, 31) para ordená-lo---
        int[] vetor = leitura("C:\\Users\\Gustavo\\Desktop\\dados.txt");

        //---Inicio da contagem---
        long tempoInicial = System.currentTimeMillis();

        //radixSort(vetor);
        //gnomeSort(vetor, vetor.length);
        countingSort(vetor);

        //---fim da contagem---
        long tempoFinal = System.currentTimeMillis();

        System.out.println("Executado em = " + (tempoFinal - tempoInicial) + " ms");

        for (int i = 0; i < vetor.length; i++) {
            texto.append(vetor[i]);
            if (i == vetor.length - 1) {
                texto.append("]");
            } else {
                texto.append(", ");
            }
        }
        System.out.println(texto);
        System.out.println("Total de " + movimentos + " movimentos.");
    }

    //leitura do arquivo a ser ordenado
    public static int[] leitura(String arquivo) {

        Path pasta = Paths.get(arquivo);

        try {
            byte[] text = Files.readAllBytes(pasta);

            String[] ler = new String(text).split(", ");
            int[] vetor = new int[ler.length];

            for (int i = 0; i < ler.length; i++) {
                vetor[i] = Integer.parseInt(ler[i]);
            }

            return vetor;
        } catch (Exception erro) {
            return null;
        }
    }


    //Algoritmo de Ordenacao
    //RadixSort
    static void radixSort(int[] input) {
        final int RADIX = 10;
        // declare and initialize bucket[]
        List<Integer>[] bucket = new ArrayList[RADIX];
        for (int i = 0; i < bucket.length; i++) {
            bucket[i] = new ArrayList<>();
        }
        // sort
        boolean maxLength = false;
        int tmp = -1, placement = 1;
        while (!maxLength) {
            maxLength = true;
            // split input between lists
            for (Integer i : input) {
                tmp = i / placement;
                bucket[tmp % RADIX].add(i);
                if (maxLength && tmp > 0) {
                    maxLength = false;
                }
            }
            // empty lists into input array
            int a = 0;
            for (int b = 0; b < RADIX; b++) {
                for (Integer i : bucket[b]) {
                    input[a++] = i;
                }
                bucket[b].clear();
            }
            // move to next digit
            placement *= RADIX;
        }
    }


    //GnomeSort
    static void gnomeSort(int[] vetor, int tamanho) {
        int index = 0;

        while (index < tamanho) {
            if (index == 0)
                index++;
            if (vetor[index] >= vetor[index - 1])
                index++;
            else {
                int temp = vetor[index];
                vetor[index] = vetor[index - 1];
                vetor[index - 1] = temp;
                index--;
                movimentos++;
            }
        }
    }


    //CountingSort
    public static void countingSort(int[] vetor) {

        int maior = vetor[0];
        int menor = vetor[0];
        for (int i = 1; i < vetor.length; i++) {
            if (vetor[i] > maior) {
                maior = vetor[i];
            } else if (vetor[i] < menor) {
                menor = vetor[i];
            }

        }

        int[] C = new int[maior - menor + 1];

        // frequência
        for (int i = 0; i < vetor.length; i++) {
            C[vetor[i] - menor] += 1;
            movimentos++;
        }

        // cumulativa
        for (int i = 1; i < C.length; i++) {
            C[i] += C[i - 1];
            movimentos++;
        }

        int[] B = new int[vetor.length];

        for (int i = vetor.length - 1; i >= 0; i--) {
            B[C[vetor[i] - menor] - 1] = vetor[i];
            C[vetor[i] - menor] -= 1;
            movimentos += 2;
        }
        //transferindo dados para vetor principal
        System.arraycopy(B, 0, vetor, 0, vetor.length);

    }
}
