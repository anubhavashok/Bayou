public class Replica
{
  private ArrayList<SongEntry> database;        //current database version
  private ArrayList<SongEntry> stableDatabase;  //back up after garbage collection
  private ArrayList<Write> writeLog;
  private int id;
  private int acceptTime=0;
  private ArrayList<Integer> V;                 // initialize this + implement removal of nodes

  public void receiveWriteFromClient()
  {
    Write w;                                    //new write that was received
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
    Write w;                                    //new write received from Replica
    writeLog.add(w);
    Integer i = V.get(w.getReplicaId());
    i=new Integer(i.intValue() + 1);
    V.set(w.getReplicaId(),i);                  //update version vector value of write
    
    
    
  }
  public void mergeWithDatabase()
  {
    for(Write w: writeLog)
    {
      if(w.getOp().equals("add"))
      {
        database.add(w.getSongEntry());
      }
      if(w.getOp().equals("delete"))
      {
        database.remove(w.getSongEntry());
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
      }
    }
  }
  
}
