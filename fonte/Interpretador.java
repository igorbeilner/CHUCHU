/**
 *	Autor: Ricardo Parizotto
 *   E-mail: ricardo.dparizotto@gmail.com
 *
 *	Esta classe possui métodos para imprimir e realizar desvios de instruções
 *   e possui o método que "LÊ" todas as instruções e as executa - se possível -.
 *
**/	

import java.util.Scanner;

class Interpretador{

	private Pilha loop;
	private String temp;
	private Memoria mem;
	private int cont;
	private Alu solve;
	private Scanner s;	
	private int flagElse;
	
	public Interpretador(){
		loop=new Pilha();
		mem = new Memoria();
		solve = new Alu();
		s=new Scanner(System.in);
		flagElse = 0;
	}

	private void imprime(String expressao){
		expressao=expressao.trim();
		double f;
		int indiceVet = 0;
		String[] vars=expressao.split(">");
		String[] aux = expressao.split("\\[|\\]|\\>");
		for(int i=0; i<vars.length; i++){
			vars[i]=vars[i].trim();
			aux[i]=aux[i].trim();
			if(mem.verificaVetor(aux[i]) != 0) {
				try {
					indiceVet = Integer.parseInt(aux[i+1]);
				}catch(Exception e) {
					if(mem.variavelExiste(aux[i+1])) {
						indiceVet = (int)mem.variavelView(aux[i+1]);
					} else {
						System.out.println("posicao de vetor invalida");
						System.exit(0);
					}
				}
				f = mem.leVetor(aux[i], indiceVet);
				System.out.print(f);
			}else if(vars[i].charAt(0)=='\''){
				String[] temp=vars[i].split("\'");
				System.out.print(temp[1]);
			} else {
				System.out.print(solve.leExpressao(vars[i], mem));
			}
		}
		System.out.print("\n");
	}

	private int desvio(String[] cmd, int i, char inicio){
		int cont=0;		
		char fim='$';	

		if(inicio=='^')
			fim = 'o';

		if(inicio=='!')
			fim='¬';	
		for(i=i+1;i<cmd.length && cmd[i] != null;i++) {
			if(cmd[i].charAt(0)==inicio)
				cont++;
			else if(cmd[i].charAt(0)==fim) {
					if(cont==0)
						break;
					else
						cont--;
			}
		}
		return i;
	}

	private double ula(String opera, double a, double b) {
		opera = opera.trim();
		if(opera.contains("+")) return a + b;
		if(opera.contains("-")) return a - b;
		if(opera.contains("*")) return a * b;
		if(opera.contains("/")) return a / b;
		if(opera.contains("%")) return a % b;
		else {
			System.out.println("expressao invalida");
			System.exit(0);
		}
		return 0;
	}

	private int verificaIndice(String h) {
		int tam = 0 ;
		try {
			tam = Integer.parseInt(h);
		}catch(Exception e) {
			if(mem.variavelExiste(h)) {
				tam = (int)mem.variavelView(h);
			} else {
				System.out.println("atribuicao invalida999");
				System.exit(0);
			}
		}
		return tam;
	}

	private double verificaatribuicao(String h) {
		h = h.trim();
		double v = 0;
		try {
			v = Double.parseDouble(h);
		}catch(Exception f) {
			if(mem.variavelExiste(h)) {
				v = (int)mem.variavelView(h);
			} else {
				System.out.println("atribuicao invalida1");
				System.exit(0);
			}
		}
		return v;
	}

