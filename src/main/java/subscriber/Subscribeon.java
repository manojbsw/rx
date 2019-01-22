package subscriber;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class Subscribeon {

	
	public static void main(String...a) {
		
		ExecutorService exeService = Executors.newFixedThreadPool(5);
	
		Observable<Integer> obs = Observable.create(s->{
			
			IntStream.range(0, 6).forEach(p -> { System.out.println(Thread.currentThread().getName()); s.onNext(p);});
		});
		
		
		
		obs.subscribeOn(Schedulers.from(exeService)).subscribe(s -> System.out.println(Thread.currentThread().getName() + " : " + s));
		
		exeService.shutdown();
		
	}
	
	
}
