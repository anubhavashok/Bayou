import java.lang.*;
import java.io.*;
import java.net.*;
import java.util.*;
public class Replica
{
  protected HashSet<SongEntry> database= new HashSet<SongEntry>();        //current database version
  protected Set<SongEntry> stableDatabase;  //back up after garbage collection
  protected ArrayList<Write> writeLog=new ArrayList<Write>();
  protected HashSet<Write> committedLog= new HashSet<Write>();
  protected int id;
  protected int acceptTime=0;
  protected ArrayList<Integer> V= new ArrayList<Integer>();                 // initialize this + implement removal of nodes
  protected InetAddress svr;  		//svr who is conencted to
  protected int CSN=0;
  

  Replica(int id, String ip)
  {
    this.id=id;			//init id
    for(int i =0; i<Init.totalReplicas; i++)	//init V for total nodes = 4
    {
       Integer number = new Integer(0);
       V.add(i,number);
    }
	setServerConnection(ip);
	System.out.println("Replica id is: " +id);
  }
	
  public void setServerConnection(String add)
  {
    svr =InetAddress.getByName(add);
  }

  public void sendWrite(Write w)
  {

    try{
Thread.sleep(3000);						//wait for 3 seconds before sending to ensure process has completed processing and is waiting to receive
    Socket skt = new Socket(svr,1237);
    OutputStream os = skt.getOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(os);
    oos.writeObject(w);
    
    oos.close();
    os.close();
    skt.close();
    Thread.sleep(1000);
  	}
  	catch(Exception e)
  	{
  	}
  }
  public void sendVersionVector(ArrayList<Integer> V)
  {
    try{
Thread.sleep(3000);						//wait for 3 seconds before sending to ensure process has completed processing and is waiting to receive
    Socket skt = new Socket(svr,1230);
    OutputStream os = skt.getOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(os);
    oos.writeObject(V);
    
    oos.close();
    os.close();
    skt.close();
    Thread.sleep(1000);
  	}
  	catch(Exception e)
  	{
  	}
  }
  public void sendCSN(int CSN)
  {
    try{
Thread.sleep(3000);						//wait for 3 seconds before sending to ensure process has completed processing and is waiting to receive
    Socket skt = new Socket(svr,1236);
    OutputStream os = skt.getOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(os);
    oos.writeObject(CSN);
    
    oos.close();
    os.close();
    skt.close();
    Thread.sleep(1000);
  	}
  	catch(Exception e)
  	{
  	}
  }



  public Write receiveWriteClient() throws IOException
  {
    	ServerSocket ss= new ServerSocket(1234);
  		ss.setReuseAddress(true); 
  		ss.setSoTimeout(4000);
  		//n++;
  	try
    	{
    	 	Socket s = ss.accept();
    		s.setReuseAddress(true); 
    		s.setSoTimeout(4000);
		
    		//System.out.println(s.getSoTimeout() );
    		//System.out.println(s.getReuseAddress() );
  	   	InputStream is = s.getInputStream();
  	   	ObjectInputStream ois = new ObjectInputStream(is);
  	  	Write w=null;
  
  	  	w=(Write)ois.readObject();
  	   	is.close();
  	   	s.close();
  	   	ss.close();

  		return w;
		
  	}
  
  	catch(Exception e)
  	{
  		System.out.println(e.getLocalizedMessage());
  		return null;
  	}
	
  }
  public Write receiveWriteReplica() throws IOException
  {
    	ServerSocket ss= new ServerSocket(1237);
  		ss.setReuseAddress(true); 
  		ss.setSoTimeout(4000);
  		//n++;
  	try
    	{
    	 	Socket s = ss.accept();
    		s.setReuseAddress(true); 
    		s.setSoTimeout(4000);
		
    		//System.out.println(s.getSoTimeout() );
    		//System.out.println(s.getReuseAddress() );
  	   	InputStream is = s.getInputStream();
  	   	ObjectInputStream ois = new ObjectInputStream(is);
  	  	Write w=null;
  
  	  	w=(Write)ois.readObject();
  	   	is.close();
  	   	s.close();
  	   	ss.close();

  		return w;
		
  	}
  
  	catch(Exception e)
  	{
  		System.out.println(e.getLocalizedMessage());
  		return null;
  	}
	
  }

