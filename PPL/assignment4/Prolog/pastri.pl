%  nextRow( Row, Next ) means
% that Next is a list of sums of two successive items in Row
%  (example:  if Row=[1,5,7,11,8] then Next should be 
%                    [ 6, 12, 18, 19].
%
%  assume Row has at least 2 items

nextRow( [A,B], [X] ) :- X is A+B.

nextRow( [A,B|T], [X|W] ) :- X is A+B, nextRow([B|T], W ).
