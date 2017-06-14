/*  a Node holds one node of a parse tree
    with several pointers to children used
    depending on the kind of node
*/

import java.util.ArrayList;
import java.util.Scanner;
import java.awt.*;

public class Node {
  public static int count = 0;  // maintain unique id for each node

  private int nodeIDNum;
  
  private static ArrayList<IDPair> mem = new ArrayList<IDPair>(); 
  
  public String kind;  // non-terminal or terminal category for the node
  public String info;  // extra information about the node such as
                        // the actual identifier for an I

  // references to children in the parse tree
  // (uniformly use these from first to fourth)
  private Node first, second, third, fourth; 

  public Node( String k, Node one, Node two, Node three, Node four ) {
    kind = k;  
    info = "";  
    first = one;  second = two;  third = three;  fourth = four;
    nodeIDNum = count;
    count++;
    System.out.println( this );
  } 

  public Node( String k, String i, Node one, Node two, Node three, Node four ) {
    kind = k;  
    info = i;  
    first = one;  second = two;  third = three;  fourth = four;
    nodeIDNum = count;
    count++;
    System.out.println( this );
  }
  
  // construct a node that is essentially a token
  public Node( Token token ) {
    kind = token.getKind();  info = token.getDetails();  
    first = null;  second = null;  third = null;  fourth = null;
    nodeIDNum = count;
    count++;
    System.out.println( this );
  }

  public String toString() {
    return "#" + nodeIDNum + "[" + kind + "," + info + "]";
  }

  // produce array with the non-null children
  // in order
  private Node[] getChildren() {
    int count = 0;
    if( first != null ) count++;
    if( second != null ) count++;
    if( third != null ) count++;
    if( fourth != null ) count++;
    Node[] children = new Node[count];
    int k=0;
    if( first != null ) {  children[k] = first; k++; }
    if( second != null ) {  children[k] = second; k++; }
    if( third != null ) {  children[k] = third; k++; }
    if( fourth != null ) {  children[k] = fourth; k++; }

     return children;
  }

  //******************************************************
  // graphical display of this node and its subtree
  // in given camera, with specified location (x,y) of this
  // node, and specified distances horizontally and vertically
  // to children
  public void draw( Camera cam, double x, double y, double h, double v ) {

//System.out.println("draw node " + id );

    // set drawing color
    cam.setColor( Color.black );

    String text = kind;
    if( ! info.equals("") ) text += "(" + info + ")";
    cam.drawHorizCenteredText( text, x, y );

    // positioning of children depends on how many
    // in a nice, uniform manner
    Node[] children = getChildren();
    int number = children.length;
//System.out.println("has " + number + " children");

    double top = y - 0.75*v;

    if( number == 0 ) {
      return;
    }
    else if( number == 1 ) {
      children[0].draw( cam, x, y-v, h/2, v );     cam.drawLine( x, y, x, top );
    }
    else if( number == 2 ) {
      children[0].draw( cam, x-h/2, y-v, h/2, v );     cam.drawLine( x, y, x-h/2, top );
      children[1].draw( cam, x+h/2, y-v, h/2, v );     cam.drawLine( x, y, x+h/2, top );
    }
    else if( number == 3 ) {
      children[0].draw( cam, x-h, y-v, h/2, v );     cam.drawLine( x, y, x-h, top );
      children[1].draw( cam, x, y-v, h/2, v );     cam.drawLine( x, y, x, top );
      children[2].draw( cam, x+h, y-v, h/2, v );     cam.drawLine( x, y, x+h, top );
    }
    else if( number == 4 ) {
      children[0].draw( cam, x-1.5*h, y-v, h/2, v );     cam.drawLine( x, y, x-1.5*h, top );
      children[1].draw( cam, x-h/2, y-v, h/2, v );     cam.drawLine( x, y, x-h/2, top );
      children[2].draw( cam, x+h/2, y-v, h/2, v );     cam.drawLine( x, y, x+h/2, top );
      children[3].draw( cam, x+1.5*h, y-v, h/2, v );     cam.drawLine( x, y, x+1.5*h, top );
    }
    else {
      System.out.println("no Node kind has more than 4 children???");
      System.exit(1);
    }

  }// draw

  // a node can translate the tree rooted at it to CalcLang code
  
