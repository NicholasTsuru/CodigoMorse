import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestesMorse {
    private static int verificacoes = 0;

    private static void igual(String esperado, String recebido) {
        if (!esperado.equals(recebido)) {
            throw new AssertionError("Esperado: " + esperado + "; recebido: " + recebido);
        }
        verificacoes++;
    }

    private static void invalido(Runnable operacao) {
        try {
            operacao.run();
        } catch (IllegalArgumentException e) {
            verificacoes++;
            return;
        }
        throw new AssertionError("A entrada inválida deveria ser rejeitada.");
    }

    public static void main(String[] args) throws IOException {
        ArvoreMorse arvore = new ArvoreMorse();
        String simbolos = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String[] codigos = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....",
            "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.",
            "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--..", "-----",
            ".----", "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----."};
        for (int i = 0; i < simbolos.length(); i++) {
            String letra = String.valueOf(simbolos.charAt(i));
            igual(codigos[i], arvore.codificar(letra));
            igual(letra, arvore.decodificar(codigos[i]));
        }
        igual("... --- ...", arvore.codificar("sos"));
        igual("--- .-.. .- / -- ..- -. -.. ---", arvore.codificar("OLA MUNDO"));
        igual("OLA MUNDO", arvore.decodificar("--- .-.. .- / -- ..- -. -.. ---"));
        igual(" A  B ", arvore.decodificar(arvore.codificar(" A  B ")));
        igual("SOS", arvore.decodificar("  ...   --- ...  "));
        igual("", arvore.codificar(""));
        igual("", arvore.decodificar(""));
        invalido(() -> arvore.codificar("OLÁ"));
        invalido(() -> arvore.codificar("!"));
        invalido(() -> arvore.codificar("\t"));
        invalido(() -> arvore.codificar("\0"));
        for (String entrada : new String[]{"...\n", "...\r", "...\t", "A", "1", "_", "......", "..--"}) {
            invalido(() -> arvore.decodificar(entrada));
        }
        Path pasta = Files.createTempDirectory("testes-morse-");
        Path texto = pasta.resolve("texto.txt");
        Path morse = pasta.resolve("morse.txt");
        Files.writeString(texto, "OLA\r\nMUNDO", StandardCharsets.UTF_8);
        Files.writeString(morse, "... --- ...", StandardCharsets.UTF_8);
        igual("--- .-.. .- / -- ..- -. -.. ---", Arquivos.codificar(texto.toString(), arvore));
        igual("SOS", Arquivos.decodificar(morse.toString(), arvore));
        Files.writeString(morse, "... --- ...\n", StandardCharsets.UTF_8);
        try {
            Arquivos.decodificar(morse.toString(), arvore);
            throw new AssertionError("Quebra de linha no arquivo Morse não pode ser aceita.");
        } catch (IllegalArgumentException e) {
            verificacoes++;
        }
        try {
            Arquivos.lerTxt(pasta.resolve("inexistente.txt").toString());
            throw new AssertionError("Arquivo inexistente deveria falhar.");
        } catch (IOException e) {
            verificacoes++;
        }
        invalido(() -> {
            try {
                Arquivos.lerTxt("arquivo.csv");
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        });
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream console = System.out;
        try {
            System.setOut(new PrintStream(saida, true, StandardCharsets.UTF_8));
            arvore.mostrar();
        } finally {
            System.setOut(console);
        }
        String desenho = saida.toString(StandardCharsets.UTF_8);
        if (!desenho.contains("RAIZ") || !desenho.contains(". (esquerda): E")
                || !desenho.contains("- (direita): T") || !desenho.contains(": 0")) {
            throw new AssertionError("O desenho deve indicar raiz, direções e números.");
        }
        verificacoes++;
        System.out.println("Morse: " + verificacoes + " verificações passaram.");
    }
}
