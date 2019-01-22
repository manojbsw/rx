package completablefuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ProjPOC {

	static ExecutorService exeService = Executors.newFixedThreadPool(5);
	public static void main(String[] args) throws InterruptedException {

		
	}

	
	 private static Integer automapVulnerabilitiesForGivenSG() {

	    	Integer totalVulsAutoMapped = 0;
	        	
	    	 CompletableFuture<List<Integer>> completableFutureVulnerabilities = fetchVMFromES();
	    	
	    	/*.subscribeOn(Schedulers.from(exeService)) )
	    	//.observeOn(Schedulers.from(exeService)) )
	    	.subscribe(mappingResult ->  {
			        
		    		Thread th = Thread.currentThread();
					th.sleep(2000);
					System.out.println("Pushing to DB/ES " + mappingResult + " Thread " + th.getName());
				
			  });*/
	    
	        return totalVulsAutoMapped;
	    }
	    
	    private static Observable<String> mergedVulnerabilityMapping(Integer data) {
	    	
	    	return  Observable.<String>create(s -> {
	 	        	
	     		String d = data + "_" + data;
	     		Thread th = Thread.currentThread();
				th.sleep(1000);
				System.out.println("Mapping VM data " + d + " Thread " + th.getName());
	        	s.onNext(d);
	  		    s.onComplete();
			});
	    }

		/**
	     * Because of large set of data in the Elastic search, we would like to process the auto mapping algorithm in batch
	     * TODO: The batch size would be governed from external configuration
	     */
	    private static CompletableFuture<List<Integer>> fetchVMFromES() {
	    	 
	    	CompletableFuture<List<Integer>> compFuture = CompletableFuture.supplyAsync(() -> {	
	    		        List<Integer> listInteger = new ArrayList<>();
		    			IntStream.range(0,8).boxed().forEach(s1 -> {
		    					Thread th = Thread.currentThread();
		    					try {
									th.sleep(1500);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		    					System.out.println("Fetching VM data " + s1 + " Thread " + th.getName());
		    					listInteger.add(s1);
		    				}
		    			);
		    			return listInteger;
					
				});
	    	
	    	return compFuture;
	    }
	    
	    /**
	     * Because of large set of data in the Elastic search, we would like to process the auto mapping algorithm in batch
	     * TODO: The batch size would be governed from external configuration
	     */
	    private static CompletableFuture<List<Integer>> fetchVMFromES2() {
	    	 
	    	
		    			IntStream.range(0,8).boxed().forEach(s1 -> {
		    					Thread th = Thread.currentThread();
		    					try {
									th.sleep(1500);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		    					System.out.println("Fetching VM data " + s1 + " Thread " + th.getName());
		    					CompletableFuture<Integer> compFuture = CompletableFuture.supplyAsync(() -> {	
		    						return s1;
		    					}, exeService);
		    					
		    				}
		    			);
					
				
	    	
	    	return null;
	    }
}
