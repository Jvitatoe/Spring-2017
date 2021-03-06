
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Scanner;

public class VPLstart {
   static final int max = 10000;
   static int[] mem = new int[max];
   static int ip, bp, sp, rv, hp, numPassed, gp;

   static String fileName;

   public static void main(String[] args) throws Exception
   {
      BufferedReader keys = new BufferedReader( 
                              new InputStreamReader( System.in));
      System.out.print("Enter name of file containing VPLstart program: ");
      fileName = keys.readLine();
   
       // load the program into the front part of
       // memory
      BufferedReader input = new BufferedReader( new FileReader( fileName ));
      String line;
      StringTokenizer st;
      int opcode = 0;
   
      ArrayList<IntPair> labels, holes;
      labels = new ArrayList<IntPair>();
      holes = new ArrayList<IntPair>();
      int label;
   
      int k=0;
      do {
         line = input.readLine();
         System.out.println("parsing line [" + line + "]");
         if( line != null )
         {// extract any tokens
            st = new StringTokenizer( line );
            if( st.countTokens() > 0 )
            {// have a token, so must be an instruction (as opposed to empty line)
            
               opcode = Integer.parseInt(st.nextToken());
            
             // load the instruction into memory:
            
               if( opcode == labelCode )
               {// note index that comes where label would go
                  label = Integer.parseInt(st.nextToken());
                  labels.add( new IntPair( label, k ) );
               }
               else
               {// opcode actually gets stored
                  mem[k] = opcode;  ++k;
               
                  if( opcode == callCode || opcode == jumpCode ||
                   opcode == condJumpCode )
                  {// note the hole immediately after the opcode to be filled in later
                     label = Integer.parseInt( st.nextToken() );
                     mem[k] = label;  holes.add( new IntPair( k, label ) );
                     ++k;
                  }
               
               // load correct number of arguments (following label, if any):
                  for( int j=0; j<numArgs(opcode); ++j )
                  {
                     mem[k] = Integer.parseInt(st.nextToken());
                     ++k;
                  }
               
               }// not a label
            
            }// have a token, so must be an instruction
         }// have a line
      }while( line != null );
       
       //System.out.println("after first scan:");
       //showMem( 0, k-1 );
   
       // fill in all the holes:
      int index;
      for( int m=0; m<holes.size(); ++m )
      {
         label = holes.get(m).second;
         index = -1;
         for( int n=0; n<labels.size(); ++n )
            if( labels.get(n).first == label )
               index = labels.get(n).second;
         mem[ holes.get(m).first ] = index;
      }
   
      System.out.println("after replacing labels:");
      showMem( 0, k-1 );
   
       // initialize registers:
      bp = k;  sp = k+2;  ip = 0;  rv = -1;  hp = max;
      numPassed = 0;
       
      int codeEnd = bp-1;
   
      System.out.println("Code is " );
      showMem( 0, codeEnd );
   
      gp = codeEnd + 1;
       
      Scanner userInput = new Scanner(System.in);
      int tmp;
      int n,a,b,c,m;
      int op = 0;
   	
      while(op < max){
      	
         op = mem[ip];
      
         if(op == 0){
            ip++;
         }
         else if(op == 1){
            ip++;
         }
         else if(op == 2){
           
            a = mem[ip+1];
            mem[sp] = bp;
            bp = sp;
            mem[sp+1] = ip + 2;
            ip = a;
            sp += numPassed + 2;
            numPassed = 0;
                  //moves stack frame over
         }
         else if(op == 3){
            a = mem[ip+1];
            mem[sp + 2 + numPassed] = mem[bp + 2 + a];
            numPassed++;
            ip += 2;
         }
         else if(op == 4){
          //increase stack size
            n = mem[ip+1];
            sp=sp + n;
            ip += 2;
         }
         else if(op == 5){
            a = mem[ip+1];
            rv = mem[bp + 2 + a];
            ip = mem[bp+1];
            sp = bp;
            bp = mem[bp];
         
         }
         else if(op == 6){
            ip++; 
            a = mem[ip++];
            mem[bp + 2 + a] = rv; 
         }
         else if(op == 7){
            ip++;
            a = mem[ip];
            ip = a;         		
         }
         else if(op == 8){
            ip++;
            a = mem[ip++];
            b = mem[ip++];
            
            if(mem[bp+2+b] == 0){
            }
            else{
               ip = a;
            }
         }
         else if(op == 9){
            //add b and c, store in a
            ip++;
            a = mem[ip++];
            b = mem[ip++];
            c = mem[ip++];
            mem[bp+2+a] = mem[bp+2+b] + mem[bp+2+c];
         }
         else if(op == 10){
            ip++;
            a = mem[ip++];
            b = mem[ip++];
            c = mem[ip++];
            mem[bp+2+a] = mem[bp+2+b] - mem[bp+2+c];
         }
         else if(op == 11){
            ip++;
            a = mem[ip++];
            b = mem[ip++];
            c = mem[ip++];
            mem[bp+2+a] = mem[bp+2+b] * mem[bp+2+c];         
         }
         else if(op == 12){
            ip++;
            a = mem[ip++];
            b = mem[ip++];
            c = mem[ip++];
            mem[bp+2+a] = mem[bp+2+b] / mem[bp+2+c];
         }
         else if(op == 13){
            ip++;
            a = mem[ip++];
            b = mem[ip++];
            c = mem[ip++];
            mem[bp+2+a] = mem[bp+2+b] % mem[bp+2+c];
         }
         else if(op == 14){
            ip++;
            a = mem[ip++];
            b = mem[ip++];
            c = mem[ip++];
            if(mem[bp+2+b] == mem[bp+2+c]){
               mem[bp+2+a] = 1;
            }
            else{
               mem[bp+2+a] = 0;
            }
         }
         else if(op == 15){
            ip++;
            a = mem[ip++];
            b = mem[ip++];
            c = mem[ip++];
         	   
            if(mem[bp+2+b] != mem[bp+2+c]){
               mem[bp+2+a] = 1;
            }
            else{
               mem[bp+2+a] = 0;
            }
         }
         else if(op == 16){
            ip++;
            a = mem[ip++];
            b = mem[ip++];
            c = mem[ip++];
            
            if(mem[bp + 2 + b] < mem[bp + 2 + c]){
               mem[bp + 2 + a ] = 1;
            }
            else{
               mem[bp +2 + a] = 0;
            }
         }
         else if(op == 17){
            ip++;
            a = mem[ip++];
            b = mem[ip++];
            c = mem[ip++];
            
            if(mem[bp+2+b] <= mem[bp+2+c]){
               mem[bp+2+a] = 1;
            }
            else{
               mem[bp+2+a] = 0;
            }
         }
         else if(op == 18){
            ip++;
            a = mem[ip++];
            b = mem[ip++];
            c = mem[ip++];
         	
            if(mem[bp+2+b] != 0 && mem[bp+2+c] != 0){
               mem[bp+2+a] = 1;
            }
            else{
               mem[bp+2+a] = 0;
            }
         }
         else if(op == 19){
            ip++;
            a = mem[ip++];
            b = mem[ip++];
            c = mem[ip++];
         	
            if(mem[bp+2+b] != 0 || mem[bp+2+c] != 0){
               mem[bp+2+a] = 1;
            }
            else{
               mem[bp+2+a] = 0;
            }
         }
         else if(op == 20){
            ip++;
            a = mem[ip++];
            b = mem[ip++];
            if(mem[bp+2+b] == 0){
               mem[bp+2+a] = 1;
            }
            else{
               mem[bp+2+a] = 0;
            }
         }
         else if(op == 21){
            ip++;
            a = mem[ip++];
            b = mem[ip++];
            n = mem[bp+2+b];
            mem[bp+2+a] = -n;
         }
         else if(op == 22){
            ip++;
            a = mem[ip++];
            n = mem[ip++];
            mem[bp + 2 + a] = n;
         }
         else if(op == 23){
            ip++;
            a = mem[ip++];
            b = mem[ip++];
            mem[bp+2+a] = mem[bp+2+b];
         }
         else if(op == 24){
            ip++;
            a = mem[ip++];
            b = mem[ip++];
            c = mem[ip++];
            n = mem[bp+2+b] + mem[bp+2+c];
            mem[bp+2+a] = mem[n];
         	//Do anything with a pointer somewhere?
         }
         else if(op == 25){
            ip++;
            a = mem[ip++];
            b = mem[ip++];
            c = mem[ip++];
            n = mem[bp+2+a] + mem[bp+2+b];
            mem[n] = mem[bp+2+c];
         	//Do we do shit with pointers in this? What happens to cell c after?
         }
         else if(op == 26){
            System.out.println("Halting...");
            System.exit(0);
         }
         else if(op == 27){
            ip++;
            a = mem[ip];
            int user;
            System.out.print("?" + "  ");
            user = userInput.nextInt();
            mem[bp + 2 + a] = user;
            ip++;
         }
         else if(op == 28){
            ip++;
            a = mem[ip++];
            System.out.print(mem[bp + 2 + a]);
         }
         else if(op == 29){
            ip++;
            System.out.print("\n");	
         }
         else if(op == 30){
            ip++;
            a = mem[ip++];
            
            if(mem[bp + 2 + a] <= 126 && mem[bp + 2 + a] >= 32){
               char temp = (char)mem[bp+2+a];
               System.out.print((temp)); 
               //I think thats how you go from ascii to string but I'm not sure 
            }
         }
         else if(op == 31){
            ip++;
            a = mem[ip++];
            b = mem[ip++];
            m = mem[bp + 2 + b];
            hp -= m;
            mem[bp+2+a] = hp; //Do we put the value in the hp cell, or the value of hp itself?
         }
         else if(op == 32){
            ip++;
            n = mem[ip++];
            bp = gp + n;
            sp = bp + 2;
         	//right now gp is already set with variable codeEnd. May have to adjust if this doesn't work
         }
         else if(op == 33){
            ip++;
            n = mem[ip++];
            a = mem[ip++];
            n += gp;
            mem[n] = mem[bp+2+a];
         }
         else if(op == 34){
            ip++;
            a = mem[ip++];
            n = mem[ip++];
            n += gp;
            mem[bp+2+a] = mem[n];
         }
      }
   }// main

