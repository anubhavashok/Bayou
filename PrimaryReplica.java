public class PrimaryReplica extends Replica
{
  private int CSNCount=0;
  private ArrayList<Write> committedWriteLog;
  
  public void mergeWithDatabase()
  {
    for(Write w: writeLog)
    {
      if(w.getOp().equals("add"))
      {
        database.add(w.getSongEntry());
        int i =writeLog.indexOf(w);
        w.setCSN(CSNCount);
        CSNCount++;
        writeLog.set(i,w);
        committedWriteLog.add(w);
        //increment CSN, ADD CSN TO WRITE IN WRITE LOG
      }
      if(w.getOp().equals("delete"))
      {
        database.remove(w.getSongEntry());
        int i =writeLog.indexOf(w);
        w.setCSN(CSNCount);
        CSNCount++;
        writeLog.set(i,w);
        committedWriteLog.add(w);
        //increment CSN, ADD CSN TO WRITE IN WRITE LOG
      }
      if(w.getOp().equals("modify"))
      {
        for(SongEntry s : database)
        {
          if(s.getSongName().equals(w.getSongEntry().getSongName()))
          {
            database.remove(s);
            database.add(w.getSongEntry());
            //store w with updated CSN in writelog for further propogation.
            int i =writeLog.indexOf(w);
            w.setCSN(CSNCount);
            CSNCount++;
            writeLog.set(i,w);
            committedWriteLog.add(w);
          }
        }

        //increment CSN, ADD CSN TO WRITE IN WRITE LOG
      }
    }
  }
  
  public void selectToSend()
  {
    ArrayList<Integer> V;                       //Version vector received from replica server
    int RCSN;                                   //highest CSN from the replica server
    if(RCSN<CSN)
    {
      for(int i=RCSN+1; i<CSN; i++)     //Send over all the committed writes
      {
            Write w = committedWriteLog.get(i);
            if(w.getAcceptTime()<=V.get(w.getReplicaId()).valueOf())
            {
             //R has the write, but does not know it is committed 
             //SendCommitNotification();
            }
            else
            {
              //R does not have the committed write
              sendWrite(w);
            }
      }

    }
  }
}
