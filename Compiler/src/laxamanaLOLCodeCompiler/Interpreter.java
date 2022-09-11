package laxamanaLOLCodeCompiler;

import java.util.ArrayList;

public class Interpreter {
	Operation syntaxTree;
	TokenTable tokenTable;
	ArrayList<String> unaryOptrs = new ArrayList<String>();
	ArrayList<String> binaryOptrs = new ArrayList<String>();
	ArrayList<String> manyFactorOptrs = new ArrayList<String>();
	ArrayList<String> controlFlowOptrs = new ArrayList<String>();
	boolean visibleYarn=false, manyFactor=false;

	
	Interpreter(Operation syntaxTree, TokenTable tokenTable){
		this.syntaxTree = syntaxTree;
		this.tokenTable = tokenTable;
		this.unaryOptrs.add("NOT");
		this.unaryOptrs.add("VISIBLE");
		this.binaryOptrs.add("SUMOF");
		this.binaryOptrs.add("DIFFOF");
		this.binaryOptrs.add("PRODUKTOF");
		this.binaryOptrs.add("QUOSHUNTOF");
		this.binaryOptrs.add("BIGGROF");
		this.binaryOptrs.add("SMALLROF");
		this.binaryOptrs.add("BOTHOF");
		this.binaryOptrs.add("EITHEROF");
		this.binaryOptrs.add("BOTHSAEM");
		this.binaryOptrs.add("DIFFRINT");
		this.manyFactorOptrs.add("ANYOF");
		this.manyFactorOptrs.add("ALLOF");
		this.controlFlowOptrs.add("ORLY?");
	}
	LOLNumber visit(Operation syntaxTree) {
		LOLNumber res=null;
		//solves binary operations
		if(syntaxTree.getOptr()!=null) {

			if(this.unaryOptrs.contains(syntaxTree.getOptr().getType())) {
//				System.out.println("Unary operation found!");
				res = this.getUnaryOp(syntaxTree);
				//to make sure visit() won't print each binary operations in manyFactors or double prints
				if(syntaxTree.hasVisible() && !syntaxTree.isSimplePrint() && !this.visibleYarn && !this.manyFactor) 
					this.makeVisible(syntaxTree, res);
				return res;
			}
			if(this.manyFactorOptrs.contains(syntaxTree.getOptr().getType())) {
//				System.out.println("Many Factor operation found!");
				res = this.getManyFactorOp(syntaxTree);
				//if to print; VISIBLE
				if(syntaxTree.hasVisible() && !syntaxTree.isSimplePrint() && !this.visibleYarn && !this.manyFactor) 
					this.makeVisible(syntaxTree, res);
				return res;
			}
			if(this.controlFlowOptrs.contains(syntaxTree.getOptr().getType())) {
//				System.out.println("Control Flow statement found!");
				return this.getControlFlowOp(syntaxTree);
			}
			if(syntaxTree.getOptr().getType().compareTo("IMINYR")==0) {
//				System.out.println("Loop statement found!");
				return this.getLoopOp(syntaxTree);
			}
			if(this.binaryOptrs.contains(syntaxTree.getOptr().getType())) {
//				System.out.println("Binary operation found!");
				res = this.getBinaryOp(syntaxTree);
				//if to print; VISIBLE
				if(syntaxTree.hasVisible() && !syntaxTree.isSimplePrint() && !this.visibleYarn && !this.manyFactor) 
					this.makeVisible(syntaxTree, res);
				return res;
			}
		}
		//save the value of new variable
		if(syntaxTree.getVar()!=null) {
//			syntaxTree.getVar().getVarStmt().printOperation();
//			System.out.println("asq1: "+syntaxTree.getVar().getVarStmt().getLeftTerm().getFactorToken().getValue());
			
			//if variable has no value yet
			if(syntaxTree.getVar().getVarStmt()==null) {
				this.tokenTable.setStringValue(syntaxTree.getVar().getVarName(), "null");
//				this.tokenTable.setValue("IT", null);
				return new LOLNumber(1, "NUMBR");
			}
			
			//string to variable
			if(syntaxTree.getVar().getVarStmt().getLeftTerm()!=null && syntaxTree.getVar().getVarStmt().getLeftTerm().getFactorToken().getType().compareTo("YARN")==0) {
				this.tokenTable.setStringValue(syntaxTree.getVar().getVarName(), syntaxTree.getVar().getVarStmt().getLeftTerm().getFactorToken().getVarValue());
				return new LOLNumber(1, "NUMBR");
			}
			//variable to variable
			if((syntaxTree.getVar().getVarStmt().getRightTerm()==null && syntaxTree.getVar().getVarStmt().getRightTermComplex()==null) &&
					syntaxTree.getVar().getVarStmt().getLeftTerm()!=null && syntaxTree.getVar().getVarStmt().getLeftTerm().getFactorToken().getType().compareTo("VARIABLE")==0) {
				//checks if variable to be assigned exists and is a LOLNumber
				if(this.tokenTable.getValue(syntaxTree.getVar().getVarStmt().getLeftTerm().getFactorToken().getVarValue())!=null) {
					this.tokenTable.setValue(syntaxTree.getVar().getVarName(), this.tokenTable.getValue(syntaxTree.getVar().getVarStmt().getLeftTerm().getFactorToken().getVarValue()));
//					this.tokenTable.setValue("IT", this.tokenTable.getValue(syntaxTree.getVar().getVarStmt().getLeftTerm().getFactorToken().getVarValue()));
				}

				//checks if variable to be assigned exists and is a YARN
				else if(this.tokenTable.getStringValue(syntaxTree.getVar().getVarStmt().getLeftTerm().getFactorToken().getVarValue())!=null)
					this.tokenTable.setStringValue(syntaxTree.getVar().getVarName(), this.tokenTable.getStringValue(syntaxTree.getVar().getVarStmt().getLeftTerm().getFactorToken().getVarValue()));
				return new LOLNumber(1, "NUMBR");
			}

			else {
				this.tokenTable.setValue(syntaxTree.getVar().getVarName(), this.visit(syntaxTree.getVar().getVarStmt()));
//				this.tokenTable.setValue("IT", this.visit(syntaxTree.getVar().getVarStmt()));
			}
			
			return this.tokenTable.getValue(syntaxTree.getVar().getVarName());
		}
		//if not complex; only a single number to be assigned to a variable name
		if(syntaxTree.getOptr()==null) {
			if(syntaxTree.getLeftTerm().getFactorToken().getType().compareTo("YARN")==0) {
				System.out.print(syntaxTree.getLeftTerm().getFactorToken().getVarValue());
				return null;
			}
			else if(syntaxTree.getLeftTerm().getFactorToken().getType().compareTo("VARIABLE")==0) {
				if(this.tokenTable.getValue(syntaxTree.getLeftTerm().getFactorToken().getVarValue())!=null) {
					System.out.print(this.tokenTable.getValue(syntaxTree.getLeftTerm().getFactorToken().getVarValue()).getValue().intValue());
				}
				else if(this.tokenTable.getStringValue(syntaxTree.getLeftTerm().getFactorToken().getVarValue())!=null) {
					System.out.print(this.tokenTable.getStringValue(syntaxTree.getLeftTerm().getFactorToken().getVarValue()));
				}
			}
			

//			System.out.println("asq"+syntaxTree.getLeftTerm().getFactorToken().getType());
//			System.out.println("asq: "+syntaxTree.getVar().getVarStmt().getLeftTerm().getFactorToken().getValue());
			return this.getLOLNumber(syntaxTree.getLeftTerm());
		}
//		if(syntaxTree.getLeftTerm()!=null) this.getLOLNumber(syntaxTree.getLeftTerm());
//		else visit(syntaxTree.getLeftTermComplex());
//		
//		if(syntaxTree.getRightTerm()!=null) System.out.println("Number found!");
//		else visit(syntaxTree.getRightTermComplex());
		
		return null;
	}
	
