package laxamanaLOLCodeCompiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class Lexer {
	String text;
	int pos = 0;
	int line = 1;
	String currString;
	String[] strtok;
	
	//Literals
	private Pattern numbr = Pattern.compile("^-?[0-9]+$");
	private Pattern numbar = Pattern.compile("-?\\d+(\\.\\d+)?");
	private Pattern sumOf = Pattern.compile("\\bSUM\\b");
	private Pattern diffOf = Pattern.compile("\\bDIFF\\b");
	private Pattern produktOf = Pattern.compile("\\bPRODUKT\\b");
	private Pattern quoshuntOf = Pattern.compile("\\bQUOSHUNT\\b");
	private Pattern of = Pattern.compile("\\bOF\\b");
	private Pattern an = Pattern.compile("\\bAN\\b");
	private Pattern I = Pattern.compile("\\bI\\b");
	private Pattern has = Pattern.compile("\\bHAS\\b");
	private Pattern a = Pattern.compile("\\bA\\b");
	private Pattern varIdentifier = Pattern.compile("^([a-zA-Z]+[0-9]*[a-zA-Z]*)+$");
	private Pattern itz = Pattern.compile("\\bITZ\\b");
	private Pattern biggr = Pattern.compile("\\bBIGGR\\b");
	private Pattern smallr = Pattern.compile("\\bSMALLR\\b");
	private Pattern both = Pattern.compile("\\bBOTH\\b");
	private Pattern either = Pattern.compile("\\bEITHER\\b");
	private Pattern won = Pattern.compile("\\bWON\\b");
	private Pattern not = Pattern.compile("\\bNOT\\b");
	private Pattern any = Pattern.compile("\\bANY\\b");
	private Pattern all = Pattern.compile("\\bALL\\b");
	private Pattern saem = Pattern.compile("\\bSAEM\\b");
	private Pattern diffrint = Pattern.compile("\\bDIFFRINT\\b");
	private Pattern o = Pattern.compile("\\bO\\b");
	private Pattern ya = Pattern.compile("\\bYA\\b");
	private Pattern rly = Pattern.compile("\\bRLY\\b");
	private Pattern rlyQuestion = Pattern.compile("\\bRLY\\b\\?");
	private Pattern mebbe = Pattern.compile("\\bMEBBE\\b");
	private Pattern no = Pattern.compile("\\bNO\\b");
	private Pattern wai = Pattern.compile("\\bWAI\\b");
	private Pattern oic = Pattern.compile("\\bOIC\\b");
	private Pattern mkay = Pattern.compile("\\bMKAY\\b");
	private Pattern im = Pattern.compile("\\bIM\\b");
	private Pattern in = Pattern.compile("\\bIN\\b");
	private Pattern out = Pattern.compile("\\bOUTTA\\b");
	private Pattern yr = Pattern.compile("\\bYR\\b");
	private Pattern uppin = Pattern.compile("\\bUPPIN\\b");
	private Pattern nerfin = Pattern.compile("\\bNERFIN\\b");
	private Pattern til = Pattern.compile("\\bTIL\\b");
	private Pattern wile = Pattern.compile("\\bWILE\\b");
	private Pattern visible = Pattern.compile("\\bVISIBLE\\b");
//	private Pattern yarn = Pattern.compile("\"[\\w]+\"");
	private Pattern yarn = Pattern.compile("([\"'])(?:\\\\.|[^\\\\])*?\\1");
	private Pattern R = Pattern.compile("\\bR\\b");
	private Pattern BTW = Pattern.compile("\\bBTW\\b");
//	private Pattern quoted = Pattern.compile("([\"'])(?:\\\\.|[^\\\\])*?\\1");
	
	
	
	Lexer(String text){
		this.text = text;
//		this.strtok = this.text.split(" +|\\t");
		//splits by spaces or tabs, but excludes those inside quotes
		this.strtok = this.text.split("( |\\t)+(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

	}
	
	void updatePosition(String posCurrString) {
		//update index of token
		this.pos+=1;
		
		//update the line number
		if(posCurrString.charAt(0) == '\n') {	//0 index since it's taking 0 as a single token
			this.line+=1;
		}
	}
	
	ArrayList<Token> createTokens() {
		ArrayList<Token> tokens = new ArrayList<Token>();
		boolean hasORLY = false;
		boolean hasOIC = false;
		
		while (this.pos<this.strtok.length) {

			//COMMENT
			if(this.BTW.matcher(this.strtok[this.pos]).matches()) {
				if(this.pos==0) return null;
				break;
			}
			if(this.strtok[this.pos].length()==0) {
				this.pos++;
				continue;
			}
			
			//KEYWORDS
			//I HAS A
			else if(this.I.matcher(this.strtok[this.pos]).matches() && this.has.matcher(this.strtok[this.pos+1]).matches()
					&& this.a.matcher(this.strtok[this.pos+2]).matches()) {
				tokens.add(new Token(this.strtok[this.pos]+this.strtok[this.pos+=1]+this.strtok[this.pos+=1]));
			}
			//R
			else if(this.R.matcher(this.strtok[this.pos]).matches()) tokens.add(new Token(this.strtok[this.pos]));
			
			//MATH
			else if((this.sumOf.matcher(this.strtok[this.pos]).matches() ||
			this.diffOf.matcher(this.strtok[this.pos]).matches() ||
			this.produktOf.matcher(this.strtok[this.pos]).matches() ||
			this.quoshuntOf.matcher(this.strtok[this.pos]).matches())) {
				if(this.of.matcher(this.strtok[this.pos+1]).matches()) {
					tokens.add(new Token(this.strtok[this.pos++]+this.strtok[this.pos]));
//					this.pos+=1;	//add 1 iteration since 'OF' is added on this token
				}
				else {
					new Error("MToken", "'OF' missing after "+this.strtok[this.pos], this.pos, this.line);
					return null;
				}
			}
			//AN
			else if(this.an.matcher(this.strtok[this.pos]).matches()) tokens.add(new Token(this.strtok[this.pos]));
			
			//ITZ
			else if(this.itz.matcher(this.strtok[this.pos]).matches()) tokens.add(new Token(this.strtok[this.pos]));
	
			
			//BOOLEAN			
			//BIGGR
			else if(this.biggr.matcher(this.strtok[this.pos]).matches()) {
				if(this.of.matcher(this.strtok[this.pos+1]).matches()) {
					tokens.add(new Token(this.strtok[this.pos++]+this.strtok[this.pos]));
					//add 1 iteration since 'OF' is added on this token
				}
				else {
					new Error("MToken", "'OF' missing after "+this.strtok[this.pos], this.pos, this.line);
					return null;
				}
			}

			//SMALLR
			else if(this.smallr.matcher(this.strtok[this.pos]).matches()) {
				if(this.of.matcher(this.strtok[this.pos+1]).matches()) {
					tokens.add(new Token(this.strtok[this.pos++]+this.strtok[this.pos]));
					//add 1 iteration since 'OF' is added on this token
				}
				else {
					new Error("MToken", "'OF' missing after "+this.strtok[this.pos], this.pos, this.line);
					return null;
				}
			}
			
			//BOTH OF, BOTH SAEM
			else if(this.both.matcher(this.strtok[this.pos]).matches()) {
				if(this.of.matcher(this.strtok[this.pos+1]).matches() || this.saem.matcher(this.strtok[this.pos+1]).matches()) {
					tokens.add(new Token(this.strtok[this.pos++]+this.strtok[this.pos]));
					//add 1 iteration since 'OF' is added on this token
				}
				else {
					new Error("MToken", "'OF' or 'SAEM' missing after "+this.strtok[this.pos], this.pos, this.line);
					return null;
				}
			}
			
			//EITHER
			else if(this.either.matcher(this.strtok[this.pos]).matches()) {
				if(this.of.matcher(this.strtok[this.pos+1]).matches()) {
					tokens.add(new Token(this.strtok[this.pos++]+this.strtok[this.pos]));
					//add 1 iteration since 'OF' is added on this token
				}
				else {
					new Error("MToken", "'OF' missing after "+this.strtok[this.pos], this.pos, this.line);
					return null;
				}
			}
			
			//WON
			else if(this.won.matcher(this.strtok[this.pos]).matches()) {
				if(this.of.matcher(this.strtok[this.pos+1]).matches()) {
					tokens.add(new Token(this.strtok[this.pos++]+this.strtok[this.pos]));
					//add 1 iteration since 'OF' is added on this token
				}
				else {
					new Error("MToken", "'OF' missing after "+this.strtok[this.pos], this.pos, this.line);
					return null;
				}
			}

			//NOT
			else if(this.not.matcher(this.strtok[this.pos]).matches()) tokens.add(new Token(this.strtok[this.pos]));
			
			//ANY
			else if(this.any.matcher(this.strtok[this.pos]).matches()) {
				if(this.of.matcher(this.strtok[this.pos+1]).matches()) {
					tokens.add(new Token(this.strtok[this.pos++]+this.strtok[this.pos]));
					//add 1 iteration since 'OF' is added on this token
				}
				else {
					new Error("MToken", "'OF' missing after "+this.strtok[this.pos], this.pos, this.line);
					return null;
				}
			}
			
			//ALL
			else if(this.all.matcher(this.strtok[this.pos]).matches()) {
				if(this.of.matcher(this.strtok[this.pos+1]).matches()) {
					tokens.add(new Token(this.strtok[this.pos++]+this.strtok[this.pos]));
					//add 1 iteration since 'OF' is added on this token
				}
				else {
					new Error("MToken", "'OF' missing after "+this.strtok[this.pos], this.pos, this.line);
					return null;
				}
			}
			
			//DIFFRINT
			else if(this.diffrint.matcher(this.strtok[this.pos]).matches()) tokens.add(new Token(this.strtok[this.pos]));
			
			
			
			
			
			//FLOW CONTROL
			//O RLY?
			else if(this.o.matcher(this.strtok[this.pos]).matches()) {

				//TODO: checks if ended with newline
				System.out.println(this.strtok[this.pos+1]);
				if(this.rlyQuestion.matcher(this.strtok[this.pos+1]).matches()) {
					tokens.add(new Token(this.strtok[this.pos++]+this.strtok[this.pos]));
					//add 1 iteration since 'OF' is added on this token
					hasORLY = true;
				}
				//checks if ended with comma
				else if(this.rlyQuestion.matcher(this.strtok[this.pos+1].substring(0, (this.strtok[this.pos+1].length()-1))).matches()) {
					if(this.strtok[this.pos+1].substring((this.strtok[this.pos+1].length()-1), (this.strtok[this.pos+1].length())).compareTo(",")==0) {
						tokens.add(new Token(this.strtok[this.pos++]+this.strtok[this.pos].substring(0, (this.strtok[this.pos].length()-1))));
						//add 1 iteration since 'OF' is added on this token
						hasORLY = true;
					}
					else {
						new Error("MToken", "',' missing after "+this.strtok[this.pos], this.pos, this.line);
						return null;
					}
				}
				else {
					new Error("MToken", "'RLY?' missing after "+this.strtok[this.pos], this.pos, this.line);
					return null;
				}
			}
			
			//YA RLY
			else if(this.ya.matcher(this.strtok[this.pos]).matches()) {

				//TODO: checks if ended with newline
				if(this.rly.matcher(this.strtok[this.pos+1]).matches()) {
					tokens.add(new Token(this.strtok[this.pos++]+this.strtok[this.pos]));
					//add 1 iteration since 'OF' is added on this token
				}
				//checks if ended with comma
				else if(this.rly.matcher(this.strtok[this.pos+1].substring(0, (this.strtok[this.pos+1].length()-1))).matches()) {
					if(this.strtok[this.pos+1].substring((this.strtok[this.pos+1].length()-1), (this.strtok[this.pos+1].length())).compareTo(",")==0) {
						tokens.add(new Token(this.strtok[this.pos++]+this.strtok[this.pos].substring(0, (this.strtok[this.pos].length()-1))));
						//add 1 iteration since 'OF' is added on this token
					}
					else {
						new Error("MToken", "',' missing after "+this.strtok[this.pos], this.pos, this.line);
						return null;
					}
				}
				else {
					new Error("MToken", "'RLY' missing after "+this.strtok[this.pos], this.pos, this.line);
					return null;
				}
			}
			
			//MEBBE
			//TODO: checks if ended with newline
			else if(this.mebbe.matcher(this.strtok[this.pos]).matches()) {
				tokens.add(new Token(this.strtok[this.pos]));
			}
			//checks if ended with comma
			else if(this.mebbe.matcher(this.strtok[this.pos].substring(0, (this.strtok[this.pos].length()-1))).matches()) {
				if(this.strtok[this.pos].substring((this.strtok[this.pos].length()-1), (this.strtok[this.pos].length())).compareTo(",")==0) {
					tokens.add(new Token(this.strtok[this.pos].substring(0, (this.strtok[this.pos].length()-1))));
				}
				else {
					new Error("MToken", "',' missing after "+this.strtok[this.pos], this.pos, this.line);
					return null;
				}
			}

			
			//NO WAI
			else if(this.no.matcher(this.strtok[this.pos]).matches()) {
				//TODO: checks if ended with newline
				if(this.wai.matcher(this.strtok[this.pos+1]).matches()) {
					tokens.add(new Token(this.strtok[this.pos++]+this.strtok[this.pos]));
					//add 1 iteration since 'OF' is added on this token
				}
				//checks if ended with comma
				else if(this.wai.matcher(this.strtok[this.pos+1].substring(0, (this.strtok[this.pos+1].length()-1))).matches()) {
					if(this.strtok[this.pos+1].substring((this.strtok[this.pos+1].length()-1), (this.strtok[this.pos+1].length())).compareTo(",")==0) {
						tokens.add(new Token(this.strtok[this.pos++]+this.strtok[this.pos].substring(0, (this.strtok[this.pos].length()-1))));
						//add 1 iteration since 'OF' is added on this token
					}
					else {
						new Error("MToken", "',' missing after "+this.strtok[this.pos], this.pos, this.line);
						return null;
					}
				}
				else {
					new Error("MToken", "'WAI' missing after "+this.strtok[this.pos], this.pos, this.line);
					return null;
				}
			}
			
			//OIC
			//TODO: checks if ended with newline
			else if(this.oic.matcher(this.strtok[this.pos]).matches()) {
				tokens.add(new Token(this.strtok[this.pos]));
				hasOIC = true;
			}
			
			//checks if ended with comma
//			else if(this.oic.matcher(this.strtok[this.pos].substring(0, (this.strtok[this.pos].length()-1))).matches()) {
//				if(this.strtok[this.pos].substring((this.strtok[this.pos].length()-1), (this.strtok[this.pos].length())).compareTo(",")==0) {
//					tokens.add(new Token(this.strtok[this.pos].substring(0, (this.strtok[this.pos].length()-1))));
//					hasOIC = true;
//				}
//				else {
//					new Error("MToken", "',' missing after "+this.strtok[this.pos], this.pos, this.line);
//					return null;
//				}
//			}
			
			else if(this.mkay.matcher(this.strtok[this.pos]).matches()) tokens.add(new Token(this.strtok[this.pos]));
			
			//LOOPS
			//IM IN/OUTTA YR
			else if(this.im.matcher(this.strtok[this.pos]).matches()) {
				if(this.in.matcher(this.strtok[this.pos+1]).matches()) {
					if(this.yr.matcher(this.strtok[this.pos+2]).matches())
						tokens.add(new Token(this.strtok[this.pos++].concat(this.strtok[this.pos++]).concat(this.strtok[this.pos])));
				}
				else if(this.out.matcher(this.strtok[this.pos+1]).matches()) {
					if(this.yr.matcher(this.strtok[this.pos+2]).matches())
						tokens.add(new Token(this.strtok[this.pos++].concat(this.strtok[this.pos++]).concat(this.strtok[this.pos])));
				}
				else {
					new Error("MToken", "'IN' or 'YR' missing after "+this.strtok[this.pos], this.pos, this.line);
					return null;
				}
			}
			
			else if(this.uppin.matcher(this.strtok[this.pos]).matches() || 
					this.nerfin.matcher(this.strtok[this.pos]).matches() ||
					this.yr.matcher(this.strtok[this.pos]).matches() ||
					this.til.matcher(this.strtok[this.pos]).matches() ||
					this.wile.matcher(this.strtok[this.pos]).matches())
				tokens.add(new Token(this.strtok[this.pos]));
			
						
			//VISIBLE
			else if(this.visible.matcher(this.strtok[this.pos]).matches()) 
				tokens.add(new Token(this.strtok[this.pos]));
		
			

			//IDENTIFIERS
			//VAR Identifier
			else if(this.varIdentifier.matcher(this.strtok[this.pos]).matches()) {
				if((this.pos-2)>=0 && this.strtok[this.pos-1].compareTo("YR")==0 &&
						(this.strtok[this.pos-2].compareTo("IN")==0 ||
						this.strtok[this.pos-2].compareTo("OUTTA")==0)) 
					tokens.add(new Token("LABEL", this.strtok[this.pos]));
				else tokens.add(new Token("VARIABLE", this.strtok[this.pos]));
			}
			
			
			
			//LITERALS
			//NUMBR
			else if(this.numbr.matcher(this.strtok[this.pos]).matches()) {
				tokens.add(new Token("NUMBR", Integer.valueOf(this.strtok[this.pos]), "NUMBR"));
			}
			//NUMBAR
			else if(this.numbar.matcher(this.strtok[this.pos]).matches()) {
				tokens.add(new Token("NUMBAR", Float.valueOf(this.strtok[this.pos]), "NUMBAR"));
			}
			//YARN
			else if(this.yarn.matcher(this.strtok[this.pos]).matches()) {
				String yarnVal="";
				boolean hasEscapeChar=false;
				HashMap<Character, Character> escapeCharVal = new HashMap<>();
				escapeCharVal.put(')', '\n');
				escapeCharVal.put('>', '\t');
//				escapeCharVal.put('o', '\g');
				
				//loop through the string
				for(int i=1; i<this.strtok[this.pos].length()-1; i++) {
					//checks for escape character
					if(hasEscapeChar) {
						//either it has a value in the escapeCharVal HashMap or it will just use that character
						yarnVal+=escapeCharVal.getOrDefault(this.strtok[this.pos].charAt(i), this.strtok[this.pos].charAt(i));
						hasEscapeChar=false;
					}
					else {
						if(this.strtok[this.pos].charAt(i)==':') {
							hasEscapeChar=true;
						}
						else yarnVal+=this.strtok[this.pos].charAt(i);
					}
				}
				tokens.add(new Token("YARN", yarnVal, "YARN"));

			}
			
			
			else {
				
				new Error("IToken", this.strtok[this.pos], this.pos, this.line);
				return null;
			}
			
			this.updatePosition(this.strtok[this.pos]);
		}
		//OIC Missing error
		if(hasORLY) {
			if(!hasOIC)	new Error("MToken", "'OIC' missing after "+this.strtok[this.pos], this.pos, this.line);
		}
		
		//end of file signal
		tokens.add(new Token("EOF"));
		return tokens;
	}
	
}
