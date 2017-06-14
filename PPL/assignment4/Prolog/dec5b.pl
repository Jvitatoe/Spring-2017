%  del( A, B, C) means if you delete item A from list B you get C

del( A, [A|R], R ).

del( A, [H|T], [H|S] ) :- del( A, T, S ).

