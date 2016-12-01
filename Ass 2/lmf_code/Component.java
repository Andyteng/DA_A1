package Afek;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.UUID;


public class Component extends UnicastRemoteObject implements Component_RMI{
	
	int Pro_id;
	UUID id = UUID.randomUUID();
	UUID ownerID;
	int[] candidateArray;
	int pro_Level;
	int owner_Level;
	int ackture = 0;
	int ackfalse = 0;
	int k = 0;
	Component_RMI proc[] = new Component_RMI[Component_main.Total_Pro_Num];
	ArrayList<Component_RMI> eRest = new ArrayList<Component_RMI>();
	ArrayList<Component_RMI> eSent = new ArrayList<Component_RMI>();
	ArrayList<Component_RMI> eTemp = new ArrayList<Component_RMI>();
	ArrayList<Messages> candidates = new ArrayList<Messages>();
	ArrayList<Messages> allRequests = new ArrayList<Messages>();
	boolean isCandidate = false;
	
	protected Component(int i) throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
		this.Pro_id = i;
		pro_Level =-1;
		owner_Level =pro_Level;
		ownerID = this.id;
	}

	private static final long serialVersionUID = 1L;
	
	@Override
	public void startcandidate() throws RemoteException {
		// TODO Auto-generated method stub
		if(isCandidate){
			pro_Level += 1;
			if(pro_Level%2 == 0){
				if(eRest.size() == 0){
					System.out.println("Pro "+Pro_id+" Elected! "+id.getLeastSignificantBits());
					return;
				}
				else{
					k = Math.min((int)(Math.pow(2, pro_Level/2)), eRest.size());
					for(int i= 0; i<k; i++){
						eSent.add(eRest.remove(0));
					}
					
					for(Component_RMI p:eSent){
						Messages msg = new Messages(getprocid());
						msg.level= pro_Level;
						msg.id = id;
						p.requestElection(msg);
					}
					//problem here
					for(Component_RMI p:eRest){
						Messages msg = new Messages(getprocid());
						msg.level= -1;
						msg.id = id;
						p.requestElection(msg);
					}
					
					for(Component_RMI p:eTemp){
						Messages msg = new Messages(getprocid());
						msg.level= -1;
						msg.id = id;
						p.requestElection(msg);
					}
					
					for(int i=0; i<eSent.size(); i++){
						eTemp.add(eSent.remove(0));
					}
				}
			}
		}
		else{
			for(Component_RMI p: proc){
				if(p.getprocid() != Pro_id){
					Messages msg = new Messages(getprocid());
					msg.level= -1;
					msg.id = id;
					p.requestElection(msg);
				}				
			}
			
		}
		
	}
	
	public void requestElection(Messages msg) throws RemoteException{
		allRequests.add(msg);
		if(msg.level >-1) candidates.add(msg);
		if(allRequests.size() == proc.length -1){
			allRequests.clear();
			ordinary();
		}
		
		
	}
	

	@Override
	public void ordinary() throws RemoteException {
		// TODO Auto-generated method stub
		if(candidates.size()>0){
		
			int maxLevel =candidates.get(0).level;
			int link_id = candidates.get(0).pro_id;
			UUID maxid = candidates.get(0).id;
			
			for(Messages s: candidates){
				if(s.level > maxLevel){
					maxLevel =s.level;
					link_id = s.pro_id;
					maxid = s.id;
				}
				else if(s.level == maxLevel){
					if(s.id.getLeastSignificantBits() > maxid.getLeastSignificantBits()){
						maxLevel =s.level;
						link_id = s.pro_id;
						maxid = s.id;
					}
				}
			}
			
			candidates.clear();
			
			if(maxLevel>owner_Level||(maxLevel==owner_Level &&
			maxid.getLeastSignificantBits()>ownerID.getLeastSignificantBits())){
				owner_Level = maxLevel;
				ownerID = maxid;
				proc[link_id].acknowledge(true);//to be continued
				
			}
			else{
				proc[link_id].acknowledge(false);
			}
			
			for(int i=0; i<proc.length;i++){
				if((i!= Pro_id) && (i!= link_id)){
					proc[i].acknowledge(false);
				}
			}
		}
		
		else{
			for(int i=0; i<proc.length;i++){
				if(i!= Pro_id) proc[i].acknowledge(false);
			}
		}
		
		if(owner_Level > -1) owner_Level++;
		
	}

	@Override
	public void setProcessesNetwork(Component_RMI[] proc) throws RemoteException {
		// TODO Auto-generated method stub
		this.proc = proc;
		for(Component_RMI i:proc){
			eRest.add(i);
		}
		eRest.remove(Pro_id);
		
	}



	@Override
	public int getprocid() throws RemoteException {
		// TODO Auto-generated method stub
		return Pro_id;
	}

	@Override
	public void setLevel() throws RemoteException {
		// TODO Auto-generated method stub
		pro_Level += 1;
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
		if(ackture+ackfalse == proc.length-1){
			if(ackture <k) isCandidate =false;
			else{
				if(isCandidate) pro_Level++;
			}
			ackture =0;
			ackfalse = 0;
			startcandidate();
		}
		
			
	}

	@Override
	public void setCandidate() throws RemoteException {
		// TODO Auto-generated method stub
		isCandidate = true;
	}

}
