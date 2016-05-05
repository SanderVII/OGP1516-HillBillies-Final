package hillbillies.model;

import java.util.List;

import hillbillies.statements.Statement;

public class Task {
	
	private String taskName;
	private int priority;
	private Statement statementActivity;
	private List<int[]> selectedCubes;

	public Task(String name, int priority, Statement activity, List<int[]> selectedCubes){
		this.taskName = name;
		this.priority = priority;
		this.statementActivity = activity;
		this.selectedCubes = selectedCubes;
	}
	
	public List<int[]> getSelectedCubes(){
		return this.getSelectedCubes();
	}
	
}
