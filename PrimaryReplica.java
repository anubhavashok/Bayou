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