  public ArrayList<Integer> receiveVersionVector() throws IOException
  {
    	ServerSocket ss= new ServerSocket(1230);
  		//System.out.println(n);
  		ss.setReuseAddress(true); 
  		ss.setSoTimeout(4000);
  		//n++;
  	try
   	 {
    	 	Socket s = ss.accept();
    		s.setReuseAddress(true); 
    		s.setSoTimeout(4000);
		
    		//System.out.println(s.getSoTimeout() );
    		//System.out.println(s.getReuseAddress() );
  	   	InputStream is = s.getInputStream();
  	   	ObjectInputStream ois = new ObjectInputStream(is);
  	  	ArrayList<Integer> V=null;
  
  	  	V=(ArrayList<Integer>)ois.readObject();
  	   	is.close();
  	   	s.close();
  	   	ss.close();
  
  		return V;
  	}
  
  	catch(Exception e)
  	{
  		System.out.println(e.getLocalizedMessage());
  		return null;
  	}
	
  }


  public int receiveCSN() throws IOException
  {
    	ServerSocket ss= new ServerSocket(1236);
  		ss.setReuseAddress(true); 
  		ss.setSoTimeout(4000);
  		//n++;
  	try
    	{
    	 	Socket s = ss.accept();
    		s.setReuseAddress(true); 
    		s.setSoTimeout(4000);
		
    		//System.out.println(s.getSoTimeout() );
    		//System.out.println(s.getReuseAddress() );
  	   	InputStream is = s.getInputStream();
  	   	ObjectInputStream ois = new ObjectInputStream(is);
  	  	int w=0;
  
  	  	w=((Integer)ois.readObject()).intValue();
  	   	is.close();
  	   	s.close();
  	   	ss.close();

  		return w;
		
  	}
  
  	catch(Exception e)
  	{
  		System.out.println(e.getLocalizedMessage());
  		return 0;
  	}
	
  }




  public void receiveWriteFromClient()
  {
    Write w;
    do{
    try{
    w=receiveWriteClient();                                    //new write that was received
    w.setReplicaId(id);
	System.out.println("Id Assigned to write is: "+w.getReplicaId());
    w.setAcceptTime(acceptTime);
    writeLog.add(w);
    System.out.println("Song received from client: "+(w.getSongEntry()).getSongName());
    Integer i = V.get(id);
    i=new Integer(i.intValue() + 1);            //increment version id of current replica
    V.set(id,i);
    }
    catch (Exception e)
    {
	//System.out.println(e.getLocalizedMessage());
      break;
    }

    }while(w!=null);
    
  }
 /* public void selectToSend() throws IOException,InterruptedException
  {
    ArrayList<Integer> V = receiveVersionVector();                       //Version vector received from replica server
	System.out.println(V);
	Thread.sleep(1000);
    for(int i=0;i<writeLog.size();i++)
    {
      Write w = writeLog.get(i);
      System.out.println("Song that is being sent: "+(w.getSongEntry()).getSongName());
      if(V.get(w.getReplicaId())<=w.getAcceptTime())  //possible error comparing 2 Integers
      {
	System.out.println("sent");
        sendWrite(w);                           //send new write to the server
      }
    }
  }*/

  
  public void selectToSend() throws IOException,InterruptedException
  {
	System.out.println("Waiting on V");
    ArrayList<Integer> V=receiveVersionVector(); 	//Version vector received from replica server
	System.out.println("V rec'd" +V);
    int RCSN = receiveCSN();				//highest CSN from the replica server
    //Thread.sleep(1000);					//wait for Other server to start receiving
    
 //-----------------------//				
	//SEND COMMITTED WRITES
    if(RCSN<CSN)
    {
      for(Write w: committedLog)
      {
         if(w.getCSN()>RCSN)
         {
             sendWrite(w);
         }
      }
	//Send over all the committed writes after RCSN

    }
//-------------------------//
	//SEND TENTATIVE WRITES
    for(int i=0;i<writeLog.size();i++)
    {
      Write w = writeLog.get(i);
      System.out.println("Song that is being sent: "+(w.getSongEntry()).getSongName());
      if(V.get(w.getReplicaId())<=w.getAcceptTime()) 	 //possible error comparing 2 Integers
      {
        sendWrite(w);                          		 //send new write to the server
      }
    }
	Thread.sleep(3000);				//so that selectToSend and replica receive end at the same tiem
  }




