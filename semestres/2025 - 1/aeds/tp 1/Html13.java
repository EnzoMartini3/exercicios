import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Html13 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String linha;

        while (true) {
            // Lê o nome da página
            String nomePagina = br.readLine();
            if (nomePagina == null || nomePagina.equals("FIM")) {
                break; // Encerra o loop se a linha for "FIM" ou nula
            }

            // Lê o endereço da página
            String endereco = br.readLine();
            if (endereco == null || endereco.equals("FIM")) {
                break; // Encerra o loop se a linha for "FIM" ou nula
            }

            try {
                String html = baixarPaginaWeb(endereco);
                Map<String, Integer> contagens = contarCaracteresEPadroes(html);
                exibirResultados(contagens, nomePagina);
            } catch (IOException e) {
                System.out.println("Erro ao acessar a página: " + endereco);
            }
        }
    }

    private static String baixarPaginaWeb(String endereco) throws IOException {
        StringBuilder conteudo = new StringBuilder();
        URL url = new URL(endereco);
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) { // Usa UTF-8
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                conteudo.append(inputLine);
            }
        }
        return conteudo.toString();
    }

    private static Map<String, Integer> contarCaracteresEPadroes(String html) {
        Map<String, Integer> contagens = new HashMap<>();

        // Inicializa as contagens para vogais, consoantes e padrões
        String[] vogais = {"a", "e", "i", "o", "u"};
        String[] vogaisAcentuadas = {"á", "é", "í", "ó", "ú", "à", "è", "ì", "ò", "ù", "ã", "õ", "â", "ê", "î", "ô", "û"};
        String[] padroes = {"<br>", "<table>"};

        for (String vogal : vogais) {
            contagens.put(vogal, 0);
        }
        for (String vogalAcentuada : vogaisAcentuadas) {
            contagens.put(vogalAcentuada, 0);
        }
        contagens.put("consoante", 0);
        for (String padrao : padroes) {
            contagens.put(padrao, 0);
        }

        // Contagem de caracteres
        for (char c : html.toLowerCase().toCharArray()) {
            String s = String.valueOf(c);
            if (contagens.containsKey(s)) {
                contagens.put(s, contagens.get(s) + 1);
            } else if (Character.isLetter(c) && !isVogal(c)) {
                contagens.put("consoante", contagens.get("consoante") + 1);
            }
        }

        // Contagem de padrões
        for (String padrao : padroes) {
            int index = html.indexOf(padrao);
            int count = 0;
            while (index != -1) {
                count++;
                index = html.indexOf(padrao, index + 1);
            }
            contagens.put(padrao, count);
        }

        return contagens;
    }

    private static boolean isVogal(char c) {
        return "aeiouáéíóúàèìòùãõâêîôû".indexOf(c) != -1;
    }

    private static void exibirResultados(Map<String, Integer> contagens, String nomePagina) {
        System.out.printf("a(%d) e(%d) i(%d) o(%d) u(%d) á(%d) é(%d) í(%d) ó(%d) ú(%d) à(%d) è(%d) ì(%d) ò(%d) ù(%d) ã(%d) õ(%d) â(%d) ê(%d) î(%d) ô(%d) û(%d) consoante(%d) <br>(%d) <table>(%d) %s%n",
                contagens.get("a"), contagens.get("e"), contagens.get("i"), contagens.get("o"), contagens.get("u"),
                contagens.get("á"), contagens.get("é"), contagens.get("í"), contagens.get("ó"), contagens.get("ú"),
                contagens.get("à"), contagens.get("è"), contagens.get("ì"), contagens.get("ò"), contagens.get("ù"),
                contagens.get("ã"), contagens.get("õ"), contagens.get("â"), contagens.get("ê"), contagens.get("î"),
                contagens.get("ô"), contagens.get("û"), contagens.get("consoante"), contagens.get("<br>"),
                contagens.get("<table>"), nomePagina);
    }
}