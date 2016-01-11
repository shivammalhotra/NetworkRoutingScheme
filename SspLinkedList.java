public class SspLinkedList {

		SspNode head;
	
		public void add(int e, int w){
			SspNode current = head;
			if(current == null){
				SspNode sn1 = new SspNode();
				sn1.setEdge(e);
				sn1.setWeight(w);
				head = sn1;
			}else{
				SspNode sn2 = new SspNode();
				sn2.setEdge(e);
				sn2.setWeight(w);
				while(current!=null){
					if(current.id !=null){
						current = current.getId();
					}else{
						current.setId(sn2);
						current = null;
					}
				}
	
			}
			
		}
		
		
		public void traverse(){
			SspNode current = head;
			while(current != null){
				System.out.print(current.edge+"("+current.weight+")");
				if(current.id!=null){
					current = current.getId();
				}else{
					current = null;
				}
			}
		}
}