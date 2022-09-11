package laxamanaLOLCodeCompiler;

import java.util.HashMap;
import java.util.Set;

public class TokenTable {
	HashMap<String, LOLNumber> tokenTable;
	HashMap<String, String> tokenTableString;
	HashMap<String, LOLNumber> parentTable;
	HashMap<String, String> parentTableString;
	
	TokenTable(){
		this.tokenTable = new HashMap<>();
		this.tokenTableString = new HashMap<>();
		this.parentTable = null;		
	}
	LOLNumber getValue(String name) {
		LOLNumber value;
		value = tokenTable.get(name);
		if(value==null && this.parentTable!=null)
			return this.parentTable.get(name);
		return value;
	}
	
	void setValue(String name, LOLNumber lolNumber) {
		this.tokenTable.put(name, lolNumber);
	}
	
	void removeValue(String name) {
		this.tokenTable.remove(name);
	}
	
	Set<String> getKeySet() {
		return this.tokenTable.keySet();
	}
	
	String getStringValue(String name) {
		String value;
		value = this.tokenTableString.get(name);
		if(value==null && this.parentTableString!=null)
			return this.parentTableString.get(name);
		return value;
	}
	
	void setStringValue(String name, String yarn) {
		this.tokenTableString.put(name, yarn);
	}
	
	void removeStringValue(String name) {
		this.tokenTableString.remove(name);
	}
	
	Set<String> getKeySetString() {
		return this.tokenTableString.keySet();
	}
}
