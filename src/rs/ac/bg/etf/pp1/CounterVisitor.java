package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.FormPars;
import rs.ac.bg.etf.pp1.ast.FormalParams;
import rs.ac.bg.etf.pp1.ast.VarDeclaration;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

public class CounterVisitor extends VisitorAdaptor {

	protected int count;
	
	public int getCount() {
		return count;
	}
	public static class FormParamCounter extends CounterVisitor{
		
		public void visit(FormalParams arg) {
			count++;
		}
		
	}
	
public static class VarCounter extends CounterVisitor{
		
		public void visit(VarDeclaration arg) {
			count++;
		}
		
	}
}
