// generated with ast extension for cup
// version 0.8
// 26/0/2022 20:53:11


package rs.ac.bg.etf.pp1.ast;

public class ConditionTermListEmpty extends CondTermList {

    public ConditionTermListEmpty () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConditionTermListEmpty(\n");

        buffer.append(tab);
        buffer.append(") [ConditionTermListEmpty]");
        return buffer.toString();
    }
}
