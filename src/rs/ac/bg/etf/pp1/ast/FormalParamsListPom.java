// generated with ast extension for cup
// version 0.8
// 17/1/2022 8:52:44


package rs.ac.bg.etf.pp1.ast;

public class FormalParamsListPom extends FormParsListPom {

    private FormParsListPom FormParsListPom;
    private FormParsSingle FormParsSingle;

    public FormalParamsListPom (FormParsListPom FormParsListPom, FormParsSingle FormParsSingle) {
        this.FormParsListPom=FormParsListPom;
        if(FormParsListPom!=null) FormParsListPom.setParent(this);
        this.FormParsSingle=FormParsSingle;
        if(FormParsSingle!=null) FormParsSingle.setParent(this);
    }

    public FormParsListPom getFormParsListPom() {
        return FormParsListPom;
    }

    public void setFormParsListPom(FormParsListPom FormParsListPom) {
        this.FormParsListPom=FormParsListPom;
    }

    public FormParsSingle getFormParsSingle() {
        return FormParsSingle;
    }

    public void setFormParsSingle(FormParsSingle FormParsSingle) {
        this.FormParsSingle=FormParsSingle;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormParsListPom!=null) FormParsListPom.accept(visitor);
        if(FormParsSingle!=null) FormParsSingle.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormParsListPom!=null) FormParsListPom.traverseTopDown(visitor);
        if(FormParsSingle!=null) FormParsSingle.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormParsListPom!=null) FormParsListPom.traverseBottomUp(visitor);
        if(FormParsSingle!=null) FormParsSingle.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormalParamsListPom(\n");

        if(FormParsListPom!=null)
            buffer.append(FormParsListPom.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormParsSingle!=null)
            buffer.append(FormParsSingle.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormalParamsListPom]");
        return buffer.toString();
    }
}
