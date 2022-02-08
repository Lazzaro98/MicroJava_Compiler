// generated with ast extension for cup
// version 0.8
// 26/0/2022 20:53:10


package rs.ac.bg.etf.pp1.ast;

public class StatementLabelSingle extends Matched {

    private Label Label;
    private ColonHelper ColonHelper;
    private SingleStatement SingleStatement;

    public StatementLabelSingle (Label Label, ColonHelper ColonHelper, SingleStatement SingleStatement) {
        this.Label=Label;
        if(Label!=null) Label.setParent(this);
        this.ColonHelper=ColonHelper;
        if(ColonHelper!=null) ColonHelper.setParent(this);
        this.SingleStatement=SingleStatement;
        if(SingleStatement!=null) SingleStatement.setParent(this);
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

    public SingleStatement getSingleStatement() {
        return SingleStatement;
    }

    public void setSingleStatement(SingleStatement SingleStatement) {
        this.SingleStatement=SingleStatement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Label!=null) Label.accept(visitor);
        if(ColonHelper!=null) ColonHelper.accept(visitor);
        if(SingleStatement!=null) SingleStatement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Label!=null) Label.traverseTopDown(visitor);
        if(ColonHelper!=null) ColonHelper.traverseTopDown(visitor);
        if(SingleStatement!=null) SingleStatement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Label!=null) Label.traverseBottomUp(visitor);
        if(ColonHelper!=null) ColonHelper.traverseBottomUp(visitor);
        if(SingleStatement!=null) SingleStatement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementLabelSingle(\n");

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

        if(SingleStatement!=null)
            buffer.append(SingleStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementLabelSingle]");
        return buffer.toString();
    }
}
