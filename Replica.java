public class Replica
{
  private Set<SongEntry> database;        //current database version
  private Set<SongEntry> stableDatabase;  //back up after garbage collection
  private ArrayList<Write> writeLog;
  private int id;
  private int acceptTime=0;
  private ArrayList<Integer> V;                 // initialize this + implement removal of nodes

  public Write receiveWrite() throws IOException
  {
    	ServerSocket ss= new ServerSocket(1234);
  		//System.out.println(n);
  		ss.setReuseAddress(true); 
  		ss.setSoTimeout(2000);
  		//n++;
  	try
    {
    	 	Socket s = ss.accept();
    		s.setReuseAddress(true); 
    		s.setSoTimeout(2000);
    		System.out.println(s.getSoTimeout() );
    		System.out.println(s.getReuseAddress() );
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






  public void receiveWriteFromClient()
  {
    Write w=receiveWrite();                                    //new write that was received
    w.setReplicaId(id);
    w.setAcceptTime(acceptTime);
    writeLog.add(w);
    Integer i = V.get(id);
    i=new Integer(i.intValue() + 1);            //increment version id of current replica
    V.set(id,i);
  }
  public void selectToSend()
  {
    ArrayList<Integer> V;                       //Version vector received from replica server
    for(int i=0;i<writeLog.size();i++)
    {
      Write w = writeLog.get(i);
      if(V.get(w.getReplicaId())<w.getAcceptTime)  //possible error comparing 2 Integers
      {
        sendWrite(w);                           //send new write to the server
      }
    }
  }
  public void receiveWriteFromReplica()
  {
    Write w=receiveWrite();                                    //new write received from Replica
    writeLog.add(w);
    Integer i = V.get(w.getReplicaId());
    i=new Integer(i.intValue() + 1);
    V.set(w.getReplicaId(),i);                  //update version vector value of write
    //check for CSN
    
    
  }
  public void mergeWithDatabase()
  {
    for(Write w: writeLog)
    {
      if(w.getOp().equals("add"))
      {
        database.add(w.getSongEntry());
        //increment CSN, ADD CSN TO WRITE IN WRITE LOG
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
            database.delete(w.getSongEntry());
          }
        }
        database.add(w.getSongEntry());
        //increment CSN, ADD CSN TO WRITE IN WRITE LOG
      }
    }
  }
  
}
