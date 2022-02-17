// generated with ast extension for cup
// version 0.8
// 17/1/2022 8:52:44


package rs.ac.bg.etf.pp1.ast;

public class StatementDoWhile extends Matched {

    private Do Do;
    private StatementList StatementList;
    private DoCondition DoCondition;

    public StatementDoWhile (Do Do, StatementList StatementList, DoCondition DoCondition) {
        this.Do=Do;
        if(Do!=null) Do.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
        this.DoCondition=DoCondition;
        if(DoCondition!=null) DoCondition.setParent(this);
    }

    public Do getDo() {
        return Do;
    }

    public void setDo(Do Do) {
        this.Do=Do;
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
    }

    public DoCondition getDoCondition() {
        return DoCondition;
    }

    public void setDoCondition(DoCondition DoCondition) {
        this.DoCondition=DoCondition;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Do!=null) Do.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
        if(DoCondition!=null) DoCondition.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Do!=null) Do.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
        if(DoCondition!=null) DoCondition.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Do!=null) Do.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        if(DoCondition!=null) DoCondition.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementDoWhile(\n");

        if(Do!=null)
            buffer.append(Do.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DoCondition!=null)
            buffer.append(DoCondition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementDoWhile]");
        return buffer.toString();
    }
}
