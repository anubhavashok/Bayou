public class Write
{
  private String op;
  private SongEntry s;
  private int acceptTime;
  private int replicaId;
  private int CSN;
  Write(SongEntry s, String op)
  {
    setOp(op);
    setSongEntry(s);
    setCSN(-1);             // where -1 represents infinity
  }
  public String getOp()
  {
    return op;
  }
  public SongEntry getSongEntry()
  {
    return s;
  }
  public void setOp(String op)
  {
    this.op=op;
  }
  public void setSongEntry(SongEntry s)
  {
    this.s=s;
  }
  public int getAcceptTime()
  {
    return acceptTime;
  }
  public void setAcceptTime(int acceptTime)
  {
    this.acceptTime=acceptTime;
  }
   public int getReplicaId()
  {
    return replicaId;
  }
  public void setReplicaId(int replicaId)
  {
    this.replicaId=replicaId;
  }
  public void setCSN(int CSN)
  {
    this.CSN=CSN;
  }
  public int getCSN()
  {
    return CSN;
  }
}

