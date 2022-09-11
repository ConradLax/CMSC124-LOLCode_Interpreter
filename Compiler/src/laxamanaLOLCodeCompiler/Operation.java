package laxamanaLOLCodeCompiler;

import java.util.ArrayList;

public class Operation {
	FactorToken leftTerm=null;
	Operation leftTermComplex=null;
	Token optr=null;
	FactorToken rightTerm=null;
	Operation rightTermComplex=null, yrCondition;
	LOLVariable var=null;
	ArrayList<Operation> anyOprtn = new ArrayList<Operation>();
	ArrayList<Operation> yrStatements = new ArrayList<Operation>();
	ArrayList<Operation> rlyCases = new ArrayList<Operation>();
	ArrayList<Operation> nowaiCases = new ArrayList<Operation>();
	ArrayList<Mebbe> mebbes = new ArrayList<Mebbe>();
	ArrayList<Operation> toPrint = new ArrayList<Operation>();
	boolean hasMebbe=false;
	boolean isUppin = false;
	boolean isTil = false;
	boolean isWile = false;
	boolean hasTilWile = false;
	boolean hasVisible=false;
	boolean isSimplePrint = false;
	Token yrVar;

	
	//UNARY optr
	Operation(FactorToken leftTerm, Token optr, boolean hasVisible){
		this.leftTerm = leftTerm;
		this.optr = optr;
		this.hasVisible = hasVisible;
	}
	
	Operation(Token optr, FactorToken rightTerm, boolean hasVisible){
		this.optr = optr;
		this.rightTerm = rightTerm;
		this.hasVisible = hasVisible;
	}
	
	Operation(Operation leftTermComplex, Token optr, boolean hasVisible){
		this.leftTerm = null;
		this.leftTermComplex = leftTermComplex;
		this.optr = optr;
		this.hasVisible = hasVisible;
	}
	
	Operation(Token optr, Operation rightTermComplex, boolean hasVisible){
		this.optr = optr;
		this.rightTermComplex = rightTermComplex;
		this.hasVisible = hasVisible;
	}
	Operation(Token optr, ArrayList<Operation> toPrint, boolean hasVisible, boolean isSimplePrint){
		this.optr = optr;
		this.toPrint = toPrint;
		this.isSimplePrint = isSimplePrint;
		this.hasVisible = hasVisible;
	}
	
	
	//BINARY optr
	Operation(FactorToken leftTerm, Token optr, FactorToken rightTerm, boolean hasVisible){
		this.leftTerm = leftTerm;
		this.optr = optr;
		this.rightTerm = rightTerm;
		this.hasVisible = hasVisible;
	}

	Operation(Operation leftTermComplex, Token optr, Operation rightTermComplex, boolean hasVisible){
		this.leftTermComplex = leftTermComplex;
		this.optr = optr;
		this.rightTermComplex = rightTermComplex;
		this.hasVisible = hasVisible;
	}
	
	Operation(Operation leftTermComplex, Token optr, FactorToken rightTerm, boolean hasVisible){
		this.leftTermComplex = leftTermComplex;
		this.optr = optr;
		this.rightTerm = rightTerm;
		this.hasVisible = hasVisible;
	}
	
	Operation(FactorToken leftTerm, Token optr, Operation rightTermComplex, boolean hasVisible){
		this.leftTerm = leftTerm;
		this.optr = optr;
		this.rightTermComplex = rightTermComplex;
		this.hasVisible = hasVisible;
	}
	
	//factor
	Operation(FactorToken leftTerm, boolean hasVisible){
		this.leftTerm = leftTerm;
		this.hasVisible = hasVisible;
	}
	
	//N factor operator
	Operation(Token optr, ArrayList<Operation> anyOprtn, boolean hasVisible){
		this.optr = optr;
		this.anyOprtn = anyOprtn;
		this.hasVisible = hasVisible;
	}
	
	//CONTROL FLOW
	Operation(Token optr, ArrayList<Operation> rlyCases, ArrayList<Operation> nowaiCases, ArrayList<Mebbe> mebbes){
		this.optr = optr;
		this.rlyCases = rlyCases;
		this.nowaiCases = nowaiCases;
		this.mebbes = mebbes;
		this.hasMebbe = true;
	}
	
	Operation(Token optr, ArrayList<Operation> rlyCases, ArrayList<Operation> nowaiCases){
		this.optr = optr;
		this.rlyCases = rlyCases;
		this.nowaiCases = nowaiCases;
	}
	
	//LOOP
	Operation(Token optr, Operation yrCondition, ArrayList<Operation> yrStatements, boolean isUppin, boolean isTil, boolean isWile, Token yrVar){
		this.optr = optr;
		this.yrCondition = yrCondition;
		this.yrStatements = yrStatements;
		this.yrVar = yrVar;
		if(isUppin) this.isUppin = true;
		this.isTil = isTil;
		this.isWile = isWile;
		if(this.isTil() || this.isWile()) this.hasTilWile = true;
	}
	
