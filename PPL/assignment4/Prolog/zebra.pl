% right(X,Y,L) means "X is immediately to the
%   right of Y in the list L"
right(X,Y,L) :- append(_,[Y,X|_], L).

%  next(X,Y,L) means "X and Y are adjacent in the list L"
next(X,Y,L) :- right(X,Y,L).
next(X,Y,L) :- right(Y,X,L).

start(S) :- length(S,5),
  member([english,_,_,_,red],S),
  member([spanish,dog,_,_,_],S),
  member([_,_,_,coffee,green],S),
  member([ukrainian,_,_,tea,_],S),
  member([_,serpent,winston,_,_],S),
  member([_,_,kool,_,yellow],S),
  member([_,_,luckyStrike,juice,_],S),
  member([japanese,_,kent,_,_],S),
  next([_,_,_,_,green],[_,_,_,_,white],S),
  S=[_,_,[_,_,_,milk,_],_,_],
  S=[[norwegian,_,_,_,_],_,_,_,_],
  next([_,_,chesterfield,_,_],[_,fox,_,_,_],S),
  next([_,_,kool,_,_],[_,horse,_,_,_],S),
  next([norwegian,_,_,_,_],[_,_,_,_,blue],S),
  member([_,zebra,_,_,_],S),
  member([_,_,_,water,_],S).
