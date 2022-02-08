// generated with ast extension for cup
// version 0.8
// 26/0/2022 20:53:11


package rs.ac.bg.etf.pp1.ast;

public class Label implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String labelName;
    private ColonHelper ColonHelper;

    public Label (String labelName, ColonHelper ColonHelper) {
        this.labelName=labelName;
        this.ColonHelper=ColonHelper;
        if(ColonHelper!=null) ColonHelper.setParent(this);
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName=labelName;
    }

    public ColonHelper getColonHelper() {
        return ColonHelper;
    }

    public void setColonHelper(ColonHelper ColonHelper) {
        this.ColonHelper=ColonHelper;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ColonHelper!=null) ColonHelper.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ColonHelper!=null) ColonHelper.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ColonHelper!=null) ColonHelper.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Label(\n");

        buffer.append(" "+tab+labelName);
        buffer.append("\n");

        if(ColonHelper!=null)
            buffer.append(ColonHelper.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Label]");
        return buffer.toString();
    }
}
