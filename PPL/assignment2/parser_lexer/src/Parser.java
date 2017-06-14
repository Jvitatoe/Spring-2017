/*
    This class provides a recursive descent parser of Blunt,
    creating a parse tree (which can later be translated to
    VPL code)
*/

import java.util.*;
import java.io.*;

public class Parser {

  private Lexer lex;

  public Parser( Lexer lexer ) {
    lex = lexer;
  }

  public static void main(String[] args) throws Exception
  {
    System.out.print("Enter file name: ");
    Scanner keys = new Scanner( System.in );
    String name = keys.nextLine();

    Lexer lex = new Lexer( name );
    Parser parser = new Parser( lex );

    Node root = parser.parseProgram();
    
    Node.execute(root);
    keys.close();
    TreeViewer viewer = new TreeViewer("Parse Tree", 0, 0, 800, 500, root );
  }

  private Node parseProgram(){
	  System.out.println("-----> parsing program:");
	  /*
	   * If we wish to handle 0 or more statements, it might do to get a token before parsing statements.
	   * As it stands, the following lines are somewhat useless :
	   *Token token = lex.getToken();
	   *lex.putBack(token);
	   */
    Node statements = parseStatements();
    Node root = new Node( "program", statements, null, null, null );
    return root;
  }

  private Node parseStatements() {
System.out.println("-----> parsing statements:");
    Node first = parseStatement();
    Node second = null;
    String info = "";

    Token token = lex.getToken();

    if( token.isKind("bif") || token.isKind("id")) {// there are more statements
      lex.putBack( token );
      info = "more statements";
      second = parseStatements();
    }
    else {// there are no more statements
      lex.putBack( token );
      info = "no more statements";
    }
 
    return new Node( "statements", info, first, second, null, null );
  }

  private Node parseStatement(){
	  System.out.println("-----> parsing statement:");
	  
	  Node first = null;
	  Token token = lex.getToken();
	  String statementType = token.getDetails();
	  String tokenKind = token.getKind();
	  lex.putBack(token);
	  if (tokenKind.equals("bif")){// check if statement is system bif
		  if(statementType.equals("msg")){
			  first = parseMessageStatement();
		  }else if(statementType.equals("print")){
			  first = parsePrintStatement();
		  }else if(statementType.equals("newline")){
			  first = parseNewlineStatement();
		  }else if(statementType.equals("input")){
			  first = parseInputStatement();
		  }else{
			  System.out.println("unrecognized statement type: " + statementType + " encountered while parsing " + token.toString());
		  }
	  }else{//must be an assignment
		  first = parseAssignmentStatement();
	  }
	  return new Node("statement", first, null, null, null );
  }
  
