public class ClientHandler
{
  Client c;
    public Instruction waitForInstructions() throws IOException
    {
          ServerSocket ss= new ServerSocket(2000);
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
}
