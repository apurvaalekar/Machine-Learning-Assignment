import java.util.ArrayList;

public class Node {

	
	Node left;    /*Left node reference*/
	Node right;   /*Right node reference*/
	String NodeName;  /*Node Label which stores attribute name corresponding to that code*/
	int lableNum; /*Number of attribute*/	
	int positiveZero; 
	int PositiveOne;
	int NegativeZero;
	int NegativeOne;
	ArrayList<Integer> instanceList; /*List of all instances available in that node*/
	ArrayList<Integer> parentLabelList; /*List of all attributes used in path till that node*/
	int isLeaf; /*Leaf node identifier*/ 
	int classLabel;   /*If node is leaf node, class label identifier*/
	Node parent;
	int nodeNum;
	
	public int getpositiveZero() {
		return positiveZero;
	}

	public void setpositiveZero(int positiveZero) {
		this.positiveZero = positiveZero;
	}

	public int getPositiveOne() {
		return PositiveOne;
	}

	public void setPositiveOne(int PositiveOne) {
		this.PositiveOne = PositiveOne;
	}

	public int getNegativeZero() {
		return NegativeZero;
	}

	public void setNegativeZero(int NegativeZero) {
		this.NegativeZero = NegativeZero;
	}

	public int getNegativeOne() {
		return NegativeOne;
	}

	public void setNegativeOne(int NegativeOne) {
		this.NegativeOne = NegativeOne;
	}   

	public Integer getLableNum() {
		return lableNum;
	}

	public void setLableNum(Integer lableNum) {
		this.lableNum = lableNum;
	}

	

	public int getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(int isLeaf) {
		this.isLeaf = isLeaf;
	}
	public int getnodeNum(){
		return this.nodeNum;
	}
	public void setnodeNum(int nodeNum){
		this.nodeNum=nodeNum;
	}

	public Node()
	{
		
		left = null;
		right = null;
		NodeName = "";
		this.instanceList = new ArrayList<Integer>();
		this.parentLabelList = new ArrayList<Integer>();
		lableNum = 0;
		isLeaf = 0;
		classLabel = -1;
		parent = null;
		nodeNum=-1;
	}

	public Node(int NodeNum,String NodeName)
	{
		this.nodeNum=NodeNum;
		this.NodeName = NodeName;
	}

	public int getNodeNum() {
		return nodeNum;
	}

	public void setNodeNum(int nodeNum) {
		this.nodeNum = nodeNum;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public String getNodeName() {
		return NodeName;
	}

	public void setNodeName(String NodeName) {
		this.NodeName = NodeName;
	}

	public void setInstanceData(ArrayList<ArrayList<Integer>> traingingData,ArrayList<Integer> parent_instanceList, 
			int lableNum,int val, ArrayList<Integer>childInstanceList) {
		// TODO Auto-generated method stub
		for (int j = 0; j < traingingData.size(); j++) {

			if(parent_instanceList.contains(traingingData.get(j).get(0)))
			{
				if (traingingData.get(j).get(lableNum) == val)
				{
					childInstanceList.add(traingingData.get(j).get(0));
				}
			}
		}
	}

	public void setParentLableList(Node root) {
		// TODO Auto-generated method stub

		/*copy all parent label list from parent node root then add parent node label as well
		 * this has been used to avoid use of same label in same path */

		for (int i = 0; i < root.parentLabelList.size(); i++) {
			this.parentLabelList.add(root.parentLabelList.get(i));			
		}
		this.parentLabelList.add(root.lableNum);	
	}

	public double getNumOfInstances() {
		// TODO Auto-generated method stub
		return this.instanceList.size();
	}
}