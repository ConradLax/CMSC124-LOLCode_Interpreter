package laxamanaLOLCodeCompiler;

public class FactorToken {
	Token factorToken;
	
	FactorToken(Token factorToken){
		this.factorToken = factorToken;
	}

	Token getFactorToken() {
		return factorToken;
	}

	void setFactorToken(Token numToken) {
		this.factorToken = numToken;
	}


	
}
