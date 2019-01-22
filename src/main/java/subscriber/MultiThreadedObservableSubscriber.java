package subscriber;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * https://dzone.com/articles/rx-java-subscribeon-and
 * @author manojain
 *
 */
		

public class MultiThreadedObservableSubscriber {

	
  public static void main(String...a) throws InterruptedException {
		
		ExecutorService exeService = Executors.newFixedThreadPool(5);
	
		//processPage(5).subscribeOn(Schedulers.from(exeService)).subscribe(s -> System.out.println(s));
		
		getPageNumber(5).forEach(map -> processPage(map).
				subscribeOn(Schedulers.from(exeService))
				.subscribe(s -> {
					System.out.println(s);	
				}));
		
		
		getPageNumber(5).flatMap(map -> processPage(map).subscribeOn(Schedulers.from(exeService))).subscribe(s -> System.out.println(s));
		
		exeService.shutdown();
		
	}
  
  
  private static Observable<Integer> getPageNumber(int totalPage) {
	  
	  Observable<Integer> obs = Observable.create(s -> {
		   System.out.println(Thread.currentThread().getName() + " Getting pages");
		   //System.out.println(Thread.currentThread().getName() + " getting page number " + p);
		  IntStream.range(1, totalPage + 1).forEach(p -> { s.onNext(p);});
		  s.onComplete();
		  
	  });
	  
	  return obs;
  }
  
 private static Observable<Integer> processPage(int pageNumber) {
	  
	  Observable<Integer> obs = Observable.create(s -> {
		  
		  System.out.println(Thread.currentThread().getName() + " Acting on page number " + pageNumber);
		  
		  //IntStream.range(1, pageNumber + 1).forEach(p -> { s.onNext(p);});
		  s.onNext(pageNumber);
		  s.onComplete();
		  
	  });
	  
	  return obs;
  }
  
}
