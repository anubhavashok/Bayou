public class Bayou
{
  public static void main(String[] args)
  {
    private ArrayList<ReplicaHandler> replicas=new ArrayList<ReplicaHandler>();
    private ArrayList<ClientHandler> clients=new ArrayList<ClientHandler>();
    private Client c= new Client(InetAddress.getLocalHost());
    private PrimaryReplica pr= new PrimaryReplica(0,InetAddress.getLocalHost().getHostAddress());
    //set total nodes
    System.out.println("Please enter number of replicas: ");
    Scanner s = new Scanner(System.in);
    int totalReplicas = s.nextInt();
    Init.totalReplicas=totalReplicas;
    setUpReplicas();
    // THE REPLICAS ARE SET TO GO
    System.out.println("Please enter number of clients: ");
    Scanner s = new Scanner(System.in);
    int totalClients = s.nextInt();
    Init.totalClients=totalClients;
    setUpClients();
    //Assuming 2 replicas and 2 clients
    // THE CLIENTS ARE READY
    writeRequest();
    
    
    
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
      ReplicaHandler r = new ReplicaHandler(id,ip);
      replicas.add(r);
    }
  public void setUpClients()
  {
    for(int i=0;i<totalClients;i++)
    {
      Scanner s = new Scanner(System.in);
      System.out.println("Please enter the IP address of the client started: ");
      String ip1 = s.nextLine();
      System.out.println("Please enter the IP address of the replica the client is connected to: ");
      String ip2 = s.nextLine();
      startClient(ip1,ip2);      
    }
    
  }

  public String getClientIP(int id)
  {
    for(ClientHandler c : clients)
    {
      if(s[0].equals(Integer.toString(id))) //id found
      {
        return s[1];                        //return IP
      }
    }
    else return null;
  }
  public String getReplicaIP(int id)
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
  public writeRequest()
  {
    Scanner s = new Scanner(System.in);
    System.out.println("Please enter which id client you want to use to do the write: ");
    displayClients();
    int id = s.nextInt();
    String clientIP = getClientIP(id);
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
  public void displayClients()
  {int i=0;
    for (ClientHandler c : clients)
    {
      System.out.println("Client id: "+i+" Server that Client is connected to: "+c.getServerIP());i++;
    }
  }
}
