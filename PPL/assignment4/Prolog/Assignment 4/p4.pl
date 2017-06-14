
% nextRow (Row, Next) means: if Row is a sequence of terms generated
% from a polynomial function, f(x), such that row is given by: [f(m-n),
% f(m-(n-1)), ..., f(m)], then Next is the sequence of terms generated
% by finding the differences between each term, i.e.:
% [f(m-n)-f(m-(n-1)), f(m-(n-2))-f(m-(n-3)), ... , f(m-1)-f(m)].

nextRow( [A,B], [L]) :- L is B-A.
nextRow( [A,B|T], [L|W]) :- L is B-A, nextRow([B|T], W).

% lastItem (List, Item) means: if List is a list of atoms, then Item is
% atom in that list.

lastItem( [A], A ).
lastItem( [_ |T], A) :- lastItem( T, A ).

% nextItem(Seq, Next) means: if Seq is the sequence of terms
% generated from a polynomial function, f(x), such that Seq is
% given by: [f(m-n), f(m-(n-1)), ..., f(m-1)], then Next is f(m),
% assuming that the number of terms in Seq is sufficient for the degree
% of f(x), and both n and m are integers.
% NOTE: for a polynomial of degree P, Seq must contain at least P+1
% terms in order to accurately determine Next.
nextItem([A], A).
nextItem([A|T], N) :- lastItem(T, X), nextRow([A|T], W), nextItem(W, Y), N is X+Y.




