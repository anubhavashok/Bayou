public class Instruction
{
    private String instr;
    private String replicaIP;
    private int order;
    
    public void setInstr(String instr)
    {
      this.instr=instr;
    }
    public void setReplicaIP(String rip)
    {
      this.replicaIP=rip;
    }
    public void setOrder(int o)
    {
      this.order=o;
    }
    public String getInstr()
    {
      return instr;
    }
    public String getReplicaIP()
    {
      return replicaIP;
    }
    public int getOrder()
    {
      return order;
    }
    
}
