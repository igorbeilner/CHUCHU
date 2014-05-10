class variable_vec{
	variable[] array;
	private int last;
	private pilha loop;
	String temp;

	public variable_vec(){
		array=new variable[1000];
		last = 0;
		loop=new pilha();
	}
	
	public int attVar(double x, String h){
		int i;
		for(i=0; i<last; i++){
			if((array[i].nome).equals(h)){
				array[i].set_valor(x);
				return 1;			
			}
		}
		array[i]=new variable(x, h);
		last++;
		return 0;
	}

	public double varView(String h){
		int i;
		for(i=0; i<last;i++){
			if(array[i] != null && (array[i].nome).equals(h))
				return array[i].get_valor();
		}
		return 12312312;
	}

	public double leExpressao(String expressao){
		expressao=expressao.trim();
		String[] vars=expressao.split("\\*|\\+|\\-|%|/");
		double[] vec=new double[vars.length]; 
		int i, j;

		for(i=0; i<vars.length && vars[i]!=null; i++){
			vars[i]=vars[i].trim();
			if(this.varView(vars[i])!=12312312)
				vec[i]=this.varView(vars[i]);
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


	public void intepretador(String code){
		String[] cmd=code.split(";");
		int i;

		for(i=0; i<cmd.length; i++)
			cmd[i]=cmd[i].trim();

		for(i=0; i<cmd.length; i++){
			switch(cmd[i].charAt(0)){
			case'#': 
				temp=cmd[i].replace("#", "");
				temp=temp.trim();
				String[] newvar=temp.split("=");
				double n = (newvar.length > 1)? this.leExpressao(newvar[1]): 0;
				attVar(n, newvar[0].trim());
				break;
			case '§': 
				temp=cmd[i].replace("§", "");
				this.imprime(temp);
				break;
			case '@': 
				temp=cmd[i].replace("@", "");
				if(leIf(temp)==false)
					for(;i<cmd.length;i++)
					if(cmd[i].charAt(0)=='$')
						break;
			case '!':
				temp=cmd[i].replace("!", "");
				if(leIf(temp)==true)
					loop.push(i);
				else
					for(;i<cmd.length;i++)
						if(cmd[i].charAt(0)=='¬')
							break;
				break;
			case '¬':
				if(loop.vazio())				
					i=loop.topo();
				loop.pop();
				break;
			case '$':
				break;
			}		
		}
	}

	public boolean leIf(String expressao){
		expressao=expressao.trim();
		String[] vars=expressao.split(">|<|&");
		double[] vec=new double[vars.length]; 
		int i, j;

		for(i=0; i<vars.length && vars[i]!=null; i++){
			vars[i]=vars[i].trim();
			if(this.varView(vars[i])!=12312312)
				vec[i]=this.varView(vars[i]);
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

}


