package laxamanaLOLCodeCompiler;

public class LOLNumber {
	Number value;
	String type;
	int startPos, endPos;
	
	LOLNumber(FactorToken numToken){
		this.type = numToken.getFactorToken().getType();
		this.value = numToken.getFactorToken().getValue();
		this.startPos = 0;
		this.endPos = 0;
	}
	
	LOLNumber(Number value, String type){
		this.value = value;
		this.type = type;
		this.startPos = 0;
		this.endPos = 0;
	}
	
	LOLNumber(){}
	
	
	Number getValue() {
		return value;
	}



	void setValue(int value) {
		this.value = value;
	}



	String getType() {
		return type;
	}


	void setType(String type) {
		this.type = type;
	}


	int getStartPos() {
		return startPos;
	}



	void setStartPos(int startPos) {
		this.startPos = startPos;
	}



	int getEndPos() {
		return endPos;
	}



	void setEndPos(int endPos) {
		this.endPos = endPos;
	}



	LOLNumber add(LOLNumber num) {
		//checks if it is adding same LOLNumbers
		if(num.getClass()==this.getClass()) {
			//checks if it is an int or a float by checking its type
			if(this.getType().equalsIgnoreCase("NUMBR") || num.getType().equalsIgnoreCase("NUMBR"))
			return new LOLNumber(this.getValue().intValue() + num.getValue().intValue(), "NUMBR");
			
			else return new LOLNumber(this.getValue().floatValue() + num.getValue().floatValue(), "NUMBAR");
		}
		return null;
	}
	
	LOLNumber subtract(LOLNumber num) {
		if(num.getClass()==this.getClass()) {
			if(this.getType().equalsIgnoreCase("NUMBR") || num.getType().equalsIgnoreCase("NUMBR"))
			return new LOLNumber(this.getValue().intValue() - num.getValue().intValue(), "NUMBR");
			
			else return new LOLNumber(this.getValue().floatValue() - num.getValue().floatValue(), "NUMBAR");
		}
		return null;
	}
	
	LOLNumber mult(LOLNumber num) {
		if(num.getClass()==this.getClass()) {
			if(this.getType().equalsIgnoreCase("NUMBR") || num.getType().equalsIgnoreCase("NUMBR"))
			return new LOLNumber(this.getValue().intValue() * num.getValue().intValue(), "NUMBR");
			
			else return new LOLNumber(this.getValue().floatValue() * num.getValue().floatValue(), "NUMBAR");
		}
		return null;
	}
	
	LOLNumber div(LOLNumber num) {
		if(num.getClass()==this.getClass()) {
			if(this.getType().equalsIgnoreCase("NUMBR") || num.getType().equalsIgnoreCase("NUMBR"))
			return new LOLNumber(this.getValue().intValue() / num.getValue().intValue(), "NUMBR");
			
			else return new LOLNumber(this.getValue().floatValue() / num.getValue().floatValue(), "NUMBAR");
		}
		return null;
	}
	
	LOLNumber bothOf(LOLNumber num) {
		if(num.getClass()==this.getClass()) {
			if(this.getType().equalsIgnoreCase("NUMBR") || num.getType().equalsIgnoreCase("NUMBR"))
			//1=true 0=false; if both 1, then 1, else 0
			return new LOLNumber(this.getValue().intValue()==1 && num.getValue().intValue()==1 ? 1 : 0, "NUMBR");
			
			else return new LOLNumber(this.getValue().floatValue()==1 && num.getValue().floatValue()==1 ? 1 : 0, "NUMBAR");
		}
		return null;
	}
	
	LOLNumber eitherOf(LOLNumber num) {
		if(num.getClass()==this.getClass()) {
			if(this.getType().equalsIgnoreCase("NUMBR") || num.getType().equalsIgnoreCase("NUMBR"))
			//1=true 0=false; one of the pair is 1, then 1, else false
			return new LOLNumber(this.getValue().intValue()==1 || num.getValue().intValue()==1 ? 1 : 0, "NUMBR");
			
			else return new LOLNumber(this.getValue().floatValue()==1 || num.getValue().floatValue()==1 ? 1 : 0, "NUMBAR");
		}
		return null;
	}
	
	LOLNumber biggrOf(LOLNumber num) {
		if(num.getClass()==this.getClass()) {
			if(this.getType().equalsIgnoreCase("NUMBR") || num.getType().equalsIgnoreCase("NUMBR"))
			//1=true 0=false; one of the pair is 1, then 1, else false
			return new LOLNumber(this.getValue().intValue() > num.getValue().intValue() ? this.getValue().intValue() : num.getValue().intValue(), "NUMBR");
			
			else return new LOLNumber(this.getValue().floatValue() > num.getValue().floatValue() ? this.getValue().intValue() : num.getValue().intValue(), "NUMBAR");
		}
		return null;
	}
	
	LOLNumber smallrOf(LOLNumber num) {
		if(num.getClass()==this.getClass()) {
			if(this.getType().equalsIgnoreCase("NUMBR") || num.getType().equalsIgnoreCase("NUMBR"))
			//1=true 0=false; one of the pair is 1, then 1, else false
			return new LOLNumber(this.getValue().intValue() < num.getValue().intValue() ? this.getValue().intValue() : num.getValue().intValue(), "NUMBR");
			
			else return new LOLNumber(this.getValue().floatValue() < num.getValue().floatValue() ? this.getValue().intValue() : num.getValue().intValue(), "NUMBAR");
		}
		return null;
	}
	
	LOLNumber bothSaem(LOLNumber num) {
		if(num.getClass()==this.getClass()) {
			if(this.getType().equalsIgnoreCase("NUMBR") || num.getType().equalsIgnoreCase("NUMBR"))
			//1=true 0=false; one of the pair is 1, then 1, else false
			return new LOLNumber(this.getValue().intValue() == num.getValue().intValue() ? 1 : 0, "NUMBR");
			
			else return new LOLNumber(this.getValue().floatValue() == num.getValue().floatValue() ? 1 : 0, "NUMBAR");
		}
		return null;
	}
	
	LOLNumber diffrint(LOLNumber num) {
		if(num.getClass()==this.getClass()) {
			if(this.getType().equalsIgnoreCase("NUMBR") || num.getType().equalsIgnoreCase("NUMBR"))
			//1=true 0=false; one of the pair is 1, then 1, else false
			return new LOLNumber(this.getValue().intValue() == num.getValue().intValue() ? 0 : 1, "NUMBR");
			
			else return new LOLNumber(this.getValue().floatValue() == num.getValue().floatValue() ? 0 : 1, "NUMBAR");
		}
		return null;
	}
	
	LOLNumber not() {
		if(this.getType().equalsIgnoreCase("NUMBR"))
		//1=true 0=false; one of the pair is 1, then 1, else false
		return new LOLNumber(this.getValue().intValue() == 1 ? 0 : 1, "TROOF");
		
		else return new LOLNumber(this.getValue().floatValue() == 1 ? 0 : 1, "TROOF");
		
	}
}
