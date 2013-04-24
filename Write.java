public class Write
{
  private String op;
  private SongEntry s;
  Write(SongEntry s, String op)
  {
    setOp(op);
    setSongEntry(s);
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
}
