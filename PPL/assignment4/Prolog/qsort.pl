% qsort(A,B) means that A quicksorted yields B

qsort([],[]).

qsort([H|T],S) :- partition(H,T,L,R),
                  qsort(L,L1),
                  qsort(R,R1),
                  app(L1,[H|R1],S).

%  partition(A,B,C,D) means
%   A is an item, B is partitioned into C and D with
%   all guys in C < A and all the guys in D >= A

partition(P, [A|X], [A|Y], Z ) :- A<P,
                                  partition(P,X,Y,Z).

partition(P, [A|X], Y, [A|Z] ) :- A>=P,
                                  partition(P,X,Y,Z).

partition(P,[],[],[]).

/*  app(X,Y,L) means L is the list X followed by the list Y  */
app([],Y,Y).
app([H|T],Y,[H|W]) :- app(T,Y,W).
