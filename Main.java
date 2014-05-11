class Main{
	public static void main(String args[]){
		Interpretador x=new Interpretador();
	
	//programa da tabuada
		//x.interpreta("#y = 1; !y < 11; §'tabuado do ' > y; #x= 1;!x < 11;   §x*y;  #x=x+1;  ¬; #y=y+1;  ¬;");
	
		//x.interpreta("#y = 2; @y < 4; §4; @y< 3; § 3; @ y < 2; § 2; $; $; $;");  
		
		x.interpreta("#x; !x!10; §x; #x=x+1; ¬;");
	/*String cmdln="    bosta+     coc      o-lixo*";
		String ln[]=cmdln.split("[a-z0-9 ]+");
		for(int i=0; i<ln.length; i++)
			System.out.println(ln[i]+" "+i);
		int i;
*/
	}
}
