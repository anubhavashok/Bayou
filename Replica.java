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
      if(V.(w.getReplicaId())<w.getAcceptTime)  //possible error comparing 2 Integers
      {
        sendWrite(w);                           //send new write to the server
      }
    }
  }
  
}
