
package Afek;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class Component extends UnicastRemoteObject implements Component_RMI {

	
	private int Pro_id;
	private int[] candidateArray;
	private int pro_Level;
	private int owner_Level;
	private int ackture = 0;
	private int ackfalse = 0;
	private int k = 0;   //min(pow(2,level/2),abs(E)), where E is the number of links connected to the node
	private boolean isCandidate = false;
	UUID id = UUID.randomUUID();
	UUID ownerID;
	
	private static final long serialVersionUID = 1L;
	
	Component_RMI proc[] = new Component_RMI[Component_main.Total_Pro_Num];
	//Rest links connected to the node
	ArrayList<Component_RMI> eRest = new ArrayList<Component_RMI>();
	ArrayList<Component_RMI> eSent = new ArrayList<Component_RMI>();
	ArrayList<Component_RMI> eTemp = new ArrayList<Component_RMI>();
	ArrayList<Messages> candidates = new ArrayList<Messages>();
	ArrayList<Messages> allRequests = new ArrayList<Messages>();
	

	protected Component(int i) throws RemoteException {
		
		this.Pro_id = i;
		this.pro_Level = -1;
		this.owner_Level = pro_Level;
		this.ownerID = this.id;
	}


	@Override
	public void startcandidate() throws RemoteException {
		if (isCandidate) {
			pro_Level += 1;
			//if level is an even number
			if (pro_Level % 2 == 0) {
				//no link connected to the node.
				if (eRest.size() == 0) {
					System.out.println("Pro " + Pro_id + " Elected! \nIt's unique id number is " + id.getMostSignificantBits());
					return;
					//there are links connected to the node.
				} else{
					k = Math.min((int)(Math.pow(2, pro_Level/2)), eRest.size());
					Component_RMI[] c = new Component_RMI[k];
					int size =eRest.size();
					
					for(int i= 0; i<k; i++){
						int j = (int)(Math.random()*size);
						c[i] = eRest.get(j);
						for(Iterator<Component_RMI> eRestit = eRest.iterator(); eRestit.hasNext();){
							if(eRestit.next() == c[i]){
								eSent.add(c[i]);
								eRestit.remove();
							}
						}
						size--;
						//eSent.add(eRest.remove(0));
					}
					
					for (Iterator<Component_RMI> eSentit = eSent.iterator(); eSentit.hasNext();) {
						Messages msg = new Messages(getprocid());
						msg.level = pro_Level;
						msg.id = id;
						try {
							eSentit.next().requestElection(msg);
						} catch (RemoteException e) {

						}
					}
					
					for (Iterator<Component_RMI> eRestit = eRest.iterator(); eRestit.hasNext();) {
						Messages msg = new Messages(getprocid());
						msg.level = -1;
						msg.id = id;
						try {
							eRestit.next().requestElection(msg);
						} catch (RemoteException e) {

						}
					}

					for (Iterator<Component_RMI> eTempit = eTemp.iterator(); eTempit.hasNext();) {
						Messages msg = new Messages(getprocid());
						msg.level = -1;
						msg.id = id;
						try {
							eTempit.next().requestElection(msg);
						} catch (RemoteException e) {

						}
					}

					for (int i = 0; i < eSent.size(); i++) {
						eTemp.add(eSent.get(i));
					}
					eSent.clear();
				}
			}
			// The level is an odd number.
		} else {
			for (Component_RMI p : proc) {
				if (p.getprocid() != Pro_id) {
					Messages msg = new Messages(getprocid());
					msg.level = -1;
					msg.id = id;
					p.requestElection(msg);
				}
			}

		}

	}

	public void requestElection(Messages msg) throws RemoteException {

		allRequests.add(msg);

		if (msg.level > -1) {

			candidates.add(msg);

		}

		if (allRequests.size() == proc.length - 1) {

			allRequests.clear();

			ordinary();
		}

	}

	@Override
	public void ordinary() throws RemoteException {
		// TODO Auto-generated method stub
		if (candidates.size() > 0) {

			int maxLevel = candidates.get(0).level;
			int link_id = candidates.get(0).pro_id;
			UUID maxid = candidates.get(0).id;

			for (Messages s : candidates) {
				if (s.level > maxLevel) {
					maxLevel = s.level;
					link_id = s.pro_id;
					maxid = s.id;
				} else if (s.level == maxLevel) {
					if (s.id.getMostSignificantBits() > maxid.getMostSignificantBits()) {
						maxLevel = s.level;
						link_id = s.pro_id;
						maxid = s.id;
					}
				}
			}

			candidates.clear();

			if (maxLevel > owner_Level
					|| (maxLevel == owner_Level && maxid.getMostSignificantBits() > ownerID.getMostSignificantBits())) {
				owner_Level = maxLevel;
				ownerID = maxid;
				proc[link_id].acknowledge(true);// to be continued

			} else {
				proc[link_id].acknowledge(false);
			}

			for (int i = 0; i < proc.length; i++) {
				if ((i != Pro_id) && (i != link_id)) {
					proc[i].acknowledge(false);
				}
			}
		}

		else {
			for (int i = 0; i < proc.length; i++) {
				if (i != Pro_id)
					proc[i].acknowledge(false);
			}
		}

		if (owner_Level > -1)
			owner_Level++;

	}

	@Override
	public void setProcessesNetwork(Component_RMI[] proc) throws RemoteException {
		// TODO Auto-generated method stub
		this.proc = proc;
		for (Component_RMI i : proc) {
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
		if (ack) {
			ackture++;
		} else {
			ackfalse++;
		}
		if (ackture + ackfalse == proc.length - 1) {
			if (ackture < k)
				isCandidate = false;
			else {
				if (isCandidate) {
					pro_Level++;
					owner_Level = pro_Level;
				}
			}
			ackture = 0;
			ackfalse = 0;
			startcandidate();
		}

	}

	@Override
	public void setCandidate() throws RemoteException {
		isCandidate = true;
	}

	@Override
	public long getid() throws RemoteException {
		return id.getMostSignificantBits();
	}

}