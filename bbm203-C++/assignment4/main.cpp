#include <fstream>
#include <sstream>
#include <string>
#include <vector>
#include <iostream>
#include <queue>
using namespace std;

class SecondaryNode {
public:
    string key;
    int data;
    int height;
    SecondaryNode* left;
    SecondaryNode* right;

    SecondaryNode(string key, int data) : key(key), data(data), left(nullptr), right(nullptr), height(1) {}

};

class PrimaryNode {
public:
    string key;
    PrimaryNode* left;
    PrimaryNode* right;
    SecondaryNode* secondaryRoot;

    PrimaryNode(string key) : key(key), left(nullptr), right(nullptr), secondaryRoot(nullptr) {}
};

//Creating the tree class
class TwoPhaseTree {
private:
    PrimaryNode *root;


    static int height(SecondaryNode* N){
        if (N == nullptr)
            return 0;
        return N->height;
    }

    int max(int x, int y)
    {
        if (x > y){
            return x;
        } else{
            return y;
        }
    }
    //Right rotation
    SecondaryNode *rightRotate(SecondaryNode *node1)
    {
        SecondaryNode *node2 = node1->left;
        SecondaryNode *T2 = node2->right;
        node2->right = node1;
        node1->left = T2;
        node1->height = max(height(node1->left),
                            height(node1->right)) + 1;
        node2->height = max(height(node2->left),
                            height(node2->right)) + 1;
        return node2;
    }
    //Left rotation
    SecondaryNode *leftRotate(SecondaryNode *x)
    {
        SecondaryNode *y = x->right;
        SecondaryNode *T2 = y->left;
        y->left = x;
        x->right = T2;
        x->height = max(height(x->left),
                        height(x->right)) + 1;
        y->height = max(height(y->left),
                        height(y->right)) + 1;

        return y;
    }

    int getBalance(SecondaryNode *N)
    {
        if (N == nullptr)
            return 0;
        return height(N->left) - height(N->right);
    }


    //Inserting a primary node
    void insertPrimary(PrimaryNode *&p, string key) {
        if (!p) {
            p = new PrimaryNode(key);
        } else if (key < p->key) {
            insertPrimary(p->left, key);
        } else {
            insertPrimary(p->right, key);
        }
    }
    //Inserting a secondary node
    SecondaryNode* insertSecondary(SecondaryNode*& s, string key, int data) {
        if (!s) {
            s = new SecondaryNode(key, data);
        } else if (key < s->key) {
            insertSecondary(s->left, key, data);
        } else {
            insertSecondary(s->right, key, data);
        }
        s->height = 1 + max(height(s->left),
                            height(s->right));

        int balance = getBalance(s);

        // Left Left
        if (balance > 1 && key < s->left->key)
            return rightRotate(s);

        // Right Right
        if (balance < -1 && key > s->right->key)
            return leftRotate(s);

        // Left Right
        if (balance > 1 && key > s->left->key)
        {
            s->left = leftRotate(s->left);
            return rightRotate(s);
        }
        // Right Left Case
        if (balance < -1 && key < s->right->key)
        {
            s->right = rightRotate(s->right);
            return leftRotate(s);
        }
        return s;

    }

    PrimaryNode* findPrimary(PrimaryNode* p, string key) {
        if (!p || p->key == key) {
            return p;
        } else if (key < p->key) {
            return findPrimary(p->left, key);
        } else {
            return findPrimary(p->right, key);
        }
    }

    SecondaryNode* findSecondary(SecondaryNode* s, string key) {
        if (!s || s->key == key) {
            return s;
        } else if (key < s->key) {
            return findSecondary(s->left, key);
        } else {
            return findSecondary(s->right, key);
        }


    }

    void deletePrimary(PrimaryNode*& p) {
        if (!p) return;
        deletePrimary(p->left);
        deletePrimary(p->right);
        delete p;
    }



    SecondaryNode* findParent(SecondaryNode* root, SecondaryNode* node) {
        if (root->left == node || root->right == node) {
            return root;
        }
        if (node->key < root->key) {
            return findParent(root->left, node);
        } else {
            return findParent(root->right, node);
        }
    }

public:
    TwoPhaseTree() : root(nullptr) {}

    ~TwoPhaseTree() {
        deletePrimary(root);
    }

    void insert(string category, string name, int price) {
        PrimaryNode* p = findPrimary(root, category);
        if (!p) {
            insertPrimary(root, category);
            p = findPrimary(root, category);
        }
        p->secondaryRoot = insertSecondary(p->secondaryRoot, name, price);
    }



    void remove(string category, string name) {
        PrimaryNode* p = findPrimary(root, category);
        if (!p) return; // category not found
        SecondaryNode* s = findSecondary(p->secondaryRoot, name);
        if (!s) return; // name not found

        //If there is no children
        if (!s->left && !s->right) {
            if (p->secondaryRoot == s){
                p->secondaryRoot = nullptr;
                delete s;
            }
            else {
                SecondaryNode* parent = findParent(p->secondaryRoot, s);
                if (parent->key > s->key){
                    parent->left = nullptr;
                }
                else{
                    parent->right = nullptr;
                }
            }
        }
        // If the node has one child
        else if (!s->left) {
            if (p->secondaryRoot == s){
                p->secondaryRoot = s->right;
                delete s;
            }
            else {
                SecondaryNode* parent = findParent(p->secondaryRoot, s);
                if (parent->key > s->key){
                    parent->left = s->right;
                }
                else{
                    parent->right = s->right;
                }
                delete s;
            }
        }
        else if (!s->right) {
            if (p->secondaryRoot == s){
                p->secondaryRoot = s->left;
                delete s;
            }
            else {
                SecondaryNode* parent = findParent(p->secondaryRoot, s);
                if (parent->key > s->key){
                    parent->left = s->left;
                    delete s;
                }
                else{
                    parent->right = s->left;
                    delete s;
                }
            }
        }
        //If the node has two children
        else {
            // Find successor
            SecondaryNode* successor = s->right;
            while (successor->left) successor = successor->left;

            SecondaryNode* parent = findParent(p->secondaryRoot, successor);
            s->key = successor->key;
            s->data = successor->data;
            // Delete successor
            if (successor -> key == parent ->key){
                parent -> right = nullptr;
            }
            else{
                parent->left = nullptr;
            }

        }
    }




