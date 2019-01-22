package completablefuture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class TestCompletableFuture {
	
	//static ExecutorService executorService  = Executors.newFixedThreadPool(6);

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		
		//CompletableFuture<List<Integer>> future = createComplatableFuture();
		
		//CompletableFuture<Integer> future = createComplatableFuture2();
		
		
		//int totalCount = future.thenApplyAsync(d -> mapData(d), executorService).thenApplyAsync(s -> updateData(s), executorService).get().stream().mapToInt(s ->s.intValue()).sum();
		
		//System.out.println("Total count " + totalCount);
		innerCompletableFuture();
		//future.thenAccept(d -> printData(d));

		// Create a combined Future using allOf()
		/*CompletableFuture<Void> allFutures = CompletableFuture.allOf(
				futureList.toArray(new CompletableFuture[futureList.size()])
		);
       
		
		// When all the Futures are completed, call `future.join()` to get their results and collect the results in a list -
		CompletableFuture<List<Integer>> allPageContentsFuture = allFutures.thenApply(v -> {
		   return futureList.stream()
		           .map(pageContentFuture -> pageContentFuture.join())
		           .collect(Collectors.toList());
		});
		
		
		// Count the number of web pages having the "CompletableFuture" keyword.
		CompletableFuture<Long> countFuture = allPageContentsFuture.thenApply(pageContents -> {
		    return pageContents.stream().count();
		});
		
		try {
			System.out.println("Calculated values " + countFuture.get());
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
        Thread.sleep(10000);
        
	}

	private static void innerCompletableFuture() throws InterruptedException, ExecutionException {
		
		
		  CompletableFuture.supplyAsync(() -> {
				

				System.out.println("Inside innercompletable future " + Thread.currentThread().getName());
				int totalCount = 0;
			    List<CompletableFuture<Integer>> futureList = new ArrayList<>();
				for(int i = 10; i <60; i = i +10) {
					
					CompletableFuture<Integer> future = createComplatableFutureInt(i);
					
					future = future.thenApplyAsync(d -> dataMap(d));
					futureList.add(future);
					
				}
				
				CompletableFuture<Void> allFutures = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[futureList.size()]));
				
				// When all the Futures are completed, call `future.join()` to get their results and collect the results in a list -
				CompletableFuture<List<Integer>> allPageContentsFuture = allFutures.thenApply(v -> {
				   return futureList.stream()
				           .map(pageContentFuture -> pageContentFuture.join())
				           .collect(Collectors.toList());
				});
				
				// Count the number of web pages having the "CompletableFuture" keyword.
				CompletableFuture<Integer> countFuture = allPageContentsFuture.thenApply(pageContents -> {
				    return pageContents.stream().mapToInt(s ->s.intValue()).sum();
				});
				
				try {
					totalCount = countFuture.get();
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return totalCount;
			 
		 }).thenAcceptAsync(i -> {
			
			 System.out.println("Do entry in activity with value " + i + " : " + Thread.currentThread().getName());
		 });
		
	}
	
	private static Integer   dataMap(Integer d) {
		 
		    try {
		    	System.out.println("waiting for seconds " + (d*100) + " " + Thread.currentThread().getName());
				Thread.sleep(d*100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return d;
		 
     }
	
	
	private static List<String>   mapData(List<Integer> list) {
		 
		return list.stream().map( s -> {
			 
			  try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			     System.out.println("map " + s + " : " + Thread.currentThread().getName());
			    return s+"";
			}).collect(Collectors.toList());
		 
     }
	
	private static List<Integer>   updateData(List<String> list) {
		 
		return list.stream().map( s -> {
			 
			  try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			     System.out.println("update " + s + " : " + Thread.currentThread().getName());
			    return Integer.parseInt(s);
			}).collect(Collectors.toList());
		 
     }
	
	private static Integer   countData(List<Integer> list) {
		 
		return list.stream().mapToInt(s -> s.intValue()).sum();
		 
     }
	
	
	
	 private static void printData(List<Integer> s) {
		 s.stream().forEach(t -> System.out.println(t));
		 System.out.println(" : " + Thread.currentThread().getName());
	 }
	 
	 private static CompletableFuture<List<Integer>>   createComplatableFuture() {
		 
			 return	 CompletableFuture.supplyAsync(() -> {
					
				     try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				     System.out.println("Creator " + Thread.currentThread().getName());
					 List<Integer> list = Arrays.asList(10,20,30,40);
					 return list;
					 
				 });
	 }
	 
	 
	 private static CompletableFuture<Integer>   createComplatableFutureInt(int val) {
		 
		 return	 CompletableFuture.supplyAsync(() -> {
				
			 	System.out.println("Creator " + Thread.currentThread().getName());
				 return val;
				 
			 });
		 }
}