	public boolean interpreta(String[] cmd){
		int i;
		char instrucao;
		String temp;
		
		//Elimina os espaços de todas as linhas e substitui pelos tokens
		for(i=0; i<cmd.length && cmd[i] != null; i++){
			cmd[i]=cmd[i].trim();
			cmd[i]=cmd[i].replace("end else","o");
			cmd[i]=cmd[i].replace("end if","$");
			cmd[i]=cmd[i].replace("end while","¬");
			cmd[i]=cmd[i].replace("var","#");
			cmd[i]=cmd[i].replace("if","@");
			cmd[i]=cmd[i].replace("while","!");
			cmd[i]=cmd[i].replace("show","§");
			cmd[i]=cmd[i].replace("break","€");
			cmd[i]=cmd[i].replace("get",".");
			cmd[i]=cmd[i].replace("else","^");
			cmd[i]=cmd[i].replace("vet","_");
		}

		for(i=0; i<cmd.length && cmd[i] != null; i++){
			String[] b = cmd[i].split("--");
			String a = cmd[i].replace(" ","");
			a = cmd[i].replace("	","");
			if(a.equals("") == false && b[0].equals("") == false){				
				//Retira o token e salva na instrucao
				instrucao=cmd[i].charAt(0);
				temp=cmd[i].substring(1);
				temp=temp.trim();

				switch(instrucao){
					case'#':
						//atualiza variavel
						String[] newvar=temp.split("=");
						double n = (newvar.length > 1)? solve.leExpressao(newvar[1], mem): 0;
						mem.atualizaVar(n, newvar[0].trim());
						break;
					case '§': 
						//imprime 
						this.imprime(temp);
						break;
					case '_': 
						//vetores
						String[] k = cmd[i].split("\\+|\\*|\\-|\\%|\\/|\\[|\\]|=");
						String[] op = cmd[i].split("\\[|\\]|=");
						int tam = 0;
						double v = 0.0;
						double v2 = 0.0;
						int indice = 0;

						tam = verificaIndice(k[1]);

						if (k.length == 4 || k.length == 5) {
							if(mem.verificaVetor(k[3]) == 0) {
								v = verificaatribuicao(k[3]);
							}					
						}

						if (k.length == 5) {
							if(mem.verificaVetor(k[3]) != 0) {
								indice = indice = verificaIndice(k[4]);
								v = mem.leVetor(k[3], indice);
							} else {
								v2 = verificaatribuicao(k[4]);
								v = ula(op[3], v, v2);						
							}
						}

						else if(k.length == 6) {
							indice = verificaIndice(k[5]);
							v = mem.leVetor(k[4], indice);
							v2 = verificaatribuicao(k[3]);
							v = ula(op[3], v2, v);
						}

						else if(k.length == 7) {
							indice = verificaIndice(k[4]);
							v = mem.leVetor(k[3], indice);
							v2 = verificaatribuicao(k[6]);
							v = ula(op[5], v, v2);
						}

						else if(k.length == 8) {
							indice = verificaIndice(k[4]);
							v = mem.leVetor(k[3], indice);
							indice = verificaIndice(k[7]);
							v2 = mem.leVetor(k[6], indice);
							v = ula(op[5], v, v2);
						}

						int j;
						k[2] = k[2].trim();
						j = mem.verificaVetor(k[2]);
						if(j == 0) {
							mem.criaVetor(k[2], tam);
							if(k.length == 4) {
								System.out.println("tentando criar vetor que já existe");
								System.exit(0);
							}
						} else {
							if(k.length >= 4 && k.length <= 8) {
								mem.atribuiVetor(k[2], tam, v);
							} else {
								System.out.println("metodo de atricuicao invalido");
								System.exit(0);
							}
						}
						break;
					case '@': 
						//if
						if(solve.leIf(temp, mem)==false)
							i=desvio(cmd, i, instrucao);
						else flagElse = 1;
						break;
					case '^':
						//else
						if(flagElse == 1) {
							i=desvio(cmd, i, instrucao);
							flagElse = 0;
						}
						break;
					case 'o':
						//end else;
						break;
					case '!': 
						//loop;
						if(solve.leIf(temp, mem)==true)
							loop.push(i);
						else
							i = this.desvio(cmd, i, instrucao);
						break;
				
					case '¬':
						//end loop;
						if(loop.vazio())				
							i=loop.topo()-1;
						loop.pop();
						break;
		
					case '€': 
						//break;
						i=desvio(cmd, i, '!');
						loop.pop();
						break;
					case '.':
						//scan
						double x = s.nextDouble();
						mem.atualizaVar(x, temp);
						break;
					case '$':
						//end if
						break;
					default: 
						System.out.println(" syntax error "+ (i+1));
						return false;
				}
			}
		}	
	return true;
	}
}