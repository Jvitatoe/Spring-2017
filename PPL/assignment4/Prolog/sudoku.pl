%  Example:  solving 4 by 4 Sudoku puzzle using Prolog
% -------------------- modified from "Seven Languages in Seven Weeks" by
%                      Bruce Tate
%  To use, do like
%   sudoku([_,_,2,3,_,_,_,_,_,_,_,_,3,4,_,_], [A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P] ).
%     Could do
%       sudoku([_,_,2,3,_,_,_,_,_,_,_,_,3,4,_,_], Solution ).
%     but swipl suppresses output details

delete1(H,[H|T],T).
delete1(X,[H|T],[H|U]) :- delete1( X, T, U ).

equalSets( [], [] ).
equalSets( [H|T], B ) :- member(H,B), delete1(H,B,C), equalSets( T, C ).

%  perm4( X )  means X is a permutation of [1,2,3,4]
perm4( X ) :- equalSets( X, [1,2,3,4] ).

sudoku(Puzzle,Solution) :-
  Solution = Puzzle,

  Puzzle = [A,B,C,D,
            E,F,G,H,
            I,J,K,L,
            M,N,O,P],

  perm4( [A,B,C,D] ),    
  perm4( [E,F,G,H] ),
  perm4( [I,J,K,L] ),
  perm4( [M,N,O,P] ),

  perm4( [A,E,I,M] ),
  perm4( [B,F,J,N] ),
  perm4( [C,G,K,O] ),
  perm4( [D,H,L,P] ),

  perm4( [A,B,E,F] ),
  perm4( [C,D,G,H] ),
  perm4( [I,J,M,N] ),
  perm4( [K,L,O,P] ).



