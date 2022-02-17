// generated with ast extension for cup
// version 0.8
// 17/1/2022 8:52:44


package rs.ac.bg.etf.pp1.ast;

public class StatementLabelList extends Matched {

    private Label Label;
    private ColonHelper ColonHelper;
    private Statements Statements;

    public StatementLabelList (Label Label, ColonHelper ColonHelper, Statements Statements) {
        this.Label=Label;
        if(Label!=null) Label.setParent(this);
        this.ColonHelper=ColonHelper;
        if(ColonHelper!=null) ColonHelper.setParent(this);
        this.Statements=Statements;
        if(Statements!=null) Statements.setParent(this);
    }

    public Label getLabel() {
        return Label;
    }

    public void setLabel(Label Label) {
        this.Label=Label;
    }

    public ColonHelper getColonHelper() {
        return ColonHelper;
    }

    public void setColonHelper(ColonHelper ColonHelper) {
        this.ColonHelper=ColonHelper;
    }

    public Statements getStatements() {
        return Statements;
    }

    public void setStatements(Statements Statements) {
        this.Statements=Statements;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Label!=null) Label.accept(visitor);
        if(ColonHelper!=null) ColonHelper.accept(visitor);
        if(Statements!=null) Statements.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Label!=null) Label.traverseTopDown(visitor);
        if(ColonHelper!=null) ColonHelper.traverseTopDown(visitor);
        if(Statements!=null) Statements.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Label!=null) Label.traverseBottomUp(visitor);
        if(ColonHelper!=null) ColonHelper.traverseBottomUp(visitor);
        if(Statements!=null) Statements.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementLabelList(\n");

        if(Label!=null)
            buffer.append(Label.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ColonHelper!=null)
            buffer.append(ColonHelper.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statements!=null)
            buffer.append(Statements.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementLabelList]");
        return buffer.toString();
    }
}
