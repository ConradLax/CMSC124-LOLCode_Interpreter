package laxamanaLOLCodeCompiler;

import java.util.ArrayList;

public class Mebbe {
	Operation mebbeWoFCase;
	ArrayList<Operation> mebbeCases = new ArrayList<Operation>();
	
	Mebbe(Operation mebbeWoFCase, ArrayList<Operation> mebbeCases){
		this.mebbeWoFCase = mebbeWoFCase;
		this.mebbeCases = mebbeCases;
	}
	
	Operation getMebbeWoFCase() {
		return mebbeWoFCase;
	}
	void setMebbeWoFCase(Operation mebbeWoFCase) {
		this.mebbeWoFCase = mebbeWoFCase;
	}
	ArrayList<Operation> getMebbeCases() {
		return mebbeCases;
	}
	void setMebbeCases(ArrayList<Operation> mebbeCases) {
		this.mebbeCases = mebbeCases;
	}
	
	
}

