public class Replica
{
  private ArrayList<SongEntry> database;        //current database version
  private ArrayList<SongEntry> stableDatabase;  //back up after garbage collection
  private ArrayList<Write> writeLog;
  
}
