package rx;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import io.reactivex.Observable;

public class TaskScatter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long startTime = System.currentTimeMillis();
		ExecutorService executorService  = Executors.newFixedThreadPool(6);
		ForkJoinPool fjPool = new ForkJoinPool(4);
		List<Observable<String>> obs =   IntStream.range(0, 6).parallel()
				.boxed()
				.map(i-> generateTask(i, executorService)).collect(Collectors.toList()) ;
		
		//fjPool.submit(() -> obs.stream());
		
		Observable<List<String>> merged = Observable.merge(obs).toList().toObservable();
		
		merged.subscribe(l -> System.out.print(l));
		
		System.out.println("Total time taken " + (System.currentTimeMillis() - startTime));
		fjPool.shutdown();
	}

	
	
	private static Observable<String> generateTask(int i, ExecutorService executorService) {
		
		return Observable.<String>create(s-> {
			
			   Thread.currentThread().sleep(2000);
			   
			   if (i == 5) {
				   throw new RuntimeException("Value found to be " + i);
			   }
			
			   s.onNext(i + " test");
			   s.onComplete();
		}).onErrorReturn(e -> e.getMessage());//.subscribeOn((Schedulers.from(executorService)));
		
	}
}
