package observable;

import io.reactivex.Observable;

public class Just {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	 Observable.just("me").subscribe(e-> System.out.println(e));
	}

}
