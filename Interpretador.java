class Interpretador{

	private Pilha loop;
	private String temp;
	private Memoria mem;
	private int cont;
	private Alu solve;	
	
	public Interpretador(){
		loop=new Pilha();
		mem = new Memoria();
		solve = new Alu();
		cont = 0;
	}


//lembrar de passar as expressões sem o token;
	public void imprime(String expressao){
		expressao=expressao.trim();
		String[] vars=expressao.split(">");
		for(int i=0; i<vars.length; i++){
			vars[i]=vars[i].trim();
			if(vars[i].charAt(0)=='\''){
				String[] temp=vars[i].split("\'");
				System.out.print(temp[1]);
			}else System.out.print(solve.leExpressao(vars[i], mem));
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
				double n = (newvar.length > 1)? solve.leExpressao(newvar[1], mem): 0;
				mem.atualizaVar(n, newvar[0].trim());
				break;

			case '§': //imprime 
				this.imprime(temp);
				break;
			case '@': //if
				if(solve.leIf(temp, mem)==false)
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
				if(solve.leIf(temp, mem)==true)
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


