#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
#include <vector>
#include <iomanip>
#include <algorithm>
#define MAX_NUM 9999999999
using namespace std;

//CASHIER CLASS
class Cashier {
public:
    double emptyTime = MAX_NUM;
    double workTime = 0;
    bool isFull = false;
    int cusNum{};

};

class Barista {
public:
    double emptyTime = MAX_NUM;
    double workTime = 0;
    bool isFull = false;
    int cusNum;

};

//CUSTOMER CLASS
class Customer {
public:
    double arrivalTime;
    double orderTime;
    double brewTime;
    double orderPrice;
    int num;
    bool gaveOrder = false;
    bool wentBrew = false;
    double turnaroundTime;

    Customer(){}

    Customer(double a, double b, double c, double d, int e) {
        arrivalTime = a;
        orderTime = b;
        brewTime = c;
        orderPrice = d;
        num = e;
        }
};
//QUEUE CLASS
class Queue {
public:
    vector<Customer> myQueue;
    int maxSize = 0;

    Queue() {
    }


    bool IsEmpty() {
        if (myQueue.empty()) return true;
        else return false;
    }
    //Add customer to queue
    void enQueue(Customer customer) {
        myQueue.push_back(customer);
        if (myQueue.size() > maxSize){
            maxSize = myQueue.size();
        }

    }
    //Erase the customer from queue
    void deQueue() {
        if (IsEmpty()) {
        }
        else {
            myQueue.erase(myQueue.begin());

        }
    }
};
//PRIORITY QUEUE CLASS
class PriorityQueue{
public:
    vector<Customer> myQueue;
    int front;
    int rear;
    int maxSize = 0;

    PriorityQueue(){
        front = -1;
        rear = -1;
    }
    bool IsEmpty() {
        if (myQueue.empty()) return true;
        else return false;
    }
    //Add customer to queue
    void enQueue (Customer customer){
        if (front == -1){
            front = 0;
            rear = 0;
            myQueue.push_back(customer);
            maxSize = 1;
        }
        else {
            myQueue.push_back(customer);
            if (myQueue.size() > maxSize){
                maxSize = myQueue.size();
            }
            double minPrice = 0;
            //Making the order with the highest price front
            for (int i = 0; i < myQueue.size(); i++){
                if (myQueue[i].orderPrice > minPrice){
                    minPrice = myQueue[i].orderPrice;
                    front = i;
                }
            }
        }
    }
    //Take the customer from queue and delete it
    void deQueue(){
        double minPrice = 0;
        myQueue.erase(myQueue.begin() + front);
        for (int i = 0; i < myQueue.size(); i++){
            if (myQueue[i].orderPrice > minPrice){
                minPrice = myQueue[i].orderPrice;
                front = i;
            }
        }
    }
};


