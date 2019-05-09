package jk00687_project_com1028;


public class KnockoutTree {

	private Node root = null;

	public KnockoutTree(Node root) {
		super();
		this.root = root;
	}

	public Node getRoot() {
		return root;
	}
	
	public void addTeamToTree(int value, Node node) {
		if (value < node.getScore()) {
			if (node.getTeamLeft() == null) {
				node.setTeamLeft(new Node(value));
			} else {
				addTeamToTree(value, node.getTeamLeft());
			}
		} else {
			if (node.getTeamRight() == null) {
				node.setTeamRight(new Node(value));
			} else {
				addTeamToTree(value, node.getTeamRight());
			}
		}
	}

	public int greatest() {
		
		return maxNode(root);
	}
	
	public int maxNode(Node node) {
		if (node.getTeamRight() == null) {
			return node.getScore();
		} else
			return maxNode(node.getTeamRight());
	}
	
	public void teamProceed(Node startPoint) {
		
		if (startPoint != null) {
			teamProceed(startPoint.getTeamLeft());
			System.out.print(startPoint.getScore() + "  ");
			teamProceed(startPoint.getTeamRight());
		}
	}
}