	LOLNumber getLOLNumber(FactorToken num) {
		return new LOLNumber(num);
	}
	
	LOLNumber getVarVal(String name) {
		return this.tokenTable.getValue(name);
	}
	
	String getVarStringVal(String name) {
		return this.tokenTable.getStringValue(name);
	}
	
	LOLNumber getBinaryOp(Operation syntaxTree) {
		LOLNumber left = null;
		LOLNumber right = null;
		LOLNumber res = null;

		if(syntaxTree.getLeftTerm()!=null) {
			if(syntaxTree.getLeftTerm().getFactorToken().getType().compareTo("NUMBR")==0 ||
					syntaxTree.getLeftTerm().getFactorToken().getType().compareTo("NUMBAR")==0) {
				left = this.getLOLNumber(syntaxTree.getLeftTerm());
			}
			else if(syntaxTree.getLeftTerm().getFactorToken().getType().compareTo("VARIABLE")==0) {
				left = this.getVarVal(syntaxTree.getLeftTerm().getFactorToken().getVarValue());
				
				//if variable has no value
				if(left==null) new Error("RuntimeErr", "Variable "+syntaxTree.getLeftTerm().getFactorToken().getVarValue()+" is undefined. ", 0);
			}
			
		}
		else left = getBinaryOp(syntaxTree.getLeftTermComplex());
		
		if(syntaxTree.getRightTerm()!=null) {
			if(syntaxTree.getRightTerm().getFactorToken().getType().compareTo("NUMBR")==0 ||
					syntaxTree.getRightTerm().getFactorToken().getType().compareTo("NUMBAR")==0) {
				right = this.getLOLNumber(syntaxTree.getRightTerm());
			}
			else if(syntaxTree.getRightTerm().getFactorToken().getType().compareTo("VARIABLE")==0) {
				right = this.getVarVal(syntaxTree.getRightTerm().getFactorToken().getVarValue());
				
				//if variable has no value
				if(right==null) new Error("RuntimeErr", "Variable "+syntaxTree.getRightTerm().getFactorToken().getVarValue()+" is undefined. ", 0);

			}
		}
		else right = getBinaryOp(syntaxTree.getRightTermComplex());
		
			if(syntaxTree.getOptr().getType().equalsIgnoreCase("SUMOF")) res = left.add(right);
			else if(syntaxTree.getOptr().getType().equalsIgnoreCase("DIFFOF")) res = left.subtract(right);
			else if(syntaxTree.getOptr().getType().equalsIgnoreCase("PRODUKTOF")) res = left.mult(right);
			else if(syntaxTree.getOptr().getType().equalsIgnoreCase("BIGGROF")) res = left.biggrOf(right);
			else if(syntaxTree.getOptr().getType().equalsIgnoreCase("SMALLROF")) res = left.smallrOf(right);
			else if(syntaxTree.getOptr().getType().equalsIgnoreCase("BOTHOF")) {
				res = left.bothOf(right);
				res.setType("TROOF");
			}
			else if(syntaxTree.getOptr().getType().equalsIgnoreCase("EITHEROF")) {
				res = left.eitherOf(right);
				res.setType("TROOF");
			}
			else if(syntaxTree.getOptr().getType().equalsIgnoreCase("BOTHSAEM")) {
				res = left.bothSaem(right);
				res.setType("TROOF");
			}
			else if(syntaxTree.getOptr().getType().equalsIgnoreCase("DIFFRINT")) {
				res = left.diffrint(right);
				res.setType("TROOF");
			}

		try {
			if(syntaxTree.getOptr().getType().equalsIgnoreCase("QUOSHUNTOF")) res = left.div(right);
		}catch(Exception e){
			if(right.getValue().intValue()==0) new Error("RuntimeErr", left.getValue()+" divided by "+right.getValue()+" is invalid.",0,0);
			else e.printStackTrace();
		}
		//give IT variable the res value
		this.tokenTable.setValue("IT", res);
		
		
		return res;
		
	}
	
