public class Client
{
  InetAddress svr;              //address of server that is connected to client
  
  public void sendWrite(Write w)
  {
    try{
    Socket skt = new Socket(svr,1234);
    OutputStream os = skt.getOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(os);
    oos.writeObject(w);
    
    oos.close();
    os.close();
    skt.close();
  	}
  	catch(Exception e)
  	{
  	}
  }
  public void add(SongEntry s)
  {
    //send song entry to server that it is connected to.
    
    Write w= new Write(s,"add");
    sendWrite(w);
  }
  public void delete(SongEntry s)
  {
    //send delete request to server that its connected to.
    Write w = new Write(s,"delete");
    sendWrite(w);
  }
  public void edit(SongEntry s, String URL)
  {
    s.setURL(URL);
    Write w = new Write(s,"modify");
    sendWrite(w);
  }
  public ArrayList<SongEntry> getPlaylist()
  {
    //return current databse stored in local server.
    //OR wait for stable state and return stableDatabase
  }
}
