public class dcLinkList {

	Fnode root;
		
	public void insert(Fnode node){
		if(root == null){
			root = node;
			root.left = root;
			root.right = root;
		}else if(root.left == root){
			root.left = node;
			root.right = node;
			node.left = root;
			node.right = root;
		}else{
			root.right.left = node;
			node.right = root.right;
			root.right = node;
			node.left = root;
		}
	}
		
		
}
	