	LOLNumber getUnaryOp(Operation syntaxTree) {
		LOLNumber left = null;
		LOLNumber res = null;
		if(syntaxTree.isSimplePrint()) {
			for(Operation oprtn : syntaxTree.getToPrint()) {
				oprtn.setSimplePrint(true);
				res = this.visit(oprtn);
				if(res!=null) this.makeVisible(syntaxTree, res);
				
//				if(oprtn.getLeftTerm().getFactorToken().getType().compareTo("NUMBR")==0) {
//					System.out.print(oprtn.getLeftTerm().getFactorToken().getValue().intValue());
//				}
//				else if(oprtn.getLeftTerm().getFactorToken().getType().compareTo("NUMBAR")==0) {
//					System.out.print(oprtn.getLeftTerm().getFactorToken().getValue().floatValue());
//				}
//				else if(oprtn.getLeftTerm().getFactorToken().getType().compareTo("VARIABLE")==0) {
//					if(this.tokenTable.getValue(oprtn.getLeftTerm().getFactorToken().getVarValue())!=null) {
//						System.out.print(this.tokenTable.getValue(oprtn.getLeftTerm().getFactorToken().getVarValue()).getValue().intValue());
//					}
//					else if(this.tokenTable.getStringValue(oprtn.getLeftTerm().getFactorToken().getVarValue())!=null) {
//						System.out.print(this.tokenTable.getStringValue(oprtn.getLeftTerm().getFactorToken().getVarValue()));
//					}
//				}
//				else if(oprtn.getLeftTerm().getFactorToken().getType().compareTo("YARN")==0) {
//					System.out.print(oprtn.getLeftTerm().getFactorToken().getVarValue());
//				}
			}
			System.out.println();
			return new LOLNumber(1, "NUMBR");
		}
		else if(syntaxTree.getLeftTerm()!=null) {
			if(syntaxTree.getLeftTerm().getFactorToken().getType().compareTo("NUMBR")==0 ||
					syntaxTree.getLeftTerm().getFactorToken().getType().compareTo("NUMBAR")==0) {
				left = this.getLOLNumber(syntaxTree.getLeftTerm());
			}
			else if(syntaxTree.getLeftTerm().getFactorToken().getType().compareTo("VARIABLE")==0 &&
					this.getVarStringVal(syntaxTree.getLeftTerm().getFactorToken().getVarValue())==null) {
				left = this.getVarVal(syntaxTree.getLeftTerm().getFactorToken().getVarValue());
				
				//if variable has no value
				if(left==null) new Error("RuntimeErr", "Variable "+syntaxTree.getLeftTerm().getFactorToken().getVarValue()+" is undefined. ", 0);
			}
			else if(this.getVarStringVal(syntaxTree.getLeftTerm().getFactorToken().getVarValue())!=null) {
				System.out.print(this.getVarStringVal(syntaxTree.getLeftTerm().getFactorToken().getVarValue()));
				this.visibleYarn=true;
				return res = new LOLNumber(1, "NUMBR");
			}
		
		}
		else if(this.unaryOptrs.contains(syntaxTree.getLeftTermComplex().getOptr().getType())) left = getUnaryOp(syntaxTree.getLeftTermComplex());
		else left = getBinaryOp(syntaxTree.getLeftTermComplex());

	//UNARY OPERATIONS
	if(syntaxTree.getOptr().getType().equalsIgnoreCase("NOT")) {
		res = left.not();
	}
	else if(syntaxTree.getOptr().getType().compareTo("VISIBLE")==0) {
		res = left;

		if(!this.visibleYarn) {
			this.makeVisible(syntaxTree, res);
			this.visibleYarn=true;
		}
	}
	
	//give IT variable the res value
	this.tokenTable.setValue("IT", res);
	
	
	return res;
	}
	
