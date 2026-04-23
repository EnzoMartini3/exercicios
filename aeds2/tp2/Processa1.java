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
		scanner.useDelimiter(":"); //"10:30"

		hora = scanner.nextInt();
		minuto = scanner.nextInt();

		scanner.close();
		return new Hora(hora, minuto);
	}

	public String formatar(){
		return String.format("%02d:%02d", this.hora, this.minuto); // "HH:mm"
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

	public static String formatar(){
		return String.format("%02d-%02d-%02d", this.ano, this.mes, this.dia);
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
	private Hora horario;
	private Data dataAbertura;
	private boolean aberto;

	public Restaurante(int id, String nome, String cidade, int capacidade, float avaliacao, /*tiposCozinha*/, int faixaPreco, Hora horario, Data dataAbertura, boolean aberto){
		this.id = id;
		this.nome = nome;
		this.cidade = cidade;
		this.capacidade = capacidade;
		this.avaliacao = avaliacao;
		//this.tiposCozinha = tiposCozinha
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
		//tiposcuzinho
		int faixaPreco = parsePreco(scanner.next());
		Hora horario = Hora.parseHora(scanner.next());
		Data dataAbertura = Data.parseData(scanner.next());
		boolean aberto = parseBool(scanner.next());

		scanner.close();
		return new Restaurante(id, nome, cidade, capacidade, avaliacao, /*tiposCozinha*/, faixaPreco, horario, dataAbertura, aberto);
	}

	public static boolean parseBool(String s){
		return s.equals("true");
	}

	public static int parsePreco(String s){
		int i=0;
		int contador=0;
		while(s.charAt(contador)=='$'){
			i++;
			contador++;
		}
		return i;
	}

	public static String formatar(){
		return String.format("[%d ## %s ## %s]", this.id, this.nome, this.horaAbertura.formatar());
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
		Scanner arquivo = new Scanner(new File("/tmp/restaurantes.csv"));
		String cabecalho = arquivo.nextLine();
		int tam=0;
		Restaurante[] rests = new Restaurante[5000];
		while(arquivo.hasNextLine()){
			String linha = arquivo.nextLine();
			Restaurante novo = Restaurante.parseRestaurante(linha) //nao precisa de new pq ja tem dentro de parseRest.
			rests[tam] = novo;
			tam++;
		}
		return new ColecaoRestaurantes(tam, rests);
	}
}

class Processa1{
	public static void main(String[] args){}
	ColecaoRestaurantes cr = lerCsv();
}
