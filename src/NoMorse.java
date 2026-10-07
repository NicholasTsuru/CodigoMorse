public class NoMorse {
    char simbolo; // '\0' significa que este nó ainda não tem letra ou número.
    NoMorse esquerda;
    NoMorse direita;

    public NoMorse(char simbolo) {
        this.simbolo = simbolo;
    }
}