	LOLNumber getManyFactorOp(Operation syntaxTree) {
		LOLNumber tempAns = null;
		LOLNumber res = null;
		boolean firstTime = true;
		
		for(Operation oprtn : syntaxTree.getAnyOprtn()) {
			//to make sure visit() won't print each binary operations
			this.manyFactor=true;
			tempAns = this.visit(oprtn);
			if(tempAns.getType().compareTo("VARIABLE")==0) tempAns = this.getVarVal(oprtn.getLeftTerm().getFactorToken().getVarValue());
			if(firstTime) {
				res = tempAns;
				firstTime = false;
				continue;
			}
			if(syntaxTree.getOptr().getType().equalsIgnoreCase("ALLOF")) res = res.bothOf(tempAns);
			else if(syntaxTree.getOptr().getType().equalsIgnoreCase("ANYOF")) res = res.eitherOf(tempAns);
			
		}
		res.setType("TROOF");
		
		//print
//		this.makeVisible(syntaxTree, res);

		//give IT variable the res value
		this.tokenTable.setValue("IT", res);

		
		return res;
	}
	
	LOLNumber getControlFlowOp(Operation syntaxTree) {
		LOLNumber initialIT = this.tokenTable.getValue("IT");
		if(initialIT==null) new Error("RuntimeErr", "IT is null.", 0);
		if(initialIT.getValue().intValue()==1) {
			for(Operation oprtn : syntaxTree.getRlyCases()) {
				this.visit(oprtn);
			}
		}
		if(syntaxTree.isHasMebbe()) {
			for(Mebbe mebbe : syntaxTree.getMebbes()) {
				if(this.visit(mebbe.getMebbeWoFCase()).getValue().intValue()==1) {
					for(Operation oprtn : mebbe.getMebbeCases()) {
						this.visit(oprtn);
					}
				}
			}
		}
		else if(initialIT.getValue().intValue()==0) {
			for(Operation oprtn : syntaxTree.getNowaiCases()) {
				this.visit(oprtn);
			}
		}
//		else {
//			System.out.println("getControlFlowOp Error");
//			return new LOLNumber(0, "NUMBR");
//		}
		return new LOLNumber(1, "NUMBR");
	}
	
