// generated with ast extension for cup
// version 0.8
// 17/1/2022 8:52:44


package rs.ac.bg.etf.pp1.ast;

public class DoCondition1 extends DoCondition {

    private DoConditionStart DoConditionStart;
    private Condition Condition;

    public DoCondition1 (DoConditionStart DoConditionStart, Condition Condition) {
        this.DoConditionStart=DoConditionStart;
        if(DoConditionStart!=null) DoConditionStart.setParent(this);
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
    }

    public DoConditionStart getDoConditionStart() {
        return DoConditionStart;
    }

    public void setDoConditionStart(DoConditionStart DoConditionStart) {
        this.DoConditionStart=DoConditionStart;
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DoConditionStart!=null) DoConditionStart.accept(visitor);
        if(Condition!=null) Condition.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DoConditionStart!=null) DoConditionStart.traverseTopDown(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DoConditionStart!=null) DoConditionStart.traverseBottomUp(visitor);
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DoCondition1(\n");

        if(DoConditionStart!=null)
            buffer.append(DoConditionStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DoCondition1]");
        return buffer.toString();
    }
}