    void printAllItems(ofstream& file) {
        if (!root) return;

        queue<PrimaryNode*> q;
        q.push(root);

        file << "{\n";
        while (!q.empty()) {
            int size = q.size();
            while (size--) {
                PrimaryNode* p = q.front();
                q.pop();
                file << "\"" << p->key << "\":\n";

                // Printing the secondary nodes
                if (p->secondaryRoot == NULL){
                    file << "\t" << "{}" << "\n";
                }
                else {
                    SecondaryNode* s = p->secondaryRoot;
                    queue<SecondaryNode*> q2;
                    q2.push(s);
                    int x = 1;
                    while (!q2.empty()) {
                        int size2 = q2.size();
                        while (size2--) {
                            SecondaryNode* s = q2.front();
                            q2.pop();
                            file << "\t\"" << s->key << "\":\"" << s->data << "\"";
                            if (x == 0){
                                file << ",";
                            }
                            else {
                                file << "\n";
                            }
                            x = 1;
                            if (s->left) q2.push(s->left);
                            if (s->right) q2.push(s->right);
                            if (s->right && s->left){
                                x = 0;
                            }
                        }
                    }
                    if (p->left) q.push(p->left);
                    if (p->right) q.push(p->right);
                }

            }
        }
        file << "}\n";
    }
    //Update data function
    void updateData(string category, string name, int data){
        PrimaryNode* p = findPrimary(root, category);
        SecondaryNode* s = findSecondary(p->secondaryRoot, name);
        s->data = data;
    }
    //Print all items in the given category
    void printAllItemsInCategory(string category, ofstream& file){
        PrimaryNode* p = findPrimary(root, category);
        file << "\"" << p->key << "\":\n";
        if (p->secondaryRoot == NULL){
            file << "\t" << "{}" << "\n";
        }
        else {
            SecondaryNode *s = p->secondaryRoot;
            queue<SecondaryNode *> q2;
            q2.push(s);
            int x = 1;
            while (!q2.empty()) {
                int size2 = q2.size();
                while (size2--) {
                    SecondaryNode *s = q2.front();
                    q2.pop();
                    file << "\t\"" << s->key << "\":\"" << s->data << "\"";
                    if (x == 0) {
                        file << ",";
                    } else {
                        file << "\n";
                    }
                    x = 1;
                    if (s->left) q2.push(s->left);
                    if (s->right) q2.push(s->right);
                    if (s->right && s->left) {
                        x = 0;
                    }
                }
            }
        }
    }
    //Print the given item
    void printItem(string category, string name, ofstream& file){
        PrimaryNode* p = findPrimary(root, category);
        SecondaryNode* s = findSecondary(p->secondaryRoot, name);
        if (!s){
            file << "{}" << "\n";
        }
        else {
            file << "{" << "\n";
            file << "\"" << p->key << "\":\n";
            file << "\t\"" << s->key << "\":\"" << s->data << "\"" << "\n" << "}" << "\n";
        }

    }
    //Find the given item
    void find(string category, string name, ofstream& file){
        PrimaryNode* p = findPrimary(root, category);
        SecondaryNode* s = findSecondary(p->secondaryRoot, name);
        if (!s){
            file << "{}" << "\n";
        }
        else {
            file << "{" << "\n";
            file << "\"" << p->key << "\":\n";
            file << "\t\"" << s->key << "\":\"" << s->data << "\"" << "\n" << "}" << "\n";
        }
    }
};


int main() {
    ifstream file("input1.txt");
    string line;
    TwoPhaseTree tree;
    TwoPhaseTree tree2;
    ofstream outputFile("output1.txt");
    while (getline(file, line)) {
        istringstream iss(line);
        string token;
        vector<string> tokens;
        while (getline(iss, token, '\t')) {
            tokens.push_back(token);
        }
        if (tokens[0] == "insert"){
            tree.insert(tokens[1], tokens[2], stoi(tokens[3]));
        }
        else if (tokens[0] == "remove"){
            tree.remove(tokens[1], tokens[2]);
        }
        else if (tokens[0] == "printAllItems"){
            outputFile << "command:printAllItems" << "\n";
            tree.printAllItems(outputFile);
        }
        else if (tokens[0] == "printAllItemsInCategory"){
            outputFile << "command:printAllItemsInCategory" << "\t" << tokens[1] << "\n" << "{" << "\n";
            tree.printAllItemsInCategory(tokens[1], outputFile);
            outputFile << "}" << "\n";
        }
        else if (tokens[0] == "printItem"){
            outputFile << "command:printItem" << "\t" << tokens[1] << "\t" << tokens[2] << "\n";
            tree.printItem(tokens[1], tokens[2], outputFile);
        }
        else if (tokens[0] == "find"){
            outputFile << "command:find" << "\t" << tokens[1] << "\t" << tokens[2] << "\n";
            tree.find(tokens[1], tokens[2], outputFile);
        }
        else if (tokens[0] == "updateData"){
            tree.updateData(tokens[1], tokens[2], stoi(tokens[3]));
        }
    }
    return 0;
}

