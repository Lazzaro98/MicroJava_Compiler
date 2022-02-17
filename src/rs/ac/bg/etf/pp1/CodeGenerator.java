package rs.ac.bg.etf.pp1;

import java.awt.Label;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.AddExpr;
import rs.ac.bg.etf.pp1.ast.AddopMinus;
import rs.ac.bg.etf.pp1.ast.AddopPlus;
import rs.ac.bg.etf.pp1.ast.CondTerm;
import rs.ac.bg.etf.pp1.ast.CondTermList;
import rs.ac.bg.etf.pp1.ast.ConditionFactor;
import rs.ac.bg.etf.pp1.ast.ConditionFactorList;
import rs.ac.bg.etf.pp1.ast.ConditionFactorListEmpty;
import rs.ac.bg.etf.pp1.ast.ConditionFactorSingle;
import rs.ac.bg.etf.pp1.ast.ConditionTermList;
import rs.ac.bg.etf.pp1.ast.DesignatorDot;
import rs.ac.bg.etf.pp1.ast.DesignatorSingle;
import rs.ac.bg.etf.pp1.ast.DesignatorSquare;
import rs.ac.bg.etf.pp1.ast.DesignatorStatementActParams;
import rs.ac.bg.etf.pp1.ast.DesignatorStatementActParamsEmpty;
import rs.ac.bg.etf.pp1.ast.DesignatorStatementDec;
import rs.ac.bg.etf.pp1.ast.DesignatorStatementEq;
import rs.ac.bg.etf.pp1.ast.DesignatorStatementInc;
import rs.ac.bg.etf.pp1.ast.Do;
import rs.ac.bg.etf.pp1.ast.DoCondition1;
import rs.ac.bg.etf.pp1.ast.DoConditionStart1;
import rs.ac.bg.etf.pp1.ast.DoStmt;
import rs.ac.bg.etf.pp1.ast.ElseStart1;
import rs.ac.bg.etf.pp1.ast.EqualsOp;
import rs.ac.bg.etf.pp1.ast.Expr;
import rs.ac.bg.etf.pp1.ast.FactorBoolConst;
import rs.ac.bg.etf.pp1.ast.FactorCharConst;
import rs.ac.bg.etf.pp1.ast.FactorNew;
import rs.ac.bg.etf.pp1.ast.FactorNewSquare;
import rs.ac.bg.etf.pp1.ast.FactorNumConst;
import rs.ac.bg.etf.pp1.ast.FormParsSingleN;
import rs.ac.bg.etf.pp1.ast.FormalParamsSingleSquare;
import rs.ac.bg.etf.pp1.ast.FuncCallNP;
import rs.ac.bg.etf.pp1.ast.FuncCallP;
import rs.ac.bg.etf.pp1.ast.GotoSingleStatement;
import rs.ac.bg.etf.pp1.ast.GreaterEqualsOperator;
import rs.ac.bg.etf.pp1.ast.GreaterOp;
import rs.ac.bg.etf.pp1.ast.IFCondition1;
import rs.ac.bg.etf.pp1.ast.LeftAnd1;
import rs.ac.bg.etf.pp1.ast.LeftOr1;
import rs.ac.bg.etf.pp1.ast.LessEqualsOperator;
import rs.ac.bg.etf.pp1.ast.LessOperator;
import rs.ac.bg.etf.pp1.ast.MatchedStmt;
import rs.ac.bg.etf.pp1.ast.MethodDeclaration;
import rs.ac.bg.etf.pp1.ast.MethodTypeName_;
import rs.ac.bg.etf.pp1.ast.MethodVoidName;
import rs.ac.bg.etf.pp1.ast.MulopDiv;
import rs.ac.bg.etf.pp1.ast.MulopMod;
import rs.ac.bg.etf.pp1.ast.MulopMul;
import rs.ac.bg.etf.pp1.ast.NoEqualsOp;
import rs.ac.bg.etf.pp1.ast.Relop;
import rs.ac.bg.etf.pp1.ast.SingleStatementPrint;
import rs.ac.bg.etf.pp1.ast.SingleStatementPrintNumconst;
import rs.ac.bg.etf.pp1.ast.SingleStatementRead;
import rs.ac.bg.etf.pp1.ast.StatementBreak;
import rs.ac.bg.etf.pp1.ast.StatementContinue;
import rs.ac.bg.etf.pp1.ast.StatementDoWhile;
import rs.ac.bg.etf.pp1.ast.StatementIfElse;
import rs.ac.bg.etf.pp1.ast.StatementLabelList;
import rs.ac.bg.etf.pp1.ast.StatementLabelSingle;
import rs.ac.bg.etf.pp1.ast.StatementReturn;
import rs.ac.bg.etf.pp1.ast.StatementReturnNull;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.ac.bg.etf.pp1.ast.TermExprMin;
import rs.ac.bg.etf.pp1.ast.TermMulFactor;
import rs.ac.bg.etf.pp1.ast.UnmatchedIf;
import rs.ac.bg.etf.pp1.ast.UnmatchedIfElse;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {
	private int mainPc;

	public int getMainPc() {
		return mainPc;
	}

	public static Struct bool = Tab.find("bool").getType();

	enum comparator {
		EQUALS, NOT_EQUALS, GREATER, GREATER_EQUALS, LESS, LESS_EQUALS
	}

	comparator last_comp = null;

	public CodeGenerator() {
		super();
		addLen();
		addChr();
		addOrd();
	}

	private void addLen() {
		Obj obj = Tab.find("len");
		obj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(obj.getLevel());
		Code.put(obj.getLocalSymbols().size());
		Code.put(Code.load_n);
		Code.put(Code.arraylength);
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	private void addChr() {
		Obj obj = Tab.find("chr");
		obj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(obj.getLevel());
		Code.put(obj.getLocalSymbols().size());
		Code.put(Code.load_n);
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	private void addOrd() {
		Obj obj = Tab.find("ord");
		obj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(obj.getLevel());
		Code.put(obj.getLocalSymbols().size());
		Code.put(Code.load_n);
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(EqualsOp arg) {
		last_comp = comparator.EQUALS;
	}

	public void visit(NoEqualsOp arg) {
		last_comp = comparator.NOT_EQUALS;
	}

	public void visit(GreaterOp arg) {
		last_comp = comparator.GREATER;
	}

	public void visit(GreaterEqualsOperator arg) {
		last_comp = comparator.GREATER_EQUALS;
	}

	public void visit(LessOperator arg) {
		last_comp = comparator.LESS;
	}

	public void visit(LessEqualsOperator arg) {
		last_comp = comparator.LESS_EQUALS;
	}

	public void visit(SingleStatementRead arg) {
		if (arg.getDesignator().obj.getType() == Tab.charType) {
			Code.put(Code.bread);
		} else if (arg.getDesignator().obj.getType() == Tab.intType || arg.getDesignator().obj.getType() == bool) {
			Code.put(Code.read);
		}

		Code.store(arg.getDesignator().obj);
	}

	// SingleStatement ::= (SingleStatementPrintNumconst) PRINT LPAREN Expr COMMA
	// NUMBER RPAREN SEMI
	public void visit(SingleStatementPrintNumconst arg) {
		if (arg.getExpr().struct == Tab.intType) {
			Code.loadConst(5);
			Code.put(Code.print);
		} else {
			Code.loadConst(1);
			Code.put(Code.bprint);
		}
	}

	public void visit(DesignatorStatementInc arg) {
		if (arg.getDesignator().obj.getKind() == Obj.Elem)
			Code.put(Code.dup2);
		if (arg.getDesignator().obj.getKind() == Obj.Fld)
			Code.put(Code.dup);
		Code.load(arg.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(arg.getDesignator().obj);
	}

	public void visit(DesignatorStatementDec arg) {
		if (arg.getDesignator().obj.getKind() == Obj.Elem)
			Code.put(Code.dup2);
		if (arg.getDesignator().obj.getKind() == Obj.Fld)
			Code.put(Code.dup);
		Code.load(arg.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(arg.getDesignator().obj);
	}

	// SingleStatement ::= (SingleStatementPrint) PRINT LPAREN Expr RPAREN SEMI
	public void visit(SingleStatementPrint arg) {
		if (arg.getExpr().struct == Tab.intType) {
			Code.loadConst(5);
			Code.put(Code.print);
		} else {
			Code.loadConst(1);
			Code.put(Code.bprint);
		}
	}

	// Factor ::= (FactorNumConst) NUMBER
	public void visit(FactorNumConst arg) {
		Obj con = Tab.insert(Obj.Con, "$", arg.struct);
		con.setLevel(0); // setujemo je na global
		con.setAdr(arg.getN1()); // Vrednost globalnih promenljivih cuvamo u adr

		Code.load(con);
	}

	// Factor ::= (FactorCharConst) CHAR
	public void visit(FactorCharConst arg) {
		Obj con = Tab.insert(Obj.Con, "$", arg.struct);
		con.setLevel(0); // setujemo je na global
		con.setAdr(arg.getC1()); // Vrednost globalnih promenljivih cuvamo u adr

		Code.load(con);
	}

	// Factor ::= (FactorBoolConst) BOOLEAN
	public void visit(FactorBoolConst arg) {
		Obj con = Tab.insert(Obj.Con, "$", arg.struct);
		con.setLevel(0); // setujemo je na global
		con.setAdr(arg.getB1()); // Vrednost globalnih promenljivih cuvamo u adr

		Code.load(con);
	}

	public void visit(MethodTypeName_ arg) {
		if ("main".equalsIgnoreCase(arg.getMethName())) {
			this.mainPc = Code.pc;
		}

		arg.obj.setAdr(Code.pc);

		// Svaka metoda picnje enter instrukcijom.
		// Enter instr je zaduzena da nrapvi aktivacioni zapis na steku i da sve
		// parametre sa expr steka
		// iskopira u kativacioni zapis pozvane metode

		// kada generisemo enter instrukciju moramo da stavimo broj parametara i
		// broj_parametara+broj_lokalnih_prom

		SyntaxNode methodNode = arg.getParent();

		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);

		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);

		// Generate the entry
		Code.put(Code.enter);
		Code.put(fpCnt.getCount());
		Code.put(fpCnt.getCount() + varCnt.getCount());
		// System.out.println(arg.getMethName() + " ima " + fpCnt.getCount() + "
		// formalni parametara, i " + varCnt.getCount() + " var-ova.");

	}

	public void visit(MethodVoidName arg) {
		if ("main".equalsIgnoreCase(arg.getMethName())) {
			this.mainPc = Code.pc;
		}

		arg.obj.setAdr(Code.pc);
		// System.out.println(Code.pc);
		// Svaka metoda picnje enter instrukcijom.
		// Enter instr je zaduzena da nrapvi aktivacioni zapis na steku i da sve
		// parametre sa expr steka
		// iskopira u kativacioni zapis pozvane metode

		// kada generisemo enter instrukciju moramo da stavimo broj parametara i
		// broj_parametara+broj_lokalnih_prom

		SyntaxNode methodNode = arg.getParent();

		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);

		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);

		// Generate the entry
		Code.put(Code.enter);
		Code.put(fpCnt.getCount());
		Code.put(fpCnt.getCount() + varCnt.getCount());

	}

	public void visit(StatementReturn arg) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(StatementReturnNull arg) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(MethodDeclaration arg) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(DesignatorStatementEq arg) {
		Code.store(arg.getDesignator().obj);
	}

	int x = 0;
	private Stack<Obj> arrayStack = new Stack<>();

	public void visit(DesignatorSingle arg) {
		// System.out.println(arg.getName() + "" + x++);
		SyntaxNode parent = arg.getParent();
		if (arg.getParent().getClass() == DesignatorDot.class || arg.getParent().getClass() == DesignatorSquare.class)
			arrayStack.push(arg.obj);

		if (DesignatorStatementEq.class != parent.getClass() && FuncCallP.class != parent.getClass()
				&& FuncCallNP.class != parent.getClass() && arg.getParent().getClass() != SingleStatementRead.class) {
			Code.load(arg.obj);
		}
	}

	int i = 0;

	public void visit(DesignatorSquare arg) {
		if (arg.getParent().getClass() != DesignatorStatementEq.class
				&& arg.getParent().getClass() != DesignatorStatementInc.class
				&& arg.getParent().getClass() != DesignatorStatementDec.class
				&& arg.getParent().getClass() != SingleStatementRead.class)
			Code.load(arg.obj);
	}

	public void visit(DesignatorDot arg) {
		if (arg.getParent().getClass() != DesignatorStatementEq.class
				&& arg.getParent().getClass() != DesignatorStatementInc.class
				&& arg.getParent().getClass() != DesignatorStatementDec.class
				&& arg.getParent().getClass() != SingleStatementRead.class)
			Code.load(arg.obj);
	}

	public void visit(AddExpr arg) {
		if (arg.getAddop().getClass() == AddopPlus.class)
			Code.put(Code.add);
		else if (arg.getAddop().getClass() == AddopMinus.class)
			Code.put(Code.sub);
	}

	public void visit(TermMulFactor arg) {
		if (arg.getMulop().getClass() == MulopDiv.class)
			Code.put(Code.div);
		else if (arg.getMulop().getClass() == MulopMul.class)
			Code.put(Code.mul);
		else if (arg.getMulop().getClass() == MulopMod.class)
			Code.put(Code.rem);
	}

	public void visit(FuncCallP arg) {
		Obj functionObj = arg.getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);

		Code.put2(offset);
	}

	public void visit(FuncCallNP arg) {
		Obj functionObj = arg.getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);

		Code.put2(offset);
	}

	public void visit(DesignatorStatementActParams arg) {
		Obj functionObj = arg.getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
		if (arg.getDesignator().obj.getType() != Tab.noType) {
			Code.put(Code.pop);
		}

	} // procedure sa param

	public void visit(DesignatorStatementActParamsEmpty arg) {

		Obj functionObj = arg.getDesignator().obj;
		// System.out.print(functionObj.getAdr() + " - " + Code.pc + " = ");
		int offset = functionObj.getAdr() - Code.pc;
		// System.out.println(offset);
		Code.put(Code.call);
		Code.put2(offset);
		if (arg.getDesignator().obj.getType() != Tab.noType) {
			Code.put(Code.pop);
		}
	} 

	
	public void visit(FactorNewSquare arg) {
		// System.out.println("hm?");
		Code.put(Code.newarray);
		if (arg.getType().struct.equals(Tab.charType))
			Code.put(0);
		else
			Code.put(1);
	}

	// FactorNew
	public void visit(FactorNew arg) {
		// System.out.println("hm?");
		Code.put(Code.new_);
		Code.put2(arg.getType().struct.getMembersTable().symbols().size() * 4);
		// System.out.println("num of fields");
	}

	public void visit(TermExprMin arg) {
		Code.put(Code.neg);
	}

	private Stack<Integer> left_and_fix = new Stack<>();
	private Stack<Integer> left_or_fix = new Stack<>();
	public void visit(LeftAnd1 leftAnd) {
		Code.put(Code.dup);
		Code.put(Code.const_1);
		left_and_fix.push(Code.pc + 1);
		Code.putFalseJump(Code.eq, Code.pc);
		Code.put(Code.pop);
	}
	public void visit(ConditionFactorList arg) {
		Code.fixup(left_and_fix.pop());
	}
	public void visit(LeftOr1 leftOr) {
		Code.put(Code.dup);
		Code.put(Code.const_n);
		left_or_fix.push(Code.pc + 1);
		Code.putFalseJump(Code.eq, Code.pc);
		Code.put(Code.pop);

	}
	public void visit(ConditionTermList arg) {
		 Code.fixup(left_or_fix.pop());
	}

	private Stack<Integer> fix_if_stack = new Stack<>();
	public void visit(UnmatchedIf unmatched) {
		Code.fixup(fix_if_stack.pop());
	}

	public void visit(UnmatchedIfElse unmatchedIfElse) {
		Code.fixup(fix_if_stack.pop());
	}

	public void visit(StatementIfElse matchedStatement) {
		Code.fixup(fix_if_stack.pop());
	}

	public void visit(IFCondition1 ifCondition) {
		Code.put(Code.const_1);
		fix_if_stack.push(Code.pc +1);
		Code.putFalseJump(Code.eq, Code.pc);
	}

	public void visit(ElseStart1 elseStart) {
		int jumpToFix = fix_if_stack.pop();
		fix_if_stack.push(Code.pc + 1);
		Code.putJump(Code.pc);
		Code.fixup(jumpToFix);
	}
	public void visit(ConditionFactor arg) {
		Relop relationalOperator = arg.getRelop();
		if (relationalOperator.getClass() == EqualsOp.class) {
			Code.put(Code.jcc + Code.inverse[Code.eq]);
		}
		if (relationalOperator.getClass() == NoEqualsOp.class) {
			Code.put(Code.jcc + Code.inverse[Code.ne]);
		}
		if (relationalOperator.getClass() == GreaterOp.class) {
			Code.put(Code.jcc + Code.inverse[Code.gt]);
		}
		if (relationalOperator.getClass() == GreaterEqualsOperator.class) {
			Code.put(Code.jcc + Code.inverse[Code.ge]);
		}
		if (relationalOperator.getClass() == LessOperator.class) {
			Code.put(Code.jcc + Code.inverse[Code.lt]);
		}
		if (relationalOperator.getClass() == LessEqualsOperator.class) {
			Code.put(Code.jcc + Code.inverse[Code.le]);
		}
		Code.put2(7);
		Code.put(Code.const_1);
		Code.put(Code.jmp);
		Code.put2(4);
		Code.put(Code.const_n);
	}

	Stack<Integer> dowhile_address_start = new Stack<>();
	Stack<Integer> dodwhile_skip_condition = new Stack<>();

	
	//dowhile
	public void visit(DoConditionStart1 arg) {
		for (int address : continue_patch_address.pop()) {
			Code.fixup(address);
		}
	}
	
	public void visit(DoStmt arg) {
		dowhile_address_start.push(Code.pc);
		break_patch_addres.push(new ArrayList<>());
		continue_patch_address.push(new ArrayList<>());
	}

	public void visit(DoCondition1 arg) {
		Code.put(Code.const_1);
		Code.put(Code.jcc + Code.eq);
		Code.put2(dowhile_address_start.pop() - Code.pc + 1);

	}

	public void visit(StatementDoWhile arg) {
		for (int address : break_patch_addres.pop()) {
			Code.fixup(address);
		}
	}
	
	Stack<Integer> for_address_start = new Stack<>();
	Stack<List<Integer>> break_patch_addres = new Stack<>();
	Stack<List<Integer>> continue_patch_address = new Stack<>();
	public void visit(StatementBreak arg) {
		break_patch_addres.peek().add(Code.pc + 1);
		Code.putJump(Code.pc);
	}

	public void visit(StatementContinue arg) {
		continue_patch_address.peek().add(Code.pc + 1);
		Code.putJump(0);
	}

	
	HashMap<String, Integer> adrese_labela = new HashMap<String, Integer>();
	HashMap<String, ArrayList<Integer>> nerazresene_adrese_labela = new HashMap<String, ArrayList<Integer>>();
	public void visit(Label label) {
	}
	
	public void visit(GotoSingleStatement arg) {
		
	}


}
