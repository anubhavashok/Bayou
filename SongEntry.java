public class SongEntry
{
  private String songName;
  private String URL;
  SongEntry(String songName, String URL)
  {
    this.songName = songName;
    this.URL=URL;
  }
  public String getSongName()
  {
    return songName;
  }
  public String getURL()
  {
    return URL;
  }
  public void setSongName(String songName)
  {
    this.songName = songName;
  }
  public void setURL(String URL)
  {
    this.URL=URL;
  }
}