int main(int argc, char** argv) {
    int cashierCount;
    int customerCount;
    int baristaCount;
    int x = 0;
    fstream newFile;            //CREATING THE INPUT AND OUTPUT FILES
    newFile.open(argv[1]);
    ofstream outputFile(argv[2]);
    string line;
    vector<Customer> customerList;
    Queue cashierQueue;
    PriorityQueue baristaQueue;

    if(newFile.is_open()){
        vector<double> v;
        int lineCount = 0;
        while (getline(newFile, line)){ //Reading the file line by line
            istringstream is( line );
            double num;
            while(is >> num){
                v.push_back(num);
                }
            if (lineCount == 0){    //Gets the cashier count from the first line
                cashierCount = num;
            }
            else if (lineCount == 1){   //Gets the order count from the second line
                customerCount = num;
                baristaCount = cashierCount/3;
            }
            else {
                //Create a customer object for each order
                customerList.push_back(Customer(v[0], v[1], v[2], v[3], x));
                x += 1;
            }
            v.clear();
            lineCount += 1;
        }
    }
    //Initializing variables
    Cashier cashierList[cashierCount];
    Barista baristaList[baristaCount];
    double time = 0;
    int csIndex = 0;
    bool finished = false;
    bool customersFinished = false;
    double csArriveTime;
    while (!finished){
        if (!customersFinished){
            csArriveTime = customerList[csIndex].arrivalTime;
        }
        else {
            csArriveTime = MAX_NUM;
        }
        double cashierFinishTime = MAX_NUM;
        int cashierIndex = 0;
        double baristaFinishTime = MAX_NUM;
        int baristaIndex;
        for(int i = 0; i < cashierCount; i++){      //FINDING THE MINIMUM CASHIER EMPTY TIME
            if (cashierList[i].emptyTime < cashierFinishTime && cashierList[i].emptyTime > time){
                cashierFinishTime = cashierList[i].emptyTime;
                cashierIndex = i;
            }
        }
        for(int i = 0; i < baristaCount; i++){      //FINDING THE MINIMUM BARISTA EMPTY TIME
            if (baristaList[i].emptyTime < baristaFinishTime && baristaList[i].emptyTime > time){
                baristaFinishTime = baristaList[i].emptyTime;
                baristaIndex = i;
            }

        }
        //We try to find the smallest value between arrive time, cashier empty time and barista empty time.
        if (csArriveTime != MAX_NUM || cashierFinishTime != MAX_NUM || baristaFinishTime != MAX_NUM){   //END IF THEY ARE ALL MAX NUM
            if (csArriveTime < baristaFinishTime && csArriveTime <= cashierFinishTime){     //If arrive time is the smallest
                time = csArriveTime;
                if(!cashierQueue.IsEmpty()){    //Check the cashier queue
                    cashierQueue.enQueue(customerList[csIndex]);
                }
                else{
                    for(int i = 0; i < cashierCount; i++){
                        if(!customerList[csIndex].gaveOrder){
                            if(!cashierList[i].isFull){     //Go to the cashier if it isn't full.
                                cashierList[i].emptyTime = customerList[csIndex].arrivalTime + customerList[csIndex].orderTime;
                                cashierList[i].workTime += customerList[csIndex].orderTime;
                                cashierList[i].isFull = true;
                                cashierList[i].cusNum = csIndex;
                                customerList[csIndex].gaveOrder = true;
                            }
                            else {
                                //Go to the cashier if its empty time is smaller than arrive time
                                if(cashierList[i].emptyTime < customerList[csIndex].arrivalTime){
                                    cashierList[i].emptyTime =  customerList[csIndex].arrivalTime + customerList[csIndex].orderTime;
                                    cashierList[i].workTime += customerList[csIndex].orderTime;
                                    cashierList[i].cusNum = csIndex;
                                    customerList[csIndex].gaveOrder = true;
                                }
                            }
                        }
                    }
                    //If all the cashiers are unavailable add to the queue
                    if(!customerList[csIndex].gaveOrder){
                        cashierQueue.enQueue(customerList[csIndex]);
                    }
                }
                if (csIndex < customerCount - 1){
                    csIndex++;
                }
                else{
                    customersFinished = true;
                }
            }
            //Go in if the cashier finish time is smallest
            else if (cashierFinishTime < baristaFinishTime && cashierFinishTime < csArriveTime){
                time = cashierFinishTime;
                int customerIndex = cashierList[cashierIndex].cusNum;
                //Check the barista queue and add the order if it isnt empty
                if(!baristaQueue.IsEmpty()){
                    baristaQueue.enQueue(customerList[cashierList[cashierIndex].cusNum]);
                }
                else{
                    for (int i = 0; i < baristaCount; i++){
                        if(!customerList[customerIndex].wentBrew){
                            //Go to the barista if it isn't full.
                            if (!baristaList[i].isFull){
                                baristaList[i].emptyTime = time + customerList[customerIndex].brewTime;
                                baristaList[i].workTime += customerList[customerIndex].brewTime;
                                baristaList[i].isFull = true;
                                baristaList[i].cusNum = customerList[customerIndex].num;
                                customerList[customerIndex].wentBrew = true;
                            }
                            else{
                                //Go to the barista if its empty time is smaller than arrive time
                                if (baristaList[i].emptyTime < time){
                                    baristaList[i].emptyTime = time + customerList[customerIndex].brewTime;
                                    baristaList[i].workTime += customerList[customerIndex].brewTime;
                                    baristaList[i].cusNum = customerList[customerIndex].num;
                                    customerList[customerIndex].wentBrew = true;
                                }
                            }
                        }
                    }
                    //If all the baristas are unavailable add to the queue
                    if (!customerList[customerIndex].wentBrew){
                        baristaQueue.enQueue(customerList[customerIndex]);
                    }
                }

                //Take the first order from the queue and send it to the available cashier
                if (!cashierQueue.IsEmpty()){
                    cashierList[cashierIndex].emptyTime += cashierQueue.myQueue[0].orderTime;
                    cashierList[cashierIndex].workTime += cashierQueue.myQueue[0].orderTime;
                    cashierList[cashierIndex].cusNum = cashierQueue.myQueue[0].num;
                    cashierQueue.deQueue();
                }
                else {
                    cashierList[cashierIndex].isFull = false;
                }


            }
            //Go in if the barista finish time is smallest
            else if (baristaFinishTime < csArriveTime && baristaFinishTime < cashierFinishTime){
                time = baristaFinishTime;
                customerList[baristaList[baristaIndex].cusNum].turnaroundTime = time - customerList[baristaList[baristaIndex].cusNum].arrivalTime;
                //Take the order with the largest price from the barista queue
                if (!baristaQueue.IsEmpty()){
                    baristaList[baristaIndex].emptyTime = time + baristaQueue.myQueue[baristaQueue.front].brewTime;
                    baristaList[baristaIndex].workTime += baristaQueue.myQueue[baristaQueue.front].brewTime;
                    baristaList[baristaIndex].cusNum = baristaQueue.myQueue[baristaQueue.front].num;
                    baristaQueue.deQueue();
                }
            }
        }
        else{
            finished = true;
        }
    }
    //WRITING THE OUTPUT TO THE FILE
    outputFile << time << "\n";
    outputFile << cashierQueue.maxSize << "\n";
    outputFile << baristaQueue.maxSize << "\n";
    for (int i = 0; i < cashierCount; i++){
        outputFile << fixed << setprecision(2) << cashierList[i].workTime/time << endl;
    }
    for (int i = 0; i < baristaCount; i++){
        outputFile << fixed << setprecision(2) << baristaList[i].workTime/time << endl;
    }
    for (int i = 0; i < customerCount; i++){
        outputFile << fixed << setprecision(2) << customerList[i].turnaroundTime <<endl;
    }
    outputFile << endl;
    outputFile << "Model 2" << endl << -1;
}
