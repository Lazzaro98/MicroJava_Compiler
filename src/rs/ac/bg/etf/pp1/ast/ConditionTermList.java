// generated with ast extension for cup
// version 0.8
// 26/0/2022 20:53:11


package rs.ac.bg.etf.pp1.ast;

public class ConditionTermList extends CondTermList {

    private LeftOr LeftOr;
    private CondTerm CondTerm;

    public ConditionTermList (LeftOr LeftOr, CondTerm CondTerm) {
        this.LeftOr=LeftOr;
        if(LeftOr!=null) LeftOr.setParent(this);
        this.CondTerm=CondTerm;
        if(CondTerm!=null) CondTerm.setParent(this);
    }

    public LeftOr getLeftOr() {
        return LeftOr;
    }

    public void setLeftOr(LeftOr LeftOr) {
        this.LeftOr=LeftOr;
    }

    public CondTerm getCondTerm() {
        return CondTerm;
    }

    public void setCondTerm(CondTerm CondTerm) {
        this.CondTerm=CondTerm;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(LeftOr!=null) LeftOr.accept(visitor);
        if(CondTerm!=null) CondTerm.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(LeftOr!=null) LeftOr.traverseTopDown(visitor);
        if(CondTerm!=null) CondTerm.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(LeftOr!=null) LeftOr.traverseBottomUp(visitor);
        if(CondTerm!=null) CondTerm.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConditionTermList(\n");

        if(LeftOr!=null)
            buffer.append(LeftOr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CondTerm!=null)
            buffer.append(CondTerm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConditionTermList]");
        return buffer.toString();
    }
}
