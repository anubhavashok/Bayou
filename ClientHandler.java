public class ClientHandler
{
    Client c;
    public static void main(String[] args)
    {
      executeInstruction(waitForInstructions());
    }
    public Instruction waitForInstructions() throws IOException
    {
      ServerSocket ss= new ServerSocket(2001);
       //System.out.println(n);
       ss.setReuseAddress(true);
       ss.setSoTimeout(0);
       //n++;
       try
        {
         Socket s = ss.accept();
         s.setReuseAddress(true);
         s.setSoTimeout(0);
    
         //System.out.println(s.getSoTimeout() );
         //System.out.println(s.getReuseAddress() );
       InputStream is = s.getInputStream();
       ObjectInputStream ois = new ObjectInputStream(is);
       Instruction I=null;
      
       I=(Instruction)ois.readObject();
       is.close();
       s.close();
       ss.close();
             return I;
       }
      
       catch(Exception e)
       {
       System.out.println(e.getLocalizedMessage());
       return null;
       }
    }
    
    public void executeInstruction(Instruction I)
    {
      if(I.getInst().equals("connectToServer"))
      {
        c=new Client(I.getReplicaIP());           //new client connected to server 
      }
      if(I.getInst().equals("Write"))
      {
        if(I.getWrite().getOp().equals("add"))
        {
          c.add(I.getWrite().getSongEntry());
        }
        if(I.getWrite().getOp().equals("delete"))
        {
          c.delete(I.getWrite().getSongEntry());
        }
        if(I.getWrite().getOp().equals("modify"))
        {
          c.modify(I.getWrite().getSongEntry());
        }
      }
    } 
}
