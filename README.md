# crypto-rsa

This repo is an experiment repo. It is used to demonstrate paillier's method, and both the power of RSA and the weaknesses when it is badly used.

RSA Key Class is composed of the three keys (N, E, D) that are part of the public key (E, N) and the private key (D, N).

Some tests were made in the test package.

## Question problem :

Rules :
Alice has created 10 questions with Bob.
Bob has to choose one question to be answered by Alice

Problem : 
Alice wants Bob to only know the answer to the question he chose
Bob doesn't want Alice to know the question he has chosen

Implement a protocol that solves this problem.
