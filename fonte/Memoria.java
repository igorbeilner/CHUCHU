/**
 *	Autor: Ricardo Parizotto
 *   E-mail: ricardo.dparizotto@gmail.com
 *
 *	Esta classe representa uma memória de variáveis. É basicamente um vetor de variáveis.
 *	A inserção de valor em uma variável não depende de sua declaração.
 *
**/	

class Memoria{
	public Variable[] array;
	public Vetor[] vet;
	public int topo;
	public int last;

	public Memoria(){
		array=new Variable[1000];
		vet = new Vetor[1000];
		last = 0;
		topo = 0;
	}

	public int atualizaVar(double x, String h){
		int i;
		for(i=0; i<last; i++){
			if((array[i].nome).equals(h)){
				this.array[i].set_valor(x);
				return 1;			
			}
		}
		array[i]=new Variable(x, h);
		this.last++;
		return 0;
	}

	public void atribuiVetor(String nome, int i, double valor) {
		int j;
		j = verificaVetor(nome);
		if(j != 0) {
			vet[j].setValor(i, valor);
		} else {
			System.out.println("ERRO: vetor nao existe");
			System.exit(0);
		}
	}

	public double leVetor(String nome, int i) {
		int j;
		j = verificaVetor(nome);
		if(j != 0) {
			return vet[j].getValor(i);
		} else {
			System.out.println("ERRO: vetor nao existe");
			System.exit(0);
		}
		return 0;
	}

	public void criaVetor(String nome, int tamanho) {
		if(verificaVetor(nome) == 0 && (topo+1) < vet.length) {
			topo++;
			this.vet[topo] = new Vetor(nome, tamanho);
		} else {
			System.out.println("ERRO: vetor duplicado");
			System.exit(0);
		}
	}

	public int verificaVetor(String h) {
		h=h.trim();
		int i;
		for(i = 1; i <= topo; i++) {
			if((vet[i].getNome()).equals(h)) return i;
		}
		return 0;
	}

	public double variavelView(String h){
		int i;
		for(i=0; i<last;i++){
			if(array[i] != null && (array[i].nome).equals(h))
				return array[i].get_valor();
		}
		return 0;
	}

	public boolean variavelExiste(String h){
		int i;
		for(i=0; i<last; i++)
			if((array[i].nome).equals(h))
				return true;
		return false;
	}

}
