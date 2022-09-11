package laxamanaLOLCodeCompiler;

public class Error {
	String errorName;
	String errorDetails;
	
	Error(String errorName, String errorDetails, int pos, int line){
		this.errorName = errorName;
		this.errorDetails = errorDetails;
		if(errorName.equalsIgnoreCase("IToken")) this.IllegalTokenError(pos, line);
		if(errorName.equalsIgnoreCase("MToken")) this.MissingTokenError(pos, line);
		else if(errorName.equalsIgnoreCase("ParseErr")) this.ParsingError(pos);
		else if(errorName.equalsIgnoreCase("SynErr")) this.InvalidSyntaxError(pos);
		else if(errorName.equalsIgnoreCase("MissingKeyErr")) this.MissingKeywordError(pos);
		else if(errorName.equalsIgnoreCase("RuntimeErr")) this.RuntimeError(pos);
		System.exit(0);
	}
	
	Error(String errorName, String errorDetails, int pos){
		this.errorName = errorName;
		this.errorDetails = errorDetails;
		if(errorName.equalsIgnoreCase("ParseErr")) this.ParsingError(pos);
		else if(errorName.equalsIgnoreCase("SynErr")) this.InvalidSyntaxError(pos);
		else if(errorName.equalsIgnoreCase("MissingKeyErr")) this.MissingKeywordError(pos);
		else if(errorName.equalsIgnoreCase("RuntimeErr")) this.RuntimeError(pos);
		System.exit(0);
	}
	
	void IllegalTokenError(int pos, int line) {
		System.out.println("Illegal Token on line " + line + ", index " + pos + ": '" + this.errorDetails+"'");
	}
	
	void MissingTokenError(int pos, int line) {
		System.out.println("Missing Token on line " + line + ", index " + pos + ": '" + this.errorDetails+"'");
	}
	
	void ParsingError(int pos) {
		System.out.println("Parsing Error on index " + pos + ": '" + this.errorDetails+"'");
	}
	
	void InvalidSyntaxError(int pos) {
		System.out.println("Syntax error " + pos + ": '" + this.errorDetails+"'");
	}
	
	void MissingKeywordError(int pos) {
		System.out.println("Missing keyword before index " + pos + ": '" + this.errorDetails+"'");
	}
	
	void RuntimeError(int pos) {
		System.out.println("Runtime Error: "+this.errorDetails);
	}
}
