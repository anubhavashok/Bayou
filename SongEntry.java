public class SongEntry implements java.io.Serializable 
{
  private String songName;
  private String URL;
  SongEntry(String setsongName, String setURL)
  {
    this.songName = setsongName;
    this.URL=setURL;
  }
  public String getSongName()
  {
    return songName;
  }
  public String getURL()
  {
    return URL;
  }
  public void setSongName(String setsongName)
  {
    this.songName = setsongName;
  }
  public void setURL(String setURL)
  {
    this.URL=setURL;
  }
}
