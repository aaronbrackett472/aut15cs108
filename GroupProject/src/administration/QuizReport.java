package administration;

public class QuizReport {
	public final int id;
	public final int quizID;
	public final String quizName;
	public final String author;
	public final String reporter;
	public final String comment;
	public final boolean resolved;
	
	public QuizReport(int id, int quizID, String quizName, String author, String reporter, String comment, boolean resolved) {
		this.id = id;
		this.quizID = quizID;
		this.quizName = quizName;
		this.author = author;
		this.reporter = reporter;
		this.comment = comment;
		this.resolved = resolved;
	}
}