	//VARIABLE
	Operation(LOLVariable var){
		this.leftTerm=null;
		this.leftTermComplex=null;
		this.optr=null;
		this.rightTerm=null;
		this.rightTermComplex=null;
		this.var = var;
	}

	FactorToken getLeftTerm() {
		return leftTerm;
	}

	void setLeftTerm(FactorToken leftTerm) {
		this.leftTerm = leftTerm;
	}

	Operation getLeftTermComplex() {
		return leftTermComplex;
	}

	void setLeftTermComplex(Operation leftTermComplex) {
		this.leftTermComplex = leftTermComplex;
	}

	Token getOptr() {
		return optr;
	}

	void setOptr(Token optr) {
		this.optr = optr;
	}

	FactorToken getRightTerm() {
		return rightTerm;
	}

	void setRightTerm(FactorToken rightTerm) {
		this.rightTerm = rightTerm;
	}

	Operation getRightTermComplex() {
		return rightTermComplex;
	}

	void setRightTermComplex(Operation rightTermComplex) {
		this.rightTermComplex = rightTermComplex;
	}
	
	LOLVariable getVar() {
		return var;
	}

	void setVar(LOLVariable var) {
		this.var = var;
	}

	ArrayList<Operation> getAnyOprtn() {
		return anyOprtn;
	}

	void setAnyOprtn(ArrayList<Operation> anyOprtn) {
		this.anyOprtn = anyOprtn;
	}

	ArrayList<Operation> getRlyCases() {
		return rlyCases;
	}

	void setRlyCases(ArrayList<Operation> rlyCases) {
		this.rlyCases = rlyCases;
	}

	ArrayList<Operation> getNowaiCases() {
		return nowaiCases;
	}

	void setNowaiCases(ArrayList<Operation> nowaiCases) {
		this.nowaiCases = nowaiCases;
	}

	ArrayList<Mebbe> getMebbes() {
		return mebbes;
	}

	void setMebbes(ArrayList<Mebbe> mebbes) {
		this.mebbes = mebbes;
	}

	boolean isHasMebbe() {
		return hasMebbe;
	}

	void setHasMebbe(boolean hasMebbe) {
		this.hasMebbe = hasMebbe;
	}

	Operation getYrCondition() {
		return yrCondition;
	}

	void setYrCondition(Operation yrCondition) {
		this.yrCondition = yrCondition;
	}

	ArrayList<Operation> getYrStatements() {
		return yrStatements;
	}

	void setYrStatements(ArrayList<Operation> yrStatements) {
		this.yrStatements = yrStatements;
	}

	boolean isUppin() {
		return isUppin;
	}

	void setUppin(boolean isUppin) {
		this.isUppin = isUppin;
	}

	boolean isTil() {
		return isTil;
	}

	void setTil(boolean isTil) {
		this.isTil = isTil;
	}

	Token getYrVar() {
		return yrVar;
	}

	void setYrVar(Token yrVar) {
		this.yrVar = yrVar;
	}

	boolean isWile() {
		return isWile;
	}

	void setWile(boolean isWile) {
		this.isWile = isWile;
	}

	boolean isHasTilWile() {
		return hasTilWile;
	}

	void setHasTilWile(boolean hasTilWile) {
		this.hasTilWile = hasTilWile;
	}
	

	boolean hasVisible() {
		return hasVisible;
	}

	void setHasVisible(boolean hasVisible) {
		this.hasVisible = hasVisible;
	}

	ArrayList<Operation> getToPrint() {
		return toPrint;
	}

	void setToPrint(ArrayList<Operation> toPrint) {
		this.toPrint = toPrint;
	}

	boolean isSimplePrint() {
		return isSimplePrint;
	}

	void setSimplePrint(boolean isSimplePrint) {
		this.isSimplePrint = isSimplePrint;
	}

	boolean isHasVisible() {
		return hasVisible;
	}

	void printLeftTerm() {
		System.out.print(this.getLeftTerm().getFactorToken().getType() + " " + 
	this.getLeftTerm().getFactorToken().getValue()+" ");
	}
	void printRightTerm() {
		System.out.print(this.getRightTerm().getFactorToken().getType() + " " + 
	this.getRightTerm().getFactorToken().getValue()+" ");
	}
	
	void printOperation() {
		System.out.print("(");
		if(this.getLeftTerm()!=null) this.printLeftTerm();
		else if(this.getRightTermComplex()!=null) this.getLeftTermComplex().printOperation();
		System.out.print(")");
		
		System.out.print(this.getOptr().getType()+" ");
		
		System.out.print("(");
		if(this.getRightTerm()!=null) this.printRightTerm();
		else if(this.getRightTermComplex()!=null) this.getRightTermComplex().printOperation();
		System.out.print(")");
		
		System.out.println();
	}
}
