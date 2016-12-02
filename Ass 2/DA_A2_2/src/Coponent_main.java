Skip to content
This repository
Search
Pull requests
Issues
Gist
 @Andyteng
 Unwatch 1
  Star 0
  Fork 0 Andyteng/DA_A1
 Code  Issues 0  Pull requests 0  Projects 0  Wiki  Pulse  Graphs  Settings
Branch: lmf_code Find file Copy pathDA_A1/Ass 2/lmf_code/Component_main.java
9aa7835  39 minutes ago
@lmf5103510 lmf5103510 latest ver
1 contributor
RawBlameHistory     
110 lines (86 sloc)  2.34 KB
package Afek;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Arrays;


public class Component_main {
	
	public static int randomNumber(){
		int i = (int) (Math.random() * Total_Pro_Num);
		return i;
	}
	
	//Generate n different number
	public static int[] randomCommon(int n){  
 
	    int[] result = new int[n];  
	    int count = 0;  
	    while(count < n) {  
	        int num = randomNumber()+1;  
	        boolean flag = true;  
	        for (int j = 0; j < n; j++) {  
	            if(num == result[j]){  
	                flag = false;  
	                break;  
	            }  
	        }  
	        if(flag){  
	            result[count] = num;  
	            count++;  
	        }  
	    }  
	    for (int j = 0; j < n; j++) {
	    	result[j] -= 1;
	    }
	    return result;  
	}  
	
	public static int Total_Pro_Num = 10;
	
	public static void main(String[] args) throws Exception {
		Component c;
		System.setSecurityManager(new RMISecurityManager());
		try{
			LocateRegistry.createRegistry(1099);
		} catch (RemoteException e){
			e.printStackTrace();
		}
		
		for(int i=0; i<Total_Pro_Num; i++){
			c = new Component(i);
			Naming.rebind("rmi://localhost/AFEK"+i, c);
		}
		
		Component_RMI[] proc = new Component_RMI[Total_Pro_Num];
		
		boolean ready = false;
		while(!ready){
			try{
				for(int i=0; i< Total_Pro_Num; i++){
					proc[i] = (Component_RMI) Naming.lookup("rmi://localhost/AFEK"+i);
				}
				ready = true;
			}catch (NotBoundException e){
				System.out.println("Not ready for all!");
			}
		}
		
		//Specify how many process participate earliest		
		int startnum = randomNumber() + 1; 
		//Decide which process start earliest
		int[] candidateArray = randomCommon(startnum);
		System.out.println(startnum);
		System.out.println(Arrays.toString(candidateArray));
		
		for(int i =0; i<Total_Pro_Num; i++){
			proc[i].setProcessesNetwork(proc);
			System.out.println(proc[i].getid());
		}
		
		
		for(int i=0; i<startnum; i++){
			Component_RMI daobi = proc[candidateArray[i]];
				try{
					daobi.setCandidate();
				}catch(Exception e){
					e.printStackTrace();
				}

		}
		
		for(int i =0; i<Total_Pro_Num; i++){
			try{
				proc[i].startcandidate();
			} catch(RemoteException e){
				
			}
		}
		
		
		
		
	}

}
Contact GitHub API Training Shop Blog About
© 2016 GitHub, Inc. Terms Privacy Security Status Help