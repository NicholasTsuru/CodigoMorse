import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        ArvoreMorse arvore = new ArvoreMorse();
        while (true) {
            System.out.println("\nCÓDIGO MORSE");
            System.out.println("1 - Codificar texto digitado");
            System.out.println("2 - Decodificar Morse digitado");
            System.out.println("3 - Codificar arquivo de texto");
            System.out.println("4 - Decodificar arquivo Morse");
            System.out.println("5 - Mostrar árvore");
            System.out.println("0 - Encerrar");
            if (!entrada.hasNextLine()) return;
            String opcao = entrada.nextLine().trim();
            if (opcao.equals("0")) return;
            try {
                switch (opcao) {
                    case "1":
                        System.out.print("Texto (sem acentos): ");
                        if (!entrada.hasNextLine()) return;
                        System.out.println("Resultado: " + arvore.codificar(entrada.nextLine()));
                        break;
                    case "2":
                        System.out.print("Morse em uma linha: ");
                        if (!entrada.hasNextLine()) return;
                        System.out.println("Resultado: " + arvore.decodificar(entrada.nextLine()));
                        break;
                    case "3":
                        System.out.print("Caminho do arquivo .txt: ");
                        if (!entrada.hasNextLine()) return;
                        System.out.println("Resultado: " + Arquivos.codificar(entrada.nextLine(), arvore));
                        break;
                    case "4":
                        System.out.print("Caminho do arquivo Morse .txt: ");
                        if (!entrada.hasNextLine()) return;
                        System.out.println("Resultado: " + Arquivos.decodificar(entrada.nextLine(), arvore));
                        break;
                    case "5":
                        arvore.mostrar();
                        break;
                    default:
                        System.out.println("Opção inválida. Digite um número do menu.");
                }
            } catch (IOException e) {
                System.out.println("Não foi possível ler o arquivo. Confira o caminho e a permissão.");
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }
}
