package laxamanaLOLCodeCompiler;

import java.util.ArrayList;

public class Parser {
	ArrayList<Token> tokenList = new ArrayList<Token>();
	int pos = 0;
	int line = 1;
	Token currToken;
	Operation operationOutput;
	boolean hasVisible=false;
	
	
	Parser(ArrayList<Token> tokenList){
		this.tokenList = tokenList;
		this.currToken = this.tokenList.get(0);
	}
	
	Operation Parse() {
		this.operationOutput = this.statement();
		//prints the operations created
//		if(this.operationOutput.getOptr()!=null) this.operationOutput.printOperation();
		return this.operationOutput;
	}
	
	void regressPosition() {
		//update index of token
		this.pos-=1;
		
		if(this.pos < this.tokenList.size()) this.currToken = this.tokenList.get(this.pos);
		else new Error("SynErr", "update", this.pos, this.line);
	}
	
	void updatePosition() {
		//update index of token
		this.pos+=1;
		
		if(this.pos < this.tokenList.size()) this.currToken = this.tokenList.get(this.pos);
		else new Error("SynErr", "update", this.pos, this.line);
	}
	
	Operation factor() {
		//if number
		if(this.currToken.getType().compareTo("NUMBR")==0 || this.currToken.getType().compareTo("NUMBAR")==0) {
			return new Operation(new FactorToken(this.currToken), hasVisible);
		}
		//if string
		else if(this.currToken.getType().compareTo("YARN")==0) {
			return new Operation(new FactorToken(this.currToken), hasVisible);
		}
		//if variable name
		else if(this.currToken.getType().compareTo("VARIABLE")==0) {
			return new Operation(new FactorToken(this.currToken), hasVisible);
		}
		new Error("SynErr", "factor", this.pos, this.line);
		return null;
	}
	
	Operation factor(Token currToken) {
		//if number
		if(currToken.getType().compareTo("NUMBR")==0 || currToken.getType().compareTo("NUMBAR")==0) {
			return new Operation(new FactorToken(currToken), hasVisible);
		}
		//if string
		else if(currToken.getType().compareTo("YARN")==0) {
			return new Operation(new FactorToken(currToken), hasVisible);
		}
		//if variable name
		else if(currToken.getType().compareTo("VARIABLE")==0) {
			return new Operation(new FactorToken(currToken), hasVisible);
		}
		new Error("SynErr", "factor", this.pos, this.line);
		return null;
	}
	
	Operation term() {
		Token op = this.currToken;
		Operation left=null;
		Operation right=null;
		boolean isComplex=false;
		this.updatePosition();
		
		//recursion
		//for left hand side
		while(this.currToken.getType().compareTo("PRODUKTOF")==0 || 
				this.currToken.getType().compareTo("QUOSHUNTOF")==0) {
			left = this.term();
			isComplex = true;
		}
		
		//checks if left hand side contains multiple factors
		if(isComplex==false) {
			//if currToken is an operation
			if(this.currToken.getValue()==null && this.currToken.getVarValue()==null) left = this.term();
			else {
				left = this.factor();
			}
		}
		
		this.updatePosition();

		if(this.isNumber(this.tokenList.get(this.pos))) {
			if(this.pos+1 < this.tokenList.size() && this.isNumber(this.tokenList.get(this.pos+1)))
				new Error("ParseErr", "Excessive number of factors", this.pos, this.line);
		}
		this.updatePosition();
		
		//reset isComplex
		isComplex = false;

		//this method increases token index
//		Operation left = this.factor();
		
		//for right hand side
		while(this.currToken.getType().compareTo("PRODUKTOF")==0 || 
				this.currToken.getType().compareTo("QUOSHUNTOF")==0) {
			right = this.term();
			isComplex = true;
		}
		

		if(isComplex==false) {
			//if currToken is an operation
			if(this.currToken.getValue()==null && this.currToken.getVarValue()==null) right = this.term();
			else right = this.factor();
		}	

		
		
		if((left==null || right==null) && this.currToken.getType().equalsIgnoreCase("EOF")) {
			new Error("ParseErr", this.currToken.getType(), this.pos, this.line);
			return null;
		}
		//whole term
		//single op single; single op complex; complex op single; and complex op complex
		if(left.getOptr()==null && right.getOptr()==null) return new Operation(new FactorToken(left.getLeftTerm().getFactorToken()), op, new FactorToken(right.getLeftTerm().getFactorToken()), hasVisible);
		if(left.getOptr()==null) return new Operation(new FactorToken(left.getLeftTerm().getFactorToken()), op, right, hasVisible);
		if(right.getOptr()==null) return new Operation(left, op, new FactorToken(right.getLeftTerm().getFactorToken()), hasVisible);
		return new Operation(left, op, right, hasVisible);
		
	}
	
