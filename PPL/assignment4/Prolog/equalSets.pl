% delete1(A,B,C) means:
%   delete one A from list B to produce the list C 
%   assuming there is an A in list B

delete1(H,[H|T],T).
delete1(X,[H|T],[H|U]) :- delete1( X, T, U ).

%  equalSets( X, Y ) means:
%   X and Y are lists with no repeats and they have the same items,
%   probably in different order

%  equalSets( [1,2,3], [3,1,2] ).  is true

equalSets( [], [] ).

%  wish we could: equalSets( [H|T], B ) :- member(H,B), equalSets( T, B-H ).

equalSets( [H|T], B ) :- member(H,B), delete1(H,B,C), equalSets( T, C ).
