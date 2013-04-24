public class Client
{
  public void add(SongEntry s)
  {
    //send song entry to server that it is connected to.
    sendWrite(s,"add");
  }
  public void delete(SongEntry s)
  {
    //send delete request to server that its connected to.
    sendWrite(s,"delete");
  }
  public void edit(SongEntry s, String URL)
  {
    s.setURL(URL);
    add(s);
  }
}
