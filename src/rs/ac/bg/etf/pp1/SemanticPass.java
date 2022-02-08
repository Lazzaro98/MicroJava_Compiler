package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticPass extends VisitorAdaptor {
	public static final int user_type_start_index = 10;

	public static int type_index = user_type_start_index;
	public static final int RECORD_STRUCT_ID = 10;

	public static final Struct boolType = new Struct(Struct.Bool);
	public static final Struct arrayType = new Struct(Struct.Array);

	public SemanticPass() {
		super();
		Tab.insert(Obj.Type, "bool", boolType); // dodali smo bool u tabelu simbola
	}

	// moji tipovi ce kretati od 10 hehe

	Logger log = Logger.getLogger(getClass());

	int printCallCount = 0;
	int varDeclCount = 0;

	Obj currentMethod = null;
	int dowhile_active_counter = 0;
	int functions_active_counter = 0;
	boolean returnFound = false;

	public boolean errorDetected = false;

	HashMap<String, ArrayList<Struct>> method_params = new HashMap<String, ArrayList<Struct>>();

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.info(msg.toString());
	}

	public void visit(ProgramName arg) {
		arg.obj = Tab.insert(Obj.Prog, arg.getProgramName(), Tab.noType);
		Tab.openScope();
	}

	public void visit(Program arg) {
		Tab.chainLocalSymbols(arg.getProgramName().obj);
		Tab.closeScope();
	}

	// MethodTypeName ::= (MethodTypeName_) Type:retType IDENT:methName
	public void visit(MethodTypeName_ arg) {
		currentMethod = Tab.insert(Obj.Meth, arg.getMethName(), arg.getType().struct);
		arg.obj = currentMethod;
		method_params.put(currentMethod.getName(), new ArrayList<Struct>());
		Tab.openScope();
		functions_active_counter++;
		report_info("Obradjuje se funkcija " + arg.getMethName(), arg);
	}

	public void visit(MethodVoidName arg) {
		currentMethod = Tab.insert(Obj.Meth, arg.getMethName(), Tab.noType);
		arg.obj = currentMethod;
		method_params.put(currentMethod.getName(), new ArrayList<Struct>());
		Tab.openScope();
		functions_active_counter++;
		report_info("Obradjuje se funkcija " + arg.getMethName(), arg);
	}

	public void visit(MethodDeclaration arg) {
		if (!returnFound && currentMethod.getType() != Tab.noType) {
			report_error("Funkcija " + currentMethod.getName() + " nema return iskaz!", null);
		}

		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();

		returnFound = false;
		currentMethod = null;
		functions_active_counter--;
	}

	public void visit(Type arg) {
		Obj typeNode = Tab.find(arg.getTypeName());
		if (typeNode == Tab.noObj) {
			report_error("Nije pronadjen tip " + arg.getTypeName() + " u tabeli simbola", null);
			arg.struct = Tab.noType;
		} else {
			if (Obj.Type == typeNode.getKind()) {
				arg.struct = typeNode.getType();
				// report_info("Tip uspesno setovan na " + typeNode.getName(), type);
			} else {
				report_error("Greska: Ime " + arg.getTypeName() + " ne predstavlja tip.", arg);
				arg.struct = Tab.noType;
			}
		}
	}

	// VarDecl ::= (VarDeclaration) Type IDENT:varName VarDeclListPom SEMI
	public void visit(VarDeclaration arg) {
		boolean postoji = (Tab.find(arg.getVarName())) != Tab.noObj;
		if (!postoji) {
			Obj inserted = Tab.insert(Obj.Var, arg.getVarName(), arg.getType().struct);
			varDeclCount++;
			report_info("Deklarisana promenljiva " + arg.getVarName() + " tipa " + ((Type) arg.getType()).getTypeName(),
					arg);
			arg.struct = arg.getType().struct;
		} else {
			report_error("Promenljiva " + arg.getVarName() + " je vec deklarisana", arg);
			arg.struct = Tab.noType;
		}
	}

	// VarDeclListPom ::= (VarDeclarationListPom) VarDeclListPom COMMA IDENT:varName
	public void visit(VarDeclarationListPomSquare arg) {
		boolean postoji = (Tab.find(arg.getVarName())) != Tab.noObj;
		if (!postoji) {

			Object pom = arg;
			while (pom.getClass() != VarDeclaration.class) {
				if (pom.getClass() == VarDeclarationListPomSquare.class)
					pom = ((VarDeclarationListPomSquare) pom).getParent();
				else
					pom = ((VarDeclarationListPom) pom).getParent();
			}
			VarDeclaration v = (VarDeclaration) pom;

			
			
			
			Struct arrayType = new Struct(Struct.Array, v.getType().struct);
			Obj inserted = Tab.insert(Obj.Var, arg.getVarName(), arrayType);
			varDeclCount++;
			report_info("Deklarisan niz " + arg.getVarName() + " tipa " + ((Type) v.getType()).getTypeName(), arg);
			arg.struct = arrayType;
		} else {
			report_error("Promenljiva " + arg.getVarName() + " je vec deklarisana", arg);
			arg.struct = Tab.noType;
		}
	}
	
	public void visit(VarDeclarationListPom arg) {
		boolean postoji = (Tab.find(arg.getVarName())) != Tab.noObj;
		if (!postoji) {

			Object pom = arg;
			while (pom.getClass() != VarDeclaration.class) {
				if (pom.getClass() == VarDeclarationListPomSquare.class)
					pom = ((VarDeclarationListPomSquare) pom).getParent();
				else
					pom = ((VarDeclarationListPom) pom).getParent();
			}
			VarDeclaration v = (VarDeclaration) pom;

			Obj inserted = Tab.insert(Obj.Var, arg.getVarName(), v.getType().struct);
			varDeclCount++;
			report_info("Deklarisana promenljiva " + arg.getVarName() + " tipa " + ((Type) v.getType()).getTypeName(),
					arg);
			arg.struct = v.getType().struct;
		} else {
			report_error("Promenljiva " + arg.getVarName() + " je vec deklarisana", arg);
			arg.struct = Tab.noType;
		}
	}

	// VarDecl ::= (VarDeclarationSquares) Type IDENT:varName LSQUARE RSQUARE
	// VarDeclListPom SEMI
	public void visit(VarDeclarationSquares arg) {
		boolean postoji = (Tab.find(arg.getVarName())) != Tab.noObj;
		if (!postoji) {
			Struct arrayType = new Struct(Struct.Array, arg.getType().struct);
			Obj inserted = Tab.insert(Obj.Var, arg.getVarName(), arrayType);
			varDeclCount++;
			report_info("Deklarisan niz " + arg.getVarName() + " tipa " + ((Type) arg.getType()).getTypeName(), arg);
			arg.struct = arrayType;

		} else {
			report_error("Promenljiva " + arg.getVarName() + " je vec deklarisana", arg);
			arg.struct = Tab.noType;
		}
	}

	// Setujem ovom expr kog je on tipa. Kako to uradim, tako sto vidim kog je tipa
	// term, i samo prepisem
	// Expr::= (TermExpr) Term:t
	public void visit(TermExpr arg) {
		arg.struct = arg.getTerm().struct;
	}

	// isto kao ovo prethodno, s tim sto proverim i da li je term int. JEr ako nije,
	// to je greska.
	// Uz minus sme da stoji samo int
	public void visit(TermExprMin arg) {
		arg.struct = arg.getTerm().struct;
		if (!arg.struct.equals(Tab.intType)) {
			report_error("Minus moze stojati samo ispred promenljive koja je tipe integer ", arg);
		}
	}

	// AddExpr = Expr Addop Term
	// Vidimo kog je tipa Expr. Obradili smo ostale dve definicije za Expr, tako da
	// ce ovde sigurno biti validni tip Expr-a
	// Zatim pogledamo tip term-a
	// ako su oni jednaki -> top, ako nisu -> greska
	public void visit(AddExpr arg) {
		Struct type_of_expr = arg.getExpr().struct;
		Struct type_of_term = arg.getTerm().struct; // ovo nismo definisali. Ide odmah ispod ove funkcije

		if (type_of_expr.equals(type_of_term) && type_of_expr == Tab.intType) {
			arg.struct = type_of_expr;
		} else {
			report_error(" Nekompatibilni tipovi u izrazu za sabiranje/oduzimanje. Ne mozete sabrati/oduzeti "
					+ this.getTypeName(type_of_expr.getKind()) + " i " + this.getTypeName(type_of_term.getKind()), arg);

			arg.struct = Tab.noType;

		}
	}

	// Term ::= (TermMulFactor) Term Mulop Factor:t
	public void visit(TermMulFactor arg) {
		Struct type_of_factor = arg.getFactor().struct;
		Struct type_of_term = arg.getTerm().struct; // nismo ovo setovali. Ide kod odmah ispod

		if (type_of_factor.equals(type_of_term) && type_of_factor == Tab.intType) {
			arg.struct = type_of_term;
		} else {
			report_error(" Nekompatibilni tipovi u izrazu za sabiranje/oduzimanje. Ne mozete sabrati/oduzeti "
					+ this.getTypeName(type_of_factor.getKind()) + " i " + this.getTypeName(type_of_term.getKind()),
					arg);
			arg.struct = Tab.noType;
		}
	}

	// Term ::= (TermSingleFactor) Factor:t
	public void visit(TermSingleFactor arg) {
		arg.struct = arg.getFactor().struct; // ali nismo faktoru setovali tip. ide kod odmah ispod
	}

	// Factor ::= (FactorBoolConst) BOOLEAN
	public void visit(FactorCharConst arg) {
		arg.struct = Tab.charType;
		report_info("Obradjena char konstanta " + arg.getC1() + " i setovan joj je tip "
				+ this.getTypeName(arg.struct.getKind()), arg);
	}

	// Factor ::= (FactorCharConst) CHAR
	public void visit(FactorBoolConst arg) {
		arg.struct = Tab.find("bool").getType();
		// report_info("Obradjena bool konstanta " + arg.getB1() + " i setovan joj je
		// tip na " + this.getTypeName(arg.struct.getKind()), arg);
	}

	// Factor ::= (FactorNumConst) NUMBER
	public void visit(FactorNumConst arg) {
		arg.struct = Tab.intType;
		
		// report_info("Obradjena int konstanta " + arg.getN1() + " i setovan joj je tip
		// na " + this.getTypeName(arg.struct.getKind()), arg);
	}

	// Factor ::= (FactorExpr) LPAREN Expr RPAREN -> ovo je npr (2+3-4) -> int
	public void visit(FactorExpr arg) {
		arg.struct = arg.getExpr().struct;
	}

	// Factor ::= (FactorNewSquare) NEW Type LSQUARE Expr RSQUARE
	public void visit(FactorNewSquare arg) {
		Struct expression = arg.getExpr().struct;
		Obj typeObj = Tab.find(arg.getType().getTypeName());

		if (!expression.equals(Tab.intType)) {
			report_error("Izraz izmedju [] mora biti tipa integer, a ne " + this.getTypeName(expression.getKind()),
					arg);
			arg.struct = Tab.noType;
		} else {
			Struct arrayType = new Struct(Struct.Array, arg.getType().struct);
			arg.struct = arrayType;
			report_info("Napravljen je niz objekata tipa " + this.getTypeName(arg.struct.getKind()), arg);
		}
	}

	// Factor ::= (FactorNew) NEW Type
	public void visit(FactorNew arg) {
		Obj o = Tab.find(arg.getType().getTypeName());
		int kind = o.getType().getKind();
		if (kind >= 10) {
			report_info("Napravljen je objekat korisnickog tipa " + o.getName() + " ", arg);
			arg.struct = arg.getType().struct;
		} else {
			report_error("Kljucnu rec new je dozvoljeno koristiti samo uz tipove definisane od strane korisnika ", arg);
			arg.struct = Tab.noType;
		}
	}

	// Factor ::= (FactorDesignator) Designator:d
	public void visit(FactorDesignator fd) {
		fd.struct = fd.getDesignator().obj.getType(); // ali nije setovan tip designatoru. Ide odmah ispod. NOTE: Kod
														// designatora, u objectu cuvamo kog je on tipa
	}

	// Designator ::= (DesignatorSingle) IDENT:name
	public void visit(DesignatorSingle arg) {
		Obj obj = Tab.find(arg.getName());
		if (obj == Tab.noObj) {
			report_error("Ime " + arg.getName() + " nije deklarisano ", arg);
		}

		arg.obj = obj; // sada u obj cuvamo kog je on tipa
	}

	// Designator ::= (DesignatorDot) Designator DOT IDENT:polje
	public void visit(DesignatorDot arg) {

		Obj postoji = arg.getDesignator().obj.getType().getMembersTable().searchKey(arg.getPolje());
		// ako je obj2 != null -> desni deo se sadrzi u levom delu
		Obj levi = arg.getDesignator().obj;
		String desni_name = arg.getPolje();
		if (arg.getDesignator().obj.getType().getKind() == RECORD_STRUCT_ID) {
			// provera da li se polje kojem je korisnik pristupio stvarno nalazi u record-u
			if (postoji != null) {
				report_info(
						"Dohvatanje vrednosti polja " + arg.getPolje() + " objekta " + arg.getDesignator().obj.getName()
								+ " klase " + this.getTypeName(arg.getDesignator().obj.getType().getKind()),
						arg);

				arg.obj = postoji;
			} else {
				report_error("Dohvatanje polja " + arg.getPolje() + " koje ne postoji u klasi "
						+ arg.getDesignator().obj.getName(), arg);
				arg.obj = Tab.noObj;
			}
		} else {
			report_error("Promenljiva " + arg.getDesignator().obj.getName() + " nije tipa rekord( type_id = "
					+ arg.getDesignator().obj.getType().getKind(), arg);
			arg.obj = Tab.noObj;
		}

	}

	// Designator ::= (DesignatorSquare) Designator LSQUARE Expr:e RSQUARE
	public void visit(DesignatorSquare arg) {
		// Designator mora biti tipa niz
		Struct typeObj = arg.getDesignator().obj.getType();
		Struct typeExpr = arg.getExpr().struct;
		if (typeObj.getKind() != Struct.Array) {
			report_error("Pokusali ste da indeksirate objekat koji nije niz", arg);
			arg.obj = Tab.noObj;
		} else if (typeExpr.getKind() != Struct.Int) {
			report_error("Pokusali ste da indeksirate niz " + arg.getDesignator().obj.getName() + " sa vrednoscu tipa "
					+ this.getTypeName(typeExpr.getKind()) + ". Dozvoljeno je indeksiranje samo sa vrednoscu int tipa.",
					arg);
			arg.obj = Tab.noObj;
		} else {
			report_info("Pristupiljeno elementu niza " + arg.getDesignator().obj.getName(), arg);
			arg.obj = new Obj(Obj.Elem, null, arg.getDesignator().obj.getType().getElemType());
		}
	}

	// FormParsSingle ::= (FormalParamsSingleSquare) Type IDENT:formparssingle
	public void visit(FormalParamsSingleSquare arg) {
		Obj obj = Tab.insert(Obj.Var, arg.getFormparssingle(), arg.getType().struct);
		ArrayList<Struct> arr = method_params.get(currentMethod.getName());
		arr.add(arg.getType().struct);
	}

	// FormParsSingle ::= (FormParsSingleN) Type IDENT:formparssinglesquare LSQUARE
	// RSQUARE
	public void visit(FormParsSingleN arg) {
		Obj obj = Tab.insert(Obj.Var, arg.getFormparssinglesquare(), arg.getType().struct);
		ArrayList<Struct> arr = method_params.get(currentMethod.getName());
		arr.add(arg.getType().struct);
	}

	// Designator ::= (FuncCallP) Designator:func LPAREN ActPars RPAREN
	public void visit(FuncCallP arg) {
		ActPars pom = arg.getActPars();
		Obj function = arg.getDesignator().obj;
		ArrayList<Struct> stvarni_parametri_funkcije = method_params.get(function.getName());
		ArrayList<Struct> prosledjeni_parametri = new ArrayList<>();

		while (pom.getClass() != ActParamsSingle.class) {
			prosledjeni_parametri.add(((ActParams) pom).getExpr().struct);
			pom = ((ActParams) pom).getActPars();
		} // sacuvacemo sve Type od parametra u listu.
		prosledjeni_parametri.add(((ActParamsSingle) pom).getExpr().struct);

		Collections.reverse(prosledjeni_parametri);
		ActPars s = arg.getActPars();
		// System.out.println(prosledjeni_parametri);
		// System.out.println(parametri_ocekivani);
		if (Obj.Meth != function.getKind()) {
			report_error(function.getName() + " nije funkcija!", null);
			arg.struct = Tab.noType;
		} else if (!valid_params(stvarni_parametri_funkcije, prosledjeni_parametri)) {
			report_error("Ocekivani parametri funkcije " + function.getName()
					+ " se ne poklapaju sa prosledjenim parametrima ", arg);
			arg.struct = Tab.noType;
		} else {
			report_info("Pozivanje funkcije " + function.getName() + " na liniji " + arg.getLine(), null);
			arg.struct = function.getType(); // samo da setujemo pri deklaraciji metoda njen tip, i sve ok
		}
	}

	// Designator ::= (FuncCallNP) Designator:func LPAREN RPAREN
	public void visit(FuncCallNP arg) {

		Obj function = arg.getDesignator().obj;
		ArrayList<Struct> parametri_ocekivani = method_params.get(function.getName());

		if (Obj.Meth == function.getKind()) {
			if (parametri_ocekivani.isEmpty()) {
				report_info("Pozivanje funkcije " + function.getName() + " na liniji " + arg.getLine(), null);
				arg.struct = function.getType();
			} else {
				report_error("Funkcija " + function.getName() + " ocekuje parametre ", arg);
				arg.struct = Tab.noType;
			}

		} else {
			report_error(function.getName() + " nije funkcija!", null);
			arg.struct = Tab.noType;
		}
	}

	// DesignatorStatement ::= (DesignatorStatementEq) Designator EQUAL Expr
	public void visit(DesignatorStatementEq arg) {
		Obj designator = arg.getDesignator().obj;
		if (designator != Tab.noObj && designator.getKind() != Obj.Con && designator.getKind() != Obj.Type
				&& designator.getKind() != Obj.Meth && designator.getKind() != Obj.Prog) {
			Struct s1 = arg.getExpr().struct;
			Struct s2 = arg.getDesignator().obj.getType();

			if (!s1.assignableTo(s2)) {
				report_error("Nekompatibilni tipovi u dodeli vrednosti! " + this.getTypeName(s1.getKind()) + " i "
						+ this.getTypeName(s2.getKind()), arg);
			} else {
				report_info("Dodela vrednosti " + arg.getDesignator().obj.getName(), arg);
			}
		} else {
			report_error("Greska: Nedozvoljena dodela vrednosti designator-u koji nije element niza ili promenljiva ",
					arg);
		}
	}

	// DesignatorStatement ::= (DesignatorStatementInc) Designator INC
	public void visit(DesignatorStatementInc arg) {
		Obj designator = arg.getDesignator().obj;
		if (designator != Tab.noObj && designator.getKind() != Obj.Con && designator.getKind() != Obj.Type
				&& designator.getKind() != Obj.Meth && designator.getKind() != Obj.Prog) {
			if (designator.getType().getKind() == Struct.Int) {
				report_info("Inkrementiranje promenljive " + arg.getDesignator().obj.getName(), arg);
			} else {
				report_info("Nedozvoljeno koriscenje inkrementa (++) nad objektom koji nije tipa int.", arg);
			}
		} else {
			report_error(
					"Greska: Nedozvoljena koriscenje inkrementa (++) nad objektom koji nije promenljiva ili element niza. ",
					arg);
		}
	}

	// DesignatorStatement ::= (DesignatorStatementInc) Designator DEC
	public void visit(DesignatorStatementDec arg) {
		Obj designator = arg.getDesignator().obj;
		if (designator != Tab.noObj && designator.getKind() != Obj.Con && designator.getKind() != Obj.Type
				&& designator.getKind() != Obj.Meth && designator.getKind() != Obj.Prog) {
			if (designator.getType().getKind() == Struct.Int) {
				report_info("Inkrementiranje promenljive " + arg.getDesignator().obj.getName(), arg);
			} else {
				report_info("Nedozvoljeno koriscenje dekrementa (--) nad objektom koji nije tipa int.", arg);
			}
		} else {
			report_error(
					"Greska: Nedozvoljena koriscenje dekrementa (--) nad objektom koji nije promenljiva ili element niza. ",
					arg);
		}
	}

	// DesignatorStatement ::= (DesignatorStatementActParamsEmpty) Designator LPAREN
	// RPAREN
	public void visit(DesignatorStatementActParamsEmpty arg) {
		Obj fun = arg.getDesignator().obj;
		ArrayList<Struct> parametri_ocekivani = method_params.get(fun.getName());

		if (Obj.Meth == fun.getKind()) {
			if (parametri_ocekivani.isEmpty()) {
				report_info("Pozivanje funkcije " + fun.getName() + " na liniji " + arg.getLine(), null);
				arg.struct = fun.getType();
			} else {
				report_error("Funkcija " + fun.getName() + " ocekuje parametre ", arg);
				arg.struct = Tab.noType;
			}

		} else {
			report_error("Ime " + fun.getName() + " nije funkcija!", arg);
			arg.struct = Tab.noType;
		}
	}

	// DesignatorStatement ::= (DesignatorStatementActParams) Designator LPAREN
	// ActPars RPAREN
	public void visit(DesignatorStatementActParams arg) {
		ActPars pom = arg.getActPars();
		Obj function = arg.getDesignator().obj;
		ArrayList<Struct> stvarni_parametri_funkcije = method_params.get(function.getName());
		ArrayList<Struct> prosledjeni_parametri = new ArrayList<>();

		while (pom.getClass() != ActParamsSingle.class) {
			prosledjeni_parametri.add(((ActParams) pom).getExpr().struct);
			pom = ((ActParams) pom).getActPars();
		} // sacuvacemo sve Type od parametra u listu.
		prosledjeni_parametri.add(((ActParamsSingle) pom).getExpr().struct);

		Collections.reverse(prosledjeni_parametri);
		ActPars s = arg.getActPars();
		// System.out.println(prosledjeni_parametri);
		// System.out.println(parametri_ocekivani);
		if (Obj.Meth != function.getKind()) {
			report_error(function.getName() + " nije funkcija!", null);
			arg.struct = Tab.noType;
		} else if (!valid_params(stvarni_parametri_funkcije, prosledjeni_parametri)) {
			report_error("Ocekivani parametri funkcije " + function.getName()
					+ " se ne poklapaju sa prosledjenim parametrima ", arg);
			arg.struct = Tab.noType;
		} else {
			report_info("Pozivanje funkcije " + function.getName() + " na liniji " + arg.getLine(), null);
			arg.struct = function.getType(); // samo da setujemo pri deklaraciji metoda njen tip, i sve ok
		}

	}

	// Matched:
	// Matched ::= (StatementDoWhile) Do StatementList WHILE LPAREN Condition RPAREN
	// SEMI
	public void visit(DoStmt arg) {
		dowhile_active_counter++;
	}

	public void visit(StatementDoWhile arg) {
		dowhile_active_counter--;
	}

	// Matched :: = break SEMI
	public void visit(StatementBreak arg) {
		if (dowhile_active_counter == 0) {
			report_error("Break se moze koristiti samo u do-while petlji.", arg);
		}
	}

	// SingleStatement = continue
	public void visit(StatementContinue arg) {
		if (dowhile_active_counter == 0) {
			report_error("Continue se moze koristiti samo u do-while petlji.", arg);
		}
	}

	// Matched ::= (StatementReturn) RETURN Expr SEMI
	public void visit(StatementReturn arg) {

		Struct currMethType = currentMethod.getType();
		if (functions_active_counter == 0) {
			report_error("Return ne sme postojati izvan funkcija", arg);
		} else if (!currMethType.compatibleWith(arg.getExpr().struct)) {
			report_error("Greska na liniji " + arg.getLine() + " : "
					+ "tip izraza u return naredbi ne slaze se sa tipom povratne vrednosti funkcije "
					+ currentMethod.getName(), null);
		} else
			returnFound = true;
	}

	// Matched ::= (StatementReturnNull) RETURN SEMI
	public void visit(StatementReturnNull arg) {
		if (functions_active_counter == 0) { // TODO: resi functions_active_counter inkrement.
			report_error("Return ne sme postojati izvan funkcija", arg);
		} else if (!(currentMethod.getType().equals(Tab.noType))) { // da li je currentMethod void
			report_error("Metoda " + currentMethod.getName() + " mora biti deklarisana kao void. ", arg);
		} else
			returnFound = true;
	}

	// ConstType ::= (NumConstDeclaration) NUMBER:num
	public void visit(NumConstDeclaration arg) {
		arg.struct = Tab.intType;
		
		
	}

	// ConstType ::= (CharConstDeclaration) CHAR:chr
	public void visit(CharConstDeclaration arg) {
		arg.struct = Tab.charType;
	}

	// ConstType ::= (BoolConstDeclaration) BOOLEAN:bool
	public void visit(BoolConstDeclaration arg) {
		arg.struct = Tab.find("bool").getType();
	}

	
	private void setConstValue(ConstType constType, Obj obj) {
		if (constType.getClass() == NumConstDeclaration.class) {
			NumConstDeclaration cit = (NumConstDeclaration) constType;
			obj.setAdr(cit.getNum());
		}
		if (constType.getClass() == CharConstDeclaration.class) {
			CharConstDeclaration cit = (CharConstDeclaration) constType;
			obj.setAdr(cit.getChr());
		}
		if (constType.getClass() == BoolConstDeclaration.class) {
			BoolConstDeclaration cit = (BoolConstDeclaration) constType;
			obj.setAdr(cit.getBool().equals("true") ? 1 : 0);
		}

	}
	
	// ConstDecl ::= (ConstDeclaration) CONST Type:type IDENT:constName EQUAL
	// ConstType:constType ConstDeclPomList SEMI;
	public void visit(ConstDeclaration arg) {
		if (!arg.getType().struct.equals(arg.getConstType().struct)) {
			report_error("Nekompatibilni tipovi dodele konstanti: " + arg.getType().getTypeName() + " i "
					+ arg.getConstType().getClass().toString().substring(6), arg);
			arg.struct = Tab.noType;
		} else {
			report_info("Deklarisana konstanta " + arg.getConstName(), arg);
			Obj varNode = Tab.insert(Obj.Con, arg.getConstName(), arg.getType().struct);
			setConstValue(arg.getConstType(), varNode);
			arg.struct = arg.getType().struct;
		}
	}

	// ConstDeclPomList ::= (ConstDeclarationPomList) ConstDeclPomList COMMA
	// IDENT:constName EQUAL ConstType:constType
	public void visit(ConstDeclarationPomList arg) {
		Object pom = arg.getParent();
		while (pom.getClass() != ConstDeclaration.class) {
			pom = ((ConstDeclarationPomList) pom).getParent();
		}
		if (!((ConstDeclaration) pom).getType().struct.equals(arg.getConstType().struct)) {
			report_error("Nekompatibilni tipovi dodele konstanti: " + ((ConstDeclaration) pom).getType().getTypeName()
					+ " = " + arg.getConstType().getClass().toString().substring(6), arg);
			arg.struct = Tab.noType;
		} else {
			report_info("Deklarisana konstanta " + arg.getConstName(), arg);
			Obj varNode = Tab.insert(Obj.Con, arg.getConstName(), ((ConstDeclaration) pom).getType().struct);
			arg.struct = ((ConstDeclaration) pom).getType().struct;
		}
	}

	public void visit(UnmatchedIf arg) {
		report_info("Obrada IF naredbe ", arg);
	}

	public void visit(UnmatchedIfElse arg) {
		report_info("Obrada IF-ELSE naredbe ", arg);
	}

	// Condition-i

	// CondFact ::= (ConditionFactor) Expr Relop Expr
	public void visit(ConditionFactor arg) {
		// pretpostavljam da ovde ne treba nista, jer ako je koriscen relop, rezultat
		// izraza ce sigurno biti bool
		Struct expression1 = arg.getExpr().struct;
		Struct expression2 = arg.getExpr1().struct;

		Relop relop = arg.getRelop();

		if ((expression1.getKind() == Struct.Array || expression2.getKind() == Struct.Array)
				&& (relop.getClass() != EqualsOp.class && relop.getClass() != NoEqualsOp.class)) {
			report_error("Sa nizovima se smeju koristiti samo == i != ", arg);
		}

		else if (!expression1.compatibleWith(expression2)) {
			report_error("Izrazi moraju kompatibilni da bi se poredili ", arg);
		} else {
			report_info("Izvrsavanje poredjenje izraza ", arg);
		}
	}

	// CondFact::= (ConditionFactorSingle) Expr
	public void visit(ConditionFactorSingle arg) {
		// ovde treba da se proveri Expr da li je bool
		Struct expression = arg.getExpr().struct;
		if (!(expression.getKind() == Struct.Bool)) {
			report_error("Uslov mora biti tipa boolean ", arg);
		} else {
			report_info("Obrada uslova ", arg);
		}
	}

	public void visit(RecordDeclarationName arg) {
		//System.out.println("record pronadjen");
		arg.obj = Tab.insert(Obj.Type, arg.getRecordName(), new Struct(RECORD_STRUCT_ID));
		Tab.openScope();
	}

	public void visit(RecordDeclaration arg) {
		Tab.chainLocalSymbols(arg.getRecordDeclName().obj.getType());
		Tab.closeScope();
	}

	// SingleStatement :

	// SingleStatement ::= (SingleStatementRead) READ LPAREN Designator RPAREN SEMI
	public void visit(SingleStatementRead arg) {
		Obj designator = arg.getDesignator().obj;
		if (designator != Tab.noObj && designator.getKind() != Obj.Con && designator.getKind() != Obj.Type
				&& designator.getKind() != Obj.Meth && designator.getKind() != Obj.Prog) {
			if (designator.getType().getKind() == Struct.Int || designator.getType().getKind() == Struct.Char
					|| designator.getType().getKind() == Struct.Bool) {
				report_info("Izvrsavanje READ naredbe nad argumentom " + arg.getDesignator().obj.getName(), arg);
			} else {
				report_error("READ: Tip objekta jeste Var, ali tip varijable nije int/char/bool.", arg);
			}
		} else {
			report_error("READ: Tip objekta nije Var.", arg);
		}
	}

	// SingleStatement ::= (SingleStatementPrintNumconst) PRINT LPAREN Expr COMMA
	// NUMBER RPAREN SEMI
	public void visit(SingleStatementPrintNumconst arg) {
		if (arg.getExpr().struct != Tab.intType && arg.getExpr().struct != Tab.charType)
			report_error("Dozvoljeno je printati samo int i char tipove", arg);
		else
			printCallCount++;
	}

	// SingleStatement ::= (SingleStatementPrint) PRINT LPAREN Expr RPAREN SEMI
	public void visit(SingleStatementPrint arg) {
		Struct expression = arg.getExpr().struct;
		if (expression.getKind() == Struct.Bool || expression.getKind() == Struct.Int
				|| expression.getKind() == Struct.Char) {
			report_info(
					"Izvrsavanje naredbe print nad argumentom tipa " + this.getTypeName(arg.getExpr().struct.getKind()),
					arg);
		} else {
			report_error("Print se moze izvrsiti samo nad naredbom koja je int, char ili boolean, a ne nad "
					+ this.getTypeName(arg.getExpr().struct.getKind()), arg);
		}
	}
	
	// (SingleStatementOrd) ORD LPAREN CHAR RPAREN SEMI


//	public void visit(FactorExpr arg) {
//		arg.struct = arg.getExpr().struct;
//	}

	public boolean passed() {
		return !errorDetected;
	}

	public static String getTypeName(int kind) {
		switch (kind) {
		case Struct.None:
			return "none";
		case Struct.Char:
			return "char";
		case Struct.Int:
			return "int";
		case Struct.Bool:
			return "bool";
		case Struct.Enum:
			return "enum";
		case Struct.Array:
			return "arr";
		case RECORD_STRUCT_ID:
			return "record";
		}
		return "Unknown type";
	}

	public static boolean valid_params(ArrayList<Struct> l1, ArrayList<Struct> l2) {
		if(l1 == null || l2 == null) return false;
		if (l1.size() != l2.size())
			return false;
		for (int i = 0; i < l1.size(); i++)
			if (!l1.get(i).assignableTo(l2.get(i)))
				return false;
		return true;
	}
}