	Operation mathStatement() {
		Token op = this.currToken;
		Operation left=null;
		Operation right=null;
		boolean isComplex=false;
		this.updatePosition();

		
		//MATH Rule
		//recursion
		while(this.currToken.getType().compareTo("SUMOF")==0 || 
				this.currToken.getType().compareTo("DIFFOF")==0) {
			left = this.mathStatement();
			isComplex = true;
		}
		
		
		//checks if left hand side contains multiple factors
		if(isComplex==false) {
			//if currToken is an operation
			if(this.currToken.getValue()==null && this.currToken.getVarValue()==null) {

//				//if currToken is an operation
				left = this.term();
			}

			else left = this.factor();
		}
		this.updatePosition();

		//without AN keyword, a number, but followed by another number error
		if(this.isNumber(this.currToken)) {
			if(this.pos+1 < this.tokenList.size() && this.isNumber(this.tokenList.get(this.pos+1)))
				new Error("ParseErr", "Excessive number of factors", this.pos, this.line);
		}		
		
		//AN keyword
		this.updatePosition();
		
		
		//reset isComplex
		isComplex = false;

		//this method increases token index
//		Operation left = this.factor();

		//for right hand side
		while(this.currToken.getType().compareTo("SUMOF")==0 || 
				this.currToken.getType().compareTo("DIFFOF")==0) {
			right = this.mathStatement();
			isComplex = true;
		}
		//checks if right hand side contains multiple factors
		if(isComplex==false) {
			//if currToken is an operation
			if(this.currToken.getValue()==null && this.currToken.getVarValue()==null) right = this.term();
			else right = this.factor();
		}		

		
		
		if((left==null || right==null) && this.currToken.getType().equalsIgnoreCase("EOF")) {
			new Error("ParseErr", this.currToken.getType(), this.pos, this.line);
			return null;
		}

		
		//whole term
		//single lhs and rhs are always in the leftTerm variable of the object Operation
		//single op single; single op complex; complex op single; and complex op complex
		if(left.getOptr()==null && right.getOptr()==null) return new Operation(new FactorToken(left.getLeftTerm().getFactorToken()), op, new FactorToken(right.getLeftTerm().getFactorToken()), hasVisible);
		if(left.getOptr()==null) return new Operation(new FactorToken(left.getLeftTerm().getFactorToken()), op, right, hasVisible);
		if(right.getOptr()==null) return new Operation(left, op, new FactorToken(right.getLeftTerm().getFactorToken()), hasVisible);
		return new Operation(left, op, right, hasVisible);
				
	}
	
	Operation compStatement() {
		Token op = this.currToken;
		Operation left=null;
		Operation right=null;
		boolean isComplex=false;
		this.updatePosition();
		
		//recursion
		//for left hand side
		while(this.currToken.getType().compareTo("BIGGROF")==0 || 
				this.currToken.getType().compareTo("SMALLROF")==0 ||
				this.currToken.getType().compareTo("BOTHSAEM")==0 ||
				this.currToken.getType().compareTo("DIFFRINT")==0 ||
				this.currToken.getType().compareTo("NOT")==0) {
			left = this.compStatement();
			isComplex = true;
		}
		
		//checks if left hand side contains multiple factors
		if(isComplex==false) {
			//if currToken is an operation
			if(this.currToken.getValue()==null && this.currToken.getVarValue()==null) left = this.mathStatement();
			else {
				left = this.factor();
			}
		}
		
		
		//NOT Operation
		if(this.tokenList.get(this.pos-1).getType().compareTo("NOT")==0) {

			//whole term
			//single op and complex op
			if(left.getOptr()==null) return new Operation(new FactorToken(left.getLeftTerm().getFactorToken()), op, hasVisible);
			return new Operation(left, op, hasVisible);
		}
		
		this.updatePosition();

		//without AN keyword, a number, but followed by another number error
		if(this.isNumber(this.tokenList.get(this.pos))) {
			if(this.pos+1 < this.tokenList.size() && this.isNumber(this.tokenList.get(this.pos+1)))
				new Error("ParseErr", "Excessive number of factors", this.pos, this.line);
		}
		this.updatePosition();
		
		//reset isComplex
		isComplex = false;

		
		//for right hand side
		while(this.currToken.getType().compareTo("BIGGROF")==0 || 
				this.currToken.getType().compareTo("SMALLROF")==0 ||
				this.currToken.getType().compareTo("BOTHSAEM")==0 ||
				this.currToken.getType().compareTo("DIFFRINT")==0) {
			right = this.compStatement();
			isComplex = true;
		}
		
		//checks if right hand side contains multiple factors
//		if(isComplex==false) right = this.factor();
		if(isComplex==false) {
			//if currToken is an operation
			if(this.currToken.getValue()==null && this.currToken.getVarValue()==null) right = this.mathStatement();
			else right = this.factor();
		}	

		
		
		if((left==null || right==null) && this.currToken.getType().equalsIgnoreCase("EOF")) {
			new Error("ParseErr", this.currToken.getType(), this.pos, this.line);
			return null;
		}
		//whole term
		//single op single; single op complex; complex op single; and complex op complex
		if(left.getOptr()==null && right.getOptr()==null) return new Operation(new FactorToken(left.getLeftTerm().getFactorToken()), op, new FactorToken(right.getLeftTerm().getFactorToken()), hasVisible);
		if(left.getOptr()==null) return new Operation(new FactorToken(left.getLeftTerm().getFactorToken()), op, right, hasVisible);
		if(right.getOptr()==null) return new Operation(left, op, new FactorToken(right.getLeftTerm().getFactorToken()), hasVisible);
		return new Operation(left, op, right, hasVisible);
	}
	