  public void receiveWriteFromReplica() throws IOException,InterruptedException
  { System.out.println("Send V done" + V);
    sendVersionVector(V);				//sends V to replica server
    sendCSN(CSN);					//sends CSN to replica server    
    
    System.out.println("Waiting to receive write from Replica");
    Write w;
    do
    {
	    try{
	    w=receiveWriteReplica();                                    //new write received from Replica
		System.out.println("Received write with Song Name: "+w.getSongEntry().getSongName());
	    if(w.getCSN()!=-1)					// SUPPORT COMMITTED WRITES
	    {System.out.println("being committed");
              for(Write existingWrite : writeLog)
	      {
                  if((existingWrite.getReplicaId()==w.getReplicaId())&&(existingWrite.getAcceptTime()==w.getAcceptTime()))	//check if write exists in write log (if it does, basically doing committNotificaiton())
		  {
			writeLog.remove(existingWrite);
			committedLog.add(w);			//remove write from writeLog and add to committedLog
			CSN++;					//update CSN number - Assume they come in order
		  }
	      }
	      committedLog.add(w);			//if committed write doesnt exist in writeLog, add to committedLog
            }


	    writeLog.add(w);
	    Integer i = V.get(w.getReplicaId());
	    i=new Integer(i.intValue() + 1);
	    V.set(w.getReplicaId(),i);                  //update version vector value of write
	    }
	    catch (Exception e)
	    {
		System.out.println(e.getLocalizedMessage());
		Thread.sleep(1000);
	      break;
	    }

    }while(w!=null);

    //check for CSN
    
    
  }
  public void mergeWithDatabase()
  {
    for(Write w: committedLog)
    {
      if(w.getOp().equals("add"))
      {
        database.add(w.getSongEntry());
        
      }
      if(w.getOp().equals("delete"))
      {
        database.remove(w.getSongEntry());
        //increment CSN, ADD CSN TO WRITE IN WRITE LOG
      }
      if(w.getOp().equals("modify"))
      {
        for(SongEntry s : database)
        {
          if(s.getSongName().equals(w.getSongEntry().getSongName()))
          {
            database.remove(w.getSongEntry());
          }
        }
        database.add(w.getSongEntry());
      }
    }
  }

  public void displayDatabase()
  {
       
	 System.out.println("Songs in database of Replica "+id+":");
	 for(SongEntry s: database)
	 {
		System.out.println(s.getSongName());
	 }
  }
  public void displayWriteLog()
  {
       
	 System.out.println("Songs in Write Log of Replica "+id+":");
	 for(Write w: writeLog)
	 {
		System.out.println("Song Name: "+ w.getSongEntry().getSongName()+ " Operation: "+w.getOp());
	 }
  }
  public void displayCommittedLog()
  {
       
	 System.out.println("Songs in Committed Log of Replica "+id+":");
	 for(Write w: committedLog)
	 {
		System.out.println("Song Name: "+ w.getSongEntry().getSongName()+ " Operation: "+w.getOp());
	 }
  }  
}
