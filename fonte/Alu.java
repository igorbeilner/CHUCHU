/**
 *	Autor: Ricardo Parizotto
 *   E-mail: ricardo.dparizotto@gmail.com
 *
 *	Esta classe possui os métodos capazes de resolver expressões booleanas de 2
 *	operandos e expressões matemáticas com vários operandos. Uma expressao booleana pode
 *	utilizar expressões matematicas. Ex:  a+1 < b
 *
**/	

class Alu{

	public double leExpressao(String expressao, Memoria mem){
		expressao=expressao.trim();
		String[] vars=expressao.split("\\*|\\+|\\-|%|/");
		double[] vec=new double[vars.length]; 
		int i, j, indice = 0;

		for(i=0; i<vars.length && vars[i]!=null; i++){
			vars[i]=vars[i].trim();
			if(mem.variavelExiste(vars[i]))
				vec[i]=mem.variavelView(vars[i]);
			else {				
				String[] aux = vars[i].split("\\[|\\]");
				if(aux.length > 1) {
					aux[0] = aux[0].trim();
					aux[1] = aux[1].trim();
					//System.out.print("aux.>0 "+aux[0]);
					//System.out.println("aux.>1 "+aux[1]);
					if(mem.verificaVetor(aux[0]) != 0){
						try {
							indice = Integer.parseInt(aux[1]);
						}catch(Exception e) {
							if(mem.variavelExiste(aux[1])) {
								indice = (int)mem.variavelView(aux[1]);
							} else {
								System.out.println("indice invalido");
								System.exit(0);
							}
						}
						vec[i]=mem.leVetor(aux[0], indice);
					}
				} else {
					try{
						vec[i]=Double.parseDouble(vars[i]);
					}catch(Exception e){
						vec[i]=0;
					}
				}
			}
		}

		double resultado=vec[0];
		for(i=0, j=1; i<expressao.length(); i++){
			if(expressao.charAt(i)=='+' || expressao.charAt(i)=='-' || expressao.charAt(i) == '%' || expressao.charAt(i)== '*' || expressao.charAt(i)=='/'){
				switch(expressao.charAt(i)){
					case '*': resultado*=vec[j];break;
					case '-': resultado-=vec[j];break;
					case '+': resultado+=vec[j];break;
					case '/': resultado/=vec[j];break;
					case '%': resultado%=vec[j];break;
				}
				j++;			
			}
		}	
		return resultado;
	}

	public boolean leIf(String expressao, Memoria mem){
		expressao = expressao.trim();
		String[] vars = expressao.split(">|<|&|!");
		double[] vec = new double[vars.length]; 
		int i;
		for(i=0; i<vars.length && vars[i]!=null; i++){
			vars[i]=vars[i].trim();
			if(mem.variavelExiste(vars[i]))
				vec[i]=mem.variavelView(vars[i]);
			else 
				vec[i]=this.leExpressao(vars[i], mem);
		}
		for(i=0; i<expressao.length(); i++){
			if(expressao.charAt(i)=='>' || expressao.charAt(i)=='<' || expressao.charAt(i) == '&' || expressao.charAt(i) == '!'){
				switch(expressao.charAt(i)){
					case '>': return (vec[0]>vec[1]);
					case '<': return (vec[0]<vec[1]);
					case '&': return (vec[0]==vec[1]);
					case '!': return (vec[0]!=vec[1]);
				}		
			}
		}	
		return false;
	}

}
