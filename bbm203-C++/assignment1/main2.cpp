#include <iostream>
#include <fstream>
#include <sstream>

using namespace std;

int main(int argc, char** argv) {

    ofstream outputFile(argv[5]);  //Taking the name and creating the output file.

    //Reading the matrix size and splitting from 'x'.
    string size1 = argv[1];
    string delimiter = "x";
    size_t pos;
    string size2 = size1.substr(0, size1.find(delimiter));
    while ((pos = size1.find(delimiter)) != string::npos) {
        size2 = size1.substr(0, pos);
        size1.erase(0, pos + delimiter.length());
    }
    int rows = stoi(size2);
    int columns = stoi(size1);

    //Reading the size of key map.
    string keySize1 = argv[2];
    int keySize = stoi(keySize1);

    //Creating the 2D matrix.
    int matrix[rows][columns];
    int key[keySize][keySize];

    //READING THE MAP FILE
    fstream mapFile;
    mapFile.open(argv[3], ios::in);
    if(mapFile.is_open()) {
        string line;
        int i = 0, j = 0;
        while (getline(mapFile, line)) {
            istringstream is( line );
            basic_string<char> n;
            while( is >> n ) {
                if (i < rows){
                    if (j < columns) {
                        matrix[i][j] = stoi(n);
                        j++;
                    }
                    else {
                        i++;
                        j = 0;
                        matrix[i][j] = stoi(n);
                        j++;
                    }
                }
            }
        }
        mapFile.close();
    }
    //READING THE KEY FILE
    fstream keyFile;
    keyFile.open(argv[4], ios::in);
    if(keyFile.is_open()){
        string line1;
        int i = 0, j = 0;
        while (getline(keyFile, line1)) {
            istringstream is( line1 );
            basic_string<char> n;
            while( is >> n ) {
                if (i < keySize){
                    if (j < keySize) {
                        key[i][j] = stoi(n);
                        j++;
                    }
                    else {
                        i++;
                        j = 0;
                        key[i][j] = stoi(n);
                        j++;
                    }
                }
            }
        }
        keyFile.close();
    }

    //Multiplying the coordinates that overlap and adding them to find 'sum'.
    int x = keySize/2, y = keySize/2;
    int a = keySize/2;
    while (true){
        int sum = 0;
        int keyI = 0, keyJ;
        for (int i = x - a; i <= x + a; i ++){
            keyJ = 0;
            for (int j = y - a; j <= y + a; j ++){
                sum += matrix[i][j] * key[keyI][keyJ];
                keyJ += 1;
            }
            keyI += 1;
        }
        outputFile << x << "," << y << ":" << sum << endl;

        //Checking the sum's remainder of 5.
        if (sum % 5 == 0){  //Found the treasure if true.
            break;
        }
        else if ( sum % 5 == 1){  //Go up if true.
            x -= keySize;
            if (x < 0){   //Check if it is out of bounds.
                x += 2*keySize;
            }
        }
        else if ( sum % 5 == 2){  //Go down.
            x += keySize;
            if (x > rows){  //Check if it is out of bounds.
                x -= 2*keySize;
            }
        }
        else if ( sum % 5 == 3){  //Go right
            y += keySize;
            if (y > columns) {  //Check if it is out of bounds.
                y -= 2 * keySize;
            }
        }
        else if ( sum % 5 == 4){  //Go left.
            y -= keySize;
            if (y < 0){  //Check if it is out of bounds.
                y += 2*keySize;
            }
        }
    }
    outputFile.close();
    return 0;
}
