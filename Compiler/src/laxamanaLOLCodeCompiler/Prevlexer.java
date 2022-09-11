package laxamanaLOLCodeCompiler;

public class Prevlexer {
//	String text;
//	int pos = -1;
//	char currChar;
//	
//	Lexer(String text){
//		this.text = text;
////		do {
//			this.nextChar();
////			System.out.println(this.currChar);
////		} while(this.currChar != '\0');
//	}
//	
//	void nextChar() {
//		this.pos = this.pos+1;
//		if(this.pos < this.text.length()) this.currChar = this.text.charAt(pos);
//		else this.currChar = '\0';
//	}
//	
//	Token createNum() {
//		String number = "";
//		int dotCount = 0;
//		
//		while (this.currChar != '\0' && Character.isDigit(this.currChar) || this.currChar != '.') {
//			if(this.currChar=='.') {
//				dotCount+=1;
//				number+=this.currChar;
//			}
//			else number+=this.currChar;
//		}
//		
//		if(dotCount==0) return new Token("NUMBR", Integer.valueOf(number));
//		else return new Token("NUMBR", Float.valueOf(number));
//	}
//	
//	void createTokens() {
//		ArrayList<String> tokens = new ArrayList<String>();
//		
//		while(this.currChar != '\0') {
//			if(this.currChar == '\t') this.nextChar();
//			
//			else if(Character.isDigit(this.currChar)) 
//		}
//	}
}
