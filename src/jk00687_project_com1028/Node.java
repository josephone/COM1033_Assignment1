package jk00687_project_com1028;

/**
 * @author Joseph Kutler
 *
 */

public class Node {

	/**
	 * These three fields are nodes that contain data of type int and two nodes,
	 * teamLeft and teamRight, which are also of type Node
	 */

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
		this.teamRight = right;
	}

	public void setTeamLeft(Node left) {
		this.teamLeft = left;
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
