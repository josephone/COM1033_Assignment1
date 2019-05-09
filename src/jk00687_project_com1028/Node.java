package jk00687_project_com1028;

public class Node {

	private int score = 0;
	private Node teamLeft = null;
	private Node teamRight = null;

	public Node(int score) {
		super();
		this.score = score;
		this.teamLeft = teamLeft;
		this.teamRight = teamRight;
	}
	

	public void setScore(int score) {
		this.score = score;
	}
	
	public void setTeamRight(Node right) {
		
	}


	public void setTeamLeft(Node left) {
		
	}
	
	public int getScore() {
		return score;
	}

	public Node getTeamLeft() {
		return teamLeft;
	}

	public Node getTeamRight() {
		return teamRight;
	}

}
