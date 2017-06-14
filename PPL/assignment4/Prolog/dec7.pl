% equalSets( A, B ) means A and B are lists with the same items
%                   (assume no repeats)

delete1(H,[H|T],T).
delete1(X,[H|T],[H|U]) :- delete1( X, T, U ).

equalSets( [], [] ).
equalSets( [H|T], B ) :- member(H,B), delete1(H,B,C), equalSets( T, C ).

good(A,B,C,D) :- equalSets([A,B,C,D],[1,2,3,4]).

sudoku([A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P]) :-
  good(A,B,C,D), good(E,F,G,H), good(I,J,K,L), good(M,N,O,P),
  good(A,E,I,M), good(B,F,J,N), good(C,G,K,O), good(D,H,L,P),
  good(A,B,E,F), good(C,D,G,H), good(I,J,M,N), good(K,L,O,P).

sudoku9([Aa,Ab,Ac,Ad,Ae,Af,Ag,Ah,Ai,
         Ba,Bb,Bc,Bd,Be,Bf,Bg,Bh,Bi,

good(
  
