class Vetor {
	private String nome;
	private double[] vvet;

	public Vetor(String name, int indice) {
		this.nome = name;
		this.vvet = new double[indice];
	}
	public String getNome() {
		return this.nome;
	}
	public double getValor(int i) {
		return this.vvet[i];
	}
	public void setValor(int i, double v) {
		this.vvet[i] = v;
	}
}