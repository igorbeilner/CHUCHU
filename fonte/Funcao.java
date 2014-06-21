class Funcao {
	int indice;
	String nomeFuncao;

	public Funcao(String name, int i) {
		this.nomeFuncao = name;
		this.indice = i;
	}
	public void setIndice(int i) {
		this.indice = i;
	}
	public void setNomeFuncao(String nome) {
		this.nomeFuncao = nome;
	}
	public int getIndice() {
		return this.indice;
	}
	public String getNomeFuncao(){
		return this.nomeFuncao;
	}
}