	Operation statement() {
		Token op = this.currToken;

		if(op.getType().compareTo("VISIBLE")==0) {
			hasVisible=true;
			ArrayList<Operation> toPrint = new ArrayList<Operation>();

			
			//NEW
			//loops through the line with optr VISIBLE and run each in statement()
			//get past VISIBLE token
			this.updatePosition();
			if(this.currToken.getType().compareTo("NUMBR")==0 || this.currToken.getType().compareTo("NUMBAR")==0 ||
					this.currToken.getType().compareTo("VARIABLE")==0 || this.currToken.getType().compareTo("YARN")==0) {
				//VISIBLE HAS TO BE THE LAST IN LOOPS AND IF-ELSE || SINGLE COMPLEX oprtn
				while(this.currToken.getType().compareTo("EOF")!=0 && 
						this.currToken.getType().compareTo("IMOUTTAYR")!=0 &&
						this.currToken.getType().compareTo("MEBBE")!=0 &&
						this.currToken.getType().compareTo("NOWAI")!=0 &&
						this.currToken.getType().compareTo("OIC")!=0) {
					if(this.currToken.getType().compareTo("NUMBR")==0 || this.currToken.getType().compareTo("NUMBAR")==0 ||
						this.currToken.getType().compareTo("VARIABLE")==0 || this.currToken.getType().compareTo("YARN")==0) {
						toPrint.add(this.factor(this.currToken));
					}
					else toPrint.add(this.statement());
					this.updatePosition();
				}
				this.regressPosition();
				return (new Operation(op, toPrint, hasVisible, true));
			}

		}
		op = this.currToken;
		String varName;
		Operation varStmt;
		Operation left=null;
		Operation right=null;
		ArrayList<Operation> rlyCases = new ArrayList<Operation>();
		ArrayList<Operation> nowaiCases = new ArrayList<Operation>();
		Operation mebbeWoFCase, yrCondition=null;
		ArrayList<Operation> mebbeCases = new ArrayList<Operation>();
		ArrayList<Mebbe> mebbes = new ArrayList<Mebbe>();
		ArrayList<Operation> yrStatements = new ArrayList<Operation>();
		Token yrVar=null;


		boolean isComplex=false;
		this.updatePosition();

		//Variable declaration
		if(op.getType().compareTo("IHASA")==0) {
			if(this.currToken.getType().compareTo("VARIABLE")!=0) new Error("SynErr", "Not a valid variable name", this.pos);
			varName = this.currToken.getVarValue();
			this.updatePosition();
			
			if(this.currToken.getType().compareTo("ITZ")!=0) {
				if(this.currToken.getType().compareTo("EOF")==0) return (new Operation(new LOLVariable(varName)));
				else new Error("SynErr", "Expected 'ITZ'", this.pos);
			}
			this.updatePosition();

			varStmt = this.statement();
			return (new Operation(this.assignVariable(varName, varStmt)));
			
		}
		
		//Variable assignment
		else if(op.getType().compareTo("VARIABLE")==0 && this.currToken.getType().compareTo("R")==0) {
			varName = op.getVarValue();
			if(this.currToken.getType().compareTo("R")!=0) new Error("SynErr", "Expected 'R'", this.pos);
			this.updatePosition();
			
			varStmt = this.statement();
			return (new Operation(this.assignVariable(varName, varStmt)));
		}
		
		//BOOL
		else if(op.getType().compareTo("ANYOF")==0 || op.getType().compareTo("ALLOF")==0) {
			ArrayList<Operation> anyOprtn = new ArrayList<Operation>();
			//add statements in anyOprtn
			while(this.currToken.getType().compareTo("MKAY")!=0) {
				//if operation
				if(this.currToken.getValue()==null && this.currToken.getVarValue()==null) {
					anyOprtn.add(this.compStatement());
				}
				else anyOprtn.add(this.factor());
				
				this.updatePosition();
				//AN keyword or MKAY, when at the last part of any stmt
				if(this.currToken.getType().compareTo("MKAY")!=0) this.updatePosition();
			}
			
			return(new Operation(op, anyOprtn, hasVisible));
		}
		
		//Control Flow
		else if(op.getType().compareTo("ORLY?")==0) {
			boolean isVariable=false;
			boolean hasMebbe=false;
			if(this.currToken.getType().compareTo("YARLY")==0) {
				//goes to the case if YA RLY
				this.updatePosition();
				
				if(this.currToken.getType().compareTo("IHASA")==0) isVariable=true;
				while(this.currToken.getType().compareTo("MEBBE")!=0 && 
						this.currToken.getType().compareTo("NOWAI")!=0) {
					rlyCases.add(this.statement());
					if(isVariable) {
						isVariable=false;
//						this.regressPosition();
					}
					this.updatePosition();
				}
				

			}
			while(this.currToken.getType().compareTo("MEBBE")==0) {
				this.updatePosition();
				mebbeWoFCase = this.statement();
				this.updatePosition();
				//TODO loop to add more mebbe cases
				mebbeCases.add(mebbeWoFCase);
				mebbes.add(new Mebbe(this.statement(), mebbeCases));
				this.updatePosition();
				hasMebbe=true;
			}
			
			if(this.currToken.getType().compareTo("NOWAI")==0) {
				this.updatePosition();
				while(this.currToken.getType().compareTo("OIC")!=0) {
					nowaiCases.add(this.statement());
					if(this.currToken.getType().compareTo("OIC")==0) continue;
					this.updatePosition();
				}

			}

			if(this.currToken.getType().compareTo("OIC")==0) {
				if(hasMebbe) {
					return(new Operation(op, rlyCases, nowaiCases, mebbes));
				}
				return(new Operation(op, rlyCases, nowaiCases));
			}
			else new Error("MissingKeyErr", "Missing control flow keyword 'OIC'", this.pos);
		}
		
		//LOOP
		else if(op.getType().compareTo("IMINYR")==0) {
			boolean isUppin=false;
			boolean isTil=false;
			boolean isWile=false;
			String label=null;
			
			//label
			if(this.currToken.getType().compareTo("LABEL")==0) {
				label = this.currToken.getVarValue();
				this.updatePosition();
			}
			else new Error("MissingKeyErr", "Missing label.", this.pos);
				
			//OPERATION
			if(this.currToken.getType().compareTo("UPPIN")==0) isUppin = true;
			else if(this.currToken.getType().compareTo("NERFIN")==0) isUppin = false;
			else new Error("MissingKeyErr", "Missing Loop Operation UPPIN or NERFIN.", this.pos);
			this.updatePosition();

			//YR
			if(this.currToken.getType().compareTo("YR")!=0) new Error("MissingKeyErr", "Missing 'YR'.", this.pos);
			this.updatePosition();

			//VARIABLE
			if(this.currToken.getType().compareTo("VARIABLE")==0) yrVar = this.currToken;
			else new Error("MissingKeyErr", "Missing loop variable.", this.pos);
			this.updatePosition();
			
			//TIL/WILE
			if(this.currToken.getType().compareTo("TIL")==0 || this.currToken.getType().compareTo("WILE")==0) {
				if(this.currToken.getType().compareTo("TIL")==0) isTil = true;
				else if(this.currToken.getType().compareTo("WILE")==0) isWile = true;
				this.updatePosition();

				yrCondition = this.statement();
				this.updatePosition();
			}
			yrStatements.add(this.statement());
			this.updatePosition();

			if(this.currToken.getType().compareTo("IMOUTTAYR")==0) {

				this.updatePosition();
				if(this.currToken.getType().compareTo("LABEL")==0) {
					//checks if labels match
					if(this.currToken.getVarValue().compareTo(label)==0)
						return(new Operation(op, yrCondition, yrStatements, isUppin, isTil, isWile, yrVar));
					else new Error("ParseErr", "Labels not matching.", this.pos);
				}
				else new Error("MissingKeyErr", "Missing last label.", this.pos);
			}
			else new Error("MissingKeyErr", "Missing Loop Operation 'IM OUTTA HERE'.", this.pos);
		}
		
		
		while(this.currToken.getType().compareTo("BOTHOF")==0 || 
				this.currToken.getType().compareTo("EITHEROF")==0 ||
				this.currToken.getType().compareTo("WONOF")==0 ||
				this.currToken.getType().compareTo("ANYOF")==0 ||
				this.currToken.getType().compareTo("ALLOF")==0) {
			left = this.statement();
			isComplex = true;
		}
		
		
		//checks if left hand side contains multiple factors
		if(isComplex==false) {
			//if currToken is an operation
			if(this.currToken.getValue()==null && this.currToken.getVarValue()==null) {
	 			//if variable assignment
				//goes back 2 steps to check for unique keyword ITZ in variable declaration
				if((this.tokenList.get(this.pos-1).getType().compareTo("NUMBR")==0 || 
						this.tokenList.get(this.pos-1).getType().compareTo("NUMBAR")==0 ||
						this.tokenList.get(this.pos-1).getType().compareTo("YARN")==0 ||
						this.tokenList.get(this.pos-1).getType().compareTo("VARIABLE")==0) 
						&& (this.pos-2)>=0 && 
						(this.tokenList.get(this.pos-2).getType().compareTo("ITZ")==0 ||
						this.tokenList.get(this.pos-2).getType().compareTo("R")==0)) {
					this.regressPosition();
					return left=this.factor(this.tokenList.get(this.pos));
				}
				//if currToken is an operation
				left = this.compStatement();

			}

			else left = this.factor();
		}
		
		//NOT Operation

		if(op.getType().compareTo("NOT")==0) {

			//whole term
			//single op and complex op
			if(left.getOptr()==null) return new Operation(new FactorToken(left.getLeftTerm().getFactorToken()), op, hasVisible);
			return new Operation(left, op, hasVisible);
		}
		
		this.updatePosition();
		
		
		//without AN keyword, a number, but followed by another number error
		if(this.isNumber(this.currToken)) {
			if(this.pos+1 < this.tokenList.size() && this.isNumber(this.tokenList.get(this.pos+1)))
				new Error("ParseErr", "Excessive number of factors", this.pos, this.line);
		}		
		
		//AN keyword
		this.updatePosition();
		
		
		//reset isComplex
		isComplex = false;

		//this method increases token index
//		Operation left = this.factor();

		//for right hand side
		while(this.currToken.getType().compareTo("BOTHOF")==0 || 
				this.currToken.getType().compareTo("EITHEROF")==0 ||
				this.currToken.getType().compareTo("WONOF")==0 ||
				this.currToken.getType().compareTo("ANYOF")==0 ||
				this.currToken.getType().compareTo("ALLOF")==0) {
			right = this.statement();
			isComplex = true;
		}
		//checks if right hand side contains multiple factors
		if(isComplex==false) {
			//if currToken is an operation
			if(this.currToken.getValue()==null && this.currToken.getVarValue()==null) right = this.compStatement();
			else right = this.factor();
		}
		
		if((left==null || right==null) && this.currToken.getType().equalsIgnoreCase("EOF")) {
			new Error("ParseErr", this.currToken.getType(), this.pos, this.line);
			return null;
		}
		
		//whole term
		//single lhs and rhs are always in the leftTerm variable of the object Operation
		//single op single; single op complex; complex op single; and complex op complex
		if(left.getOptr()==null && right.getOptr()==null) return new Operation(new FactorToken(left.getLeftTerm().getFactorToken()), op, new FactorToken(right.getLeftTerm().getFactorToken()), hasVisible);
		if(left.getOptr()==null) return new Operation(new FactorToken(left.getLeftTerm().getFactorToken()), op, right, hasVisible);
		if(right.getOptr()==null) return new Operation(left, op, new FactorToken(right.getLeftTerm().getFactorToken()), hasVisible);
		return new Operation(left, op, right, hasVisible);
		
		
	}
	
	LOLVariable assignVariable(String varName, Operation varStmt) {
		return new LOLVariable(varName, varStmt);
	}
	
	boolean isNumber(Token tok) {
		if(tok.getType().equalsIgnoreCase("NUMBR") || tok.getType().equalsIgnoreCase("NUMBAR"))
			return true;
		return false;
	}
}
