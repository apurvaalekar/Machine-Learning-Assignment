
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


//Class to keep count of positive and negative values 
class ClassCount {
	int positive_count;
	int negative_count;

	public ClassCount()
	{
		this.negative_count = 0;
		this.positive_count = 0;

	}
}

public class ID3 {

	ArrayList<ArrayList<Integer>> trainingData;
	ArrayList<ArrayList<Integer>> testingData;
	ArrayList<ArrayList<Integer>> validationData;
	ArrayList<String> lables; 
	Node root;
	static int nodeCnt;
	ArrayList<Node> leafNodes;
	int nonLeafNodes;

	public ID3(File fileName, File fileName1,File fileName2)
	{ 
		Scanner sc = null;
		try {
			sc = new Scanner(fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		lables = new ArrayList<String>();
		String labledata = sc.nextLine();

		parseLabels(labledata);

		trainingData = new ArrayList<ArrayList<Integer>>();
		leafNodes = new ArrayList<Node>();
		int dataCnt=0;
		while(sc.hasNextLine())
		{
			dataCnt = dataCnt + 1;
			String line = sc.nextLine();
			ArrayList<Integer> data = new ArrayList<Integer>();
			String[] parts = line.split(",");
		
			data.add(0, dataCnt); 
			int j=1;
			for (int i = 0; i < parts.length; i++) { 
					
					data.add(j, Integer.parseInt(parts[i]));
					j++;
				
			}
			this.trainingData.add(data);
		}
		nodeCnt = 0;

		nodeCnt++;
		root = new Node();
		root.nodeNum = nodeCnt;
		sc.close();

		try {
			sc = new Scanner(fileName1);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		testingData = new ArrayList<ArrayList<Integer>>();
		labledata = sc.nextLine();
		while(sc.hasNextLine())
		{
			dataCnt = dataCnt + 1;
			String line1 = sc.nextLine();
			String[] parts1 = line1.split(",");
			ArrayList<Integer> data1 = new ArrayList<Integer>();

		
			data1.add(0, dataCnt); 
			int j=1;
			for (int i = 0; i < parts1.length; i++) { 
					
					data1.add(j,Integer.parseInt(parts1[i]));
					j++;
				
			}
			this.testingData.add(data1);
		}
		
		
		try {
			sc = new Scanner(fileName2);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		validationData = new ArrayList<ArrayList<Integer>>();
		labledata = sc.nextLine();
		while(sc.hasNextLine())
		{
			dataCnt = dataCnt + 1;
			String line1 = sc.nextLine();
			String[] parts1 = line1.split(",");
			ArrayList<Integer> data1 = new ArrayList<Integer>();

		
			data1.add(0, dataCnt); 
			int j=1;
			for (int i = 0; i < parts1.length; i++) { 
					
					data1.add(j,Integer.parseInt(parts1[i]));
					j++;
				
			}
			this.validationData.add(data1);
		}

		
		
	}

	
	private void parseLabels(String labledata) {
		// TODO Auto-generated method stub

		
		this.lables.add(0,"instance label");
		String[] lable = labledata.split(",");
		int j=1;
		for (int i = 0; i < lable.length; i++) {

			    this.lables.add(j,lable[i]);
			    j++;
		}
			
	}

		private int getBestAttr(Node root, int bestAttributeMethod){
		
		double negativeCount = getCountforValueEqual(0, root);
		double positiveCount = getCountforValueEqual(1, root);
		double totalCount=positiveCount+negativeCount;
		double sizeofset = root.getNumOfInstances();
		
		ArrayList<Double> informationGain = new ArrayList<Double>();
		informationGain.add(0, -999999999999999.0);
		int maxIGattr = 0;
		
		
		if(bestAttributeMethod==0)
		{
			double probablity0 =  negativeCount /totalCount;
			double probablity1 = positiveCount /totalCount;
	
			if (probablity1 == 0) probablity1 =1;
			if (probablity0 == 0) probablity0 =1;
	
			double entropy = (-1) *( probablity1 * Math.log10(probablity1) / Math.log10(2) + 
					probablity0 * Math.log10(probablity0) / Math.log10(2));
	
			
			for (int i = 1; i < lables.size() - 2; i++) 
			{
				//checking if attribute is used or not
				if (!root.parentLabelList.contains(i))
				{	 				
					ClassCount classcount = getLableClassCnt(root, i, 0);
	
					double attr_unmatched_0 = classcount.negative_count;
					double attr_matched_0 = classcount.positive_count;
					double attr_total_0 = attr_unmatched_0+attr_matched_0;
	
					double probablitymatched0 = attr_matched_0 / attr_total_0;
					double probablityunmatched0 = attr_unmatched_0 / attr_total_0;
	
					if (probablitymatched0 == 0)
						probablitymatched0 = 1;
					if (probablityunmatched0 == 0) 
						probablityunmatched0 = 1;
	
					classcount = getLableClassCnt(root, i, 1);
	
					double attr_unmatched_1 = classcount.negative_count;
					double attr_matched_1 = classcount.positive_count;
					double attr_total_1 = attr_unmatched_1+attr_matched_1;
	
					double probablitymatched1 = attr_matched_1 /attr_total_1;
					double probablityunmatched1 = attr_unmatched_1 / attr_total_1;
	
					if (probablitymatched1 == 0) 
						probablitymatched1 = 1;
					if (probablityunmatched1 == 0) 
						probablityunmatched1 = 1;
	
					double attr_entropy_0 = (-1 * probablityunmatched0 * Math.log(probablityunmatched0) / Math.log(2)) + 
							(-1 * probablitymatched0 * Math.log(probablitymatched0) / Math.log(2));
					double attr_entropy_1 = (-1 * probablityunmatched1 * Math.log(probablityunmatched1) / Math.log(2)) + 
							(-1 * probablitymatched1 * Math.log(probablitymatched1) / Math.log(2));
	
					double IG = entropy - (attr_total_0/sizeofset * attr_entropy_0) - (attr_total_1/sizeofset * attr_entropy_1);
	
					informationGain.add(i, IG);
				}	
				else
					informationGain.add(i, -999999999999999.0);
			}
			maxIGattr = getmaxIGattr(informationGain);
			return maxIGattr;
		}
		else{
			//variance impurity
			
			double VI_s = (positiveCount/totalCount)*(negativeCount/totalCount);
			for (int i = 1; i < lables.size() - 2; i++) 
			{
				//checking if attribute is used or not
				if (!root.parentLabelList.contains(i))
				{
					ClassCount classcount = getLableClassCnt(root, i, 0);
					
					double attr_unmatched_0 = classcount.negative_count;
					double attr_matched_0 = classcount.positive_count;
					double attr_total_0 = attr_unmatched_0+attr_matched_0;
	
					double probablitymatched0 = attr_matched_0 / attr_total_0;
					double probablityunmatched0 = attr_unmatched_0 / attr_total_0;
					
					double VI_s_0=(attr_total_0/totalCount)*(probablitymatched0*probablityunmatched0);
					
					
					classcount = getLableClassCnt(root, i, 1);
					double attr_unmatched_1 = classcount.negative_count;
					double attr_matched_1 = classcount.positive_count;
					double attr_total_1 = attr_unmatched_1+attr_matched_1;
	
					double probablitymatched1 = attr_matched_1 / attr_total_1;
					double probablityunmatched1 = attr_unmatched_1 / attr_total_1;
					
					double VI_s_1=(attr_total_1/totalCount)*(probablitymatched1*probablityunmatched1);
					
					double VI_s_x = VI_s_0+VI_s_1;
					
					double IG = VI_s-VI_s_x;
					informationGain.add(i, IG);
					
				}
				else
					informationGain.add(i, -999999999999999.0);
			}
			maxIGattr = getmaxIGattr(informationGain);
			return maxIGattr;
			}
		
			
			
			
			
		
		}

	private void makeTree(Node root, int bestAttributeMethod) {
		// TODO Auto-generated method stub

		if (root!= null) 
		{   
			double negativeCount = getCountforValueEqual(0, root);
			double positiveCount = getCountforValueEqual(1, root);
		
			//Checking if node is classified or not
			if (!(negativeCount ==0 || positiveCount==0 ))
			{	
				//checking if all attributes are used or not
				if (!(root.parentLabelList.size() == lables.size() -2))
				{	
					int maxIGattr=getBestAttr(root,bestAttributeMethod);
					
					ClassCount cnt1 = getLableClassCnt(root, maxIGattr, 0);
					root.setNodeName(lables.get(maxIGattr));
					root.setLableNum(maxIGattr);
					root.setNegativeZero(cnt1.negative_count);
					root.setpositiveZero(cnt1.positive_count);
					cnt1 = getLableClassCnt(root, maxIGattr, 1);
					root.setNegativeOne(cnt1.negative_count);
					root.setPositiveOne(cnt1.positive_count);

					nodeCnt++;
					root.left = new Node();
					root.left.parent = root;
					root.left.nodeNum = nodeCnt;
					root.left.setInstanceData(trainingData,root.instanceList,root.lableNum,0,root.left.instanceList);
					root.left.setParentLableList(root); 
					if ((root.getNegativeZero() ==0 || root.getpositiveZero() ==0))
					{
						if (root.getNegativeZero() == 0)
							root.left.classLabel = 1;
						else
							root.left.classLabel = 0;
						root.left.isLeaf = 1;    
					}
					else
						makeTree(root.left,bestAttributeMethod);

					nodeCnt++;
					
					root.right = new Node();
					root.right.parent = root;
					root.right.nodeNum=nodeCnt;
					root.right.setInstanceData(trainingData,root.instanceList,root.lableNum,1,root.right.instanceList);
					root.right.setParentLableList(root); 
					if ((root.getNegativeOne() ==0 || root.getPositiveOne() == 0))
					{
						if (root.getNegativeOne() == 0)
							root.right.classLabel = 1;
						else
							root.right.classLabel = 0;
						root.right.isLeaf = 1;
					}
					else
						makeTree(root.right,bestAttributeMethod);
				}
				else
				{   //this is the leaf node as all attributes are used up.
					
					root.isLeaf = 1;
					if (positiveCount > negativeCount) 
						root.classLabel = 1; 
					else 
						root.classLabel = 0;

				}
			}
			else
			{
				if (negativeCount > 0)
				{
					root.isLeaf = 1;
					root.classLabel = 1;
				}
				else
				{
					root.isLeaf = 1;
					root.classLabel = 0;
				}
			}
		}
		else
		{   
			return;
		}
	}

	private int getmaxIGattr(ArrayList<Double> iG) {
		// TODO Auto-generated method stub
		double max;
		int maxIndex = 1;
		max = iG.get(1);

		for (int i = 2; i < iG.size(); i++) {
			if (iG.get(i) > max) { 
				max = iG.get(i);
				maxIndex = i;
			}
		}
		return maxIndex;
	}

	private ClassCount getLableClassCnt(Node root, int attributeNum, int AttributeVal) {
		// TODO Auto-generated method stub
		ClassCount classcount = new ClassCount();

		for (int i = 0; i < trainingData.size(); i++) {
			if(root.instanceList.contains(trainingData.get(i).get(0)))
			{
				if(trainingData.get(i).get(attributeNum) == AttributeVal && trainingData.get(i).get(trainingData.get(i).size()-1) == 1)
					classcount.positive_count++;

				if(trainingData.get(i).get(attributeNum) == AttributeVal && trainingData.get(i).get(trainingData.get(i).size()-1) == 0)
					classcount.negative_count++;
			}
		}
		return classcount;
	}

	private int getCountforValueEqual(int val, Node root) {
		// TODO Auto-generated method stub

		/* Get count of instances in root 
		 * for which class value equal to classVal*/

		int count = 0;
		for (int i = 0; i < this.trainingData.size(); i++) {
			if(root.instanceList.contains(trainingData.get(i).get(0)))
				if(trainingData.get(i).get(trainingData.get(i).size()-1) == val)
					count++;
		}
		return count;
	}

	private void buildTree(Node root, int bestAttributeMethod) {
		// TODO Auto-generated method stub

		/* Node root is a root node for decision tree 
		 * setup all required data for it*/

		//add all instances as available instances for root node
		for (int i = 0; i < trainingData.size(); i++) 			
			root.instanceList.add(trainingData.get(i).get(0));

		//call recursive nodes creation method based on ID3 logic
		this.makeTree(root,bestAttributeMethod);				
	}

	private void printTree(Node node, String seperator) {
		// TODO Auto-generated method stub

		if(node!=null)
		{   seperator = seperator + "|";
		if (node.isLeaf == 0)
		{
			System.out.println("");	
			System.out.print(seperator + node.NodeName + " = 0 :");
		}
		else if (node.isLeaf == 1)
		{
			System.out.print(node.classLabel);
		}

		printTree(node.left,seperator);

		if (node.isLeaf == 0)
		{
			System.out.println("");	
			System.out.print(seperator + node.NodeName + " = 1 :");
		}

		printTree(node.right,seperator);

		}
	}

	private void printTreeInorder(Node node) {
		// TODO Auto-generated method stub
        //used to count leaf nodes in tree
		
		if(node!= null)
		{   
			if(node.isLeaf == 1)
				leafNodes.add(node);
			//System.out.println(tempNode.data + " ");
			printTreeInorder(node.left);
			printTreeInorder(node.right);
		}

	}

	public double calculateAccuracy(Node root,ArrayList<ArrayList<Integer>> data)
	{
		double correctHit = 0;
		//Node temp = new Node()
		for (int i = 0; i < data.size(); i++) {
			Node temp=root;
			while(temp.isLeaf == 0)
			{
				if(data.get(i).get(temp.lableNum) == 0)
					temp = temp.left;
				else
					temp = temp.right;
			}

			if (temp.classLabel == data.get(i).get(data.get(i).size()-1))
				correctHit++;
		}
		//System.out.println("Correct hit"+correctHit);
		double accuracy = (double)correctHit / data.size() * 100;
		return accuracy;
	}
	
	public Node pruning(Node root,int L,int K){
		
		Node Dbest= new Node();
		Dbest = root;
		copyTree(root,Dbest);
		double accuracy = calculateAccuracy(Dbest, this.validationData);
		Node temp= new Node();
		for(int i=0;i<L;i++)
		{
			//temp=root;
			copyTree(root,temp);
			Random r = new Random();
			int M = r.nextInt(K)+1;
			//System.out.println("M:"+M);
			//int M = (int) (Math.random()* (K + 1));
			for(int j=0;j<M;j++){
				//System.out.println("Node Count:"+ID3.nodeCnt);
				//System.out.println("Leaf Nodes:"+this.leafNodes.size());
				int N = numberofNonLeafnode(temp);
				 //int N=this.nonLeafNodes;
				//int N = ID3.nodeCnt-this.leafNodes.size();
				if(N>1){
					//System.out.println("N:"+N);
					int P = r.nextInt(N)+1;
					//System.out.println("P:"+P);
					getNodefromNodeNum(temp,P);
					//System.out.println("Node returned:"+tempNode.NodeName + " "+tempNode.nodeNum);
					this.nonLeafNodes=0;
				}
				else
					break;
			}
			
			double post_accuracy = calculateAccuracy(temp, this.validationData); 
			if(post_accuracy>accuracy)
				copyTree(temp,Dbest);
			
			
			
		}
		return Dbest;		
	}
	
	


	private int numberofNonLeafnode(Node temp) {
		// TODO Auto-generated method stub
		nonLeafNodes =0;
		if(temp.left==null || temp.right==null)
			return 0;
		else
		{
			nonLeafNodes++;
			nonLeafNodes = nonLeafNodes + numberofNonLeafnode(temp.left)+numberofNonLeafnode(temp.right);
		}
		return nonLeafNodes;
	}


	private void copyTree(Node root, Node dbest) {
		// TODO Auto-generated method stub
		
		dbest.setIsLeaf(root.getIsLeaf());
		dbest.setNodeName(root.getNodeName());
		dbest.setLableNum(root.getLableNum());
		dbest.setnodeNum(root.getnodeNum());
		dbest.setNegativeOne(root.getNegativeOne());
		dbest.setNegativeZero(root.getNegativeZero());
		dbest.setPositiveOne(root.getPositiveOne());
		dbest.setpositiveZero(root.getpositiveZero());
	
		if(root.isLeaf!=1){
			dbest.setLeft(root.getLeft());
			dbest.left.parent=dbest;
			dbest.setRight(root.getRight());
			dbest.right.parent=dbest;
			
			
			
			copyTree(root.getLeft(),dbest.getLeft());
			copyTree(root.getRight(),dbest.getRight());
		}
	}


	private void getNodefromNodeNum(Node temp,int p) {
		// TODO Auto-generated method stub
		
		//Node temp = node;
		if(temp!=null){
		if(temp.isLeaf!=1){
		
		if(temp.nodeNum==p){
			temp.isLeaf = 1;
			if (temp.left != null)
			{
				temp.left = null;
				ID3.nodeCnt --;
			}
			
			if (temp.right != null)
			{
				temp.right = null;
				ID3.nodeCnt --;
			}
			if (temp.positiveZero + temp.PositiveOne > temp.NegativeZero + temp.NegativeOne)
				temp.classLabel = 1;
			else
				temp.classLabel = 0;
			
			//return;
			
		}
		else{
		 getNodefromNodeNum(temp.getLeft(), p);
		getNodefromNodeNum(temp.getRight(), p);
		}
		
		}
			
		}
	}


	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		if(args.length<6)
		{
			System.out.println("Please provide all the arguments");
			System.exit(0);
			
		}
		
		File file = new File(args[2]);
		File file1 = new File(args[3]);
		File file2 = new File(args[4]);
		int L = Integer.parseInt(args[0]);
		int K = Integer.parseInt(args[1]);
		ID3 id3 = new ID3(file,file1,file2);
		ID3 id3_1 = new ID3(file,file1,file2);

		String to_print = args[5];
		//display loaded data
		//		id3.dispData();
		
		System.out.println("Best Attribute by Entropy method");
		
		id3.buildTree(id3.root,0);
		//System.out.println("Tree creation completed");
	//	id3.printTree(id3.root, "");
		id3.printTreeInorder(id3.root);
		double accuracy = id3.calculateAccuracy(id3.root,id3.trainingData);

		System.out.println("\nOutput Summary");
		
		System.out.println("Total number of nodes in a tree = " + ID3.nodeCnt);
		System.out.println("Total number of leaf nodes in a tree = " + id3.leafNodes.size());
		System.out.println("Accuracy of the model on training dataset = " + Math.round(accuracy) + "%\n\n");

		accuracy = id3.calculateAccuracy(id3.root,id3.testingData);

	
		System.out.println("Accuracy of the model on testing dataset = " + (accuracy) + "%");

		
		accuracy = id3.calculateAccuracy(id3.root,id3.validationData);
		
		System.out.println("Accuracy of the model on validation dataset = " + (accuracy) + "%");
		
		Node dBest = id3.pruning(id3.root, L, K);
		System.out.println("Post Pruning Accuracy: "+id3.calculateAccuracy(dBest, id3.validationData) );
		if(to_print.equalsIgnoreCase("yes"))
			id3.printTree(id3.root, "");
		
		System.out.println("------------------------------------------------------------------------");
		System.out.println("Best Attribute by variance impurity method");
		id3_1.buildTree(id3_1.root,1);
		
		//id3_1.printTree(id3_1.root, "");
		id3_1.printTreeInorder(id3_1.root);
		 accuracy = id3_1.calculateAccuracy(id3_1.root,id3_1.trainingData);

		System.out.println("\nOutput Summary");
	
		System.out.println("Total number of nodes in a tree = " + ID3.nodeCnt);
		
		System.out.println("Accuracy of the model on training dataset = " + Math.round(accuracy) + "%\n\n");

		accuracy = id3_1.calculateAccuracy(id3_1.root,id3_1.testingData);

	
		System.out.println("Accuracy of the model on testing dataset = " + (accuracy) + "%");
		
		accuracy = id3_1.calculateAccuracy(id3_1.root,id3_1.validationData);

		
		System.out.println("Accuracy of the model on validation dataset = " + (accuracy) + "%");

		Node dBest1 = id3.pruning(id3_1.root, L, K);
		System.out.println("Post Pruning Accuracy: "+id3.calculateAccuracy(dBest1, id3_1.validationData) );
		if(to_print.equalsIgnoreCase("yes"))
			id3_1.printTree(id3_1.root, "");
	
		}
}
