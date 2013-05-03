public class Bayou
{
  public static void main(String[] args)
  {
    private ArrayList<String[]> replicas=new ArrayList<String[]>();
    private Client c= new Client(InetAddress.getLocalHost());
    private PrimaryReplica pr= new PrimaryReplica(0,InetAddress.getLocalHost().getHostAddress());
    //set total nodes
    System.out.println("Please enter number of replicas: ");
    Scanner s = new Scanner(System.in);
    String totalReplicas = s.nextLine();
    Init.totalReplicas=totalReplicas;
    setUpReplicas();
    
  }
  public void setUpReplicas()
  {
    for(int i=0;i<totalReplicas;i++)
    {
      Scanner s = new Scanner(System.in);
      System.out.println("Please enter the ID number of the replica started (greater than 0): ");
      int id= s.nextInt();
      System.out.println("Please enter the IP address of the replica started: ");
      String ip = s.nextLine();

      String[] r = new String[2];
      r[0]=id;
      r[1]=ip;
      replicas.add(r);
    }
  }

  public String getIP(int id)
  {
    for(String[] s: replicas)
    {
      if(s[0].equals(Integer.toString(id))) //id found
      {
        return s[1];                        //return IP
      }
    }
    else return null;
  }
  public writeRequest(String clientIP)
  {
    Scanner s = new Scanner(System.in);
    System.out.println("Please which id client you want to use to do the write: ");
    int id = s.nextInt();
    System.out.println("Please enter what kind of operation you want to do: ");
    String op = s.nextLine();
    System.out.println("Please enter Song Name: ");
    String songName=s.nextLine();
    String URL=null;
    if(op.equals("add"))
    {
      System.out.println("Please enter Song URL: ");
      URL=s.nextLine();
    }
    if(op.equals("modify"))
    {
      System.out.println("Please enter new Song URL: ");
      URL=s.nextLine();
    }
    Write w = new Write(new SongEntry(songName,URL),op);
    Instruction i = new Instruction("Write", w);
    sendInstructionToClient(I,clientIP);            //called by bayouhandler
  }
}
