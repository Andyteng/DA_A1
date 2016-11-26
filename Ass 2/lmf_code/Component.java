package Afek;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Component extends UnicastRemoteObject implements Component_RMI{
	
	static int Pro_id;
	static int id;
	int[] candidateArray;
	static int[] ambionArray;
	Component_RMI proc[] = new Component_RMI[Component_main.Total_Pro_Num];
	
	protected Component(int i) throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
		this.Pro_id = i;
		this.id = (int) Math.random()*100;
	}

	private static final long serialVersionUID = 1L;

	public static int[] randomObj(int n){  
		 
	    int[] result = new int[n];  
	    int count = 0;  
	    while(count < n) {  
	        int num = (int)(Math.random()* Component_main.Total_Pro_Num +1);
	        boolean flag = true;  
	        for (int j = 0; j < n; j++) {  
	            if(num == result[j] || num == Pro_id){  
	                flag = false;  
	                break;  
	            }  
	        }  
	        for(int i=0; i<ambionArray.length; i++){
	        	if(num == ambionArray[i]){
	        		flag =false;
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
	
	@Override
	public void candidate(Messages msg) throws RemoteException {
		// TODO Auto-generated method stub
		int num = (int) Math.pow(2, msg.level);
		int[] ambition = randomObj(num);
		
		for(int i = 0; i<num; i++){
			int count =ambition[i];
			Thread tr = new Thread("can_"+i){
				public void run(){
					try{
						msg.level +=1;
						proc[count].ordinary(msg);
					} catch(RemoteException e){
						e.printStackTrace();
					}
				}
			};
			tr.start();
		}
		
		
		
		
	}

	@Override
	public void ordinary(Messages msg) throws RemoteException {
		// TODO Auto-generated method stub
		if(msg.id >= id){
			
			
			
		}
		else{
			
		}
	}

	@Override
	public void setProcessesNetwork(Component_RMI[] proc) throws RemoteException {
		// TODO Auto-generated method stub
		this.proc = proc;
	}

	@Override
	public void setcandidateArray(int[] arr) throws RemoteException {
		// TODO Auto-generated method stub
		this.candidateArray = arr;
	}

	@Override
	public int getid() throws RemoteException {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public int getprocid() throws RemoteException {
		// TODO Auto-generated method stub
		return this.Pro_id;
	}

}
