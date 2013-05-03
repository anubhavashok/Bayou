public class Bayou
{
  public static void main(String[] args)
  {
    static Vector<Thread> replicas=new Vector<Thread>();
    static Vector<Thread> clients= new Vector<Thread>();
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
      System.out.println("Please enter the IP address of the replica started: ");
      Scanner s = new Scanner(System.in);
      String ip = s.nextLine();
      System.out.println("Please enter the ID number of the replica started: ");
      int id= s.nextInt();
      Replica s = new Replica(id,ip);
      Thread t = new Thread(s);
      replicas.add(t);
    }
  }
}
