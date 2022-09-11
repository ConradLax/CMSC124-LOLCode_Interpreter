package laxamanaLOLCodeCompiler;

public class LOLVariable {
	String varName;
	Operation varStmt;
	
	LOLVariable(String varName, Operation varStmt){
		this.varName = varName;
		this.varStmt = varStmt;
	}
	
	LOLVariable(String varName){
		this.varName = varName;
		this.varStmt = null;
	}

	String getVarName() {
		return this.varName;
	}

	void setVarName(String varName) {
		this.varName = varName;
	}

	Operation getVarStmt() {
		return this.varStmt;
	}

	void setVarStmt(Operation varStmt) {
		this.varStmt = varStmt;
	}
	
}