  public static void execute(Node iNode){
	  if(iNode.kind.equals("program")){
		  execute(iNode.first);
	  }else if(iNode.kind.equals("statements")){
		  execute(iNode.first);
		  if(iNode.info.equals("more statements")){
			  execute(iNode.second);
		  }
	  }else if(iNode.kind.equals("statement")){
		  execute(iNode.first);
	  }else if(iNode.kind.equals("newlineStatement")){
		  System.out.println();
	  }else if(iNode.kind.equals("printStatement")){
		  System.out.print(evaluate(iNode.second));
	  }else if(iNode.kind.equals("messageStatement")){
		  System.out.print(iNode.info);		  
	  }else if(iNode.kind.equals("inputStatement")){
		  String id = "";
		  double value = 0;
		  IDPair pair = null;
		  boolean idExists = false;
		  @SuppressWarnings("resource")
		Scanner keyboard = new Scanner(System.in);
		  id = iNode.second.info;
		  
		  System.out.print(iNode.info);

		  value = keyboard.nextDouble();
		  if(!mem.isEmpty()){
			  for(int i = 0; i < mem.size(); i++){
				  pair = mem.get(i);
				  if(pair.id.equals(id)){
					  pair.value = value;
					  idExists = true;
				  }
			  }  
		  }
		  
		  
		  if(!idExists){
			  pair = new IDPair(id, value);
			  mem.add(pair);
		  }
		  
	  }else if(iNode.kind.equals("assignmentStatement")){
		  String id = "";
		  double value = 0;
		  IDPair pair = null;
		  boolean idExists = false;
		  
		  id = iNode.first.info;
		  value = evaluate(iNode.second);
		  if(!mem.isEmpty()){
			  for(int i = 0; i < mem.size(); i++){
				  pair = mem.get(i);
				  if(pair.id.equals(id)){
					  pair.value = value;
					  idExists = true;
				  }
			  }  
		  }
		  
		  
		  if(!idExists){
			  pair = new IDPair(id, value);
			  mem.add(pair);
		  }
		  
	  }else {
		  System.out.println("Attempted to execute undefined statement of kind: " + iNode.info + " while evaluating node: " +iNode.toString());
		  System.exit(1);
	  }
  }

  public static double evaluate(Node iNode){
	  double result = 0;
	  String operation = "";
	  
	  if(iNode.kind.equals("expression")){//if expression
		  double e1 = evaluate(iNode.first);
		  double e2 = 0;
		  operation = iNode.info;
		  if(operation.equals("+")){
			  e2 = evaluate(iNode.second);
			  result =  e1 + e2;
		  }else if(operation.equals("-")){
			  e2 = evaluate(iNode.second);
			  result = e1 - e2;
		  }else {
			  result = e1;
		  }
		  
	  }else if(iNode.kind.equals("term")){//if term
		  double t1 = evaluate(iNode.first);
		  double t2 = 0;
		  operation = iNode.info;
		  if(operation.equals("*")){
			  t2 = evaluate(iNode.second);
			  result = t1 * t2;
		  }else if(operation.equals("/")){
			  t2 = evaluate(iNode.second);
			  result = t1/t2;
		  }else {
			  result = t1;
		  }
		  
	  }else if(iNode.kind.equals("factor")){
		  operation = iNode.info;
		  if(operation.equals("-")){
			  result = evaluate(iNode.first);
			  result = -result;
		  }else {
			  result = evaluate (iNode.first);
		  }
		  
	  }else if(iNode.kind.equals("funcCall")){
		  operation = iNode.info;
		  if(operation.equals("sin")){
			  result = Math.sin(evaluate(iNode.first));
		  }else if(operation.equals("cos")){
			  result = Math.cos(evaluate(iNode.first));
		  }else if(operation.equals("exp")){
			  result = Math.exp(evaluate(iNode.first));
		  }else if(operation.equals("sqrt")){
			  result = Math.sqrt(evaluate(iNode.first));
		  }else {//not a defined function
			  System.out.println("funcCall to undefined function: " + operation + " encountered while evaluating node: " +iNode.toString() );
			  System.exit(1);
		  }
		  
	  }else if(iNode.kind.equals("args")){
		  result = evaluate(iNode.first);
	  }else if(iNode.kind.equals("num")){
		  result = Double.parseDouble(iNode.info);
	  }else if(iNode.kind.equals("id")){
		  String id = "";
		  IDPair pair = null;
		  IDPair trialPair = null;
		  boolean foundID = false;
		  
		  id = iNode.info;
		  //look for the matching id in memory
		  for(int i = 0; i < mem.size(); i++){
			   trialPair = mem.get(i);
			  if(trialPair.id.equals(id)){
				  pair = mem.get(i);
				  foundID = true;
			  }
		  }
		  
		  if(foundID){ // if the id is found, return its associated value
			  result = pair.value;
		  }else{ // if the id is not found, then the programmer neglected to create an assignment statement for that reference
			  System.out.println("Unknown variable: " + id + " encountered while evaluating node: " +iNode.toString());
			  System.exit(1);
		  }
		  
	  }else if(iNode.kind.equals("expression")){
		  result = evaluate(iNode);
	  }else{
		  System.out.println("Attempted to evaluate unevaluable node of kind: " + iNode.kind + " while evaluating node: " +iNode.toString());
		  System.exit(1);
	  }
	  
	  return result;
  }
}// Node class
