package rx;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ProjPOC {

	static ExecutorService exeService = Executors.newFixedThreadPool(5);
	
	public static void main(String[] args) throws InterruptedException {
		
		long startTime = System.currentTimeMillis();
		try {
			reactiveExample();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Exception main block" + e.getMessage());
		}
		System.out.println("Time taken " + (System.currentTimeMillis() - startTime));
		
	/*	while(true) {
			System.out.print("-");
		}*/
		exeService.awaitTermination(5, TimeUnit.MINUTES);
		exeService.shutdown();
		
		
	}

	
	
	 private static Integer reactiveExample() throws IOException{

	    	Integer totalVulsAutoMapped = 0;
	        	
	    	Observable<Integer> obervableVulnerabilities = fetchDataFromES();
	    	obervableVulnerabilities.subscribeOn(Schedulers.io())
	    	.flatMap(mapper -> mapData(mapper)
	    	.subscribeOn(Schedulers.io()) )
	    	.flatMap(vm -> saveData(vm)
	    			.subscribeOn(Schedulers.io()))
	    	//.observeOn(Schedulers.from(exeService)) )
	    	.blockingSubscribe(count ->  {
			        
		    		Thread th = Thread.currentThread();
					th.sleep(2000);
					System.out.println("Pushing to DB/ES " + count + " Thread " + th.getName());
				
			  }, throwable -> {
				  System.out.println("Got exception " + throwable.getMessage() );
				  throw new IOException(throwable);
			  });
	    
	        return totalVulsAutoMapped;
	    }
	    
	  private static Observable<Integer> saveData(String data) {
	    	
	    	return  Observable.<Integer>create(s -> {
	 	        	
	    		String val = data.split("_")[0];
	     		Integer count = Integer.parseInt(val);
	     		Thread th = Thread.currentThread();
				th.sleep(1000);
				System.out.println("Savin VM data " + count + " Thread " + th.getName());
	        	s.onNext(count);
	  		    s.onComplete();
			});
	    }
	 
	    private static Observable<String> mapData(Integer data) {
	    	
	    	return  Observable.<String>create(s -> {
	 	        	
	     		String d = data + "_" + data;
	     		Thread th = Thread.currentThread();
				th.sleep(1000);
				System.out.println("Mapping VM data " + d + " Thread " + th.getName());
	        	s.onNext(d);
	  		    s.onComplete();
			});
	    }

	    private static Observable<Integer> fetchDataFromES() {
	    	 
	    	return  Observable.<Integer>create(s -> {
	    			
	    			IntStream.range(0,8).boxed().forEach(s1 -> {
	    					Thread th = Thread.currentThread();
	    					try {
								th.sleep(1500);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	    					System.out.println("Fetching VM data " + s1 + " Thread " + th.getName());
	    					s.onNext(s1);
	    				}
	    			);
	    			
		  		    s.onComplete();
	    		});
	    }
	
}
