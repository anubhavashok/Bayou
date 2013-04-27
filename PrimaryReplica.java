public class PrimaryReplica extends Replica
{
  private int CSNCount=0;
  
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
        //increment CSN, ADD CSN TO WRITE IN WRITE LOG
      }
      if(w.getOp().equals("delete"))
      {
        database.remove(w.getSongEntry());
        int i =writeLog.indexOf(w);
        w.setCSN(CSNCount);
        CSNCount++;
        writeLog.set(i,w);
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
          }
        }

        //increment CSN, ADD CSN TO WRITE IN WRITE LOG
      }
    }
  }
}
