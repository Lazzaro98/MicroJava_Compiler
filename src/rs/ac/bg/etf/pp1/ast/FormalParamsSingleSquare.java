// generated with ast extension for cup
// version 0.8
// 17/1/2022 8:52:44


package rs.ac.bg.etf.pp1.ast;

public class FormalParamsSingleSquare extends FormParsSingle {

    private Type Type;
    private String formparssingle;

    public FormalParamsSingleSquare (Type Type, String formparssingle) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.formparssingle=formparssingle;
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getFormparssingle() {
        return formparssingle;
    }

    public void setFormparssingle(String formparssingle) {
        this.formparssingle=formparssingle;
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
        buffer.append("FormalParamsSingleSquare(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+formparssingle);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormalParamsSingleSquare]");
        return buffer.toString();
    }
}
