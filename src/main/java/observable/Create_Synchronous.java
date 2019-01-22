package observable;

import java.util.stream.IntStream;

import io.reactivex.Observable;

public class Create_Synchronous {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
	 Observable<Integer> obs =	Observable.<Integer>create( subscriber -> {
			
					IntStream.range(0, 6).boxed().forEach(e -> subscriber.onNext(e));
					
					subscriber.onComplete();
		
			}
				
		);
	 
	 
	 	obs.subscribe(a -> System.out.println(a), e -> System.out.println(e));
	 
	}

}