	  // use symbolic names for all opcodes:

	  // op to produce comment on a line by itself
   private static final int noopCode = 0;

	  // ops involved with registers
   private static final int labelCode = 1;
   private static final int callCode = 2;
   private static final int passCode = 3;
   private static final int allocCode = 4;
   private static final int returnCode = 5;  // return a means "return and put
	           // copy of value stored in cell a in register rv
   private static final int getRetvalCode = 6;//op a means "copy rv into cell a"
   private static final int jumpCode = 7;
   private static final int condJumpCode = 8;

	  // arithmetic ops
   private static final int addCode = 9;
   private static final int subCode = 10;
   private static final int multCode = 11;
   private static final int divCode = 12;
   private static final int remCode = 13;
   private static final int equalCode = 14;
   private static final int notEqualCode = 15;
   private static final int lessCode = 16;
   private static final int lessEqualCode = 17;
   private static final int andCode = 18;
   private static final int orCode = 19;
   private static final int notCode = 20;
   private static final int oppCode = 21;
	  
	  // ops involving transfer of data
   private static final int litCode = 22;  // litCode a b means "cell a gets b"
   private static final int copyCode = 23;// copy a b means "cell a gets cell b"
   private static final int getCode = 24; // op a b means "cell a gets
	                                                // contents of cell whose 
	                                                // index is stored in b"
   private static final int putCode = 25;  // op a b means "put contents
	     // of cell b in cell whose offset is stored in cell a"

