import java.util.*;
import java.io.*;

class Hora{ //hora tem 2 atributos, hora e minuto. com o parseHora, recebemos uma string, e colocamos o scanner pra analisar ela, usando o delimiter. ele separa ela pelo :, permitindo a separacao da string pelos valores que precisamos, como se fosse um split(). e entao fica facil pegar os valores usando o scanner.next. Usamos o construtor da propria classe direto no retorno da funcao.
	private int hora;
	private int minuto;

	public Hora(int hora, int minuto){
		this.hora = hora;
		this.minuto = minuto;
	}

	public static Hora parseHora(String s){
		int hora;
		int minuto;
		Scanner scanner = new Scanner(s);
		scanner.useDelimiter("[:|-]"); //para que o scanner possa ler 10:30 e 10-30 

		hora = scanner.nextInt();
		minuto = scanner.nextInt();

		scanner.close();
		return new Hora(hora, minuto);
	}

	public String formatar(){
		return String.format("%02d:%02d", this.hora, this.minuto);
	}
}

class Data{ //extremamente similar a classe acima, mas agora tambem temos o formatar(), que é essencialmenrte o processo reverso do que fazemos, com os valores que desejamso. a ideia é imprimir os valores separados pelo -, assim como foi recebido na entrada, mas dessa vez ja temos os dados extraidos da string original, entao fica facil.
	private int ano;
	private int mes;
	private int dia;

	public Data(int ano, int mes, int dia){
		this.ano = ano;
		this.mes = mes;
		this.dia = dia;
	}

	public static Data parseData(String s){
		int ano;
		int mes;
		int dia;
		Scanner scanner = new Scanner(s);
		scanner.useDelimiter("-");

		ano = scanner.nextInt();
		mes = scanner.nextInt();
		dia = scanner.nextInt();

		scanner.close();
		return new Data(ano, mes, dia);
	}

	public String formatar(){
		return String.format("%02d/%02d/%04d", this.dia, this.mes, this.ano);
	}
}

class Restaurante{ //uma classe com varios parametros/atributos que extrai da string obtida do csv (uma linha por vez) todos os atributos e os formata da forma necessária usando o parseRestaurante, parseBool e parsePreco para auxiliar a formatacao de atributos que nao vêm da forma ideal
	private int id;
	private String nome;
	private String cidade;
	private int capacidade;
	private float avaliacao;
	private String[] tiposCozinha;
	private int faixaPreco;
	private String horario;
	private Data dataAbertura;
	private boolean aberto;

	public Restaurante(int id, String nome, String cidade, int capacidade, float avaliacao, String[] tiposCozinha, int faixaPreco, String horario, Data dataAbertura, boolean aberto){
		this.id = id;
		this.nome = nome;
		this.cidade = cidade;
		this.capacidade = capacidade;
		this.avaliacao = avaliacao;
		this.tiposCozinha = tiposCozinha;
		this.faixaPreco = faixaPreco;
		this.horario = horario;
		this.dataAbertura = dataAbertura;
		this.aberto = aberto;
	}

	public static Restaurante parseRestaurante(String s){
		Scanner scanner = new Scanner(s);
		scanner.useDelimiter(",");

		int id = scanner.nextInt(); //o scanner para assim que encontra uma virgula. como o primeiro elemento é o id(int) podemos pegar o id facilmente dessa forma. e então, fazemos o mesmo com todo o resto
		String nome = scanner.next(); //o proximo elemento na string a ser processada é o nome
		String cidade = scanner.next();
		int capacidade = scanner.nextInt();
		float avaliacao = scanner.nextFloat();
		String[] tiposCozinha = parseCozinha(scanner.next());
		int faixaPreco = parsePreco(scanner.next());
		String horario = scanner.next();
		Data dataAbertura = Data.parseData(scanner.next());
		boolean aberto = parseBool(scanner.next());

		scanner.close();
		return new Restaurante(id, nome, cidade, capacidade, avaliacao, tiposCozinha, faixaPreco, horario, dataAbertura, aberto);
	}

	public int getId(){
		return this.id;
	}

	public static boolean parseBool(String s){
		return s.equals("true");
	}

	public static String[] parseCozinha(String s){
		int i = 0;
		String[] array = new String[200];
		Scanner scanner = new Scanner(s);
		scanner.useDelimiter(";");
		while(scanner.hasNext()){
			array[i] = scanner.next();
			i++;
		}
		scanner.close();
		return array;
	}

	public static int parsePreco(String s){
		int contador=0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '$') {
				contador++;
			}
		}
		return contador;
	}

	private String formatarCozinhas() { //exibe todo o array
		String res = "[";
		for (int i = 0; i < tiposCozinha.length; i++) {
			if (tiposCozinha[i] != null) {
				if (i > 0) res += ",";
				res += tiposCozinha[i];
			}
		}
		return res + "]";
	}

	private String formatarPreco() { //transforma o numero novamente em $
		String res = "";
		for (int i = 0; i < faixaPreco; i++) res += "$";
		return res;
	}

	public String formatar() {
		return String.format("[%d ## %s ## %s ## %d ## %.1f ## %s ## %s ## %s ## %s ## %b]",  //%.1f remove os zeros excessivos da avaliação
			this.id, 
			this.nome, 
			this.cidade, 
			this.capacidade, 
			this.avaliacao, 
			this.formatarCozinhas(), 
			this.formatarPreco(), 
			this.horario,
			this.dataAbertura.formatar(), 
			this.aberto);
	}
}

class ColecaoRestaurantes{
	private int tamanho;
	private Restaurante[] restaurantes;

	public ColecaoRestaurantes(int tamanho, Restaurante[] restaurantes){
		this.tamanho = tamanho;
		this.restaurantes = restaurantes;
	}

	public static ColecaoRestaurantes lerCsv(){
		int tam=0;
		Restaurante[] rests = new Restaurante[5000];
		try {
			Scanner arquivo = new Scanner(new File("/tmp/restaurantes.csv"));
			String cabecalho = arquivo.nextLine();
			while(arquivo.hasNextLine()){
				String linha = arquivo.nextLine();
				Restaurante novo = Restaurante.parseRestaurante(linha); //nao precisa de new pq ja tem dentro de parseRest.
				rests[tam] = novo;
				tam++;
			}		
		} catch (Exception e) {
            e.printStackTrace();
        }
		return new ColecaoRestaurantes(tam, rests);
	}

	public Restaurante getRestauranteById(int id){ //buscamos o restaurante que queremos baseado no id, passando por todo o array restaurantes até encontrarmos um id correspondente. em seguida, guardamos a posicao dele em z e retornamos o restaurante na posicao.
		int z=0;
		for(int i=0; i<tamanho; i++){
			if(restaurantes[i].getId() == id){
				z = i;
			}
		}
		if(z==0){
			return null;
		}else{
			return restaurantes[z];
		}
	}
}


class Processa1{
	public static void main(String[] args){
		ColecaoRestaurantes cr = ColecaoRestaurantes.lerCsv();
		Scanner sc = new Scanner(System.in);
		int entrada = sc.nextInt();
		while(entrada > 0){
			Restaurante r = cr.getRestauranteById(entrada);
			if(r!=null){
        		System.out.println(r.formatar());
    		}
			entrada = sc.nextInt();
		}
		sc.close();
	}
}
