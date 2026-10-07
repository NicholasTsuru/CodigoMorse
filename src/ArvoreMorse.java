public class ArvoreMorse {
    private final NoMorse raiz = new NoMorse('\0');

    public ArvoreMorse() {
        // Inserções manuais: ponto vai à esquerda; traço vai à direita.
        inserir('A', ".-");
        inserir('B', "-...");
        inserir('C', "-.-.");
        inserir('D', "-..");
        inserir('E', ".");
        inserir('F', "..-.");
        inserir('G', "--.");
        inserir('H', "....");
        inserir('I', "..");
        inserir('J', ".---");
        inserir('K', "-.-");
        inserir('L', ".-..");
        inserir('M', "--");
        inserir('N', "-.");
        inserir('O', "---");
        inserir('P', ".--.");
        inserir('Q', "--.-");
        inserir('R', ".-.");
        inserir('S', "...");
        inserir('T', "-");
        inserir('U', "..-");
        inserir('V', "...-");
        inserir('W', ".--");
        inserir('X', "-..-");
        inserir('Y', "-.--");
        inserir('Z', "--..");
        inserir('0', "-----");
        inserir('1', ".----");
        inserir('2', "..---");
        inserir('3', "...--");
        inserir('4', "....-");
        inserir('5', ".....");
        inserir('6', "-....");
        inserir('7', "--...");
        inserir('8', "---..");
        inserir('9', "----.");
    }

    private void inserir(char simbolo, String codigo) {
        NoMorse atual = raiz;
        for (int i = 0; i < codigo.length(); i++) {
            if (codigo.charAt(i) == '.') {
                if (atual.esquerda == null) atual.esquerda = new NoMorse('\0');
                atual = atual.esquerda;
            } else {
                if (atual.direita == null) atual.direita = new NoMorse('\0');
                atual = atual.direita;
            }
        }
        atual.simbolo = simbolo;
    }

    public String codificar(String texto) {
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < texto.length(); i++) {
            char letra = texto.charAt(i);
            String codigo;
            if (letra == ' ') {
                codigo = "/"; // Cada espaço é preservado por uma barra.
            } else {
                if (letra >= 'a' && letra <= 'z') letra = Character.toUpperCase(letra);
                codigo = buscar(raiz, letra, "");
                if (codigo == null || letra == '\0') {
                    throw new IllegalArgumentException(
                        "Símbolo inválido na posição " + (i + 1)
                        + ". Use letras sem acentos, números e espaços.");
                }
            }
            if (i > 0) resultado.append(' ');
            resultado.append(codigo);
        }
        return resultado.toString();
    }

    private String buscar(NoMorse no, char letra, String caminho) {
        if (no == null) return null;
        if (no.simbolo == letra) return caminho;
        String esquerda = buscar(no.esquerda, letra, caminho + ".");
        if (esquerda != null) return esquerda;
        return buscar(no.direita, letra, caminho + "-");
    }

    public String decodificar(String morse) {
        // Não usamos trim(): ele esconderia quebras de linha e tabulações inválidas.
        for (int i = 0; i < morse.length(); i++) {
            char c = morse.charAt(i);
            if (c != '.' && c != '-' && c != '/' && c != ' ') {
                throw new IllegalArgumentException(
                    "Morse inválido: use somente ponto, traço, barra e espaço, em uma linha.");
            }
        }
        StringBuilder texto = new StringBuilder();
        StringBuilder codigo = new StringBuilder();
        for (int i = 0; i < morse.length(); i++) {
            char c = morse.charAt(i);
            if (c == '.' || c == '-') {
                codigo.append(c);
            } else {
                acrescentarLetra(texto, codigo);
                if (c == '/') texto.append(' ');
            }
        }
        acrescentarLetra(texto, codigo);
        return texto.toString();
    }

    private void acrescentarLetra(StringBuilder texto, StringBuilder codigo) {
        if (codigo.length() == 0) return;
        NoMorse atual = raiz;
        for (int i = 0; i < codigo.length() && atual != null; i++) {
            atual = codigo.charAt(i) == '.' ? atual.esquerda : atual.direita;
        }
        if (atual == null || atual.simbolo == '\0') {
            throw new IllegalArgumentException("Código sem letra ou número: " + codigo);
        }
        texto.append(atual.simbolo);
        codigo.setLength(0);
    }

    public void mostrar() {
        System.out.println("RAIZ (sem símbolo)");
        mostrarFilhos(raiz, "");
    }

    private void mostrarFilhos(NoMorse no, String recuo) {
        if (no.esquerda != null) mostrarNo(no.esquerda, recuo, ". (esquerda)");
        if (no.direita != null) mostrarNo(no.direita, recuo, "- (direita)");
    }

    private void mostrarNo(NoMorse no, String recuo, String direcao) {
        String nome = no.simbolo == '\0' ? "(sem símbolo)" : String.valueOf(no.simbolo);
        System.out.println(recuo + "+-- " + direcao + ": " + nome);
        mostrarFilhos(no, recuo + "|   ");
    }
}
