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

    TreeViewer viewer = new TreeViewer("Parse Tree", 0, 0, 800, 500, root );

  }

  private Node parseProgram()
  {
    Token token = lex.getToken();  

    Node globals = null;

    // optional global list
    if( token.isKind( "global" ) ) {// have globals
      globals = parseGlobalDecs();
    }
    else {// no globals
      lex.putBack( token );
    }

    Node funcDefs = parseFuncDefs();

    Node root = new Node( "program", globals, funcDefs, null, null );
    return root;
  }

  private Node parseGlobalDecs() {
    Token token = lex.getToken();
    errorCheck( token, "id" );
    Node first = new Node( token );
    Node rest = null;

    token = lex.getToken();
    if( token.matches( "single", "," ) ) {// have more global decs
      rest = parseGlobalDecs();
    }
    else
      lex.putBack( token );

    return new Node( "globalDecs", first, rest, null, null );

  }// parseGlobalDecs

  private Node parseFuncCall() {
System.out.println("-----> parsing funcCall:");
    Token token = lex.getToken();
    errorCheck( token, "id" );
    Node first = new Node( token );  
    token = lex.getToken();
    errorCheck( token, "single", "(" );
    token = lex.getToken();
    if( token.matches( "single", ")" ) ) {// have funcCall of form: ID ()
      return new Node( "funcCall", first, null, null, null );
    }
    else {// have args
      lex.putBack( token );
      Node args = parseArgs();
      token = lex.getToken();
      errorCheck( token, "single", ")" );
      return new Node( "funcCall", first, args, null, null );
    }
  }

  private Node parseArgs() {
System.out.println("-----> parsing args:");
    Node first = parseExpression();
    Token token = lex.getToken();
    if( token.matches( "single", "," ) ) {// have more args
      Node rest = parseArgs();
      return new Node( "args", first, rest, null, null );
    }
    else {// done with args
      lex.putBack( token );
      return new Node( "args", first, null, null, null );
    }
  }

  private Node parseExpression() {
System.out.println("-----> parsing expression:");
    Token token1 = lex.getToken();
    if( token1.isKind("num") || token1.isKind("string") ) {
      return new Node("expression", new Node( token1 ), null, null, null );
    }
    else {// token1 must be identifier (noticing <funcCall> starts with ID)
      errorCheck( token1, "id" );
      Node first = new Node( token1 );  // need this node for all 3 possibilities

      Token token2 = lex.getToken();  // look ahead

      if( token2.matches( "single", "[" ) ) {// array retrieve
        Node second = parseExpression();
        Token token = lex.getToken();
        errorCheck( token, "single", "]" );
        return new Node("expression", first, second, null, null );
      }
      else if( token2.matches( "single", "(" ) ) {// funcCall
        lex.putBack( token2 );  // put back ( and identifier that turns out to be func name
        lex.putBack( token1 );
        Node second = parseFuncCall();
        return new Node("expression", second, null, null, null );
      }
      else {// must be just an identifier
        lex.putBack( token2 );
        return new Node("expression", first, null, null, null );
      }
    }

  }

  private Node parseFuncDefs() {
   System.out.println("-----> parsing funcDefs:");
    Node first = parseFuncDef();
    Node second = null;

    Token token = lex.getToken();
    if( token.isKind("def") ) {// have more funcDefs
      lex.putBack( token );
      Node rest = parseFuncDefs();
      return new Node( "funcDefs", first, rest, null, null );
    }
    else {// done with funcDefs
      lex.putBack( token );     // putting back EOF token?
      return new Node( "funcDefs", first, null, null, null );
    }
  }

  private Node parseFuncDef() {
   System.out.println("-----> parsing funcDef:");
   Node first, second=null, third=null;

   // get def decoration
   Token token = lex.getToken();
   errorCheck( token, "def" );

   // get name of function
   token = lex.getToken();
   errorCheck( token, "id" ); // must have name of function
   first = new Node( token );

   token = lex.getToken();              // check for (
   errorCheck( token, "single", "(" );

   token = lex.getToken();              // look ahead

   if( ! token.matches( "single", ")" ) ) {// have params
     lex.putBack( token );
     second = parseParams();
     token = lex.getToken();
     errorCheck( token, "single", ")" );
   }

   // process statements
   token = lex.getToken();
   if( ! token.isKind( "end" ) ) {// have statements
     lex.putBack( token );
     third = parseStatements();
   }
   else
     lex.putBack( token );

   token = lex.getToken();
   errorCheck( token, "end" );

   return new Node( "funcDef", first, second, third, null );
   
  }// parseFuncDef

  private Node parseParams() {
System.out.println("-----> parsing params:");
    Token token = lex.getToken();
    errorCheck( token, "id" );
    Node first = new Node( token );
    Node second = null;

    token = lex.getToken();
    if( token.matches( "single", "," ) ) {// have more
      second = parseParams();
    }
    else
      lex.putBack( token );

    return new Node( "params", first, second, null, null );

  }

  private Node parseStatements() {
System.out.println("-----> parsing statements:");
    Node first = parseStatement();
    Node second = null;

    Token token = lex.getToken();

    if( token.isKind("end") || token.isKind("else") ||
        token.isKind("elif") ) {// there are no more 
      lex.putBack( token );
    }
    else {// there are more statements
      lex.putBack( token );
      second = parseStatements();
    }
 
    return new Node( "statements", first, second, null, null );
  }

  private Node parseStatement() {
    Token token = lex.getToken();

    if( token.isKind("return") ) {
      Node expr = parseExpression();
      return new Node("statement",
                 new Node("returnStatement",expr,null,null,null),
                 null,null,null);
    }
    else if( token.isKind("while") ) {
      lex.putBack( token );
      Node first = parseWhileStatement();
      return new Node("statement", first,null,null,null);
    }
    else if( token.isKind("id") ) {// could be several choices
      lex.putBack( token );
      Node first = parseWhileStatement();
      return new Node("statement", first,null,null,null);
    }
    
    
  }
  // check whether token is correct kind
  private void errorCheck( Token token, String kind ) {
    if( ! token.isKind( kind ) ) {
      System.out.println("Error:  expected " + token + " to be of kind " + kind );
      System.exit(1);
    }
  }

  // check whether token is correct kind and details
  private void errorCheck( Token token, String kind, String details ) {
    if( ! token.isKind( kind ) || ! token.getDetails().equals( details ) ) {
      System.out.println("Error:  expected " + token + " to be kind=" + kind + " and details=" + details );
      System.exit(1);
    }
  }

  

}
