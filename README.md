# Código Morse com árvore binária

Integrantes: **Nicholas Tsuru Ramos**.

Programa em Java que converte letras de A a Z, números de 0 a 9 e espaços.
Aceita letras minúsculas, convertendo para maiúsculas. Não aceita acentos nem pontuação.
Não usa bibliotecas externas.

## Compilar e executar

Instale **JDK 17 ou superior** (o JRE sozinho não tem o compilador).
Verifique `java -version` e `javac -version` no terminal.
Abra o terminal **na pasta deste projeto**, onde estão `src` e este README.

```sh
mkdir bin
javac -encoding UTF-8 --release 17 -d bin src/*.java
java -cp bin Main
```

Se `bin` já existe, pule `mkdir bin`. Os mesmos comandos servem para os terminais
usuais do Windows, Linux e macOS. Não misture as classes deste projeto com as do Flood Fill:
ambos possuem uma classe chamada `Main`, em pastas `bin` independentes.

## Menu e exemplos

| Opção | O que faz | Entrada de exemplo |
|---|---|---|
| 1 | Codifica texto digitado | `OLA MUNDO` |
| 2 | Decodifica Morse digitado | `... --- ...` |
| 3 | Codifica arquivo `.txt` | `exemplos/texto.txt` |
| 4 | Decodifica arquivo Morse `.txt` | `exemplos/morse.txt` |
| 5 | Desenha a árvore no terminal | Não pede entrada |
| 0 | Encerra | — |

Resultados conhecidos:

```text
SOS       -> ... --- ...
OLA MUNDO -> --- .-.. .- / -- ..- -. -.. ---
... --- ... -> SOS
```

Um espaço separa os códigos das letras. Cada espaço do texto vira uma barra `/`.
Assim, dois espaços entre palavras viram `/ /`, e voltam a ser dois espaços.
Espaços extras entre códigos Morse são ignorados; cada barra sempre gera um espaço.
A barra também pode aparecer encostada a um código, por exemplo `.../---` vira `S O`.

Na decodificação, só são permitidos `.`, `-`, `/` e espaço. O arquivo Morse precisa
ter **uma linha sem quebra de linha, inclusive no final**. Não usamos `trim()` para
esconder uma entrada inválida. `exemplos/morse_invalido.txt` tem uma quebra de linha
de propósito e deve ser rejeitado. Texto comum em arquivo pode ter várias linhas:
cada quebra de linha vira um espaço antes da codificação. Arquivos usam UTF-8.

O resultado é impresso no terminal. Um erro mostra uma mensagem e volta ao menu.

## Como o código funciona

- `NoMorse`: guarda um símbolo e referências para os filhos esquerdo e direito.
- `ArvoreMorse`: insere manualmente os 36 símbolos e realiza as conversões e o desenho.
- `Arquivos`: lê `.txt`; deixa a conversão com a árvore.
- `Main`: repete o menu até a opção 0.

Para achar `A`, percorremos `.-`: raiz → esquerda (`E`) → direita (`A`).
Para decodificar, o programa segue o caminho indicado pelos pontos e traços.
Para codificar, procura a letra na árvore e guarda o caminho percorrido.
Nenhum mapa pronto substitui a árvore. Alguns nós existem só para completar caminhos
e não possuem símbolo; eles aparecem como `(sem símbolo)`.

No desenho, cada nível de recuo representa um filho. `+--` liga um nó ao seu pai;
as legendas `. (esquerda)` e `- (direita)` identificam os caminhos.
Veja também [EXPLICACAO.md](EXPLICACAO.md).



