package jk00687_project_com1028;

/**
 * @author Joseph Kutler
 *
 */
public class KnockoutTree {

	private Node root = null;

	public KnockoutTree(Node root) {
		super();
		this.root = root;
	}

	/**
	 * @return This method acts as a getter for the root of the binary tree
	 */

	public Node getRoot() {
		return root;
	}

	/**
	 * The method addTeamToTree creates a new node of type Node with the parameter value. This
	 * new node is then added to the tree below node.
	 * 
	 * @param value 
	 * @param node
	 */

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

	
	/**
	 * @returns the greatest value in the tree
	 */
	
	public int greatest() {

		return maxNode(root);
	}

	public int maxNode(Node node) {
		if (node.getTeamRight() == null) {
			return node.getScore();
		} else
			return maxNode(node.getTeamRight());
	}



	/**
	 * The method teamProceed outputs the contents of the tree below the node 'startPoint'
	 * passed as the parameter
	 * 
	 * @param start
	 */
	
	public void teamProceed(Node startPoint) {

		if (startPoint != null) {
			teamProceed(startPoint.getTeamLeft());
			System.out.print(startPoint.getScore() + "  ");
			teamProceed(startPoint.getTeamRight());
		}
	}
}
