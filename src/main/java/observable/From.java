package observable;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class From {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

     List<String> list = new ArrayList<>();
     list.add("hi");
     list.add("how");
     list.add("are you");
		
	 Observable.fromIterable(list).subscribe(o-> {
		 System.out.println(o);
	 });
	 
	 Observable.fromArray("hi", "how", "are you").subscribe(o-> {
		 System.out.println(o);
	 });
	 
	}
	

}
