// generated with ast extension for cup
// version 0.8
// 17/1/2022 8:52:44


package rs.ac.bg.etf.pp1.ast;

public class FormParsSingleN extends FormParsSingle {

    private Type Type;
    private String formparssinglesquare;

    public FormParsSingleN (Type Type, String formparssinglesquare) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.formparssinglesquare=formparssinglesquare;
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getFormparssinglesquare() {
        return formparssinglesquare;
    }

    public void setFormparssinglesquare(String formparssinglesquare) {
        this.formparssinglesquare=formparssinglesquare;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormParsSingleN(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+formparssinglesquare);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormParsSingleN]");
        return buffer.toString();
    }
}
