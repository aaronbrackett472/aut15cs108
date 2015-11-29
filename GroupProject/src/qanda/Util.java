package qanda;

public class Util {
	public static String showSuccessMessage(String message){
		return "<div class=\"row\"><div class=\"col-md-12 bg-success\"><h4>" + message + "</h4></div></div>";
	}
	
	public static String showWarningMessage(String message){
		return "<div class=\"row\"><div class=\"col-md-12 bg-warning\"><h4>" + message + "</h4></div></div>";
	}
	
	public static String showErrorMessage(String message){
		return "<div class=\"row\"><div class=\"col-md-12 bg-danger\"><h4>" + message + "</h4></div></div>";
	}
	
	public static String showInfoMessage(String message){
		return "<div class=\"row\"><div class=\"col-md-12 bg-info\"><h4>" + message + "</h4></div></div>";
	}
}
