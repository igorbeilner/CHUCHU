class interpretador{

	private pilha loop;
	String temp;
	private memoria mem;
	int cont;

	public interpretador(){
		loop=new pilha();
		cont = 0;
		mem = new memoria();
	}
	
	public double leExpressao(String expressao){
		expressao=expressao.trim();
		String[] vars=expressao.split("\\*|\\+|\\-|%|/");
		double[] vec=new double[vars.length]; 
		int i, j;

		for(i=0; i<vars.length && vars[i]!=null; i++){
			vars[i]=vars[i].trim();
			if(mem.variavelView(vars[i])!=12312312)
				vec[i]=mem.variavelView(vars[i]);
			else 
				vec[i]=Double.parseDouble(vars[i]);
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

	public boolean leIf(String expressao){
		expressao=expressao.trim();
		String[] vars=expressao.split(">|<|&");
		double[] vec=new double[vars.length]; 
		int i, j;

		for(i=0; i<vars.length && vars[i]!=null; i++){
			vars[i]=vars[i].trim();
			if(mem.variavelView(vars[i])!=12312312)
				vec[i]=mem.variavelView(vars[i]);
			else 
				vec[i]=leExpressao(vars[i]);
		}
		boolean resultado;
		for(i=0; i<expressao.length(); i++){
			if(expressao.charAt(i)=='>' || expressao.charAt(i)=='<' || expressao.charAt(i) == '&'){
				switch(expressao.charAt(i)){
					case '>': return (vec[0]>vec[1]);
					case '<': return (vec[0]<vec[1]);
					case '&': return (vec[0]==vec[1]);
				}		
			}
		}	
		return false;
	}

//lembrar de passar as expressões sem o token;
	public void imprime(String expressao){
		expressao=expressao.trim();
		String[] vars=expressao.split(">");
		for(int i=0; i<vars.length; i++){
			vars[i]=vars[i].trim();
			if(vars[i].charAt(0)=='\"'){
				String[] temp=vars[i].split("\"");
				System.out.print(temp[1]);
			}else System.out.print(this.leExpressao(vars[i]));
		}
		System.out.print("\n");
	}


	public void interpreta(String code){
		String[] cmd=code.split(";");
		int i;
		char instrucao;

		for(i=0; i<cmd.length; i++)
			cmd[i]=cmd[i].trim();

		for(i=0; i<cmd.length; i++){

			instrucao=cmd[i].charAt(0);
			temp=cmd[i].substring(1);
			temp=temp.trim();

			switch(instrucao){
			case'#':
 
				//atualiza variavel
				String[] newvar=temp.split("=");
				double n = (newvar.length > 1)? this.leExpressao(newvar[1]): 0;
				mem.atualizaVar(n, newvar[0].trim());
				break;

			case '§': //imprime 
				this.imprime(temp);
				break;
			case '@': //if
				if(leIf(temp)==false)
					for(;i<cmd.length;i++)
						if(cmd[i].charAt(0)=='@')
							cont++;
						else if(cmd[i].charAt(0)=='$')
								if(cont==0)
									break;
								else
									cont--;
				break;
			case '!': //loop;
				if(leIf(temp)==true)
					loop.push(i);
				else
					for(i=i+1;i<cmd.length;i++)
						if(cmd[i].charAt(0)=='!')
							cont++;
						else if(cmd[i].charAt(0)=='¬')
								if(cont==0)
									break;
								else
									cont--;
				break;
			case '¬':	//end loop;
				if(loop.vazio())				
					i=loop.topo()-1;
				loop.pop();
				break;
			case '€': //break;
				while(cmd[i].charAt(0)!='¬')
					i++;
				break;
			}	
		}
	}
}


