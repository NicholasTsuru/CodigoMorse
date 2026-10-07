import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

public class Arquivos {
    public static String lerTxt(String caminho) throws IOException {
        if (!caminho.toLowerCase(Locale.ROOT).endsWith(".txt")) {
            throw new IllegalArgumentException("Escolha um arquivo com extensão .txt.");
        }
        return Files.readString(Path.of(caminho), StandardCharsets.UTF_8);
    }

    public static String codificar(String caminho, ArvoreMorse arvore) throws IOException {
        String texto = lerTxt(caminho);
        // Arquivos de texto comum podem ter parágrafos; cada quebra vira um espaço.
        texto = texto.replace("\r\n", " ").replace('\n', ' ').replace('\r', ' ');
        return arvore.codificar(texto);
    }

    public static String decodificar(String caminho, ArvoreMorse arvore) throws IOException {
        // O arquivo Morse é validado inteiro, inclusive possíveis quebras no fim.
        return arvore.decodificar(lerTxt(caminho));
    }
}