	LOLNumber getLoopOp(Operation syntaxTree) {
		int haltCond;
		//stops if true for TIL, false for WILE
		if(syntaxTree.isTil()) haltCond = 1;
		else haltCond = 0;
		

		//loop start
		while(!syntaxTree.hasTilWile ? true : this.visit(syntaxTree.getYrCondition()).getValue().intValue()!=haltCond) {

			for(Operation oprtn : syntaxTree.getYrStatements()) {
				this.visit(oprtn);
			}
			//UPPIN OR NERFIN
			LOLNumber var = this.tokenTable.getValue(syntaxTree.getYrVar().getVarValue());
			if(syntaxTree.isUppin()) {
				//increment the variable directly through tokenTable
				var.setValue(var.add(new LOLNumber(1, "NUMBR")).getValue().intValue());
			}
			else if(!syntaxTree.isUppin) {
				//decrement the variable directly through tokenTable
				var.setValue(var.subtract(new LOLNumber(1, "NUMBR")).getValue().intValue());
			}
		}
		
		return new LOLNumber(1, "NUMBR");

	}
	
	
	void makeVisible(Operation syntaxTree, LOLNumber res) {
		//if to print; VISIBLE
		if(syntaxTree.hasVisible()) {
			if(res.getType().compareTo("NUMBR")==0)
				System.out.println(res.getValue().intValue());
			else if(res.getType().compareTo("NUMBAR")==0)
				System.out.println(res.getValue().floatValue());
			else if(res.getType().compareTo("TROOF")==0) {
				if(res.getValue().intValue()==1) System.out.println("WIN");
				else if(res.getValue().intValue()==0) System.out.println("FAIL");
			}
		}
	}
}