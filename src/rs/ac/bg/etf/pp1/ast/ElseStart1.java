// generated with ast extension for cup
// version 0.8
// 26/0/2022 20:53:10


package rs.ac.bg.etf.pp1.ast;

public class ElseStart1 extends ElseStart {

    public ElseStart1 () {
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
        buffer.append("ElseStart1(\n");

        buffer.append(tab);
        buffer.append(") [ElseStart1]");
        return buffer.toString();
    }
}