package laxamanaLOLCodeCompiler;

public class Token {
	String type;
	String dataType;
	Number numValue = null;
	String varValue = null;
	
	Token(String type, Number numValue){
		this.type = type;
		this.numValue = numValue;
	}
	Token(String type, Number numValue, String dataType){
		this.type = type;
		this.numValue = numValue;
		this.dataType = dataType;
	}
	Token(String type, String varValue){
		this.type = type;
		this.varValue = varValue;
	}
	Token(String type, String varValue, String dataType){
		this.type = type;
		this.varValue = varValue;
		this.dataType = dataType;
	}
	Token(String type){
		this.type = type;
	}
	String getType() {
		return type;
	}
	void setType(String type) {
		this.type = type;
	}
	String getDataType() {
		return dataType;
	}
	void setDataType(String dataType) {
		this.dataType = dataType;
	}
	Number getNumValue() {
		return numValue;
	}
	void setNumValue(Number numValue) {
		this.numValue = numValue;
	}
	Number getValue() {
		return numValue;
	}
	void setValue(Number value) {
		this.numValue = value;
	}
	String getVarValue() {
		return varValue;
	}
	void setVarValue(String varValue) {
		this.varValue = varValue;
	}
	
	
}
