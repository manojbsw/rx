package observable;

import java.util.stream.IntStream;

import io.reactivex.Observable;

public class Create_Asynchronous {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
	 Observable<Integer> obs =	Observable.<Integer>create( subscriber -> {
			
		 			
					IntStream.range(0, 6).boxed().parallel().forEach(e -> subscriber.onNext(e));
					
					subscriber.onComplete();
					
					//subscriber.onError(e);
		
			}
				
		);
	 
	 
	 	obs.subscribe(a -> System.out.println(a), e -> System.out.println(e));
	 
	}

}


/*
 * 1. Create a method to fetch all the vulnerabilities mapping irrespective of security group. This will fetch all the VM which is not mapped yet (first call sequential)
 *      Fetch all VM for a given size and page number -> Once data available run it through all the SG's
 *      for(page number ) {
 *           
 *          for(for all SG) {
 *          
 *          }
 *      }
 * 
 * 2. Based on the security group filter out VM's add it to collection.  At the end we will have list of VM for a given security group. Like Map<SG, List<VM>>  (multithreaded)
 * 3. Based on the security group returned from above step: Get all CVIDs fetch the remediation content for a given CVids and map it to the respective VM
 * 4. Add/update DB and ES
 * 
 *
 * 
 * https://dzone.com/articles/rx-java-subscribeon-and
 */
