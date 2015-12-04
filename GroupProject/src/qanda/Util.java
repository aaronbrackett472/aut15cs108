package qanda;

public class Util {
	public static String showSuccessMessage(String message, int col){
		return "<div class=\"row\"><div class=\"col-md-" + col + " bg-success\"><h4>" + message + "</h4></div></div>";
	}
	
	public static String showSuccessMessage(String message){
		return showSuccessMessage(message, 12);
	}
	
	public static String showWarningMessage(String message, int col){
		return "<div class=\"row\"><div class=\"col-md-" + col + " bg-warning\"><h4>" + message + "</h4></div></div>";
	}
	
	public static String showWarningMessage(String message){
		return showWarningMessage(message, 12);
	}
	
	public static String showErrorMessage(String message, int col){
		return "<div class=\"row\"><div class=\"col-md-" + col + " bg-danger\"><h4>" + message + "</h4></div></div>";
	}
	
	public static String showErrorMessage(String message){
		return showErrorMessage(message, 12);
	}
	
	public static String showInfoMessage(String message, int col){
		return "<div class=\"row\"><div class=\"col-md" + col + " bg-info\"><h4>" + message + "</h4></div></div>";
	}
	
	public static String showInfoMessage(String message){
		return showInfoMessage(message, 12);
	}
}