	  // system-level ops:
   private static final int haltCode = 26;
   private static final int inputCode = 27;
   private static final int outputCode = 28;
   private static final int newlineCode = 29;
   private static final int symbolCode = 30;
   private static final int newCode = 31;
	  
	  // global variable ops:
   private static final int allocGlobalCode = 32;
   private static final int toGlobalCode = 33;
   private static final int fromGlobalCode = 34;

	  // debug ops:
   private static final int debugCode = 35;

	  // return the number of arguments after the opcode,
	  // except ops that have a label return number of arguments
	  // after the label, which always comes immediately after 
	  // the opcode
   private static int numArgs( int opcode )
   {
       // highlight specially behaving operations
      if( opcode == labelCode ) 
         return 1;  // not used
      else if( opcode == jumpCode ) 
         return 0;  // jump label
      else if( opcode == condJumpCode ) 
         return 1;  // condJump label expr
      else if( opcode == callCode ) 
         return 0;  // call label
      
       // for all other ops, lump by count:
      
      else if( opcode==noopCode ||
                opcode==haltCode ||
                opcode==newlineCode ||
                opcode==debugCode
              ) 
         return 0;  // op
      
      else if( opcode==passCode || opcode==allocCode || 
                opcode==returnCode || opcode==getRetvalCode || 
                opcode==inputCode || 
                opcode==outputCode || opcode==symbolCode ||
                opcode==allocGlobalCode
              )  
         return 1;  // op arg1
      
      else if( opcode==notCode || opcode==oppCode || 
                opcode==litCode || opcode==copyCode || opcode==newCode ||
                opcode==toGlobalCode || opcode==fromGlobalCode
      
              ) 
         return 2;  // op arg1 arg2
      
      else if( opcode==addCode ||  opcode==subCode || opcode==multCode ||
                opcode==divCode ||  opcode==remCode || opcode==equalCode ||
                opcode==notEqualCode ||  opcode==lessCode || 
                opcode==lessEqualCode || opcode==andCode ||
                opcode==orCode || opcode==getCode || opcode==putCode
              )
         return 3;
      
      else
      {
         System.out.println("Fatal error: unknown opcode [" + opcode + "]" );
         System.exit(1);
         return -1;
      }
   
   }// numArgs

   private static void showMem( int a, int b )
   {
      for( int k=a; k<=b; ++k )
      {
         System.out.println( k + ": " + mem[k] );
      }
   }// showMem
}