  //--------------------PARSE STATEMENTS OF PARTICULAR TYPES ------------------------------
  /*
   * parse<Type>Statement creates a node that is a system function call. 
   * Should make a function of kind bif,
   * Where Details = <Type>
   * Node should take care of dealing with invocation of specific behavior
   */
private Node parseMessageStatement() {
	System.out.println("-----> parsing message statement:");
	
	Node first = null;
	String arg = "";
	Token token = lex.getToken();
	
	errorCheck(token, "bif", "msg");
	first = new Node(token);
	
	token = lex.getToken();
	errorCheck(token, "string");
	arg = token.getDetails();
	
	return new Node("messageStatement", arg, first, null, null, null);
}

private Node parsePrintStatement() {
	System.out.println("-----> parsing print statement:");
	
	Node first = null;
	Node second = null;
	
	Token token = lex.getToken();
	errorCheck(token, "bif", "print");
	first = new Node(token);
	
	second = parseExpression();
	
	return new Node("printStatement", first, second, null, null);
}

private Node parseNewlineStatement() {
	System.out.println("-----> parsing newline statement:");
	
	Token token = lex.getToken();
	errorCheck(token, "bif", "newline");
	
	return new Node("newlineStatement", null, null, null, null);
}

private Node parseInputStatement() {
	System.out.println("-----> parsing input statement:");
	Node first = null;
	Node second = null;
	String arg1 = "";
	Token token = lex.getToken();
	
	errorCheck(token, "bif", "input");
	first = new Node(token);
	
	token = lex.getToken();
	errorCheck(token, "string");
	arg1 = token.getDetails();
	
	token = lex.getToken();
	errorCheck(token, "id");
	lex.putBack(token);
	second = parseID();
	
	return new Node("inputStatement", arg1, first, second, null, null);
}

private Node parseAssignmentStatement() {
	System.out.println("-----> parsing assignment statement:");
	Node first = null;
	Token token = lex.getToken();	
	errorCheck(token, "id");
	lex.putBack(token);	
	first = parseID();
	
	token = lex.getToken();
	errorCheck(token, "single", "=");
	
	Node second = parseExpression();

	return new Node("assignmentStatement", first, second, null, null);
}

// ------------------END OF STATEMENT TYPE DEFINITIONS------------------------------
private Node parseID() {
	Token token = lex.getToken();
	
	errorCheck(token, "id");
		
	return new Node(token);
}

private Node parseExpression() {//parses an expression: 1 or more Terms composed of 1 or more Factors separated by a + or -
System.out.println("-----> parsing expression:");
	
	Node first= parseTerm();
	Node second = null;
	String operation = "";
	
	Token token1 = lex.getToken();  // look ahead
	
	if(token1.matches("single", "+") || token1.matches("single", "-")){ // if there is a + or -, there must be more expressions
		operation = token1.getDetails();
		second = parseExpression();
	}else { //otherwise, there are no more expressions
		lex.putBack(token1);
	}
    
	return new Node("expression", operation, first, second, null, null);
  }

private Node parseTerm(){
	System.out.println("-----> parsing term:");
	Node first= parseFactor();
	Node second = null;
	String operation = "";
	
	Token token1 = lex.getToken();  // look ahead
	
	if(token1.matches("single", "*") || token1.matches("single", "/")){ // if there is a * or /, there must be more Terms
		operation = token1.getDetails();
		second = parseTerm();
	}else { //otherwise, there are no more terms
		lex.putBack(token1);
	}
    
	return new Node("term", operation, first, second, null, null);
}

private Node parseFactor(){
	System.out.println("-----> parsing factor:");
	Node first = null;
	Token token1 = lex.getToken(); //look ahead
	Token token2 = null;
	String operation = "";
	
	while (token1.matches("single", "-")){ //if the token is a '-', we must negate the factor which is to follow it
		if(operation.equals("")){  
		operation = token1.getDetails();
		} else{
			operation = "";
		}
		  token1 = lex.getToken();
	}
	
	if(token1.isKind("num")) {//if the token is a numeric literal, make a node out of it
			first = new Node(token1);
		  }else if ( token1.isKind("bif")){//if the token is a bif, put the token back and invoke parseFuncCall
		  	lex.putBack(token1);
		  	first = parseFuncCall();
		  }else if (token1.matches("single", "(")) { //if the factor begins with a left paren, it must be another expression
			  first = parseExpression();
			  token2 = lex.getToken();
			  errorCheck( token2, "single", ")");
		  }else {// token1 must be an Identifier
		    errorCheck( token1, "id" );
		    lex.putBack(token1);
		    first = parseID();
		  }
	return new Node ("factor", operation, first, null, null, null);
	
}

/*
 * ParseFuncCall creates a node that is a function call. 
 * Should make a function of kind bif,
 * With Details: sin, cos, exp, or sqrt.
 * Node should take care of dealing with invocation of specific functions
 */

private Node parseFuncCall() {
System.out.println("-----> parsing funcCall:");
	Node args = null;
	Token token = lex.getToken();
  
  errorCheck( token, "bif" );
  
  String functionName = token.getDetails(); 
  token = lex.getToken();
  errorCheck( token, "single", "(" );
  
  args = parseArgs();
  
  token = lex.getToken();
  errorCheck( token, "single", ")" );
  
  return new Node( "funcCall", functionName, args, null, null, null );
}

private Node parseArgs() {
System.out.println("-----> parsing args:");
  Node first = parseExpression();
  Node rest = null;
  Token token = lex.getToken();
  if( token.matches( "single", "," ) ) {// have more args
    rest = parseArgs();
  }else {// done with args
    lex.putBack( token );
  }
  
  return new Node("args", first, rest, null, null);
}

// check whether token is correct kind and details
  private void errorCheck( Token token, String kind, String details ) {
    if( ! token.isKind( kind ) || ! token.getDetails().equals( details ) ) {
      System.out.println("Error:  expected " + token + " to be kind=" + kind + " and details=" + details );
      System.exit(1);
    }
  }

  // check whether token is correct kind
  private void errorCheck( Token token, String kind ) {
    if( ! token.isKind( kind ) ) {
      System.out.println("Error:  expected " + token + " to be of kind " + kind );
      System.exit(1);
    }
  }

}
