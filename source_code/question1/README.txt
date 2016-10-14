Question 1:

BPNN:
BPNN directory contains BPNN library with the network file and patter file for XOR problem.
To run:
1. remove the nntrain and nntest file if they exist
2. run make
3. Train the neural network by `./nntrain xorplus.net xorplus.pat`
4. Test the neural networb by `./nntest xorplus.net xorplus.pat weights.dat`

GP:
To run the genetic programme for XOR problem
run `java -jar xor_runnable.jar [training filename] [test filename]`
You can pass the training file and test filename.
The program uses test.txt and training.txt files for training the GP and testing the GP performance
