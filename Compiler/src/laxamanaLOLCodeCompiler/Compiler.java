package laxamanaLOLCodeCompiler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Compiler {
	public static void main(String[] args) throws IOException {
		TokenTable tokenTable = new TokenTable();
		tokenTable.setValue("WIN", new LOLNumber(1, "TROOF"));
		tokenTable.setValue("FAIL", new LOLNumber(0, "TROOF"));

		expression();
		
		//multi-line
		ArrayList<Token> tokens = new ArrayList<Token>();
		String tempStr=null, theLine=null;
		boolean addMore=false;
		boolean stillComment=false;
		
				for(String i : tokenTable.getKeySet()) System.out.println("key: "+i+" value: "+tokenTable.getValue(i).getValue());
				for(String i : tokenTable.getKeySetString()) System.out.println("key: "+i+" value: "+tokenTable.getStringValue(i));
		
				System.out.print("LOLCode> ");
		
		//get text file path
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		String fp = bufferedReader.readLine();
		System.out.println(fp);
		//src/filetrial/sum.txt
		String input = null;
		try(
				FileInputStream file = new FileInputStream(fp);
				BufferedReader br =
		           new BufferedReader(new InputStreamReader(file)))
		   {
		      StringBuilder sb = new StringBuilder();
		      String line;
		      while(( line = br.readLine()) != null ) {
		         sb.append( line );
		         sb.append( '\n' );
		      }
		      input = sb.toString();
		      
		   }catch(Exception e) {
			   System.out.println("Could not find the file specified.");
			   System.exit(0);
		   }
		String lines[] = input.split("\\r?\\n+\\t*");
		//check HAI and KTHXBYE
		if(!lines[0].startsWith("HAI")) new Error("MToken", "Missing 'HAI'", 0, 0);
		if(lines[lines.length-1].compareTo("KTHXBYE")!=0) new Error("MToken", "Missing 'KTHXBYE'", 0, lines.length);
		
		for(int j=1; j<(lines.length-1); j++) {
			//TODO insert keyword for loops and if else in the first part of next line
			//TODO make keyword list of these keywords
			if(lines[j].contains("TLDR")) {
				stillComment = false;
				continue;
			}
			else if(lines[j].contains("OBTW")) {
				stillComment = true;
			}
			
			
			if(!stillComment) {
				if(lines[j].contains("IM IN YR") || lines[j].contains("O RLY?")) {
					tempStr = lines[j]+" ";
					addMore = true;
				}
				//IF-ELSE or Loop
				else if(addMore) {
					tempStr = tempStr + lines[j] + " ";
					
					if(lines[j].contains("IM OUTTA YR") || lines[j].contains("OIC")) {
						addMore = false;
						theLine = tempStr;
					}
				}
				//single line
				else theLine = lines[j];
				
				//single line
				if(!addMore) {
					Lexer lex = new Lexer(theLine);
					tokens = lex.createTokens();
					
					if(tokens!=null) {
						
						//prints tokens created
						for (Token i:tokens) {
							System.out.print(i.type + " ");
							if(i.getValue()!=null) System.out.print(i.getValue()+ " ");
							else if(i.getVarValue()!=null) System.out.print(i.getVarValue()+" ");
						}	
						System.out.println();
						
						Parser parser = new Parser(tokens);
						Operation syntaxTree = parser.Parse();
						
						Interpreter interpreter = new Interpreter(syntaxTree, tokenTable);
						LOLNumber res = null;
						res = interpreter.visit(syntaxTree);
//						System.out.println("ANS: "+res.getValue());	
					}
				
				
				}
			}
			
		}
		
	}

	private static void expression() {
		// TODO Auto-generated method stub
		
	}
}
