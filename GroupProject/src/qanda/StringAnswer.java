package qanda;

public class StringAnswer extends Answer {
	private String response;
	
	public StringAnswer(String answer){
		response = answer.trim().toLowerCase();
	}
	
	
	public String getResponse(){
		return response;
	}
}
