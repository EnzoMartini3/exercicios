import java.util.Scanner;
import java.io.RandomAccessFile;
import java.io.IOException;

public class Arquivo14 {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String arquivo = "arquivo.txt"; // Caminho do arquivo
        RandomAccessFile arq = new RandomAccessFile(arquivo, "rw"); //abre o arquivo para escrita
        int n = scanner.nextInt();

        for (int i = 0; i < n; i++) {
            double t = scanner.nextDouble();
            arq.writeDouble(t); //anota os números no arquivo
        }
        arq.close(); // Fecha o arquivo após a escrita

        arq = new RandomAccessFile(arquivo, "r"); //reabre o arquivo, agora para leitura

        long tam = arq.length();


        for (int i = 0; i < n; i++) { // Lê os números de trás para frente
            arq.seek(tam - (i + 1) * 8); // Coloca o ponteiro na posição correta (8 bytes por double)
            double valor = arq.readDouble(); // Lê o número real
            
            // Verifica se existem decimais (o exercicio dispensa a impressao de .0
            if (valor == Math.floor(valor)) {
                System.out.println((int) valor);
            } else {
                System.out.println(valor);
            }
        }

        arq.close(); // Fecha o arquivo após a leitura
        scanner.close(); // Fecha o scanner
    }
}
