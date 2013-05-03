public class Instruction
{
    private String instr;
    private String replicaIP;
    private int order;
    private Write w;
    Instr(String i, String r, int o)
    {
        setInstr(i);
        setReplicaIP(r);
        setOrder(o);
    }
    Instr(String i, String r)
    {
        setInstr(i);
        setReplicaIP(r);
    }
    Instr(String i, Write w)
    {
        setInstr(i);
        setWrite(w);
    }
    
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
    public void setWrite(Write w)
    {
        this.w=w;
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
    public Write getWrite()
    {
        return w;
    }
    
}
