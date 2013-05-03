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
}
