// generated with ast extension for cup
// version 0.8
// 17/1/2022 8:52:44


package rs.ac.bg.etf.pp1.ast;

public class LeftAnd1 extends LeftAnd {

    private CondFactList CondFactList;

    public LeftAnd1 (CondFactList CondFactList) {
        this.CondFactList=CondFactList;
        if(CondFactList!=null) CondFactList.setParent(this);
    }

    public CondFactList getCondFactList() {
        return CondFactList;
    }

    public void setCondFactList(CondFactList CondFactList) {
        this.CondFactList=CondFactList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(CondFactList!=null) CondFactList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CondFactList!=null) CondFactList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CondFactList!=null) CondFactList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("LeftAnd1(\n");

        if(CondFactList!=null)
            buffer.append(CondFactList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [LeftAnd1]");
        return buffer.toString();
    }
}
