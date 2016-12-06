import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.UUID;

public class Component extends UnicastRemoteObject implements Component_RMI{
	
	private static final long serialVersionUID = 1L;
	int Pro_id;
	int Total_Pro_Num;
	UUID id = UUID.randomUUID();
	int pro_Level;
	boolean isCandidate;
	Component_RMI proc[];
	CopyOnWriteArrayList<Component_RMI> eRest = new CopyOnWriteArrayList<Component_RMI>();
	CopyOnWriteArrayList<Component_RMI> eSent = new CopyOnWriteArrayList<Component_RMI>();
	int test_num = 0;
	CopyOnWriteArrayList<Messages> candidateMsg = new CopyOnWriteArrayList<Messages>();
	int comparison = 0;
	int ackture = 0;
	int ackfalse = 0;
	int t = 0;
	
	protected Component(int i, int j) throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
		Pro_id = i;
		pro_Level = -1;
		Total_Pro_Num = j;
		proc = new Component_RMI[j];
		int bool = (int)(Math.random()*2);
		if(bool == 0) isCandidate = false;
		if(bool == 1) isCandidate = true;
	}

	@Override
	public void setProcessesNetwork() throws RemoteException{
		// TODO Auto-generated method stub
		for(int i=0; i<Total_Pro_Num; i++){
			try{
				int port = i+1099;
				proc[i] = (Component_RMI) Naming.lookup("rmi://localhost:"+port+"/AFEK"+i);
			} catch(NotBoundException e){
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
			}
		}
		
		for(Component_RMI i:proc){
			eRest.add(i);
		}
		eRest.remove(Pro_id);	
	}
	
	@Override
	public boolean IsCandidate() throws RemoteException {
		// TODO Auto-generated method stub
		return isCandidate;
	}
	
	@Override
	public int getprocid() throws RemoteException {
		// TODO Auto-generated method stub
		return Pro_id;
	}
	
	@Override
	public void startcandidate() throws RemoteException {
		// TODO Auto-generated method stub
		pro_Level += 1;
		int round = pro_Level/2 + 1;
		if(isCandidate){
			if(pro_Level%2 == 0){
				if(eRest.size() == 0){
					System.out.println("Pro "+Pro_id+"! I am Elected! "+id.getMostSignificantBits());
					return;
				}
				else{
					System.out.println("This is round "+round+". "+" --The process"+Pro_id+" still have "+eRest.size()+" pro left to test.");
					eSent.clear();
					test_num = Math.min((int)(Math.pow(2, pro_Level/2)), eRest.size());
					for(int i= 0; i<test_num; i++){
						int j = (int)(Math.random()*eRest.size());
						eSent.add(eRest.remove(j));
					}

					System.out.print("I want to check: ");
					for(Component_RMI p : eSent){
						System.out.print(" "+p.getprocid());
					}
					System.out.println(" now.");

					for(Component_RMI p : eSent){
						Messages msg = new Messages(Pro_id);
						msg.level= pro_Level;
						msg.id = id;
						p.compareTime();
						Thread tr = new Thread("t_"+(t++)){ 
							public void run(){ 
 								try{ 
 									Thread.sleep(1000);
 									p.receiveRequest(msg);
 								} catch (RemoteException e) { 
									e.printStackTrace(); 
 								} catch (InterruptedException e) {
									e.printStackTrace();
								}  

 							} 
 						}; 
 						tr.start();
					}		
				}
			}
			else{
				System.out.println(round+" round passed!");
				startcandidate();
			}
		}
		else{
			System.out.println("I am not candidate any more!");
		}
	}
		
	public void receiveRequest(Messages msg) throws RemoteException{

		candidateMsg.add(msg);
		if(candidateMsg.size() == comparison){
			int maxLevel =candidateMsg.get(0).level;
			int link_id = candidateMsg.get(0).pro_id;
			UUID maxid = candidateMsg.get(0).id;
			for(Messages s: candidateMsg){
				if(s.level > maxLevel){
					maxLevel =s.level;
					link_id = s.pro_id;
					maxid = s.id;
				}
				else if(s.level == maxLevel){
					if(s.id.getMostSignificantBits() > maxid.getMostSignificantBits()){
						maxLevel =s.level;
						link_id = s.pro_id;
						maxid = s.id;
					}
				}
			}
			comparison = 0;
			System.out.println("       ************       ");
			for(Messages s: candidateMsg){
				System.out.println("Receive from "+s.pro_id+" but max is "+link_id);
			}
			System.out.println("--------------------------");
			if(maxLevel>pro_Level||(maxLevel==pro_Level &&
					maxid.getMostSignificantBits()>id.getMostSignificantBits())){
				pro_Level = maxLevel;
				id = maxid;
				proc[link_id].acknowledge(true);//to be continued	
			}
			else{
				proc[link_id].acknowledge(false);
			}
			
			for(Messages s: candidateMsg){
				if(s.pro_id != link_id){
					proc[s.pro_id].acknowledge(false);
				}
			}
			candidateMsg.clear();	
			if(!isCandidate){
				pro_Level++;
			}
		}
	}
	
	@Override
	public void compareTime() throws RemoteException {
		// TODO Auto-generated method stub
		comparison++;
	}
	
	@Override
	public void acknowledge(boolean ack) throws RemoteException {
		// TODO Auto-generated method stub
		if(ack){
			ackture++;
		}
		else{
			ackfalse++;
		}
		if(ackture+ackfalse == test_num){
			if(ackture < test_num){ 
				isCandidate =false;
				ackture = 0;
				ackfalse = 0;
				startcandidate();
			}
			else{
				ackture = 0;
				ackfalse = 0;
				startcandidate();
			}
		}
	}

	@Override
	public long getid() throws RemoteException {
		// TODO Auto-generated method stub
		return id.getMostSignificantBits();
	}

}
